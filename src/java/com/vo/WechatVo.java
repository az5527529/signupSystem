package com.vo;

/**
 * Created by victor on 2018/3/9.
 */
public class WechatVo {
    private String accessToken;
    private String openid;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String toString(){
        return "accessToken="+accessToken+",openid=" + openid;
    }
}
