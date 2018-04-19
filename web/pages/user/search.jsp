<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@include file="/pages/common/head.jsp"%>
<%--<%@include file="/pages/user/filter.jsp"%>--%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/user/common.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/user/toPay.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/user/search.css">
</head>
<style type="text/css">
    .searchDiv ul{
        display:flex;
        display: -webkit-flex; /* Safari */
        flex-direction: row;
    }
    .searchDiv li{
        line-height: 1.5rem;
        height: 2rem;
        margin-left: 1rem;
        margin-top: 1rem;
        font-size: 1rem;
        float:none;
    }
</style>
<body>
<div id="container" class="container">
    <div class="header">
        <%@include file="/pages/user/header.jsp"%>
    </div>
    <div class="content">
        <div class="searchDiv">
            <ul>
                <li style="width: 30%"><label>真实姓名<span class="redStar">*</span>:</label></li>
                <li style="width: 70%">
                    <input id="name" style="width: 95%;line-height: 1.5rem;font-size: 1rem" name="name" placeholder="请输入真实姓名" />
                </li>
            </ul>
            <ul>
                <li style="width: 30%"><label>序号:</label></li>
                <li style="width: 70%">
                    <input id="number" style="width: 95%;line-height: 1.5rem;font-size: 1rem" name="name" placeholder="可填可不填" />
                </li>
            </ul>
            <ul style="margin-top:1rem;">
                <li style="width: 90%;text-align: center">
                    <a style="width: 100%" id="searchBtn" class="greenBtn" onclick="searchSignupInfo()">查询</a>
                </li>

            </ul>
        </div>
        <div id="infoDiv" style="margin-top: 1rem">

        </div>

    </div>
    
    <div class="footer">
        <%@include file="/pages/user/footer.jsp"%>
    </div>
</div>

</body>
<script type="text/javascript" src="${ctx}/js/user/search.js">
</script>
</html>