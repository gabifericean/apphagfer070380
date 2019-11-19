package com.automation.hackathon;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.RectangleSize;
import com.automation.hackathon.commons.Configuration;
import com.automation.hackathon.commons.TestBase;
import com.automation.hackathon.pageobjects.FinancialOverviewPage;
import com.automation.hackathon.pageobjects.LoginFormPage;

public class VisualAITests extends TestBase {

	/**
	 * Open the login page and write assertions to ensure everything looks OK on that page. i.e. 
	 * add assertions to ensure all the fields, labels and all other items exist.
	 * 
	 * Bugs for v2:
	 * 
	 * 1. Form header is not correct; Expected = Login Form; Actual = Logout Form
	 * 2. Password label is not present; Expected =  Password; Actual = Pwd
	 * 3. Username textbox placeholder is not correct
	 * 4. Password textbox placeholder is not correct
	 * 5. User male image is not present
	 * 6. Fingerprint image is not present
	 * 7. Linkedin image is not present
	 */
	@Test
	public void Test1_LoginPageUIElementsTest(){
		eyes.open(driver, "Login App", "Test1_LoginPageUIElementsTest");		
		eyes.checkWindow("Login Window");		
		eyes.closeAsync();
	}
	
	
	
	
	@DataProvider(name = "login")
	public Object[][] LoginDataProvider() {
	 return new Object[][] {
	   //username, password, expected message, test name
	   { "", "", "Both Username and Password must be present", "Login Test - Both Username and Password fields are empty" },			 
	   { "user", "", "Password must be present", "Login Test - Password field is empty" },
	   { "", "pass", "Username must be present", "Login Test - Username field is empty" },
	   { "user", "pass", "", "Login Test - Success" }
	 };
	}
	/**
	 * Test the following login functionality by entering different values to username and password fields.
			1. If you don’t enter the username and password and click the login button, it should throw an error
			2. If you only enter the username and click the login button, it should throw an error
			3. If you only enter the password and click the login button, it should throw an error
			4. If you enter both username (any value) and password (any value), it should log you in.
	 * @param username
	 * @param password
	 * @param expectedMessage
	 * @param testName
	 * 
	 * Bugs for v2:
	 * 
	 * 1. Warning message for first case is not the same. Actual message is:'Please enter both username and password'; Expected message is:Both Username and Password must be present
	 * 2. Warning message for second case is not the same. Actual message is not displayed; Expected message is: Password must be present
	 * 3. Warning message for third case is not positioned well. THIS ISSUE IS NOT CATCHED BY TEST!!!
	 */
	@Test(dataProvider = "login")
	public void Test2_DataDrivenTest(String username, String password, String expectedMessage, String testName){
		eyes.open(driver, "Login App", "Test2_DataDrivenTest "+testName);
		LoginFormPage loginPage = new LoginFormPage(driver);
		loginPage.fillUsernameTextbox(username);
		loginPage.fillPasswordTextbox(password);
		loginPage.clickLoginButton();
		eyes.checkWindow("Login Window");
		//eyes.check(testName, Target.window().ignoreDisplacements());
		eyes.closeAsync();			
	}
	
	/**
	 * Once logged in (use any username and password to login), view the Recent Transactions table.
	 * Your test should click on the "Amounts" header, and verify that the column is in ascending order
	 * and that each row’s data stayed in tact after the sorting.
	 * 
	 * Bugs for v2:
	 * 1. Sorting in ascending order is not working when we click on 'Amount' header link
	 * 
	 */
	@Test
	public void Test3_TableSortTest(){
		eyes.open(driver, "Login App", "Test3_TableSortTest");
		LoginFormPage loginPage = new LoginFormPage(driver);
		loginPage.fillUsernameTextbox("username");
		loginPage.fillPasswordTextbox("password");
		loginPage.clickLoginButton();
		waitFor(Configuration.WAIT_MILLIS_MED_4);
		FinancialOverviewPage financialOverview = new FinancialOverviewPage(driver);

		financialOverview.clickAmountLink();
		waitFor(Configuration.WAIT_MILLIS_MED_4);
		eyes.checkWindow("Amount - sorted");
		waitFor(Configuration.WAIT_MILLIS_MED_4);
		waitFor(Configuration.WAIT_MILLIS_MED_4);
		eyes.closeAsync();	
		
	}
	
	/**
	 * Once logged in, click on the "Compare Expenses" button on the toolbar. 
	 * This will display a bar chart comparing the expenses for the year 2017 and 2018.
	 * Assume the values of the chart are coming from a test data and the test data will not change across versions. 
	 * Validate that the bar chart and representing that data (number of bars and their heights). 
	 * They should remain the same across versions. Then click on the "Show data for next year" button. 
	 * This should add the data for the year 2019. Verify that this data set is added for the year 2019
	 */
	@Test
	public void Test4_CanvasChartTest(){
		eyes.open(driver, "Login App", "Test4_CanvasChartTest");
		
		LoginFormPage loginPage = new LoginFormPage(driver);
		loginPage.fillUsernameTextbox("username");
		loginPage.fillPasswordTextbox("password");
		loginPage.clickLoginButton();
		FinancialOverviewPage financialOverview = new FinancialOverviewPage(driver);
		waitFor(Configuration.WAIT_MILLIS_MED_3);
		financialOverview.clickFinancialOverviewCompareExpensesLink();
		waitFor(Configuration.WAIT_MILLIS_MED_1);
		eyes.checkWindow("Compare expenses - defauld preview");		
		financialOverview.clickShowDataForNextYearLink();
		waitFor(Configuration.WAIT_MILLIS_MED_1);
		eyes.checkWindow("Compare expenses - after click on Show data for next year");
		waitFor(Configuration.WAIT_MILLIS_MED_3);
		eyes.closeAsync();	
	}	

	/**
	 * Test for the existence of a display ad that’s dynamic and at times might go missing by using this URL: https://demo.applitools.com/hackathon.html?showAd=true. 
	 * Log in by entering any username and password. Once logged in, you should see two different "Flash sale" gifs. Make sure both gifs exists.
	 * 
	 * Bugs for v2:
	 * 1. The flash sales from version 1 are not shown on v2. A new flash sale image is displayed!
	 */
	@Test
	public void Test5_DynamicContentTest(){	
		driver.navigate().to(Configuration.URL_TEST_5);
		eyes.open(driver, "Login App", "Test5_DynamicContentTest");
		eyes.setMatchLevel(MatchLevel.LAYOUT2);
		LoginFormPage loginPage = new LoginFormPage(driver);
		waitFor(Configuration.WAIT_MILLIS_MED_2);
		loginPage.fillUsernameTextbox("username");
		loginPage.fillPasswordTextbox("password");
		loginPage.clickLoginButton();
		waitFor(Configuration.WAIT_MILLIS_MED_4);
		eyes.checkWindow("Dynamic Content");
		waitFor(Configuration.WAIT_MILLIS_MED_4);
		waitFor(Configuration.WAIT_MILLIS_MED_4);
		waitFor(Configuration.WAIT_MILLIS_MED_4);
		eyes.closeAsync();
	}	
	
}
