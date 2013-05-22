package com.chihuo.service;

import com.chihuo.bussiness.Owner;

public interface UserContext {

	public Owner getCurrentUser();

	public void setCurrentUser(Owner user);
}
