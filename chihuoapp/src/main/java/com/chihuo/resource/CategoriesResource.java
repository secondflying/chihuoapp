package com.chihuo.resource;

import java.io.InputStream;
import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.Category;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.service.CategoryService;
import com.sun.jersey.api.core.ResourceContext;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Component
public class CategoriesResource {
	private Restaurant restaurant;
	
	@Context
	ResourceContext resourceContext;
	
	@Autowired
	CategoryService service;
	

	@GET
	@Produces("application/json; charset=UTF-8")
	public List<Category> getCategories() {
		return service.findByRestaurant(restaurant);
	}

	@POST
	@RolesAllowed({"OWNER"})
	@Consumes("multipart/form-data")
	public Response create(@FormDataParam("name") String name,
			@FormDataParam("description") String description,
			@FormDataParam("image") InputStream upImg,
			@FormDataParam("image") FormDataContentDisposition fileDetail) {

		Category category = service.createOrUpdate(name, description, upImg, fileDetail, restaurant, new Category());

		return Response.created(URI.create(String.valueOf(category.getId())))
				.build();
	}

	@Path("{id}")
	public CategoryResource getSingleResource(@PathParam("id") int id) {
		Category category = service.findByIdInRestaurant(restaurant, id);
		
		CategoryResource resource = resourceContext.getResource(CategoryResource.class);
		resource.setRestaurant(restaurant);
		resource.setCategory(category);
		
		return resource;
	}
	
	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

}
