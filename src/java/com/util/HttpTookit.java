package com.util;


import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpTookit {
	private static final CloseableHttpClient httpClient;
    public static final String CHARSET = "UTF-8";
 
    static {
    	//HttpHost proxy = new HttpHost("127.0.0.1", 8888, "http");  //设置本地代理fiddler抓包用
        RequestConfig config = RequestConfig.custom().setConnectTimeout(30*1000).
        		setConnectionRequestTimeout(10*1000).setSocketTimeout(30*1000)
        		.build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).setMaxConnTotal(800).setMaxConnPerRoute(400).build();
    }
    public static String doGet(String url, Map<String, String> params){
        return doGet(url, params,CHARSET);
    }
    public static String doPost(String url, Map<String, String> params){
        return doPost(url, params,CHARSET);
    }
    public static String doPost(String url, String params){
        return doPost(url, params,CHARSET);
    }
    /**
     * HTTP Get 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param charset    编码格式
     * @return    页面内容
     */
    public static String doGet(String url,Map<String,String> params,String charset){
        if(StringUtils.isBlank(url)){
            return null;
        }
        CloseableHttpResponse response = null;
        System.out.println("----------------111doGet55-------");
        HttpGet httpGet = null;
        try {
            if(params != null && !params.isEmpty()){
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for(Map.Entry<String,String> entry : params.entrySet()){
                    String value = entry.getValue();
                    if(value != null){
                        pairs.add(new BasicNameValuePair(entry.getKey(),value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            httpGet = new HttpGet(url);
            HttpEntity entity=null;
            //for(int i=0;i<3;i++){
            //	  System.out.println("----------------111doGet70-------");
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                entity = response.getEntity();
                System.out.println("status="+statusCode+",entity="+EntityUtils.toString(entity, "utf-8"));
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
             entity = response.getEntity();
 //            System.out.println("----------------111doGet78-------entity:"+entity);
//             if(entity!=null){
//            	 System.out.println("----------------111doGet81-------entity:"+entity.toString());
//            	 break;  
//             }
           // }
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, "utf-8");
               
            }
            System.out.println("----------------111doGet89-------result:"+result);
            EntityUtils.consume(entity);
          
            return result;
        } catch (Exception e) {
        	httpGet.abort();
            e.printStackTrace();
        }finally{
        	if(response != null){
        		try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
        return null;
    }
     
    /**
     * HTTP Post 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param charset    编码格式
     * @return    页面内容
     */
    public static String doPost(String url,Map<String,String> params,String charset){
        if(StringUtils.isBlank(url)){
            return null;
        }
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        try {
            List<NameValuePair> pairs = null;
            if(params != null && !params.isEmpty()){
                pairs = new ArrayList<NameValuePair>(params.size());
                for(Map.Entry<String,String> entry : params.entrySet()){
                    String value = entry.getValue();
                    if(value != null){
                        pairs.add(new BasicNameValuePair(entry.getKey(),value));
                    }
                }
            }
           httpPost = new HttpPost(url);
            if(pairs != null && pairs.size() > 0){
                httpPost.setEntity(new UrlEncodedFormEntity(pairs,CHARSET));
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
         
            return result;
        } catch (Exception e) {
        	httpPost.abort();
            e.printStackTrace();
        }finally{
        	if(response!=null){
        		   try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
        return null;
    }
    /**
     * HTTP Post 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param charset    编码格式
     * @return    页面内容
     */
    public static String doPost(String url,String params,String charset){
        if(StringUtils.isBlank(url)){
            return null;
        }
        HttpPost httpPost =null;
        CloseableHttpResponse response = null;
        try {
            httpPost = new HttpPost(url);
            StringEntity stringentity=new StringEntity(params, CHARSET);
            httpPost.setEntity(stringentity);
             response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            
            return result;
        } catch (Exception e) {
        	httpPost.abort();
            e.printStackTrace();
        }finally{
        	if(response!=null){
        		try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
        return null;
    }
}
