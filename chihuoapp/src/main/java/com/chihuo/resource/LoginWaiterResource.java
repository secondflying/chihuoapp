package com.chihuo.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.Waiter;
import com.chihuo.service.RestaurantService;
import com.chihuo.service.WaiterService;
import com.chihuo.util.PublicHelper;

@Component
@Path("/wlogin")
public class LoginWaiterResource {
	@Autowired
	RestaurantService restaurantService;

	@Autowired
	WaiterService waiterService;

	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response waiterLogin(@FormParam("restaurant") String code,
			@FormParam("username") String username,
			@FormParam("password") String password) {
		Restaurant restaurant2 = restaurantService.findById(Integer.parseInt(code));
		if (restaurant2 == null || restaurant2.getStatus() != 1) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("餐厅编码错误").type(MediaType.TEXT_PLAIN).build();
		}

		Waiter u = waiterService.findByNameAndPassword(username, password, restaurant2);
		if (u == null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("用户名密码不匹配").type(MediaType.TEXT_PLAIN).build();
		}

		return Response
				.ok(String.format("{\"rid\":\"%s\",\"wid\":\"%s\"}",
						restaurant2.getId(), u.getId()))
				.header("Authorization",
						PublicHelper.encryptUser(u.getId(), u.getPassword(), 3))
				.build();
	}
}
