package com.vo;

import com.entity.PayOrder;
import com.entity.SignupInfo;

import java.util.List;

/**
 * Created by victor on 2018/4/4.
 */
public class OrderVo {
    PayOrder payOrder;

    public PayOrder getPayOrder() {
        return payOrder;
    }

    public void setPayOrder(PayOrder payOrder) {
        this.payOrder = payOrder;
    }

    public List<SignupInfo> getSignupInfoList() {
        return signupInfoList;
    }

    public void setSignupInfoList(List<SignupInfo> signupInfoList) {
        this.signupInfoList = signupInfoList;
    }

    List<SignupInfo> signupInfoList;
}
