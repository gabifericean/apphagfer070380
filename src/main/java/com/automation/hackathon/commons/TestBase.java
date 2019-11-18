package com.automation.hackathon.commons;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;


public class TestBase {

	public static WebDriver driver;
	
	private static EyesRunner runner;
	public static Eyes eyes;
	private static BatchInfo batch;

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Method method) throws InterruptedException {
		System.out.println("================================================");
		System.out.println("================= TEST STARTED =================");
		System.out.println("================================================");
		SimpleDateFormat time_formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");
		String current_time_str = time_formatter.format(System.currentTimeMillis());
		System.out.println("TEST STARTED: " + method.getName() + "	||"+current_time_str);		
		try{
			setBatch();
			startBrowser();
			
		}catch(Exception e){
			System.out.println("Catch exception in before method" + e.getMessage());
		}
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) throws IOException {
		try{
			driver.close();
			driver.quit();
			eyes.abort();
		}
		catch(Exception e){
			System.out.println("Catch exceptions in aftermethod - > the tests should be run even if the after method is crashing");
		}
	}

	public static void startBrowser() {
		ChromeOptions optionsCH = new ChromeOptions();
		optionsCH.addArguments("--start-maximized");
		optionsCH.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		optionsCH.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		optionsCH.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
		final String CHROME_DRIVER = "resources\\drivers\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
		driver = new ChromeDriver(optionsCH);
		driver.manage()
			.timeouts()
			.implicitlyWait(Configuration.WAIT_FIVE_SECONDS,
					TimeUnit.SECONDS)
					.pageLoadTimeout(Configuration.WAIT_TWENTYFIVE_SECONDS, TimeUnit.SECONDS);
			driver.get(Configuration.URL);
		}
	
	// ==============================================================
	// 							Applitools 
	// ==============================================================
	public static void setBatch(){
		batch = new BatchInfo("Login Test Visual AI");
		runner = new ClassicRunner();
		eyes = new Eyes(runner);
		eyes.setApiKey(Configuration.APPLITOOLS_API_KEY);
		eyes.setBatch(batch);
		eyes.setMatchLevel(MatchLevel.STRICT);
	}
	
	
	// ==============================================================
	// 							Commons
	// ==============================================================
	/**
	 * Static wait
	 * 
	 * @param milliseconds
	 */
	public static void waitFor(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Wait for element to be clickable
	 * 
	 * @param element
	 * @return
	 */
	public static WebElement waitForElementToBeClickable(WebElement element) {
		return (new WebDriverWait(driver, Configuration.WAIT_TWENTYFIVE_SECONDS))
				.until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * Wait for element to be visible in the dom
	 * 
	 * @param element
	 * @return
	 */
	public static WebElement waitForElementToBeVisible(WebElement element) {
		return (new WebDriverWait(driver, Configuration.WAIT_TWENTYFIVE_SECONDS))
				.until(ExpectedConditions.visibilityOf(element));
	}

}
