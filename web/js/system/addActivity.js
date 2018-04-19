$(function(){
    $('#activity').datagrid({
        pageList: [10,20,50,100],
        pagination:true,
        rownumbers:true,
        pagePosition:"bottom",
        idField: 'activityId',
        width: ($("#list").width()),
        height:$("#list").height(),
        fitColumns:true,
        toolbar:"#btn",
        singleSelect:true,

        url:ctx+"/activity/searchActivity.action?ids=" + Math.random(),
        columns:[[
            {field:'activityId',title:'',hidden:true},
            {field:'content',title:'',hidden:true},
            {field:'backgroundUrl',title:'',hidden:true},
            {field:'topic',title:'活动名称',width:'200',align:'center'},
            {field:'startTime',title:'开始时间',width:'200',align:'center'},
            {field:'endTime',title:'结束时间',width:'200',align:'center'},
        ]]
    });


});




var url = ctx + "/activity/saveOrUpdate.action";;


//保存方法
function saveActivity(){
    if(!$("#uploadFile").val() && !$("#backgroundUrl").val()){
        newAlert("请选择需要上传的图片");
        return;
    }
    if($("#endTime").datetimebox('getValue')&&$("#startTime").datetimebox('getValue')
        && $("#endTime").datetimebox('getValue')<$("#startTime").datetimebox('getValue')){
        newAlert("结束时间不能小于开始时间");
        return;
    }
    $("#fm").form("submit", {
        url: url,
        onsubmit: function () {
            return $(this).form("validate");
        },
        success: function (result) {
            result = JSON.parse(result);
            if (result.success) {
                newShow("操作成功");
                $("#fm").form("load", result.activity);
            }
            else {
                $.messager.alert("提示信息", "操作失败");
            }
        }
    });
}


//图片预览
function preview(file){

    if (file.files && file.files[0]) {
        var max_size = 3;// 10M
        if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(file.value)) {
            newAlert("图片类型必须是[.gif,jpeg,jpg,png]中的一种");
            file.value = "";
            $("#backgroundImg").attr("src","");
            return false;
        } else {
            var fileData = file.files[0];
            var size = fileData.size;
            if (size > max_size * 1024 * 1024) {
                newAlert("图片大小不能超过" + max_size + "M");
                file.value = "";
                $("#backgroundImg").attr("src","");
            } else {
                //实例化一个FileReader
                var reader = new FileReader();
                reader.onload = function (e) {
                    //当reader加载时，把图片的内容赋值给
                    $("#backgroundImg").attr("src",e.target.result);
                };
                //读取选中的图片，并转换成dataURL格式
                reader.readAsDataURL(file.files[0]);
            }
        }

    }
}