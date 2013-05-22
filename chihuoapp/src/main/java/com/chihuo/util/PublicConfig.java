package com.chihuo.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PublicConfig {

	private static Properties prop;
	static{
		try {
			InputStream in = PublicConfig.class.getResourceAsStream("/config.properties"); 
			prop = new Properties();  
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	public static String getImagePath(){
		return prop.getProperty("imagePath");
	}
	
	public static String getJUserName() {
		return prop.getProperty("userName");
	}
	
	public static String getJUserPassword() {
		return prop.getProperty("userPassword");
	}
	
	public static String getJUserAppKey() {
		return prop.getProperty("userAppKey");
	}
	
	public static String getJWaiterName() {
		return prop.getProperty("waiterName");
	}
	
	public static String getJWaiterPassword() {
		return prop.getProperty("waiterPassword");
	}
	
	public static String getJWaiterAppKey() {
		return prop.getProperty("waiterAppKey");
	}
	
	public static boolean isLocal() {
		return Boolean.parseBoolean(prop.getProperty("isLocal"));
	}
	
	public static String getImageUrl() {
		return prop.getProperty("imageUrl");
	}	
}