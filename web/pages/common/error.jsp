

<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@include file="/pages/common/head.jsp"%>

<html>
<head>
    <title>错误提示页面</title>
</head>
<body>
<span id="errorMsg"></span>
</body>
<script type="text/javascript">
    var errorMsg = getQueryString("errorMsg");
    $("#errorMsg").text(errorMsg?errorMsg:"页面错误");
</script>
</html>
