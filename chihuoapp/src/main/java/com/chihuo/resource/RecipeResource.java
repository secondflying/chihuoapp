package com.chihuo.resource;

import java.io.InputStream;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.Category;
import com.chihuo.bussiness.Recipe;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.service.CategoryService;
import com.chihuo.service.RecipeService;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Component
public class RecipeResource {
	Restaurant restaurant;
	Recipe recipe;

	@Autowired
	CategoryService categoryService;

	@Autowired
	RecipeService recipeService;

	@GET
	@Produces("application/json; charset=UTF-8")
	public Recipe get() {
		return recipe;
	}

//	@POST
//	@RolesAllowed({ "OWNER" })
//	@Consumes("multipart/form-data")
//	public Response update(@FormDataParam("name") String name,
//			@FormDataParam("price") Double price,
//			@FormDataParam("description") String description,
//			@DefaultValue("-1") @FormDataParam("cid") int cid,
//			@FormDataParam("image") InputStream upImg,
//			@FormDataParam("image") FormDataContentDisposition fileDetail) {
//
//		Category category = null;
//		if (cid != -1) {
//			category = categoryService.findByIdInRestaurant(restaurant, cid);
//			if (category == null || category.getStatus() == -1) {
//				return Response.status(Response.Status.BAD_REQUEST)
//						.entity("种类ID不存在").type(MediaType.TEXT_PLAIN).build();
//			}
//		}
//
//		recipeService.createOrUpdate(name, price, description, upImg, category,
//				restaurant, recipe);
//
//		return Response.status(Response.Status.OK).build();
//	}

	@DELETE
	@RolesAllowed({ "OWNER" })
	public void delete() {
		recipeService.delete(recipe);
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

}
