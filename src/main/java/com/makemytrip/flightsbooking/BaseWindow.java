package com.makemytrip.flightsbooking;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.makemytrip.utils.Utils;

public class BaseWindow {

	private static final Logger log = LogManager.getLogger(BaseWindow.class.getName());

	protected WebDriver driver;
	protected JavascriptExecutor js;
	protected WebDriverWait wait;
	protected Stack<String> windowStack;

	public BaseWindow(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		windowStack = new Stack<>();
	}

	/***
	 * Builds the By type with given locator strategy
	 * 
	 * @param locator - locator strategy, id=>example, name=>example, css=>#example,
	 *                * tag=>example, xpath=>//example, link=>example
	 * @return Returns By Type
	 */
	public By getByType(String locator) {
		By by = null;
		String locatorType = locator.split("=>")[0];
		locator = locator.split("=>")[1];
		try {
			if (locatorType.contains("id")) {
				by = By.id(locator);
			} else if (locatorType.contains("name")) {
				by = By.name(locator);
			} else if (locatorType.contains("xpath")) {
				by = By.xpath(locator);
			} else if (locatorType.contains("css")) {
				by = By.cssSelector(locator);
			} else if (locatorType.contains("class")) {
				by = By.className(locator);
			} else if (locatorType.contains("tag")) {
				by = By.tagName(locator);
			} else if (locatorType.contains("link")) {
				by = By.linkText(locator);
			} else if (locatorType.contains("partiallink")) {
				by = By.partialLinkText(locator);
			} else {
				log.info("Locator type not supported");
			}
		} catch (Exception e) {
			log.error("By type not found with: " + locatorType);
		}
		return by;
	}

	public WebElement getElement(WebElement element) {
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	public WebElement getElement(String locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(getByType(locator)));
	}

	/***
	 *
	 * @param locator - locator strategy, id=>example, name=>example, css=>#example,
	 *                * tag=>example, xpath=>//example, link=>example
	 * @param info    - Information about element, usually text on element
	 * @return
	 */
	public List<WebElement> getElementList(String locator) {
		List<WebElement> elementList = new ArrayList<WebElement>();
		By byType = getByType(locator);
		try {
			elementList = driver.findElements(byType);
			if (elementList.size() > 0) {
				log.info("Element List found with: " + locator);
			} else {
				log.info("Element List not found with: " + locator);
			}
		} catch (Exception e) {
			log.error("Element List not found with: " + locator);
			e.printStackTrace();
		}
		return elementList;
	}

	public void clickElement(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element)).click();
	}

	public void clickElement(String locator) {
		wait.until(ExpectedConditions.elementToBeClickable(getByType(locator))).click();
	}

	/***
	 * Click element using JavaScript
	 * 
	 * @param element - WebElement to perform Click operation
	 * @param info    - Information about element
	 */
	public void javascriptClick(WebElement element, String info) {
		try {
			js.executeScript("arguments[0].click();", element);
			log.info("Clicked on :: " + info);
		} catch (Exception e) {
			log.error("Cannot click on :: " + info);
		}
	}

	/***
	 * Click element using JavaScript
	 * 
	 * @param locator - locator strategy, id=>example, name=>example, css=>#example,
	 *                * tag=>example, xpath=>//example, link=>example
	 * @param info    - Information about element
	 */
	public void javascriptClick(String locator, String info) {
		WebElement element = getElement(locator);
		try {
			js.executeScript("arguments[0].click();", element);
			log.info("Clicked on :: " + info);
		} catch (Exception e) {
			log.error("Cannot click on :: " + info);
		}
	}

	public void type(WebElement element, String text) {
		WebElement inputElement = getElement(element);
		inputElement.clear();
		inputElement.sendKeys(text);
	}

	public void type(String locator, String text) {
		WebElement inputElement = getElement(locator);
		inputElement.clear();
		inputElement.sendKeys(text);
	}

	/**
	 * Get text of a web element
	 *
	 * @param element - WebElement to perform click action
	 * @param info    - Information about element
	 */
	public String getElementText(WebElement element, String info) {
		log.info("Getting Text on element :: " + info);
		String text = null;
		text = element.getText();
		if (text.length() == 0) {
			text = element.getAttribute("innerText");
		}
		if (!text.isEmpty()) {
			log.info("The text is : " + text);
		} else {
			log.error("Text Not Found");
		}
		return text.trim();
	}

	/**
	 * Get text of a web element with locator
	 * 
	 * @param locator
	 * @param info
	 * @return
	 */
	public String getText(String locator, String info) {
		WebElement element = getElement(locator);
		return getElementText(element, info);
	}

	/**
	 * @param locator
	 * @param attribute
	 * @return
	 */
	public String getElementAttributeValue(String locator, String attribute) {
		WebElement element = getElement(locator);
		return element.getAttribute(attribute);
	}

	/**
	 * @param element
	 * @param attribute
	 * @return
	 */
	public String getElementAttributeValue(WebElement element, String attribute) {
		return element.getAttribute(attribute);
	}

	/***
	 * Check if element is present
	 * 
	 * @param locator locator strategy, id=>example, name=>example, css=>#example, *
	 *                tag=>example, xpath=>//example, link=>example
	 * @return boolean if element is present or not
	 */
	public boolean isElementPresent(String locator) {
		List<WebElement> elementList = getElementList(locator);
		int size = elementList.size();
		if (size > 0) {
			log.info("Element is present with locator " + locator);
			return true;
		} else {
			log.info("Element is not present with locator " + locator);
			return false;
		}
	}

	/**
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElement(String locator, Duration seconds) {
		By byType = getByType(locator);
		WebElement element = null;
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
			log.info("Waiting for max:: " + seconds + " seconds for element to be available");
			WebDriverWait wait = new WebDriverWait(driver, seconds);
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(byType));
			log.info("Element appeared on the web page");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		} catch (Exception e) {
			log.error("Element not appeared on the web page");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		}
		return element;
	}

	/***
	 * Wait for element to be clickable
	 * 
	 * @param locator - locator strategy, id=>example, name=>example, css=>#example,
	 *                * tag=>example, xpath=>//example, link=>example
	 * @param timeout - Duration to try before timeout
	 */
	public WebElement waitForElementToBeClickable(String locator, Duration seconds) {
		By byType = getByType(locator);
		WebElement element = null;
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
			log.info("Waiting for max:: " + seconds + " seconds for element to be clickable");

			WebDriverWait wait = new WebDriverWait(driver, seconds);
			element = wait.until(ExpectedConditions.elementToBeClickable(byType));
			log.info("Element is clickable on the web page");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		} catch (Exception e) {
			log.error("Element not appeared on the web page");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		}
		return element;
	}

	/**
	 *
	 */
	public boolean waitForLoading(String locator, Duration seconds) {
		By byType = getByType(locator);
		boolean elementInvisible = false;
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
			log.info("Waiting for max:: " + seconds + " seconds for element to be available");
			WebDriverWait wait = new WebDriverWait(driver, seconds);
			elementInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(byType));
			log.info("Element appeared on the web page");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		} catch (Exception e) {
			log.error("Element not appeared on the web page");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		}
		return elementInvisible;
	}

	/**
	 * Mouse Hovers to an element
	 *
	 * @param locator
	 */
	public void mouseHover(String locator) {
		WebElement element = getElement(locator);
		Actions action = new Actions(driver);
		action.moveToElement(element).perform();
		// Util.sleep(5000);
	}

	/**
	 * @param element
	 * @param optionToSelect
	 */
	public void selectOption(WebElement element, String optionToSelect) {
		Select sel = new Select(element);
		sel.selectByVisibleText(optionToSelect);
		log.info("Selected option : " + optionToSelect);
	}

	/**
	 * Selects a given option in list box
	 *
	 * @param locator
	 * @param optionToSelect
	 */
	public void selectOption(String locator, String optionToSelect) {
		WebElement element = getElement(locator);
		this.selectOption(element, optionToSelect);
	}

	/**
	 * get Selected drop down value
	 *
	 * @param element
	 * @return
	 */
	public String getSelectDropDownValue(WebElement element) {
		Select sel = new Select(element);
		return sel.getFirstSelectedOption().getText();
	}

	/**
	 * @param element
	 * @param optionToVerify
	 */
	public boolean isOptionExists(WebElement element, String optionToVerify) {
		Select sel = new Select(element);
		boolean exists = false;
		List<WebElement> optList = sel.getOptions();
		for (int i = 0; i < optList.size(); i++) {
			String text = getElementText(optList.get(i), "Option Text");
			if (text.matches(optionToVerify)) {
				exists = true;
				break;
			}
		}
		if (exists) {
			log.info("Selected Option : " + optionToVerify + " exist");
		} else {
			log.info("Selected Option : " + optionToVerify + " does not exist");
		}
		return exists;
	}

	/***
	 *
	 * @param methodName
	 * @param browserName
	 * @return
	 */
	public String takeScreenshot(String methodName, String browserName) {
		String fileName = Utils.getScreenshotName(methodName, browserName);
		String screenshotDir = System.getProperty("user.dir") + "//screenshots//";
		new File(screenshotDir).mkdirs();
		String path = screenshotDir + fileName;
		File filePath = new File(path);
		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(screenshot, filePath);
			log.info("Screen Shot Was Stored at: " + path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	public void DoubleClick(WebElement element, String info) {
		Actions action = new Actions(driver);
		action.doubleClick(element);
		log.info("Double Clicked on :: " + info);
		action.perform();
	}

	/**
	 * Right Click a WebElement
	 *
	 * @param locator
	 */
	public void rightClick(String locator) {
		WebElement element = getElement(locator);
		Actions action = new Actions(driver);
		action.contextClick(element).build().perform();
		log.info("Double Clicked on :: " + locator);
	}

	/**
	 * Right click a WebElement and select the option
	 *
	 * @param elementLocator
	 * @param itemLocator
	 */
	public void selectItemRightClick(String elementLocator, String itemLocator) {
		WebElement element = getElement(elementLocator);
		Actions action = new Actions(driver);
		action.contextClick(element).build().perform();
		WebElement itemElement = getElement(itemLocator);
		clickElement(itemElement);
	}

	/**
	 * @param key
	 */
	public void keyPress(Keys key, String info) {
		Actions action = new Actions(driver);
		action.keyDown(key).build().perform();
		log.info("Key Pressed :: " + info);
	}

	/**
	 * Switch to iframe with name or id
	 *
	 * @param frameNameId - Name or Id of the iframe
	 */
	public void switchFrame(String frameNameId) {
		try {
			driver.switchTo().frame(frameNameId);
			log.info("Switched to iframe");
		} catch (Exception e) {
			log.error("Cannot switch to iframe");
		}
	}

	public void scrollElementIntoView(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	/** Push the original window handle onto the stack */
	public void getMainWindowHandle() {
		windowStack.push(driver.getWindowHandle());
	}

	// Method to switch to the latest opened window
	public void switchToNewWindow() {
		Set<String> allWindows = driver.getWindowHandles();

		// Push new window handles onto the stack
		for (String windowHandle : allWindows) {
			if (!windowStack.contains(windowHandle)) {
				windowStack.push(windowHandle);
			}
		}

		// Switch to the latest window (top of the stack)
		driver.switchTo().window(windowStack.peek());
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
		log.info("Switched to window- " + driver.getTitle());
	}

	// Method to switch back to the previous window
	public void switchToPreviousWindow() {
		if (windowStack.size() > 1) {
			driver.close(); // Close the current window
			windowStack.pop(); // Remove the current window handle from the stack
			driver.switchTo().window(windowStack.peek()); // Switch to the previous window (now top of the stack)
			log.info("Switched to window- " + driver.getTitle());
		}
	}

	// Method to close all windows and quit the driver
	public void closeAllWindowsAndQuit() {
		while (!windowStack.isEmpty()) {
			driver.switchTo().window(windowStack.pop());
			driver.close();
		}
	}
}
