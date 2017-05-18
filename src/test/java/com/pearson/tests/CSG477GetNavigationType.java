package com.pearson.tests;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.pearson.pageobjects.NavigationController;
import com.pearson.regression.utilityHelpers.APIHelper;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.ResponseValidator;

public class CSG477GetNavigationType extends BaseTest {

	LinkedHashMap<String, LinkedHashMap<String, String>> hashMap = null;
	String TestDescription = "";

	@Test(dataProvider = "dp")
	public void Get_Navigation_Types_With_ValidIndexID_01(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final String content = response.getBody().asString();
			final NavigationController navcontrol = new NavigationController(data_Search);
			navcontrol.navigationTypeimgValidation(response, data_Search, content);
			// Validating the JsonSchema file with Json Response
			final String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Get_Navigation_Types_With_InvalidIndexID_02(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			// 2. Validating the Invalid response
			final String expInvalidResponse = data_Search.get(Constants.expJsonRes).trim();
			if (!expInvalidResponse.equals(Constants.emptyString)) {
				ResponseValidator.compareJsonResponse(response, expInvalidResponse);
			}

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Get_Navigation_Types_Without_IndexID_03(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);
			APIHelper.validaErrorMessage(response, data_Search);

			// 2. Validating the Invalid response
			final String expInvalidResponse = data_Search.get(Constants.expJsonRes).trim();
			if (!expInvalidResponse.equals(Constants.emptyString)) {
				ResponseValidator.compareJsonResponse(response, expInvalidResponse);
			}

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void cm_Get_Navigation_Types_With_ValidIndexID_01(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final String content = response.getBody().asString();
			final NavigationController navcontrol = new NavigationController(data_Search);
			navcontrol.navigationTypeimgValidation(response, data_Search, content);
			// Validating the JsonSchema file with Json Response
			final String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void cm_Get_Navigation_Types_With_InvalidIndexID_02(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			// 2. Validating the Invalid response
			final String expInvalidResponse = data_Search.get(Constants.expJsonRes).trim();
			if (!expInvalidResponse.equals(Constants.emptyString)) {
				ResponseValidator.compareJsonResponse(response, expInvalidResponse);
			}

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void cm_Get_Navigation_Types_Without_IndexID_03(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			// 2. Validating the Invalid response
			final String expInvalidResponse = data_Search.get(Constants.expJsonRes).trim();
			if (!expInvalidResponse.equals(Constants.emptyString)) {
				ResponseValidator.compareJsonResponse(response, expInvalidResponse);
			}

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

}
