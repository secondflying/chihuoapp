package com.chihuo.daoimp;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.chihuo.bussiness.Owner;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.RestaurantDao;

@Repository
public class RestaurantDaoImp  extends GenericDAOImp﻿<Restaurant, Integer> implements RestaurantDao{

	@SuppressWarnings("unchecked")
	public List<Restaurant> findByStatus(int status) {
		Criteria crit = getSession().createCriteria(Restaurant.class).add(
				Restrictions.eq("status", status));
		crit.setCacheable(true);
		return (List<Restaurant>) crit.list();
	}

	@SuppressWarnings("unchecked")
	public List<Restaurant> findNotDeleted() {
		Criteria crit = getSession().createCriteria(Restaurant.class).add(
				Restrictions.not(Restrictions.eq("status", -1)));
		crit.setCacheable(true);
		return (List<Restaurant>) crit.list();
	}

	@SuppressWarnings("unchecked")
	public List<Restaurant> findByUser(Owner u) {
		Criteria crit = getSession().createCriteria(Restaurant.class)
				.createCriteria("owner").add(Restrictions.eq("id", u.getId()));
		crit.setCacheable(true);
		return (List<Restaurant>) crit.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Restaurant> findByExtent(double xmin,double xmax,double ymin,double ymax){
		Criterion res1 = Restrictions.and(Restrictions.gt("x", xmin),
				Restrictions.lt("x", xmax));
		Criterion res2 = Restrictions.and(Restrictions.gt("y", ymin),
				Restrictions.lt("y", ymax));
		Criteria crit = getSession().createCriteria(Restaurant.class).add(
				Restrictions.and(res1, res2));
		crit.setCacheable(true);
		return (List<Restaurant>) crit.list();
	}

	
}
