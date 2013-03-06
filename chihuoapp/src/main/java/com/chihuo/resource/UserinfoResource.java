package com.chihuo.resource;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.User;
import com.chihuo.service.UserService;

@Component
@Path("/userinfo")
public class UserinfoResource {
	
	@Autowired
	UserService service;

	@GET
	@RolesAllowed({ "USER,OWER" })
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@Context SecurityContext securityContext) {
		User u = service.getLoginUser(securityContext);
		if (u == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("用户不存在")
					.type(MediaType.TEXT_PLAIN).build();
		}

		return Response.ok(u).build();
	}

}
