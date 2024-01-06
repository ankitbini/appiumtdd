package ankit.qa.reports;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport {

	static ExtentReports extent;
	final static String filePath = "Extent.html";
	static  Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
	
	public synchronized static ExtentReports getReporter() {
		if(extent == null) {
			ExtentSparkReporter spark = new ExtentSparkReporter("Extent.html");
			spark.config().setDocumentTitle("Appium Framework");
			spark.config().setReportName("My App");
			spark.config().setTheme(Theme.DARK);
			extent = new ExtentReports();
			extent.attachReporter(spark);
			
			// for version 5 use below
			//ExtentSparkReporter spark = new ExtentSparkReporter("Extent.html");
			//extent = new ExtentReports();
			//extent.attachReporter(spark);
		}
		return extent;
	}
	
	public static synchronized ExtentTest getTest() {
		System.out.println("getting test = "+ extentTestMap.get((int) (long)(Thread.currentThread().getId())).toString());
		return (ExtentTest) extentTestMap.get((int) (long)(Thread.currentThread().getId()));
	}
	
	public static synchronized ExtentTest startTest(String testName, String desc) {
		ExtentTest test = getReporter().createTest(testName, desc);
		extentTestMap.put((int) (long)(Thread.currentThread().getId()), test);
		System.out.println("adding test ID to map = "+extentTestMap.size());
		return test;
	}
}
