package com.chihuo.resource;

import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.Desk;
import com.chihuo.bussiness.Device;
import com.chihuo.bussiness.Order;
import com.chihuo.bussiness.OrderItem;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.User;
import com.chihuo.bussiness.Waiter;
import com.chihuo.service.DeskService;
import com.chihuo.service.DeviceService;
import com.chihuo.service.OrderService;
import com.chihuo.service.UserService;
import com.chihuo.service.WaiterService;
import com.chihuo.util.CodeUserType;
import com.sun.jersey.api.core.ResourceContext;
import com.sun.jersey.multipart.FormDataParam;

@Component
public class OrdersResource {
	Restaurant restaurant;
	
	@Context
	private ResourceContext resourceContext;

	@Autowired
	private OrderService orderService;

	@Autowired
	private DeskService deskService;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private WaiterService waiterService;
	
	@Autowired
	private UserService userService;

	@GET
	@RolesAllowed({ "USER,OWNER,WAITER" })
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Order> getMyOrder(@Context SecurityContext securityContext) {
		return orderService.findByRestaurant(restaurant);
	}

	// 开台
	@POST
	@RolesAllowed({ "WAITER" })
	@Consumes("multipart/form-data")
	public Response create(@FormDataParam("did") int did,
			@FormDataParam("number") int number,
			@Context HttpServletRequest request,
			@Context SecurityContext securityContext) {

		// 判断桌子是否能开台
		Desk d = deskService.findByIdInRestaurant(this.restaurant, did);
		if (d == null) {
			return Response.status(Response.Status.NOT_FOUND)
					.entity("桌号为" + did + "的桌子不存在").type(MediaType.TEXT_PLAIN)
					.build();
		} else if (!orderService.isDeskCanOrder(d.getId())) {
			return Response.status(Response.Status.CONFLICT)
					.entity("桌号为" + did + "的桌子不可开台").type(MediaType.TEXT_PLAIN)
					.build();
		}

		Waiter u = waiterService.getLoginWaiter(securityContext);

		Order order = orderService.createOrder(d, number, restaurant, u);

		String udid = request.getHeader("X-device");
		if (!StringUtils.isBlank(udid)) {
			Device device = deviceService.findByUDID(udid);
			deviceService.recordLogin(order, device, u.getId(),
					CodeUserType.WAITER);
		}

		return Response.created(URI.create(String.valueOf(order.getId())))
				.entity(order).type(MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("code/{code}")
	// @RolesAllowed({"USER,OWNER,WAITER"})
	@Produces("application/json; charset=UTF-8")
	public Response joinOrder(@PathParam("code") String code,
			@Context HttpServletRequest request,
			@Context SecurityContext securityContext) {
		// 用户加入点餐
		Order order = orderService.findByCode(restaurant, code);
		if (order == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("编码错误")
					.type(MediaType.TEXT_PLAIN).build();
		}

		String udid = request.getHeader("X-device");
		if (!StringUtils.isBlank(udid)) {
			Device device = deviceService.findByUDID(udid);
			User u = userService.getLoginUser(securityContext);
			if (u != null) {
				deviceService.recordLogin(order, device, u.getId(),
						CodeUserType.USER);
			} else {
				deviceService.recordLogin(order, device, -1,
						CodeUserType.ANONYMOUS);
			}
		}

		List<OrderItem> list = orderService.queryOrderItems(order.getId());
		order.setOrderItems(list);

		return Response.status(Response.Status.OK).entity(order)
				.type(MediaType.APPLICATION_JSON).build();
	}

	@Path("{id}")
	public OrderResource getSingleResource(@PathParam("id") int id) {
		Order c = orderService.findByIdInRestaurant(this.restaurant, id);
		checkNull(c);
		
		OrderResource resource = resourceContext.getResource(OrderResource.class);
		resource.setRestaurant(restaurant);
		resource.setOrder(c);
		return resource;
	}

	private void checkNull(Order c) {
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
