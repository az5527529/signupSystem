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
            {field:'aa',title:'网站地址',width:'200',align:'center',formatter:webFormatter}
        ]]
    });


});

function webFormatter(value,rowData,index) {
    return ctx + "/pages/user/index.jsp?activityId=" + rowData.activityId;
}




//查询方法
function searchActivity(){
    $('#activity').datagrid("options").queryParams={
        "startTimeBegin": $("#startTimeBegin").datebox('getValue'),
        "startTimeEnd": $("#startTimeEnd").datebox('getValue'),
        "endTimeBegin": $("#endTimeBegin").datebox('getValue'),
        "endTimeEnd": $("#endTimeEnd").datebox('getValue'),
        "topic": $("#topic").val()

    };
    // $('#activity').datagrid("options").url=ctx+"/choiceactivity/searchactivity.action?ids=" + Math.random();
    $('#activity').datagrid("load");
}

var url = ctx + "/activity/saveOrUpdate.action";;
//新增方法
function newActivity(){
    addTab("新增",ctx+"/pages/system/addActivity.action")
}

function editActivity(){
    var row = $("#activity").datagrid("getSelected");
    if (row) {
        addTab("活动编辑",ctx+"/pages/system/addActivity.action?id=" + row.activityId)
    }
}



function deleteActivity(){
    var row = $('#activity').datagrid('getSelected');
    if (row) {
        $.messager.confirm('Confirm', '您确定要删除该活动吗?', function (r) {
            if (r) {
                $.post(ctx + '/activity/deleteById.action', { id: row.activityId }, function (result) {
                    if (result.success) {
                        newShow("删除成功");
                        $('#activity').datagrid('reload');    // reload the user data
                    } else {
                        newAlert(result.errorMsg);
                    }
                }, 'json');
            }
        });
    }
}
