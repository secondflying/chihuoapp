package com.chihuo.bussiness;

import java.util.Date;

// Generated 2012-11-27 16:53:06 by Hibernate Tools 3.4.0.CR1

/**
 * Users generated by hbm2java
 */
public class UserSNS implements java.io.Serializable {

	private Integer id;
	private String name;
	private String openid;
	private String thumbnail;
	private String accessToken;
	private String refreshToken;
	private Date expiresTime;
	private Integer snstype;
	private User user;

	public UserSNS() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Date getExpiresTime() {
		return expiresTime;
	}

	public void setExpiresTime(Date expiresTime) {
		this.expiresTime = expiresTime;
	}

	public Integer getSnstype() {
		return snstype;
	}

	public void setSnstype(Integer snstype) {
		this.snstype = snstype;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

}
