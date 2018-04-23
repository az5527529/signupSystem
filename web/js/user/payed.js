
//加载已支付的报名信息
$(function () {
    $.ajax({
        type : "post",
        dataType : 'json',
        data : {status:3},
        url : ctx+"/signupInfo/loadSignInfoByStatas.action?ids=" + Math.random(),

        success : function(result) {
            if (result.success) {
                var list = result.list;
                var infoDiv = $("#infoDiv");
                for(var i = 0;i < list.length;i++){
                    var info = list[i];
                    var signupDiv = $("<div>").addClass("signupDiv");
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

                    signUl.append($("<li style='color:red;text-align: center;'>支付成功</li>"));

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
    
});
function confirmReceive() {
    $.messager.confirm('Confirm', '您还有'+ungetNum+'件未领取，确定已领取全部物资?', function (r) {
        if (r) {
            $.ajax({
                type : "post",
                url : ctx + "/identifyOrder/confirmReceive.action",
                data : {"identifyNo":identifyNo},
                success : function(data) {
                    data = JSON.parse(data);
                    if(data.success){
                        newShow("操作成功");
                        $("#confirmDiv").hide();
                    }else{
                        newAlert(data.errorMsg);
                    }
                },
                async : true
            });
        }
    });

}
