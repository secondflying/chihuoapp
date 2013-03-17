package com.chihuo.resource;

import java.io.InputStream;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.Category;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.service.CategoryService;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Component
public class CategoryResource {
	private Restaurant restaurant;
	private Category category;

	@Autowired
	CategoryService service;

	@GET
	@Produces("application/json; charset=UTF-8")
	public Category get() {
		return category;
	}

	@POST
	@RolesAllowed({ "OWNER" })
	@Consumes("multipart/form-data")
	public Response update(@FormDataParam("name") String name,
			@FormDataParam("description") String description,
			@FormDataParam("image") InputStream upImg,
			@FormDataParam("image") FormDataContentDisposition fileDetail) {

		service.createOrUpdate(name, description, upImg, fileDetail,
				restaurant, category);
		return Response.status(Response.Status.OK).build();
	}

	@DELETE
	@RolesAllowed({ "OWNER" })
	public void delete() {
		service.delete(category);
	}

	
	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
