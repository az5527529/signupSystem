package com.util;

import com.entity.UserInfo;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/****************************************
 * @name Session
 * @description 系统管理平台会话实体
 * @author hewc
 * @since 2016-08-15
 ***************************************/
public class SessionManager {
	private static Map<String,SessionManager> sessionMap = new HashMap<String,SessionManager>();
	private static final ThreadLocal httpSession = new ThreadLocal();

	public static void setSession(HttpSession paramHttpSession) {
		httpSession.set(paramHttpSession);
	}

	public static HttpSession getSession() {
		return (HttpSession) httpSession.get();
	}

	public static void setAttribute(String paramString, Object paramObject) {
		HttpSession localHttpSession = (HttpSession) httpSession.get();
		if (localHttpSession != null)
			localHttpSession.setAttribute(paramString, paramObject);
	}

	public static Object getAttribute(String paramString) {
		HttpSession localHttpSession = (HttpSession) httpSession.get();
		if (localHttpSession != null)
			return localHttpSession.getAttribute(paramString);
		return null;
	}

	public static UserInfo getUserInfo(){
		return (UserInfo)getAttribute("userInfo");
	}
	public static void setUserInfo(UserInfo userInfo){
		setAttribute("userInfo",userInfo);
	}
	public static String getUserName(){
		UserInfo userInfo = getUserInfo();
		if(userInfo != null){
			return userInfo.getUserName();
		}
		return "";
	}

	public static void clearUserInfo(){
		HttpSession localHttpSession = (HttpSession) httpSession.get();
		if (localHttpSession != null)
			localHttpSession.removeAttribute("userInfo");
	}
}
