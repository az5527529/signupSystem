package com.service.impl.user;

import com.entity.IdentifyOrder;
import com.entity.SignupInfo;
import com.exception.MessageException;
import com.service.impl.common.BaseServiceImpl;
import com.service.user.IdentifyOrderService;
import com.util.CollectionUtil;
import com.util.SessionManager;
import com.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by victor on 2018/4/19.
 */
@Service("identifyOrderService")
public class IdentifyOrderServiceImpl extends BaseServiceImpl<IdentifyOrder> implements IdentifyOrderService {
    @Override
    public IdentifyOrder loadIdentifyOrder() {
        String openid = SessionManager.getAttribute("openid") == null ? "" : SessionManager.getAttribute("openid").toString();
//        String openid = "1";
        String activityId = SessionManager.getAttribute("activityId") == null ? "" : SessionManager.getAttribute("activityId").toString();
        if(!StringUtil.isEmptyString(openid) && !StringUtil.isEmptyString(activityId)){
            String hql = "from IdentifyOrder where openid=:openid and activityId=:activityId";
            List<IdentifyOrder> list = super.getSession().createQuery(hql).setString("openid",openid)
                                        .setString("activityId",activityId).list();
            if(!CollectionUtil.isEmptyCollection(list)){
                return list.get(0);
            }
        }
        return null;
    }

    @Override
    public int confirmReceive(String identifyNo, String numer) throws MessageException {
        int result = 1;
        //当物资领取号不为空的时候，将其绑定的已付款的报名信息全部更新为已领取
        if(!StringUtil.isEmptyString(identifyNo)){
            String sql = "update signup_info t join identify_order s on s.activity_id=t.activity_id and s.openid=t.openid" +
                    " set t.is_Take_Material=1 where t.is_Take_Material=0 and s.identify_no=:identifyNo";
            result = super.getSession().createSQLQuery(sql).setString("identifyNo",identifyNo).executeUpdate();
            if(result == 0){
                throw new MessageException("物资领取号不存在或者重复领取");
            }
        }else if (!StringUtil.isEmptyString(numer)){//通过序号确认报名信息已领取
            String hql = "from SignupInfo where number=:number";
            List<SignupInfo> list = super.getSession().createQuery(hql).setString("number",numer).list();
            if(CollectionUtil.isEmptyCollection(list)){
                throw new MessageException("序号不存在");
            }
            SignupInfo signupInfo = list.get(0);
            if(signupInfo.getIsTakeMaterial()){
                throw new MessageException("该序号已领取物资，请勿重复领取");
            }
            signupInfo.setIsTakeMaterial(true);
            super.getSession().update(signupInfo);
        }else {
            throw new MessageException("物资领取号与序号都为空");
        }
        return result;
    }
}
