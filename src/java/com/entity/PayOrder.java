package com.entity;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by victor on 2018/4/2.
 */
@Entity
@Table(name = "pay_order", schema = "activitymanage", catalog = "")
public class PayOrder implements Serializable{
    private Long payOrderId;
    private String orderNo;
    private Integer status;
    private String createdTime;
    private String payTime;
    private String description;
    private String expireTime;
    private String signupNumbers;
    private Double money;

    @Id
    @Column(name = "pay_order_id", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    public Long getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(Long payOrderId) {
        this.payOrderId = payOrderId;
    }

    @Basic
    @Column(name = "order_no", nullable = false, length = 20)
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "created_time", nullable = false, length = 20)
    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    @Basic
    @Column(name = "pay_time", nullable = false, length = 20)
    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 64)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "expire_time", nullable = false, length = 20)
    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    @Basic
    @Column(name = "signup_numbers", nullable = false, length = 64)
    public String getSignupNumbers() {
        return signupNumbers;
    }

    public void setSignupNumbers(String signupNumbers) {
        this.signupNumbers = signupNumbers;
    }

    @Basic
    @Column(name = "money", nullable = false, precision = 0)
    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PayOrder payOrder = (PayOrder) o;

        if (payOrderId != null ? !payOrderId.equals(payOrder.payOrderId) : payOrder.payOrderId != null) return false;
        if (orderNo != null ? !orderNo.equals(payOrder.orderNo) : payOrder.orderNo != null) return false;
        if (status != null ? !status.equals(payOrder.status) : payOrder.status != null) return false;
        if (createdTime != null ? !createdTime.equals(payOrder.createdTime) : payOrder.createdTime != null)
            return false;
        if (payTime != null ? !payTime.equals(payOrder.payTime) : payOrder.payTime != null) return false;
        if (description != null ? !description.equals(payOrder.description) : payOrder.description != null)
            return false;
        if (expireTime != null ? !expireTime.equals(payOrder.expireTime) : payOrder.expireTime != null) return false;
        if (signupNumbers != null ? !signupNumbers.equals(payOrder.signupNumbers) : payOrder.signupNumbers != null) return false;
        if (money != null ? !money.equals(payOrder.money) : payOrder.money != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = payOrderId != null ? payOrderId.hashCode() : 0;
        result = 31 * result + (orderNo != null ? orderNo.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createdTime != null ? createdTime.hashCode() : 0);
        result = 31 * result + (payTime != null ? payTime.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (expireTime != null ? expireTime.hashCode() : 0);
        result = 31 * result + (signupNumbers != null ? signupNumbers.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        return result;
    }
}
