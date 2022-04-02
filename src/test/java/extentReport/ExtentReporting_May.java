package extentReport;

import java.util.Date;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

import baseMay.BaseClass_May;

public class ExtentReporting_May extends BaseClass_May{
	public static ExtentReports ext;
	
	public static ExtentReports reporting(){
		if(ext==null){
			Date d=new Date();
			String FN=d.toString().replace(":", "_").replace(" ", "_")+".html";
			ext=new ExtentReports("C:\\Manoj_Data\\report\\"+FN,true,DisplayOrder.NEWEST_FIRST);
			ext.addSystemInfo("QA System", "Manoj Kushwaha").
			addSystemInfo("Testing Environment", "10.67.175.30");
		}
		return ext;
	}

}
