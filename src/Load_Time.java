import java.util.Date;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Load_Time {
public static void main(String[] args) {
	System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"./Driver/chromedriver.exe");
	WebDriver driver = new ChromeDriver();
	driver.manage().window().maximize();
	driver.get("https:/www.facebook.com/");
	Date startTime = new Date();
	JavascriptExecutor js = (JavascriptExecutor) driver;
	js.executeScript("return document.readyState").equals("complete");
	Date endTime = new Date();
	long pageLoadTime = endTime.getTime() - startTime.getTime();
	
	System.out.println("Page Load Time: " + pageLoadTime + "milliseconds");
	
	driver.quit();
}
}
