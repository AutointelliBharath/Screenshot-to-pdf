import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

public class Autointelli_Website {
	public static void main(String[] args) throws InterruptedException {
		ChromeOptions options = new ChromeOptions();

		options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\USER\\eclipse-workspace\\Screenshot_To_Pdf\\Driver\\chromedriver.exe");

		WebDriver driver = new ChromeDriver(options);

		driver.manage().window().maximize();

		// Navigate to web pages and capture screenshots
		driver.get("https://www.autointelli.com");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		JavascriptExecutor js = (JavascriptExecutor) driver;

		WebElement cookie = driver.findElement(By.xpath("//div[@class='cookieinfo-close']"));
		cookie.click();

		WebElement click = driver.findElement(By.xpath("//a[text()='REQUEST FOR DEMO']"));
		click.click();

		WebElement name = driver.findElement(By.xpath("//input[@type=\"text\"]"));
		name.sendKeys("Abdul Wahab");

		WebElement Email = driver.findElement(By.xpath("//input[@type=\"email\"]"));
		Email.sendKeys("abdulwahab.j@gmail.com");

		WebElement Phno = driver.findElement(By.xpath("//input[@id='txtdemophnnumber']"));
		Phno.sendKeys("8056656716");

		WebElement ComName = driver.findElement(By.xpath("//input[@id=\"txtdemofstcmpnyname\"]"));
		ComName.sendKeys("Autointelli");
		js.executeScript("arguments[0].scrollIntoView();", ComName);

		WebElement ItIncidentsvalue = driver.findElement(By.xpath("//input[@id=\"txtdemonoofincident\"]"));
		ItIncidentsvalue.sendKeys("10");

		WebElement AlertGeneratedValue = driver.findElement(By.xpath("//input[@id=\"txtdemomonsys\"]"));
		AlertGeneratedValue.sendKeys("7");

		WebElement select = driver.findElement(By.xpath("//select[@id=\"seldemoaitool\"]"));
		select.sendKeys("No");

		WebElement reason = driver.findElement(By.xpath("//input[@id=\"txtdemoreasonforchange\"]"));
		reason.sendKeys("Nil");

		driver.switchTo().frame(0);
		WebElement captacha = driver.findElement(By.xpath("//div[@id=\"rc-anchor-container\"]"));
		captacha.click();

		Thread.sleep(30000);

		driver.switchTo().defaultContent();
		WebElement submit = driver.findElement(By.xpath("//button[@class=\"btn btn-primary\"]"));

		submit.click();

		Thread.sleep(6000);
		captureScreenshot(driver, "Ai.png");

		// Close the WebDriver
		driver.quit();

		// Create a PDF document and add screenshots
		Document document = new Document(new Rectangle(600f, 350f));
		try {
			PdfWriter.getInstance(document, new FileOutputStream("Autointelli_Report.pdf"));
			document.open();

			// Add screenshots to the PDF
			addScreenshotToPDF(document, "Ai.png", 1366, 768);

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
	public static void addScreenshotToPDF(Document document, String screenshotFileName, int width, int height)
			throws IOException, DocumentException {
		Image image = Image.getInstance(screenshotFileName);

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String currentDateAndTime = dateformat.format(date);

//	image.scaleToFit(595.28f, 841.89f);

		float scaleFactorWidth = 600f / width;
		float scaleFactorHeight = 350f / height;
		float scaleFactor = Math.min(scaleFactorWidth, scaleFactorHeight);
		image.scaleAbsolute(width * scaleFactorWidth, height * scaleFactorHeight);

		float x = (600f - (width * scaleFactor)) / 2f;
		float y = (350f - (height * scaleFactor)) / 2f;
		image.setAbsolutePosition(x, y);

		document.add(new Paragraph(""));
		Paragraph dateAndTimeParagraph = new Paragraph(new Phrase("Date and Time: " + currentDateAndTime));
		dateAndTimeParagraph.setAlignment(Element.ALIGN_CENTER);

		document.add(image);
		document.add(new Paragraph(dateAndTimeParagraph));
		document.newPage();

		System.out.println("Converted");
	}
}
