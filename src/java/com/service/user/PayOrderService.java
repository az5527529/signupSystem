package com.service.user;

import com.entity.Activity;
import com.entity.PayOrder;
import com.entity.SignupInfo;
import com.exception.MessageException;
import com.service.common.BaseService;
import com.vo.JsPayVo;
import com.vo.OrderVo;

import java.util.List;

/**
 * Created by victor on 2018/4/4.
 */
public interface PayOrderService extends BaseService<PayOrder> {
    /**
     * 发起支付接口
     * @return
     */
    public JsPayVo toPay() throws MessageException;

    /**
     * 通过微信号跟活动获取订单以及报名信息
     * @param openid
     * @param activity
     * @return
     */
    public OrderVo getOrderVoByActivityAndOpenid(String openid, Activity activity) throws MessageException;

    /**
     * 根据报名信息生成新订单
     * @param signupList
     * @return
     */
    public PayOrder createPayOrderBySignup(List<SignupInfo> signupList,Activity activity) throws MessageException;

    public String callPayInterface(String openid,PayOrder payOrder) throws Exception;

    /**
     * 根据订单号获取订单
     * @param orderNo
     * @return
     */
    public PayOrder getPayOrderByNo(String orderNo);

    /**
     * 根据微信支付返回通知来修改订单状态
     * @param orderNo
     * @param isSuccess
     */
    public void updateOrderStatus(String orderNo,boolean isSuccess);

    /**
     * 取消支付
     * @param orderNo
     */
    public void cancelOrder(String orderNo);

}
