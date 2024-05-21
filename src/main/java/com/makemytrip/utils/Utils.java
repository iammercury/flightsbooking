package com.makemytrip.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {

	private static final Logger log = LogManager.getLogger(Utils.class.getName());

	/***
	 * Sleep for specified number of milliseconds
	 * 
	 * @param msec
	 */
	public static void sleep(long msec) {
		log.info("Waiting for " + (msec * .001) + " seconds");
		try {
			Thread.sleep(msec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Get a unique screenshot name
	 * 
	 * @param methodName
	 * @return
	 */
	public static String getScreenshotName(String methodName, String browserName) {
		String localDateTime = getCurrentDateTime();
		StringBuilder name = new StringBuilder().append(browserName).append("_").append(methodName).append("_")
				.append(localDateTime).append(".png");
		return name.toString();
	}

	/***
	 * Get a unique report name
	 * 
	 * @return
	 */
	public static String getReportName() {
		String localDateTime = getCurrentDateTime();
		StringBuilder name = new StringBuilder().append("AutomationReport_").append(localDateTime).append(".html");
		return name.toString();
	}

	/***
	 * Get simple date time as string
	 * 
	 * @return date time as string type
	 */
	public static String getCurrentDateTime() {
		LocalDateTime current = LocalDateTime.now();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String date = formatter.format(current).replace("/", "_");
		date = date.replace(":", "_").replace(" ", "_");
		log.info("Current Date and Time :: " + date);
		return date;
	}

	/***
	 * Get random unique string with specified length
	 * 
	 * @param length
	 * @return a unique string
	 */
	public static String getRandomString(int length) {
		StringBuilder sbuilder = new StringBuilder();
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		for (int i = 0; i < length; i++) {
			sbuilder.append(chars.charAt(new Random().nextInt(chars.length())));
		}
		String randomString = sbuilder.toString();
		log.info("Random string with length :: " + length + " is :: " + randomString);
		return randomString;
	}

}
