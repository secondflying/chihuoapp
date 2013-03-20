package com.chihuo.sns;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class SNSConfig {
	public SNSConfig() {
	}

	private static Properties props = new Properties();
	static {
		try {
			props.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("sns.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------
	public static String getWeiboClientID() {
		return props.getProperty("weibo.client_ID").trim();
	}

	public static String getWeiboClientSercert() {
		return props.getProperty("weibo.client_SERCRET").trim();
	}

	public static String getWeiboRedirectURI() {
		return props.getProperty("weibo.redirect_URI").trim();
	}

	public static String getWeiboBaseURI() {
		return props.getProperty("weibo.baseURL").trim();
	}

	public static String getWeiboAccesstokenURI() {
		return props.getProperty("weibo.accessTokenURL").trim();
	}

	public static String getWeiboAuthorizeURI() {
		return props.getProperty("weibo.authorizeURL").trim();
	}

	// --------------------------------------------------------------------
	public static String getQzoneClientID() {
		return props.getProperty("qzone.client_ID").trim();
	}

	public static String getQzoneClientSercert() {
		return props.getProperty("qzone.client_SERCRET").trim();
	}

	public static String getQzoneRedirectURI() {
		return props.getProperty("qzone.redirect_URI").trim();
	}

	public static String getQzoneBaseURI() {
		return props.getProperty("qzone.baseURL").trim();
	}

	public static String getQzoneAccesstokenURI() {
		return props.getProperty("qzone.accessTokenURL").trim();
	}

	public static String getQzoneAuthorizeURI() {
		return props.getProperty("qzone.authorizeURL").trim();
	}

	// --------------------------------------------------------------------
	public static String getTqqClientID() {
		return props.getProperty("tqq.client_ID").trim();
	}

	public static String getTqqClientSercert() {
		return props.getProperty("tqq.client_SERCRET").trim();
	}

	public static String getTqqRedirectURI() {
		return props.getProperty("tqq.redirect_URI").trim();
	}

	public static String getTqqBaseURI() {
		return props.getProperty("tqq.baseURL").trim();
	}

	public static String getTqqAccesstokenURI() {
		return props.getProperty("tqq.accessTokenURL").trim();
	}

	public static String getTqqAuthorizeURI() {
		return props.getProperty("tqq.authorizeURL").trim();
	}

	// --------------------------------------------------------------------
	public static String getDoubanClientID() {
		return props.getProperty("douban.client_ID").trim();
	}

	public static String getDoubanClientSercert() {
		return props.getProperty("douban.client_SERCRET").trim();
	}

	public static String getDoubanRedirectURI() {
		return props.getProperty("douban.redirect_URI").trim();
	}

	public static String getDoubanBaseURI() {
		return props.getProperty("douban.baseURL").trim();
	}

	public static String getDoubanAccesstokenURI() {
		return props.getProperty("douban.accessTokenURL").trim();
	}

	public static String getDoubanAuthorizeURI() {
		return props.getProperty("douban.authorizeURL").trim();
	}

}
