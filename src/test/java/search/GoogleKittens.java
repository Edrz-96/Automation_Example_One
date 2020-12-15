package search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleKittens {

	private static String URL = "https://www.google.com/";
	private static WebDriver driver;

	// Our initialise method
	@BeforeClass
	public static void setup() {

		System.setProperty("webdriver.gecko.driver",
				"src/test/resources/resource/geckodriver-v0.28.0-win64/geckodriver.exe");
		FirefoxOptions fOptions = new FirefoxOptions();
		fOptions.setHeadless(false);
		driver = new FirefoxDriver(fOptions);
		fOptions.addPreference("profile.default_content_setting_values.cookies", 2);
		fOptions.addPreference("network.cookie.cookieBehavior", 2);
		fOptions.addPreference("profile.block_third_party_cookies", true);
		driver.manage().window().setSize(new Dimension(1366, 768));

	}

	// Our tear-down method
	@AfterClass
	public static void tearDown() {
		driver.quit();
	}

	// Checks Googles page title
	@Test
	public void googleTitleTest() {
		driver.get(URL);
		assertEquals("Google", driver.getTitle());

	}

	// Checks the current driver URL
	@Test
	public void googleURLTest() {
		assertEquals("https://www.google.com/", driver.getCurrentUrl());
	}

	// Searches for Kittens, gets the number of images available and navigates to index 0
	@Test
	public void searchTest() throws InterruptedException {
		driver.get(URL + "/images");
		// Locates the input box with name "q"
		WebElement input = driver.findElement(By.name("q"));
		
		// Sends "Kittens" to input box
		input.sendKeys("Kittens");
		input.submit();
		
		// Waits for the search result class to finish loading before attempting to locate
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
		// Using getAttribute we are able to get the src containing a link, copy and paste the output into your browser
		String kittenImgURL = listResult.get(3).getAttribute("src").toString();
		System.out.println(kittenImgURL);
	
	}
}
