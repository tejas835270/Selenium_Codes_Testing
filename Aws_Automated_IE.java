package com.automate.urls;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Aws_Automated_IE 
{

	public static void main(String[] args) throws Exception 
	{
		
		Properties prop = new Properties();
		ResourceBundle rb=ResourceBundle.getBundle("com.automate.resources.config");
		System.out.println("Hi"+rb.getString("IE_DRIVER_PATH"));
		try {
			//input = new FileInputStream("config.properties");

			// load a properties file
			//prop.load(input);

			// get the property value and print it out
			
			System.out.println(prop.getProperty("IE_DRIVER_PATH"));
			
			//prop.
		
			System.setProperty("webdriver.chrome.driver", rb.getString("IE_DRIVER_PATH"));
		
		
		
		
		
		System.setProperty("webdriver.ie.driver",  rb.getString("IE_DRIVER_PATH"));	
		DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
		caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
		WebDriver driver = new InternetExplorerDriver(caps);
		driver.get("https://clients.amazonworkspaces.com/Health.html");
		WebDriverWait wait = new WebDriverWait(driver,60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@title='Service Operating Normally']")));
		Thread.sleep(5000);
		System.out.println("Kindly find the Screenshot at this location "+"C:/Tejas/AWS/");
		Date  d=new Date();
		DateFormat df=new SimpleDateFormat("ddMMMYYYY-hh-mm-ss");
		
		String obj=rb.getString("IMAGE_FILEPATH_AWS")+df.format(d)+"_Screen.png";
		//String obj="C:/Tejas/AWS/"+df.format(d)+"_Screen.png";
		takeSnapShot(driver,obj );
		System.out.println("-------Screenshot taken-------");
		driver.close();
		driver.quit();
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
		}
	}
	
	public static void takeSnapShot(WebDriver webdriver,String fileWithPath) throws Exception
	{

        //Convert web driver object to TakeScreenshot

        TakesScreenshot scrShot =((TakesScreenshot)webdriver);

        //Call getScreenshotAs method to create image file

         File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

            //Move image file to new destination

         File DestFile=new File(fileWithPath);
              
                
                //Copy file at destination

          FileUtils.copyFile(SrcFile, DestFile);
	
	}
}
