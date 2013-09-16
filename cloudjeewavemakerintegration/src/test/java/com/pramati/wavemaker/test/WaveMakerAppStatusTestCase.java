package com.pramati.wavemaker.test;

import org.testng.annotations.Test;

import com.pramati.wavemaker.base.BaseTest;
import com.pramati.wavemaker.pages.CloudJeeApplication;
import com.pramati.wavemaker.pages.Deployment;
import com.pramati.wavemaker.pages.NewProjectDialog;
import com.pramati.wavemaker.pages.ProjectCreationPage;
import com.pramati.wavemaker.util.ConfigProperties;

public class WaveMakerAppStatusTestCase extends BaseTest {
	
	NewProjectDialog newProjectDialog = null;
	ProjectCreationPage projectCreationPage = null;
	Deployment deployment = null;
	CloudJeeApplication cloudJeeApplication = null;
	
	@Test(enabled=true)
	public void testStopApp(){
		homePage.openExistingProject("KrishnaA056");
		projectCreationPage = new ProjectCreationPage();
		projectCreationPage.clickMenuBar("File","Deploy Project");
		projectCreationPage.clickFileSubSubMenu("Manage Deployment");
		deployment = new Deployment();
		deployment.setUserPassword(ConfigProperties.USERNAME, ConfigProperties.PASSWORD);
		deployment.clickManageWMCloudApBtn();
		deployment.setUserPassword(ConfigProperties.USERNAME, ConfigProperties.PASSWORD);
		cloudJeeApplication = new CloudJeeApplication();
		cloudJeeApplication.changeAPPStatus("KrishnaA848", "STOP");
		System.out.println(cloudJeeApplication.getAPPStatus("KrishnaA848"));
		cloudJeeApplication.changeAPPStatus("KrishnaA4737", "STOP");
		System.out.println(cloudJeeApplication.getAPPStatus("KrishnaA4737"));
		cloudJeeApplication.changeAPPStatus("KrishnaA5419", "START");
		System.out.println(cloudJeeApplication.getAPPStatus("KrishnaA5419"));
		System.out.println("Done");
		
	}

}
