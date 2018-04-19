package com.service.common;

import com.Constant.DocumentNoEnum;
import com.entity.NoRobot;

import java.util.List;

/**
 * Created by victor on 2018/4/3.
 */
public interface NoRobotService extends BaseService<NoRobot> {
    /**
     * 根据单据类型获取单据号
     * @param noType 单据类型
     * @param length 获取单据个数
     * @return
     */
    public String getCurrentNo(DocumentNoEnum noType, int length);
    /**
     * 根据单据号前缀和位数获取单号
     * @param prefix 单据前缀
     * @param bit 位数
     * @param length 获取单据个数
     * @return
     */
    public List<String> getCurrentNos(String prefix, int bit, int length);
}
