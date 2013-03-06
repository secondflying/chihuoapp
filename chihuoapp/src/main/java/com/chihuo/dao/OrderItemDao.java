package com.chihuo.dao;

import java.util.List;

import com.chihuo.bussiness.OrderItem;

public interface OrderItemDao extends GenericDao<OrderItem, Integer> {
	public OrderItem queryByOrderAndRecipe(int oid, int rid);

	public OrderItem findByIdInOrder(int oid, int iid);

	public List<OrderItem> queryByOrder(int oid);
}
