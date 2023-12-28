
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Itext_Conversion {
	public static void main(String[] args) throws InterruptedException {
		// Set up the WebDriver (e.g., ChromeDriver)
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\USER\\eclipse-workspace\\Screenshot_To_Pdf\\Driver\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		driver.manage().window().maximize();

		// Navigate to web pages and capture screenshots
		driver.get("https://www.amazon.com/");
		Thread.sleep(3000);
		captureScreenshot(driver, "screenshot1.png");

		driver.get("https://www.flipkart.com/");
		Thread.sleep(3000);
		captureScreenshot(driver, "screenshot2.png");

		// Close the WebDriver
		driver.quit();

		// Create a PDF document and add screenshots
		Document document = new Document(new Rectangle(600f, 350f));
		try {
			PdfWriter.getInstance(document, new FileOutputStream("a4_screenshots7.pdf"));
			document.open();

			// Add screenshots to the PDF
			addScreenshotToPDF(document, "screenshot1.png", 1366, 768);
			addScreenshotToPDF(document, "screenshot2.png", 1366, 768);

			document.close();
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}

	// Capture a screenshot and save it to a file
	public static void captureScreenshot(WebDriver driver, String filename) {
		try {
			TakesScreenshot screenshot = (TakesScreenshot) driver;
			File source = screenshot.getScreenshotAs(OutputType.FILE);
			File destination = new File(filename);
			Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Add a screenshot to the PDF document
	public static void addScreenshotToPDF(Document document, String screenshotFileName,int width, int height)
			throws IOException, DocumentException {
		Image image = Image.getInstance(screenshotFileName);
//		image.scaleToFit(595.28f, 841.89f);
		
		float scaleFactorWidth = 600f / width;
		float scaleFactorHeight = 350f / height;
		float scaleFactor = Math.min(scaleFactorWidth, scaleFactorHeight);
		image.scaleAbsolute(width * scaleFactorWidth,height * scaleFactorHeight);
		
		float x = (600f - (width * scaleFactor)) / 2f;
		float y = (350f - (height * scaleFactor)) / 2f;
		image.setAbsolutePosition(x, y);
		
		document.add(image);
		document.newPage();
		
		System.out.println("Converted");
	}	
}
