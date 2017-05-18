package com.pearson.tests;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.pearson.framework.report.LoggerUtil;
import com.pearson.pageobjects.V2SearchRequest;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.ReportHelper;
import com.relevantcodes.extentreports.LogStatus;

/**
 * 
 * Normal V2Search API
 * 
 *Validating the wildcard ,simpleterm, logical ,exact pharse,Specific field  & combinational logic search term for get & post method
 */


// V2 Search Test cases
public class TC003_V2SearchTest extends BaseTest {
	LinkedHashMap<String, LinkedHashMap<String, String>> hashMap = null;
	String TestDescription = Constants.emptyString;
	String methodName = Constants.emptyString;
	LinkedHashMap<String, String> data_Search = new LinkedHashMap<String, String>();

	@Test(dataProvider = "dp")
	public void normal_and_wildCard_V2Search_GET(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchRequest v2SearchRequest = new V2SearchRequest(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchRequest.getSingleandMultipleTermSearch(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void normal_and_wildCard_V2Search_POST(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchRequest v2SearchRequest = new V2SearchRequest(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchRequest.postLogicalOperator(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void multiSpecificField_V2Search_GET(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchRequest v2SearchRequest = new V2SearchRequest(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchRequest.
			getMultipleSpecificFieldWildCard_ResponseValidate(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void multiSpecificField_V2Search_POST(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchRequest v2SearchRequest = new V2SearchRequest(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchRequest.postMultipleSpecificFieldWildCard_ResponseValidate(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void singleSpecificField_V2Search_GET(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchRequest v2SearchRequest = new V2SearchRequest(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchRequest.getSingleSpecificFieldsAndLogicResponseValidate(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void singleSpecificField_V2Search_POST(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchRequest v2SearchRequest = new V2SearchRequest(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchRequest.postSpecificFielsAndLogicResponseValidate(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	
	@Test(dataProvider = "dp")
	public void multiSpecificField_ExactPharse_V2Search_POST(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchRequest v2SearchRequest = new V2SearchRequest(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchRequest.postMultipleSpecificFieldWildCard_ResponseValidate_exactPharse(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void multiFieldWithComboLogic_V2Search_GET(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchRequest v2SearchRequest = new V2SearchRequest(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchRequest.getMultiFieldSearchWithCombLogic(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	
	@Test(dataProvider = "dp")
	public void logicalOperator_ExactPharse_V2Search_POST(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchRequest v2SearchRequest = new V2SearchRequest(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchRequest.postLogicalOperator_ExactPharse(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	
	@Test(dataProvider = "dp")
	public void logicalOperator_ExactPharse_V2Search_GET(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchRequest v2SearchRequest = new V2SearchRequest(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchRequest.getLogicalOperator_ExactPharse(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void multiSpecificFieldLogic_V2Search_POST(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchRequest v2SearchRequest = new V2SearchRequest(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchRequest.postMultiSpecificFieldLogic_SearchResponseValidate(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void multiSpecificFieldLogic_V2Search_GET(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchRequest v2SearchRequest = new V2SearchRequest(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchRequest.getMultiSpecificFieldLogic_SearchResponseValidate(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
}
