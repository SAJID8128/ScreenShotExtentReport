package basePackage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;
	public static WebDriver driver;
	
	@BeforeSuite
	public void setExtent() {
		// Configuration of the Extent Report.
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/ExtentReport/MyReport.html");
		
		htmlReporter.config().setDocumentTitle("Automation Test Report");
		htmlReporter.config().setReportName("OrangeHRM Test Automation Report");
		htmlReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		
		extent.setSystemInfo("HostName", "MyHost");
		extent.setSystemInfo("ProjectName", "OrangeHRM");
		extent.setSystemInfo("Tester", "Sajid");
		extent.setSystemInfo("OS", "macOS Big Sur");
		extent.setSystemInfo("Browser", "Chrome");
		
	}
	
	@AfterSuite
	public void endReport() {
		// To end the report.
		extent.flush();
	}
	
	@BeforeMethod
	public void setUp() {
		// Open browser and navigate to the stated URL.
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://opensource-demo.orangehrmlive.com/index.php/auth/validateCredentials");
		driver.manage().window().maximize();
		
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		// Code to get status of our test cases and log them in our extent report.
		if(result.getStatus()==ITestResult.FAILURE) {
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
			
			// These 2 lines are required to attach the screenshot onto our extent report.
			String pathString=BaseClass.screenShot(driver, result.getName());
			test.addScreenCaptureFromPath(pathString);
			
		} else if(result.getStatus()==ITestResult.SKIP) {
			test.log(Status.SKIP, "Skipped Test case is: "+result.getName());
			
		} else if(result.getStatus()==ITestResult.SUCCESS) {
			test.log(Status.PASS, "Passed Test case is: "+result.getName());
		}
		// Close the browser.
		driver.close();
	}
	
	
	public static String screenShot(WebDriver driver, String filename) {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir")+"/ScreenShot/"+filename+".png";
		File finalDestination = new File(destination);
		
		try {
			FileUtils.copyFile(source, finalDestination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return destination;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
