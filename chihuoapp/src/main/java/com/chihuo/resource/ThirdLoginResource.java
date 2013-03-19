package com.chihuo.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.model.User;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

import com.chihuo.bussiness.UserSNS;
import com.chihuo.service.UserSNSService;
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
	public Response getMyHistory(@Context HttpServletRequest request,
			@Context UriInfo uriInfo, @Context SecurityContext securityContext)
			throws JSONException {
		if (!StringUtils.isEmpty(request.getParameter("code"))) {
			String code = request.getParameter("code");

			try {
				 // 根据code获取token
//				 Oauth oauth = new Oauth();
//				 AccessToken at = oauth.getAccessTokenByCode(code);
//				
//				 Users um = new Users();
//				 um.client.setToken(at.getAccessToken());
//				 User user = um.showUserById(at.getUid());

				String res = "{"
						+ "\"access_token\": \"2.00DkAT_CpOxhWB3ae1e4001fWb6MmD\","
						+ "\"expires_in\": 157679999," + "\"remind_in\":\"\","
						+ "\"uid\":\"2119930415\"" + "}";
				AccessToken at = new AccessToken(res);

				String res2 = "{"
						+"\"id\": 1404376560,"
						+"\"screen_name\": \"zaku\","
						+"\"name\": \"zaku\","
						+"\"province\": \"11\","
						+"\"city\": \"5\","
						+"\"location\": \"北京 朝阳区\","
						+"\"description\": \"人生五十年，乃如梦如幻；有生斯有死，壮士复何憾。\","
						+"\"url\": \"http://blog.sina.com.cn/zaku\","
						+"\"profile_image_url\": \"http://tp1.sinaimg.cn/1404376560/50/0/1\","
						+"\"domain\": \"zaku\","
						+"\"gender\": \"m\""
						+"}";
				JSONObject obj = new JSONObject(res2);
				User user = new User(obj);

				UserSNS sns = snsService.findByOpenID(user.getId(),
						CodeSNSType.WEIBO);
				// 如果没有注册
				if (sns == null) {
					sns = snsService
							.create(user.getId(), user.getName(),
									user.getProfileImageUrl(),
									at.getAccessToken(), at.getRefreshToken(),
									Long.parseLong(at.getExpireIn()),
									CodeSNSType.WEIBO);
				} else {
					snsService.update(user.getName(),
							user.getProfileImageUrl(), at.getAccessToken(),
							at.getRefreshToken(),
							Long.parseLong(at.getExpireIn()), sns);
				}

				// 判断该用户是否在本地库中，如果没有新增，有的话更新token和expire
				List<PathSegment> list = uriInfo.getPathSegments();
				for (PathSegment pathSegment : list) {
					System.out.println(pathSegment.getPath());
				}
				
				String encry = PublicHelper.encryptUser(sns.getUser().getId(), sns.getOpenid() + sns.getUser().getFromsns(),CodeUserType.USER);

				//TODO 这里以后需要更正
				UriBuilder ub = uriInfo.getAbsolutePathBuilder().replacePath("rest/1/taochike/thirdlogin/callback");
				URI listUri = ub.queryParam("Authorization", encry)
								.queryParam("openid", sns.getOpenid())
								.queryParam("access_token", sns.getAccessToken())
								.queryParam("expires_in", sns.getExpiresTime().getTime())
								.build();
				return Response.seeOther(listUri).build();

			} catch (WeiboException e) {
				return Response
						.status(Status.BAD_REQUEST)
						.entity("weibo错误："
								+ request.getParameter("error_description"))
						.type(MediaType.TEXT_PLAIN).build();
			}

		} else if (!StringUtils.isEmpty(request.getParameter("error"))) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity("授权错误：" + request.getParameter("error_description"))
					.type(MediaType.TEXT_PLAIN).build();
		}

		return Response.status(Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
				.entity("地址错误").build();
	}
	
	
	
	@GET
	@Path("/callback")
	@Produces({ MediaType.TEXT_PLAIN })
	public Response getCallback(@Context HttpServletRequest request) {
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
