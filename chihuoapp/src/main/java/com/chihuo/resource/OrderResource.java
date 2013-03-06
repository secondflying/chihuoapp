package com.chihuo.resource;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.Device;
import com.chihuo.bussiness.Order;
import com.chihuo.bussiness.OrderItem;
import com.chihuo.bussiness.Recipe;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.service.DeviceService;
import com.chihuo.service.NotificationService;
import com.chihuo.service.OrderService;
import com.chihuo.service.RecipeService;
import com.chihuo.service.UserService;
import com.chihuo.service.WaiterService;
import com.chihuo.util.CodeNotificationType;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

@Component
public class OrderResource {
	Restaurant restaurant;
	Order order;

	@Autowired
	OrderService orderService;

	@Autowired
	RecipeService recipeService;

	@Autowired
	DeviceService deviceService;

	@Autowired
	WaiterService waiterService;

	@Autowired
	UserService userService;

	@Autowired
	NotificationService notificationService;

	@GET
	// @RolesAllowed({ "USER,OWER,WAITER" })
	@Produces("application/json; charset=UTF-8")
	public Order get() {
		List<OrderItem> list = orderService.queryOrderItems(order.getId());

		order.setOrderItems(list);
		return order;
	}
	
	@Path("QR")
	@GET
	@Produces("image/png")
	public StreamingOutput getQRCode() {
		if (order.getStatus() != 1) {
			throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity("二维码错误")
					.type(MediaType.TEXT_PLAIN).build());
		}
		
		return new StreamingOutput() {
			@Override
			public void write(OutputStream output) throws IOException,
					WebApplicationException {
				String str = restaurant.getId() + "_" + order.getId();// 二维码内容  
				try {
					BitMatrix byteMatrix = new MultiFormatWriter().encode(new String(str.getBytes("GBK"),"iso-8859-1"),  
					        BarcodeFormat.QR_CODE, 200, 200);
					MatrixToImageWriter.writeToStream(byteMatrix, "png", output); 
				} catch (WriterException e) {
					e.printStackTrace();
				}  
			}
		};
	}

	// 加减菜
	@POST
	// @RolesAllowed({ "USER,OWER,WAITER" })
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@FormParam("rid") int rid,
			@FormParam("count") int count, @Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@Context SecurityContext securityContext) {

		if (order.getStatus() != null && order.getStatus() != 1) {
			// TODO 判断该台号是否可以加减菜
			return Response.status(Response.Status.CONFLICT)
					.entity("桌号为" + order.getDesk().getId() + "的桌子不可加减菜")
					.type(MediaType.TEXT_PLAIN).build();
		}

		Recipe recipe = recipeService.findByIdInRestaurant(restaurant, rid);
		if (recipe == null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("编号为" + rid + "的菜不存在").type(MediaType.TEXT_PLAIN)
					.build();
		}

		orderService.addMenu(order, recipe, count);

		// 发送通知给服务员和其他点餐者
		Device waiterDevice = deviceService.getWaiterDeviceByOrder(order);
		List<Device> userDevices = deviceService
				.getAnonymousDeviceByOrder(order);

		notificationService.sendMessageToWaiter(order.getId().toString(),
				CodeNotificationType.AddMenu, waiterDevice);

		String udid = request.getHeader("X-device");
		for (Device device : userDevices) {
			if (!device.getDeviceid().equals(udid)) {
				notificationService.sendNotifcationToUser(order.getId()
						.toString(), CodeNotificationType.AddMenu, device);
			}
		}

		return Response.status(Response.Status.OK).entity(order)
				.type(MediaType.APPLICATION_JSON).build();

		// URI uri = uriInfo.getRequestUri();
		// // UriBuilder ub = uriInfo.getAbsolutePathBuilder();
		// // URI listUri = ub.path("list").build();
		// return Response.seeOther(uri).build();
	}

	// 改变菜的状态，如已上，
	@Path("{iid}")
	@PUT
	@RolesAllowed({ "OWER,WAITER" })
	@Produces(MediaType.APPLICATION_JSON)
	public Response alterOrderItemStatus(@PathParam("iid") int iid) {

		OrderItem oi = orderService.findByIdInOrder(order.getId(), iid);
		if (oi == null || oi.getStatus() == -1) {
			return Response.status(Response.Status.NOT_FOUND)
					.entity("ID为" + iid + "的已点菜不存在").type(MediaType.TEXT_PLAIN)
					.build();
		}

		oi = orderService.giveItemToClient(oi);

		// 发送通知给服客户
		List<Device> userDevices = deviceService
				.getAnonymousDeviceByOrder(order);
		for (Device device : userDevices) {
			notificationService.sendNotifcationToUser(oi.getOrder().getId()
					.toString(), CodeNotificationType.AlterMenu, device);
		}

		return Response.status(Response.Status.OK).entity(oi)
				.type(MediaType.APPLICATION_JSON).build();
	}

	// 其他服务，呼叫服务员等
	@Path("/assistent")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response AssistentHelp(@FormParam("type") String msg,
			@Context UriInfo uriInfo, @Context HttpServletRequest request,
			@Context SecurityContext securityContext) {

		Device waiterDevice = deviceService.getWaiterDeviceByOrder(order);

		notificationService.sendNotificationToWaiter(msg, order.getDesk()
				.getName(), order.getId(), waiterDevice);

		return Response.status(Response.Status.OK)
				.type(MediaType.APPLICATION_JSON).build();
	}
	

	// 下单
	@Path("/deposit")
	@PUT
	// @RolesAllowed({ "USER,OWER,WAITER" })
	@Produces(MediaType.APPLICATION_JSON)
	public Response deposit() {
		order = orderService.deposit(order);

		return Response.status(Response.Status.OK).entity(order)
				.type(MediaType.APPLICATION_JSON).build();
	}

	// 请求结账
	@Path("/tocheck")
	@PUT
	// @RolesAllowed({ "USER,OWER,WAITER" })
	@Produces(MediaType.APPLICATION_JSON)
	public Response tocheck() {
		order = orderService.toCheckOrder(order);

		Device waiterDevice = deviceService.getWaiterDeviceByOrder(order);
		notificationService.sendNotificationToWaiter("结账", order.getDesk()
				.getName(), order.getId(), waiterDevice);

		return Response.status(Response.Status.OK).entity(order)
				.type(MediaType.APPLICATION_JSON).build();
	}

	// 结账
	@Path("/check")
	@PUT
	@RolesAllowed({ "OWER,WAITER" })
	@Produces(MediaType.APPLICATION_JSON)
	public Response check() {
		order = orderService.checkOrder(order);

		return Response.status(Response.Status.OK).entity(order)
				.type(MediaType.APPLICATION_JSON).build();
	}

	// 撤单
	@Path("/cancel")
	@PUT
	@RolesAllowed({ "OWER,WAITER" })
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancel() {
		order = orderService.cancelOrder(order);

		return Response.status(Response.Status.OK).entity(order)
				.type(MediaType.APPLICATION_JSON).build();
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
