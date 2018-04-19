package com.service.user;

import com.entity.IdentifyOrder;
import com.exception.MessageException;
import com.service.common.BaseService;

/**
 * Created by victor on 2018/4/19.
 */
public interface IdentifyOrderService extends BaseService<IdentifyOrder> {
    /**
     * 获取物资领取号
     * @return
     */
    public IdentifyOrder loadIdentifyOrder();
    /**
     * 通过识别号或者报名序号来确认领取物资
     * @param identifyNo
     * @param numer
     * @return
     */
    public int confirmReceive(String identifyNo,String numer) throws MessageException;
}
