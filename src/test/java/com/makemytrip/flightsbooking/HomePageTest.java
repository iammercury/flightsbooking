package com.makemytrip.flightsbooking;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {
  @Test(enabled=false)
  public void testCaseOne() {
	  HomePage homePage = new HomePage(driver);
	  homePage.closePopups();
	  homePage.setFromCity("Hyderabad");
	  homePage.setToCity("Indore");
  }
  
  @Test
  public void clickGooglePlayStore() {
	  HomePage homePage = new HomePage(driver);
	  homePage.closePopups();
	  String title = homePage.clickGooglePlayStoreAndGetTitle();
	  Assert.assertEquals(title, "MakeMyTrip Hotels, Flight, Bus - Apps on Google Play");
  }
}
