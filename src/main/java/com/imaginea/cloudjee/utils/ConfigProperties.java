package com.imaginea.cloudjee.utils;

import java.io.IOException;
import java.util.Properties;


public class ConfigProperties {

	// Autentication
	public static final String USER_NAME;
	public static final String PASSWORD;
	public static final String USER_NAMES;
	public static final String HOST_NAME;
	public static final String AUTH_LOGIN_URI;
	public static final String BASE_URI;
	public static final String GROUP_URI;
	public static final String PROVISION_URI;
	public static final String ACTIVITY_PROGRESS_URI;
	public static final String APPLICATION_URI;
	public static final String CREATE_GROUP_METHOD_NAME;
	public static final String CREATE_GRP_INVO_CNT;
	
	// Application
	public static final String APP_NAME;
	public static final String APP_PATH;
	
	// deploy urls
	public static final String DEPLOY;
	public static final String UNDEPLOY;
	public static final String START;
	public static final String STOP;
	public static final String LIST;
	
	private static Properties properties = new Properties();

	static {
		init();
		USER_NAME = properties.getProperty("userName");
		PASSWORD = properties.getProperty("password");
		USER_NAMES = properties.getProperty("userNames");
		HOST_NAME = properties.getProperty("hostName");
		AUTH_LOGIN_URI = properties.getProperty("loginUri");
		BASE_URI = properties.getProperty("baseUri");
		GROUP_URI = BASE_URI + properties.getProperty("groupService");
		PROVISION_URI = BASE_URI + properties.getProperty("provisionService");
		ACTIVITY_PROGRESS_URI = BASE_URI + properties.getProperty("activityService");
		APPLICATION_URI = BASE_URI + properties.getProperty("applicationService");
		CREATE_GROUP_METHOD_NAME = properties.getProperty("createGroupMethod");
		CREATE_GRP_INVO_CNT = properties.getProperty("createGrpInvocationCount");	
		
		APP_NAME = properties.getProperty("appName");
		APP_PATH = properties.getProperty("appPath");
		
		DEPLOY = properties.getProperty("deploy");
		UNDEPLOY = properties.getProperty("undeploy");
		START = properties.getProperty("start");
		STOP = properties.getProperty("stop");
		LIST = properties.getProperty("list");
	}

	public static void init() {
		try {
			properties.load(ConfigProperties.class.getResourceAsStream("/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		init();
		System.out.println("name:" + USER_NAME);
		System.out.println("pwd:" + PASSWORD);
	}

}
