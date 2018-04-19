function confirm(){
    var name = $("#name").val();
    var telephone = $("#telephone").val();
    var idCard = $("#idCard").val();
    if(!name){
        alert("姓名不能为空");
        return;
    }
    if(!telephone){
        alert("手机不能为空");
        return;
    }
    if(!idCard){
        alert("身份证不能为空");
        return;
    }
    $("#fm").form("submit", {
        url: ctx+"/signupInfo/saveOrUpdate.action",
        onsubmit: function () {
            return $(this).form("validate");
        },
        success: function (result) {
            result = JSON.parse(result);
            if (result.success) {
                window.location.href=ctx + "/pages/user/tips.jsp";
            }
            else {
                if(result.errorCode && result.errorCode=="003"){//验证失败
                    alert(result.errorMsg);
                }else{
                    window.location.href=ctx + "/pages/common/error.jsp?errorMsg=" + result.errorMsg;
                }

            }
        }
    });
}