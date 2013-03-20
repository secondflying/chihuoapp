package com.chihuo.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.chihuo.sns.AccessToken;
import com.sina.sae.memcached.SaeMemcache;

public class MemCachedTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	// @Test
	public void memcahcedInSina() throws ClientProtocolException, IOException {
		// MemcachedClient cache = new =
		// MemcachedClient(AddrUtil.getAddresses("127.0.0.1:11211"));

		SaeMemcache cache = new SaeMemcache("127.0.0.1", 11211);
		cache.init();

		String key = "test";
		String value = "tgesttesttest22222";
		System.out.println("Get Object before set :" + cache.get(key));

		// set a new object

		cache.set(key, value, 0);

		System.out.println("Get Object after set :" + cache.get(key));

	}

	// @Test
	public void memcahcedInLocal() throws ClientProtocolException, IOException {
		MemcachedClient cache = new MemcachedClient(
				AddrUtil.getAddresses("127.0.0.1:11211"));

		String key = "com.chihuo.bussiness.Order:0:4";
		// String value = "tgesttesttest22222111";
		// System.out.println("Get Object before set :" + cache.get(key));
		//
		// cache.set(key, 0, value);

		System.out.println("Get Object after set :" + cache.get(key));

	}

	@Test
	public void testdate() throws ClientProtocolException, IOException {

		long timeStamp = 120758;
		// long timeStamp = 1280512800;

		// setExpiresIn(System.currentTimeMillis() + Long.parseLong(expiresIn) *
		// 1000);

		Date now = new Date();
		Date time = new java.util.Date(now.getTime() + (long) timeStamp * 1000);

		System.out.println("Get Object after set :" + time);

		AccessToken accessToken = new AccessToken();

		String json = "access_token=c9372b4b76e241a004cc284eae79d7ef&expires_in=604800&refresh_token=cc2e9b250d8b7cffa6bd24c07ea7895d&openid=16dbb4541fde38f9edd5c98d2fe2ef9d&name=t22098057&nick=金乌贼";
		Matcher m = Pattern.compile(
				"^access_token=(\\w+)&expires_in=(\\w+)&refresh_token=(\\w+)&openid=(\\w+)")
				.matcher(json);
		if (m.find()) {
			accessToken.setAccessToken(m.group(1));
			accessToken.setExpireIn(Long.parseLong(m.group(2)));
			accessToken.setRefreshToken(m.group(3));
			accessToken.setUid(m.group(4));
		}
	}

}
