package com.chihuo.dao;

import java.util.List;

import com.chihuo.bussiness.Assistent;
import com.chihuo.bussiness.Restaurant;

public interface AssistentDao extends GenericDao<Assistent, Integer> {

	public List<Assistent> findByRestaurant(Restaurant r);

	public Assistent findByIdInRestaurant(Restaurant r, int id);

}
