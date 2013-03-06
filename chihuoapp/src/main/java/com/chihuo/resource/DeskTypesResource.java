package com.chihuo.resource;

import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.DeskType;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.service.DeskTypeService;
import com.sun.jersey.multipart.FormDataParam;

@Component
public class DeskTypesResource {
	Restaurant restaurant;

	@Autowired
	DeskTypeService service;

	@GET
	@Produces("application/json; charset=UTF-8")
	public List<DeskType> get() {
		return service.findByRestaurant(restaurant);
	}

	@Path("{id}")
	@GET
	@Produces("application/json; charset=UTF-8")
	public DeskType getSingle(@PathParam("id") int id) {
		DeskType c = service.findByIdInRestaurant(restaurant, id);
		checkNull(c);

		return c;
	}

	@POST
	@RolesAllowed({ "OWER" })
	@Consumes("multipart/form-data")
	public Response create(@FormDataParam("name") String name) {
		DeskType d = service.createOrUpdate(name, restaurant, new DeskType());

		return Response.created(URI.create(String.valueOf(d.getId()))).build();
	}

	@Path("{id}")
	@POST
	@RolesAllowed({ "OWER" })
	@Consumes("multipart/form-data")
	public Response update(@PathParam("id") int id,
			@FormDataParam("name") String name) {
		DeskType c = service.findByIdInRestaurant(restaurant, id);
		checkNull(c);

		service.createOrUpdate(name, restaurant, c);

		return Response.status(Response.Status.OK).build();
	}

	@Path("{id}")
	@DELETE
	@RolesAllowed({ "OWER" })
	public void delete(@PathParam("id") int id) {
		DeskType c = service.findByIdInRestaurant(restaurant, id);
		checkNull(c);

		service.delete(c);
	}

	private void checkNull(DeskType c) {
		if (c == null || c.getStatus() == -1) {
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
