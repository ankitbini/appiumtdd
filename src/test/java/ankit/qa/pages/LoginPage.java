package ankit.qa.pages;

import org.openqa.selenium.WebElement;

import ankit.qa.BaseTest;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginPage extends BaseTest{
	
	@AndroidFindBy(accessibility = "test-Username")
	private WebElement userNameField;
	
	@AndroidFindBy(accessibility = "test-Password")
	private WebElement passwordField;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"LOGIN\"]")
	private WebElement loginBtn;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Username and password do not match any user in this service.\"]")
	private WebElement errorTxt;
	
	
	public LoginPage enterUserName(String userName) {
		clear(userNameField);
		sendKeys(userNameField, userName, "Login with "+ userName);
		return this;
	}
	
	public LoginPage enterPassword(String password) {
		clear(passwordField);
		sendKeys(passwordField, password, "Password is "+ password);
		return this;
	}
	
	public ProductsPage pressLogin() {
		click(loginBtn, "press login Button");
		return new ProductsPage();
	}
	
	public String getErrorTxt() {
		String errorMsg = getAttribute(errorTxt, "text", "Error msg = ");
		return errorMsg;
	}
	
	public ProductsPage login(String userName, String password) {
		enterUserName(userName);
		enterPassword(password);
		return pressLogin();
	}

}
