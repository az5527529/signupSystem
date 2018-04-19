package com.entity;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by victor on 2018/4/2.
 */
@Entity
@Table(name = "user_info", schema = "activitymanage", catalog = "")
public class UserInfo implements Serializable{
    private Long userInfoId;
    private String userName;
    private String password;

    @Id
    @Column(name = "user_info_id", nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    @Basic
    @Column(name = "user_name", nullable = false, length = 32)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInfo userInfo = (UserInfo) o;

        if (userInfoId != null ? !userInfoId.equals(userInfo.userInfoId) : userInfo.userInfoId != null) return false;
        if (userName != null ? !userName.equals(userInfo.userName) : userInfo.userName != null) return false;
        if (password != null ? !password.equals(userInfo.password) : userInfo.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userInfoId != null ? userInfoId.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    private String errorMsg;
    @Transient
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
