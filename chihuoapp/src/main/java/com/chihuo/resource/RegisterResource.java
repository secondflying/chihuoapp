package com.chihuo.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.User;
import com.chihuo.service.UserService;
import com.chihuo.util.CodeUserType;
import com.chihuo.util.PublicHelper;

@Component
@Path("/register")
public class RegisterResource {
	@Autowired
	UserService userService;

	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("utype") int utype) {
		User user = userService.findByName(username);
		if (user != null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("该用户已存在").type(MediaType.TEXT_PLAIN).build();
		}

		if (utype != CodeUserType.USER && utype != CodeUserType.OWER) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("用户类型错误").type(MediaType.TEXT_PLAIN).build();
		}

		User u = userService.create(username, password, utype);

		String encry = PublicHelper.encryptUser(u.getId(), u.getPassword(),
				utype);
		return Response
				.ok(u)
				.cookie(new NewCookie(new javax.ws.rs.core.Cookie(
						"Authorization", encry), "用户名",
						NewCookie.DEFAULT_MAX_AGE, false))
				.header("Authorization", encry).build();
	}

}
