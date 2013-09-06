package com.imaginea.cloudjee.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author: Nellore Krishna Kumar
 *
 * @Desc: This class reads the property file where user defined attributes are stored
 */

public class ReadConfig {
		
	private static Properties loginProperties = new Properties();
	static{
			
		try {
			loginProperties.load(ReadConfig.class.getResourceAsStream("/login.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static Properties getloginProperties(){
		return loginProperties;
	}
	
	private static Properties cloudjeereguser = new Properties();
	static{

		try {
			cloudjeereguser.load(ReadConfig.class.getResourceAsStream("/cloudjeereguser.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static Properties getconfigProperties(){
		return cloudjeereguser;
	}
	
	
	private static Properties dbProperties = new Properties();
	static{

		try {
			dbProperties.load(ReadConfig.class.getResourceAsStream("/database.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static Properties getdbProperties(){
		return dbProperties;
	}

}