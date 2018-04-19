package com.controller.user;

import com.entity.Activity;
import com.exception.MessageException;
import com.service.user.ActivityService;
import com.util.StringUtil;
import com.util.WebUtil;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by victor on 2018/4/2.
 */
@Controller
@RequestMapping("/activity")
public class ActivityController {
    @Resource
    private ActivityService activityService;
    /**
     * 新增编辑保存方法
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public void saveOrUpdate(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        JSONObject result = new JSONObject();
        try{
            Activity entity = new Activity();
            String backgroundUrl = "";

            //图片信息
            // 1. 创建DiskFileItemFactory对象，设置缓冲区大小和临时文件目录
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 2. 创建ServletFileUpload对象，并设置上传文件的大小限制。
            ServletFileUpload sfu = new ServletFileUpload(factory);
            sfu.setFileSizeMax(3 * 1024 * 1024);//最大3M
            sfu.setHeaderEncoding("utf-8");
            // 3.
            // 调用ServletFileUpload.parseRequest方法解析request对象，得到一个保存了所有上传内容的List对象。
            @SuppressWarnings("unchecked")
            List<FileItem> fileItemList = sfu.parseRequest(request);
            Iterator<FileItem> fileItems = fileItemList.iterator();
            // 4. 遍历list，每迭代一个FileItem对象，调用其isFormField方法判断是否是上传文件
            while (fileItems.hasNext()) {
                FileItem fileItem = fileItems.next();
                //表示普通元素
                if (fileItem.isFormField()) {
                    String fieldName = fileItem.getFieldName();
                    String value = fileItem.getString("utf-8");
                    //活动信息
                    switch (fieldName){
                        case "activityId" : entity.setActivityId(StringUtil.isEmptyString(value) ? 0 : Long.parseLong(value));
                            break;
                        case "content" : entity.setContent(StringUtil.isEmptyString(value) ? "" : value);
                            break;
                        case "startTime" : entity.setStartTime(StringUtil.isEmptyString(value) ? "" : value);
                            break;
                        case "endTime" : entity.setEndTime(StringUtil.isEmptyString(value) ? "" : value);
                            break;
                        case "backgroundUrl" : entity.setBackgroundUrl(StringUtil.isEmptyString(value) ? "" : value);
                            break;
                        case "topic" : entity.setTopic(StringUtil.isEmptyString(value) ? "" : value);
                            break;
                        case "money" : entity.setMoney(StringUtil.isEmptyString(value) ? 0 : Double.parseDouble(value));
                            break;
                    }
                }else{//图片
                    String fileName = fileItem.getName();// 原文件名
                    if(!StringUtil.isEmptyString(fileName)){
                        String suffix = fileName.substring(fileName.lastIndexOf('.'));//扩展名
                        String newFileName = "activityImg/"+new Date().getTime() + suffix;//保存到服务器的图片名称

                        backgroundUrl = newFileName;

                        // 5. 调用FileItem的write()方法，写入文件
                        File file = new File("d:\\image\\" + newFileName);
                        fileItem.write(file);
                        // 6. 调用FileItem的delete()方法，删除临时文件
                        fileItem.delete();
                    }
                }
            }
            //如果背景图片值不为空，则设值
            if(!StringUtil.isEmptyString(backgroundUrl)){
                entity.setBackgroundUrl(backgroundUrl);
            }
            if(entity.getActivityId() > 0){//编辑
                //图片修改时，删除旧图片
                String srcImge = this.activityService.getImgeUrlById(entity.getActivityId());//将旧数据load出来
                if(!entity.getBackgroundUrl().equals(srcImge)){
                    File file = new File("d:\\image\\" + srcImge);
                    // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
                    if (file.exists() && file.isFile()) {
                        if (!file.delete()) {
                            throw  new MessageException().setErrorMsg("删除原背景图片失败");
                        }
                    }
                }

            }
            this.activityService.saveOrUpdate(entity);

            result.put("success",1);
        }catch (Exception e){
            e.printStackTrace();
            result.put("success",0);
            result.put("errorMsg",e.getMessage());
        }
        WebUtil.outputPage(request,response,result.toString());
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    public void deleteById(HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        int n = activityService.deleteById(Long.parseLong(id));
        JSONObject o = new JSONObject();
        if(n>0){
            o.put("success", "1");
        }else{
            o.put("errorMsg", "删除失败");
        }
        WebUtil.outputPage(request, response, o.toString());
    }
    @RequestMapping(value = "/searchActivity", method = RequestMethod.POST)
    public void searchActivity(HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
        StringBuffer whereCondition = new StringBuffer();
        String topic = request.getParameter("topic");
        String startTimeBegin = request.getParameter("startTimeBegin");
        String startTimeEnd = request.getParameter("startTimeEnd");
        String endTimeBegin = request.getParameter("endTimeBegin");
        String endTimeEnd = request.getParameter("endTimeEnd");
        if(!StringUtil.isEmptyString(startTimeBegin)){
            whereCondition.append(" and startTime>='" + startTimeBegin + " 00:00:00'");
        }
        if(!StringUtil.isEmptyString(startTimeEnd)){
            whereCondition.append(" and startTime<='" + startTimeEnd + " 23:59:59'");
        }
        if(!StringUtil.isEmptyString(endTimeBegin)){
            whereCondition.append(" and endTime>='" + endTimeBegin + " 00:00:00'");
        }
        if(!StringUtil.isEmptyString(endTimeEnd)){
            whereCondition.append(" and endTime<='" + endTimeEnd + " 23:59:59'");
        }
        if(!StringUtil.isEmptyString(topic)){
            whereCondition.append(" and topic like '%" + topic + "%'");
        }
        String rowsStr = request.getParameter("rows");
        String pageStr=request.getParameter("page");
        int pageSize = 10;
        int page = 1;
        if(!StringUtil.isEmptyString(rowsStr)&&!StringUtil.isEmptyString(pageStr)){
            pageSize = Integer.parseInt(rowsStr);
            page = Integer.parseInt(pageStr);
        }

        List<Activity> list = activityService.query(whereCondition.toString(),(page-1)*pageSize,pageSize,"startTime","desc");
        JSONObject o = new JSONObject();
        o.put("total", activityService.getCount(whereCondition.toString()));
        o.put("rows", list);
        WebUtil.outputPage(request, response, o.toString());
    }

    @RequestMapping(value = "/loadById", method = RequestMethod.POST)
    public void loadById(HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        Activity activity = activityService.loadById(Long.parseLong(id));

        WebUtil.outputPage(request, response, JSONObject.fromObject(activity).toString());
    }
}
