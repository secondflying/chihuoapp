package com.chihuo.dao;

import java.util.List;

import com.chihuo.bussiness.Owner;
import com.chihuo.bussiness.Restaurant;

public interface RestaurantDao extends GenericDao<Restaurant, Integer> {

	public List<Restaurant> findByStatus(int status);

	public List<Restaurant> findNotDeleted(int city,String name);

	public List<Restaurant> findByUser(Owner u);

	public List<Restaurant> findByExtent(double xmin,double xmax,double ymin,double ymax,int city);
	
}