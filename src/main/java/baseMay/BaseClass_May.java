package baseMay;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import extentReport.ExtentReporting_May;

public class BaseClass_May {
	public Properties pro;
	public Logger log=LogManager.getLogger(BaseClass_May.class);
	public ExtentReports ext=ExtentReporting_May.reporting();
	public ExtentTest test;
	public WebDriver dr;
	
	
	public BaseClass_May(){
		init();
		PropertyConfigurator.configure(pro.getProperty("Log_File_Path"));
		
	}
	
	public void init(){
		pro=new Properties();
		try{
			FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\baseMay\\or.properties");
		    pro.load(fis);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		System.out.println("Properties File ininsialized .... ");
		System.out.println(pro.getProperty("url"));
	}
	
	public void openBrowser(String broName){
		if(broName.equalsIgnoreCase("chrome")){
			ChromeOptions op=new ChromeOptions();
			op.addArguments("start-maximized");
			op.addArguments("disable-infobars");
			dr=new ChromeDriver(op);
			
		}else{
			dr=new FirefoxDriver();
			dr.manage().window().maximize();
		}
		dr.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		test.log(LogStatus.INFO,"Browser Open "+ pro.getProperty("Browser"));
		log.info("Broser open -- "+pro.getProperty("Browser"));
	}
	
	public void wait(int s){
		try{
			Thread.sleep(1000*s);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		test.log(LogStatus.INFO,"Wait for "+ s + " second.");
		log.info("Wait for "+ s + " second.");
	}
	
	public void navigate(String url){
		dr.navigate().to(url);
		test.log(LogStatus.INFO, "URL inserted in browser .. " + url);
	}
	
	public void clickBTN(String locater){
		getElement(locater).click();
		test.log(LogStatus.INFO, "Find element and Click on it.." + pro.getProperty(locater));
	}
	
	public void input_Enter_Click(String locater){
		getElement(locater).sendKeys(Keys.ENTER);
		test.log(LogStatus.INFO, "Enter text and click on Enter button .. " + pro.getProperty(locater));
	}
	
	public void input_type(String locater,String data){
		getElement(locater).sendKeys(data);
		test.log(LogStatus.INFO, "Enter text inside textbox .. " + pro.getProperty(locater));
	}
	
	public void screenshot(){
		Date d=new Date();
		String FileName=d.toString().replace(" ", "_").replace(":", "_")+".jpg";
		File src=((TakesScreenshot)dr).getScreenshotAs(OutputType.FILE);
		try{
			FileHandler.copy(src, new File(pro.getProperty("Screenshot_File_path")+FileName));
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		test.log(LogStatus.INFO, "Screenshot Taken -- > "
		+ test.addScreenCapture(pro.getProperty("Screenshot_File_path")+FileName));
	}
	
	public WebElement getElement(String loc){
		WebElement w=null;
		if(loc.endsWith("_id")){
			w=dr.findElement(By.id(pro.getProperty(loc)));
		}else if(loc.endsWith("_name")){
			w=dr.findElement(By.name(pro.getProperty(loc)));
		}else if(loc.endsWith("_xpath")){
			w=dr.findElement(By.xpath(pro.getProperty(loc)));
		}else{
			System.out.println("Locter not found on Page...");
			Failure_Reporting("Test Reporting ... ");
		}
		return w;
	}
	
	public void Failure_Reporting(String msg){
		screenshot();
		test.log(LogStatus.FAIL, "Failed Reporting");
	}
	
	public boolean isElementPresent(String loc){
		List<WebElement> we=null;
		if(loc.endsWith("_id")){
			we=dr.findElements(By.id(pro.getProperty(loc)));
		}else if(loc.endsWith("_name")){
			we=dr.findElements(By.name(pro.getProperty(loc)));
		}else if(loc.endsWith("_xpath")){
			we=dr.findElements(By.xpath(pro.getProperty(loc)));
		}else{
			System.out.println("Element not presnt on Page...");
			Failure_Reporting("Test Reporting ... ");
		}
		if(we.size()==0){
			return false;
		}else {
			return true;
		}
	}
	
	
	
	
	
	
}
