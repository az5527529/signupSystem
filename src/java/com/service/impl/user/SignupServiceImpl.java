package com.service.impl.user;

import com.Constant.CommonConstants;
import com.Constant.DocumentNoEnum;
import com.entity.Activity;
import com.entity.SignupInfo;
import com.exception.MessageException;
import com.service.impl.common.BaseServiceImpl;
import com.service.user.SignupService;
import com.util.CollectionUtil;
import com.util.SessionManager;
import com.util.StringUtil;
import com.util.Tools;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by victor on 2018/4/3.
 */
@Service("signupService")
public class SignupServiceImpl extends BaseServiceImpl<SignupInfo> implements SignupService{
    @Override
    public SignupInfo saveOrUpdate(SignupInfo entity)  throws MessageException {
        if(entity.getSignupInfoId() != null && entity.getSignupInfoId() > 0){
            return this.updateEntity(entity);
        }else {
            return this.addEntity(entity);
        }
    }

    /**
     * 新增报名信息
     * @param entity
     * @return
     */
    @Override
    public SignupInfo addEntity(SignupInfo entity)  throws MessageException{
        //1.校验合法性
        validate();

        String openid = SessionManager.getAttribute("openid").toString();
//        String openid = "1";
        Long activityId = Long.parseLong(SessionManager.getAttribute("activityId").toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(new Date());

        //校验是否已报名
        String sql = "select 1 from signup_info where 1=1 and id_card=:idCard";
        List list = super.getSession().createSQLQuery(sql).setString("idCard",entity.getIdCard()).list();
        if(!CollectionUtil.isEmptyCollection(list)){
            throw new MessageException().setErrorMsg("该身份证已报名").setErrorCode(CommonConstants.errorCode.VALIDATE_FAIL);
        }

        entity.setSignupInfoId(0L);
        entity.setOpenid(openid);//微信号
        entity.setActivityId(activityId);//活动id
        entity.setSignupTime(currentTime);
        entity.setIsTakeMaterial(false);//是否已领取物资
        entity.setStatus(CommonConstants.signupStatus.INIT);//状态为未付款
        entity.setOrderNo("");//刚报名订单号为空
        entity.setNumber(Tools.getDocumentNo(DocumentNoEnum.SignupNumber));//报名序号

        return super.save(entity);
    }

    @Override
    public SignupInfo updateEntity(SignupInfo entity) throws MessageException {
        //校验是否已报名
        String sql = "select 1 from signup_info where 1=1 and id_card=:idCard and signup_info_id<>:id";
        List list = super.getSession().createSQLQuery(sql).setString("idCard",entity.getIdCard()).setLong("id",entity.getSignupInfoId()).list();
        if(!CollectionUtil.isEmptyCollection(list)){
            throw new MessageException().setErrorMsg("该身份证已报名");
        }
        beforeUpdateOrDelete(entity.getSignupInfoId());
        return super.update(entity);
    }

    @Override
    public List<SignupInfo> loadSignInfoByStatas(int status) throws MessageException{
        //校验合法性
        validate();

        String openid = SessionManager.getAttribute("openid").toString();
//        String openid = "1";
        Long activityId = Long.parseLong(SessionManager.getAttribute("activityId").toString());

        String hql = "from SignupInfo where 1=1 and openid=:openid and activityId=:id and status=:status";
        List<SignupInfo> list = super.getSession().createQuery(hql).setString("openid",openid)
                .setLong("id",activityId).setInteger("status",status).list();

        return list;
    }

    @Override
    public int deleteInfoById(Long signupInfoId) throws MessageException {
        //删除前进行状态验证
        beforeUpdateOrDelete(signupInfoId);
        SignupInfo entity = super.loadById(signupInfoId);
        if(entity != null && !StringUtil.isEmptyString(entity.getOrderNo())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(new Date());
            String sql = "update pay_order set status=:status ,expire_time=:expireTime where order_no=:orderNo and status=:oldStatus";
            super.getSession().createSQLQuery(sql).setString("orderNo", entity.getOrderNo()).setInteger("status", CommonConstants.orderStatus.EXPIRE).
                    setInteger("oldStatus", CommonConstants.orderStatus.INIT).setString("expireTime", currentTime).executeUpdate();
        }

        int result = super.deleteById(signupInfoId);
        return result;
    }

    @Override
    public List<SignupInfo> searchSignupInfoFromApp(String name, String number) throws MessageException {
        //校验合法性
        validate();

        String openid = SessionManager.getAttribute("openid").toString();
//        String openid = "1";
        Long activityId = Long.parseLong(SessionManager.getAttribute("activityId").toString());

        String hql = "from SignupInfo where 1=1 and openid=:openid and activityId=:id";
        if(!StringUtil.isEmptyString(name)){
            hql += " and name='"+name+"'";
        }
        if(!StringUtil.isEmptyString(number)){
            hql += " and number='"+number+"'";
        }
        List<SignupInfo> list = super.getSession().createQuery(hql).setString("openid",openid)
                .setLong("id",activityId).list();

        return list;
    }

    @Override
    public int loadSignNumByStatus(String openid, String activityId, int status) {
        String sql = "select ifnull(count(1),0) from signup_info where 1=1 and openid=:openid and activity_id=:activityId" +
                " and status=:status";
        List list = super.getSession().createSQLQuery(sql).setString("openid",openid).setString("activityId",activityId)
                    .setInteger("status",status).list();
        if(!CollectionUtil.isEmptyCollection(list)){
            return Integer.parseInt(list.get(0).toString());
        }
        return 0;
    }

    //校验
    public void validate() throws MessageException{
        //1.校验微信id
        Object openidObj = SessionManager.getAttribute("openid");
        if(openidObj == null){
            throw new MessageException().setErrorMsg("请进行微信认证").setErrorCode(CommonConstants.errorCode.WITHOUT_WECHAT);
        }

        //校验活动
        Object activityIdObj = SessionManager.getAttribute("activityId");
        if(activityIdObj == null){
            throw new MessageException().setErrorMsg("非法访问，请重新进入").setErrorCode(CommonConstants.errorCode.ACCESS_VOLATION);
        }
        //活动id
        Long activityId = Long.parseLong(activityIdObj.toString());

        //校验活动当前状态
        String searchActivity = "from Activity where activityId=:id";
        List<Activity> activityList = super.getSession().createQuery(searchActivity).setLong("id",activityId).list();
        Activity activity = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(new Date());
        if(CollectionUtil.isEmptyCollection(activityList)){
            throw new MessageException().setErrorMsg("活动不存在").setErrorCode(CommonConstants.errorCode.VALIDATE_FAIL);
        }else {
            activity = activityList.get(0);

            /*if(currentTime.compareTo(activity.getStartTime()) < 0){
                throw new MessageException().setErrorMsg("活动还没开始").setErrorCode(CommonConstants.errorCode.VALIDATE_FAIL);
            }
            if(currentTime.compareTo(activity.getEndTime()) > 0){
                throw new MessageException().setErrorMsg("活动已结束").setErrorCode(CommonConstants.errorCode.VALIDATE_FAIL);
            }*/
        }
    }

    /**
     * 修改或者删除前操作
     */
    public void beforeUpdateOrDelete(Long signupInfoId) throws MessageException{
        String sql = "select status from signup_info where signup_info_id=:id";
        List list = super.getSession().createSQLQuery(sql).setLong("id",signupInfoId).list();
        if(!CollectionUtil.isEmptyCollection(list)){
            String status = list.get(0).toString();//状态
            //该报名信息已付款，不可修改
            if(!status.equals(String.valueOf(CommonConstants.signupStatus.INIT))){
                throw new MessageException().setErrorMsg("该报名信息已付款，不可修改或删除").setErrorCode(CommonConstants.errorCode.VALIDATE_FAIL);
            }
        }
    }
}
