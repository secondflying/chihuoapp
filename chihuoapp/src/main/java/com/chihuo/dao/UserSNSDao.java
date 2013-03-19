package com.chihuo.dao;

import com.chihuo.bussiness.UserSNS;

public interface UserSNSDao extends GenericDao<UserSNS, Integer> {
	public UserSNS findByOpenID(String openid, Integer snstype);
	
	public UserSNS findByUidSnsType(Integer uid, Integer snstype);
}
