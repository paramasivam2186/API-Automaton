package com.pearson.tests;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.pearson.pageobjects.searchController;
import com.pearson.regression.utilityHelpers.APIHelper;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.RESTServiceBase;
import com.pearson.regression.utilityHelpers.ReportHelper;
import com.pearson.regression.utilityHelpers.ResponseValidator;
import com.pearson.regression.utilityHelpers.Template;
import com.relevantcodes.extentreports.LogStatus;

public class CSG641DefaultContentSearchBe extends BaseTest {
	private static RESTServiceBase webCredentials_rest = new RESTServiceBase();
	LinkedHashMap<String, LinkedHashMap<String, String>> hashMap = null;
	SoftAssert soft = new SoftAssert();

	@Test(dataProvider = "dp")
	public void SearchContent_with_valid_IndexId_POST(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String reqBody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, reqBody);
			APIHelper.validaStatusCode(response, data_Search);

			final searchController search = new searchController(data_Search);
			
			final Boolean statusJsonPath = search.jsonPathvalidateSingleWordHit(response, data_Search, reqBody);
			
			if(statusJsonPath){
				soft.assertTrue(statusJsonPath);
			}else{
				soft.assertTrue(statusJsonPath);
			}
			
			final Boolean statusemTag = search.emTagValidation(response, data_Search, reqBody);
			
			if(statusemTag){
				ReportHelper.log(LogStatus.FAIL, "emTag Validation", "emTag Validation is successful ");
				soft.assertTrue(statusemTag);
			}else{
				soft.assertTrue(statusemTag);
			}
			
			LoggerUtil.log(m.getName(), m.getName()+" Validation", "SubStep", null);
			// Validating the JsonSchema file with Json Response
			final String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);
			
			soft.assertAll();
						
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CM_SearchContent_with_valid_IndexId_POST(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {


			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String reqBody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, reqBody);
			APIHelper.validaStatusCode(response, data_Search);

			final searchController search = new searchController(data_Search);
			
			final Boolean statusJsonPath = search.jsonPathvalidateSingleWordHit(response, data_Search, reqBody);
			
			if(statusJsonPath){
				soft.assertTrue(statusJsonPath);
			}else{
				soft.assertTrue(statusJsonPath);
			}
			
			final Boolean statusemTag = search.emTagValidation(response, data_Search, reqBody);
			
			if(statusemTag){
				ReportHelper.log(LogStatus.FAIL, "emTag Validation", "emTag Validation is successful ");
				soft.assertTrue(statusemTag);
			}else{
				soft.assertTrue(statusemTag);
			}
			
			LoggerUtil.log(m.getName(), m.getName()+" Validation", "SubStep", null);
			// Validating the JsonSchema file with Json Response
			final String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);
			
			soft.assertAll();
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void SearchContentFilter_with_valid_IndexId_POST(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, responseBody);
			APIHelper.validaStatusCode(response, data_Search);		
			
			LoggerUtil.log(m.getName(), m.getName()+" Validation", "SubStep", null);
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
	public void SearchContent_with_valid_IndexId_GET(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			//Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("api_getCallWithParameter", parameters.toString(), Constants.subStep, null);
			final Response response = webCredentials_rest.getCallWithParameters(parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final searchController search = new searchController(data_Search);
			final Boolean statusJsonPath = search.jsonPathvalidateSingleWordHit(response, data_Search, parameters);
			
			if(statusJsonPath){
				soft.assertTrue(statusJsonPath);
			}else{
				soft.assertTrue(statusJsonPath);
			}

			final Boolean statusemTag = search.emTagValidation(response, data_Search, parameters);
			
			if(statusemTag){
				soft.assertTrue(statusemTag);
			}else{
				soft.assertTrue(statusemTag);
			}
			
			
			LoggerUtil.log(m.getName(), m.getName()+" Validation", "SubStep", null);
			// Validating the JsonSchema file with Json Response
			final String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);
			
			soft.assertAll();

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CM_SearchContent_with_valid_IndexId_GET(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {


			final String URI = APIHelper.URIgenerator_CM(data_Search);
			//Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("api_getCallWithParameter", parameters.toString(), Constants.subStep, null);
			final Response response = webCredentials_rest.getCallWithParameters(parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final searchController search = new searchController(data_Search);
			final Boolean statusJsonPath = search.jsonPathvalidateSingleWordHit(response, data_Search, parameters);
			
			if(statusJsonPath){
				soft.assertTrue(statusJsonPath);
			}else{
				soft.assertTrue(statusJsonPath);
			}

			final Boolean statusemTag = search.emTagValidation(response, data_Search, parameters);
			
			if(statusemTag){
				
				soft.assertTrue(statusemTag);
			}else{
				soft.assertTrue(statusemTag);
			}
			
			
			LoggerUtil.log(m.getName(), m.getName()+" Validation", "SubStep", null);
			// Validating the JsonSchema file with Json Response
			final String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);
			
			soft.assertAll();

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CSG_SEARCH_API_SearchContent_With_Invalid_IndexId(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);

			APIHelper.validaStatusCode(response, data_Search);

			final String expected = APIHelper.retriveValuefromJson("$..totalHits", response.getBody().asString());
			Assert.assertEquals("0", expected);
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
	public void CM_SEARCH_API_SearchContent_With_Invalid_IndexId(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);

			APIHelper.validaStatusCode(response, data_Search);

			final String expected = APIHelper.retriveValuefromJson("$..totalHits", response.getBody().asString());
			Assert.assertEquals("0", expected);
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
	public void CM_SEARCH_API_SearchContent_With_Null_IndexId(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

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
	public void CSG_SEARCH_API_SearchContent_With_Null_IndexId(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

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
	public void CSG_SEARCH_API_SearchContent_With_Invalid_IndexId_post(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, responseBody);
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
	public void CM_SEARCH_API_SearchContent_With_Invalid_IndexId_post(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, responseBody);
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
	public void CM_SEARCH_API_SearchContent_With_Null_IndexId_post(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, responseBody);
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
