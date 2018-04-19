<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@include file="/pages/common/head.jsp"%>
<%@include file="/pages/user/filter.jsp"%>
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
    <div class="content" style="max-height: 60%">
        <div id="infoDiv">

        </div>
        <div id="btnDiv">
            <a class="greenBtn" onclick="pay()" id="confirmBtn">付款</a>
        </div>

    </div>
    <div class="footer">
        <%@include file="/pages/user/footer.jsp"%>
    </div>
</div>

</body>
<script type="text/javascript" src="${ctx}/js/user/toPay.js">
</script>
</html>