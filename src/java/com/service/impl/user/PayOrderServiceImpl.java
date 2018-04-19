package com.service.impl.user;

import com.Constant.CommonConstants;
import com.Constant.DocumentNoEnum;
import com.Constant.WxPayConstants;
import com.entity.Activity;
import com.entity.IdentifyOrder;
import com.entity.PayOrder;
import com.entity.SignupInfo;
import com.exception.MessageException;
import com.service.impl.common.BaseServiceImpl;
import com.service.user.PayOrderService;
import com.util.*;
import com.vo.JsPayVo;
import com.vo.OrderVo;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hibernate.criterion.CriteriaSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by victor on 2018/4/4.
 */
@Service("payOrderService")
public class PayOrderServiceImpl extends BaseServiceImpl<PayOrder> implements PayOrderService {
    Logger logger = LoggerFactory.getLogger(PayOrderServiceImpl.class);
    @Override
    public JsPayVo toPay()  throws MessageException {
        //1.校验微信id
        Object openidObj = SessionManager.getAttribute("openid");
        if(openidObj == null){
            throw new MessageException().setErrorMsg("请进行微信认证").setErrorCode(CommonConstants.errorCode.WITHOUT_WECHAT);
        }

        String openid = openidObj.toString();
//        String openid = "1";
        //校验活动
        Object activityIdObj = SessionManager.getAttribute("activityId");
        if(activityIdObj == null){
            throw new MessageException().setErrorMsg("非法访问，请重新进入").setErrorCode(CommonConstants.errorCode.ACCESS_VOLATION);
        }
        //活动id
        Long activityId = Long.parseLong(activityIdObj.toString());

        //校验活动当前状态
        String searchActivity = "from Activity where activityId=:id";
        List<Activity> activityList = super.getSession().createQuery(searchActivity).setLong("id",activityId).list();
        Activity activity = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(new Date());
        if(CollectionUtil.isEmptyCollection(activityList)){
            throw new MessageException().setErrorMsg("活动不存在").setErrorCode(CommonConstants.errorCode.VALIDATE_FAIL);
        }else {
            activity = activityList.get(0);

            if(currentTime.compareTo(activity.getStartTime()) < 0){
                throw new MessageException().setErrorMsg("活动还没开始").setErrorCode(CommonConstants.errorCode.VALIDATE_FAIL);
            }
            if(currentTime.compareTo(activity.getEndTime()) > 0){
                throw new MessageException().setErrorMsg("活动已结束").setErrorCode(CommonConstants.errorCode.VALIDATE_FAIL);
            }
        }


        //
        //1.获取订单信息
        OrderVo orderVo = this.getOrderVoByActivityAndOpenid(openid,activity);
        PayOrder payOrder = orderVo.getPayOrder();
        List<SignupInfo> signupInfoList = orderVo.getSignupInfoList();


        //6.调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder 生产预支付订单
        String prepay_id="";
        try {
            //调用支付接口
            String strResult = this.callPayInterface(openid,payOrder);
            String returnCode = WechatUtil.getXmlPara(strResult,"return_code");
            if("SUCCESS".equals(returnCode)){
                String result_code = WechatUtil.getXmlPara(strResult,"result_code");
                if("SUCCESS".equals(result_code)){
                    prepay_id = WechatUtil.getXmlPara(strResult,"prepay_id");
                    System.out.println(prepay_id);
                    logger.info("prepay_id={}",prepay_id);
                    payOrder.setStatus(CommonConstants.orderStatus.PAYING);//订单状态改为支付中
                    payOrder.setPayTime(currentTime);
                    super.update(payOrder);
                    //把订单号保存到报名信息里面
                    for(SignupInfo signupInfo : signupInfoList){
                        signupInfo.setStatus(CommonConstants.orderStatus.PAYING);//报名信息状态改为支付中
                        super.getSession().update(signupInfo);
                    }
                }else{
                    String err_code = WechatUtil.getXmlPara(strResult,"err_code");
                    logger.info("err_code",err_code);
                    System.out.println(err_code);
                    logger.error("调用接口异常失败,error:{}",WechatUtil.getXmlPara(strResult,"return_msg"));
                    throw new MessageException().setErrorMsg("后台出小差了，请稍后再试");
                }
            }else{
                logger.error("调用接口异常失败,error:{}",WechatUtil.getXmlPara(strResult,"return_msg"));
                throw new MessageException().setErrorMsg("后台出小差了，请稍后再试");
            }

        } catch (MessageException e1){
            throw e1;
        } catch (Exception e) {
            logger.error("统一支付接口获取预支付订单出错", e);
            throw new MessageException().setErrorMsg("后台出小差了，请稍后再试");
        }



        //7.将预支付订单的id和其他信息生成签名并一起返回到jsp页面
        String nonce_str= MD5.MD5Encode(String.valueOf(new Random().nextInt(10000)),"utf-8");
        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String packages = "prepay_id="+prepay_id;
        finalpackage.put("appId",  WxPayConstants.APPID);
        finalpackage.put("timeStamp", timestamp);
        finalpackage.put("nonceStr", nonce_str);
        finalpackage.put("package", packages);
        finalpackage.put("signType", "MD5");

        String finalsign = WechatUtil.createSign(finalpackage);
        logger.debug("finalsign={}",finalsign);
        JsPayVo vo = new JsPayVo();
        vo.setAppid(WxPayConstants.APPID);
        vo.setTimeStamp(timestamp);
        vo.setNonceStr(nonce_str);
        vo.setPackageValue(packages);
        vo.setPaySign(finalsign);
        vo.setOrderNo(payOrder.getOrderNo());
        return vo;
    }

    @Override
    public OrderVo getOrderVoByActivityAndOpenid(String openid, Activity activity) throws MessageException{
        OrderVo vo = new OrderVo();
        //1.通过微信id以及活动id获取未支付的报名信息
        Long activityId = activity.getActivityId();
        String hql = "from SignupInfo where 1=1 and status=:status and openid=:openid and activity_id=:activityId";
        List<SignupInfo> signupInfoList = super.getSession().createQuery(hql).setInteger("status",CommonConstants.signupStatus.INIT)
                                            .setString("openid",openid).setLong("activityId",activityId).list();
        if(CollectionUtil.isEmptyCollection(signupInfoList)){
            throw new MessageException("您没有未付款的报名信息",CommonConstants.errorCode.VALIDATE_FAIL);
        }

        String orderNo = "";//订单号
        PayOrder payOrder = null;
        String signupNumbers = "";
        //2.循环遍历报名信息，倘若存在多个订单号，则新增一个订单，原订单取消
        //  如果不存在订单号则，新增一个订单
        //  如果只有一个订单号，则判断该订单的金额与当前的总数是否相同，相同则用该订单，不相同则新增一个订单，原订单取消
        for(SignupInfo signupInfo : signupInfoList){
            signupNumbers += "," + signupInfo.getNumber();
            //存在多个订单，新增一个订单
            if(!StringUtil.isEmptyString(orderNo) && !orderNo.equals(signupInfo.getOrderNo()) && payOrder == null){
                payOrder = this.createPayOrderBySignup(signupInfoList,activity);
            }else{
                orderNo = signupInfo.getOrderNo();
            }
        }

        signupNumbers = signupNumbers.substring(1);

        //不存在订单号，新增一个订单
        if(StringUtil.isEmptyString(orderNo)){
            payOrder = this.createPayOrderBySignup(signupInfoList,activity);
        }
        //只存在一个订单
        if(payOrder == null){
            //根据订单号读取数据库中的订单
            List<PayOrder> orderList = super.getSession().createQuery("from PayOrder where 1=1 and orderNo=:orderNo")
                                        .setString("orderNo",orderNo).list();
            if(CollectionUtil.isEmptyCollection(orderList)){
                payOrder = createPayOrderBySignup(signupInfoList,activity);
            }else{
                payOrder = orderList.get(0);
                //如果金额不相同则新增一个订单
                if(!payOrder.getMoney().equals(signupInfoList.size()*activity.getMoney())){
                    payOrder = createPayOrderBySignup(signupInfoList,activity);
                }else{
                    String createdTime = payOrder.getCreatedTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentTime = sdf.format(new Date());//当前时间
                    //支付時間超過一小時了，取消該單，重新生成
                    if(Tools.dateDiff("H",createdTime,currentTime) > 1){
                        //修改当前订单状态
                        String sql= "update pay_order set status=:status ,expire_time=:expireTime" +
                                " where order_no=:orderNo and status=:oldStatus";
                        super.getSession().createSQLQuery(sql).setString("orderNo",payOrder.getOrderNo()).setInteger("status",CommonConstants.orderStatus.EXPIRE)
                                .setInteger("oldStatus",CommonConstants.signupStatus.INIT).setString("expireTime",currentTime).executeUpdate();
                        payOrder = createPayOrderBySignup(signupInfoList,activity);
                    }
                }
            }
        }

        if(payOrder != null && !signupNumbers.equals(payOrder.getSignupNumbers())){
            payOrder.setSignupNumbers(signupNumbers);
            super.getSession().update(payOrder);
        }
        vo.setPayOrder(payOrder);
        vo.setSignupInfoList(signupInfoList);
        return vo;
    }

    @Override
    public PayOrder createPayOrderBySignup(List<SignupInfo> signupList,Activity activity)  throws MessageException{
        PayOrder payOrder = new PayOrder();
        String orderNo = Tools.getDocumentNo(DocumentNoEnum.OrderNo);
        payOrder.setOrderNo(orderNo);//生成订单号
        payOrder.setStatus(CommonConstants.orderStatus.INIT);//状态为初始状态
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createdTime = sdf.format(new Date());
        payOrder.setCreatedTime(createdTime);
        payOrder.setDescription("报名信息支付");
        payOrder.setMoney(signupList.size()*activity.getMoney());
        payOrder.setExpireTime("");
        payOrder.setPayTime("");
        String signupNumbers = "";

        //把订单号保存到报名信息里面
        for(SignupInfo signupInfo : signupList){
            signupNumbers += "," + signupInfo.getNumber();
            signupInfo.setOrderNo(orderNo);
            super.getSession().update(signupInfo);
        }
        signupNumbers = signupNumbers.substring(1);
        payOrder.setSignupNumbers(signupNumbers);
        payOrder = super.save(payOrder);
        return payOrder;
    }

    @Override
    public String callPayInterface(String openid, PayOrder payOrder) throws Exception{
        if(payOrder == null){
            logger.error("生成订单发生异常");
            System.out.println("生成订单发生异常");
            throw new MessageException().setErrorMsg("后台出小差了，请稍后再试").setErrorCode(CommonConstants.errorCode.VALIDATE_FAIL);
        }
        //商户订单号
        String out_trade_no= payOrder.getOrderNo();
        String finalmoney = String.valueOf((int)(payOrder.getMoney()*100));
        //获取用户的code

        //2.根据code获取微信用户的openId和access_token
        //注： 如果后台程序之前已经得到了用户的openId 可以不需要这一步，直接从存放openId的位置或session中获取就可以。
        //toPay.jsp页面中提交的url路径也就不需要再经过微信重定向。写成：http://localhost:8080/项目名/wechat/pay?money=${sumPrice}&state=${orderId}

        //3.生成预支付订单需要的的package数据
        //随机数
        String nonce_str= MD5.MD5Encode(String.valueOf(new Random().nextInt(10000)),"utf-8");
        //订单生成的机器 IP
        String spbill_create_ip = SessionManager.getAttribute("remoteAddr").toString();
        //交易类型 ：jsapi代表微信公众号支付
        String trade_type = "JSAPI";

        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid",  WxPayConstants.APPID);
        packageParams.put("mch_id",  WxPayConstants.MCH_ID);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", "费用");
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("total_fee", finalmoney);
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", WxPayConstants.NOTIFY_URL);
        packageParams.put("trade_type", trade_type);
        packageParams.put("openid", openid);
        packageParams.put("attach", payOrder.getSignupNumbers());//关联的报名信息


        String sign = WechatUtil.createSign(packageParams);
        System.out.println("sign="+sign);

        //5.生成需要提交给统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder 的xml数据
        String xml="<xml>"+
                "<appid>"+ WxPayConstants.APPID+"</appid>"+
                "<attach>"+ payOrder.getSignupNumbers()+"</attach>"+
                "<mch_id>"+ WxPayConstants.MCH_ID+"</mch_id>"+
                "<nonce_str>"+nonce_str+"</nonce_str>"+
                "<sign>"+sign+"</sign>"+
                "<body><![CDATA["+"费用"+"]]></body>"+
                "<out_trade_no>"+out_trade_no+"</out_trade_no>"+
                "<total_fee>"+finalmoney+"</total_fee>"+
                "<spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip>"+
                "<notify_url>"+WxPayConstants.NOTIFY_URL+"</notify_url>"+
                "<trade_type>"+trade_type+"</trade_type>"+
                "<openid>"+openid+"</openid>"+
                "</xml>";

        logger.debug(xml);
        //6.调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder 生产预支付订单
        String strResult = "";
        //设置访问路径
        HttpPost httppost = new HttpPost("https://api.mch.weixin.qq.com/pay/unifiedorder");

        StringEntity reqEntity = new StringEntity(new String (xml.getBytes("UTF-8"),"ISO8859-1"));//这个处理是为了防止传中文的时候出现签名错误
        httppost.setEntity(reqEntity);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        logger.info("调用微信支付接口，orderNo={},请求参数xml={}",payOrder.getOrderNo(),xml);
        CloseableHttpResponse rs = httpclient.execute(httppost);
        strResult = EntityUtils.toString(rs.getEntity(), Charset.forName("utf-8"));
        System.out.println("统一下单返回xml：" + strResult);
        logger.info("统一下单返回xml：" + strResult);
        return strResult;
    }

    @Override
    public PayOrder getPayOrderByNo(String orderNo) {
        List<PayOrder> list = super.loadByProperty("orderNo",orderNo);
        if(!CollectionUtil.isEmptyCollection(list)){
            return list.get(0);
        }
        return null;
    }

    @Override
    public void updateOrderStatus(String orderNo, boolean isSuccess) {
        //成功支付时的逻辑
        int status = CommonConstants.orderStatus.INIT;//状态为未支付
        if(isSuccess){
            status = CommonConstants.orderStatus.PAY;//状态置为已支付
            //支付成功时
            //获取该订单下的一条报名记录信息以及报名成功后的身份识别信息
            String getOpenidSql = "select si.activity_Id activityId,si.openid openid,io.identify_no identifyNo" +
                    " from signup_info si left join " +
                    "   identify_order io on io.activity_id=si.activity_id and io.openid=si.openid" +
                    " where 1=1 and si.order_No=:orderNo and si.status=:status limit 1";
            List<Map<String,Object>> list = getSession().createSQLQuery(getOpenidSql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).setString("orderNo",orderNo)
                                    .setInteger("status",CommonConstants.signupStatus.PAYING).list();

            System.out.println("查询结束:" + list.size());
            if(!CollectionUtil.isEmptyCollection(list)){
                Map<String,Object> result = list.get(0);
                //如果身份识别编码为空，则新增一条记录
                Object identifyNo = result.get("identifyNo");
                System.out.println("identifyNo:" + identifyNo);
                if(identifyNo == null || StringUtil.isEmptyString(identifyNo.toString())|| "null".equals(identifyNo.toString())){
                    System.out.println("开始:" + identifyNo);
                    String activityId = result.get("activityId").toString();
                    String openid = result.get("openid").toString();
                    String insertSql = "insert into identify_order(openid,activity_id,identify_no) values(:openid,:activityId,:identifyNo)";
                    System.out.println(insertSql);
                    super.getSession().createSQLQuery(insertSql).setString("openid",openid).setString("activityId",activityId)
                            .setString("identifyNo",Tools.getDocumentNo(DocumentNoEnum.IdentifyNo)).executeUpdate();
                    /*IdentifyOrder identifyOrder = new IdentifyOrder();
                    identifyOrder.setActivityId(Long.parseLong(activityId));
                    identifyOrder.setOpenid(openid);
                    identifyOrder.setIdentifyNo(Tools.getDocumentNo(DocumentNoEnum.IdentifyNo));
                    super.getSession().save(identifyOrder);*/
                    System.out.println("结束:" + identifyNo);
                }
            }
        }
        //修改当前订单绑定的正在支付报名信息的状态
        System.out.println("更新状态:success=" + status);
        String sql = "update signup_info set status=:status where order_no=:orderNo and status=:oldStatus";
        super.getSession().createSQLQuery(sql).setString("orderNo",orderNo).setInteger("status",status)
                .setInteger("oldStatus",CommonConstants.signupStatus.PAYING).executeUpdate();
        //修改当前订单状态
        sql = "update pay_order set status=:status where order_no=:orderNo and status=:oldStatus";
        super.getSession().createSQLQuery(sql).setString("orderNo",orderNo).setInteger("status",status)
                .setInteger("oldStatus",CommonConstants.signupStatus.PAYING).executeUpdate();
        System.out.println("更新状态end:success=" + status);
    }

    @Override
    public void cancelOrder(String orderNo) {
        //修改当前订单绑定的正在支付报名信息的状态
        String sql = "update signup_info set status=:status where order_no=:orderNo and status=:oldStatus";
        super.getSession().createSQLQuery(sql).setString("orderNo",orderNo).setInteger("status",CommonConstants.signupStatus.INIT)
                .setInteger("oldStatus",CommonConstants.signupStatus.PAYING).executeUpdate();
        //修改当前订单状态
        sql = "update pay_order set status=:status where order_no=:orderNo and status=:oldStatus";
        super.getSession().createSQLQuery(sql).setString("orderNo",orderNo).setInteger("status",CommonConstants.orderStatus.INIT)
                .setInteger("oldStatus",CommonConstants.signupStatus.PAYING).executeUpdate();
    }

}
