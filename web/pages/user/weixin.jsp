<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 2018/3/9
  Time: 9:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@include file="/pages/common/head.jsp"%>
<%
    String url = request.getParameter("url");
    System.out.println(url);
%>
<html>
<head>
    <title>微信认证</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0,user-scalable=no"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style,content=black"/>
</head>
<body>
<script type="text/javascript">
    var url = "<%=url%>";
    var appid = 'wx2d1ca99d8f706c8a';//appid

    var redirect_uri = "http://mo.yjrb.com.cn"+url;
    redirect_uri = encodeURIComponent(redirect_uri);
console.log(url);	
console.log(redirect_uri);	
    var response_type = 'code';
//    var scope = 'snsapi_userinfo';//snsapi_base/snsapi_userinfo
    var scope = 'snsapi_base';
    var web = '#wechat_redirect';
    var ref = 'https://open.weixin.qq.com/connect/oauth2/authorize?';
    ref += 'appid=' + appid;
    ref += '&redirect_uri=' + redirect_uri;
    ref += '&response_type=code';
    ref += '&scope=' + scope;
    ref += web;
    location.href = ref;
</script>
</body>
</html>
