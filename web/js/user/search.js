
function searchSignupInfo() {
    var name = $("#name").val();
    if(!name){
       alert("名字不能为空");
        return;
    }
    $.ajax({
        type : "post",
        dataType : 'json',
        data : {name:$("#name").val(),number:$("#number").val()},
        url : ctx+"/signupInfo/searchSignupInfoFromApp.action?ids=" + Math.random(),

        success : function(result) {
            if (result.success) {
                var list = result.list;
                var infoDiv = $("#infoDiv");
                infoDiv.html("");
                for(var i = 0;i < list.length;i++){
                    var info = list[i];
                    var signupDiv = $("<div>").addClass("signupDiv").css("height","15rem");
                    var signUl = $("<ul>");
                    signupDiv.append(signUl)
                    signUl.append($("<li>&nbsp序号&nbsp;"+info.number+"</li>"));
                    signUl.append($("<li>&nbsp姓名&nbsp;"+info.name+"</li>"));
                    var idCard = info.idCard;
                    signUl.append($("<li>身份证&nbsp;"+idCard+"</li>"));
                    var sex = info.sex;
                    if(sex == 1){
                        sex = "男";
                    }else{
                        sex = "女";
                    }
                    signUl.append($("<li>&nbsp性别&nbsp;"+sex+"</li>"));
                    var telephone = info.telephone;
                    signUl.append($("<li>&nbsp电话&nbsp;"+telephone+"</li>"));

                    var resultInfo = "";
                    var status = info.status
                    if(status == 1){
                        resultInfo = "未支付";
                    }else if(status == 2){
                        resultInfo = "支付中";
                    }else if(status == 3){
                        resultInfo = "支付成功";
                    }
                    signUl.append("&nbsp;&nbsp;&nbsp;&nbsp;"+resultInfo);
                    /*var modifyBtn = $("<li><a onclick='edit("+info.signupInfoId+")'>修改</a></li>");
                    var deleteBtn = $("<li><a onclick='deleteInfo("+info.signupInfoId+")'>删除</a></li>");

                    signupDiv.append(modifyBtn).append(deleteBtn);*/

                    infoDiv.append(signupDiv);
                }
            }
            else {
                if(result.errorCode && result.errorCode=="003"){//验证失败
                    alert(result.errorMsg);
                }else{
                    window.location.href=ctx + "/pages/common/error.jsp?errorMsg=" + result.errorMsg;
                }

            }
        },
        error : function(xhr, type, msg) {
            var prompt = xhr.status + ': ' + msg;
            if (xhr.status == 500) {
                var error = jQuery.parseJSON(xhr.responseText);
                prompt = error.errorMsg;
                if (error.exceptionCode)
                    prompt = prompt + ' (' + error.exceptionCode + ')';
            } else if (xhr.status == 0) {
                prompt = "服务器无响应";
            }
            window.location.href = ctx + "/error.jsp?errorMsg="+prompt;//跳到错误页面
        },
        async : false
    });
}
function edit(signupInfoId){
    window.location.href = ctx + "/pages/user/signup.jsp?signupInfoId=" + signupInfoId;//跳转到编辑页面
}
function deleteInfo(signupInfoId){
    $.messager.confirm('Confirm', '您确定要删除该报名信息吗?', function (r) {
        if (r) {
            $.post(ctx + '/signupInfo/deleteById.action', { id: signupInfoId }, function (result) {
                if (result.success) {
                    $("#searchBtn").click();
                } else {
                    newAlert(result.errorMsg);
                }
            }, 'json');
        }
    });
}