package com.service.common;

import com.entity.UserInfo;


public interface LoginService extends BaseService<UserInfo> {
	public UserInfo login(String userName, String password);
}
