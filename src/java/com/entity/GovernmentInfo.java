package com.entity;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by victor on 2018/3/29.
 */
@Entity
@Table(name = "government_info", schema = "person", catalog = "")
public class GovernmentInfo implements Serializable{
    private long governmentInfoId;
    private String name;
    private String telphone;
    private String briefIntroduction;
    private String duty;
    private String address;
    private String leader;

    @Id
    @Column(name = "government_info_id", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    public long getGovernmentInfoId() {
        return governmentInfoId;
    }

    public void setGovernmentInfoId(long governmentInfoId) {
        this.governmentInfoId = governmentInfoId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "telphone", nullable = false, length = 16)
    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    @Basic
    @Column(name = "brief_introduction", nullable = false, length = 64)
    public String getBriefIntroduction() {
        return briefIntroduction;
    }

    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction;
    }

    @Basic
    @Column(name = "duty", nullable = false, length = 64)
    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    @Basic
    @Column(name = "address", nullable = false, length = 128)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "leader", nullable = false, length = 32)
    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GovernmentInfo that = (GovernmentInfo) o;

        if (governmentInfoId != that.governmentInfoId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (telphone != null ? !telphone.equals(that.telphone) : that.telphone != null) return false;
        if (briefIntroduction != null ? !briefIntroduction.equals(that.briefIntroduction) : that.briefIntroduction != null)
            return false;
        if (duty != null ? !duty.equals(that.duty) : that.duty != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (leader != null ? !leader.equals(that.leader) : that.leader != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (governmentInfoId ^ (governmentInfoId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (telphone != null ? telphone.hashCode() : 0);
        result = 31 * result + (briefIntroduction != null ? briefIntroduction.hashCode() : 0);
        result = 31 * result + (duty != null ? duty.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (leader != null ? leader.hashCode() : 0);
        return result;
    }
}
