package com.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by victor on 2018/4/8.
 */
@Controller
@RequestMapping("/image")
public class ImageController {
    /**
     * 通过url访问服务器的照片
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/getImgByUrl", method = RequestMethod.GET)
    public void getImgByUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String path = "d:\\image\\" + request.getParameter("imgUrl");
//        final String path = "d:\\image\\activityImg/1523274346051.jpg" ;
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
        BufferedInputStream bis = null;
        OutputStream os = null;
        FileInputStream fileInputStream = new FileInputStream(new File(path));
        bis = new BufferedInputStream(fileInputStream);
        byte[] buffer = new byte[512];
        response.reset();
        response.setCharacterEncoding("UTF-8");
        //不同类型的文件对应不同的MIME类型
        response.setContentType("image/png");
        //文件以流的方式发送到客户端浏览器
        response.setContentLength(bis.available());
        os = response.getOutputStream();
        int n;
        while ((n = bis.read(buffer)) != -1) {
            os.write(buffer, 0, n);
        }
        bis.close();
        os.flush();
        os.close();
    }
}
