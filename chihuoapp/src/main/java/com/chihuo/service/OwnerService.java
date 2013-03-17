package com.chihuo.service;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chihuo.bussiness.Owner;
import com.chihuo.dao.OwnerDao;

@Service
@Transactional
public class OwnerService {
	@Autowired
	private OwnerDao dao;

	public Owner findById(Integer id) {
		return dao.findById(id);
	}

	public Owner findByName(String name) {
		return dao.findByName(name);
	}

	public Owner findByNameAndPassword(String name, String password) {
		return dao.findByNameAndPassword(name, password);
	}
	
	public Owner create(String username, String password) {
		Owner u = new Owner();
		u.setName(username);
		u.setPassword(password);
		dao.saveOrUpdate(u);
		return u;
	}

	public Owner getLoginOwner(SecurityContext securityContext) {
		Principal p = securityContext.getUserPrincipal();
		if (p != null && StringUtils.isNotBlank(p.getName())) {
			System.out.println("Principal:" + p.getName());
			String[] tmp = StringUtils.split(p.getName(), ':');
			if ("OWNER".equals(tmp[0])) {
				int uid = Integer.parseInt(tmp[1]);
				return dao.findById(uid);
			}
		}
		return null;
	}

}
