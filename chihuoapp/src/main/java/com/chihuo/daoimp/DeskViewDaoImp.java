//package com.chihuo.daoimp;
//
//import java.util.List;
//
//import org.hibernate.Criteria;
//import org.hibernate.criterion.Restrictions;
//import org.springframework.stereotype.Repository;
//
//import com.chihuo.bussiness.DeskStatusView;
//import com.chihuo.bussiness.DeskType;
//import com.chihuo.bussiness.Restaurant;
//import com.chihuo.dao.DeskViewDao;
//
//@Repository
//public class DeskViewDaoImp extends GenericDAOImp﻿<DeskStatusView, Integer> implements DeskViewDao{
//	@SuppressWarnings("unchecked")
//	public List<DeskStatusView> findByRestaurant(Restaurant r) {
//		Criteria crit = getSession().createCriteria(DeskStatusView.class).add(Restrictions.not(Restrictions.eq("status", -1)));
//		crit = crit.add(Restrictions.eq("rid", r.getId()));
//		crit.setCacheable(true);
//
//		return (List<DeskStatusView>)crit.list();
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<DeskStatusView> findByType(DeskType r) {
//		Criteria crit = getSession().createCriteria(DeskStatusView.class).add(Restrictions.not(Restrictions.eq("status", -1)));
//		crit = crit.add(Restrictions.eq("tid", r.getId()));
//		crit.setCacheable(true);
//
//		return (List<DeskStatusView>)crit.list();
//	}
//}
