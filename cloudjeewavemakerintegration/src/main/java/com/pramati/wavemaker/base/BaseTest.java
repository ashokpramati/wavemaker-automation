package com.pramati.wavemaker.base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.pramati.wavemaker.pages.WelcomeStudio;
import com.pramati.wavemaker.util.ScreenshotCapture;

public class BaseTest {

	protected WelcomeStudio homePage = null;

	@BeforeMethod(alwaysRun = true)
	public void runBeforeMethod() {
		homePage = new WelcomeStudio();
	}

	@AfterMethod(alwaysRun = true)
	public void runAfterMethod() {

		if (homePage != null) {
			ScreenshotCapture.takeScreenshot();
			homePage.quitBrowser();
			homePage.resetDriver();
			homePage = null;
		}

	}

}