<%@ page contentType="text/html;charset=utf-8" language="java" %>

<%@include file="/pages/common/head.jsp"%>
<%
    String code = request.getParameter("code");
    if("null".equals(code) || code == null){
        code = "";
    }
%>
<script type="text/javascript">
    var code = "<%=code%>";
</script>
<html>
<head>
    <title>活动过期</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/user/common.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/user/index.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/user/expire.css">
</head>
<body>
<div id="container" class="container">
    <div class="header">
        <%@include file="/pages/user/header.jsp"%>
    </div>
    <div class="content">
        <div class="tips">
            <li>报名未开始或已经结束</li>
            <li>开始时间:${activity.startTime}</li>
            <li>结束时间:${activity.endTime}</li>
        </div>
        <div class="btn">
            <li><a class="greenBtn" onclick="javascript:history.back(-1);">返回</a>
                </li>
        </div>
        <div style="text-align: center;margin-top: 1rem">
            <a style="color: blue"  href="index.jsp">活动声明</a>
        </div>

    </div>
    <div class="footer">
        <%@include file="/pages/user/footer.jsp"%>
    </div>
</div>

</body>
<script type="text/javascript">

</script>

</html>