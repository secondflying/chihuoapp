package com.chihuo.dao;

import com.chihuo.bussiness.User;

public interface UserDao extends GenericDao<User, Integer> {
	public User findByName(String name);
	
	public User findByNameAndPassword(String name, String password,Integer utype);
}
