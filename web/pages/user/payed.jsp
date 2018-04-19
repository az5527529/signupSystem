<%@ page import="com.service.user.IdentifyOrderService" %>
<%@ page import="com.entity.IdentifyOrder" %>
<%@ page import="com.entity.Activity" %>
<%@ page import="com.util.StringUtil" %>
<%@ page import="com.util.WechatUtil" %>
<%@ page import="com.service.user.ActivityService" %>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@include file="/pages/common/head.jsp"%>
<%
    String code = request.getParameter("code");
    if("null".equals(code) || code == null){
        code = "";
    }
    Activity headActivity = null;
    String openid = SessionManager.getAttribute("openid") == null ? "" : SessionManager.getAttribute("openid").toString();
    String activityId = request.getParameter("activityId");
    //未进行微信认证
    if(StringUtil.isEmptyString(code) && StringUtil.isEmptyString(openid)){
        response.sendRedirect(request.getContextPath() + "/pages/user/weixin.jsp?url=" + request.getContextPath() + "/pages/user/payed.jsp?activityId=" + activityId);
        return;
    }
    //获取微信id\
    if(!StringUtil.isEmptyString(code)&&StringUtil.isEmptyString(openid)){
        WechatUtil.getWechatVo(code);
    }

    if(!StringUtil.isEmptyString(activityId)){
        SessionManager.setAttribute("activityId",activityId);
        WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        ActivityService service = (ActivityService)wc.getBean("activityService");
        headActivity = service.loadById(Long.parseLong(activityId));
        SessionManager.setAttribute("activity",headActivity);
    }else{
        if(SessionManager.getAttribute("activityId") == null ){
            response.sendRedirect(request.getContextPath() + "/pages/common/error.jsp");
            return;
        }
    }
%>
<c:set value="${activity}" var="headActivity" />
<script type="text/javascript">
    var code = "<%=code%>";
    var activityId= "<%=activityId%>";
</script>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/user/common.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/user/toPay.css">
</head>

<body>
<div id="container" class="container">
    <div class="header">
        <%@include file="/pages/user/header.jsp"%>
    </div>
    <div class="content">
        <div id="confirmDiv" style="display: none;">
            <p style="font-size: 1rem;text-align: center">您的物资领取号为:&nbsp;<span id="identifyNo"></span></p>
            <div id="btnDiv"><a class="greenBtn" onclick="confirmReceive()" id="confirmBtn">确认领取物资</a></div>
        </div>
        <div id="infoDiv">

        </div>

    </div>
    <div class="footer">
        <%@include file="/pages/user/footer.jsp"%>
    </div>
</div>

</body>
<%
//    WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
    IdentifyOrderService identifyOrderService = (IdentifyOrderService)wc.getBean("identifyOrderService");
    IdentifyOrder identifyOrder = identifyOrderService.loadIdentifyOrder();
    String identifyNo = "";
    if(identifyOrder != null){
        identifyNo = identifyOrder.getIdentifyNo();
}
%>
<script type="text/javascript">
    var identifyNo = "<%=identifyNo%>";
    /*if(identifyNo){
        $("#identifyNo").html(identifyNo);
        $("#confirmDiv").show();
    }*/
	$("#payedBtn").css("color","green");
</script>
<script type="text/javascript" src="${ctx}/js/user/payed.js">
</script>
</html>