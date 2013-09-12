package com.pramati.wavemaker.base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.pramati.wavemaker.pages.HomePage;

public class BaseTest {

	protected HomePage homePage = null;

	@BeforeMethod(alwaysRun = true)
	public void runBeforeMethod() {

		homePage = new HomePage();

	}

	@AfterMethod(alwaysRun = true)
	public void runAfterMethod() {

		if (homePage != null) {			
			homePage.quitBrowser();
			homePage.resetDriver();
			homePage = null;
		}

	}

}