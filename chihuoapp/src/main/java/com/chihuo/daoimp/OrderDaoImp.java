package com.chihuo.daoimp;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.chihuo.bussiness.Order;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.Waiter;
import com.chihuo.dao.OrderDao;

@Repository
public class OrderDaoImp extends GenericDAOImp﻿<Order, Integer> implements
		OrderDao {

	@SuppressWarnings("unchecked")
	public List<Order> findByRestaurant(Restaurant r) {
		Criteria crit = getSession().createCriteria(Order.class).add(
				Restrictions.not(Restrictions.eq("status", -1)));
		crit = crit.createCriteria("restaurant").add(
				Restrictions.eq("id", r.getId()));
		crit.setCacheable(true);
		return (List<Order>) crit.list();
	}

	public Order findByIdInRestaurant(Restaurant r, int id) {
		Criteria crit = getSession().createCriteria(Order.class)
				.add(Restrictions.eq("id", id))
				.add(Restrictions.not(Restrictions.eq("status", -1)))
				.createCriteria("restaurant")
				.add(Restrictions.eq("id", r.getId()));
		crit.setCacheable(true);

		return (Order) crit.uniqueResult();
	}

	// 判断该桌是否有status 为1的order，有的话，说明该桌已开台
	public boolean isDeskCanOrder(int did) {
		Criteria crit = getSession().createCriteria(Order.class).add(
				Restrictions.eq("status", 1));
		crit = crit.createCriteria("desk").add(Restrictions.eq("id", did));
		crit.setCacheable(true);

		return crit.list().isEmpty();
	}

	// 获取状态为1的桌子列表
	@SuppressWarnings("unchecked")
	public List<Order> findByStatus(Restaurant r) {
		Criteria crit = getSession().createCriteria(Order.class).add(
				Restrictions.eq("status", 1));
		crit = crit.createCriteria("restaurant").add(
				Restrictions.eq("id", r.getId()));
		crit.setCacheable(true);

		return (List<Order>) crit.list();
	}

	public Order findByCode(Restaurant r, String code) {
		Criteria crit = getSession().createCriteria(Order.class)
				.add(Restrictions.eq("code", code))
				.add(Restrictions.eq("status", 1));
		crit = crit.createCriteria("restaurant").add(
				Restrictions.eq("id", r.getId()));
		crit.setCacheable(true);

		return (Order) crit.uniqueResult();
	}

	public Order findByDesk(Restaurant r, int did) {
		Criteria crit = getSession().createCriteria(Order.class)
				.add(Restrictions.eq("status", 1))
				.add(Restrictions.eq("desk.id", did))
				.add(Restrictions.eq("restaurant.id", r.getId()));
		crit.setCacheable(true);

		return (Order) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Order> findByWaiter(Waiter u) {
		Criteria crit = getSession().createCriteria(Order.class);
		crit = crit.add(Restrictions.eq("waiter.id", u.getId()));
		crit = crit.add(Restrictions.eq("restaurant.id", u.getRestaurant().getId()));
		crit.setCacheable(true);

		return (List<Order>) crit.list();
	}

}
