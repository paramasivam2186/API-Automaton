package com.pearson.tests;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.pearson.framework.report.LoggerUtil;
import com.pearson.pageobjects.V2SearchSingleFacetRequest;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.ReportHelper;
import com.relevantcodes.extentreports.LogStatus;

/**
 * 
 * Single Facet V2Search API
 * 
 * Validating the wild card ,simple term, logical ,exact pharse ,Specific field &
 * combinational logic search term for get & post method
 */

// V2 Search Test cases
public class TC003_V2SearchSingleFacetTest extends BaseTest {

	LinkedHashMap<String, LinkedHashMap<String, String>> hashMap = null;
	String TestDescription = Constants.emptyString;
	String methodName = Constants.emptyString;
	LinkedHashMap<String, String> data_Search = new LinkedHashMap<String, String>();

	@Test(dataProvider = "dp")
	public void logicalOperator_V2SearchSingleFacet_POST(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchSingleFacetRequest v2SearchSingleFacetRequest = new V2SearchSingleFacetRequest(data_Search);
			TestDescription = m.getName();

			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchSingleFacetRequest.postLogicalOperator_SF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void multiSpecificField_V2SearchSingleFacet_POST_SchemaValidate(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchSingleFacetRequest v2SearchSingleFacetRequest = new V2SearchSingleFacetRequest(data_Search);
			TestDescription = m.getName();

			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchSingleFacetRequest
					.postMultiSpecificFieldLogic_SearchResponseValidate_SF_SchemaVerification(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void logicalOperator_V2SearchSingleFacet_GET(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchSingleFacetRequest v2SearchSingleFacetRequest = new V2SearchSingleFacetRequest(data_Search);
			TestDescription = m.getName();

			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchSingleFacetRequest.getLogicalOperator_SF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void specificFielsAndLogic_V2SearchSingleFacet_POST(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchSingleFacetRequest v2SearchSingleFacetRequest = new V2SearchSingleFacetRequest(data_Search);
			TestDescription = m.getName();

			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchSingleFacetRequest.postSpecificFielsAndLogicResponseValidate_SF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void multipleSpecificFieldWildCard_V2SearchSingleFacet_POST(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchSingleFacetRequest v2SearchSingleFacetRequest = new V2SearchSingleFacetRequest(data_Search);
			TestDescription = m.getName();

			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchSingleFacetRequest.postMultipleSpecificFieldWildCard_ResponseValidate_SF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void multiSpecificField_V2SearchSingleFacet_POST(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchSingleFacetRequest v2SearchSingleFacetRequest = new V2SearchSingleFacetRequest(data_Search);
			TestDescription = m.getName();

			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchSingleFacetRequest.postMultiSpecificFieldLogic_SearchResponseValidate_SF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void singleSpecificField_V2SearchSingleFacet_GET(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchSingleFacetRequest v2SearchSingleFacetRequest = new V2SearchSingleFacetRequest(data_Search);
			TestDescription = m.getName();

			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchSingleFacetRequest.getSpecificFielsAndLogicResponseValidate_SF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void multiSpecificField_V2SearchSingleFacet_GET(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchSingleFacetRequest v2SearchSingleFacetRequest = new V2SearchSingleFacetRequest(data_Search);
			TestDescription = m.getName();

			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchSingleFacetRequest.getMultiSpecificFieldLogic_SearchResponseValidate_SF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void multipleSpecificFieldWildCard_V2SearchSingleFacet_GET(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchSingleFacetRequest v2SearchSingleFacetRequest = new V2SearchSingleFacetRequest(data_Search);
			TestDescription = m.getName();

			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchSingleFacetRequest.getMultipleSpecificFieldWildCard_ResponseValidate_SF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void multipleSpecificFieldWildCard_exactPharse_V2SearchSingleFacet_GET(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchSingleFacetRequest v2SearchSingleFacetRequest = new V2SearchSingleFacetRequest(data_Search);
			TestDescription = m.getName();

			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchSingleFacetRequest.getMultipleSpecificFieldWildCard_ResponseValidate_exactPharse_SF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void multipleSpecificFieldWildCard_exactPharse_V2SearchSingleFacet_POST(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchSingleFacetRequest v2SearchSingleFacetRequest = new V2SearchSingleFacetRequest(data_Search);
			TestDescription = m.getName();

			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchSingleFacetRequest.postMultipleSpecificFieldWildCard_ResponseValidate_exactPharse_SF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void invalidValueinHeader(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchSingleFacetRequest v2SearchSingleFacetRequest = new V2SearchSingleFacetRequest(data_Search);
			TestDescription = m.getName();

			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchSingleFacetRequest.postInvalidValueinHeader(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

}