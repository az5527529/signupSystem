package com.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class WebUtil {
	/**
	 * controller想view层返回信息的方法
	 * @param request
	 * @param response
	 * @param s
	 * @throws IOException
	 */
	public static void outputPage(HttpServletRequest request,HttpServletResponse response,String s) throws IOException{
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter printwriter = response.getWriter();
		printwriter.print(s);
		printwriter.close();
	}
	/**
	 * 根据传进来的参数拼 hql的where条件
	 * @param request
	 * @return
	 */
	public static String getWhereCondition(HttpServletRequest request){
		StringBuilder condition = new StringBuilder();
		String fields = request.getParameter("fields");
		if(!StringUtil.isEmptyString(fields)){
			JSONObject json = JSONObject.fromObject(fields);
			 Iterator keyIter = json.keys();
			    String key;
			    String value;
			    while( keyIter.hasNext())
			    {
			        key = (String)keyIter.next();
			        value = (String)json.get(key);
			        if(!StringUtil.isEmptyString(value)){
			        	condition.append(" and "+key+"='"+value+"'");
			        }
			    } 
		}

		return condition.toString();
	}
}
