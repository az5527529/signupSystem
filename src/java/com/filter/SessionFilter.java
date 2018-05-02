package com.filter;

import com.util.SessionManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by victor on 2017/8/7.
 */
public class SessionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        session.setAttribute("remoteAddr",req.getRemoteAddr());
        SessionManager.setSession(session);
        String currentURL = req.getRequestURI();

        //如果是忽略的路径，isIgnored返回true
        if (!this.isIgnored(currentURL, req) //没被忽略的路径
            && (SessionManager.getUserInfo() == null) && (SessionManager.getAttribute("activity") == null)) {//没登陆
            //普通路径，切用户没登录
            //如果是ajax请求响应头会有，x-requested-with；
            if (req.getHeader("x-requested-with") != null
                    && req.getHeader("x-requested-with")
                    .equalsIgnoreCase("XMLHttpRequest")){
                //在响应头设置session状态
                res.setHeader("sessionstatus", "sessionOut");
                res.getWriter().print("sessionOut");
                return;
            }
            res.getWriter().print("<script language='javascript'>window.top.location='"+req.getContextPath()+"/login.jsp';</script>");
//            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
    /**
     * 判断是否属于过滤范围
     * @param currentURL
     * @param request
     * @return
     */
    protected boolean isIgnored(String currentURL, HttpServletRequest request) {
        boolean filterResult = false;

        StringBuilder prefix = new StringBuilder();
        prefix.append(request.getContextPath());
        prefix.append(prefix.toString().equals("/") ? "" : "/");
        String pre = prefix.toString();
        if (pre.equals(currentURL)) {
            return true;
        }

        String[] ignoreFiles = new String[]{"login.jsp","error.jsp","toPay.jsp"};
        for (String ignoreFile: ignoreFiles) {
            if(filterResult){
                return true;
            }
            filterResult =  currentURL.toLowerCase().endsWith(ignoreFile.toLowerCase());
        }
        String[] ignorePaths = new String[]{"js/common","css/common","login/login.action",
                "img","jquery-easyui-1.4.4","randomCodeServlet","js/user","css/user"
                ,"pages/user","image/getImgByUrl","payOrder"};
        for (String ignorePath: ignorePaths) {
            if(filterResult){
                return true;
            }
            filterResult = currentURL.toLowerCase().startsWith((pre + ignorePath).toLowerCase());
        }
        return filterResult;
    }
}
