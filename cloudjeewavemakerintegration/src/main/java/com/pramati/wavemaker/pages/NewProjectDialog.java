package com.pramati.wavemaker.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.pramati.wavemaker.page.BasePage;

public class NewProjectDialog extends BasePage{
	
	private static final String TEMPLATE_MENU = "Menu";

	private static final String NEW_PROJECT_DIALOG = "studio_newProjectDialog";
	private static final String PROJECT_NAME = "dijit_form_ValidationTextBox_0";
	private static final String TAB_CLIENT = "studio_newProjectDialog_newProjectDialog_tabs_client";
	private static final String DESKTOP_NONE = "studio_newProjectDialog_newProjectDialog_templatepanel_";
	private static final String DESKTOP_MENU_SLIDE = "studio_newProjectDialog_newProjectDialog_templatepanel_desktopTemplatesInsertLayersideMenuTemplate";
	private static final String BUTTON_PARENT_ELEMENT = "studio_newProjectDialog_newProjectDialog_buttonPanel";
	private static final String CANCEL_BUTTON = "studio_newProjectDialog_newProjectDialog_CancelButton";
	private static final String OK_BUTTON = "studio_newProjectDialog_newProjectDialog_OKButton";
	
	private static final String WAIT_DIALOG ="wmWaitDialog";
	
	private static final String WAIT_DIALOG_MSG ="wmWaitMessage";

	BasePage basePage = new BasePage();

	public WebElement WelcomeTab() {
		waitForElementLocatedByID(NEW_PROJECT_DIALOG, getTimeOutInSeconds());	
		return basePage.getElementByID(NEW_PROJECT_DIALOG);
	}

	public void setProjectName(String projectName){
		WebElement projectEle = WelcomeTab().findElement(By.id(PROJECT_NAME));
		projectEle.clear();
		projectEle.sendKeys(projectName);
	}

	/**
	 * Set's Desktop Template for new project
	 * 
	 * @param templateName
	 */
	public void setDeskTopTemplate(String templateName){
		WebElement tabClient = WelcomeTab().findElement(By.id(TAB_CLIENT));
		if(templateName.equalsIgnoreCase("None")){
			clickOnNone(tabClient);
		}
		else if(templateName.equalsIgnoreCase("Menu")){
			clickOnMenuSlide(tabClient);
		}
	}

	/**
	 * @param tabClient
	 */
	private void clickOnNone(WebElement tabClient) {
		tabClient.findElement(By.id(DESKTOP_NONE)).click();
	}

	/**
	 * @param tabClient
	 */
	private void clickOnMenuSlide(WebElement tabClient) {
		System.out.println("done");
		tabClient.findElement(By.id(DESKTOP_MENU_SLIDE)).click();
	}

	public void clickButton(String btn){
		WebElement btnEle = WelcomeTab().findElement(By.id(BUTTON_PARENT_ELEMENT));
		if(btn.equalsIgnoreCase("CANCEL")){
			clickCancelBtn(btnEle);
		}
		else if(btn.equalsIgnoreCase("OK")){			
			clickOkBtn(btnEle);
		}
		
	}

	/**
	 * @param btnEle
	 */
	private void clickCancelBtn(WebElement btnEle) {
		btnEle.findElement(By.id(CANCEL_BUTTON)).click();
	}

	/**
	 * @param btnEle
	 */
	private void clickOkBtn(WebElement btnEle) {
		btnEle.findElement(By.id(OK_BUTTON)).click();		
		waitForDeployementPageToOpen();
	}

	/**
	 * 
	 */
	private void waitForDeployementPageToOpen() {
		waitForElementLocatedByClassName(WAIT_DIALOG, getTimeOutInSeconds());		
		waitForElementToDisableByClass(WAIT_DIALOG_MSG);
	}
}
