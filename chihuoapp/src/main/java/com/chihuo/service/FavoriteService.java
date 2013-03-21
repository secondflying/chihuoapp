package com.chihuo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chihuo.bussiness.Favorite;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.User;
import com.chihuo.dao.FavoriteDao;

@Service
@Transactional
public class FavoriteService {
	@Autowired
	private FavoriteDao dao;

	public List<Favorite> findByUser(int uid) {
		return dao.findByUser(uid);
	}

	public Favorite addToFavorite(User user, Restaurant restaurant) {
		return dao.addToFavorite(user, restaurant);
	}

	public void delete(int id) {
		Favorite favorite = dao.findById(id);
		dao.delete(favorite);
	}

}
