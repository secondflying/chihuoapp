package com.chihuo.resource;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.Order;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.Waiter;
import com.chihuo.service.OrderService;
import com.chihuo.service.RestaurantService;
import com.chihuo.service.WaiterService;
import com.chihuo.util.CodeUserType;
import com.chihuo.util.PublicHelper;

@Component
@Path("/waiter")
public class MyWaiterResource {

	@Autowired
	WaiterService waiterService;

	@Autowired
	OrderService orderService;
	
	@Autowired
	RestaurantService restaurantService;

	//获取我开台的列表
	@GET
	@RolesAllowed({ "WAITER" })
	@Path("/orders")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Order> getMyOrder(@PathParam("rid") int id,
			@Context SecurityContext securityContext) {
		Waiter user = waiterService.getLoginWaiter(securityContext);
		return orderService.findByWaiter(user);
	}
	
	@POST
	@Path("/login")
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
		
		//TODO 记录当前设备登录情况
		//记录账号和设备之间的关系
		//相同账号 不同设备
		//不同账号 同一设备

		return Response
				.ok(String.format("{\"rid\":\"%s\",\"wid\":\"%s\"}",
						restaurant2.getId(), u.getId()))
				.header("Authorization",
						PublicHelper.encryptUser(u.getId(), u.getPassword(), CodeUserType.WAITER))
				.build();
	}

}
