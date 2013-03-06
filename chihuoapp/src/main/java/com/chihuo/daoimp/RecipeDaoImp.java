package com.chihuo.daoimp;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.chihuo.bussiness.Category;
import com.chihuo.bussiness.Recipe;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.RecipeDao;

@Repository
public class RecipeDaoImp extends GenericDAOImp﻿<Recipe, Integer> implements RecipeDao{
	@SuppressWarnings("unchecked")
	public List<Recipe> findByRestaurant(Restaurant r) {
		Criteria crit = getSession().createCriteria(Recipe.class).add(Restrictions.not(Restrictions.eq("status", -1))).addOrder( Order.asc("category.id"));
		crit = crit.createCriteria("restaurant").add(Restrictions.eq("id", r.getId()));
		crit.setCacheable(true);

		return (List<Recipe>)crit.list();
	}
	
	public Recipe findByIdInRestaurant(Restaurant r,int id) {
		Criteria crit = getSession().createCriteria(Recipe.class).add(Restrictions.eq("id", id)).add(Restrictions.not(Restrictions.eq("status", -1)));
		crit = crit.createCriteria("restaurant").add(Restrictions.eq("id", r.getId()));
		crit.setCacheable(true);

		return (Recipe) crit.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Recipe> findByCategory(Category r) {
		Criteria crit = getSession().createCriteria(Recipe.class).add(Restrictions.not(Restrictions.eq("status", -1)));
		crit = crit.createCriteria("category").add(Restrictions.eq("id", r.getId()));
		crit.setCacheable(true);

		return (List<Recipe>)crit.list();
	}
}
