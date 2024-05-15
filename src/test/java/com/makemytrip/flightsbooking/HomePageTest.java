package com.makemytrip.flightsbooking;

import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {
  @Test
  public void testCaseOne() {
	  HomePage homePage = new HomePage(driver);
	  homePage.closePopups();
	  homePage.setFromCity("Hyderabad");
	  homePage.setToCity("Indore");
  }
}
