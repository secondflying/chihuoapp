package com.chihuo.resource;

import java.io.InputStream;
import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.Category;
import com.chihuo.bussiness.Recipe;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.service.CategoryService;
import com.chihuo.service.RecipeService;
import com.sun.jersey.api.core.ResourceContext;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Component
public class RecipesResource {
	private Restaurant restaurant;

	@Context
	ResourceContext resourceContext;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	RecipeService recipeService;
	

	@GET
	@Produces("application/json; charset=UTF-8")
	public Response getRecipes(@DefaultValue("-1") @QueryParam("cid") int cid) {
		if (cid != -1) {
			Category category = categoryService.findByIdInRestaurant(restaurant, cid);
			if (category == null || category.getStatus() == -1) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("种类ID不存在").type(MediaType.TEXT_PLAIN).build();
			}
			
			List<Recipe> list = recipeService.findByCategory(category);
			GenericEntity<List<Recipe>> entity = new GenericEntity<List<Recipe>>(list) {};
			return Response.status(Response.Status.OK)
					.entity(entity).build();
		}else {
			List<Recipe> list = recipeService.findByRestaurant(restaurant);
			GenericEntity<List<Recipe>> entity = new GenericEntity<List<Recipe>>(list) {};
			return Response.status(Response.Status.OK)
					.entity(entity).build();
		}
	}

	@POST
	@RolesAllowed({"OWER"})
	@Consumes("multipart/form-data")
	public Response create(@FormDataParam("name") String name,
			@FormDataParam("price") Double price,
			@FormDataParam("description") String description,
			@DefaultValue("-1") @FormDataParam("cid") int cid,
			@FormDataParam("image") InputStream upImg,
		@FormDataParam("image") FormDataContentDisposition fileDetail) {

		Category category = null;
		if (cid != -1) {
			 category = categoryService.findByIdInRestaurant(restaurant, cid);
			if (category == null || category.getStatus() == -1) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("种类ID不存在").type(MediaType.TEXT_PLAIN).build();
			}
		}
		
		Recipe recipe = recipeService.createOrUpdate(name, price,description, upImg, fileDetail, category,restaurant, new Recipe());


		return Response.created(URI.create(String.valueOf(recipe.getId())))
				.build();
	}

	@Path("{id}")
	public RecipeResource getSingleResource(@PathParam("id") int id) {
		Recipe c = recipeService.findByIdInRestaurant(restaurant,id);
		checkNull(c);
		
		RecipeResource resource = resourceContext.getResource(RecipeResource.class);
		resource.setRestaurant(restaurant);
		resource.setRecipe(c);
		
		return resource;
	}
	
	private void checkNull(Recipe c){
		if (c == null ||c.getStatus() == -1) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
	
	
	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
}
