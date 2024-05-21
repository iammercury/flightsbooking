package com.makemytrip.flightsbooking;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;

public class WebDriverFactory {

	private static final WebDriverFactory instance = new WebDriverFactory();

	private WebDriverFactory() {

	}

	public static WebDriverFactory getInstance() {
		return instance;
	}

	private static ThreadLocal<WebDriver> threadedDriver = new ThreadLocal<WebDriver>();

	public WebDriver getDriver(String browser) {
		WebDriver driver = null;

		if (threadedDriver.get() == null) {
			if (browser.equalsIgnoreCase("chrome")) {
//				ChromeOptions chromeOptions = setChromeOptions();
//              driver = new ChromeDriver(chromeOptions);
				driver = new ChromeDriver();
				threadedDriver.set(driver);
			} else if (browser.equalsIgnoreCase("firefox")) {
//				FirefoxOptions ffOptions = setFFOptions();
//              driver = new FirefoxDriver(ffOptions);
				driver = new FirefoxDriver();
				threadedDriver.set(driver);
			}
		}

		return threadedDriver.get();

	}

	/***
	 * Quit driver instance
	 */
	public void quitDriver() {
		threadedDriver.get().quit();
		threadedDriver.set(null);
	}

	/***
	 * Set Chrome Options
	 * 
	 * @return options
	 */
	private ChromeOptions setChromeOptions() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		return options;
	}

	/***
	 * Set Firefox Options
	 * 
	 * @return options
	 */
	private FirefoxOptions setFFOptions() {
		FirefoxOptions options = new FirefoxOptions();
		options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, false);
		return options;
	}

	/***
	 * Set Internet Explorer Options
	 * 
	 * @return options
	 */
	private InternetExplorerOptions setIEOptions() {
		InternetExplorerOptions options = new InternetExplorerOptions();
		options.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
		options.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
		options.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
		options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		return options;
	}
}
