package com.chihuo.resource;

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

import com.chihuo.bussiness.Desk;
import com.chihuo.bussiness.DeskStatusView;
import com.chihuo.bussiness.DeskType;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.service.DeskService;
import com.chihuo.service.DeskTypeService;
import com.sun.jersey.api.core.ResourceContext;
import com.sun.jersey.multipart.FormDataParam;

@Component
public class DesksResource {
	private Restaurant restaurant;

	@Context
	ResourceContext resourceContext;
	
	@Autowired
	DeskService deskService;
	
	@Autowired
	DeskTypeService typeService;

	@GET
	@Produces("application/json; charset=UTF-8")
	public Response get(@DefaultValue("-1") @QueryParam("tid") int tid) {
		if (tid != -1) {
			DeskType dtype = typeService.findByIdInRestaurant(restaurant, tid);
			if (dtype == null || dtype.getStatus() == -1) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("桌子类型不存在").type(MediaType.TEXT_PLAIN).build();
			}
			
			List<DeskStatusView> list = deskService.findByType(dtype);
			GenericEntity<List<DeskStatusView>> entity = new GenericEntity<List<DeskStatusView>>(list) {};
			return Response.status(Response.Status.OK)
					.entity(entity).build();
		}else {
			List<DeskStatusView> list = deskService.findByRestaurant(restaurant);
			GenericEntity<List<DeskStatusView>> entity = new GenericEntity<List<DeskStatusView>>(list) {};
			return Response.status(Response.Status.OK)
					.entity(entity).build();
		}
	}
	
	@POST
	@RolesAllowed({"OWNER"})
	@Consumes("multipart/form-data")
	public Response create(@FormDataParam("name") String name,
			@FormDataParam("capacity") int capacity,
			@DefaultValue("-1") @FormDataParam("tid") int tid) {

		DeskType category = null;
		if (tid != -1) {
			 category = typeService.findByIdInRestaurant(restaurant, tid);
			if (category == null || category.getStatus() == -1) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("餐桌类型不存在").type(MediaType.TEXT_PLAIN).build();
			}
		}
		
		Desk desk = deskService.createOrUpdate(name, capacity, category,restaurant, new Desk());

		return Response.created(URI.create(String.valueOf(desk.getId())))
				.build();
	}

	@Path("{id}")
	public DeskResource getSingleResource(@PathParam("id") int id) {
		Desk c = deskService.findByIdInRestaurant(restaurant, id);
		checkNull(c);
		
		DeskResource resource = resourceContext.getResource(DeskResource.class);
		resource.setRestaurant(restaurant);
		resource.setDesk(c);
		
		return resource;
	}
	
	private void checkNull(Desk c){
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
