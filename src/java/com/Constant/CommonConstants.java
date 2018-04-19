package com.Constant;

/**
 * Created by victor on 2018/4/3.
 */
public class CommonConstants {
    //报名信息状态
    public class signupStatus{
        public static final int INIT = 1;//未付款
        public static final int PAYING = 2;//未付款
        public static final int PAY = 3;//已付款
    }
    //异常编码
    public class errorCode{
        public static final String ACCESS_VOLATION = "001";//非法访问
        public static final String WITHOUT_WECHAT = "002";//未进行微信认证
        public static final String VALIDATE_FAIL = "003";//验证失败
    }

    //报名信息状态
    public class orderStatus{
        public static final int INIT = 1;//未付款
        public static final int PAYING = 2;//未付款
        public static final int PAY = 3;//已付款
        public static final int EXPIRE = 4;//已失效
    }
}
