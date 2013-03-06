package com.chihuo.dao;

import java.util.List;

import com.chihuo.bussiness.Category;
import com.chihuo.bussiness.Restaurant;

public interface CategoryDao extends GenericDao<Category, Integer> {
	public List<Category> findByRestaurant(Restaurant r) ;
	
	public Category findByIdInRestaurant(Restaurant r,int id) ;
}
