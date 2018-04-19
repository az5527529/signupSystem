package com.service.impl.schedule;

import com.Constant.CommonConstants;
import com.Constant.WxPayConstants;
import com.entity.PayOrder;
import com.service.impl.common.BaseServiceImpl;
import com.service.schedule.OrderScheduleService;
import com.util.CollectionUtil;

import com.util.MD5;
import com.util.WechatUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by victor on 2018/4/18.
 */
@Service("orderScheduleService")
public class OrderScheduleServiceImpl extends BaseServiceImpl<PayOrder> implements OrderScheduleService {
    private Logger logger = LoggerFactory.getLogger(OrderScheduleServiceImpl.class);
    private static ExecutorService taskThreadPool = null;
    @Override
    public void dealAbnormalOrder() {
        logger.info("OrderScheduleServiceImpl.dealAbnormalOrder start={}",System.currentTimeMillis());
        //把执行时间在5分钟外的状态一直处于支付中的订单查询出来
        String sql = "select order_no orderNo from pay_order where 1=1 and status=:status and TIMESTAMPDIFF(MINUTE ,pay_time,now())>=5";

        List<String> list = super.getSession().createSQLQuery(sql).setInteger("status", CommonConstants.orderStatus.PAYING).list();
        for(String orderNo : list){
            this.dealOrder(orderNo);
        }

        //启动线程数量
        Integer threadCount = 3;
        /*if (taskThreadPool == null) {
            taskThreadPool = Executors.newFixedThreadPool(threadCount);
        }
        if (!taskThreadPool.isShutdown()) {
            for(final String orderNo : list){

                //把任务放到线程池里执行
                Thread thread = new Thread(){
                    public void run() {
                        try {
                            OrderScheduleService service = new OrderScheduleServiceImpl();
                            service.dealOrder(orderNo);
                        } catch (Exception e) {
                            e.printStackTrace();
                            logger.info("调用转业务方法报错，errorMsg={}",e.getMessage());
                        } finally {

                        }
                    }
                };
                taskThreadPool.execute(thread);
            }
        }*/
        logger.info("OrderScheduleServiceImpl.dealAbnormalOrder end={}",System.currentTimeMillis());
    }

    @Override
    public void dealOrder(String orderNo) {
        logger.info("处理异常订单,orderNo:{}",orderNo);
        //根据订单号加载订单
        String hql = "from PayOrder where orderNo=:orderNo";
        List<PayOrder> list = super.getSession().createQuery(hql).setString("orderNo",orderNo).list();
        if(!CollectionUtil.isEmptyCollection(list)){
            PayOrder payOrder = list.get(0);
            //状态为支付中时，需要处理
            if(payOrder.getStatus() == CommonConstants.orderStatus.PAYING){

                try {
                    //随机数
                    String nonce_str= MD5.MD5Encode(String.valueOf(new Random().nextInt(10000)),"utf-8");
                    SortedMap<String, String> packageParams = new TreeMap<String, String>();
                    packageParams.put("appid",  WxPayConstants.APPID);
                    packageParams.put("mch_id",  WxPayConstants.MCH_ID);
                    packageParams.put("nonce_str", nonce_str);
                    packageParams.put("out_trade_no", orderNo);
                    String sign = WechatUtil.createSign(packageParams);
                    String xml="<xml>"+
                            "<appid>"+ WxPayConstants.APPID+"</appid>"+
                            "<mch_id>"+ WxPayConstants.MCH_ID+"</mch_id>"+
                            "<nonce_str>"+nonce_str+"</nonce_str>"+
                            "<out_trade_no>"+orderNo+"</out_trade_no>"+
                            "<sign>"+sign+"</sign>"+
                            "</xml>";
                    logger.debug(xml);
                    //6.调用订单查询接口https://api.mch.weixin.qq.com/pay/orderquery
                    String strResult = "";
                    //设置访问路径
                    HttpPost httppost = new HttpPost("https://api.mch.weixin.qq.com/pay/orderquery");

                    StringEntity reqEntity = null;//这个处理是为了防止传中文的时候出现签名错误
                    reqEntity = new StringEntity(new String (xml.getBytes("UTF-8"),"ISO8859-1"));
                    httppost.setEntity(reqEntity);
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    logger.info("调用订单查询接口，orderNo={},请求参数xml={}",payOrder.getOrderNo(),xml);
                    CloseableHttpResponse rs = httpclient.execute(httppost);
                    strResult = EntityUtils.toString(rs.getEntity(), Charset.forName("utf-8"));
                    System.out.println("查询接口返回xml：" + strResult);
                    logger.info("查询接口返回xml：" + strResult);
                    String result_code = WechatUtil.getXmlPara(strResult,"result_code");
                    String return_code = WechatUtil.getXmlPara(strResult,"return_code");
                    boolean isSuccess = false;//是否支付成功
                    //调用成功时操作
                    if("SUCCESS".equals(result_code) && "SUCCESS".equals(return_code)){
                        //交易状态
                        String trade_state = WechatUtil.getXmlPara(strResult,"trade_state");
                        if("SUCCESS".equals(trade_state)){
                            //支付成功状态弄为成功
                            isSuccess = true;
                        }
                    }
                    int status = CommonConstants.signupStatus.PAY;//报名信息状态
                    //支付成功
                    if(isSuccess){
                        payOrder.setStatus(CommonConstants.orderStatus.PAY);
                    }else{
                        //支付失败则取消订单
                        status = CommonConstants.signupStatus.INIT;//支付失败，报名信息状态弄为初始
                        payOrder.setStatus(CommonConstants.orderStatus.EXPIRE);
                        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        payOrder.setExpireTime(currentTime);
                        payOrder.setDescription("订单支付异常，取消订单");
                    }
                    //更新报名信息状态
                    String sql = "update signup_info set status=:status where 1=1 and order_no=:orderNo and status=" + CommonConstants.signupStatus.PAYING;
                    super.getSession().createSQLQuery(sql).setInteger("status",status).setString("orderNo",orderNo).executeUpdate();
                    //更新订单状态
                    super.update(payOrder);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("处理异常，orderNo:{},errorMsg:{}",orderNo,e.getMessage());
                }

            }
        }
    }
}
