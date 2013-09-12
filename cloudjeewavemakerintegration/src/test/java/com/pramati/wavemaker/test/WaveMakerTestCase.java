package com.pramati.wavemaker.test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.testng.annotations.Test;

import com.pramati.wavemaker.base.BaseTest;

/**
 * Test Case for deployement of Application in Cloud
 * 
 * @author krishnakumarnellore
 *
 */
public class WaveMakerTestCase extends BaseTest {
	 Calendar calendar = new GregorianCalendar();

	 @Test(groups="smoke")
	 public void testNewProjectDeployment() throws InterruptedException {
	  
	  homePage.waitForElementLocatedByID("studio_startPageDialog_start_newProject", 30);
	  homePage.clickByID("studio_startPageDialog_start_newProject");
	  homePage.waitForElementLocatedByID("dijit_form_ValidationTextBox_0", 10);
	  homePage.typeByID("dijit_form_ValidationTextBox_0", "A"+calendar.get(Calendar.MINUTE)+calendar.get(Calendar.SECOND));
	  homePage.clickByID("studio_newProjectDialog_newProjectDialog_OKButton");
	  
	  homePage.sleep(10000); 
	  homePage.waitForElementLocatedByID("studio_wip_layoutBox1", 30);
	  homePage.dragAndDropByCss( "img[class='Studio_paletteImageList_4']" , "div[id='studio_wip_layoutBox1']" );
	  homePage.clickByID("dijit_PopupMenuBarItem_25");//Click on File
	  homePage.waitForElementLocatedByID("dijit_MenuItem_265_text", 30); 
	  homePage.clickByID("dijit_MenuItem_265_text");//Click on Deployment
	  homePage.waitForElementLocatedByID("dijit_MenuItem_266_text", 30); 
	  homePage.clickByID("dijit_MenuItem_266_text");//Click on New Deployment..
	  homePage.waitForElementLocatedByID("dijit_form_RadioButton_6", 30); 
	  homePage.clickByID("dijit_form_RadioButton_6"); // Click on CloudJee Radio button
	  homePage.clickByID("studio_deploymentDialog_deploymentDialog_okButton"); // Click on Ok Button
	  
	  homePage.typeByCSS("input#dijit_form_TextBox_23.dijitReset.dijitInputInner", "ashok.c@imaginea.com");
	  homePage.typeByCSS("input#dijit_form_TextBox_24.dijitReset.dijitInputInner", "pramati123");
	  homePage.clickByID("studio_deploymentDialog_deploymentDialog_cjLogonOkButton");
	  
	  homePage.sleep(10000); 
	  homePage.clickByCSS("button[id='studio_deploymentDialog_deploymentDialog_deployButton']");
	  
	  homePage.sleep(5000);
	  //homePage.waitForElementToEnableByCss("button#app_confirmDialog_button1");
	  homePage.clickByCSS("button#app_confirmDialog_button1");
	  
	  homePage.waitForElementToDisableByID("studio_dialog");
	  
	  System.out.println("Done");
	  
	 }
	 

	}
