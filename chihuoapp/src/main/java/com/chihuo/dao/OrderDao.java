package com.chihuo.dao;

import java.util.List;

import com.chihuo.bussiness.Order;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.Waiter;

public interface OrderDao extends GenericDao<Order, Integer> {

	public List<Order> findByRestaurant(Restaurant r);

	public Order findByIdInRestaurant(Restaurant r, int id);

	// 判断该桌是否有status 为1的order，有的话，说明该桌已开台
	public boolean isDeskCanOrder(int did);

	// 获取状态为1的桌子列表
	public List<Order> findByStatus(Restaurant r);

	public Order findByCode(Restaurant r, String code);

	public Order findByDesk(Restaurant r, int did);

	public List<Order> findByWaiter(Waiter u);

}
