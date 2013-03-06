package com.chihuo.dao;

import java.util.List;

import com.chihuo.bussiness.Category;
import com.chihuo.bussiness.Recipe;
import com.chihuo.bussiness.Restaurant;

public interface RecipeDao extends GenericDao<Recipe, Integer> {
	public List<Recipe> findByRestaurant(Restaurant r);

	public Recipe findByIdInRestaurant(Restaurant r, int id);

	public List<Recipe> findByCategory(Category r);
}
