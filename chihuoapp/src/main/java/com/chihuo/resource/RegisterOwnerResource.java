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

import com.chihuo.bussiness.Owner;
import com.chihuo.service.OwnerService;
import com.chihuo.util.CodeUserType;
import com.chihuo.util.PublicHelper;

@Component
@Path("/oregister")
public class RegisterOwnerResource {
	@Autowired
	OwnerService ownerService;

	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(@FormParam("username") String username,
			@FormParam("password") String password) {
		Owner user = ownerService.findByName(username);
		if (user != null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("该用户已存在").type(MediaType.TEXT_PLAIN).build();
		}


		Owner u = ownerService.create(username, password);

		String encry = PublicHelper.encryptUser(u.getId(), u.getPassword(),
				CodeUserType.OWNER);
		return Response
				.ok(u)
				.cookie(new NewCookie(new javax.ws.rs.core.Cookie(
						"Authorization", encry), "用户名",
						NewCookie.DEFAULT_MAX_AGE, false))
				.header("Authorization", encry).build();
	}

}
