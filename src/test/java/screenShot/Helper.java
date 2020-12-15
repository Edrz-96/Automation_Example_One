package screenShot;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.google.common.io.Files;

public class Helper {

	public static void snapShot(WebDriver webdriver, String fileWithPath) throws Exception {

		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);

		// Call method to create image file

		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

		// Move image file to new destination

		File DestFile = new File(fileWithPath);

		// Copy file at destination

		Files.copy(SrcFile, DestFile);

	}
}
