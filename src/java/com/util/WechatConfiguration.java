package com.util;

import java.util.ResourceBundle;

/**
 * Created by victor on 2018/3/9.
 */
public class WechatConfiguration {
    private static String appid;
    private static String secret;
    static{
        ResourceBundle bundle = ResourceBundle.getBundle("config");
        appid =bundle.getString("wechat.appid");
        secret =bundle.getString("wechat.secret");
    }

    public static String getAppid() {
        return appid;
    }

    public static void setAppid(String appid) {
        WechatConfiguration.appid = appid;
    }

    public static String getSecret() {
        return secret;
    }

    public static void setSecret(String secret) {
        WechatConfiguration.secret = secret;
    }
}
