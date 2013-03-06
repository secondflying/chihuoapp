package com.chihuo.daoimp;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.chihuo.bussiness.User;
import com.chihuo.dao.UserDao;

@Repository
public class UserDaoImp extends GenericDAOImp﻿<User, Integer> implements
		UserDao {
	public User findByName(String name) {
		Criteria crit = getSession().createCriteria(User.class).add(
				Restrictions.eq("name", name));
		
		return (User) crit.uniqueResult();
	}

	public User findByNameAndPassword(String name, String password,
			Integer utype) {
		Criteria crit = getSession().createCriteria(User.class)
				.add(Restrictions.eq("name", name))
				.add(Restrictions.eq("password", password))
				.add(Restrictions.eq("utype", utype));
		return (User) crit.uniqueResult();
	}
}
