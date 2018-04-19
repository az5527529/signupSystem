package com.Constant;

/**
 * Created by victor on 2018/3/29.
 */
public class WxPayConstants {
    //第三方用户唯一ID
    public static String APPID = "wx2d1ca99d8f706c8a";
    //第三方用户唯一凭证密码
    public static String APP_SECRET = "";
    //商户ID
    public static String MCH_ID = "1233202502";
    //微信商户平台-账户设置-安全设置-api安全,配置32位key
    public static String KEY  = "yjrbadministratoryj0662328091632";
    //交易类型
    public static String TRADE_TYPE_JS = "JSAPI";
    //微信支付回调url
//    public static String NOTIFY_URL = "http://mo.yjrb.com.cn/activityManage/payOrder/weixinNotify.action";
    public static String NOTIFY_URL = "http://mo.yjrb.com.cn/testSystem/payOrder/weixinNotify.action";
}
