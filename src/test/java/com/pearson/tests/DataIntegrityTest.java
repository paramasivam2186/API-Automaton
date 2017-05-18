package com.pearson.tests;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import com.pearson.framework.report.LoggerUtil;
import com.pearson.pageobjects.V2SearchDataIntegrityTestRequest;
import com.pearson.regression.utilityHelpers.Constants;

public class DataIntegrityTest extends BaseTest {
	LinkedHashMap<String, LinkedHashMap<String, String>> hashMap = null;
	String TestDescription = Constants.emptyString;
	String methodName = Constants.emptyString;
	LinkedHashMap<String, String> data_Search = new LinkedHashMap<String, String>();

	@Test(dataProvider = "dp")
	public void dataIntegrityCheck_V2Search(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchDataIntegrityTestRequest v2SearchDataIntegrityTestRequest = new V2SearchDataIntegrityTestRequest(
					data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchDataIntegrityTestRequest.datintegrityValidation(data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void dataIntegrityCheck_V2Search_UsingJSONPath(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchDataIntegrityTestRequest v2SearchDataIntegrityTestRequest = new V2SearchDataIntegrityTestRequest(
					data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchDataIntegrityTestRequest.datintegrityValidation_JsonPath(data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Collection_Staging(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchDataIntegrityTestRequest v2SearchDataIntegrityTestRequest = new V2SearchDataIntegrityTestRequest(
					data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchDataIntegrityTestRequest.collection(data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void exchange_Disp(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchDataIntegrityTestRequest v2SearchDataIntegrityTestRequest = new V2SearchDataIntegrityTestRequest(
					data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchDataIntegrityTestRequest.exchange_Disp(data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
}