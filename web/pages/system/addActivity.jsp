<%--
  Created by IntelliJ IDEA.
  User: victor
  Date: 2018/2/28
  Time: 9:49
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@include file="/pages/common/head.jsp"%>
<%
    String id = request.getParameter("id");
%>
<html>
<head>
    <title>活动管理</title>

    <link rel="stylesheet" href="${ctx}/css/system/activityManage.css" type="text/css">
    <link rel="stylesheet" href=${ctx}/kindeditor-4.1.10/themes/default/default.css" />
    <script src="${ctx}/kindeditor-4.1.10/kindeditor.js"></script>
    <script src="${ctx}/kindeditor-4.1.10/kindeditor-all.js"></script>
    <script src="${ctx}//kindeditor-4.1.10/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${ctx}/kindeditor-4.1.10/kindeditor-min.js"></script>
    <script charset="utf-8" src="${ctx}/kindeditor-4.1.10/lang/zh_CN.js"></script>
</head>
<script src="${ctx}/js/system/addActivity.js" type="text/javascript"></script>
<body>

<div>
    <form id="fm" method="post" action="${ctx}/activity/saveOrUpdate.action" enctype="multipart/form-data">
        <ul>
            <li class="labelLi"><label>活动名称<span class="redStar">*</span>:</label></li>
            <li class="inputLi"><input name="topic" class="easyui-validatebox" required="true" /></li>
        </ul>

        <ul>
            <li class="labelLi"><label>开始时间<span class="redStar">*</span>:</label></li>
            <li class="inputLi"><input name="startTime"  id="startTime" class="easyui-datetimebox" data-options="editable:false" required="true"></input></li>
        </ul>
        <ul>
            <li class="labelLi"><label>结束时间<span class="redStar">*</span>:</label></li>
            <li class="inputLi"><input name="endTime"  id="endTime" class="easyui-datetimebox" data-options="editable:false" required="true"></input></li>
        </ul>
        <ul>
            <li class="labelLi"><label>活动费用<span class="redStar">*</span>:</label></li>
            <li class="inputLi"><input name="money" class="easyui-numberbox" data-options="min:0,precision:2" required="true" /></li>
        </ul>
        <ul style="margin-top: 0rem;width: 100%">
            <li><label>活动声明<span class="redStar">*</span>:</label></li>
            <li>
                <textarea rows="5" style="width:40rem;" id="content" name="content" class="easyui-validatebox" data-options="required:true,validType:'length[1,1024]'" invalidMessage="最大长度不能超过256"></textarea>
                <%--<input name="content" class="easyui-textbox" style="height:180px" data-options="multiline:true" required="true" />--%>
            </li>
        </ul>
        <ul>
            <li class="labelLi"><label>主题图片<span class="redStar">*</span>:</label></li>
            <li  class="inputLi"><input type="file" id="uploadFile"  name="uploadFile" onchange="preview(this)">
        </ul>
        <ul>
            <li style="height: auto;">
                <div class="imageDiv">
                    <img id="backgroundImg" src="">
                </div>

            </li>
        </ul>
        <input type="hidden" name="action" id="hidtype" />
        <input type="hidden" name="backgroundUrl" id="backgroundUrl" />
        <input type="hidden" name="activityId" id="activityId" />
    </form>
</div>
<div id="dlg-buttons" style="margin-top: -5rem;">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveActivity()" iconcls="icon-save">保存</a>
</div>

</body>
<script>
    var editor;
    $(function() {
        editor = KindEditor.create('textarea[name="content"]',{resizeType : 1,width:"100%",height:"200px",afterChange:function(){
            this.sync();
        },afterBlur:function(){
            this.sync();
        }});
        var id = "<%=id%>"
        if(id && id!="null"){
            $.ajax({
                type : "post",
                url : "${ctx}/activity/loadById.action?id=" + id,
                success : function(data) {
                    data = JSON.parse(data);
                    $("#fm").form("load", data);
                    $("#uploadFile").val("");
                    KindEditor.html('#content',data.content);
                    $("#backgroundImg").attr("src",ctx + "/image/getImgByUrl.action?imgUrl=" +data.backgroundUrl);
                },
                async : true
            });

        }

    });
</script>
</html>
