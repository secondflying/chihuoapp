package com.chihuo.auth;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.User;
import com.chihuo.bussiness.Waiter;
import com.chihuo.service.UserService;
import com.chihuo.service.WaiterService;
import com.chihuo.util.CodeUserType;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

@Component
public class SecurityFilter implements ContainerRequestFilter {

	@Context
	UriInfo uriInfo;
	
	@Autowired
	private WaiterService waiterService;
	
	@Autowired
	private UserService userService;

	@Override
	public ContainerRequest filter(ContainerRequest request) {

		String authHead = request.getHeaderValue("Authorization");
		Cookie authCookie = request.getCookies().get("Authorization");

		String auth = "";
		if (authCookie != null) {
			// 网页登录
			auth = authCookie.getValue();
		} else if (StringUtils.isNotBlank(authHead)) {
			// 手机端登录
			auth = authHead;
		}

		// 手机端登录，暂时用于服务员
		if (StringUtils.isNotBlank(auth)) {
			String []tmp = StringUtils.split(auth,'|');
			if (tmp.length == 3) {
				int uid = Integer.parseInt(tmp[0]);
				int utype = Integer.parseInt(tmp[2]);
				String token = tmp[1];

				if (utype == CodeUserType.USER || utype == CodeUserType.OWER) {
					User user = userService.findById(uid);

					String signature = DigestUtils.shaHex(StringUtils
							.join(new String[] { user.getId().toString(),
									user.getPassword() }));
					if (token.equals(signature)) {
						request.setSecurityContext(new Authorizer(user, uriInfo));
					}
				} else if (utype == CodeUserType.WAITER) {
					Waiter waiter = waiterService.findById(uid);

					String signature = DigestUtils.shaHex(StringUtils
							.join(new String[] { waiter.getId().toString(),
									waiter.getPassword() }));
					if (token.equals(signature)) {
						request.setSecurityContext(new Authorizer(waiter,
								uriInfo));
					}
				}
			} 
		}

		return request;
	}

}
