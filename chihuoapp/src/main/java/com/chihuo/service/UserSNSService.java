package com.chihuo.service;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chihuo.bussiness.User;
import com.chihuo.bussiness.UserSNS;
import com.chihuo.dao.UserDao;
import com.chihuo.dao.UserSNSDao;
import com.sun.jersey.core.header.FormDataContentDisposition;

@Service
@Transactional
public class UserSNSService {
	@Autowired
	private UserSNSDao snsDao;
	
	@Autowired 
	private UserDao userDao;

	public UserSNS findById(Integer id) {
		return snsDao.findById(id);
	}
	
	public UserSNS findByOpenID(String openid, Integer snstype){
		return snsDao.findByOpenID(openid, snstype);
	}

	
	public UserSNS create(String openid, String name,String thumbnail, String accessToken, String refreshToken, long expirein, Integer snstype) {
		User user = new User();
		user.setName(name);
		user.setThumbnail(thumbnail);
		user.setFromsns(snstype);
		
		
		UserSNS sns = new UserSNS();
		sns.setOpenid(openid);
		sns.setName(name);
		sns.setThumbnail(thumbnail);
		sns.setAccessToken(accessToken);
		sns.setRefreshToken(refreshToken);
		sns.setSnstype(snstype);
		sns.setExpiresTime(new java.util.Date(System.currentTimeMillis() + (long)expirein*1000));
		sns.setUser(user);
		
		userDao.saveOrUpdate(user);
		
		snsDao.saveOrUpdate(sns);
		
		return sns;
	}
	
	
	public void update(String name,String thumbnail, String accessToken, String refreshToken, long expirein,UserSNS sns){
		sns.setName(name);
		sns.setThumbnail(thumbnail);
		sns.setAccessToken(accessToken);
		sns.setRefreshToken(refreshToken);
		sns.setExpiresTime(new java.util.Date(System.currentTimeMillis() + (long)expirein*1000));
		snsDao.saveOrUpdate(sns);
	}


}
