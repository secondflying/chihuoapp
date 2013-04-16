package com.chihuo.daoimp;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.chihuo.bussiness.Assistent;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.AssistentDao;

@Repository
public class AssisttentDaoImp extends GenericDAOImp﻿<Assistent, Integer> implements AssistentDao {

	@SuppressWarnings("unchecked")
	public List<Assistent> findByRestaurant(Restaurant r) {
		Criteria crit = getSession().createCriteria(Assistent.class)
							.add(Restrictions.eq("restaurant.id", r.getId()));
		crit.setCacheable(true);
		return (List<Assistent>) crit.list();
	}

	public Assistent findByIdInRestaurant(Restaurant r, int id) {
		Criteria crit = getSession().createCriteria(Assistent.class)
				.add(Restrictions.eq("id", id))
				.createCriteria("restaurant")
				.add(Restrictions.eq("id", r.getId()));
		crit.setCacheable(true);
		return (Assistent) crit.uniqueResult();
	}


}
