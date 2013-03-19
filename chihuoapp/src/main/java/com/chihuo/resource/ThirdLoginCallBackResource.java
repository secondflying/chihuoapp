package com.chihuo.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
@Path("/callback")
public class ThirdLoginCallBackResource {

	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	public Response getMyHistory(@Context HttpServletRequest request,
			@Context UriInfo uriInfo, @Context SecurityContext securityContext) {
		if (!StringUtils.isEmpty(request.getParameter("uid"))) {
			String uid = request.getParameter("uid");
			return Response
					.status(Status.OK)
					.entity(uid)
					.type(MediaType.TEXT_PLAIN).build();
		}
		return Response.status(Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
				.entity("参数错误").build();
	}
}
