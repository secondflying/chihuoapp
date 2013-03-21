package com.chihuo.resource;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.Device;
import com.chihuo.bussiness.Favorite;
import com.chihuo.bussiness.History;
import com.chihuo.bussiness.User;
import com.chihuo.service.DeviceService;
import com.chihuo.service.FavoriteService;
import com.chihuo.service.UserService;
import com.chihuo.util.CodeUserType;
import com.chihuo.util.PublicHelper;

@Component
@Path("/user")
public class MyUserResource {
	@Autowired
	private UserService userService;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private FavoriteService favoriteService;

	@GET
	@Path("/history")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<History> getMyHistory(@Context HttpServletRequest request,
			@Context SecurityContext securityContext) {
		User user = userService.getLoginUser(securityContext);

		Device device = null;
		String udid = request.getHeader("X-device");
		if (!StringUtils.isBlank(udid)) {
			device = deviceService.findByUDID(udid);
		}
		return deviceService.getHistoryOrder(user, device);
	}

	@GET
	@RolesAllowed({ "USER" })
	@Path("/favorites")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Favorite> getFavorites(@Context SecurityContext securityContext) {
		User owner = userService.getLoginUser(securityContext);
		if (owner == null) {
			throw new WebApplicationException(Response
					.status(Response.Status.BAD_REQUEST).entity("用户不存在")
					.type(MediaType.TEXT_PLAIN).build());
		}
		return favoriteService.findByUser(owner.getId());
	}

	@DELETE
	@RolesAllowed({ "USER" })
	@Path("/favorites/{fid}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteFavorite(@PathParam("fid") Integer fid,
			@Context SecurityContext securityContext) {
		User owner = userService.getLoginUser(securityContext);
		if (owner == null) {
			throw new WebApplicationException(Response
					.status(Response.Status.BAD_REQUEST).entity("用户不存在")
					.type(MediaType.TEXT_PLAIN).build());
		}
		favoriteService.delete(fid);
	}

	@GET
	@RolesAllowed({ "USER" })
	@Path("/userinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public User get(@Context SecurityContext securityContext) {
		User owner = userService.getLoginUser(securityContext);
		if (owner == null) {
			throw new WebApplicationException(Response
					.status(Response.Status.BAD_REQUEST).entity("用户不存在")
					.type(MediaType.TEXT_PLAIN).build());
		}

		return owner;
	}

	@POST
	@Path("/login")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@FormParam("username") String username,
			@FormParam("password") String password) {

		User u = userService.findByNameAndPassword(username, password);
		if (u == null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("用户名密码不匹配").type(MediaType.TEXT_PLAIN).build();
		}

		String encry = PublicHelper.encryptUser(u.getId(), u.getPassword(),
				CodeUserType.USER);
		return Response
				.ok(u)
				.cookie(new NewCookie(new javax.ws.rs.core.Cookie(
						"Authorization", encry), "用户名",
						NewCookie.DEFAULT_MAX_AGE, false))
				.header("Authorization", encry).build();
	}

	@POST
	@Path("/register")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(@FormParam("username") String username,
			@FormParam("password") String password) {
		User user = userService.findByName(username);
		if (user != null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("该用户已存在").type(MediaType.TEXT_PLAIN).build();
		}

		User u = userService.create(username, password);

		String encry = PublicHelper.encryptUser(u.getId(), u.getPassword(),
				CodeUserType.USER);
		return Response
				.ok(u)
				.cookie(new NewCookie(new javax.ws.rs.core.Cookie(
						"Authorization", encry), "用户名",
						NewCookie.DEFAULT_MAX_AGE, false))
				.header("Authorization", encry).build();
	}

}
