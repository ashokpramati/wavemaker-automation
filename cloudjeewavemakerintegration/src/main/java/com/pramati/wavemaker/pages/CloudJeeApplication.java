package com.pramati.wavemaker.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.pramati.wavemaker.page.BasePage;

public class CloudJeeApplication extends BasePage {

	private static final String CLOUDJEE_APP_LIST = "studio_deploymentDialog_deploymentDialog_cloudJeeAppList";
	private static final String APP_NAME ="div[id^='studio.deploymentDialog.deploymentDialog.cloudJeeAppList_ITEM_']";
	private static final String START_BTN = "studio_deploymentDialog_deploymentDialog_cloudJeeStartFromListButton";
	private static final String STOP_BTN = "studio_deploymentDialog_deploymentDialog_cloudJeeStopFromListButton";
	private static final String UNDEPLOY_BTN = "studio_deploymentDialog_deploymentDialog_cloudJeeUndeployFromListButton";
	private static final String CLOSE_BTN = "studio_deploymentDialog_deploymentDialog_cloudJeeAppListDialogCloseButton";

	private static final String WAIT_DIALOG_MSG = "wmWaitDialog";


	BasePage basePage = new BasePage();


	private static Logger log = Logger.getLogger(CloudJeeApplication.class);

	public WebElement Deployment() {
		waitForElementToEnableByClass("wmSizeNode");
		waitForElementLocatedByID(CLOUDJEE_APP_LIST, getTimeOutInSeconds());
		return basePage.getElementByID(CLOUDJEE_APP_LIST);
	}

	public void changeAPPStatus(String applicationName,String status){
		waitForElementLocatedByCSS(APP_NAME, getTimeOutInSeconds());
		List<WebElement> appName = Deployment().findElements(By.cssSelector(APP_NAME));
		System.out.println("In App Status change page");
		for (WebElement aName : appName) {
			List<WebElement> tdList = aName.findElements(By.tagName("td"));
			if((tdList.get(0).getText().equalsIgnoreCase(applicationName)) ){
				if(status.equalsIgnoreCase("Start")){
					tdList.get(0).click();
					basePage.getElementByID(START_BTN).click();
					waitForElementToDisableByClass(WAIT_DIALOG_MSG);
					break;
				}
				else if(status.equalsIgnoreCase("Stop")){
					tdList.get(0).click();
					basePage.getElementByID(STOP_BTN).click();
					waitForElementToDisableByClass(WAIT_DIALOG_MSG);					
					break;
				}
				else if(status.equalsIgnoreCase("Undeploy")){
					tdList.get(0).click();
					basePage.getElementByID(UNDEPLOY_BTN).click();
					new Deployment().clickOkBtn();
					waitForElementToDisableByClass(WAIT_DIALOG_MSG);
					break;
				}
				else if(status.equalsIgnoreCase("Close")){
					basePage.getElementByID(CLOSE_BTN).click();
					break;
				}
			}
			else {
				log.info("No Change can be done, As app is already in status "+status);

			}
		}
	}

	public String getAPPStatus(String applicationName){
		String statusText = null;
		waitForElementLocatedByCSS(APP_NAME, getTimeOutInSeconds());
		List<WebElement> appName = Deployment().findElements(By.cssSelector(APP_NAME));
		System.out.println("In App Status change page");
		for (WebElement aName : appName) {
			List<WebElement> tdList = aName.findElements(By.tagName("td"));
			if((tdList.get(0).getText().equalsIgnoreCase(applicationName)) ){
				statusText = tdList.get(1).getText();
				break;
			}
		}
		return statusText;
	}
}


