package com.chihuo.dao;

import java.util.List;

import com.chihuo.bussiness.Device;
import com.chihuo.bussiness.Logins;
import com.chihuo.bussiness.Order;

public interface LoginsDao extends GenericDao<Logins, Integer> {
	public Logins findByUserID(int userid, int usertype, Device device,
			Order order);

	// 获取负责当前订单的服务员设备
	public Device getWaiterDeviceByOrder(Order order);

	// 获取匿名点餐的Device，可能有多个
	public List<Device> getAnonymousDeviceByOrder(Order order);
}
