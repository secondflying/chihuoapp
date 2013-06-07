package com.chihuo.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chihuo.bussiness.Category;
import com.chihuo.bussiness.Recipe;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.RecipeDao;
import com.chihuo.util.PinyinUtil;
import com.chihuo.util.PublicHelper;
import com.sun.jersey.core.header.FormDataContentDisposition;

@Service
@Transactional
public class RecipeService {
	@Autowired
	private RecipeDao dao;

	public List<Recipe> findByCategory(Category category) {
		return dao.findByCategory(category);
	}

	public List<Recipe> findByRestaurant(Restaurant restaurant) {
		return dao.findByRestaurant(restaurant);
	}
	
	public Recipe findByIdInRestaurant(Restaurant restaurant, int id) {
		return dao.findByIdInRestaurant(restaurant, id);
	}

	public Recipe createOrUpdate(Recipe recipe) {
		dao.saveOrUpdate(recipe);
		return recipe;
	}

	public void delete(Recipe recipe) {
		recipe.setStatus(-1);
		dao.saveOrUpdate(recipe);
	}
}
