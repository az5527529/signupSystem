<%@ page import="com.util.StringUtil" %>
<%@ page contentType="text/html;charset=utf-8" language="java" %>

<%@include file="/pages/common/head.jsp"%>
<%@include file="/pages/user/filter.jsp"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/user/common.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/user/signup.css">
</head>
<%
    //报名信息id,如果不为空，则加载报名信息
    String signupInfoId = request.getParameter("signupInfoId");
%>
<body>
<div id="container" class="container">
    <div class="header">
        <%@include file="/pages/user/header.jsp"%>
    </div>
    <div class="content">
        <form id="fm" method="post">
            <ul>
                <li class="labelLi"><label>姓名<span class="redStar">*</span>:</label></li>
                <li class="inputLi"><input name="name" id="name" class="easyui-validatebox"  /></li>
            </ul>

            <ul>
                <li class="labelLi"><label>手机<span class="redStar">*</span>:</label></li>
                <li class="inputLi"><input name="telephone"  id="telephone" class="easyui-validatebox"  /></li>
            </ul>
            <ul>
                <li class="labelLi"><label>身份证<span class="redStar">*</span>:</label></li>
                <li class="inputLi"><input name="idCard" id="idCard" class="easyui-validatebox" /></li>
            </ul>
            <ul>
                <li class="labelLi"><label>性别<span class="redStar">*</span>:</label></li>
                <li class="inputLi">
                    <input type="radio" name="sex" value="1" checked="checked" />男
                    <input type="radio" name="sex" value="0" />女
                </li>
            </ul>
            <ul>
                <li style="width: 100%;text-align: center">
                    <a class="greenBtn" onclick="confirm()" id="confirmBtn">确认报名</a>
                </li>
            </ul>
            <input type="hidden" name="signupInfoId">
        </form>
        <div style="text-align: center;">
            <a style="color: blue"  href="index.jsp">活动声明</a>
        </div>
    </div>
    <div class="footer">
        <%@include file="/pages/user/footer.jsp"%>
    </div>
</div>

</body>
<script type="text/javascript" src="${ctx}/js/user/signup.js">

</script>
<script>
    $(function () {
        var signupInfoId = "<%=signupInfoId%>";
        if(signupInfoId && signupInfoId != "null"){
            $.post(ctx + '/signupInfo/loadById.action', { "id": signupInfoId }, function (result) {
                if (result) {
                    $("#fm").form("load", result);
                }
            }, 'json');
            $("#confirmBtn").text("确认修改");
        }
	$("#signBtn").css("color","green");
    });
</script>
</html>