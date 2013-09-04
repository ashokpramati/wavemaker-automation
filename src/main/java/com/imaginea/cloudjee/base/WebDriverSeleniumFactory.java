package com.imaginea.cloudjee.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


/**
 * This class returns a class implementing selenium interface.
 * O
 * This includes:
 * 
 * 1) DefaultSelenium
 * 2) WebDriverBackedSelenium
 * 
 * WebDriverBackedSelenium implementation classes that can be obtained are:
 * 
 * 1) FirefoxDriver
 * 2) IEdriver
 * 3) OperaDriver
 * 4) HtmlUnitDriver
 * 5) iPhoneDriver
 * 6) AndroidDriver
 * 
 * @author sshyamala
 */
public class WebDriverSeleniumFactory {

    /**
     * DefaultSelenium identifier key
     */
    public static final String DEFAULT = "DEFAULT";
    
    /**
     * Firefox WebDriver identifier key
     */
    public static final String FIREFOXWEBDRIVER = "FIREFOXWEBDRIVER";
    
     /**
      * Internet Explorer (IE) WebDriver identifier key
      */
    public static final String IEWEBDRIVER = "IEWEBDRIVER";
    
     /**
      * Chrome WebDriver identifier key
      */
    public static final String CHROMEWEBDRIVER = "CHROMEWEBDRIVER";
    
     /**
      * Opera WebDriver identifier key
      */
    public static final String OPERAWEBDRIVER = "OPERAWEBDRIVER";
    
     /**
      * HtmlUnit WebDriver identifier key
      */
    public static final String HTMLUNITDRIVER = "HTMLUNITDRIVER";
    
    /**
     * Default Constructor.
     */
    public WebDriverSeleniumFactory() { }
    
   
    
    /**
     * Use this constructor if you want to get an implementation of Selenium
     * using a WebDriver.
     * 
     * @param webDriverId the id of the WebDriver to use
     * @param browserURL the starting URL including just a domain name
     * 
     * @return Instance of a Selenium implementation class WebDriver
     * 
     * @throws Exception 
     */
    public WebDriver getSelenium(String webDriverId) throws Exception {
        
        
        // now set the selenium class per webDriverId
    	WebDriver driver = null; 
        
        if(webDriverId.equals(FIREFOXWEBDRIVER)) driver = new FirefoxDriver();
        
        else if(webDriverId.equals(IEWEBDRIVER)) driver = new InternetExplorerDriver();
        
        else if(webDriverId.equals(CHROMEWEBDRIVER)) driver = new ChromeDriver();
        
        //else if(webDriverId.equals(OPERAWEBDRIVER)) driver = new OperaDriver();
        
        else if(webDriverId.equals(HTMLUNITDRIVER)) driver = new HtmlUnitDriver(true);
        
        // if the driver is still null then we got a non-matching or supported webDriverId
        if(driver == null) throw new Exception("Error: could not find a Selenium implementation for the webDriverId: " + webDriverId);
        
        // if we get here, things are good so navigate to the browserURL specified
        //webDriverBackedSelenium.get(browserURL);
        
        return driver;
        
    }
       
    
    
}