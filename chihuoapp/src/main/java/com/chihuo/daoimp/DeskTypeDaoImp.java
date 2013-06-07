package com.chihuo.daoimp;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.chihuo.bussiness.DeskType;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.DeskTypeDao;

@Repository
public class DeskTypeDaoImp extends GenericDAOImp﻿<DeskType, Integer> implements
		DeskTypeDao {
	@SuppressWarnings("unchecked")
	public List<DeskType> findByRestaurant(Restaurant r) {
		Criteria crit = getSession().createCriteria(DeskType.class).add(
				Restrictions.not(Restrictions.eq("status", -1)));
		crit = crit.createCriteria("restaurant").add(
				Restrictions.eq("id", r.getId()));
		crit.setCacheable(true);

		return (List<DeskType>) crit.list();
	}

	public DeskType findByIdInRestaurant(Restaurant r, int id) {
		Criteria crit = getSession().createCriteria(DeskType.class)
				.add(Restrictions.eq("id", id))
				.add(Restrictions.not(Restrictions.eq("status", -1)))
				.createCriteria("restaurant")
				.add(Restrictions.eq("id", r.getId()));
		crit.setCacheable(true);

		return (DeskType) crit.uniqueResult();
	}

	@Override
	public DeskType findByNameInRestaurant(Restaurant r, String name) {
		Criteria crit = getSession().createCriteria(DeskType.class)
				.add(Restrictions.eq("name", name))
				.add(Restrictions.not(Restrictions.eq("status", -1)))
				.createCriteria("restaurant")
				.add(Restrictions.eq("id", r.getId()));
		crit.setCacheable(true);

		return (DeskType) crit.uniqueResult();
	}

}
