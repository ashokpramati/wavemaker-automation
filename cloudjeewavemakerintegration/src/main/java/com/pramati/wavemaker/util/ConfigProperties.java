package com.pramati.wavemaker.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Read property from configuration file
 * 
 * @author krishnakumarnellore
 *
 */
public class ConfigProperties {

	// Browser Configuration
	public static final String URL;
	public static final String BROWSER;
	public static final String COREDRIVERLOC;
	public static final String TIMEOUT;
	public static final String USERNAME;
	public static final String PASSWORD;
	
	private static Logger log = Logger.getLogger(ConfigProperties.class);
	
	
	private static Properties properties = new Properties();

	static {
		init();
		URL = properties.getProperty("url");
		log.info("Got url value from property file "+ URL);
		
		BROWSER = properties.getProperty("browser");
		log.info("Got browser value from property file "+ BROWSER);
		
		COREDRIVERLOC = properties.getProperty("coreDriverLoc");
		log.info("Got driver value from property file "+ COREDRIVERLOC);
		
		TIMEOUT = properties.getProperty("timeout");
		log.info("Got timeout value from property file "+ TIMEOUT);
		
		USERNAME = properties.getProperty("cloudJeeUsername");
		log.info("Got Cloudjee Username value from property file "+ USERNAME);
		
		PASSWORD = properties.getProperty("cloudJeePassword");
		log.info("Got Cloudjee Password value from property file "+ PASSWORD);
	}

	public static void init() {
		try {
			properties.load(ConfigProperties.class
					.getResourceAsStream("/config.properties"));
			log.info("Successfully loaded config.properties file");
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Unable to load property file");
		}
	}	

}
