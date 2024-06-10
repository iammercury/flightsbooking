package com.makemytrip.flightsbooking;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.makemytrip.utils.Utils;

public class HomePage extends BaseWindow {

	private static final Logger log = LogManager.getLogger(HomePage.class.getName());

	private static final String OPTION_TO_SELECT = "xpath=>//ul[@role='listbox']//p//span[contains(text(), '%s')]";

	@FindBy(css = "a#webklipper-publisher-widget-container-notification-close-div")
	WebElement addPopupCloseButton;

	@FindBy(css = "span.commonModal__close")
	WebElement signInPopupCloseButton;

	@FindBy(css = "input#fromCity")
	WebElement fromField;

	@FindBy(css = "input[placeholder='From']")
	WebElement fromInput;

	@FindBy(css = "input#toCity")
	WebElement toField;

	@FindBy(css = "input[placeholder='To']")
	WebElement toInput;

	@FindBy(css = ".appSprite.icPlayStore.pointer")
	WebElement googlePlayStoreIcon;

	public HomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void closePopups() {
		clickElement(signInPopupCloseButton);
		switchFrame("webklipper-publisher-widget-container-notification-frame");
		clickElement(addPopupCloseButton);
		driver.switchTo().defaultContent();
	}

	public void setFromCity(String city) {
		clickElement(fromField);
		type(fromInput, city);
		log.info(String.format("Entered %s in the From field", city));
		clickElement(String.format(OPTION_TO_SELECT, city));
	}

	public void setToCity(String city) {
		clickElement(toField);
		type(toInput, city);
		log.info(String.format("Entered %s in the To field", city));
		clickElement(String.format(OPTION_TO_SELECT, city));
	}

	public String clickGooglePlayStoreAndGetTitle() {
//		scrollElementIntoView(googlePlayStoreIcon);
		Utils.sleep(3000);
		javascriptClick(googlePlayStoreIcon,"Clicked on Google Pay Store Icon");
		switchToNewWindow();
		String title = driver.getTitle();
		switchToPreviousWindow();
		return title;
	}
}
