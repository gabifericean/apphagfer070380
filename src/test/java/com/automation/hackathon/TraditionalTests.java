package com.automation.hackathon;

import java.util.Collections;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.automation.hackathon.commons.TestBase;
import com.automation.hackathon.pageobjects.FinancialOverviewPage;
import com.automation.hackathon.pageobjects.LoginFormPage;


public class TraditionalTests extends TestBase {

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
		LoginFormPage loginPage = new LoginFormPage(driver);
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(loginPage.verifyLogo(),"Logo is not present \n");
		softAssert.assertTrue(loginPage.verifyLoginFormHeader("Login Form"),"Form header is not correct \n");
		softAssert.assertTrue(loginPage.verifyLabel("Username"),"Username label is not present \n");
		softAssert.assertTrue(loginPage.verifyLabel("Password"),"Password label is not present  \n");
		softAssert.assertTrue(loginPage.verifyLabel("Remember Me"),"Remember Me label is not present \n");
		softAssert.assertTrue(loginPage.verifyUsernameTextbox(),"Username textbox is not present \n");
		softAssert.assertTrue(loginPage.verifyUsernameTextboxPlaceholder("Enter your username"),"Username textbox placeholder is not correct \n");
		softAssert.assertTrue(loginPage.verifyPasswordTextbox(),"Password textbox is not present \n");
		softAssert.assertTrue(loginPage.verifyPasswordTextboxPlaceholder("Enter your password"),"Password textbox placeholder is not correct \n");
		softAssert.assertTrue(loginPage.verifyLoginButton(),"Login button is not present \n");
		softAssert.assertTrue(loginPage.verifyRememberMeCheckbox(),"Remember Me checkbox is not present \n");
		softAssert.assertTrue(loginPage.verifyTwitterImg(),"Twiter image is not present \n");
		softAssert.assertTrue(loginPage.verifyFacebookImg(),"Facebook image is not present \n");
		softAssert.assertTrue(loginPage.verifyLinkedinImg(),"Linkedin image is not present \n");
		softAssert.assertTrue(loginPage.verifyUserMaleImg(),"User male image is not present \n");
		softAssert.assertTrue(loginPage.verifyFingerprintImg(),"Fingerprint image is not present \n");		
		softAssert.assertAll();
	}
	
	@DataProvider(name = "login")
	public Object[][] LoginDataProvider() {
	 return new Object[][] {
	   //username, password, expected message
	   { "", "", "Both Username and Password must be present" },			 
	   { "user", "", "Password must be present" },
	   { "", "pass", "Username must be present" },
	   { "user", "pass", "" }
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
	 * 
	 * Bugs for v2:
	 * 
	 * 1. Warning message for first case is not the same. Actual message is:'Please enter both username and password'; Expected message is:Both Username and Password must be present
	 * 2. Warning message for second case is not the same. Actual message is not displayed; Expected message is: Password must be present
	 * 3. Warning message for third case is not positioned well. THIS ISSUE IS NOT CATCHED BY TEST!!!
	 */
	@Test(dataProvider = "login", priority=2)
	public void Test2_DataDrivenTest(String username, String password, String expectedMessage){
		LoginFormPage loginPage = new LoginFormPage(driver);
		SoftAssert softAssert = new SoftAssert();
		loginPage.fillUsernameTextbox(username);
		loginPage.fillPasswordTextbox(password);
		loginPage.clickLoginButton();
		if(expectedMessage.length()>0){//logged in not successfully
			softAssert.assertTrue(loginPage.getAlertWarning().contains(expectedMessage), "Expected message is NOT correct; Actual message is:'"+loginPage.getAlertWarning()+"'; Expected message is:"+expectedMessage);
			softAssert.assertTrue(loginPage.isDisplayedAlertWarning(), "Expected message is NOT displayed");			
		}else{//logged in is successfully
			FinancialOverviewPage financialOverview = new FinancialOverviewPage(driver);
			softAssert.assertTrue(financialOverview.verifyFinancialOverviewHeader(), "Logged in is NOT successfully");
		}
		softAssert.assertAll();
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
		LoginFormPage loginPage = new LoginFormPage(driver);
		SoftAssert softAssert = new SoftAssert();
		loginPage.fillUsernameTextbox("username");
		loginPage.fillPasswordTextbox("password");
		loginPage.clickLoginButton();
		FinancialOverviewPage financialOverview = new FinancialOverviewPage(driver);
		softAssert.assertTrue(financialOverview.verifyFinancialOverviewHeader(), "Logged in is NOT successfully");
		
		List<String>all1 = financialOverview.getAllFromRecentTransactions();
		List<Double>amount1 = financialOverview.getAmountFromRecentTransactions();
		List<Double>amountSort = amount1;
		Collections.sort(amountSort);
		financialOverview.clickAmountLink();
		List<String>all2 = financialOverview.getAllFromRecentTransactions();
		List<Double>amount2 = financialOverview.getAmountFromRecentTransactions();		
		softAssert.assertTrue(amount2.equals(amountSort),"Sort is NOT working! Actual="+amount2+"   Expected="+amountSort);
		softAssert.assertTrue(all2.containsAll(all1),"The values from table are not the same!");
		softAssert.assertAll();
	}
	
	/**
	 * THIS TEST CANNOT BE AUTOMATED USING SELENIUM WEBDRIVER - because we cannot verify the canvan.
	 * 
	 * 	 - the CANVAS tag doesn't contain any information about the bars and values. 
	 *   - in order to test it we need to use image processing tools
	 * 
	 * Once logged in, click on the "Compare Expenses" button on the toolbar. 
	 * This will display a bar chart comparing the expenses for the year 2017 and 2018.
	 * Assume the values of the chart are coming from a test data and the test data will not change across versions. 
	 * Validate that the bar chart and representing that data (number of bars and their heights). 
	 * They should remain the same across versions. Then click on the "Show data for next year" button. 
	 * This should add the data for the year 2019. Verify that this data set is added for the year 2019
	 */
	@Test
	public void Test4_CanvasChartTest(){
		LoginFormPage loginPage = new LoginFormPage(driver);
		SoftAssert softAssert = new SoftAssert();
		loginPage.fillUsernameTextbox("username");
		loginPage.fillPasswordTextbox("password");
		loginPage.clickLoginButton();
		FinancialOverviewPage financialOverview = new FinancialOverviewPage(driver);
		softAssert.assertTrue(financialOverview.verifyFinancialOverviewHeader(), "Logged in is NOT successfully");
		financialOverview.clickFinancialOverviewCompareExpensesLink();
		//the CANVAS tag doesn't contain any information about the bars and values. 
		//In order to test it we need to use image processing tools		
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
		driver.navigate().to("https://demo.applitools.com/hackathon.html?showAd=true");
		//driver.navigate().to("https://demo.applitools.com/hackathonV2.html?showAd=true");
		
		LoginFormPage loginPage = new LoginFormPage(driver);
		SoftAssert softAssert = new SoftAssert();
		loginPage.fillUsernameTextbox("username");
		loginPage.fillPasswordTextbox("password");
		loginPage.clickLoginButton();
		FinancialOverviewPage financialOverview = new FinancialOverviewPage(driver);
		softAssert.assertTrue(financialOverview.verifyFinancialOverviewHeader(), "Logged in is NOT successfully");
		softAssert.assertTrue(financialOverview.verifyFlashSale1(), "Flash sale 1 is not present \n");
		softAssert.assertTrue(financialOverview.verifyFlashSale2(), "Flash sale 2 is not present \n");	
		Integer noOfFlashSaleImg =financialOverview.verifyNumberOfFlashSale();
		softAssert.assertTrue(noOfFlashSaleImg==2, "Flash sales are not as many as we expect.Please corelate this result with the above assertions; Number of flash sales images is "+noOfFlashSaleImg);		
		softAssert.assertAll();
	}
}

