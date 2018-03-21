package com.automate.urls;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;
public class CloudPing_GoogleChrome
{

	public static void main(String[] args) 
	{
		Properties prop = new Properties();
		ResourceBundle rb=ResourceBundle.getBundle("com.automate.resources.config");
		System.out.println("Hi"+rb.getString("CHROME_DRIVER_PATH"));
		
		//InputStream input = null;

		try {
			//input = new FileInputStream("config.properties");

			// load a properties file
			//prop.load(input);

			// get the property value and print it out
			
			System.out.println(prop.getProperty("CHROME_DRIVER_PATH"));
			
			//prop.
		
			System.setProperty("webdriver.chrome.driver", rb.getString("CHROME_DRIVER_PATH"));
		
		
			ChromeDriver driver=new ChromeDriver();
	
			driver.get("http://www.cloudping.info/");
			WebElement e=driver.findElement(By.xpath("//input[@id='pingbutton']"));
			e.click();
		
			Thread.sleep(25000);
			System.out.println("Kindly find the Screenshot at this location "+ "C:\\Tejas\\Cloudping\\");
			Date  d=new Date();
			DateFormat df=new SimpleDateFormat("ddMMMYYYY-hh-mm-ss");
		//BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));


		
			String obj=rb.getString("IMAGE_FILEPATH")+df.format(d)+"_Screen.png";
			//System.out.println("the wait is over "+obj);
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
