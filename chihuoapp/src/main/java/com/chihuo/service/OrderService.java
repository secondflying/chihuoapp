package com.chihuo.service;

import java.util.Date;
import java.util.List;

import org.ietf.jgss.Oid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chihuo.bussiness.Desk;
import com.chihuo.bussiness.Order;
import com.chihuo.bussiness.OrderItem;
import com.chihuo.bussiness.Recipe;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.Waiter;
import com.chihuo.dao.OrderDao;
import com.chihuo.dao.OrderItemDao;

@Service
@Transactional
public class OrderService {
	@Autowired
	private OrderDao dao;

	@Autowired
	private OrderItemDao itemDao;

	public List<Order> findByWaiter(Restaurant r, Waiter user) {
		return dao.findByWaiter(r, user);
	}

	public List<Order> findByRestaurant(Restaurant restaurant) {
		return dao.findByRestaurant(restaurant);
	}

	public Order findByCode(Restaurant restaurant, String code) {
		return dao.findByCode(restaurant, code);
	}

	public Order findByIdInRestaurant(Restaurant restaurant, int id) {
		return dao.findByIdInRestaurant(restaurant, id);
	}
	
	public Order findByDesk(Restaurant r, int deskid) {
		return dao.findByDesk(r, deskid);
	}

	public boolean isDeskCanOrder(int id) {
		return dao.isDeskCanOrder(id);
	}

	// 开台
	public Order createOrder(Desk d, int number, Restaurant restaurant, Waiter u) {
		// TODO order状态： 1为新开台 3为请求结账 4为已结账 5为撤单
		Order order = new Order();
		order.setDesk(d);
		order.setNumber(number);
		order.setStarttime(new Date());
		order.setStatus(1);
		order.setRestaurant(restaurant);
		order.setWaiter(u);

		// TODO 在生成的code里面包含桌号，避免同时有相同code的order
		order.setCode(Math.round(Math.random() * 9000 + 1000) + "");
		dao.saveOrUpdate(order);
		return order;
	}
	
	public Order deposit(Order order) {
		List<OrderItem> list = itemDao.queryByOrder(order.getId());
		for (OrderItem orderItem : list) {
			if (orderItem.getStatus() == 0) {
				orderItem.setStatus(1);
				itemDao.saveOrUpdate(orderItem);
			}
		}
		order.setOrderItems(list);
		return order;
	}

	// 请求结账
	public Order toCheckOrder(Order order) {
		order.setStatus(3);
		dao.saveOrUpdate(order);
		return order;
	}

	// 结账
	public Order checkOrder(Order order) {
		order.setStatus(4);
		dao.saveOrUpdate(order);
		return order;
	}

	// 取消
	public Order cancelOrder(Order order) {
		order.setStatus(5);
		dao.saveOrUpdate(order);
		return order;
	}

	// 加减菜
	public void addMenu(Order order, Recipe recipe, int count) {
		OrderItem item = itemDao.queryByOrderAndRecipe(order.getId(),
				recipe.getId());

		int totalCount = 0;
		if (item != null && item.getCount() != null) {
			totalCount = item.getCount() + count;
		} else {
			totalCount = count;
		}
		totalCount = totalCount < 0 ? 0 : totalCount;

		if (totalCount == 0) {
			if (item != null) {
				itemDao.delete(item);
			}
		} else {
			if (item == null) {
				item = new OrderItem();
			}
			
			
			item.setOrder(order);
			item.setRecipe(recipe);
			item.setCount(totalCount);
			item.setStatus(0);
			itemDao.saveOrUpdate(item);
		}
	}

	// 改变菜的状态为已上
	public OrderItem giveItemToClient(OrderItem item) {
		item.setStatus(1);
		itemDao.saveOrUpdate(item);
		return item;
	}

	public OrderItem findByIdInOrder(Integer id, int iid) {
		return itemDao.findByIdInOrder(id, iid);
	}

	public List<OrderItem> queryOrderItems(Integer id) {
		return itemDao.queryByOrder(id);
	}

}
