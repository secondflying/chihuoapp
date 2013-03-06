package com.chihuo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chihuo.bussiness.DeskType;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.DeskTypeDao;

@Service
@Transactional
public class DeskTypeService {
	@Autowired
	private DeskTypeDao dao;

	public List<DeskType> findByRestaurant(Restaurant restaurant) {

		return dao.findByRestaurant(restaurant);
	}

	public DeskType findByIdInRestaurant(Restaurant restaurant, int id) {
		return dao.findByIdInRestaurant(restaurant, id);
	}

	public DeskType createOrUpdate(String name, Restaurant restaurant,
			DeskType deskType) {

		deskType.setName(name);
		deskType.setRestaurant(restaurant);
		deskType.setStatus(0);

		dao.saveOrUpdate(deskType);
		return deskType;
	}

	public void delete(DeskType c) {
		c.setStatus(-1);
		dao.saveOrUpdate(c);
	}

}
