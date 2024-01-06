package ankit.qa;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.Status;

import ankit.qa.reports.ExtentReport;
import ankit.qa.utils.TestUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
 
	protected static AppiumDriver driver;
	protected static Properties props;
	protected static String dateTime; 
	protected static HashMap<String, String> strings = new HashMap<String, String>();
	InputStream inputStream;
	InputStream stringsis;
	public TestUtils utils = new TestUtils();
	private static AppiumDriverLocalService server;
	
	
  public BaseTest() {
	  PageFactory.initElements(new AppiumFieldDecorator(driver), this);
  }
  
   
  @BeforeMethod
  public void beforeMethod() {
	  utils.log().info("super before method");
	  ((CanRecordScreen)driver).startRecordingScreen();
  }
  
  @AfterMethod
  public void afterMethod(ITestResult result) {
	  utils.log().info("super after method");
	  String media = ((CanRecordScreen) getDriver()).stopRecordingScreen();
	  Map <String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();
	  String dirPath = "videos" + File.separator + params.get("platformName") + "_" + params.get("deviceName") 
		+ File.separator + getDateTime() + File.separator + result.getTestClass().getRealClass().getSimpleName();
	  // now the video is recorded only when the test case is failed
	  if(result.getStatus() == 2) {
		  File videoDir = new File(dirPath);
		  if(!videoDir.exists()) {
				videoDir.mkdirs();
			}	
		  
		  try {
			FileOutputStream stream = new FileOutputStream(videoDir + File.separator + result.getName() + ".mp4");
			stream.write(Base64.decodeBase64(media));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  

  }
	
  @Parameters({"platformName","deviceName"})	
  @BeforeTest
  public void beforeTest(String platformName, String deviceName) throws Exception {
	  dateTime = utils.getDateTime();
	  try {
		  props = new Properties();
		  String propFileName = "config.properties";
		  String xmlFileName = "strings/strings.xml";
		  
		  inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		  props.load(inputStream);
		  
		  stringsis = getClass().getClassLoader().getResourceAsStream(xmlFileName);
		  strings = utils.parseStringXML(stringsis);
		  String appUrl = System.getProperty("user.dir")+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"app"+File.separator+"Android.SauceLabs.Mobile.Sample.app.2.7.1.apk";
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("platformName", platformName);
			caps.setCapability("deviceName", deviceName);
			caps.setCapability("automationName", props.getProperty("androidAutomationName"));
			caps.setCapability("appPackage", props.getProperty("androidAppPackage"));
			caps.setCapability("appActivity", props.getProperty("androidAppActivity"));
			caps.setCapability("app", appUrl);
			
			URL url = new URL(props.getProperty("appiumURL"));
			
			driver = new AndroidDriver(url, caps);
			
		
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}finally {
		if(inputStream != null) {
			inputStream.close();
		}
		if(stringsis != null) {
			stringsis.close();
		}
	}
	  
	  
  }
  
  @BeforeSuite
  public void beforeSuite() {
	  server = getAppiumServerDefault();
	  server.start();
	  //If we comment the below line then all the Appium server log is coming into the console
	  server.clearOutPutStreams();
	  utils.log().info("Appium server started");
  }
  
  @AfterSuite
  public void afterSuite() {
	server.stop();  
	utils.log().info("Appium server stoped");
  }
  
//for Windows
	public AppiumDriverLocalService getAppiumServerDefault() {
		return AppiumDriverLocalService.buildDefaultService();
	}
  
  public AppiumDriver getDriver() {
	  return driver;
  }
  
  public String getDateTime() {
	  return dateTime;
  }
  
  public void waitForVisibility(WebElement element)
  {
	  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestUtils.WAIT));
	  wait.until(ExpectedConditions.visibilityOf(element));
  }
  
  public void clear(WebElement element) {
	  waitForVisibility(element);
	  element.clear();
  }
  
  public void clear(WebElement element, String msg) {
	  waitForVisibility(element);
	  element.clear();
  }
  
  public void click(WebElement element)
  {
	  waitForVisibility(element);
	  element.click();
  }
  
  public void click(WebElement element, String msg)
  {
	  waitForVisibility(element);
	  utils.log().info(msg);
	  ExtentReport.getTest().log(Status.INFO, msg);
	  element.click();
  }
  
  public void sendKeys(WebElement element, String txt) {
	  waitForVisibility(element);
	  element.sendKeys(txt);
  }
  
  public void sendKeys(WebElement element, String txt, String msg) {
	  waitForVisibility(element);
	  utils.log().info(msg);
	  ExtentReport.getTest().log(Status.INFO, msg);
	  element.sendKeys(txt);
  }
  
  public String getAttribute(WebElement element, String attribut) {
	  waitForVisibility(element);
	  return element.getAttribute(attribut);
  }
  
  public String getAttribute(WebElement element, String attribut, String msg) {
	  waitForVisibility(element);
	  String text = element.getAttribute(attribut);
	  utils.log().info(msg + " "+ text);
	  ExtentReport.getTest().log(Status.INFO, msg + " "+ text);
	  return text;
  }
  
  public void closeApp() {
	  ((AndroidDriver)driver).terminateApp("com.swaglabsmobileapp");
  }
  
  public void launchApp() {
	  ((AndroidDriver)driver).activateApp("com.swaglabsmobileapp");
  }
  
  
  //in the below method description is represented as accessibility id of the web element
  public WebElement scrollToElement() {
	  return driver.findElement(AppiumBy.androidUIAutomator(
			  "new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
					  + "new UiSelector().description(\"test-Price\"));"));
  }

  @AfterTest
  public void afterTest() {
	  
	  driver.quit();
  }

}
