var  appId;
var  timeStamp;
var  nonceStr;
var  packageValue;
var  paySign;
var orderNo;
function pay(){
    var bh = $("body").height();
    var bw = $("body").width();
    $("#fullbg").css({
                height:bh,
                width:bw,
                display:"block"
            });
    $.ajax({
        type : "post",
        dataType : 'json',
        data : {},
        url : ctx+"/payOrder/toPay.action?ids=" + Math.random(),

        success : function(data) {
            if(data.success){
                appId = data.appid;
                timeStamp = data.timeStamp;
                nonceStr = data.nonceStr;
                packageValue = data.packageValue;
                paySign = data.paySign;
                orderNo = data.orderNo;

                if (typeof WeixinJSBridge == "undefined"){
                    if( document.addEventListener ){
                        document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
                    }else if (document.attachEvent){
                        document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                        document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
                    }
                }else{
                    onBridgeReady();
                }
            }else{
                window.location.href = ctx + "/pages/common/error.jsp?errorMsg="+data.errorMsg;//跳到错误页面
            }
            
        },
        error : function(xhr, type, msg) {
	    $("#fullbg").hide();	
	    $.ajax({
                    type : "post",
                    dataType : 'json',
                    data : {orderNo:orderNo},
                    url : ctx+"/payOrder/cancelOrder.action?ids=" + Math.random(),

                    success : function(result) {
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
        },
        async : true
    });
}
function onBridgeReady(){
    $("#fullbg").hide();
    WeixinJSBridge.invoke(
        'getBrandWCPayRequest', {
            "appId" : appId,     //公众号名称，由商户传入
            "timeStamp": timeStamp,         //时间戳，自1970年以来的秒数
            "nonceStr" : nonceStr, //随机串
            "package" : packageValue,
            "signType" : "MD5",         //微信签名方式:
            "paySign" : paySign    //微信签名
        },function(res){
	    
            if(res.err_msg == "get_brand_wcpay_request:ok"){
                // window.location.href=ctx + "/pages/user/tips.jsp?tips=付款成功";
                window.location.href=ctx + "/pages/user/payed.jsp";
            }else {
                $.ajax({
                    type : "post",
                    dataType : 'json',
                    data : {orderNo:orderNo},
                    url : ctx+"/payOrder/cancelOrder.action?ids=" + Math.random(),

                    success : function(result) {
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
        });
}

//加载未支付的报名信息
$(function () {

    $("#toPayBtn").css("color","green");
    $.ajax({
        type : "post",
        dataType : 'json',
        data : {status:1},
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

                    var modifyBtn = $("<a onclick='edit("+info.signupInfoId+")'>修改</a>");
                    var deleteBtn = $("<a onclick='deleteInfo("+info.signupInfoId+")'>删除</a>");

                    signUl.append(modifyBtn).append(deleteBtn);

                    infoDiv.append(signupDiv);
                }
		if(list.length > 0){
		    var btnDiv = $('<div id="btnDiv"><a class="greenBtn" onclick="pay()" id="confirmBtn">付款</a></div>');		
                    infoDiv.append(btnDiv);
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

function edit(signupInfoId){
    window.location.href = ctx + "/pages/user/signup.jsp?signupInfoId=" + signupInfoId;//跳转到编辑页面
}

function deleteInfo(signupInfoId){
    $.messager.confirm('Confirm', '您确定要删除该报名信息吗?', function (r) {
        if (r) {
            $.post(ctx + '/signupInfo/deleteById.action', { id: signupInfoId }, function (result) {
                if (result.success) {
                    window.location.href=ctx + "/toPay.jsp";
                } else {
                    newAlert(result.errorMsg);
                }
            }, 'json');
        }
    });
}