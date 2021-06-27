package com.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import basePackage.BaseClass;

public class OrangeHRMTest extends BaseClass{
	
	@Test
	public void loginPageTest() {
		test = extent.createTest("loginPageTest");
		WebElement image = driver.findElement(By.xpath("//*[@id=\"divLogo\"]/img"));
		AssertJUnit.assertTrue(image.isDisplayed());
	}
	
	@Test
	public void loginTest () {
		test = extent.createTest("loginTest");
		driver.findElement(By.id("txtUsername")).sendKeys("Admin");
		driver.findElement(By.id("txtPassword")).sendKeys("TK@x3Rx5Rp");
		driver.findElement(By.id("btnLogin")).click();
		String actualTitle = driver.getTitle();
		String expectedTitle = "OrangeHRM";
		AssertJUnit.assertEquals(actualTitle, expectedTitle);
	}
	
	@Test
	public void sampleCase() {
		test = extent.createTest("sampleCase");
		test.createNode("Validation1");
		Assert.assertTrue(true);
		test.createNode("Validation2");
		Assert.assertTrue(true);
		test.createNode("Validation3");
		Assert.assertTrue(true);
		test.createNode("Validation4");
		Assert.assertTrue(true);
	}

}
