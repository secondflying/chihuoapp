package com.chihuo.daoimp;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.chihuo.bussiness.Category;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.CategoryDao;

@Repository
public class CategoryDaoImp extends GenericDAOImp﻿<Category, Integer> implements CategoryDao {
	@SuppressWarnings("unchecked")
	public List<Category> findByRestaurant(Restaurant r) {
		Criteria crit = getSession().createCriteria(Category.class).add(Restrictions.not(Restrictions.eq("status", -1)));
		crit = crit.createCriteria("restaurant").add(Restrictions.eq("id", r.getId()));
		crit.setCacheable(true);
		return (List<Category>)crit.list();
	}
	
	public Category findByIdInRestaurant(Restaurant r,int id) {
		Criteria crit = getSession().
									createCriteria(Category.class)
									.add(Restrictions.eq("id", id))
									.add(Restrictions.not(Restrictions.eq("status", -1)))
									.createCriteria("restaurant").add(Restrictions.eq("id", r.getId()));
		crit.setCacheable(true);

		return (Category) crit.uniqueResult();
	}
}
