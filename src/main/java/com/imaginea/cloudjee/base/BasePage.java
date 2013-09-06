package com.imaginea.cloudjee.base;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * BasePage is the class that all other page classes should extend for web-based UI testing.
 * 
 * It obfuscates the driver- and interface-specific programming from the automated
 * test developer and offers general functionality and convenience methods to make
 * programming easier and faster.
 * <p>
 * <b>
 * The current interface used is <code>Selenium</code>, and the implementation class is
 * <code>DefaultSelenium</code>, but that may change in the future. If it does,
 * all that needs to be done is to change the implementation class with the new one
 * without any change to this class (other than changing the implementation reference) 
 * or any classes that extend this class.
 * </b>
 * <p>
 * <h3>Element Locators</h3>
 * <p>
 * Element Locators identify which HTML element a command refers to. The format of a locator is:
 * <p>
 * <ul><i>locatorType = argument</i></ul>
 * <p>
 * The following strategies for locating elements are supported:
 * <p>
 * <ul>
 * <b>identifier</b> = <i>id</i>
 * <br>
 * <ul>
 * Select the element with the specified @id attribute. If no match is found, select the first element 
 * whose @name attribute is id. (This is normally the default; see below.)
 * </ul>
 * <br>
 * <b>id</b> = <i>id</i>
 * <br>
 * <ul>Select the element with the specified @id attribute.
 * </ul>
 * <br>
 * <b>name</b> = <i>name</i>
 * <br>
 * <ul>
 * Select the first element with the specified @name attribute.
 * <ul>
 * <li> username
 * <li> name = username
 * </ul>
 * <br>
 * The name may optionally be followed by one or more element-filters, separated from the name 
 * by whitespace. If the filterType is not specified, value is assumed.
 * <ul>
 * <li>name=flavour value=chocolate
 * </ul>
 * </ul>
 * <br>
 * <b>dom</b> = <i>javascriptExpression</i>
 * <ul>
 * Find an element by evaluating the specified string. This allows you to traverse 
 * the HTML Document Object Model using JavaScript. Note that you must not return a 
 * value in this string; simply make it the last expression in the block.
 * <ul>
 * <li> dom=document.forms['myForm'].myDropdown
 * <li> dom=document.images[56]
 * <li> dom=function foo() { return document.links[1]; }; foo();
 * </ul>
 * </ul>
 * <br>
 * <b>xpath</b> = <i>xpathExpression</i>
 * <ul>
 * Locate an element using an XPath expression.
 * <il>
 * <li> xpath = //img[@alt='The image alt text']
 * <li> xpath = //table[@id='table1']//tr[4]/td[2]
 * </ul>
 * <br>
 * <b>link</b> = <i>textPattern</i>
 * <ul>
 * Select the link (anchor) element which contains text matching the specified pattern.
 * <ul>
 * <li> link = The link text
 * </ul>
 * </ul>
 * <br>
 * <b>css</b> = <i>cssSelectorSyntax</i>
 * <ul>Select the element using css selectors. Please refer to CSS2 selectors, CSS3 selectors
 * for more information. You can also check the TestCssLocators test in the selenium test suite 
 * for an example of usage, which is included in the downloaded selenium core package.
 * <ul>
 * <li> css = a[href="#id3"]
 * <li> css = span#firstChild + span
 * </ul>
 * Currently the css selector locator supports all css1, css2 and css3 selectors except namespace in 
 * css3, some pseudo classes(:nth-of-type, :nth-last-of-type, :first-of-type, :last-of-type, 
 * :only-of-type, :visited, :hover, :active, :focus, :indeterminate) and pseudo elements(::first-line, 
 * ::first-letter, ::selection, ::before, ::after). 
 * </ul>
 * </ul>
 * <p>
 * Without an explicit locator prefix, you can use the following default strategies:
 * <ul> 
 * <li> <b>dom</b>, for locators starting with "document."
 * <li> <b>xpath</b>, for locators starting with "//"
 * <li> <b>identifier</b>, otherwise
 * </ul>
 * <p>
 * <h3>Element Filters</h3>
 * <p>
 * <ul>
 * Element filters can be used with a locator to refine a list of candidate elements. They are 
 * currently used only in the 'name' element-locator.
 * <p>
 * Filters look much like locators, ie.
 * <ul><i>filterType = argument</i></ul>
 * <p>
 * Supported element-filters are:
 * <ul><i>value = valuePattern</i>
 * <p>
 * <ul>
 * Matches elements based on their values. This is particularly useful for refining a list of similarly-named toggle-buttons.
 * </ul>
 * </ul>
 * <p>
 * <ul><i>index = index</i>
 * <ul>
 * Selects a single element based on its position in the list (offset from zero).
 * </ul>
 * </ul>
 * </ul>
 * <p>
 * <h3>String-match Patterns</h3>
 * <p>
 * Various Pattern syntaxes are available for matching string values:
 * <p>
 * <ul>
 * <b>glob</b>:pattern
 * <br>
 * <ul>Match a string against a "glob" (aka "wildmat") pattern. "Glob" is a kind of limited regular-expression 
 * syntax typically used in command-line shells. In a glob pattern, "*" represents any sequence of characters, 
 * and "?" represents any single character. Glob patterns match against the entire string.
 * </ul>
 * <b>regexp</b>:regexp
 * <ul>Match a string using a regular-expression. The full power of JavaScript regular-expressions is available.
 * </ul>
 * <b>exact</b>:string
 * <ul>
 * Match a string exactly, verbatim, without any of that fancy wildcard stuff.
 * </ul>
 * </ul>
 * <p>
 * If no pattern prefix is specified, then it is assumed that it's a "glob" pattern. 
 * 
 * @author sshyamala
 */
public class BasePage extends TestCase {
    
    /**
     * the properties file path
     */
    protected final String PROPERTIESFILEPATH = "./conf/seleniumconfiguration.properties";
    
    /**
     * JavaScript library that the application is using. This is required to query the active ajax connection
     */
    private String jsLibrary = null;
    
    /**
     * Perform screen shot. default is <code>false</code>.
     */
    private boolean doScreenshot = false;
    
    /**
     * Default timeout for page loading in milliseconds (ms). Default is <code>50000</code> (50 seconds). 
     */
    protected String timeout = "50000";
    
    /**
     * Selenium interface instance.
     */
    protected WebDriver driver=null;
    
    /**
     * This string is prepended to a wait for condition call if it does not
     * start with "selenium."
     */
    protected final String WAITFORCONDITIONPREPENDSTRING = "selenium."; 
    
    protected CommonProperties properties = CommonProperties.getInstance();
   	
    /**
     *  logging object, logging conf is defined in conf/log4j.properties
     */
    protected static Logger log = Logger.getLogger(BasePage.class);
    
    /**
     * Network capture type dump file format - xml.
     */
    public static final String NETWORKCAPTURETYPE_XML = "xml";
    
    /**
     * Network capture type dump file format - json.
     */
    public static final String NETWORKCAPTURETYPE_JSON = "json";
    
    /**
     * Network capture type dump file format - plain.
     */
    public static final String NETWORKCAPTURETYPE_PLAIN = "plain";
    
    
    /**
     * Maximize Browser Window. Default is <code>true</code>.
     */
    protected boolean maximizeBrowserWindow = true;
    
    /**
     * Capture selenium commands. Default is <code>false</code>.
     */
    protected boolean captureSeleniumCommands = false;
    
   
    
    /**
     * Initialize this class to prepare for testing. Loads the properties file 
     * located at <code>conf/seleniumconfiguration.properties</code>.
     * <p>
     * This method also calls "start" method.
     */
    protected void init() throws Exception { 

        WebDriverSeleniumFactory webDriverSeleniumFactory = new WebDriverSeleniumFactory();
        
        try {
            
            // load the properties
            this.properties.load(PROPERTIESFILEPATH);
            
            log.info("Properties loaded at: " + PROPERTIESFILEPATH + ", size: " + this.properties.size());
                                    
            // Always use the default selenium, some selenium 1 commands are not supported in WebDriver
            //log.info("Preparing to load selenium driver with id: " + properties.get("seleniumDriverId"));
            //if(WebDriverSeleniumFactory.DEFAULT.equals(properties.get("seleniumDriverId"))) {
                
                this.driver =webDriverSeleniumFactory.getSelenium(properties.get("iedriver"));
                
               
                
                if(properties.get("timeout") != null) { this.timeout = properties.get("timeout"); }
                
                this.driver.manage().timeouts().implicitlyWait(Long.parseLong(this.timeout), TimeUnit.SECONDS);
                
                log.info("Timeout is set to: " + this.timeout);
             // set the maximizeBrowserWindow
                if(properties.getProperty("maximizeBrowserWindow") != null) { this.maximizeBrowserWindow = (new Boolean(properties.getProperty("maximizeBrowserWindow"))).booleanValue(); }
                
                if(this.maximizeBrowserWindow) maximizeWindow();
                
                log.info("Maximize browser set to: " + this.maximizeBrowserWindow);
                
                // screenshot properties
                if(properties.getProperty("doScreenshots") != null) { this.doScreenshot  = (new Boolean(properties.getProperty("doScreenshots"))).booleanValue(); }
                
                log.info("Do screenshots on each page load: " + this.doScreenshot);
                
                // set the captureSeleniumCommands
                if(properties.getProperty("captureSeleniumCommands") != null) { this.captureSeleniumCommands = (new Boolean(properties.getProperty("captureSeleniumCommands"))).booleanValue(); }
                
                log.info("Capture selenium commands: " + this.captureSeleniumCommands);
                
                jsLibrary = properties.getProperty("jsLibrary");
                log.info("jsLibrary: " + this.jsLibrary);        
                
                Runtime run = Runtime.getRuntime();
                try {
        			Process pp=run.exec(properties.get("AuthenticationFile"));
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}   
               
            
            open(properties.get("baseURL"));
            
        }
        catch(Exception e) { 
        
            log.error(e);
            throw e; 
        
        }

    }
           
    /**
     * return the username set in property config
     */
    protected String getUsername() { return properties.getProperty("username"); }
    
    /**
     * return the password set in property config     
     */
    protected String getPassword() { return properties.getProperty("password"); }
    
    /**    
     * @return: true if using internet explorer browser
     */
    protected boolean isBrowserIE() {
    	
    	String browser = properties.getProperty("browser");
    	return browser.contains("iexplore");
    }
    /**
     * Get a properties object given a filename.
     * 
     * @param filename The path to the file to use (relative or absolute path)
     * 
     * @return The properties object reference
     * 
     * @throws Exception 
     *
    protected Properties getProperties(String filename) throws Exception {
        
        Properties properties = new Properties();
        
        try { properties.load(new FileInputStream(filename)); }
        catch(Exception e) { throw e; }
        
        return properties;
        
    }
    */
    
    /**
     * Specifies the amount of time that Selenium will wait for actions to complete.
     * <p>
     * Actions that require waiting include "open" and the "waitFor*" actions.
     * <p>
     * The default timeout is 30 seconds. 
     * 
     * @param timeout The timeout in milliseconds, after which the action will return with an error 
     */
    protected void setTimeout(String timeout) { driver.manage().timeouts().implicitlyWait(Long.parseLong(timeout), TimeUnit.SECONDS);; }
    
    /**
     * Return the default timeout. This can be set by assigning the value for
     * <code>timeout</code> in the <code>seleniumconfiguration</code> file.
     * 
     * @return The default timeout 
     */
    protected String getTimeout() { return this.timeout; }
    	
    protected String getJSLibrary() { return this.jsLibrary; }
    
    /**
     * Return the selenium interface.
     * 
     * @return The selenium interface
     */   
    
    protected WebDriver getDriver(){ return driver;}
    

    /**
     * Opens an URL in the test frame. 
     * <p>
     * This accepts both relative and absolute URLs. 
     * <p>
     * The "open" command waits for the page to load before proceeding, ie. 
     * the "AndWait" suffix is implicit. 
     * <p>
     * Note: The URL must be on the same domain as the runner HTML due to
     * security restrictions in the browser (Same Origin Policy). If you need
     * to open an URL on another domain, use the Selenium Server to start a
     * new browser session on that domain. 
     * 
     * @param url The URL to open; may be relative or absolute.
     */
    protected void open(String url) { 
        
     
        driver.get(url); 
        ((JavascriptExecutor) driver).executeScript("window.focus();");
        
    
    }
    
    protected String clickPopUp(By by)
    {
    	
    	String newWindow=null;
    	driver.findElement(by).click();
    	Set<String> availableWindows = driver.getWindowHandles(); 
    	System.out.println("Handle Size:" +availableWindows.size());
    		for (String windowId : availableWindows) { 
    			System.out.println(driver.switchTo().window(windowId).getTitle());
    			newWindow=windowId;
    			  }
    		return newWindow;
    }
    
    protected String getWindowId()
    {
    	return driver.getWindowHandle();
    }
    protected void switchTo(String wId)
    {
    	driver.close();
    	driver.switchTo().window(wId); 
    }
    
    /**
     * Opens a popup window (if a window with that ID isn't already open). 
     * <p>
     * After opening the window, you'll need to select it using the selectWindow command.
     * 
     * @param windowId The JavaScript window ID of the window to select
     */
    protected void openWindow(String windowId) { 
        
        
        driver.switchTo().window(windowId);       
        
        
    
    }  
    /**
     * Opens a popup Frame 
     * <p>
     * After opening the frame, you'll need to select it using the selectframe command.
     * 
     * @param frameName 
     */
    protected void openFrame(String frameName) { 
        
     
        driver.switchTo().frame(frameName);       
        
        
    
    }    

    
    /**
     * Wait for a page to load for <code>s</code> number of milliseconds (n).
     * 
     * @param s The number of milliseconds (ms) 
     */
    protected void waitforPageToLoad(int n)
    {
    	driver.manage().timeouts().implicitlyWait(n, TimeUnit.SECONDS);
    }
    
    protected void waitForPageToLoad(String s) {
    	waitForPageToLoad(s, true);
    }
    protected void waitForPageToLoad(String s, boolean checkAjaxComplete) {    
       
        
        driver.manage().timeouts().implicitlyWait(Long.parseLong(s), TimeUnit.SECONDS);
        
        //if (checkAjaxComplete && (jsLibrary!=null && jsLibrary.length()>0))
        	//waitForAJaxCompletion(s);
    
    }
    
    protected void waitForElementPreset(By by,long l)
    {
    	new WebDriverWait(driver, l).until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    
    /**
     * Assert a title equals exactly a string specified by <code>s</code>.
     * 
     * @param s The title
     */
    protected void assertTitle(String s) { 
        
              
        assertEquals("Expect HTML title '" + s + "' but got '" + driver.getTitle() + "'.", s, driver.getTitle());
        
    }
    protected boolean verifyTitle(String s) { 
        
        boolean check=s.equals(driver.getTitle());
        return check;
        
    }

    /**
     * Return the page title.
     * 
     * @return The page title
     */
    protected String getTitle() { return driver.getTitle(); }

    /**
     * Assert that text specified in <code>s</code> exists in the document.
     * 
     * @param s The text that should be present
     */
    protected void assertText(String s) { 
        
    
        assertTrue(driver.getPageSource().contains(s));        
    
    }

    /**
     * Simulates the user clicking the "close" button in the titlebar of a popup window or tab.
     */
    protected void close() { 
        
          
        driver.close();        
    
    }

    /**
     * Inherits the session from the previous page class.
     * 
     * @param basePage 
     */
    protected void inheritSession(BasePage basePage) { 
        
        driver = basePage.getDriver();        		

        timeout = basePage.getTimeout();
        
        jsLibrary = basePage.getJSLibrary();
        
        
                
    }

    /**
     * Return the html source.
     * 
     * @return The html source
     */
    protected String getHtmlSource() { return driver.getPageSource(); 		 }
    
    /**
     * Assert text <code>s</code> in the HTML source
     * 
     * @param s The text string that should be in the HTML source 
     */
    protected void assertTextInHtmlSource(String s) { 
        
      
        
        assertTrue("Expect text '"+s+"' in html source but not found.", getHtmlSource().contains(s)); 
    
    }

    /**
     * Type text <code>value</code> into an element indicated by <code>locator</code>.
     * 
     * @param locator
     * @param value 
     */
    public void type(By locator, String value) { 
        
       
        driver.findElement(locator).sendKeys(value);
        //selenium.type(locator, value); 
    
    }

    /**
     * Simulates typing a key at the end of value.     
     * 
     * @param keycode 
     */
    public void typeNative(By locator, String value) {
    	
    	
    	
    	type(locator, value);		
		//focus(locator);    	
		
    }
		
	public void typeAndEnterNative(By locator, String value) {
		
		
		
		typeNative(locator, value);

    }
    
    
    /**
     * Clicks on a link, button, checkbox or radio button. 
     * <p>
     * If the click action causes a new page to load (like a link usually does), call waitForPageToLoad. 
     * 
     * @param locator An element locator
     */
    public void click(By locator) { 
    
       
        driver.findElement(locator).click();                
       
    
    }
    
    /**
     * Ends the test session, killing the browser.
     */
    public void stop() { 
    
       
        driver.quit();       
    
    }
    
    /**
     * Runs the specified JavaScript snippet repeatedly until it evaluates to "true". 
     * The snippet may have multiple lines, but only the result of the last line will be considered.
     * <p>
     * Note that, by default, the snippet will be run in the runner's test window, 
     * not in the window of your application. To get the window of your application, 
     * you can use the JavaScript snippet selenium.browserbot.getCurrentWindow(), 
     * and then run your JavaScript in there
     * 
     * @param script The JavaScript snippet to run
     * @param timeout The timeout in milliseconds, after which this command will return with an error
     *      
     */
    public void waitForCondition(final String script, String timeout) {
    	/*EventFiringWebDriver fire = new EventFiringWebDriver(driver);
    	fire.executeScript(arg0, arg1)*/
        
    }
    
   
     
    /**
     * wait until there are no more active ajax connection
     * @param timeout
     * Reference: http://agilesoftwaretesting.com/selenium-wait-for-ajax-the-right-way/
     */
    public void waitForAJaxCompletion(String timeout) {
    	
    	    	
    	assertTrue("'jslibrary' is not set in properties config before calling waitForAJaxCompletion", (jsLibrary!=null && jsLibrary.length()>0));
    	
     	String ajaxObject = "";
    	String openConnVariable = "";
    	if ("prototype".equalsIgnoreCase(jsLibrary))
    	{
    		ajaxObject = WAITFORCONDITIONPREPENDSTRING + "browserbot.getCurrentWindow().Ajax";
    		openConnVariable = "activeRequestCount";
    	}
    	else if ("jquery".equalsIgnoreCase(jsLibrary))
    	{
    		ajaxObject = WAITFORCONDITIONPREPENDSTRING + "browserbot.getCurrentWindow().jQuery";
    		openConnVariable = "active";
    	}
    	    	
    	assertTrue("Unknown 'jslibrary' set in properties config, valid values are prototype|jquery", (ajaxObject!=null && ajaxObject.length()>0));
    	
    	log.debug("1.typeof ajaxObject = '" + getEval("typeof(" + ajaxObject + ")") + "'" );    	  	    	
    	log.debug("2.typeof ajaxObject = '" + getEval("typeof(" + ajaxObject + ")") + "'" );
    	
    	waitForCondition(ajaxObject+"."+openConnVariable + " == 0;", getTimeout());     	    	
    }
    
    /**
     * Verifies that the specified text pattern appears somewhere on the rendered page shown to the user. 
     * 
     * Excludes text in tags.
     * 
     * @param s the pattern to match with the text of the page 
     * 
     * @return <code>true</code> if the pattern matches the text, <code>false</code> otherwise
     */
    public boolean isTextPresent(String s) { 
    
       
        
        return driver.getPageSource().contains(s);        		
    
    }
    
    /**
     * Select an option from a drop-down using an option locator.
     * <p>
     * Option locators provide different ways of specifying options of an HTML 
     * Select element (e.g. for selecting a specific option, or for asserting 
     * that the selected option satisfies a specification). 
     * <p>
     * There are several forms of Select Option Locator.
     * <p>
     * <b><code>label</code></b> = <i>labelPattern</i>
     * <br>
     * <ul>Matches options based on their labels, i.e. the visible text. (This is the default.)
     * <br>
     * <ul><li> label = regexp:^[Oo]ther</ul>
     * </ul>
     * <br>
     * <b><code>value</code></b> = <i>valuePattern</i>
     * <br>
     * <ul>
     * Matches options based on their values.
     * <br>
     * <ul><li> value = other</ul>
     * </ul>
     * <br>
     * <b><code>id</code></b> = <i>id</i>
     * <ul>
     * Matches options based on their ids.
     * <br>
     * <ul><li> id = option1</ul>
     * </ul>
     * <br>
     * <b><code>index</code></b> = <i>index</i>
     * <ul>
     * Matches an option based on its index (offset from zero).
     * <br>
     * <ul><li> index = 2</ul>
     * </ul>
     * <p>
     * If no option locator prefix is provided, the default behaviour is to match on label. 
     * 
     * @param locator an Element locator identifying a drop-down menu
     * @param value An option locator (a label by default)
     */
    public void select(By locator, String value) { 
        
       
        
        driver.findElement(locator).getAttribute(value);     }
    
    /**
     * Gets option value (value attribute) for selected option in the specified select element. 
     * 
     * @param locator An element locator
     * 
     * @return The text of the element
     */
    public String getSelectedValue(By locator) { 
    
       
        
        return driver.findElement(locator).getText();
        		
    
    }
    
    public String getSelectedLabel(By locator) { 
        
     
        
        return driver.findElement(locator).getTagName();
        		
    }
    
    /**
     * Gets the text of an element. This works for any element that contains text. 
     * This command uses either the textContent (Mozilla-like browsers) or the 
     * innerText (IE-like browsers) of the element, which is the rendered text 
     * shown to the user. 
     * 
     * @param locator An element locator
     * 
     * @return The text of the element
     */
    public String getText(By locator) { 
    
               
        return driver.findElement(locator).getText();
    
    }
    
    /**
     * Resize window to take up the entire screen.
     * 
     * @param windowName The name of the window to be enlarged
     */
    //public void windowMaximize(String windowName)  { selenium.windowMaximize(windowName); }
    
    /**
     * Gives focus to a window.
     * 
     * @param windowName The name of the window to be given focus 
     */
    //public void windowFocus(String windowName) { selenium.windowFocus(windowName); }
    
   
    
    public void alertHandle()
    {
    	driver.switchTo().alert().accept();
    }
    public String getAlertText()
    {
    	return driver.switchTo().alert().getText();
    }
    public void promptHandle(String text)
    {
    	driver.switchTo().alert().sendKeys(text);
    }
    /**
     * check a toggle-button (checkbox/radio).
     * 
     * @param The locator an element locator 
     */
    protected void check(By locator) { 
    
       
        WebElement checkBox = driver.findElement(locator);

        if(!checkBox.getAttribute("type").toLowerCase().equals("checkbox")){

            try {
				throw new Exception("This element is not a checkbox!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }

        if(checkBox.isSelected()){

            checkBox.click();

        }

    
    }
    
    /**
     * Uncheck a toggle-button (checkbox/radio).
     * 
     * @param The locator an element locator 
     */
    protected void uncheck(By locator) { 
    
       
        WebElement checkBox = driver.findElement(locator);

        if(!checkBox.getAttribute("type").toLowerCase().equals("checkbox")){

            try {
				throw new Exception("This element is not a checkbox!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }

        if(!checkBox.isSelected()){

            checkBox.click();

        }

    
    }
    
    /**
     * Submit the specified form. This is particularly useful for forms without 
     * submit buttons, e.g. single-input "Search" forms.
     * 
     * @param formLocator An element locator for the form you want to submit
     */
    public void submit(By formLocator) {     
       
        
        driver.findElement(formLocator).submit();
    
    }
    
    /**
     * Set execution speed (i.e., set the millisecond length of a delay which 
     * will follow each selenium operation). 
     * <p>
     * By default, there is no such delay, i.e., the delay is 0 milliseconds.
     * 
     * @param value The number of milliseconds to pause after operation
     */
    public void setSpeed(int value) { 
    
        
        driver.manage().timeouts().implicitlyWait(value, TimeUnit.SECONDS);        
    
    }
    
    /**
     * Writes a message to the status bar and adds a note to the browser-side log.
     * <p>
     * If logLevelThreshold is specified, set the threshold for logging to that level (debug, info, warn, error).
     * <p>
     * (Note that the browser-side logs will not be sent back to the server, and are invisible to the Client Driver.)
     * 
     * @param context The message to be sent to the browser
     * @param logLevelThreshold One of "debug", "info", "warn", "error", sets the threshold for browser-side logging
     */
    //public void setContext(String context, String logLevelThreshold)  { selenium.setContext(context, logLevelThreshold); }
    
   
    
    /**
     * Selects a popup window. 
     * <p>
     * Once a popup window has been selected, all commands go to that window. 
     * To select the main window again, use null as the target.
     * <p>
     * There are several strategies for finding the window object referred to by the "windowID" parameter.
     * <p>
     * 1.) If windowID is null, then it is assumed the user is referring to the original window instantiated by the browser).
     * <p>
     * 2.) If the value of the "windowID" parameter is a JavaScript variable name in the current 
     * application window, then it is assumed that this variable contains the return value 
     * from a call to the JavaScript window.open() method.
     * <p>
     * 3.) Otherwise, it looks in a hash it maintains that maps string names to window objects. 
     * Each of these string names matches the second parameter "windowName" past to the JavaScript method
     * window.open(url, windowName, windowFeatures, replaceFlag) (which gets intercepted).
     * <p>
     * If you're having trouble figuring out what is the name of a window that you want to 
     * manipulate, look at the selenium log messages which identify the names of windows created 
     * via window.open (and therefore intercepted). You will see messages like the following 
     * for each window as it is opened:
     * <p>
     * <code>debug: window.open call intercepted; window ID (which you can use with selectWindow()) is "myNewWindow"</code>
     * 
     * @param windowId The JavaScript window Id of the window to select
     */
    protected void selectWindow(String windowId) { 
    
      
        
        driver.switchTo().window( windowId );
    
    }
    
    /**
     * Selects a frame within the current window. (You may invoke this command
     * multiple times to select nested frames.) To select the parent frame, 
     * use "relative=parent" as a locator; to select the top frame, use "relative=top".
     * <p>
     * You may also use a DOM expression to identify the frame you want directly, 
     * like this: dom=frames["main"].frames["subframe"]
     * 
     * @param locator An element locator identifying a frame or iframe 
     */
    protected void selectFrame(String locator) { 
    
       
        
        driver.switchTo().frame(locator );
    
    }
    
    /**
     * Determines if the specified element is visible. 
     * <p>
     * An element can be rendered invisible by setting the CSS "visibility" 
     * property to "hidden", or the "display" property to "none", either for 
     * the element itself or one if its ancestors. This method will fail if 
     * the element is not present. 
     * 
     * @param locator An element locator
     * 
     * @return <code>true</code> if the input element is editable, <code>false</code> otherwise
     */
    protected boolean isVisible(By locator) { 
    
      
        
        return driver.findElement(locator).isDisplayed(); 
    
    }
    
    
    /**
     * Determines whether the specified input element is editable, ie hasn't 
     * been disabled. This method will fail if the specified element isn't an input element. 
     * 
     * @param locator An element locator
     * 
     * @return <code>true</code> if the input element is editable, <code>false</code> otherwise
     */
    protected boolean isEditable(By locator) { 
    
       
        return driver.findElement(locator).isEnabled(); 
    
    }
    
    
    /**
     * Gets whether a toggle-button (checkbox/radio) is checked. 
     * <p>
     * Fails if the specified element doesn't exist or isn't a toggle-button.
     * 
     * @param locator An element locator pointing to a checkbox or radio button 
     * 
     * @return <code>true</code> if the checkbox is checked, <code>false</code> otherwise
     */
    protected boolean isChecked(By locator)  { 
    
       
        return driver.findElement(locator).isSelected();
    
    }
    
    
    /**
     * Simulates the user clicking the "back" button on their browser.
     */
    public void goBack() { 
    
        
        
        driver.navigate().back(); 
    
    }
    
    /**
     * Gets the entire text of the page. 
     * 
     * @return The entire text of the page
     */
    protected String getBodyText() { 
    
      
        
        return driver.getPageSource(); 
    
    }
    
    /**
     * Gets the result of evaluating the specified JavaScript snippet. 
     * <p>
     * The snippet may have multiple lines, but only the result of the last line will be returned.
     * <p>
     * Note that, by default, the snippet will run in the context of this object itself, 
     * and window will refer to the top-level runner test window, not the window of your application.
     * 
     * @param script The JavaScript snippet to run
     * 
     * @return The results of evaluating the snippet
     */
    protected String getEval(String script) { 
    
      
        
        return (String) ((JavascriptExecutor) driver).executeScript(script);

    
    }
     
    /**
     * Gets the value of an element attribute. 
     * 
     * @param attributeLocator an element locator followed by an attribute
     * 
     * @return the value of the specified attribute 
     */
    protected String getAttribute(By attributeLocator,String  att) { 
    
       
        return driver.findElement(attributeLocator).getAttribute(att);
    
    }   
   
    
    
    /**
     * @param xpath
     * @return Returns the number of nodes that match the specified xpath, eg. "//table" would give the number of tables.
     */
    protected int getXpathCount(By xpath) { 
    
     
        
        return driver.findElements(xpath).size();
    
    }
    
   
    
    /**
     * Maximize the browser window.
     */
    public void maximizeWindow() { 
        
       
        //driver.manage().window().maximize();
        driver.manage().window().setPosition(new Point(0,0));
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dim = new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());
        driver.manage().window().setSize(dim);
       


    
    }
    
    
    /**     
     * Verifies that the specified element is somewhere on the page. 
     * 
     * @param locator
     * @return 
     */
       public boolean isElementPresent(By locator){   
    	try{   
    		//driver.manage().timeouts().implicitlyWait(2, TimeUnit.MINUTES);
    		//driver.switchTo().frame(driver.findElement(By.xpath("//html/body/form/div[8]/div/div[2]/div[2]/div[2]/div/div/div/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr/td[5]/table/tbody/tr[2]/td/div/div/iframe")));
    		driver.findElement(locator);    
    	return true;   }
    	catch(NoSuchElementException e){    
    		return false;   }  }
    
    /**
     * Asserts that the specified element is somewhere on the page. 
     * 
     * @param message - message to describe assertion in case of fail
     * @param locator
     */
    protected void assertElementPresent(String message, By locator) { 
        
        
        
        assertTrue(message, isElementPresent(locator)); 
    
    }
    
    /**
     * Asserts that the specified element is not on the page. 
     * 
     * @param message - message to describe assertion in case of fail
     * @param locator
     */
    protected void assertElementNotPresent(String message, By locator) { 
    
    assertFalse(message, isElementPresent(locator)); 
    
    }
    
    
    
    
    
    /**
     * Create the screenshot in JPG format. Use this method for screen capture
     * without using Selenium or if the selenium object being used does not
     * support browser window capture.
     * 
     * @param screenshotFilename the name of the screenshot image file
     * 
     * @throws Exception 
     */
    public void createScreenshot(String screenshotFilename) throws Exception {
       
    	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    	// Now you can do whatever you need to do with it, for example copy somewhere
    	FileUtils.copyFile(scrFile, new File(screenshotFilename+".jpg"));
       
    }
    
    /**
     * Create the screenshot in JPG format. This method uses selenium 
     * capabilities to create a screenshot of the browser window only.
     * 
     * Please be sure that the selenium object being used supports screenshot
     * feature.
     * 
     * @param screenshotFilename the name of the screenshot image file
     * 
     * @throws Exception 
     */ 
    public void createScreenshotSelenium(String screenshotFilename) throws Exception {
       
    	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    	// Now you can do whatever you need to do with it, for example copy somewhere
    	FileUtils.copyFile(scrFile, new File(screenshotFilename+".jpg"));
       
       
   }
    
    /**
     * Focus the element identified by <code>locator</code>.
     * 
     * @param locator 
     */
    public void focus(String script) { 
    
      
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript (script+".focus()");
    
    }
    

    /**
     * Simulates typing a key identified by <code>keycode</code> on the keyboard.
     * To send a keystroke to a particular element, focus on the element first before runnning this command
     * 
     * @param keycode 
     */
    public void keyPressNative(By locator,Keys key) { 
        
       
        driver.findElement(locator).sendKeys(key);
        
    
    }
    
    /**      
     * @param locator
     * @return Gets all option labels in the specified select drop-down. 
     */
    public List<WebElement> getSelectOptions(By locator) {
    
    	
    		
    	return driver.findElement(locator).findElements(By.tagName("option"));
    	
    }
    
    /**     
     * @param locator
     * @param optionLabel
     * @return 
     */
    public boolean hasSelectOption(By locator, String optionLabel) {
    	
    	List<WebElement> options = getSelectOptions(locator);
    	return options.contains(optionLabel);
    }
    
    
    
    /**
     * Simulates keystroke events on the specified element, as though you typed the value 
     * key-by-key.This is a convenience method for calling keyDown, keyUp, keyPress for 
     * every character in the specified string; this is useful for dynamic UI widgets 
     * that require explicit key events.
     * 
     * @param locator an element locator
     * @param value  the value to type
     */
    public void typeKeys( By locator, String value ){
    	
    	
        
    	driver.findElement(locator).sendKeys(value);
        
    } 
    
    /**
     * Causes the currently executing thread to sleep (temporarily cease execution) 
     * for the specified number of milliseconds.
     * 
     * @param millis delay in milliseconds 
     */
    public void sleep(long millis) {
        
        try {        	
        	Thread.sleep(millis);
        	//PerformanceCapture.getInstance().addSleepTime(millis);        	
       	}
        catch(Exception e) { log.error(e); }
        
    }
    
    
    
    /**
     * 
     * @return the name of invoking Class
     */
    private String getPageName() {
    	
    	String fullClassName = getClass().getName();
    	int i = fullClassName.lastIndexOf(".");
    	String className = fullClassName.substring(i+1);
    	
    	return className;
    }   
    
   
}

