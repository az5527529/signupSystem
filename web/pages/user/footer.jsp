﻿<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="com.service.user.SignupService" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%
    WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
    SignupService service = (SignupService)wc.getBean("signupService");
//    int num = 0;
    int num = service.loadSignNumByStatus(SessionManager.getAttribute("openid").toString(),SessionManager.getAttribute("activityId").toString(),1);
%>
<html>
<body>
    <ul>
        <li>
            <a id="indexBtn" href="${ctx}/pages/user/index.jsp">首页</a>
        </li>
        <li>
            <a id="signBtn" href="${ctx}/pages/user/announce.jsp">报名</a>
        </li>
        <li>
            <a id="toPayBtn" href="${ctx}/toPay.jsp">待付(<span id="number" style="color: red;"><%=num%></span>)</a>
        </li>
        <li>
            <a id="payedBtn" href="${ctx}/pages/user/payed.jsp">已付</a>
        </li>
    </ul>

</body>

<script type="text/javascript">
    $(document).ready(function () {
        $('body').height($('body')[0].clientHeight);
    });
</script>
</html>