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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.User;
import com.chihuo.service.RestaurantService;
import com.chihuo.service.UserService;
import com.sun.jersey.api.core.ResourceContext;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Component
@Path("/restaurants")
public class RestaurantsResource {

	@Context
	private ResourceContext resourceContext;

	@Autowired
	private RestaurantService service;

	@Autowired
	private UserService userService;

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Restaurant> get() {
		return service.getVerified();
	}

	@Path("/around")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Restaurant> getAround(@QueryParam("x") double x,
			@QueryParam("y") double y, @QueryParam("distance") double distance) {

		return service.findAround(x, y, distance);
	}

	@POST
	@RolesAllowed({ "OWER" })
	@Consumes("multipart/form-data")
	public Response create(@FormDataParam("name") String name,
			@FormDataParam("telephone") String telephone,
			@FormDataParam("address") String address,
			@DefaultValue("-1000") @FormDataParam("x") double x,
			@DefaultValue("-1000") @FormDataParam("y") double y,
			@FormDataParam("image") InputStream upImg,
			@FormDataParam("image") FormDataContentDisposition fileDetail,
			@Context SecurityContext securityContext) {

		User loginUser = userService.getLoginUser(securityContext);
		Restaurant r = service.create(name, telephone, address, x, y, upImg,
				fileDetail, loginUser);

		return Response.created(URI.create(String.valueOf(r.getId()))).build();
	}

	@Path("{id}")
	public RestaurantResource getRestaurant(@PathParam("id") int id) {
		Restaurant r = service.findById(id);

		RestaurantResource resource = resourceContext
				.getResource(RestaurantResource.class);
		resource.setRestaurant(r);
		return resource;
	}

	@GET
	@RolesAllowed({ "ADMIN" })
	@Path("/all")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Restaurant> getAll() {
		return service.findNotDeleted();
	}

	@GET
	@RolesAllowed({ "ADMIN" })
	@Path("/toverify")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Restaurant> getToVerify() {
		return service.getToVerify();
	}

	@GET
	@RolesAllowed({ "ADMIN" })
	@Path("/verified")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Restaurant> getVerified() {
		return service.getVerified();
	}

	@GET
	@RolesAllowed({ "ADMIN" })
	@Path("/notverified")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Restaurant> getNotVerified() {
		return service.getNotVerified();
	}

}
