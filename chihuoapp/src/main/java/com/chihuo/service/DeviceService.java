package com.chihuo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chihuo.bussiness.Device;
import com.chihuo.bussiness.History;
import com.chihuo.bussiness.Logins;
import com.chihuo.bussiness.Order;
import com.chihuo.bussiness.User;
import com.chihuo.dao.DeviceDao;
import com.chihuo.dao.LoginsDao;

@Service
@Transactional
public class DeviceService {
	@Autowired
	private DeviceDao dao;
	
	@Autowired
	LoginsDao lDao;
	
	//注册设备
	public Device register(String udid,int codePlatform){
		Device device = dao.findByUDID(udid);
		if (device == null) {
			device = new Device();
			device.setDeviceid(udid);
			device.setPtype(codePlatform);
			device.setRegisterTime(new Date());
			dao.saveOrUpdate(device);
		} else {
			device.setRegisterTime(new Date());
			dao.saveOrUpdate(device);
		}
		return device;
	}
	
	
	public Device findByUDID(String udid){
		Device device = dao.findByUDID(udid);
		return device;
	}
	
	//订单和设备间的关系
	public Logins recordLogin(Order order, Device device, int uid, int utype) {
		Logins login = lDao.findByUserID(uid, utype,device,order);
		if(login != null){
			login.setLoginTime(new Date());
		}else {
			login = new Logins();
			login.setUid(uid);
			login.setUtype(utype);
			login.setDevice(device);
			login.setOrder(order);
			login.setStatus(0);
			login.setLoginTime(new Date());
		}
		lDao.saveOrUpdate(login);
		return login;
	}

	public List<Device> getAnonymousDeviceByOrder(Order order) {
		return lDao.getAnonymousDeviceByOrder(order);
	}

	public Device getWaiterDeviceByOrder(Order order) {
		return lDao.getWaiterDeviceByOrder(order);
	}
	
	public List<History> getHistoryOrder(User user,Device device) {
		if(user != null){
			return lDao.getHistoryOrderByUser(user);
		}else if (device != null) {
			return lDao.getHistoryOrderByDevice(device);
		}
		return null;
	}
}

