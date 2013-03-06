package com.chihuo.dao;

import com.chihuo.bussiness.Device;

public interface DeviceDao extends GenericDao<Device, Integer> {
	public Device findByUserID(int userid, int usertype);
	
	public Device findByUDID(String udid);
}
