package com.chihuo.daoimp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.chihuo.bussiness.Device;
import com.chihuo.bussiness.Logins;
import com.chihuo.bussiness.Order;
import com.chihuo.dao.LoginsDao;
import com.chihuo.util.CodeUserType;

@Repository
public class LoginsDaoImp extends GenericDAOImp﻿<Logins, Integer> implements LoginsDao{
	public Logins findByUserID(int userid, int usertype, Device device,
			Order order) {
		Criteria crit = getSession().createCriteria(Logins.class)
				.add(Restrictions.eq("uid", userid))
				.add(Restrictions.eq("utype", usertype))
				.add(Restrictions.eq("order.id", order.getId()));
		if (device == null) {
			crit = crit.add(Restrictions.isNull("device"));
		}else {
			crit = crit.add(Restrictions.eq("device.id", device.getId()));
		}
		crit.setCacheable(true);
		return (Logins) crit.uniqueResult();
	}

	// 获取负责当前订单的服务员设备
	public Device getWaiterDeviceByOrder(Order order) {
		Criteria crit = getSession().createCriteria(Logins.class)
				.add(Restrictions.eq("status", 0))
				.add(Restrictions.eq("utype", CodeUserType.WAITER))
				.add(Restrictions.eq("order.id", order.getId()));
		crit.setCacheable(true);
		Logins login = (Logins) crit.uniqueResult();
		if (login != null) {
			return login.getDevice();
		}
		return null;
	}

	// 获取匿名点餐的Device，可能有多个
	@SuppressWarnings("unchecked")
	public List<Device> getAnonymousDeviceByOrder(Order order) {
		Criteria crit = getSession().createCriteria(Logins.class)
				.add(Restrictions.eq("status", 0))
				.add(Restrictions.eq("utype", CodeUserType.ANONYMOUS))
				.add(Restrictions.eq("order.id", order.getId()));
		crit.setCacheable(true);
		List<Logins> logins =  crit.list();
		List<Device> devices = new ArrayList<Device>();
		
		for (Logins login : logins) {
			devices.add(login.getDevice());
		}
		return devices;
	}
}
