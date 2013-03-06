package com.chihuo.daoimp;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.Waiter;
import com.chihuo.dao.WaiterDao;

@Repository
public class WaiterDaoImp extends GenericDAOImp﻿<Waiter, Integer> implements
		WaiterDao {

	@SuppressWarnings("unchecked")
	public List<Waiter> findByRestaurant(Restaurant r) {
		Criteria crit = getSession().createCriteria(Waiter.class).add(
				Restrictions.not(Restrictions.eq("status", -1)));
		crit = crit.createCriteria("restaurant").add(
				Restrictions.eq("id", r.getId()));
		crit.setCacheable(true);
		return (List<Waiter>) crit.list();
	}

	public Waiter findByIdInRestaurant(Restaurant r, int id) {
		Criteria crit = getSession().createCriteria(Waiter.class)
				.add(Restrictions.eq("id", id))
				.add(Restrictions.not(Restrictions.eq("status", -1)))
				.createCriteria("restaurant")
				.add(Restrictions.eq("id", r.getId()));
		crit.setCacheable(true);
		return (Waiter) crit.uniqueResult();
	}

	public Waiter findByName(String name, Restaurant r) {
		Criteria crit = getSession().createCriteria(Waiter.class)
				.add(Restrictions.eq("name", name))
				.add(Restrictions.not(Restrictions.eq("status", -1)));
		crit = crit.createCriteria("restaurant").add(
				Restrictions.eq("id", r.getId()));
		crit.setCacheable(true);
		return (Waiter) crit.uniqueResult();
	}

	public Waiter findByNameAndPassword(String name, String password,
			Restaurant r) {
		Criteria crit = getSession().createCriteria(Waiter.class)
				.add(Restrictions.eq("name", name))
				.add(Restrictions.eq("password", password));
		crit = crit.createCriteria("restaurant").add(
				Restrictions.eq("id", r.getId()));
		crit.setCacheable(true);
		return (Waiter) crit.uniqueResult();
	}
}
