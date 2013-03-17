package com.chihuo.daoimp;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.chihuo.bussiness.Owner;
import com.chihuo.bussiness.User;
import com.chihuo.dao.OwnerDao;

@Repository
public class OwnerDaoImp extends GenericDAOImp﻿<Owner, Integer> implements
		OwnerDao {
	public Owner findByName(String name) {
		Criteria crit = getSession().createCriteria(User.class).add(
				Restrictions.eq("name", name));

		return (Owner) crit.uniqueResult();
	}

	public Owner findByNameAndPassword(String name, String password) {
		Criteria crit = getSession().createCriteria(Owner.class)
				.add(Restrictions.eq("name", name))
				.add(Restrictions.eq("password", password));
		return (Owner) crit.uniqueResult();
	}
}
