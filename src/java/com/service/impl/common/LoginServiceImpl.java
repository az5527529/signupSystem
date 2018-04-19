package com.service.impl.common;

import com.entity.UserInfo;
import com.service.common.LoginService;
import com.util.CollectionUtil;
import com.util.SessionManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by victor on 2018/3/27.
 */
@Service("loginService")
public class LoginServiceImpl extends BaseServiceImpl<UserInfo> implements LoginService {
	@Override
	public UserInfo login(String userName, String password) {
		UserInfo user = new UserInfo();
		String hql = "from UserInfo where userName=:userName";
		List<UserInfo> list = super.getSession().createQuery(hql).setString("userName",userName).list();
		if(CollectionUtil.isEmptyCollection(list)){
			user.setErrorMsg("该用户不存在!");
			return user;
		}
		UserInfo sysUser = list.get(0);
		if(!sysUser.getPassword().equals(password)){
			user.setErrorMsg("密码错误!");
			return user;
		}
		sysUser.setErrorMsg("");
		SessionManager.setUserInfo(sysUser);
		return sysUser;
	}
}
