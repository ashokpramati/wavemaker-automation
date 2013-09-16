package com.pramati.wavemaker.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.pramati.wavemaker.page.BasePage;

public class Deployment extends BasePage{

	private static final String WAVEMAKER_CLOUDACCOUNT = "studio_deploymentDialog_deploymentDialog_cjLoginDialog";
	private static final String USERNAME = "studio_deploymentDialog_deploymentDialog_loginDialogUserEditor";
	private static final String PASSWORD = "studio_deploymentDialog_deploymentDialog_loginDialogPasswordEditor";
	private static final String OK_BTN = "studio_deploymentDialog_deploymentDialog_cjLogonOkButton";
	private static final String CANCEL_BTN = "studio_deploymentDialog_deploymentDialog_cjLoginCancelButton";

	private static final String SETTING_WINDOW = "studio_deploymentDialog_deploymentDialog_settingLayers_client";
	private static final String DEPLOYMENT_EDITOR = "studio_deploymentDialog_deploymentDialog_cjDeploymentNameEditor";
	private static final String DEPLOYMENT_TYPE = "studio_deploymentDialog_deploymentDialog_cjDeploymentTypeEditor";
	private static final String DEPLOYMENT_NAME = "studio_deploymentDialog_deploymentDialog_cjNameEditor";
	private static final String DEPLOYMENT_URL = "studio_deploymentDialog_deploymentDialog_cjUrlpanel";

	private static final String BUTTON_BAR = "studio_deploymentDialog_deploymentDialog_buttonBar";
	private static final String DEPLOY_NOW = "studio_deploymentDialog_deploymentDialog_deployButton";
	private static final String SAVE_BTN  = "studio_deploymentDialog_deploymentDialog_saveButton";
	private static final String CLOSE_BTN  = "studio_deploymentDialog_deploymentDialog_closeButton";
	private static final String MANAGE_CLOUD_APP  = "studio_deploymentDialog_deploymentDialog_manageUndeployButton";


	private static final String CONFIRM_DIALOG = "app_confirmDialog";
	private static final String CONFIRM_CANCEL_BTN ="app_confirmDialog_button2";
	private static final String CONFIRM_OK_BTN= "app_confirmDialog_button1";

	private static final String  CJCANCELBTN= "studio_deploymentDialog_deploymentDialog_cjLoginCancelButton";
	private static final String  CJOKBTN= "studio_deploymentDialog_deploymentDialog_cjLogonOkButton";

	private static final String WAIT_DIALOG_MSG ="wmWaitMessage";
	private static final String WAIT_DIALOG = "wmWaitDialog";

	private static final String STUDIO_DIALOG = "studio_dialog";

	private static final String ALERT_TEXT = "a[target='_NewWindow']";

	BasePage basePage = new BasePage();

	public WebElement Deployment() {
		waitForElementLocatedByID(WAVEMAKER_CLOUDACCOUNT, getTimeOutInSeconds());
		return basePage.getElementByID(WAVEMAKER_CLOUDACCOUNT);
	}

	public void setUserPassword(String username,String password){
		WebElement userEle = Deployment().findElement(By.id(USERNAME)).findElement(By.cssSelector("input[type='text']"));
		userEle.clear();
		userEle.sendKeys(username);
		WebElement passEle = Deployment().findElement(By.id(PASSWORD)).findElement(By.cssSelector("input[type='password']"));
		passEle.clear();
		passEle.sendKeys(password);
		Deployment().findElement(By.id(OK_BTN)).click();
		waitForElementToDisableByClass(WAIT_DIALOG_MSG);
	}

	private WebElement getButtonBarParentElement(){
		waitForElementLocatedByID("studio_deploymentDialog_deploymentDialog_buttonBar2", getTimeOutInSeconds());
		return basePage.getElementByID("studio_deploymentDialog_deploymentDialog_buttonBar2");
	}
	/**
	 * Click on Wavemaker Cloud Account Ok button
	 * 
	 */
	public String clickCloudAccountOkBtn(){
		waitForElementLocatedByID(CONFIRM_OK_BTN, getTimeOutInSeconds());
		basePage.getElementByID(CONFIRM_OK_BTN).click();
		/*waitForElementLocatedByID(CJOKBTN, getTimeOutInSeconds());
		basePage.sleep(3000);
		basePage.getElementByID(CJOKBTN).click();*/
		waitForElementToDisableByID(STUDIO_DIALOG);
		return basePage.getElementByCSS(ALERT_TEXT).getText();
	}

	/**
	 * Click on Wavemaker Cloud Account Cancel button
	 * 
	 */
	public void clickCloudAccountCancelBtn(){
		waitForElementLocatedByID(CONFIRM_OK_BTN, getTimeOutInSeconds());
		basePage.getElementByID(CONFIRM_OK_BTN).click();
		Deployment().findElement(By.id(CJCANCELBTN)).click();
	}

	private WebElement getSettingWindowEle(){
		waitForElementLocatedByID(SETTING_WINDOW, getTimeOutInSeconds());
		return basePage.getElementByID(SETTING_WINDOW);
	}

	public String getDeploymentName(){

		return getSettingWindowEle().findElement(By.id(DEPLOYMENT_EDITOR)).findElement(By.tagName("input")).getText();
	}

	public String getDeploymentType(){
		return getSettingWindowEle().findElement(By.id(DEPLOYMENT_TYPE)).findElement(By.cssSelector("div[role='textbox'")).getText();
	}

	public String getDeploymentAPPName(){
		return getSettingWindowEle().findElement(By.id(DEPLOYMENT_NAME)).findElement(By.cssSelector("div[role='presentation'")).getText();
	}

	public String getDeploymentAPPURL(){
		waitForElementLocatedByID(DEPLOYMENT_URL, getTimeOutInSeconds());
		return getSettingWindowEle().findElement(By.id(DEPLOYMENT_URL)).findElement(By.cssSelector("div[role='textbox'")).getText();
	}

	private WebElement getButtonParentEle(){
		waitForElementLocatedByID(BUTTON_BAR, getTimeOutInSeconds());
		return basePage.getElementByID(BUTTON_BAR);
	}

	public void clickDeployNowBtn(){
		getButtonParentEle().findElement(By.id(DEPLOY_NOW)).click();
	}

	public void clickSaveBtn(){
		getButtonParentEle().findElement(By.id(SAVE_BTN)).click();
	}

	public void clickCloseBtn(){
		getButtonParentEle().findElement(By.id(CLOSE_BTN)).click();
	}



	public void clickManageWMCloudApBtn(){
		getButtonParentEle().findElement(By.id(MANAGE_CLOUD_APP)).click();
	}

	/**
	 * Deployment Confirm dialog box
	 * 
	 * @return Parent webelement of Confirm dialog box
	 */
	private WebElement getConfirmWindowEle(){
		waitForElementLocatedByID(CONFIRM_DIALOG, getTimeOutInSeconds());
		return basePage.getElementByID("CONFIRM_DIALOG");
	}

	public void clickCancelBtn(){
		getConfirmWindowEle().findElement(By.id(CONFIRM_CANCEL_BTN)).click();
	}

	public void clickOkBtn(){
		getConfirmWindowEle().findElement(By.id(CONFIRM_OK_BTN)).click();
		waitForElementToDisableByClass(WAIT_DIALOG_MSG);		
	}
}