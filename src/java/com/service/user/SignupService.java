package com.service.user;

import com.entity.SignupInfo;
import com.exception.MessageException;
import com.service.common.BaseService;

import java.util.List;

/**
 * Created by victor on 2018/4/3.
 */
public interface SignupService extends BaseService<SignupInfo>{
    public SignupInfo saveOrUpdate(SignupInfo entity) throws MessageException;

    public SignupInfo addEntity(SignupInfo entity) throws MessageException;

    public SignupInfo updateEntity(SignupInfo entity) throws MessageException;

    /**
     * 通过状态获取报名信息
     * @param status
     * @return
     */
    public List<SignupInfo> loadSignInfoByStatas(int status) throws MessageException;

    /**
     * 通过id删除报名信息
     * @param signupInfoId
     * @return
     * @throws MessageException
     */
    public int deleteInfoById(Long signupInfoId) throws MessageException;

    /**
     * 查询报名信息
     * @return
     */
    public List<SignupInfo> searchSignupInfoFromApp(String name,String number) throws MessageException;

    /**
     * 通过状态查询报名信息数量
     * @param status
     * @return
     */
    public int loadSignNumByStatus(String openid,String activityId,int status);
}
