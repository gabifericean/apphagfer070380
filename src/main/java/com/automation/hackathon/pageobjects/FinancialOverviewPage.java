package com.automation.hackathon.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.automation.hackathon.commons.TestBase;

public class FinancialOverviewPage extends TestBase {

	WebDriver driver;
	
	public FinancialOverviewPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	//Financial overview header
	@FindBy(how = How.XPATH, using = "//h6[contains(.,'Financial Overview')]")
	private WebElement elemFinancialOverviewHeader;
	
	//Amount link
	@FindBy(how = How.ID, using = "amount")
	private WebElement elemFinancialOverviewAmountLink;
	
	//Show Data For Next Year link
	@FindBy(how = How.ID, using = "addDataset")
	private WebElement elemShowDataForNextYearLink;	
	
	//Compare expenses link
	@FindBy(how = How.ID, using = "showExpensesChart")
	private WebElement elemFinancialOverviewCompareExpensesLink;

	//All data from Recent transactions	
	@FindAll({@FindBy(how = How.XPATH, using = "//*[@id='transactionsTable']/tbody/tr")})
	private List<WebElement> listAllColumn;	
	
	//Status from Recent transactions	
	@FindAll({@FindBy(how = How.XPATH, using = "//*[@id='transactionsTable']/tbody/tr/td[1]/span[2]")})
	private List<WebElement> listStatusColumn;	
	
	//Date from Recent transactions	
	@FindAll({@FindBy(how = How.XPATH, using = "//*[@id='transactionsTable']/tbody/tr/td[2]")})
	private List<WebElement> listDateColumn;	

	//Description from Recent transactions	
	@FindAll({@FindBy(how = How.XPATH, using = "//*[@id='transactionsTable']/tbody/tr/td[3]/span")})
	private List<WebElement> listDescriptionColumn;	

	//Category from Recent transactions	
	@FindAll({@FindBy(how = How.XPATH, using = "//*[@id='transactionsTable']/tbody/tr/td[4]/a")})
	private List<WebElement> listCategoryColumn;	
	
	//Amount from Recent transactions	
	@FindAll({@FindBy(how = How.XPATH, using = "//*[@id='transactionsTable']/tbody/tr/td[5]/span")})
	private List<WebElement> listAmountColumn;		

	//Flash sale images
	@FindAll({@FindBy(how = How.XPATH, using = "//div[@class='balance hidden-mobile']/img")})
	private List<WebElement> listOfFlassSalesImg;		
	
	@FindBy(how = How.XPATH, using = "//div[@id='flashSale']/img[@src='img/flashSale.gif']")
	private WebElement elemFlashSaleFirst;
	//Flash sale second
	@FindBy(how = How.XPATH, using = "//div[@id='flashSale2']/img[@src='img/flashSale2.gif']")
	private WebElement elemFlashSaleSecond;
	
	
	public boolean verifyFinancialOverviewHeader(){
		waitForElementToBeVisible(elemFinancialOverviewHeader);
		return elemFinancialOverviewHeader.isDisplayed();
	}
	
	public void clickAmountLink(){
		waitForElementToBeVisible(elemFinancialOverviewAmountLink);
		elemFinancialOverviewAmountLink.click();
	}
	
	public void clickFinancialOverviewCompareExpensesLink(){
		waitForElementToBeVisible(elemFinancialOverviewCompareExpensesLink);
		elemFinancialOverviewCompareExpensesLink.click();
	}
	
	public List<Double> getAmountFromRecentTransactions(){
		List<Double>amount = new ArrayList<Double>();
		for (int i=0; i<listAmountColumn.size(); i++){
			String value = listAmountColumn.get(i).getText().replace("USD", "").replace(" ", "").replace("+","").replace(",","");
			amount.add(Double.parseDouble(value));
		}
		return amount;
	}
	
	public List<String> getAllFromRecentTransactions(){
		List<String>all = new ArrayList<String>();
		for (int i=0; i<listAllColumn.size(); i++){
			all.add(listAllColumn.get(i).getText());
		}
		return all;
	}	
	
	public void clickShowDataForNextYearLink(){
		waitForElementToBeVisible(elemShowDataForNextYearLink);
		elemShowDataForNextYearLink.click();
	}
	
	public Integer verifyNumberOfFlashSale(){
		Integer result = 0;
		try{
			result = listOfFlassSalesImg.size();
		}catch(Exception e){
			result = 0;
		}				
		return result;		
	}
	
	public boolean verifyFlashSale1(){
		try{
			return elemFlashSaleFirst.isDisplayed();			
		}catch(Exception e){
			return false;
		}		
	}		
	
	public boolean verifyFlashSale2(){
		try{
			return elemFlashSaleSecond.isDisplayed();			
		}catch(Exception e){
			return false;
		}		
	}	
}
