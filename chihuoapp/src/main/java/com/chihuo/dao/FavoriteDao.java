package com.chihuo.dao;

import java.util.List;

import com.chihuo.bussiness.Favorite;
import com.chihuo.bussiness.Owner;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.User;

public interface FavoriteDao extends GenericDao<Favorite, Integer> {

	public List<Favorite> findByUser(int uid);
	Favorite addToFavorite(User user, Restaurant restaurant);
}