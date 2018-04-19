<%@ page import="com.entity.Activity" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="com.service.user.ActivityService" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%
    //获取当前活动
    Activity activity = SessionManager.getAttribute("activity") == null ? null :  (Activity) SessionManager.getAttribute("activity");
    if(activity == null){
        if(SessionManager.getAttribute("activityId") == null){
            response.sendRedirect(request.getContextPath() + "/pages/common/error.jsp");
            return;
        }
        String activityId = SessionManager.getAttribute("activityId").toString();
        SessionManager.setAttribute("activityId",activityId);

        WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        ActivityService service = (ActivityService)wc.getBean("activityService");
        activity = service.loadById(Long.parseLong(activityId));
        SessionManager.setAttribute("activity",activity);
    }
    //判断活动是否已过期
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String currentTime = sdf.format(new Date());
    if(activity == null || currentTime.compareTo(activity.getStartTime()) < 0
            || currentTime.compareTo(activity.getEndTime()) > 0){
        response.sendRedirect(request.getContextPath() + "/pages/user/expire.jsp");
    }
%>
<c:set value="${activity}" var="activity" />
