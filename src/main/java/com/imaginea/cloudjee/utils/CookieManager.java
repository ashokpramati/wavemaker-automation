package com.imaginea.cloudjee.utils;

import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CookieManager {
	
	static WebDriver driver = null;
	private final static JSONParser JSON_PARSER = new JSONParser();
	
	public static void main(String[] args) throws ParseException {
		driver = new FirefoxDriver();
		driver.get("https://apps.mywavemaker.com");
		driver.findElement(By.id("email")).sendKeys("krishnakumar.nellore@imaginea.com");
		driver.findElement(By.id("password")).sendKeys("pramati");		
		driver.findElement(By.id("loginrow")).click();
		driver.get("https://apps.mywavemaker.com/userportal/rest/applicationService/listApps");
		System.out.println(driver.findElement(By.tagName("pre")).getText());
		
	
	    /*
	    
		JSONObject jsonObject = (JSONObject) JSON_PARSER.parse(driver.findElement(By.tagName("pre")).getText());
		Set<String> keys =  jsonObject.get
	    for (String string : keys) {
		   System.out.println(string);
	        
	    }
	    //return out;
		System.out.println(jsonObject. get("success"));
		System.out.println(jsonObject.get("served-at"));
		System.out.println(jsonObject.get("request-id"));
		System.out.println(jsonObject.get("time-taken"));
		System.out.println(jsonObject.get("served-by"));*/
		
		
	}
		
	
}
