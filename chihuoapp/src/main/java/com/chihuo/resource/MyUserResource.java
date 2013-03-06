package com.chihuo.resource;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.Order;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.User;
import com.chihuo.bussiness.Waiter;
import com.chihuo.service.OrderService;
import com.chihuo.service.RestaurantService;
import com.chihuo.service.UserService;
import com.chihuo.service.WaiterService;

@Component
@Path("/user")
public class MyUserResource {
	@Autowired
	UserService userService;
	
	@Autowired 
	WaiterService waiterService;
	
	@Autowired
	RestaurantService restaurantService;
	
	@Autowired 
	OrderService orderService;
	
	@GET
	@RolesAllowed({"OWER"})
	@Path("/restaurants")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Restaurant> getMyRestaurants(@Context SecurityContext securityContext) {
		User user = userService.getLoginUser(securityContext);
		return restaurantService.findByUser(user);
	}
	
	@GET
	@RolesAllowed({"WAITER"})
	@Path("/restaurants/{rid}/orders")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Order> getMyOrder(@PathParam("rid") int id,@Context SecurityContext securityContext) {
		Restaurant r = restaurantService.findById(id);
		if (r == null || r.getStatus() == -1) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		Waiter user = waiterService.getLoginWaiter(securityContext);
		return orderService.findByWaiter(r,user);
	}
}
