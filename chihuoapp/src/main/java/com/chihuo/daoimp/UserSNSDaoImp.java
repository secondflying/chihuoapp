package com.chihuo.daoimp;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.chihuo.bussiness.User;
import com.chihuo.bussiness.UserSNS;
import com.chihuo.dao.UserSNSDao;

@Repository
public class UserSNSDaoImp extends GenericDAOImp﻿<UserSNS, Integer> implements
		UserSNSDao {
	public UserSNS findByOpenID(String openid, Integer snstype) {
		Criteria crit = getSession().createCriteria(UserSNS.class)
				.add(Restrictions.eq("openid", openid))
				.add(Restrictions.eq("snstype", snstype));
		crit.setCacheable(true);
		return (UserSNS) crit.uniqueResult();
	}
}
