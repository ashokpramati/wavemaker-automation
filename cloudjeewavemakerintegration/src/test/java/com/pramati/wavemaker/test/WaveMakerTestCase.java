package com.pramati.wavemaker.test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.pramati.wavemaker.base.BaseTest;
import com.pramati.wavemaker.page.BasePage;
import com.pramati.wavemaker.pages.CloudJeeApplication;
import com.pramati.wavemaker.pages.Deployment;
import com.pramati.wavemaker.pages.NewProjectDialog;
import com.pramati.wavemaker.pages.ProjectCreationPage;
import com.pramati.wavemaker.util.ConfigProperties;

/**
 * Test Case for deployement of Application in Cloud
 * 
 * @author krishnakumarnellore
 *
 */
public class WaveMakerTestCase extends BaseTest {
	Calendar calendar = new GregorianCalendar();
	String url = null;
	NewProjectDialog newProjectDialog = null;
	ProjectCreationPage projectCreationPage = null;
	Deployment deployment = null;
	CloudJeeApplication cloudJeeApplication = null;


	@Test(enabled=false)
	public void testNewProjectDeployment() throws InterruptedException {
		homePage.clickNewProject();
		newProjectDialog = new NewProjectDialog();
		newProjectDialog.setProjectName("Krishna"+"A"+calendar.get(Calendar.MINUTE)+calendar.get(Calendar.SECOND));
		newProjectDialog.setDeskTopTemplate("Menu");
		newProjectDialog.clickButton("ok");
		projectCreationPage = new ProjectCreationPage();
		projectCreationPage.clickMenuBar("File","Deploy Project");
		projectCreationPage.clickFileSubSubMenu("New Deployment");
		projectCreationPage.selectDeployment("cloudjee");
		projectCreationPage.clickDeploymentBtn("Ok");
		deployment = new Deployment();
		deployment.setUserPassword(ConfigProperties.USERNAME, ConfigProperties.PASSWORD);

		System.out.println(deployment.getDeploymentAPPName());
		System.out.println(deployment.getDeploymentAPPURL());
		System.out.println(deployment.getDeploymentName());
		System.out.println(deployment.getDeploymentType());
		deployment.clickDeployNowBtn();		
		String url = deployment.clickCloudAccountOkBtn();

		System.out.println("URL of the page is :" +url);
		deployment.quitBrowser();



		WebDriver driver = BasePage.getDriver();
		driver.get(url);
		System.out.println("Title is :" +driver.getTitle());
		driver.quit();
		System.out.println("done");
	}




}
