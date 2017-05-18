package com.pearson.regression.utilityHelpers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ReportHelper {
	private static ExtentReports extentD;
	public static ExtentTest testParent;
	public static String reportPath;
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<Boolean> isPassed = new ThreadLocal<Boolean>();

	public static int passCountD = 0;
	public static int failCountD = 0;
	public static int passCountM = 0;
	public static int failCountM = 0;

	public static Map<String, ReportHelper> classReportMap = new HashMap<String, ReportHelper>();

	/**
	 * @param suiteName
	 */
	public static void initReport(String suiteName) {
//		String path = "./Automation_ReportFiles/";
//		String fileName = new SimpleDateFormat("yyyyMMddhhmm").format(new Date());
//		new File(path + "Report_" + fileName).mkdirs();
		// {
//		reportPath = path + "Report_" + fileName;
		// System.out.println(reportPath);
		// System.out.println("Into Init Report");
		@SuppressWarnings("unused")
		String css = ".test-body .categoryAPI {background-color: #22b5e1; border-radius: 2px; color: #fff; font-size: 12px; margin-right: 3px; padding: 2px 4px; font-family: Roboto, Nunito, 'Source Sans Pro', Arial;font-weight: 400;line-height: 1.5;}"
				+ ".test-body .categoryAPI1 {background-color: #22b5e1; border-radius: 2px; color: #fff; font-size: 12px; margin-right: 3px; padding: 2px 4px; font-family: Roboto, Nunito, 'Source Sans Pro', Arial;font-weight: 400;line-height: 1.5;}"
				+ ".test-body .categoryAPI2 {background-color: #22b5e1; border-radius: 2px; color: #fff; font-size: 12px; margin-right: 3px; padding: 2px 4px; font-family: Roboto, Nunito, 'Source Sans Pro', Arial;font-weight: 400;line-height: 1.5;}";

//		extentD = new ExtentReports(reportPath + "/CSG_Regression_Execution_Report.html", true);
		extentD = new ExtentReports("./" + "/CSG_Regression_Execution_Report.html", true);

	}

	// }
	/**
	 * @param testName
	 * @param description
	 * @param suiteName
	 */
	public ReportHelper(String testName, String description, String suiteName) {
		initParent(testName, description, suiteName);
	}

	/**
	 * @param logStatus
	 * @param stepName
	 * @param details
	 */
	public static void log(LogStatus logStatus, String stepName, String details) {
		// System.out.println("++++++++++++++++++++++++++++++++++++++++++++" +
		// stepName);
		// test.get().log(logStatus, stepName);
		@SuppressWarnings("unused")
		String path = "";
		switch (logStatus) {
		case PASS:
			// path = WrapperMethods.captureScreen();
			test.get().log(logStatus, stepName, details);
			break;
		case FAIL:
			// path = WrapperMethods.captureScreen();
			test.get().log(logStatus, stepName, details);
			isPassed.set(false);
			// Assert.fail();
			break;
		case INFO:
		case SKIP:
		case WARNING:
			test.get().log(logStatus, stepName, details);
			break;
		case ERROR:
		case FATAL:
		case UNKNOWN:
			test.get().log(logStatus, stepName, details);
			isPassed.set(false);
			System.out.println(isPassed.get());
			Assert.fail();
			break;
		default:
			break;
		}
		System.out.println(isPassed.get());
	}

	/**
	 * @param testName
	 * @param description
	 * @param suiteName
	 */
	public static void initParent(String testName, String description, String suiteName) {
		testParent = extentD.startTest(testName, description);

	}

	/**
	 * @param testName
	 * @param suiteName
	 */
	public static void initTest(String testName, String suiteName) {
		String browser = "API";// ConfigProvider.getConfig("Browser").toUpperCase();
		isPassed.set(true);
		testName = testName + " <span class='categoryAPI'>" + browser + "</span>";
		test.set(getextent().startTest(testName));

	}

	/**
	 * @param Catogory
	 */
	public static void assignCatogory(String Catogory) {
		test.get().assignCategory(Catogory);
	}

	/**
	 * @param suiteName
	 */
	public void endParent(String suiteName) {

		extentD.endTest(testParent);

	}

	/**
	 * @param suiteName
	 */
	public static void endReport(String suiteName) {
		extentD.flush();

	}

	/**
	 * @return
	 */
	private static ExtentReports getextent() {
		return extentD;
	}

	
	public void appendParent() {
		testParent.appendChild(test.get());
		if (isPassed.get()) {
			passCountD = passCountD + 1;
		} else {
			failCountD = failCountD + 1;
		}
		test.remove();

		isPassed.remove();

	}

}
