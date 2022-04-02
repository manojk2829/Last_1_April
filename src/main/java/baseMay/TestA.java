package baseMay;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.LogStatus;

public class TestA extends BaseClass_May {
	
	@Test
	public void testA(){
		test=ext.startTest("TestA Started");
		openBrowser("chrome");
		navigate(pro.getProperty("url"));
		screenshot();
		clickBTN("sign_up_xpath");
		test.log(LogStatus.INFO,"Testing start with Reporting ..");
		wait(2);
/*		if(isElementPresent("popup_Close_xpath")==true){
			clickBTN("popup_Close_xpath");
		}*/
		
		input_type("UserEmail_name", pro.getProperty("userEmailID"));
		clickBTN("submit_btn_id");
		wait(2);
		screenshot();
	}
	
	@AfterMethod
	public void tearDOwn(){
	    test.log(LogStatus.PASS, "Done test method execution ... ");
		wait(5);
		//dr.quit();
		ext.endTest(test);
		ext.flush();
	}

}
