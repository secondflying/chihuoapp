package com.chihuo.dao;

import com.chihuo.bussiness.Owner;

public interface OwnerDao extends GenericDao<Owner, Integer> {
	public Owner findByName(String name);
	
	public Owner findByNameAndPassword(String name, String password);
}
