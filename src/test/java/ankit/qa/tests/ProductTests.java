package ankit.qa.tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import ankit.qa.BaseTest;
import ankit.qa.pages.LoginPage;
import ankit.qa.pages.ProductDetailsPage;
import ankit.qa.pages.ProductsPage;
import ankit.qa.pages.SettingsPage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.AfterClass;

public class ProductTests extends BaseTest{
	
	LoginPage login;
	ProductsPage product;
	SettingsPage setting;
	ProductDetailsPage productDetails;
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
		  utils.log().info("product test before method");
		  login = new LoginPage();
		  utils.log().info("\n"+"**** Starting test:"+m.getName());
			
			  product =
			  login.login(loginUsers.getJSONObject("validUser").getString("userName"),
			  loginUsers.getJSONObject("validUser").getString("password"));
			 
	  }

	  @AfterMethod
	  public void afterMethod() {
		closeApp();
		launchApp();
	  }
 
	  @Test
	  public void validateProductsOnProductPageTest() {
		
		SoftAssert sa = new SoftAssert();
	    String actualSlbTitle = product.getSLBTitle();
		String expectedSLBTitle = strings.get("product_page_slb_title");
	    sa.assertEquals(actualSlbTitle, expectedSLBTitle);
			  
		String actualSlbPrice = product.getSLBPrice();
		String expectedSLBPrice = strings.get("product_page_slb_price");
		sa.assertEquals(actualSlbPrice, expectedSLBPrice);
		sa.assertAll();
	  }
	  
	  @Test
	  public void validateProductsOnProductDetailsPageTest() {
			
			/*
			 * product =
			 * login.login(loginUsers.getJSONObject("validUser").getString("userName"),
			 * loginUsers.getJSONObject("validUser").getString("password"));
			 */
			 
		SoftAssert sa = new SoftAssert();
		productDetails = product.pressSLBTitle();
			  
		String actualSlbTitle = productDetails.getSLBTitle();
		String expectedSLBTitle = strings.get("product_detalis_slb_title");
		sa.assertEquals(actualSlbTitle, expectedSLBTitle);
		
		String actualSlbTxt = productDetails.getSLTxt();
		String expectedSLBTxt = strings.get("product_details_slb_txt");
		sa.assertEquals(actualSlbTxt, expectedSLBTxt);
		
		String actualSlbPrice = productDetails.scrollToSBLPrice();
		String expectedSLBPrice = strings.get("product_description_page_slb_price");
		sa.assertEquals(actualSlbPrice, expectedSLBPrice);
		sa.assertAll();
	  }
}
