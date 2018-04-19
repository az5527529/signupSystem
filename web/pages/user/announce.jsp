<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@include file="/pages/common/head.jsp"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/user/common.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/user/toPay.css">
</head>
<body>
<style type="text/css">
    .announce{
        margin:1rem 0.5rem;
        font-size: 1.2rem;
        height:auto;
    }
    .announce p{
        line-height: 2rem;
        margin-top:0.5rem;
	margin:0;	
    
    }
</style>
<div id="container" class="container">
    <div class="header">
        <%@include file="/pages/user/header.jsp"%>
    </div>
    <div class="content">
        
        <div style="text-align: center;margin-top:10rem;">
            <div style="text-align: center;margin-bottom: 1rem;">
                <a style="color: blue"  href="index.jsp">活动声明</a>
            </div>
            <div>
                <input id="checkBox" type="checkbox" onchange="changeCheck()">我已阅读并同意以上声明
            </div>
            <div style="margin-top:0.5rem;">
                <a id="nextBtn"  class="greenBtn" style="margin:0 auto;width: 60%;background-color:grey; " onclick="nextStept()" >下一步</a>
            </div>

        </div>
    </div>
    <div class="footer">
        <%@include file="/pages/user/footer.jsp"%>
    </div>
</div>

</body>
<script type="text/javascript">
    function changeCheck() {
        if($("#checkBox").get(0).checked){
            $("#nextBtn").css("background-color","green");
        }else{
            $("#nextBtn").css("background-color","gray");
        }
    }
    function nextStept() {
        if($("#checkBox").get(0).checked){
            window.location.href=ctx + "/pages/user/signup.jsp";
        }else{
            alert("请先勾选我已阅读并同意以上声明");
            return;
        }
    }
</script>
</html>