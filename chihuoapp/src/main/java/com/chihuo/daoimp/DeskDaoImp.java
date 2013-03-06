package com.chihuo.daoimp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.chihuo.bussiness.Desk;
import com.chihuo.bussiness.DeskStatusView;
import com.chihuo.bussiness.DeskType;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.DeskDao;

@Repository
public class DeskDaoImp extends GenericDAOImp﻿<Desk, Integer> implements DeskDao {
	@SuppressWarnings("unchecked")
	public List<Desk> findByRestaurant(Restaurant r) {
		Criteria crit = getSession().createCriteria(Desk.class).add(Restrictions.not(Restrictions.eq("status", -1)));
		crit = crit.createCriteria("restaurant").add(Restrictions.eq("id", r.getId()));
		crit.setCacheable(true);

		return (List<Desk>)crit.list();
	}
	
	public Desk findByIdInRestaurant(Restaurant r,int id) {
		Criteria crit = getSession().createCriteria(Desk.class).add(Restrictions.eq("id", id)).add(Restrictions.not(Restrictions.eq("status", -1)));
		crit = crit.createCriteria("restaurant").add(Restrictions.eq("id", r.getId()));
		crit.setCacheable(true);

		return (Desk) crit.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Desk> findByType(DeskType r) {
		Criteria crit = getSession().createCriteria(Desk.class).add(Restrictions.not(Restrictions.eq("status", -1)));
		crit = crit.createCriteria("deskType").add(Restrictions.eq("id", r.getId()));
		crit.setCacheable(true);

		return (List<Desk>)crit.list();
	}
	
	
	public List<DeskStatusView> findByRestaurantWithStatus(Restaurant r) {
		Query q = getSession().createQuery("SELECT d.id, d.name, d.capacity,d.deskType.id,o.id,o.waiter.id,o.status,o.code FROM Desk as d  LEFT JOIN d.orderList as o WITH(o.status=1) WHERE(d.restaurant.id=:rid and d.status != -1)");
		q.setParameter("rid", r.getId());
		q.setCacheable(true);

		List<DeskStatusView> result = new ArrayList<DeskStatusView>();
		List<Object[]> oList = q.list();
		for (Object[] object : oList) {
			DeskStatusView v = new DeskStatusView();
			v.setId((Integer) object[0]);
			v.setName((String) object[1]);
			v.setCapacity((Integer) object[2]);
			v.setTid((Integer) object[3]);
			v.setOid((Integer) object[4]);
			v.setWid((Integer) object[5]);
			v.setOrderStatus((Integer) object[6]);
			v.setCode((String) object[7]);
			result.add(v);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<DeskStatusView> findByTypeWithStatus(DeskType r) {
		Query q = getSession().createQuery("SELECT d.id, d.name, d.capacity,d.deskType.id,o.id,o.waiter.id,o.status,o.code FROM Desk as d  LEFT JOIN d.orderList as o WITH(o.status=1) WHERE(d.deskType.id=:typeid and d.status != -1)");
		q.setParameter("typeid", r.getId());
		q.setCacheable(true);
		
		List<DeskStatusView> result = new ArrayList<DeskStatusView>();
		List<Object[]> oList = q.list();
		for (Object[] object : oList) {
			DeskStatusView v = new DeskStatusView();
			v.setId((Integer) object[0]);
			v.setName((String) object[1]);
			v.setCapacity((Integer) object[2]);
			v.setTid((Integer) object[3]);
			v.setOid((Integer) object[4]);
			v.setWid((Integer) object[5]);
			v.setOrderStatus((Integer) object[6]);
			v.setCode((String) object[7]);
			result.add(v);
		}
		return result;
	}
}
