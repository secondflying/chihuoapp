package com.chihuo.sns;

import java.io.Serializable;

public class AccessToken implements Serializable {

	private static final long serialVersionUID = 6986530164134648944L;
	
	private String accessToken;
	private long expireIn;
	private String refreshToken;
	private String uid;

	public AccessToken() {
		
	}

	public String getAccessToken() {
		return accessToken;
	}

	public long getExpireIn() {
		return expireIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public String getUid() {
		return uid;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setExpireIn(long expireIn) {
		this.expireIn = expireIn;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}


	@Override
	public String toString() {
		return "AccessToken [" + "accessToken=" + accessToken + ", expireIn="
				+ expireIn + ", refreshToken=" + refreshToken + ",uid=" + uid
				+ "]";
	}

}
