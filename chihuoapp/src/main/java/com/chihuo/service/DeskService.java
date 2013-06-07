package com.chihuo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chihuo.bussiness.Desk;
import com.chihuo.bussiness.DeskStatusView;
import com.chihuo.bussiness.DeskType;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.DeskDao;

@Service
@Transactional
public class DeskService {
	@Autowired
	private DeskDao dao;

//	@Autowired
//	private DeskViewDao viewDao;

	public List<DeskStatusView> findByType(DeskType dtype) {
		return dao.findByTypeWithStatus(dtype);
	}

	public List<DeskStatusView> findByRestaurant(Restaurant restaurant) {
		return dao.findByRestaurantWithStatus(restaurant);
	}
	
	public boolean checkNameExistInRestaurant(Restaurant r, String name){
		return dao.checkNameExistInRestaurant(r, name);
	}


	public Desk createOrUpdate(String name, int capacity, DeskType category,
			Restaurant restaurant, Desk desk) {
		desk.setName(name);
		desk.setCapacity(capacity);
		desk.setRestaurant(restaurant);
		desk.setStatus(0);
		desk.setDeskType(category);
		dao.saveOrUpdate(desk);
		return desk;
	}

	public Desk findByIdInRestaurant(Restaurant restaurant, int id) {
		return dao.findByIdInRestaurant(restaurant, id);
	}

	public void delete(Desk desk) {
		desk.setStatus(-1);
		dao.saveOrUpdate(desk);
	}

}
