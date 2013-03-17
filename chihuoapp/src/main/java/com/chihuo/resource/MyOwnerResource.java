package com.chihuo.resource;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.Owner;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.service.OwnerService;
import com.chihuo.service.RestaurantService;
import com.chihuo.util.CodeUserType;
import com.chihuo.util.PublicHelper;

@Component
@Path("/owner")
public class MyOwnerResource {
	@Autowired
	private OwnerService ownerService;

	@Autowired
	private RestaurantService restaurantService;

	//获取我拥有的餐厅列表
	@GET
	@RolesAllowed({ "OWNER" })
	@Path("/restaurants")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Restaurant> getMyRestaurants(
			@Context SecurityContext securityContext) {
		Owner owner = ownerService.getLoginOwner(securityContext);
		if (owner == null) {
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("用户不存在")
					.type(MediaType.TEXT_PLAIN).build());
		}
		return restaurantService.findByUser(owner);
	}
	
	@GET
	@RolesAllowed({"OWNER" })
	@Path("/userinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public Owner get(@Context SecurityContext securityContext) {
		Owner owner = ownerService.getLoginOwner(securityContext);
		if (owner == null) {
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("用户不存在")
					.type(MediaType.TEXT_PLAIN).build());
		}
		
		return owner;
	}
	
	@POST
	@Path("/login")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON )
	public Response createCategory(@FormParam("username") String username, @FormParam("password") String password) {

		Owner u = ownerService.findByNameAndPassword(username,password);
		if(u == null){
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("用户名密码不匹配").type(MediaType.TEXT_PLAIN).build();
		}
		
		String encry = PublicHelper.encryptUser(u.getId(), u.getPassword(),CodeUserType.OWNER);
		return Response.ok(u)
	               .cookie(new NewCookie(new javax.ws.rs.core.Cookie("Authorization", encry),"用户名",NewCookie.DEFAULT_MAX_AGE,false))
	               .header("Authorization", encry)
	               .build();
	}

}
