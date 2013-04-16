package com.chihuo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chihuo.bussiness.Assistent;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.AssistentDao;

@Service
@Transactional
public class AssistentService {
	@Autowired
	private AssistentDao dao;

	public Assistent findById(Integer id) {
		return dao.findById(id);
	}

	public List<Assistent> findByRestaurant(Restaurant r) {
		return dao.findByRestaurant(r);
	}

	public Assistent findByIdInRestaurant(Restaurant r, int id) {
		return dao.findByIdInRestaurant(r, id);
	}

}
