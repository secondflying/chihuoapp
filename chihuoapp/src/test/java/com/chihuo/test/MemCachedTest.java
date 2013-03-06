package com.chihuo.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

import com.sina.sae.memcached.SaeMemcache;

public class MemCachedTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void 登录测试() throws ClientProtocolException, IOException {
		// MemcachedClient cache = new
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
}
