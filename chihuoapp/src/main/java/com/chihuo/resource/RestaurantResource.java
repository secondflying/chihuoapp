package com.chihuo.resource;

import java.io.InputStream;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.Owner;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.User;
import com.chihuo.service.OwnerService;
import com.chihuo.service.RestaurantService;
import com.chihuo.service.UserService;
import com.sun.jersey.api.core.ResourceContext;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Component
public class RestaurantResource {
	private Restaurant restaurant;
	
	@Context
	ResourceContext resourceContext;
	
	@Autowired
	RestaurantService service;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OwnerService ownerService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Restaurant getSingle() {
		return restaurant;
	}

	@POST
	@RolesAllowed({ "OWNER" })
	@Consumes("multipart/form-data")
	public Response update(@FormDataParam("name") String name,
			@FormDataParam("telephone") String telephone,
			@FormDataParam("address") String address,
			@DefaultValue("-1000") @FormDataParam("x") double x,
			@DefaultValue("-1000") @FormDataParam("y") double y,
			@FormDataParam("image") InputStream upImg,
			@FormDataParam("image") FormDataContentDisposition fileDetail,
			@Context SecurityContext securityContext) {

		Owner owner = ownerService.getLoginOwner(securityContext);
		service.update(name, telephone, address, x, y, upImg, fileDetail, owner, restaurant);

		return Response.status(Response.Status.OK).build();
	}

	@DELETE
	@RolesAllowed({ "OWNER" })
	public void delete() {
		service.delete(restaurant);
	}

	@PUT
	@RolesAllowed({ "ADMIN" })
	@Path("/verify")
	public void verify() {
		service.verify(restaurant);
	}

	@PUT
	@RolesAllowed({ "ADMIN" })
	@Path("/noverify")
	public void notverify() {
		service.notverify( restaurant);
	}

	@Path("/categories")
	public CategoriesResource getCategorie() {
		CategoriesResource resource = resourceContext.getResource(CategoriesResource.class);
		resource.setRestaurant(restaurant);
		return resource;
	}

	@Path("/recipes")
	public RecipesResource getRecipes() {
		
		RecipesResource resource = resourceContext.getResource(RecipesResource.class);
		resource.setRestaurant(restaurant);
		return resource;
	}

	@Path("/desktypes")
	public DeskTypesResource getDeskTypeResource() {
		DeskTypesResource resource = resourceContext.getResource(DeskTypesResource.class);
		resource.setRestaurant(restaurant);
		return resource;
	}

	@Path("/desks")
	public DesksResource getDeskResource() {
		DesksResource resource = resourceContext.getResource(DesksResource.class);
		resource.setRestaurant(restaurant);
		return resource;
	}

	@Path("/orders")
	public OrdersResource getOrderResource() {
		OrdersResource resource = resourceContext.getResource(OrdersResource.class);
		resource.setRestaurant(restaurant);
		return resource;
	}
	
	@Path("/waiters")
	public WaitersResource getWaiterResource() {
		WaitersResource resource = resourceContext.getResource(WaitersResource.class);
		resource.setRestaurant(restaurant);
		return resource;
	}
	
	
	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

}
