package ankit.qa.tests;

import org.testng.annotations.Test;

import ankit.qa.BaseTest;
import ankit.qa.pages.LoginPage;
import ankit.qa.pages.ProductsPage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class LoginTests extends BaseTest{
	
	LoginPage login;
	ProductsPage product;
	InputStream data;
	JSONObject loginUsers;
	
	@BeforeClass
	  public void beforeClass() throws IOException {
		try {
			String dataFile = "data/loginUsers.json";
			data = getClass().getClassLoader().getResourceAsStream(dataFile);
			JSONTokener tokener = new JSONTokener(data);
			loginUsers = new JSONObject(tokener);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			if(data != null) {
				data.close();
			}
		}
		
		
	  }

	  @AfterClass
	  public void afterClass() {
	  }
	  
	  @BeforeMethod
	  public void beforeMethod(Method m) {
		  utils.log().info("login test before method");
		  login = new LoginPage();
		  System.out.println("\n"+"**** Starting test:"+m.getName());
	  }

	  @AfterMethod
	  public void afterMethod() {
		  utils.log().info("login test after method");
		  closeApp();
		  launchApp();
	  }
 
	  @Test
	  public void invalidUserNameTest() {
			  login.enterUserName(loginUsers.getJSONObject("invalidUser").getString("userName"));
			  login.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password"));
			  login.pressLogin();
			  
			  
			  String actualErrorTxt = login.getErrorTxt()+"abc";
			  String expectedErrorTxt = strings.get("err_invalid_user_or_password");
			  utils.log().info("invaid user name");
			  Assert.assertEquals(expectedErrorTxt, actualErrorTxt);
	  }
	  
	  @Test
	  public void invalidPasswordTest()
	  {
		  login.enterUserName(loginUsers.getJSONObject("invalidPassword").getString("userName"));
		  login.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
		  login.pressLogin();
		  
		  
		  String actualErrorTxt = login.getErrorTxt();
		  String expectedErrorTxt = strings.get("err_invalid_user_or_password");
		  utils.log().info("invaid user name");
		  Assert.assertEquals(expectedErrorTxt, actualErrorTxt);
	  }
	  
	  
	  @Test
	  public void validUserTest()
	  {
		  login.enterUserName(loginUsers.getJSONObject("validUser").getString("userName"));
		  login.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
		  product = login.pressLogin();
		  
		  String actualProductTitle = product.getTitle();
		  String expectedProductTitle = strings.get("product_title");
		  utils.log().info("valid login");
		  Assert.assertEquals(actualProductTitle, expectedProductTitle);
	  }
  

  

}
