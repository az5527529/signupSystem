
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ page import="com.entity.Activity" %>
<%@ page import="com.util.StringUtil" %>
<%@ page import="com.util.WechatUtil" %>
<%@ page import="com.service.user.ActivityService" %>
<%@include file="/pages/common/head.jsp"%>
<%
    String code = request.getParameter("code");
    if("null".equals(code) || code == null){
        code = "";
    }
    Activity headActivity = null;
//    String openid = SessionManager.getAttribute("openid") == null ? "" : SessionManager.getAttribute("openid").toString();
    String openid = "1";
    SessionManager.setAttribute("openid",openid);
    String activityId = request.getParameter("activityId");
    //未进行微信认证
    /*if(StringUtil.isEmptyString(code) && StringUtil.isEmptyString(openid)){
        response.sendRedirect(request.getContextPath() + "/pages/user/weixin.jsp?url=" + request.getContextPath() + "/pages/user/index.jsp?activityId=" + activityId);
        return;
    }
    //获取微信id\
    if(!StringUtil.isEmptyString(code)&&StringUtil.isEmptyString(openid)){
        WechatUtil.getWechatVo(code);
    }*/

    if(!StringUtil.isEmptyString(activityId)){
        SessionManager.setAttribute("activityId",activityId);
        WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        ActivityService service = (ActivityService)wc.getBean("activityService");
        headActivity = service.loadById(Long.parseLong(activityId));
        SessionManager.setAttribute("activity",headActivity);
    }else{
        if(SessionManager.getAttribute("activityId") == null ){
            response.sendRedirect(request.getContextPath() + "/pages/common/error.jsp");
            return;
        }
    }
%>
<c:set value="${activity}" var="activity" />
<script type="text/javascript">
    var code = "<%=code%>";
    var activityId= "<%=activityId%>";
</script>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/user/common.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/user/index.css?version=1">
</head>

<body>
    <div id="container" class="container">
        <div class="header">
            <%@include file="/pages/user/header.jsp"%>
        </div>
        <div class="content">
            <div id="btnDiv" class="btnDiv">
                <ul>
                    <li>
                        <a class="greenBtn" href="announce.jsp">报名</a>
                    </li>
                    <li>
                        <a class="greenBtn" href="search.jsp">查询</a>
                    </li>
                </ul>
            </div>
            <div id="announce" class="announce">
                ${activity.content}
                <%--<p style="text-align: center;font-size: 3rem;">报名已截止</p>--%>
                <%--<li style="text-align: center;">免责声明</li>
                <li style="text-align: center;color: red;">请仔细阅读以下活动声明，报名者提交报名信息即被默认为同意以下声明内容：</li>

                <li>1、报名者自愿报名参加阳江市第四届“五一”公益健步行活动 (以下统称"活动")；全面了解并同意遵守活动的各项规定；</li>

                <li>2、主办方统一为报名者购买人身保险，报名者同意向组委会提供真实有效的报名信息（包括姓名、身份证、手机号码等资料），若报名资料不实或不全，主办方则不予以购买；</li>

                <li>3、报名者必须对自己的安全负责，活动中如发生意外，主办方和同行者有义务组织救援，但不承担任何法律和经济责任;</li>

                <li>4、报名者具有完全民事行为能力（60岁以上者请充分考虑自身健康状况谨慎出行，18岁以下者需监护人同意并由监护人陪同参加），且身体健康，无任何不适合进行徒步或户外运动的疾病，包括高血压、心脏病、哮喘、糖尿病、呼吸系统疾病等，同时，过度劳累、急症病期患者等也不得报名;</li>

                <li>5、由于健步行线路的特殊性，请始终保持离湖边20米以上的距离，千万不可靠近河边拍照或玩水，以免发生意外；</li>

                <li>6、主办方有权根据天气、场地线路、环境安全等原因对活动日期、时间、线路进行合理的调整；</li>

                <li>7、报名者或指定代理人已认真阅读并同意遵守执行上述所有内容，并承担相应的法律责任;</li>

                <li>8、主办方对本次活动拥有最终解释权。</li>--%>
            </div>
            
        </div>
        <div class="footer">
            <%@include file="/pages/user/footer.jsp"%>
        </div>
    </div>

</body>
<script type="text/javascript">
    $(function () {
        
	$("#indexBtn").css("color","green");
    });
</script>

</html>