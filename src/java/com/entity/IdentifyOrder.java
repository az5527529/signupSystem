package com.entity;

import javax.persistence.*;

/**
 * Created by victor on 2018/4/3.
 */
@Entity
@Table(name = "identify_order", schema = "activitymanage", catalog = "")
public class IdentifyOrder {
    private Long identifyOrderId;
    private Long activityId;
    private String openid;
    private String identifyNo;

    @Id
    @Column(name = "identify_order_id", nullable = false)
    public Long getIdentifyOrderId() {
        return identifyOrderId;
    }

    public void setIdentifyOrderId(Long identifyOrderId) {
        this.identifyOrderId = identifyOrderId;
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
    @Column(name = "identify_no", nullable = false, length = 16)
    public String getIdentifyNo() {
        return identifyNo;
    }

    public void setIdentifyNo(String identifyNo) {
        this.identifyNo = identifyNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdentifyOrder that = (IdentifyOrder) o;

        if (identifyOrderId != null ? !identifyOrderId.equals(that.identifyOrderId) : that.identifyOrderId != null)
            return false;
        if (activityId != null ? !activityId.equals(that.activityId) : that.activityId != null) return false;
        if (openid != null ? !openid.equals(that.openid) : that.openid != null) return false;
        if (identifyNo != null ? !identifyNo.equals(that.identifyNo) : that.identifyNo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = identifyOrderId != null ? identifyOrderId.hashCode() : 0;
        result = 31 * result + (activityId != null ? activityId.hashCode() : 0);
        result = 31 * result + (openid != null ? openid.hashCode() : 0);
        result = 31 * result + (identifyNo != null ? identifyNo.hashCode() : 0);
        return result;
    }
}
