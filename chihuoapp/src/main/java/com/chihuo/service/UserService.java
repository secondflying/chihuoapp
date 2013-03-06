package com.chihuo.service;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chihuo.bussiness.User;
import com.chihuo.dao.UserDao;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserDao dao;

	public User findById(Integer id) {
		return dao.findById(id);
	}

	public User findByName(String name) {
		return dao.findByName(name);
	}

	public User findByNameAndPassword(String name, String password,
			Integer utype) {
		return dao.findByNameAndPassword(name, password, utype);
	}
	
	public User create(String username, String password, int utype) {
		User u = new User();
		u.setName(username);
		u.setPassword(password);
		u.setUtype(utype);
		dao.saveOrUpdate(u);
		return u;
	}

	public User getLoginUser(SecurityContext securityContext) {
		Principal p = securityContext.getUserPrincipal();
		if (p != null && StringUtils.isNotBlank(p.getName())) {
			System.out.println("Principal:" + p.getName());
			String[] tmp = StringUtils.split(p.getName(), ':');
			if ("USER".equals(tmp[0])) {
				int uid = Integer.parseInt(tmp[1]);
				return dao.findById(uid);
			}
		}
		return null;
	}

}
