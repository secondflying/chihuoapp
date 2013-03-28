package com.chihuo.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
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
import com.sun.tools.javac.resources.javac;

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
		
		InetAddress[] addresses = 
		java.net.InetAddress.getAllByName("baidu.com");
		for (InetAddress inetAddress : addresses) {
			System.out.println(inetAddress.getHostName());
			System.out.println(inetAddress.getHostAddress());
			System.out.println(inetAddress.getCanonicalHostName());
		}

	}

}
