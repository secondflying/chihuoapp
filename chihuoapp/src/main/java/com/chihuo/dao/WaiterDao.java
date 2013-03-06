package com.chihuo.dao;

import java.util.List;

import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.Waiter;

public interface WaiterDao extends GenericDao<Waiter, Integer> {

	public List<Waiter> findByRestaurant(Restaurant r);

	public Waiter findByIdInRestaurant(Restaurant r, int id);

	public Waiter findByName(String name, Restaurant r);

	public Waiter findByNameAndPassword(String name, String password,
			Restaurant r);
}
