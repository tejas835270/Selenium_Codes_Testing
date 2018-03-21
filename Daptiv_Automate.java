package automateTest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.exec.ExecuteException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
public class Daptiv_Automate {

	

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		Properties prop = new Properties();
		
		InputStream input = null;

		try {


/*System.setProperty("webdriver.ie.driver", "C:/Tejas/eclipse-jee-mars-2-win32-x86_64/eclipse/dropins/IEDriverServer.exe");
DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
caps.setCapability("enablePersistentHover", false);
caps.setCapability("ignoreZoomSetting", true);
caps.setCapability("nativeEvents", false);
caps.setCapability("ignoreProtectedModeSettings", true);
caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
caps.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
caps.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
caps.setCapability("ie.ensureCleanSession", true);
caps.setJavascriptEnabled(true);


WebDriver driver = new InternetExplorerDriver(caps);
caps.setCapability("ignoreProtectedModeSettings", true);*/
			input = new FileInputStream("config.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			
			System.out.println(prop.getProperty("CHROME_DRIVER_PATH"));
		
			System.setProperty("webdriver.chrome.driver", prop.getProperty("CHROME_DRIVER_PATH"));
		
		
			ChromeDriver driver=new ChromeDriver();
			
			driver.get("https://login.daptiv.nl/?returnUrl=http%3a%2f%2fppm.daptiv.nl%2fdefault.aspx");
		
			
			Thread.sleep(2000);
			WebElement email= driver.findElement(By.id("email"));
			String Email=prop.getProperty("EMAIL_ID");
			
			email.sendKeys(Email);
			System.out.println(Email);
						
			//password entering using config files
			WebElement pass=driver.findElement(By.id("password"));
			String Pass=prop.getProperty("PASSWORD");
			pass.sendKeys(Pass);
			
			WebElement login=driver.findElement(By.id("login-btn"));
			login.submit();
		
			Thread.sleep(2000);
			
			/*WebElement sysnot=driver.findElement(By.xpath("//*[@id='system-notification']"));
			if(sysnot.isDisplayed()){
				WebElement close=driver.findElement(By.id("closeSystemNotification"));
				close.click();
			}*/
			
			Thread.sleep(3000);
			
			
			
			WebElement timesheet=driver.findElement(By.linkText("Timesheets"));
			timesheet.click();
			Thread.sleep(3000);
			
			//Use to enter hours mentioned in config files i.e either 6.0 or 2.0
			
		
			
		/* 	String E3=prop.getProperty("HOL_HOURS");
		
		 	
		 	
		 		String HOUR11=prop.getProperty("H11");
		 		String HOUR12=prop.getProperty("H12");
		 		String HOUR13=prop.getProperty("H13");
		 		String HOUR14=prop.getProperty("H14");
		 		String HOUR15=prop.getProperty("H15");
		 		
		 		HashMap hoursData1 = new HashMap();
				if(!StringUtils.isBlank(HOUR11)){
					hoursData1.put("H21", HOUR11);
				}
				if(!StringUtils.isBlank(HOUR12)){
					hoursData1.put("H22", HOUR12);
				}
				if(!StringUtils.isBlank(HOUR13)){
					hoursData1.put("H23", HOUR13);
				}
				if(!StringUtils.isBlank(HOUR14)){
					hoursData1.put("H24", HOUR14);
				}
				if(!StringUtils.isBlank(HOUR15)){
					hoursData1.put("H25", HOUR15);
				}
				
		 		
				Set set1 = hoursData1.entrySet();
			      Iterator iterator1 = set1.iterator();
			      while(iterator1.hasNext()) {
			         Map.Entry mentry1 = (Map.Entry)iterator1.next();
			         System.out.print("key is: "+ mentry1.getKey() + " & Value is: ");
			         System.out.println(mentry1.getValue());
			         WebElement hourObj1=driver.findElement(By.id(mentry1.getValue().toString()));
			         hourObj1.clear();
			         String E1=prop.getProperty("HOURS1");
			         hourObj1.sendKeys(E1);
		 
			      }*/
			/*WebElement HOUR111=driver.findElement(By.id(HOUR11));
			HOUR111.clear();
			HOUR111.sendKeys(E1);
			
			
			WebElement HOUR121=driver.findElement(By.id(HOUR12));
			HOUR121.clear();
			HOUR121.sendKeys(E1);
			
			
			WebElement HOUR131=driver.findElement(By.id(HOUR13));
			HOUR131.clear();
			//String E3=prop.getProperty("HOURS2");
			HOUR131.sendKeys(E1);
			
	
				
			
			
			WebElement HOUR141=driver.findElement(By.id(HOUR14));
			HOUR141.clear();
			HOUR141.sendKeys(E1);
			
			
			WebElement HOUR151=driver.findElement(By.id(HOUR15));
			HOUR151.clear();
			HOUR151.sendKeys(E1);
			
			*/
			//---------------------------------
			
			
			//enter the timesheet hours into each field for Hours2
			
			
		/*	try{
				String HOUR21=prop.getProperty("H21");
				String HOUR22=prop.getProperty("H22");
				String HOUR23=prop.getProperty("H23");		
				String HOUR24=prop.getProperty("H24");
				String HOUR25=prop.getProperty("H25");
			
				
				if(HOUR21.isEmpty()&&HOUR22.isEmpty()&&HOUR23.isEmpty()&&HOUR24.isEmpty()&&HOUR25.isEmpty())		
					System.out.println("There are no values present");
			
				HashMap hoursData = new HashMap();
				if(!StringUtils.isBlank(HOUR21)){
					hoursData.put("H21", HOUR21);
				}
				if(!StringUtils.isBlank(HOUR22)){
					hoursData.put("H22", HOUR22);
				}
				if(!StringUtils.isBlank(HOUR23)){
					hoursData.put("H23", HOUR23);
				}
				if(!StringUtils.isBlank(HOUR24)){
					hoursData.put("H24", HOUR24);
				}
				if(!StringUtils.isBlank(HOUR25)){
					hoursData.put("H25", HOUR25);
				}
				
				hoursData.put("H22", (StringUtils.isBlank(HOUR22))?HOUR22:"");
				hoursData.put("H23", (StringUtils.isBlank(HOUR23))?HOUR23:"");
				hoursData.put("H24", (StringUtils.isBlank(HOUR24))?HOUR24:"");
				hoursData.put("H25", (StringUtils.isBlank(HOUR25))?HOUR25:"");
				
				Set set = hoursData.entrySet();
			      Iterator iterator = set.iterator();
			      while(iterator.hasNext()) {
			         Map.Entry mentry = (Map.Entry)iterator.next();
			         System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
			         System.out.println(mentry.getValue());
			         WebElement hourObj=driver.findElement(By.id(mentry.getValue().toString()));
			         hourObj.clear();
			         String E2=prop.getProperty("HOURS2");
			         hourObj.sendKeys(E2);
			      }
			
				
			}
			catch(Exception e){
				e.printStackTrace();
				System.out.println(e);
			}*/
			
			//-----------------------------*********--------------------------
			
			//Holiday entries
			
			
			/*try{
				String HOL11=prop.getProperty("HOL1");
				String HOL12=prop.getProperty("HOL2");
				String HOL13=prop.getProperty("HOL3");

				
			if(HOL11.isEmpty()&&HOL12.isEmpty()&&HOL13.isEmpty())
			System.out.println("Holiday entries are empty");
			else{
				
			WebElement HR1=driver.findElement(By.id(HOL11));
			HR1.clear();
			HR1.sendKeys(E3);
			
			
			WebElement HR2=driver.findElement(By.id(HOL12));
			HR2.clear();
			HR2.sendKeys(E3);
			
			WebElement HR3=driver.findElement(By.id(HOL13));
			HR3.clear();
			HR3.sendKeys(E3);
			}
			}
						catch(NullPointerException e){
				 System.out.println(e);
			}*/
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement save=driver.findElement(By.id("SaveButton"));
			if(!save.isDisplayed())
			{
				Thread.sleep(1000);
				js.executeScript("alert('Time Sheet has been already Submitted....!!!!')");
				Thread.sleep(2000);
				driver.switchTo().alert().accept();
				Thread.sleep(2000);
			}
			else
			{
			
			Thread.sleep(1000);
			String loghours="40.00";
			
			save.click();
			Thread.sleep(5000);
			WebElement total=driver.findElement(By.xpath("//*[@id='_grid_row13']/td[@class='ftr-ttls ftr-brd']"));
			
			System.out.println(total.getText());
			
			String totalold=total.getText();
			
			Thread.sleep(5000);
			
			if(totalold.equals(loghours))
			{
				
				System.out.println("Already 40hrs are saved");
			}
			
					
			else
			{
		
				js.executeScript("alert('Please fill in up your Timesheet')");
				
				Thread.sleep(5000);
				
				driver.switchTo().alert().accept();
			
			
			boolean done=false;
				while(!done)
				{
					
				
				Thread.sleep(10000);
				save.click();
				Thread.sleep(5000);
				WebElement total1=driver.findElement(By.xpath("//*[@id='_grid_row13']/td[11]/span"));
				String ntotal=total1.getText();
				System.out.println(total1.getText());
				if(ntotal.equals(loghours))
				{
					
					js.executeScript("alert('Thanks...!!!!You have logged 40Hrs...!!!')");
					Thread.sleep(3000);
					driver.switchTo().alert().dismiss();
					Thread.sleep(3000);
					/*js.executeScript("alert('Your timesheet will be submited now')");
					driver.switchTo().alert().dismiss();
					Thread.sleep(3000);*/
					
					//System.out.println("You have completed the 40 hrs time");
					done=true;
				}else {
					Thread.sleep(2000);
					js.executeScript("alert('Complete your 40 Hrs Time')");
					Thread.sleep(2000);
					driver.switchTo().alert().dismiss();
					Thread.sleep(2000);
					//System.out.println("Yours total is not yet complete");
				}
					
				Thread.sleep(1000);
				}
			
			
			}
				Thread.sleep(1000);
			WebElement sub=driver.findElement(By.id("SubmitButton"));
			sub.click();
			
					
			Thread.sleep(2000);
			
			WebElement Fframe=driver.findElement(By.tagName("iframe"));
			driver.switchTo().frame(Fframe);
			
			WebElement txt_com =driver.findElement(By.id("txtComment"));
			
			txt_com.click();
			String sendk=prop.getProperty("COMM");
			txt_com.sendKeys(sendk);
			System.out.println(sendk);
			
			
			WebElement submitbutn=driver.findElement(By.id("SubmitButton"));
			submitbutn.click();
			
			
			/*WebElement cancelbutn=driver.findElement(By.xpath("//*[@id='submitComment']/table/tbody/tr[2]/td/input[2]"));
			cancelbutn.click();
			*/
			
			/*
			System.out.println("It is working fine");
			
			System.out.println("The timesheet is saved");*/
	
			
			
			Thread.sleep(5000);
			js.executeScript("alert('Your timesheet is submitted')");
			Thread.sleep(2000);
			driver.switchTo().alert().dismiss();
			
			Thread.sleep(3000);
			
			
			}	
			driver.close();
			driver.quit();
		}//try

		catch(Exception ex) 
		{
			ex.printStackTrace();
		}
}//main
}//class
