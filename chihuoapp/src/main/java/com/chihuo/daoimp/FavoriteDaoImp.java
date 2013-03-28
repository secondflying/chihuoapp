package com.chihuo.daoimp;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.chihuo.bussiness.Favorite;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.User;
import com.chihuo.dao.FavoriteDao;

@Repository
public class FavoriteDaoImp extends GenericDAOImp﻿<Favorite, Integer> implements FavoriteDao {

	@Override
	public List<Favorite> findByUser(int uid) {
		Criteria crit = getSession().createCriteria(Favorite.class).
				add(Restrictions.eq("user.id", uid)).
				addOrder(Order.desc("time"));
		crit.setCacheable(true);
		return crit.list();
	}

	@Override
	public Favorite addToFavorite(User user, Restaurant restaurant) {
		Criteria crit = getSession().createCriteria(Favorite.class).
				add(Restrictions.eq("user.id", user.getId())).
				add(Restrictions.eq("restaurant.id", restaurant.getId()));
		
		Favorite favorite = (Favorite)crit.uniqueResult();
		if (favorite == null) {
			favorite = new Favorite();
			favorite.setUser(user);
			favorite.setRestaurant(restaurant);
			favorite.setTime(new Date());
			getSession().saveOrUpdate(favorite);
		}
		return favorite;
	}

	@Override
	public void cancelFavorite(User user, Integer rid) {
		Criteria crit = getSession().createCriteria(Favorite.class).
				add(Restrictions.eq("user.id", user.getId())).
				add(Restrictions.eq("restaurant.id", rid));
		
		Favorite favorite = (Favorite)crit.uniqueResult();
		if (favorite != null) {
			getSession().delete(favorite);
		}
	}


}
