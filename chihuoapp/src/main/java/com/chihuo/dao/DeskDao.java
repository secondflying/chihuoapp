package com.chihuo.dao;

import java.util.List;

import com.chihuo.bussiness.Desk;
import com.chihuo.bussiness.DeskStatusView;
import com.chihuo.bussiness.DeskType;
import com.chihuo.bussiness.Restaurant;

public interface DeskDao extends GenericDao<Desk, Integer> {
	public List<Desk> findByRestaurant(Restaurant r);

	public Desk findByIdInRestaurant(Restaurant r, int id);
	
	public boolean checkNameExistInRestaurant(Restaurant r, String name);

	public List<Desk> findByType(DeskType r);
	
	public List<DeskStatusView> findByRestaurantWithStatus(Restaurant r);
	
	public List<DeskStatusView> findByTypeWithStatus(DeskType r);

}
