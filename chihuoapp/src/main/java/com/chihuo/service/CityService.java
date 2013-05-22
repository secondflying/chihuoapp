package com.chihuo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chihuo.bussiness.City;
import com.chihuo.dao.CityDao;

@Service
@Transactional
public class CityService {
	@Autowired
	private CityDao dao;

	public List<City> getAll() {
		return dao.findAll();
	}

	public City findById(Integer id) {
		return dao.findById(id);
	}
}
