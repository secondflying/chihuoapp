package com.chihuo.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
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

import com.chihuo.bussiness.Device;
import com.chihuo.bussiness.Order;
import com.chihuo.bussiness.OrderItem;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.User;
import com.chihuo.service.DeviceService;
import com.chihuo.service.OrderService;
import com.chihuo.service.RestaurantService;
import com.chihuo.service.UserService;
import com.chihuo.util.CodeUserType;

@Component
@Path("/QR")
public class QRCodeResource {

	@Autowired
	RestaurantService restaurantService;

	@Autowired
	OrderService orderService;

	@Autowired
	DeviceService deviceService;

	@Autowired
	UserService userService;

	@Path("{qr}")
	@GET
	@Produces("application/json; charset=UTF-8")
	public Response joinOrder(@PathParam("qr") String qrcode,
			@Context HttpServletRequest request,
			@Context SecurityContext securityContext) {
		String[] tmp = qrcode.split("_");

		int rid = Integer.parseInt(tmp[0]);
		int oid = Integer.parseInt(tmp[1]);

		Restaurant r = restaurantService.findById(rid);
		checkNull(r);

		Order order = orderService.findByIdInRestaurant(r, oid);
		if (order == null || order.getStatus() != 1) {
			return Response.status(Response.Status.NOT_FOUND).entity("二维码错误")
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

	private void checkNull(Restaurant c) {
		if (c == null || c.getStatus() == -1) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
}
