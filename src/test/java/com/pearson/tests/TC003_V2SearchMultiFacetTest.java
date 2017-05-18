package com.pearson.tests;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.pearson.framework.report.LoggerUtil;
import com.pearson.pageobjects.V2SearchMultiFacetRequest;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.ReportHelper;
import com.relevantcodes.extentreports.LogStatus;
/**
 * 
 * Multi Facet V2Search API
 * 
 *Validating the wildcard ,simpleterm, logical ,exact pharse,Specific field  & combinational logic search term for get & post method
 */


// V2 Search Test cases
public class TC003_V2SearchMultiFacetTest extends BaseTest {
	LinkedHashMap<String, LinkedHashMap<String, String>> hashMap = null;
	String TestDescription = Constants.emptyString;
	String methodName = Constants.emptyString;
	LinkedHashMap<String, String> data_Search = new LinkedHashMap<String, String>();

	@Test(dataProvider = "dp")
	public void logicalOperator_V2SearchMF_POST(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchMultiFacetRequest v2SearchMultiFacetRequest = new V2SearchMultiFacetRequest(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchMultiFacetRequest.postLogicalOperator_MF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void singleSpecificFieldsLogic_V2SearchMF_POST(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchMultiFacetRequest v2SearchMultiFacetRequest = new V2SearchMultiFacetRequest(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchMultiFacetRequest.postSpecificFielsAndLogicResponseValidate_MF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void multiSpecificFields_Wildcard_V2SearchMF_POST(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchMultiFacetRequest v2SearchMultiFacetRequest = new V2SearchMultiFacetRequest(data_Search);
				TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchMultiFacetRequest.postMultipleSpecificFieldWildCard_ResponseValidate_MF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void multiSpecificFields_Wildcard_V2SearchMF_GET(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchMultiFacetRequest v2SearchMultiFacetRequest = new V2SearchMultiFacetRequest(data_Search);
				TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchMultiFacetRequest.getMultipleSpecificFieldWildCard_ResponseValidate_MF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void multiSpecificFieldsLogic_V2SearchMF_POST(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchMultiFacetRequest v2SearchMultiFacetRequest = new V2SearchMultiFacetRequest(data_Search);
				TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchMultiFacetRequest.postMultiSpecificFieldLogic_SearchResponseValidate_MF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void singleSpecificFieldsLogic_V2SearchMF_GET(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchMultiFacetRequest v2SearchMultiFacetRequest = new V2SearchMultiFacetRequest(data_Search);
				TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchMultiFacetRequest.getSpecificFielsAndLogicResponseValidate_MF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void multiSpecificFieldsLogic_V2SearchMF_GET(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchMultiFacetRequest v2SearchMultiFacetRequest = new V2SearchMultiFacetRequest(data_Search);
				TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchMultiFacetRequest.getMultiSpecificFieldLogic_SearchResponseValidate_MF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void singleSpecificFieldsLogic_ExactPharse_V2SearchMF_POST(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchMultiFacetRequest v2SearchMultiFacetRequest = new V2SearchMultiFacetRequest(data_Search);
				TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchMultiFacetRequest.postSpecificFielsAndLogicResponseValidate_MF_ExactPharse(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void singleSpecificFieldsLogic_ExactPharse_V2SearchMF_GET(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchMultiFacetRequest v2SearchMultiFacetRequest = new V2SearchMultiFacetRequest(data_Search);
				TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchMultiFacetRequest.getSpecificFielsAndLogicResponseValidate_MF_ExactPharse(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void multiSpecificFieldsLogic_ExactPharse_V2SearchMF_GET(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchMultiFacetRequest v2SearchMultiFacetRequest = new V2SearchMultiFacetRequest(data_Search);
				TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchMultiFacetRequest.getMultipleSpecificFieldWildCard_ResponseValidate_exactPharse_MF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void multiSpecificFieldsWildCard_ExactPharse_V2SearchMF_POST(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchMultiFacetRequest v2SearchMultiFacetRequest = new V2SearchMultiFacetRequest(data_Search);
				TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchMultiFacetRequest.postMultipleSpecificFieldWildCard_ResponseValidate_exactPharse_MF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void logicalOperator_V2SearchMF_GET(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchMultiFacetRequest v2SearchMultiFacetRequest = new V2SearchMultiFacetRequest(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchMultiFacetRequest.getLogicalOperator_MF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
}