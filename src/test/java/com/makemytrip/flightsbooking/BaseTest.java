package com.makemytrip.flightsbooking;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseTest {

	WebDriver driver;

	@BeforeMethod
	@Parameters({"browser"})
	public void beforeMethod(String browser) {
		driver = WebDriverFactory.getInstance().getDriver(browser);
		BaseWindow baseWindow = new BaseWindow(driver);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://www.makemytrip.com/");
		baseWindow.getMainWindowHandle();
	}

	@AfterMethod
	public void afterMethod() {
//		driver.quit();
		WebDriverFactory.getInstance().quitDriver();
	}

}
