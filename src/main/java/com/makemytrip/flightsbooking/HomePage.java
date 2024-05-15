package com.makemytrip.flightsbooking;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BaseWindow {

	private static final String OPTION_TO_SELECT = "//ul[@role='listbox']//p//span[contains(text(), '%s')]";

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

	public HomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void closePopups() {
		driver.switchTo().frame("webklipper-publisher-widget-container-notification-frame");
		clickElement(addPopupCloseButton);
		driver.switchTo().defaultContent();
		clickElement(signInPopupCloseButton);
	}

	public void setFromCity(String city) {
		clickElement(fromField);
		type(fromInput, city);
		System.out.println(String.format("Entered %s in the From field", city));
		clickElement(String.format(OPTION_TO_SELECT, city));
	}

	public void setToCity(String city) {
		clickElement(toField);
		type(toInput, city);
		System.out.println(String.format("Entered %s in the To field", city));
		clickElement(String.format(OPTION_TO_SELECT, city));
	}
}
