package com.automation.hackathon.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.automation.hackathon.commons.Configuration;
import com.automation.hackathon.commons.TestBase;

public class LoginFormPage extends TestBase {

	WebDriver driver;
	
	public LoginFormPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/*
	 * Define elements
	 */
	//<<Login Form>> header
	@FindBy(how = How.XPATH, using = "//h4[@class='auth-header']")
	private WebElement elemLoginFormHeader;
	
	//Logo image
	@FindBy(how = How.XPATH, using = "//img[contains(@src,'logo')]")
	private WebElement elemLogoImg;	
	
	// <<Username, Password, Remember me>> label	
	@FindAll({@FindBy(how = How.XPATH, using = "//div/label")})
	private List<WebElement> listLabels;	

	//Username textbox
	@FindBy(how = How.ID, using = "username")
	private WebElement elemLoginUsername;
	
	//Password textbox
	@FindBy(how = How.ID, using = "password")
	private WebElement elemLoginPassword;
	
	//Log In button
	@FindBy(how = How.ID, using = "log-in")
	private WebElement elemLogin;

	//Remember me checkbox
	@FindBy(how = How.XPATH, using = "//label/input[@type='checkbox']")
	private WebElement elemRememberMeCheckbox;
	
	//Twiter image
	@FindBy(how = How.XPATH, using = "//img[contains(@src,'twitter')]")
	private WebElement elemTwitterImg;	
	
	//Facebook image
	@FindBy(how = How.XPATH, using = "//img[contains(@src,'facebook')]")
	private WebElement elemFacebookImg;
	
	//Linkedin image
	@FindBy(how = How.XPATH, using = "//img[contains(@src,'linkedin')]")
	private WebElement elemLinkedinImg;		
	
	//User male circle image
	@FindBy(how = How.XPATH, using = "//div[@class='pre-icon os-icon os-icon-user-male-circle']")
	private WebElement elemUserMaleCircleImg;	
	
	//User male circle image
	@FindBy(how = How.XPATH, using = "//div[@class='pre-icon os-icon os-icon-fingerprint']")
	private WebElement elemFingerprintImg;	
	
	@FindBy(how = How.XPATH, using = "//div[@class='alert alert-warning']")
	private WebElement elemAlertWarning;	
	
	
	
	public boolean verifyLogo(){
		try{
			waitForElementToBeVisible(elemLogoImg);	
			elemLogoImg.isDisplayed();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public boolean verifyLoginFormHeader(String value){
		try{
			waitForElementToBeVisible(elemLoginFormHeader);
			return elemLoginFormHeader.getText().contains(value);
		}catch(Exception e){
			return false;
		}		
	}
	
	public boolean verifyLabel(String label){
		boolean result = false;
		for (int i = 0; i<listLabels.size(); i++){
			if (listLabels.get(i).getText().toString().contains(label)){
				result =  true;
				break;
			}
		}
		return result;
	}
	
	public boolean verifyUsernameTextbox(){
		try{
			waitForElementToBeVisible(elemLoginUsername);	
			elemLoginUsername.isDisplayed();
			return true;
		}catch(Exception e){
			return false;
		}		
	}

	public boolean verifyUsernameTextboxPlaceholder(String value){
		try{
			waitForElementToBeVisible(elemLoginUsername);	
			String actualValue = elemLoginUsername.getAttribute("placeholder");
			if(actualValue.equals(value)){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			return false;
		}		
	}
	
	public boolean verifyPasswordTextbox(){
		try{
			waitForElementToBeVisible(elemLoginPassword);	
			elemLoginPassword.isDisplayed();
			return true;
		}catch(Exception e){
			return false;
		}	
	}	
	
	public boolean verifyPasswordTextboxPlaceholder(String value){
		try{
			waitForElementToBeVisible(elemLoginPassword);	
			String actualValue = elemLoginPassword.getAttribute("placeholder");
			if(actualValue.equals(value)){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			return false;
		}		
	}
	
	public boolean verifyLoginButton(){
		try{
			waitForElementToBeVisible(elemLogin);	
			elemLogin.isDisplayed();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public boolean verifyRememberMeCheckbox(){
		try{
			waitForElementToBeVisible(elemRememberMeCheckbox);	
			elemRememberMeCheckbox.isDisplayed();
			return true;
		}catch(Exception e){
			return false;
		}		
	}	
	
	public boolean verifyTwitterImg(){
		try{
			waitForElementToBeVisible(elemTwitterImg);	
			elemTwitterImg.isDisplayed();
			return true;
		}catch(Exception e){
			return false;
		}		
	}		
	
	public boolean verifyFacebookImg(){
		try{
			waitForElementToBeVisible(elemFacebookImg);	
			elemFacebookImg.isDisplayed();
			return true;
		}catch(Exception e){
			return false;
		}		
	}		
	
	public boolean verifyLinkedinImg(){
		try{
			waitForElementToBeVisible(elemLinkedinImg);	
			elemLinkedinImg.isDisplayed();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public boolean verifyUserMaleImg(){
		try{
			waitForElementToBeVisible(elemUserMaleCircleImg);	
			elemUserMaleCircleImg.isDisplayed();
			return true;
		}catch(Exception e){
			return false;
		}		
	}	
	
	public boolean verifyFingerprintImg(){
		try{
			waitForElementToBeVisible(elemFingerprintImg);	
			elemFingerprintImg.isDisplayed();
			return true;
		}catch(Exception e){
			return false;
		}				
	}	

	public void clickLoginButton(){
		waitForElementToBeVisible(elemLogin);
		elemLogin.click();
	}
	
	public String getAlertWarning(){
		waitFor(Configuration.WAIT_MILLIS_MED_1);
		waitForElementToBeVisible(elemAlertWarning);
		return elemAlertWarning.getText();
	}

	public boolean isDisplayedAlertWarning(){
		waitFor(Configuration.WAIT_MILLIS_MED_1);
		try{			
			elemAlertWarning.isDisplayed();
			return true;
		}catch(Exception e){
			return false;
		}	
	}
	
	public void fillUsernameTextbox(String value){
		waitForElementToBeVisible(elemLoginUsername);
		if(value.length()>0){
			elemLoginUsername.sendKeys(value);	
		}
	}

	public void fillPasswordTextbox(String value){
		waitForElementToBeVisible(elemLoginPassword);
		if(value.length()>0){
			elemLoginPassword.sendKeys(value);
		}
	}	
}
