<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="pages/common/head.jsp"%>
<html>

<script type="text/javascript">
	
	$(function(){
		$("a[title]").click(function(){
			var text = $(this).text();
			var href = $(this).attr("title");
			//1.判断是否已打开相应 的tab,有则选中，无责新建
			/* if($("#tt").tabs("exists",text)){
				$("#tt").tabs("select",text);
			}else{
				$("#tt").tabs("add",{
					title:text,
					content:'<iframe src="'+href+'" frameborder="0" width="100%" height="100%" />',
					//href:默认是通过url加载body内容，不加载head
					//href:href,
					closable:true
				});
			} */
			addTab(text,href);
		});
	});
	function logout(){
		$.ajax({
			type : "post",
			url : "${ctx}/login/logout.action?ids=" + Math.random(),
			success : function(data) {
				window.location = 'login.jsp';;
			},
			async : true
		});
	}
</script>
<style type="text/css">
#menu {
	width: 200px;
}

#menu ul {
	list-style: none;
	padding: 0;
	margin: 0;
	
}

#menu ul li {
	background-color: #008792;
	padding-left:20px;
	float:none;
}
#menu ul li:hover{
	background-color: #00a6ac;
}
#menu ul li a {
	display: block;
	color: #fff;
	padding: 5px;
	text-decoration: none;
}

#menu ul li a:hover {
	cursor: pointer;
}
</style>
<body class="easyui-layout">
	<div data-options="region:'north',title:'欢迎来到人口信息管理系统',split:true"
		style="height: 100px;">
		<b>欢迎您，${userInfo.userName}</b><br />登录时间:<%=new java.text.SimpleDateFormat("yyyy年MM月dd日 HH:MM").format(new java.util.Date()) %><br />
		<input type="button" id="logout" value="退出" onclick="logout()">
		</div>
	<div data-options="region:'west',title:'系统菜单',split:true"
		style="width: 200px;">
		<div id="menu" class="easyui-accordion" data-options="fit:true">

			<div title="题目管理">
				<ul>
					<li><a href="#" title="${ctx}/pages/system/activityManage.action">活动管理</a></li>
				</ul>
			</div>
		</div>

		</div>
	</div>
	<div data-options="region:'center',title:'操作页面'"
		style="padding: 1px; background: #fff;">
		<div id="tt" class="easyui-tabs" data-options="fit:true">
			<div title="系统首页" style="padding: 5px;">系统首页</div>
		</div>
	</div>
</body>

</html>