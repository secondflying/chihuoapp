package com.chihuo.service;

import java.security.Principal;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.Waiter;
import com.chihuo.dao.WaiterDao;

@Service
@Transactional
public class WaiterService {
	@Autowired
	private WaiterDao dao;

	public Waiter findById(Integer id) {
		return dao.findById(id);
	}

	public Waiter getLoginWaiter(SecurityContext securityContext) {
		Principal p = securityContext.getUserPrincipal();
		if (p != null) {
			String[] tmp = StringUtils.split(p.getName(), ':');
			if ("WAITER".equals(tmp[0])) {
				int uid = Integer.parseInt(tmp[1]);
				return dao.findById(uid);
			}
		}
		return null;
	}

	public List<Waiter> findByRestaurant(Restaurant r) {
		return dao.findByRestaurant(r);
	}

	public Waiter findByIdInRestaurant(Restaurant r, int id) {
		return dao.findByIdInRestaurant(r, id);
	}

	public Waiter findByName(String name, Restaurant r) {
		return dao.findByName(name, r);
	}

	public Waiter findByNameAndPassword(String name, String password,
			Restaurant r) {
		return dao.findByNameAndPassword(name, password, r);
	}

	public void delete(Waiter c) {
		c.setStatus(-1);
		dao.saveOrUpdate(c);
	}

	public Waiter createOrUpdate(String name, String password,
			Restaurant restaurant, Waiter waiter) {
		waiter.setName(name);
		waiter.setPassword(password);
		waiter.setRestaurant(restaurant);
		waiter.setStatus(0);
		dao.saveOrUpdate(waiter);
		return waiter;
	}

}
