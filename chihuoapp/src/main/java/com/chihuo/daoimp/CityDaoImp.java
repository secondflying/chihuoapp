package com.chihuo.daoimp;

import org.springframework.stereotype.Repository;

import com.chihuo.bussiness.City;
import com.chihuo.dao.CityDao;

@Repository
public class CityDaoImp extends GenericDAOImp﻿<City, Integer> implements CityDao{
}
