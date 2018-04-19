<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@page import="com.entity.UserInfo"%>
<%@ page import="com.util.SessionManager" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>


<%
	UserInfo userInfo = SessionManager.getUserInfo();
%>
<c:set value="${pageContext.request.contextPath}" var="ctx" />

<!-- easyui环境 -->
<link rel="stylesheet" href="${ctx}/jquery-easyui-1.4.4/themes/icon.css" type="text/css">
<link rel="stylesheet" href="${ctx}/jquery-easyui-1.4.4/themes/default/easyui.css" type="text/css">
<link rel="stylesheet" href="${ctx}/css/common/common.css" type="text/css">
<script type="text/javascript">
	var ctx="${ctx}";//定义项目路径
</script>
<script type="text/javascript" src="${ctx}/jquery-easyui-1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/common/common.js"></script>
<script  type="text/javascript">
	$.ajaxSetup({
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
		complete:function(XMLHttpRequest,textStatus){
			var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus");
			if(sessionstatus=="sessionOut"){
				alert("会话已超时,请重新登陆!");
				window.top.location.replace(ctx+"/login.jsp");
			}
		}
	});
</script>
