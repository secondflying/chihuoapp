package com.chihuo.daoimp;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.chihuo.bussiness.OrderItem;
import com.chihuo.dao.OrderItemDao;

@Repository
public class OrderItemDaoImp extends GenericDAOImp﻿<OrderItem, Integer> implements OrderItemDao{
	public OrderItem queryByOrderAndRecipe(int oid, int rid) {
		Criteria crit = getSession().createCriteria(OrderItem.class);
		crit.createCriteria("order").add(Restrictions.eq("id", oid));
		crit.createCriteria("recipe").add(Restrictions.eq("id", rid));
		crit.setCacheable(true);
		return (OrderItem) crit.uniqueResult();
	}

	public OrderItem findByIdInOrder(int oid, int iid) {
		Criteria crit = getSession().createCriteria(OrderItem.class)
				.add(Restrictions.not(Restrictions.eq("status", -1)))
				.add(Restrictions.eq("id", iid))
				.add(Restrictions.eq("order.id", oid));
		crit.setCacheable(true);
		return (OrderItem) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<OrderItem> queryByOrder(int oid) {
		Criteria crit = getSession().createCriteria(OrderItem.class)
				.add(Restrictions.not(Restrictions.eq("status", -1)))
				.addOrder(org.hibernate.criterion.Order.asc("id"))
				.add(Restrictions.eq("order.id", oid));
		crit.setCacheable(true);
		return (List<OrderItem>) crit.list();
	}
}
