package com.chihuo.daoimp;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.chihuo.bussiness.Device;
import com.chihuo.dao.DeviceDao;

@Repository
public class DeviceDaoImp extends GenericDAOImp﻿<Device, Integer> implements DeviceDao{
	public Device findByUserID(int userid, int usertype){
		Criteria crit = getSession().createCriteria(Device.class).add(Restrictions.eq("userid", userid)).add(Restrictions.eq("usertype", usertype));
		crit.setCacheable(true);
		return (Device)crit.uniqueResult();
	}
	
	public Device findByUDID(String udid){
		Criteria crit = getSession().createCriteria(Device.class).add(Restrictions.eq("deviceid", udid));
		crit.setCacheable(true);
		return (Device)crit.uniqueResult();
	}
}
