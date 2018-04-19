package com.controller.user;

import com.Constant.CommonConstants;
import com.Constant.WxPayConstants;
import com.entity.PayOrder;
import com.exception.MessageException;
import com.service.user.PayOrderService;
import com.util.*;
import com.vo.JsPayVo;
import com.vo.WechatVo;
import net.sf.json.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by victor on 2018/3/29.
 */
@Controller
@RequestMapping("/payOrder")
public class PayOrderController {
    Logger logger = LoggerFactory.getLogger(PayOrderController.class);
    @Resource
    private PayOrderService payOrderService;
    @RequestMapping(value = "/toPay")
    public void toPay(HttpServletRequest request,
                              HttpServletResponse response) throws IOException {
        JSONObject result = new JSONObject();
        try {
            JsPayVo vo = this.payOrderService.toPay();
            result = JSONObject.fromObject(vo);
            result.put("success",1);
            result.put("orderNo",vo.getOrderNo());
        } catch (MessageException e) {
            e.printStackTrace();
            result.put("success",0);
            result.put("errorMsg",e.getErrorMsg());
        } catch (Exception e1){
            e1.printStackTrace();
            result.put("success",0);
            result.put("errorMsg","后台出小差了，请稍后再试");
        }

        WebUtil.outputPage(request, response, result.toString());
    }

    /**
     * 提交支付后的微信异步返回接口
     */
    @RequestMapping(value="/weixinNotify")
    public void weixinNotify(HttpServletRequest request, HttpServletResponse response){
        String out_trade_no = "";
        String return_code;
        boolean isSuccess = false;
        String returnCode = "FAIL";
        String returnMsg = "";
        try {
            InputStream inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            String notifyXml  = new String(outSteam.toByteArray(),"utf-8");
            System.out.println("接收到的xml：" + notifyXml);
            logger.debug("收到微信异步回调：");
            logger.debug(notifyXml);
            if(StringUtil.isEmptyString(notifyXml)){
                logger.debug("xml为空：");
            }

            out_trade_no = WechatUtil.getXmlPara(notifyXml,"out_trade_no");
            String result_code = WechatUtil.getXmlPara(notifyXml,"result_code");
            return_code = WechatUtil.getXmlPara(notifyXml,"return_code");
            String sign = WechatUtil.getXmlPara(notifyXml,"sign");
            String total_fee = WechatUtil.getXmlPara(notifyXml,"total_fee");


            //根据返回xml计算本地签名
            SortedMap<String, String> localMap = Tools.getResult(notifyXml);
            localMap.remove("sign");
            String localSign = WechatUtil.createSign(localMap);//将数据MD5加密

            logger.debug("本地签名是：" + localSign);
            logger.debug("微信支付签名是：" + sign);

            System.out.println("本地签名是：" + localSign);
            System.out.println("微信支付签名是：" + sign);
            System.out.println("订单号为：" + out_trade_no);
            request.setAttribute("out_trade_no", out_trade_no);



            //本地计算签名与微信返回签名不同||返回结果为不成功
            if(!localSign.equals(sign) || !"SUCCESS".equals(result_code) || !"SUCCESS".equals(return_code)){
                System.out.println("验证签名失败或返回错误结果码");
                logger.error("验证签名失败或返回错误结果码");
                returnMsg = "验证签名失败或返回错误结果码";

            }else{
                logger.info("订单号为:{}",out_trade_no);
                if(StringUtil.isEmptyString(out_trade_no)){
                    logger.error("返回的订单号为空");
                    returnMsg = "返回的订单号为空";
                }else{
                    PayOrder payOrder = this.payOrderService.getPayOrderByNo(out_trade_no);
                    if(payOrder == null){
                        logger.error("商户系统中不存在该订单");
                        returnMsg = "商户系统中不存在该订单";
                    }else {
                        //如果订单不是支付中，则证明订单已处理，返回成功，
                        if(!payOrder.getStatus().equals(CommonConstants.orderStatus.PAYING)){
                            System.out.println("订单已处理");
                            returnMsg = "订单已处理";
                            returnCode = "SUCCESS";
                        }else{
                            Double money = payOrder.getMoney();
                            Integer totalFee = Integer.parseInt(total_fee);//微信返回的订单金额
                            if((int)(money * 100) != totalFee.intValue()){
                                logger.error("金额不一致");
                                returnMsg = "金额不一致";
                            }else{
                                isSuccess = true;
                                System.out.println("支付成功");
                                returnMsg = "支付成功";
                                returnCode = "SUCCESS";
                                logger.debug("公众号支付成功，out_trade_no(订单号)为：" + out_trade_no);
                            }
                        }

                    }
                }
            }
            System.out.println("return_code=" + returnCode + " returnMsg=" + returnMsg);
            response.getWriter().write("<xml>" + "<return_code><![CDATA["+returnCode+"]]></return_code>" + "<return_msg><![CDATA["+returnMsg+"]]></return_msg>" + "</xml> ");
        }  catch (Exception e) {
            logger.error("微信回调接口出现错误：",e);
            try {
                response.getWriter().write("<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[error]]></return_msg>" + "</xml> ");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        System.out.println("isSuccess:"+isSuccess);
        if(isSuccess){
            //支付成功的业务逻辑
            this.payOrderService.updateOrderStatus(out_trade_no,true);
        }else if(!"".equals(out_trade_no) && "FAIL".equals(returnCode)){
            //支付失败的业务逻辑
            this.payOrderService.updateOrderStatus(out_trade_no,false);
        }
    }

    @RequestMapping(value = "/cancelOrder")
    public void cancelOrder(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        String orderNo = request.getParameter("orderNo");//
        logger.info("用户取消支付，orderNo={}",orderNo);
        this.payOrderService.cancelOrder(orderNo);
        JSONObject result = new JSONObject();
        result.put("success",1);

        WebUtil.outputPage(request, response, result.toString());
    }
}
