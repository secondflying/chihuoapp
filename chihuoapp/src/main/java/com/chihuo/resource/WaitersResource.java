package com.chihuo.resource;

import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.Waiter;
import com.chihuo.service.WaiterService;

@Component
public class WaitersResource {
	private Restaurant restaurant;

	@Autowired
	WaiterService service;

	@GET
	@Produces("application/json; charset=UTF-8")
	public List<Waiter> get() {
		return service.findByRestaurant(restaurant);
	}

	@Path("{id}")
	@RolesAllowed({ "OWER" })
	@GET
	@Produces("application/json; charset=UTF-8")
	public Waiter getSingle(@PathParam("id") int id) {
		Waiter c = service.findByIdInRestaurant(restaurant, id);
		checkNull(c);

		return c;
	}

	@POST
	@RolesAllowed({ "OWER" })
	@Consumes("application/x-www-form-urlencoded")
	public Response create(@FormParam("name") String name,
			@FormParam("password") String password) {

		Waiter waiter = service.findByName(name, restaurant);
		if (waiter != null) {
			return Response.status(Response.Status.CONFLICT).entity("已存在该名称")
					.type(MediaType.TEXT_PLAIN).build();
		}

		waiter = service.createOrUpdate(name, password, restaurant,
				new Waiter());

		return Response.created(URI.create(String.valueOf(waiter.getId())))
				.build();
	}

	@POST
	@Path("{id}")
	@RolesAllowed({ "OWER" })
	@Consumes("application/x-www-form-urlencoded")
	public void updateone(@PathParam("id") int id,
			@FormParam("name") String name,
			@FormParam("password") String password) {
		Waiter c = service.findByIdInRestaurant(restaurant, id);
		checkNull(c);

		service.createOrUpdate(name, password, restaurant, c);

	}

	@Path("{id}")
	@RolesAllowed({ "OWER" })
	@DELETE
	public void delete(@PathParam("id") int id) {
		Waiter c = service.findByIdInRestaurant(restaurant, id);
		checkNull(c);

		service.delete(c);
	}

	private void checkNull(Waiter c) {
		if (c == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		if (c.getStatus() == -1) {
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
