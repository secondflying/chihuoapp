package com.chihuo.resource;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
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

import com.chihuo.bussiness.UserSNS;
import com.chihuo.service.UserSNSService;
import com.chihuo.util.CodeSNSType;

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
				 Oauth oauth = new Oauth();
				 AccessToken at = oauth.getAccessTokenByCode(code);
				
				 Users um = new Users();
				 um.client.setToken(at.getAccessToken());
				 User user = um.showUserById(at.getUid());

//				String res = "{"
//						+ "\"access_token\": \"2.00DkAT_CpOxhWB3ae1e4001fWb6MmD\","
//						+ "\"expires_in\": 157679999," + "\"remind_in\":\"\","
//						+ "\"uid\":\"2119930415\"" + "}";
//				AccessToken at = new AccessToken(res);
//
//				String res2 = "{"
//						+"\"id\": 1404376560,"
//						+"\"screen_name\": \"zaku\","
//						+"\"name\": \"zaku\","
//						+"\"province\": \"11\","
//						+"\"city\": \"5\","
//						+"\"location\": \"北京 朝阳区\","
//						+"\"description\": \"人生五十年，乃如梦如幻；有生斯有死，壮士复何憾。\","
//						+"\"url\": \"http://blog.sina.com.cn/zaku\","
//						+"\"profile_image_url\": \"http://tp1.sinaimg.cn/1404376560/50/0/1\","
//						+"\"domain\": \"zaku\","
//						+"\"gender\": \"m\""
//						+"}";
//				JSONObject obj = new JSONObject(res2);
//				User user = new User(obj);

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
				UriBuilder ub = uriInfo.getAbsolutePathBuilder();
				URI listUri = ub.path("callback")
						.queryParam("uid", sns.getUser().getId()).build();
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
}
