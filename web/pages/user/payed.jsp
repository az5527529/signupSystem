<%@ page import="com.service.user.IdentifyOrderService" %>
<%@ page import="com.entity.IdentifyOrder" %>

<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@include file="/pages/common/head.jsp"%>

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
    var ungetNum = "<%=ungetNum%>";
    //未领取物资数目
    /*if(ungetNum == 0){
        $("#confirmDiv").hide();
    }*/
	$("#payedBtn").css("color","green");
</script>
<script type="text/javascript" src="${ctx}/js/user/payed.js">
</script>
</html>