package search;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import screenShot.Helper;

public class GoogleKittens {

	private static String URL = "https://www.google.com/";
	private static RemoteWebDriver driver;
	private static ExtentReports extent;
	private static ExtentTest test;

	// Our initialise method
	@BeforeClass
	public static void init() {
		extent = new ExtentReports("src/test/resources/reports/report1.html", true);
		test = extent.startTest("ExtentDemo");
		System.setProperty("webdriver.gecko.driver",
				"src/test/resources/resource/geckodriver-v0.28.0-win64/geckodriver.exe");
		FirefoxOptions fOptions = new FirefoxOptions();
		fOptions.setHeadless(true);
		driver = new FirefoxDriver(fOptions);
		fOptions.addPreference("profile.default_content_setting_values.cookies", 2);
		fOptions.addPreference("network.cookie.cookieBehavior", 2);
		fOptions.addPreference("profile.block_third_party_cookies", true);
		driver.manage().window().setSize(new Dimension(1366, 768));

	}

	@Before
	public void setup() {

		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		try {
			driver.get(URL);
		} catch (TimeoutException e) {
			System.out.println("Page: " + URL + " did not load within 30 seconds!");
		}

	}

	// Our tear-down method
	@AfterClass
	public static void tearDown() {
		driver.quit();
		extent.endTest(test);
		extent.flush();
		extent.close();

	}

	// Checks Googles page title
	@Test
	public void googleTitleTest() {
		if (driver.getTitle().equals("Google")) {
			test.log(LogStatus.PASS, "Success");
		} else {
			test.log(LogStatus.FAIL, "Failed");
		}
	}

	// Checks the current driver URL
	@Test
	public void googleURLTest() {
		assertEquals("https://www.google.com/", driver.getCurrentUrl());
	}

	// Searches for Kittens, gets the number of images available and navigates to
	// index 0
	@Test
	public void searchTest() throws Exception {
		driver.get(URL + "/images");
		// Locates the input box with name "q"
		WebElement input = driver.findElement(By.name("q"));

		// Sends "Kittens" to input box
		input.sendKeys("Kittens");
		input.submit();

		// Waits for the search result class to finish loading before attempting to
		// locate
		new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.className("islrc")));

		// Sets the results as result
		WebElement result = driver.findElement(By.className("islrc"));

		// Adds all elements with an img tag to listResult
		List<WebElement> listResult = result.findElements(By.tagName("img"));
		System.out.println(listResult.size());

		// Creates a new action called action
		Actions action = new Actions(driver);

		// Using the action, this will click on the first element in the list
		action.moveToElement(listResult.get(0)).click().perform();
		
		// Captures a shot of the page at this point, you can see the highlighted img, etc
		Helper.snapShot(driver, "src/test/resources/reports/shot.png");
		
		// Using getAttribute we are able to get the src containing a link, copy and paste the output into your browser
		String kittenImgURL = listResult.get(3).getAttribute("src").toString();
		System.out.println(kittenImgURL);

	}
}
