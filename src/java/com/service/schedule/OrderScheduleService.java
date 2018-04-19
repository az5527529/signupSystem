package com.service.schedule;

import com.entity.PayOrder;
import com.service.common.BaseService;

/**
 * Created by victor on 2018/4/18.
 */
public interface OrderScheduleService extends BaseService<PayOrder>{
    /**
     * 处理异常订单
     */
    public void dealAbnormalOrder();

    /**
     * 通过订单号处理订单
     * @param orderNo
     */
    public void dealOrder(String orderNo);
}
