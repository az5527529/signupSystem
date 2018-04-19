package com.util;

import com.Constant.WxPayConstants;
import com.vo.WechatVo;
import net.sf.json.JSONObject;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.*;

/**
 * Created by victor on 2018/3/9.
 */
public class WechatUtil {
    static Logger  logger = LoggerFactory.getLogger(WechatUtil.class);
    /**
     * 获取access_token已经openid
     * @param code
     * @return
     */
    public static WechatVo getWechatVo(String code){
        logger.info("start:{},code=:{}",System.currentTimeMillis(),code);
        WechatVo vo = new WechatVo();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+WechatConfiguration.getAppid()+"&secret="+WechatConfiguration.getSecret()+"&code="+code+"&grant_type=authorization_code";//请求的url
        Map<String,String> params = new HashMap<String, String>();
        String doPost = HttpTookit.doGet(url, params);//获取access_token
        JSONObject tempRespJson = JSONObject.fromObject(doPost);
        Object openidObj =  tempRespJson.get("openid");
        Object access_tokenObj =  tempRespJson.get("access_token");
        String openid = openidObj==null?"":openidObj.toString();
        String access_token = access_tokenObj==null?"":access_tokenObj.toString();
        vo.setAccessToken(access_token);
        vo.setOpenid(openid);
        SessionManager.setAttribute("openid",openid);
        logger.info("start:{},vo=:{}",System.currentTimeMillis(),vo.toString());
        return vo;
    }

    /**
     * 微信支付签名算法sign
     * @param parameters
     * @return
     */
    public static String createSign(SortedMap<String,String> parameters){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }

        sb.append("key=" + WxPayConstants.KEY);
        System.out.println("字符串:"+sb.toString());
        String sign = MD5.MD5Encode(sb.toString(),"utf-8").toUpperCase();
        return sign;
    }

    /**
     * description: 解析微信通知xml
     *
     * @param xml
     * @return
     * @author ex_yangxiaoyi
     * @see
     */
    @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
    private static Map parseXmlToList(String xml) {
        Map retMap = new HashMap();
        try {
            StringReader read = new StringReader(xml);
            // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
            InputSource source = new InputSource(read);
            // 创建一个新的SAXBuilder
            SAXBuilder sb = new SAXBuilder();
            // 通过输入源构造一个Document
            Document doc = (Document) sb.build(source);
            Element root = doc.getRootElement();// 指向根节点
            List<Element> es = root.getChildren();
            if (es != null && es.size() != 0) {
                for (Element element : es) {
                    retMap.put(element.getName(), element.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retMap;
    }
    /**
     * 解析XML 获得名称为para的参数值
     * @param xml
     * @param para
     * @return
     */
    public static String getXmlPara(String xml,String para){
        int start = xml.indexOf("<"+para+">");
        int end = xml.indexOf("</"+para+">");

        if(start < 0 && end < 0){
            return null;
        }
        return xml.substring(start + ("<"+para+">").length(),end).replace("<![CDATA[","").replace("]]>","");
    }
}
