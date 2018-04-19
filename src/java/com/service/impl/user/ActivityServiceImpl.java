package com.service.impl.user;

import com.entity.Activity;
import com.service.impl.common.BaseServiceImpl;
import com.service.user.ActivityService;
import com.util.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by victor on 2018/4/2.
 */
@Service("activityService")
public class ActivityServiceImpl extends BaseServiceImpl<Activity> implements ActivityService{

    @Override
    public Activity saveOrUpdate(Activity entity) {
        if(entity.getActivityId() > 0){
            return super.update(entity);
        }else{
            return super.save(entity);
        }
    }
    /**
     * 通过id获取活动 背景图片url
     * @param id
     * @return
     */
    @Override
    public String getImgeUrlById(long id) {
        String sql = "select background_Url from activity where 1=1 and activity_id=:id";
        List<String> list = this.getSession().createSQLQuery(sql).setLong("id",id).list();
        if(!CollectionUtil.isEmptyCollection(list)){
            return list.get(0);
        }
        return "";
    }
}
