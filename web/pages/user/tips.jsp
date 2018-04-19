

<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@include file="/pages/common/head.jsp"%>

<html>
<head>
    <title>提示页面</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/user/common.css">
    <style type="text/css">
        #tipsDiv{
            font-size: 3rem;
            color:green;
            /*display:flex;
            display: -webkit-flex; !* Safari *!
            flex-direction: column;
            justify-content: center;
            align-items: center;*/
            height: 20%;
            text-align: center;
        }
	.btnDiv{
    		/*display:flex;
    		display: -webkit-flex; !* Safari *!
    		justify-content: space-around;*/
    		
            	text-align: center;
	}
	.btnDiv a{
		height:3rem;
		line-height:3rem;
		font-size:2rem;
		margin-top:2rem;
		display:inline-block;
            	width:90%;
	}
    </style>
</head>
<body>

    <div id="container" class="container">
        <div class="header">
            <%@include file="/pages/user/header.jsp"%>
        </div>
        <div class="content">
            <div id="tipsDiv"><span id="tips" ></span></div>
	    <div id="btnDiv" class="btnDiv">
               
                <a class="greenBtn" href="${ctx}/toPay.jsp">去付款</a>
                <a class="greenBtn" href="signup.jsp">继续报名</a>

            </div>
        </div>
        <div class="footer">
            <%@include file="/pages/user/footer.jsp"%>
        </div>
    </div>
</body>
<script type="text/javascript">
    var tips = getQueryString("tips");
    $("#tips").text(tips?tips:"");
</script>
</html>
