package com.entity;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by victor on 2018/4/2.
 */
@Entity
@Table(name = "signup_info", schema = "activitymanage", catalog = "")
public class SignupInfo implements Serializable{
    private Long signupInfoId;
    private String name;
    private String telephone;
    private String idCard;
    private Integer sex;
    private Long activityId;
    private String openid;
    private Integer status;
    private String number;
    private String signupTime;
    private String orderNo;
    private Boolean isTakeMaterial;

    @Id
    @Column(name = "signup_info_id", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    public Long getSignupInfoId() {
        return signupInfoId;
    }

    public void setSignupInfoId(Long signupInfoId) {
        this.signupInfoId = signupInfoId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "telephone", nullable = false, length = 32)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "id_card", nullable = false, length = 32)
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Basic
    @Column(name = "sex", nullable = false)
    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "activity_id", nullable = false)
    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    @Basic
    @Column(name = "openid", nullable = false, length = 64)
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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
    @Column(name = "number", nullable = false, length = 32)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "signup_time", nullable = false, length = 20)
    public String getSignupTime() {
        return signupTime;
    }

    public void setSignupTime(String signupTime) {
        this.signupTime = signupTime;
    }

    @Basic
    @Column(name = "order_no", nullable = false, length = 32)
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SignupInfo that = (SignupInfo) o;

        if (signupInfoId != null ? !signupInfoId.equals(that.signupInfoId) : that.signupInfoId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (idCard != null ? !idCard.equals(that.idCard) : that.idCard != null) return false;
        if (sex != null ? !sex.equals(that.sex) : that.sex != null) return false;
        if (activityId != null ? !activityId.equals(that.activityId) : that.activityId != null) return false;
        if (openid != null ? !openid.equals(that.openid) : that.openid != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (signupTime != null ? !signupTime.equals(that.signupTime) : that.signupTime != null) return false;
        if (orderNo != null ? !orderNo.equals(that.orderNo) : that.orderNo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = signupInfoId != null ? signupInfoId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (idCard != null ? idCard.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (activityId != null ? activityId.hashCode() : 0);
        result = 31 * result + (openid != null ? openid.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (signupTime != null ? signupTime.hashCode() : 0);
        result = 31 * result + (orderNo != null ? orderNo.hashCode() : 0);
        return result;
    }


    @Basic
    @Column(name = "is_take_material", nullable = false)
    public Boolean getIsTakeMaterial() {
        return isTakeMaterial;
    }

    public void setIsTakeMaterial(Boolean takeMaterial) {
        isTakeMaterial = takeMaterial;
    }
}
