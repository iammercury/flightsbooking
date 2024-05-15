package com.makemytrip.flightsbooking;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseWindow {

	WebDriver driver;
	WebDriverWait wait;

	public BaseWindow(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public WebElement getElement(WebElement element) {
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void clickElement(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element)).click();;
	}

	public void clickElement(String xpathOfElement) {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathOfElement))).click();;
	}

	public void type(WebElement element, String text) {
		WebElement inputElement = getElement(element);
		inputElement.clear();
		inputElement.sendKeys(text);
	}

}
