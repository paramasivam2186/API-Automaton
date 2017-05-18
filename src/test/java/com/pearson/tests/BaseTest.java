package com.pearson.tests;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.pearson.framework.core.APIBase;
import com.pearson.hipchat.HipChat;
import com.pearson.hipchat.hipchat_enum;
import com.pearson.regression.utilityHelpers.ExcelRead;
import com.pearson.regression.utilityHelpers.ReportHelper;
import com.pearson.util.ALMResultUpdater;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTest extends APIBase {
	private ReportHelper classReport;
	static Map<String, ArrayList<LinkedHashMap<String, String>>> data = null;

	@BeforeClass
	public void initParentRep(ITestContext ctx) {
		final String className = this.getClass().getSimpleName();
		classReport = new ReportHelper(className + ctx.getCurrentXmlTest().getName(), "Regression Test Set", "");
		// UMReporter.initParent(className, className, className);
	}

	@BeforeMethod
	public void setUp(Object[] testArgs, ITestContext ctx, Method method ) {
		final String testName = method.getName();
		final String suiteName = ctx.getCurrentXmlTest().getSuite().getName();
		// UMReporter.initParent(suiteName, suiteName, suiteName);
		
		ReportHelper.initTest(testName, suiteName);
		ReportHelper.assignCatogory("API");
	
	}

	@AfterClass
	public void endParentRepo(ITestContext ctx) {
		final String suiteName = ctx.getCurrentXmlTest().getSuite().getName();
		classReport.endParent(suiteName);

	}

	@AfterMethod
	public void write(ITestResult result, ITestContext ctx) throws Exception {
		classReport.appendParent();
		try {

			if ((ctx.getCurrentXmlTest().getParameter("Testcase_Name").trim().isEmpty()) == true) {
				System.out.println("TC# ID is empty in excel");
			}

			else {
				final int duration = (int) (result.getEndMillis() - result.getStartMillis());
				final String methodName = result.getName();
				if (result.getStatus() == ITestResult.SUCCESS) {
					ALMResultUpdater.updateStatus(ctx.getCurrentXmlTest().getParameter("Testcase_Name"), "Passed",
							duration, ctx.getCurrentXmlTest().getParameter("Testcase_ID"));
				} else if (result.getStatus() == ITestResult.FAILURE) {
					ALMResultUpdater.updateStatus(ctx.getCurrentXmlTest().getParameter("Testcase_Name"), "Failed",
							duration, ctx.getCurrentXmlTest().getParameter("Testcase_ID"));
				} else {
					ALMResultUpdater.updateStatus(methodName, "No Run", duration, " ");
				}
			}

		} catch (Exception e) {

		}
	}

	@BeforeSuite
	public void getTestData(ITestContext test) {
		ReportHelper.initReport(test.getSuite().getName());
		// String className=this.getClass().getSimpleName();
		// System.out.println("current
		// ClassName+"+this.getClass().getSimpleName());
		try {
			data = ExcelRead.getData();
			// System.out.println("Total Data" + data);
		} catch (Exception e) {
			// System.out.println(e.getMessage());
		}

	}

	@AfterSuite(alwaysRun = true)
	public void poststeps(ITestContext ctx) {
		String suiteName = ctx.getCurrentXmlTest().getSuite().getName();
		System.out.println("Executing Post-steps of the Test Suite");
		ReportHelper.endReport(suiteName);
		System.out.println(ReportHelper.passCountD);
		System.out.println(ReportHelper.failCountD);
		HipChat hipchat = new HipChat("Q8RSfFXVMTGa8pVQ4drHVa3xCLq4FhXBTwMYXXmC");
		hipchat.messageUser("CSG_Automation_Testing_Status: Pass:"+ReportHelper.passCountD+"Fail:"+ReportHelper.failCountD, true, hipchat_enum.TEXT);
	}

	@DataProvider(name = "dp")
	public Object[][] dp(Method m) throws Exception {

		ArrayList<LinkedHashMap<String, String>> data2 = new ArrayList<LinkedHashMap<String, String>>();
		// Object[][] objArray =new Object[1][1];
		// objArray[0][0]=data.get(m.getName());
		// System.out.println("Data input for
		// method"+objArray[0][0]);

		data2 = data.get(m.getName());
		final Object[][] objArray = new Object[data2.size()][1];
		for (int i = 0; i < data2.size(); i++) {
			objArray[i][0] = data2.get(i);
		// System.out.println(data2.get(i));
		}
		return objArray;

	}

}