<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="pages/common/head.jsp"%>
<html>

<link rel="stylesheet" href="${ctx}/css/common/login.css"
	type="text/css">
<script type="text/javascript">
	function login() {
		if(!$("#login").form("validate")){
			return false;
		};
		$.ajax({
			type : "post",
			dataType : 'json',
			url : "${ctx}/login/login.action?ids=" + Math.random(),
			data : {
				userName : $("#userName").val(),
				password : $("#password").val(),
				checkCode:$("#checkCode").val()
			},
			success : function(data) {
				var errorMsg = data['errorMsg'];
				if (errorMsg != "") {
					showErrorMsg(errorMsg);
					refreshImg(document.getElementById("random"));
					$("#checkCode").val('');
					$("#random").click();
					return;
				}
				window.location = 'index.jsp';
			},
			async : true
		});
	}
	function showErrorMsg(errorMsg){
		$("#notice").html(errorMsg);
	}
	function refreshImg(img) {
		var src = img.src;
		if (src.indexOf("?") < 0) {
			src += "?"+Math.random();
		} else {
			src = src.substring(0, src.indexOf("?"))+"?"+Math.random();
		}
		img.src = src;
	}
</script>
<body>
	<div id="title">活动管理系统</div>
	<div id="form">
	<form id="login" method="post">
			<ul>
				<li style="padding-left:0px">
				<label for="userName">用户名:</label> <input class="easyui-validatebox"
				type="text" name="userName" id="userName" data-options="required:true,missingMessage:'请输入您的用户名'" />
				</li>
			
				<li style="padding-left:15px;padding-top:10px">
				<label for="password">密码:</label> <input class="easyui-validatebox"
				type="password" name="password" id="password" data-options="required:true,missingMessage:'请输入您的密码'" />
				</li>
	
				<li style="padding-left:0px;padding-top:10px">
				<label for="checkCode">验证码:</label> <input class="easyui-validatebox"
				type="text" id="checkCode" name="checkCode" data-options="required:true,missingMessage:'请输入验证码'" />
				</li>
				
				<li style="padding-left:0px;padding-top:10px">
				<img src="${ctx}/randomCodeServlet" border="0" id="random" onclick="refreshImg(this);"
				style="cursor:pointer;vertical-align:middle;" />
				</li>
				
				<li><span id="notice"></span></li>
			</ul>		
	</form>
	</div>
	<div id="btn">
		<input type="button" id="submit" value="登陆" onclick="login()">
		<input type="button" id="reset" value="重置">
	</div>
</body>
</html>