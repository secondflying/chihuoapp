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

	public Recipe createOrUpdate(String name, Double price, String description,
			InputStream upImg, FormDataContentDisposition fileDetail,
			Category category, Restaurant restaurant, Recipe recipe) {

		recipe.setName(name);
		recipe.setPrice(price);
		recipe.setDescription(description);
		recipe.setRestaurant(restaurant);
		recipe.setStatus(0);
		recipe.setCategory(category);
		
		if (upImg != null && !StringUtils.isEmpty(fileDetail.getFileName())) {
			try {
				String image = PublicHelper.saveImage(upImg);
				recipe.setImage(image);

			} catch (IOException e) {
				throw new WebApplicationException(Response
						.status(Response.Status.BAD_REQUEST).entity("保存图片失败")
						.type(MediaType.TEXT_PLAIN).build());
			}
		}

		dao.saveOrUpdate(recipe);
		return recipe;
	}

	public void delete(Recipe recipe) {
		recipe.setStatus(-1);
		dao.saveOrUpdate(recipe);
	}
}
