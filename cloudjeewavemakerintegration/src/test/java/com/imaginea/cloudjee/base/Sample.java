package com.imaginea.cloudjee.base;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Sample {
	static WebDriver driver = null;
	public static void main(String[] args) {
		driver = new FirefoxDriver();
		driver.get("http://www.vit.ac.in/admissions.asp");
		extractAdminLink();
		//extractedAllLeftPanelLink();
	}

	private static void extractAdminLink() {
		WebElement AdminEle =
				driver.findElement(By.cssSelector("td[id='stUI26_cnt']"));
		AdminEle.click();
		System.out.println(AdminEle.getText());
		WebElement internatioAdm = driver.findElement(By.cssSelector("table[id='stUI27_body']"));
		List<WebElement> tdEle = internatioAdm.findElements(By.tagName("tr"));
		for (WebElement te : tdEle) {
			System.out.println(te.getText());
		}
	}

	private static void extractedAllLeftPanelLink() {
		WebElement tableEle =
				driver.findElement(By.cssSelector("table[id='stUI2_body']"));
		List<WebElement> trElements = tableEle.findElements(By.tagName("tr"));
		for (WebElement webElement : trElements) {
			System.out.println(webElement.getText());
		}
	}

}