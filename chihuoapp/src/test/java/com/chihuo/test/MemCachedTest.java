package com.chihuo.test;

import java.io.IOException;
import java.net.InetAddress;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

import org.apache.http.client.ClientProtocolException;
import org.junit.After;
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
