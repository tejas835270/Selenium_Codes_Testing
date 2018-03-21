package com.nice.actimize;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

//import com.Loggers.Log_Demo;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class Nice_Filo {

	
	// TODO Auto-generated method stub

	public static ArrayList<ArrayList<String>> executeQuery(String fileName, String query) throws FilloException {
		return executeExcelQuery(fileName, query);
	}

	public static String executeUpdateQuery(String fileName, String query) throws FilloException {
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(fileName);
		connection.executeUpdate(query);
		connection.close();
		return "Update was Successful";
	}

	/**
	 * This method treats excel as a database.<BR>
	 * You can get data from the excel. file just using a basic query.<BR>
	 * 
	 * @param fileName
	 * @param query
	 * @return
	 * @throws FilloException
	 */
	public static ArrayList<ArrayList<String>> executeExcelQuery(String fileName, String query) throws FilloException {
		ArrayList<ArrayList<String>> listOfLists = new ArrayList<ArrayList<String>>();
		ArrayList<String> someList = new ArrayList<String>();
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(fileName);
		query = query.toUpperCase();
		System.out.println(query);
		Recordset recordset = connection.executeQuery(query);
		System.out.println("Total number of row returned " + recordset.getCount());
		System.out.println();
		while (recordset.next()) {
			ArrayList<String> dataColl = recordset.getFieldNames();
			// System.out.println("Total data column " + dataColl);
			Iterator<String> dataIterator = dataColl.iterator();
			String[] columns = columnsSplit(query);
			int width = 0;
			// Width size
			if (query.contains("*")) {
				width = dataColl.size();
				// System.out.println(width);
			} else {
				width = columns.length;
				// System.out.println(width);
			}
			String[] rowByRow = new String[width];
			int rowNo = 0;
			while (dataIterator.hasNext()) {
				for (int i = 0; i <= dataColl.size() - 1; i++) {
					String data = dataIterator.next();
					if (query.contains("*")) {
						String dataVal = recordset.getField(data);
						// System.out.println(dataVal);
						rowByRow[rowNo] = dataVal;
						rowNo++;
					} else {
						// System.out.println(columns.length);
						for (String column : columns) {
							if (column.length() > 0) {
								if (data.equalsIgnoreCase(column)) {
									String dataVal = recordset.getField(column);
									rowByRow[rowNo] = dataVal;
									// System.out.println(dataVal);
									rowNo++;
								}
							}
						}
					}
				}
				ArrayList<String> test = new ArrayList<>();
				List<String> xyz = Arrays.asList(rowByRow);
				test.addAll(xyz);
				listOfLists.add(test);
				rowNo = 0;
				someList.iterator();
				break;
			}
		}
		recordset.close();
		connection.close();
		return listOfLists;
	}

	
	private static String[] columnsSplit(String query) {
		int start = 7;
		int end = query.indexOf(" FROM");
		String columnsWithComma = query.substring(start, end);
		return columnsWithComma.split(",");
	}

	public static String getFileName(String fileOrigin) {
		String projectPath = System.getProperty("user.dir");
		String filePath = projectPath + fileOrigin;
		filePath = filePath.replace("/", "\\");
		return filePath;
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
	

	 public static void main(String[] args) throws IOException, InterruptedException {
	
		Properties prop = new Properties();

		InputStream input = null;
		input = new FileInputStream("Config.prop");
	
		Logger log = Logger.getLogger(Nice_Filo.class);
        PropertyConfigurator.configure("./src/log.properties");
        log.debug("Log4j.property file is imported properly");
		// load a properties file
		prop.load(input);
		String NiceTrail = Nice_Filo.getFileName(prop.getProperty("SourceFilePath"));
		String sourceColumns = prop.getProperty("Source_Columns");
		String sourceSheetName = prop.getProperty("Source_Sheet_Name");
		
		//Code for user and Password 
		System.setProperty("webdriver.ie.driver",
				"C:/Tejas/eclipse-jee-mars-2-win32-x86_64/eclipse/dropins/IEDriverServer.exe");
		DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
		caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		caps.setCapability("enablePersistentHover", false);
		caps.setCapability("ignoreZoomSetting", true);
		caps.setCapability("nativeEvents", false);
		caps.setCapability("ignoreProtectedModeSettings", true);
		caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		caps.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
		caps.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
		caps.setCapability("ie.erensureCleanSession", true);
		caps.setJavascriptEnabled(true);
		
	
		String Dest_File="Update1.txt";
		File src = new File(Dest_File);
		//log.debug("Destination file is at this location:- "+src.getCanonicalPath());
		System.out.println("Destination file is at this location:- "+src.getCanonicalPath());
		
		log.info("Destination file is at this location :- "+src.getCanonicalPath());
		System.out.println();
		
		FileOutputStream fileOutput = new FileOutputStream(src);
		FileWriter FW=new FileWriter(src);
		BufferedWriter BW=new BufferedWriter(FW);
		
		WebDriver driver = new InternetExplorerDriver(caps);
		driver.get("https://rsit.actimize/RCM/");

		WebDriverWait wait = new WebDriverWait(driver, 5);
		//wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("j_username_1")));
		
		driver.manage().window().maximize();
		
		WebElement username =(new WebDriverWait(driver, 5)).until(ExpectedConditions.elementToBeClickable(By.id("j_username_1")));
		
		//WebElement username = driver.findElement(By.id("j_username_1"));
		username.click();
		username.clear();
		username.sendKeys("tvx");

		WebElement pass = driver.findElement(By.id("j_password"));
		pass.click();
		pass.clear();
		pass.sendKeys("password");

		Thread.sleep(1000);
		WebElement login = driver.findElement(By.id("textButton_btnLogin"));
		login.click();

		//Thread.sleep(15000);

		WebElement ondemandsearch = (new WebDriverWait(driver, 8)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='menu_tab_2']")));
				//driver.findElement(By.xpath("//*[@id='menu_tab_2']"));
		ondemandsearch.click();

		Thread.sleep(5000);

		WebElement SD = driver.findElement(By.xpath("//*[@id='ScanConfigDropDown']"));

		Select dropdown = new Select(SD);
		dropdown.selectByValue("UKSANCTIONSD");

		WebElement custtype = driver.findElement(By.xpath("//*[@id='ctype']"));
		Select cust_person = new Select(custtype);
		cust_person.selectByValue("U");
		
		//End of code for user and password
		
		
		
		try {
			ArrayList<ArrayList<String>> TESTDATA = Nice_Filo.executeQuery(NiceTrail,"Select " + sourceColumns + " From " + sourceSheetName);
			
			/*
			String Dest_File="/src/com/nice/actimize/Update1.txt";
			File src = new File(Dest_File);
			System.out.println(src.getCanonicalPath());
			
			FileInputStream filein=new FileInputStream(DestinationFile);
			
			FileOutputStream fileOutput = new FileOutputStream(src);
			
				
			FileWriter FW=new FileWriter(src);
			BufferedWriter BW=new BufferedWriter(FW);
			BW.write("This is 1st line");*/
			
			for (int row=0 ; row<TESTDATA.size() ; row ++) {
				ArrayList<String> rowData = TESTDATA.get(row);
				//int col=0;
				System.out.println("Test Data Size is "+TESTDATA.size());
				//List<String> coldata=TESTDATA.get(col);
				boolean flag = false;
				System.out.println();
				
				System.out.println((row+1)+"th row is started for processing ");
				log.info("Row has started processing");
				System.out.println();
				System.out.println("Row Data before update is :- "+rowData);
				log.info("Row data is before update is :-"+rowData);
				Thread.sleep(1000);
				// Code for Column Specific 
				WebElement radiobutton = driver.findElement(By.xpath("//*[@id='nameRadioFull']"));
				radiobutton.click();
				
				WebElement fullname = driver.findElement(By.xpath("//*[@id='fullNameTxt']"));
				fullname.clear();
				fullname.click();
				
				String ename=TESTDATA.get(row).get(0);
				Thread.sleep(2000);
				
				//WebElement ename1=wait.until(ExpectedConditions.textToBePresentInElement(element, text));
				//fullname.sendKeys(ename);
				JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("arguments[0].value='" + ename + "' ;", fullname);
				String ufname=fullname.getAttribute("value");
				System.out.println();
				if(!fullname.getAttribute("value").contentEquals(ename)){
				wait.withMessage("Name is not exact as in Excel file");
				Thread.sleep(1000);}
				else{
				
				System.out.println("Entered Name :-"+ufname);
				log.info("Entered Name :-"+ufname);
				}
				
				
				WebElement city = driver.findElement(By.xpath("//*[@id='divRefContainer_settingPanel']/div/table/tbody/tr/td[1]/table/tbody/tr[16]/td[3]/input[@name='city']"));
				city.clear();
				city.click();
				Thread.sleep(500);
				String ecity=TESTDATA.get(row).get(1);
				Thread.sleep(1000);
				//For NULL Entries
				if(ecity.equalsIgnoreCase("NULL"))
					city.sendKeys("");
				else
				city.sendKeys(ecity);
				Thread.sleep(1000);
				
				System.out.println("Entered City is :- " + ecity);
				
				
				WebElement state = driver.findElement(By.xpath("//*[@id='divRefContainer_settingPanel']/div/table/tbody/tr/td[1]/table/tbody/tr[17]/td[3]/input[@name='state']"));
				state.clear();
				state.click();
				Thread.sleep(500);
				String estate=TESTDATA.get(row).get(2);
				Thread.sleep(1000);
				if(estate.equalsIgnoreCase("NULL"))
					state.sendKeys("");
				else
					state.sendKeys(estate);
				System.out.println("Entered State is :- " + estate);
				Thread.sleep(1000);
				
				WebElement country = driver.findElement(By.xpath("//*[@id='divRefContainer_settingPanel']/div/table/tbody/tr/td[1]/table/tbody/tr[18]/td[3]/select"));
				Select countryselect = new Select(country);
				String ecountry = TESTDATA.get(row).get(3);
				Thread.sleep(1000);
				if(ecountry.equalsIgnoreCase("NULL"))
				{					
					countryselect.selectByVisibleText("Select One");
				
				}else			
				countryselect.selectByValue(ecountry);
				System.out.println("Country Selected is :- " + ecountry);
				Thread.sleep(1000);

				WebElement birthcountry = driver.findElement(By.xpath("//*[@id='birthCountry']"));
				Select bcountry = new Select(birthcountry);
				String ebc = TESTDATA.get(row).get(4);
				Thread.sleep(1000);
				if(ebc.equalsIgnoreCase("NULL"))
					bcountry.selectByVisibleText("Select One");
				else
				bcountry.selectByValue(ebc);
				System.out.println("Birth Country Selected is :- " + ebc);
				Thread.sleep(1000);

				WebElement dob = driver.findElement(By.xpath("//*[@id='dtBirthTxt']"));
				dob.clear();
				dob.click();
				Thread.sleep(1000);
				String edob = TESTDATA.get(row).get(5);
				Thread.sleep(1000);
				if(edob.equalsIgnoreCase("NULL"))
					dob.sendKeys("");
				else
					jse.executeScript("arguments[0].value='" + edob + "' ;", dob);
				//dob.sendKeys(edob);
				Thread.sleep(1000);
				System.out.println("Entered Date of Birth is :- " + edob);
				System.out.println();
				
				// to uncheck the tickbox
				Thread.sleep(1000);;
				WebElement uncheck = driver.findElement(By.cssSelector("#alertTr > td:nth-child(2) > input[type='checkbox']"));
				
				jse.executeScript("arguments[0].click();", uncheck);
				
				//uncheck.click();
				Thread.sleep(3000);

				// to submit
				WebElement submit = driver.findElement(By.xpath("//*[@id='editButtonTd' and @title='Edit List']"));
				//Thread.sleep(3000);
				submit.click();
				Thread.sleep(4000);
				
				
				
				try {
					
					
					if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='0_tr']"))).isDisplayed())
					{
						// System.out.println("This is not displayed");
						System.out.println("**********Alert is displayed**********");
					//	log.info("ALert is displayed");
						System.out.println();
						WebElement alertname = driver.findElement(By.xpath("//*[@id='0_tr']/td[7]/span[@class='clsGridCellFont pluginDataCell']"));
						if (alertname.isDisplayed()) {
							String Alert = alertname.getText();
							System.out.println("Retrieved Name is :- " + Alert);
							
							String actualname = TESTDATA.get(row).get(6);
							if (Alert.equalsIgnoreCase(actualname)) 
							{
								System.out.println("Expected Name " + "--"+Alert +"--"+ " matches with the Actual Name " + actualname);
								System.out.println();
								/*System.out.println("It is Direct Hit");
								System.out.println();*/
															
							} else
								System.out.println("Name is not matching");
						}
						
					}
										
					}
					
				
				catch(Exception e)
				{
					flag=true;
					System.out.println(e);
					System.out.println("*********There is no Alert*********");
					//log.info("There is no alert");
					System.out.println();
				}
				// End Code here 
				
				if(!flag)
				{
					
					TESTDATA.get(row).add("HIT");
					TESTDATA.get(row).add("Data imported");
					System.out.println("Updated Value with HIT is :- "+rowData);
					System.out.println();
					String updatevalue=rowData.get(7);
					//System.out.println("8th column is "+updatevalue);
					
					String stringdata=rowData.toString();
					BW.write(stringdata+" ----  "+updatevalue);
					BW.newLine();
					
				}
				else
				{
					TESTDATA.get(row).add("NO HIT");;//.add("HIt");
					TESTDATA.get(row).add("Data Imported");
					System.out.println("Updated Value with NO HIT is :- "+rowData);
					String updatevalue=TESTDATA.get(row).get(7);
					//System.out.println("8th column is "+updatevalue);
					String stringdata=rowData.toString();
					BW.write(stringdata+" ----  "+updatevalue);
					BW.newLine();
			
				}
			System.out.println((row+1)+" th row is finished ");
			
			log.info((row+1)+" th row is finished ");
			
			//System.out.println("The page is on Hold to take Screen Shot");
			
			JavascriptExecutor js = ((JavascriptExecutor) driver);
			js.executeScript("window.scrollTo(0, -document.body.scrollHeight);");
			
			
			String screenshotfile="C:/Automation Testing Set up/Nice_Actimize/Screenshots/";
			System.out.println("Kindly find the Screenshot at this location "+screenshotfile);
			log.trace("Kindly find the Screenshot at this location "+screenshotfile);
			Date  d=new Date();
			DateFormat df=new SimpleDateFormat("ddMMMYYYY-hh-mm-ss aa");
		
		
			String obj=screenshotfile+df.format(d)+"_"+row+"_Iteration"+"_Screen.png";
		
			//System.out.println("the wait is over "+obj);
			Thread.sleep(2000);
			takeSnapShot(driver,obj );
			System.out.println("//-------Screenshot taken-------//");
			log.info("Screenshot taken");
			System.out.println();
			
			}//row for
			System.out.println("Test Data after Complete Cycle is :- "+TESTDATA+"\n");
			BW.flush();
			BW.close();
			
		} //try
		
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		finally{
			System.out.println("Write is complted in file ");
			log.info("File write is complete");
			System.out.println();
			
			BW.close();
			FW.close();
		}
		
		System.out.println("*********END********");
		log.info("This is end of process");
		System.out.println("You can check the logs and Screenshots now");
		log.info("You can check the logs and Screenshots now");
		driver.close();
		driver.quit();

	}
}
