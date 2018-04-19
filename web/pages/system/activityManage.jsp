<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 2018/2/28
  Time: 9:49
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@include file="/pages/common/head.jsp"%>
<html>
<head>
    <title>活动管理</title>

    <link rel="stylesheet" href="${ctx}/css/system/activityManage.css" type="text/css">
</head>
<script src="${ctx}/js/system/activityManage.js" type="text/javascript"></script>
<body>
<div id="cc" class="easyui-layout" style="width: 100%; height: 460px">

    <div data-options="region:'north',border:false" id="search"
         style="padding: 5px; height: 90rem;">
        <form id="searchForm" style="margin-top: 10px">
            <ul>
                <li><label>开始日期:</label>
                    <input id="startTimeBegin" class="easyui-datebox" data-options="editable:false"></input>
                    -
                    <input id="startTimeEnd" class="easyui-datebox" data-options="editable:false"></input>

                </li>
                <li><label>结束日期:</label>
                    <input id="endTimeBegin" class="easyui-datebox" data-options="editable:false"></input>
                    -
                    <input id="endTimeEnd" class="easyui-datebox" data-options="editable:false"></input>

                </li>
            </ul>
            <ul>
                <li>
                    <label>活动名称:</label>
                    <input name="topic" id="topic" class="easyui-validatebox"  />
                </li>
            </ul>
        </form>
    </div>
    <div data-options="region:'center',split:true" style="height: 350px"
         id="list">
        <table id="activity"></table>
    </div>
</div>
<div id="btn">
    <a href="#" class="easyui-linkbutton"
       data-options="iconCls:'icon-search'" onclick="searchActivity()"
       plain="true"></a>
    <a href="#" class="easyui-linkbutton"
       iconCls="icon-add" plain="true" id="new" onclick="newActivity()"></a>
    <a href="#"
       class="easyui-linkbutton" iconCls="icon-edit" id="edit" plain="true" onclick="editActivity()"></a>
    <a
            href="#" class="easyui-linkbutton" iconCls="icon-remove" id="remove" plain="true" onclick="deleteActivity()"></a>
</div>


</body>

</html>
