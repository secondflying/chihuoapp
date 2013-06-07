package com.chihuo.dao;

import java.util.List;

import com.chihuo.bussiness.DeskType;
import com.chihuo.bussiness.Restaurant;

public interface DeskTypeDao extends GenericDao<DeskType, Integer> {
	public List<DeskType> findByRestaurant(Restaurant r);

	public DeskType findByIdInRestaurant(Restaurant r, int id);

	public DeskType findByNameInRestaurant(Restaurant r, String name);
}
