package com.controller.user;

import com.entity.SignupInfo;
import com.exception.MessageException;
import com.service.user.SignupService;
import com.util.SessionManager;
import com.util.WebUtil;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by victor on 2018/4/3.
 */
@Controller
@RequestMapping("/signupInfo")
public class SignupController {
    @Resource
    private SignupService signupService;
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
        SignupInfo entity = null;
        String id = request.getParameter("signupInfoId");
        if(id != null && !id.equals("")){
            entity = this.signupService.loadById(Long.parseLong(id));
        }else{
            entity = new SignupInfo();
        }
        try {
            BeanUtils.populate(entity, request.getParameterMap());
            entity = this.signupService.saveOrUpdate(entity);
            result.put("success",1);
        } catch (MessageException e) {
            e.printStackTrace();
            result.put("success",0);
            result.put("errorMsg",e.getErrorMsg());
            result.put("errorCode",e.getErrorCode());
        }catch (Exception e1){
            e1.printStackTrace();
            result.put("success",0);
            result.put("errorMsg","后台发生错误");
        }
        WebUtil.outputPage(request, response, result.toString());
    }
    @RequestMapping(value = "/loadSignInfoByStatas", method = RequestMethod.POST)
    public void loadSignInfoByStatas(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        String status = request.getParameter("status");
        JSONObject result = new JSONObject();
        try {
            List list = this.signupService.loadSignInfoByStatas(Integer.parseInt(status));
            result.put("success",1);
            result.put("list",list);
        } catch (MessageException e) {
            e.printStackTrace();
            result.put("success",0);
            result.put("errorMsg",e.getErrorMsg());
            result.put("errorCode",e.getErrorCode());
        }catch (Exception e1){
            e1.printStackTrace();
            result.put("success",0);
            result.put("errorMsg","后台发生错误");
        }
        WebUtil.outputPage(request, response, result.toString());
    }
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    public void deleteById(HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        int n = 0;
        JSONObject o = new JSONObject();
        try {
            n = this.signupService.deleteInfoById(Long.parseLong(id));
        } catch (MessageException e) {
            o.put("errorMsg", e.getErrorMsg());
            e.printStackTrace();
        }

        if(n>0){
            o.put("success", "1");
        }
        WebUtil.outputPage(request, response, o.toString());
    }
    @RequestMapping(value = "/loadById", method = RequestMethod.POST)
    public void loadById(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        SignupInfo entity = this.signupService.loadById(Long.parseLong(id));
        WebUtil.outputPage(request, response, JSONObject.fromObject(entity).toString());
    }

    @RequestMapping(value = "/searchSignupInfoFromApp", method = RequestMethod.POST)
    public void searchSignupInfoFromApp(HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String number = request.getParameter("number");
        JSONObject result = new JSONObject();
        try {
            List list = this.signupService.searchSignupInfoFromApp(name,number);
            result.put("success",1);
            result.put("list",list);
        } catch (MessageException e) {
            e.printStackTrace();
            result.put("success",0);
            result.put("errorMsg",e.getErrorMsg());
            result.put("errorCode",e.getErrorCode());
        }catch (Exception e1){
            e1.printStackTrace();
            result.put("success",0);
            result.put("errorMsg","后台发生错误");
        }
        WebUtil.outputPage(request, response, result.toString());
    }
    @RequestMapping(value = "/loadSignNumByStatus", method = RequestMethod.POST)
    public void loadSignNumByStatus(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        String status = request.getParameter("status");
        String activityId = SessionManager.getAttribute("activityId") == null ? "" : SessionManager.getAttribute("activityId").toString();
        String openid = SessionManager.getAttribute("openid") == null ? "" : SessionManager.getAttribute("openid").toString();
        int n = this.signupService.loadSignNumByStatus(openid,activityId,Integer.parseInt(status));
        JSONObject result = new JSONObject();
        result.put("num",n);
        WebUtil.outputPage(request, response, result.toString());
    }
}
