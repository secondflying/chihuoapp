package com.chihuo.resource;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.UserSNS;
import com.chihuo.service.UserSNSService;
import com.chihuo.sns.AccessToken;
import com.chihuo.sns.SNSException;
import com.chihuo.sns.SdkDouban;
import com.chihuo.sns.SdkQzone;
import com.chihuo.sns.SdkTqq;
import com.chihuo.sns.SdkWeibo;
import com.chihuo.sns.UserInfo;
import com.chihuo.util.CodeSNSType;
import com.chihuo.util.CodeUserType;
import com.chihuo.util.PublicHelper;

@Component
@Path("/thirdlogin")
public class ThirdLoginResource {
	@Autowired
	private UserSNSService snsService;

	@GET
	@Path("/weibo")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response weiboRedirect(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		if (!StringUtils.isEmpty(request.getParameter("code"))) {
			String code = request.getParameter("code");

			try {
				// 1根据code获取token和openid
				SdkWeibo sdk = new SdkWeibo();
				AccessToken at = sdk.getAccessTokenByCode(code);

				// 2根据token获取用户基本信息
				UserInfo user = sdk.showUserById(at.getUid(),
						at.getAccessToken());

				return successResponse(at, user, CodeSNSType.WEIBO, uriInfo);

			} catch (SNSException e) {
				return errorResponse("weibo错误了：" + e.getMessage());
			}

		} else if (!StringUtils.isEmpty(request.getParameter("error"))) {
			return errorResponse("授权错误："
					+ request.getParameter("error_description"));
		}

		return errorResponse("地址错误");
	}

	@GET
	@Path("/qzone")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response qzoneRedirect(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		if (!StringUtils.isEmpty(request.getParameter("code"))) {
			String code = request.getParameter("code");
			try {
				SdkQzone sdk = new SdkQzone();
				AccessToken at = sdk.getAccessTokenByCode(code);
				UserInfo user = sdk.showUserById(at.getUid(),
						at.getAccessToken());
				return successResponse(at, user, CodeSNSType.QZONE, uriInfo);
			} catch (SNSException e) {
				return errorResponse("qzone错误了：" + e.getMessage());
			}

		} else if (!StringUtils.isEmpty(request.getParameter("usercancel"))) {
			return errorResponse("授权错误：用户取消授权");
		}

		return errorResponse("地址错误");
	}

	@GET
	@Path("/tqq")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response tqqRedirect(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		if (!StringUtils.isEmpty(request.getParameter("code"))) {
			String code = request.getParameter("code");
			try {
				SdkTqq sdk = new SdkTqq();
				AccessToken at = sdk.getAccessTokenByCode(code);

				UserInfo user = sdk.showUserById(at.getUid(),
						at.getAccessToken());
				return successResponse(at, user, CodeSNSType.TQQ, uriInfo);
			} catch (SNSException e) {
				return errorResponse("qzone错误了：" + e.getMessage());
			}

		} else if (!StringUtils.isEmpty(request.getParameter("usercancel"))) {
			return errorResponse("授权错误：用户取消授权");
		}

		return errorResponse("地址错误");
	}

	@GET
	@Path("/douban")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response doubanRedirect(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		if (!StringUtils.isEmpty(request.getParameter("code"))) {
			String code = request.getParameter("code");
			try {
				SdkDouban sdk = new SdkDouban();
				AccessToken at = sdk.getAccessTokenByCode(code);

				UserInfo user = sdk.showUserById(at.getUid(),
						at.getAccessToken());
				return successResponse(at, user, CodeSNSType.DOUBAN, uriInfo);
			} catch (SNSException e) {
				return errorResponse("douban错误了：" + e.getMessage());
			}

		} else if (!StringUtils.isEmpty(request.getParameter("error"))) {
			return errorResponse("授权错误：用户取消授权");
		}

		return errorResponse("地址错误");
	}

	private Response successResponse(AccessToken at, UserInfo user,
			Integer snstype, UriInfo uriInfo) {
		UserSNS sns = snsService.findByOpenID(at.getUid(), snstype);
		if (sns == null) {
			sns = snsService.create(at.getUid(), user.getName(),
					user.getThumbnail(), at.getAccessToken(),
					at.getRefreshToken(), at.getExpireIn(), snstype);
		} else {
			snsService.update(user.getName(), user.getThumbnail(),
					at.getAccessToken(), at.getRefreshToken(),
					at.getExpireIn(), sns);
		}

		String encry = PublicHelper
				.encryptUser(sns.getUser().getId(), sns.getOpenid()
						+ sns.getUser().getFromsns(), CodeUserType.USER);

		// TODO 这里以后需要更正
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().replacePath(
				"rest/1/taochike/thirdlogin/callback");
		URI listUri = ub.queryParam("Authorization", encry)
				.queryParam("openid", sns.getOpenid())
				.queryParam("access_token", sns.getAccessToken())
				.queryParam("expires_in", sns.getExpiresTime().getTime())
				.queryParam("name", sns.getUser().getName())
				.queryParam("thumbnail", sns.getUser().getThumbnail())
				.queryParam("snstype", sns.getSnstype()).build();
		return Response.seeOther(listUri).build();
	}

	public Response errorResponse(String error) {
		return Response
				.status(Status.BAD_REQUEST)
				.entity(error)
				.header(HttpHeaders.CONTENT_TYPE,
						MediaType.TEXT_PLAIN + "; charset=UTF-8")
				.type(MediaType.TEXT_PLAIN).build();
	}

	@GET
	@Path("/callback")
	@Produces({ MediaType.TEXT_PLAIN })
	public Response getCallback(@Context HttpServletRequest request) {
		if (!StringUtils.isEmpty(request.getParameter("uid"))) {
			String uid = request.getParameter("uid");
			return Response.status(Status.OK).entity(uid)
					.type(MediaType.TEXT_PLAIN).build();
		}
		return Response.status(Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
				.entity("参数错误").build();
	}
}
