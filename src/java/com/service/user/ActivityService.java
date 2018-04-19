package com.service.user;

import com.entity.Activity;
import com.service.common.BaseService;
import com.util.CollectionUtil;

import java.util.List;

/**
 * Created by victor on 2018/4/2.
 */
public interface ActivityService extends BaseService<Activity> {
    public Activity saveOrUpdate(Activity entity);

    /**
     * 通过id获取活动 背景图片url
     * @param id
     * @return
     */
    public String getImgeUrlById(long id);
}
