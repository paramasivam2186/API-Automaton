package com.pearson.tests;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.pearson.pageobjects.GlossaryControllerRequest;
import com.pearson.pageobjects.NavigationController;
import com.pearson.pageobjects.Regression_eTextPO;
import com.pearson.pageobjects.searchController;
import com.pearson.regression.utilityHelpers.APIHelper;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.RESTServiceBase;
import com.pearson.regression.utilityHelpers.ReportHelper;
import com.pearson.regression.utilityHelpers.ResponseValidator;
import com.pearson.regression.utilityHelpers.Template;
import com.relevantcodes.extentreports.LogStatus;

import net.minidev.json.JSONObject;


public class Regression_eText extends BaseTest {

	private final Regression_eTextPO eTextPO =new Regression_eTextPO();
	SoftAssert soft = new SoftAssert();
	private static RESTServiceBase webCredentials_rest = new RESTServiceBase();
	
	/**
	 * Method to verify response of a CSG Search API POST call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	
	@Test(dataProvider = "dp")
	public void csgSearchContentValidResponsePost(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String reqBody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, reqBody);
			APIHelper.validaStatusCode(response, data_Search);
			System.out.println("Response is " + response.asString());
			
			final String fieldType = APIHelper.retriveValuefromJson("$..type", reqBody);			

			final searchController search = new searchController(data_Search);

			if (fieldType.toLowerCase().contains("annotation"))
			{
				final Boolean SearchValidation = search.jsonPathvalidateKeyValidation(response);
				if (SearchValidation) {	soft.assertTrue(SearchValidation);} else {soft.assertTrue(SearchValidation);}
			}
			else if(fieldType.toLowerCase().contains("content"))
			{
				final Boolean SearchValidation = search.jsonPathvalidateKeyValidationTypeContent(response);
				if (SearchValidation) {	soft.assertTrue(SearchValidation);} else {soft.assertTrue(SearchValidation);}
			}

			final Boolean statusJsonPath = search.jsonPathvalidateSingleWordHit(response, data_Search, reqBody);
			if (statusJsonPath) {soft.assertTrue(statusJsonPath);} else {soft.assertTrue(statusJsonPath);}

			final Boolean statusemTag = search.emTagValidation(response, data_Search, reqBody);

			if (statusemTag) {soft.assertTrue(statusemTag);} else {soft.assertTrue(statusemTag);}

			LoggerUtil.log(m.getName(), m.getName() + " Validation", "SubStep", null);
			
			// Validating the JsonSchema file with Json Response
			final String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

			soft.assertAll();

		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}	

	/**
	 * Method to verify Valid response of a CM Search API POST call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	
	@Test(dataProvider = "dp")
	public void cmSearchContentValidResponsePost(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());
			
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String reqBody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, reqBody);
			APIHelper.validaStatusCode(response, data_Search);
			
			final String fieldType = APIHelper.retriveValuefromJson("$..type", reqBody);
			final searchController search = new searchController(data_Search);

			if (fieldType.toLowerCase().contains("annotation"))
			{
				final Boolean SearchValidation = search.jsonPathvalidateKeyValidation(response);
				if (SearchValidation) {	soft.assertTrue(SearchValidation);} else {soft.assertTrue(SearchValidation);}
			}
			else if(fieldType.toLowerCase().contains("content"))
			{
				final Boolean SearchValidation = search.jsonPathvalidateKeyValidationTypeContent(response);
				if (SearchValidation) {	soft.assertTrue(SearchValidation);} else {soft.assertTrue(SearchValidation);}
				System.out.println("First softAssert "+ SearchValidation);
			}

			final Boolean statusJsonPath = search.jsonPathvalidateSingleWordHit(response, data_Search, reqBody);
			if (statusJsonPath) {soft.assertTrue(statusJsonPath);} else {soft.assertTrue(statusJsonPath);}

			final Boolean statusemTag = search.emTagValidation(response, data_Search, reqBody);
			if (statusemTag) {soft.assertTrue(statusemTag);} else {soft.assertTrue(statusemTag);}

			LoggerUtil.log(m.getName(), m.getName() + " Validation", "SubStep", null);
			
			// Validating the JsonSchema file with Json Response
			final String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

			soft.assertAll();

		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * Method to verify invalid response of a CSG Search API POST call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	
	@Test(dataProvider = "dp")
	public void csgSearchContentInvalidResponsePost(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, responseBody);
			APIHelper.validaStatusCode(response, data_Search);
			System.out.println("Response is " + response.asString());
			// 2. Validating the Invalid response
			final String expInvalidResponse = data_Search.get(Constants.expJsonRes).trim();
			if (!expInvalidResponse.equals(Constants.emptyString)) {
				ResponseValidator.compareJsonResponse(response, expInvalidResponse);
			}
			
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * Method to verify invalid response of a CM Search API POST call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	
	@Test(dataProvider = "dp")
	public void cmSearchContentInvalidResponsePost(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, responseBody);
			APIHelper.validaStatusCode(response, data_Search);
			System.out.println("Response is " + response.asString());
			// 2. Validating the Invalid response
			final String expInvalidResponse = data_Search.get(Constants.expJsonRes).trim();
			if (!expInvalidResponse.equals(Constants.emptyString)) {
				ResponseValidator.compareJsonResponse(response, expInvalidResponse);
			}
			
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * Method to verify Valid response of a CSG Search API GET call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	
	@Test(dataProvider = "dp")
	public void csgSearchContentValidResponseGet(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());

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
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * Method to verify Valid response of a CM Search API GET call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void cmSearchContentValidResponseGet(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());

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
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * Method to verify invalid response of a CSG Search API GET call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void csgSearchContentInvalidResponseGet(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());
			
			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);

			APIHelper.validaStatusCode(response, data_Search);
			System.out.println("Response is " + response.asString());
			//System.out.println("********************" + response.getBody().asString());
			//final String expected = APIHelper.retriveValuefromJson("$..totalHits", response.getBody().asString());
			//Assert.assertEquals("0", expected);
			// 2. Validating the Invalid response
			final String expInvalidResponse = data_Search.get(Constants.expJsonRes).trim();
			if (!expInvalidResponse.equals(Constants.emptyString)) {
				ResponseValidator.compareJsonResponse(response, expInvalidResponse);
			}

		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * Method to verify invalid response of a CM Search API GET call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void cmSearchContentInvalidResponseGet(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());
			
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
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * Test to validate accent query for a CSG API POST request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void csgSearchContentAccentQueryPost(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String reqBody = APIHelper.GetrequestBody(data_Search);
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("Content-Type", "application/json");
			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParamAsString(headers, reqBody, URI);
			
			APIHelper.validaStatusCode(response, data_Search);			
			final String fieldType = APIHelper.retriveValuefromJson("$..type", reqBody);	
			final searchController search = new searchController(data_Search);
			if (fieldType.toLowerCase().contains("annotation"))
			{
				final Boolean SearchValidation = search.jsonPathvalidateKeyValidation(response);
				if (SearchValidation) {	soft.assertTrue(SearchValidation);} else {soft.assertTrue(SearchValidation);}
			}
			else if(fieldType.toLowerCase().contains("content"))
			{
				final Boolean SearchValidation = search.jsonPathvalidateKeyValidationTypeContent(response);
				if (SearchValidation) {	soft.assertTrue(SearchValidation);} else {soft.assertTrue(SearchValidation);}
			}

			final Boolean statusJsonPath = search.jsonPathvalidateSingleWordHit(response, data_Search, reqBody);
			if (statusJsonPath) {soft.assertTrue(statusJsonPath);} else {soft.assertTrue(statusJsonPath);}

			final Boolean statusemTag = search.emTagValidation(response, data_Search, reqBody);

			if (statusemTag) {soft.assertTrue(statusemTag);} else {soft.assertTrue(statusemTag);}

			LoggerUtil.log(m.getName(), m.getName() + " Validation", "SubStep", null);
			
			// Validating the JsonSchema file with Json Response
			final String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

			soft.assertAll();

		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}	
	
	/**
	 * Test to validate accent query for a CM API POST request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void cmSearchContentAccentQueryPost(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());
			
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String reqBody = APIHelper.GetrequestBody(data_Search);
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("Content-Type", "application/json");
			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParamAsString(headers, reqBody, URI);
			
			APIHelper.validaStatusCode(response, data_Search);
			System.out.println("Response is " + response.asString());
			
			final String fieldType = APIHelper.retriveValuefromJson("$..type", reqBody);
			final searchController search = new searchController(data_Search);

			if (fieldType.toLowerCase().contains("annotation"))
			{
				final Boolean SearchValidation = search.jsonPathvalidateKeyValidation(response);
				if (SearchValidation) {	soft.assertTrue(SearchValidation);} else {soft.assertTrue(SearchValidation);}
			}
			else if(fieldType.toLowerCase().contains("content"))
			{
				final Boolean SearchValidation = search.jsonPathvalidateKeyValidationTypeContent(response);
				if (SearchValidation) {	soft.assertTrue(SearchValidation);} else {soft.assertTrue(SearchValidation);}
				System.out.println("First softAssert "+ SearchValidation);
			}

			final Boolean statusJsonPath = search.jsonPathvalidateSingleWordHit(response, data_Search, reqBody);
			if (statusJsonPath) {soft.assertTrue(statusJsonPath);} else {soft.assertTrue(statusJsonPath);}

			final Boolean statusemTag = search.emTagValidation(response, data_Search, reqBody);
			if (statusemTag) {soft.assertTrue(statusemTag);} else {soft.assertTrue(statusemTag);}

			LoggerUtil.log(m.getName(), m.getName() + " Validation", "SubStep", null);
			
			// Validating the JsonSchema file with Json Response
			final String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

			soft.assertAll();

		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	//---------------------------------End of Search API Testcases-----------------------------//
	
	/**
	 * Test to validate GET Navigation API for valid index ID and type
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void Get_Navigation_With_ValidIndexID_and_type_01(Method m, LinkedHashMap<String, String> data_Search,
				ITestContext ctx) {
			try {

				ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
				ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
				ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());
				
				LoggerUtil.log(m.getName(), "", Constants.testStep, null);

				final String URI = APIHelper.URIgenerator_CSG(data_Search);

				final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
				APIHelper.validaStatusCode(response, data_Search);

				final String content = response.getBody().asString();
				final NavigationController navcontrol = new NavigationController(data_Search);
				navcontrol.ValidateMediaarrayjsonPath(response, data_Search, content);

				// Validating the JsonSchema file with Json Response
				final String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
				ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

			} catch (Exception e) {
				ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
				LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
				e.getMessage();
				e.printStackTrace();
				Assert.fail(e.getMessage(), e);
			}
		}
	
	/**
	 * Test to validate CM GET Navigation API for valid index ID and type
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CM_Get_Navigation_With_ValidIndexID_and_type_01(Method m, LinkedHashMap<String, String> data_Search,
				ITestContext ctx) {
			try {

				ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
				ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
				ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());
				
				LoggerUtil.log(m.getName(), "", Constants.testStep, null);

					final String URI = APIHelper.URIgenerator_CM(data_Search);

				final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
				APIHelper.validaStatusCode(response, data_Search);

				final String content = response.getBody().asString();
				final NavigationController navcontrol = new NavigationController(data_Search);
				navcontrol.ValidateMediaarrayjsonPath(response, data_Search, content);

				// Validating the JsonSchema file with Json Response
				final String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
				ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

			} catch (Exception e) {
				ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
				LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
				e.getMessage();
				e.printStackTrace();
				Assert.fail(e.getMessage(), e);
			}
		}
	
	/**
	 * Test to validate GET Navigation API for invalid index ID
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void Get_Navigation_With_InvalidIndexID_02(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {

		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());
			
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
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	 }
	
	/**
	 * Test to validate GET Navigation API with valid index ID and type
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CM_Get_Navigation_With_InvalidIndexID_02(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {

		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());
			
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
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	 }
	
	/**
	 * Test to validate GET Navigation CSG API without an index ID
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void Get_Navigation_Without_indexId(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());
			
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
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Test to validate GET Navigation CM API without an index ID
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CM_Get_Navigation_Without_indexId(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());
			
			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			// APIHelper.validaErrorMessage(response, data_Search);
			// 2. Validating the Invalid response
			final String expInvalidResponse = data_Search.get(Constants.expJsonRes).trim();
			if (!expInvalidResponse.equals(Constants.emptyString)) {
				ResponseValidator.compareJsonResponse(response, expInvalidResponse);
			}

		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * Test to validate GET Navigation CSG API for an invalid Hits start index
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CSG_Get_Navigation_With_Invalid_Hits_Start_Index_04(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());
			
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
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void Get_Navigation_With_Invalid_Hits_Start_Index_04(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());
			
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
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * Test to validate CM Navigation GET API for an invalid Hits start index
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CM_Get_Navigation_With_Invalid_Hits_Start_Index_04(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());
			
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
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * To validate CSG invalid scenarios in eText
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CSG_Get_Regression_eText_Invalid_Scenarios(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());
			
			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = eTextPO.api_getCallWithParameterforNavigation(data_Search, URI);

			APIHelper.validaStatusCode(response, data_Search);

			// 2. Validating the Invalid response
			final String expInvalidResponse = data_Search.get(Constants.expJsonRes).trim();
			if (!expInvalidResponse.equals(Constants.emptyString)) {
				eTextPO.compareJsonResponse(response, expInvalidResponse);
			}

		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * To validate CM invalid scenarios in eText
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CM_Get_Regression_eText_Invalid_Scenarios(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());
			
			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = eTextPO.api_getCallWithParameterforNavigation(data_Search, URI);


			APIHelper.validaStatusCode(response, data_Search);

			// 2. Validating the Invalid response
			final String expInvalidResponse = data_Search.get(Constants.expJsonRes).trim();
			if (!expInvalidResponse.equals(Constants.emptyString)) {
				eTextPO.compareJsonResponse(response, expInvalidResponse);
			}

		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * CM Search Filter
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CM_Search_Filter(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());
			
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String reqBody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, reqBody);
			APIHelper.validaStatusCode(response, data_Search);

			final String indexId = APIHelper.retriveValuefromJson("$..[0]", response.getBody().asString());
			
			final String api2ReqBody = data_Search.get("api2RequestBody").replace("^^", indexId).trim();
			final JSONObject json1 = Template.convertStringtoJsonObject(api2ReqBody);
			final Response response1 = webCredentials_rest.postCallWithBodyParam(json1, "https://dragonfly-qa.stg-openclass.com:443/pxereader-cm/api/cm/search");
			
			final searchController search = new searchController(data_Search);
			search.returnTotalHit(response1);
	
			// Validating the response schema
			final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
			if (!expSchemafileName.equals(Constants.emptyString)) {
				ResponseValidator.validateJsonSchema(response1, expSchemafileName);
			}
							
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * CSG Search Filter
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CSG_Search_Filter(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String reqBody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, reqBody);
			APIHelper.validaStatusCode(response, data_Search);

			final String indexId = APIHelper.retriveValuefromJson("$..[0]", response.getBody().asString());
			
			final String api2ReqBody = data_Search.get("api2RequestBody").replace("^^", indexId).trim();
			final JSONObject json1 = Template.convertStringtoJsonObject(api2ReqBody);
			final Response response1 = webCredentials_rest.postCallWithBodyParam(json1, "https://content-service-qa.stg-prsn.com:443/csg/api/cm/search");
			
			final searchController search = new searchController(data_Search);
			search.returnTotalHit(response1);
	
			// Validating the response schema
			final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
			if (!expSchemafileName.equals(Constants.emptyString)) {
				ResponseValidator.validateJsonSchema(response1, expSchemafileName);
			}
							
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * CM Meta data Search API
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CM_METADATA_SEARCH_API(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
			
			final String URI = APIHelper.URIgenerator_CM(data_Search);			
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			final Response response = webCredentials_rest.getCallWithParameters(parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final int actualcount = eTextPO.returnTotalHit(response);
			if (actualcount == 0) {
				ReportHelper.log(LogStatus.FAIL, "TotalHit Returned is Zero - Please verify the search text", new Integer(actualcount).toString());
			} else if (actualcount > 0) {
				ReportHelper.log(LogStatus.PASS, "TotalHit Returned are ", new Integer(actualcount).toString());
			}
			
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
			
		}
	}

	/**
	 * CSG Meta data Search API
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CSG_METADATA_SEARCH_API(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			final Response response = webCredentials_rest.getCallWithParameters(parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final int actualcount = eTextPO.returnTotalHit(response);
			if (actualcount == 0) {
				ReportHelper.log(LogStatus.FAIL, "TotalHit Returned is Zero - Please verify the search text", new Integer(actualcount).toString());
			} else if (actualcount > 0) {
				ReportHelper.log(LogStatus.PASS, "TotalHit Returned are ", new Integer(actualcount).toString());
			}
			
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * CSG Meta data search API for an invalid search text
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CSG_METADATA_SEARCH_API_with_Invalid_searchText(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
		
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			final Response response = webCredentials_rest.getCallWithParameters(parameters, URI);

			APIHelper.validaStatusCode(response, data_Search);		

			final int actual = eTextPO.returnTotalHit(response);
			final int expected = 0;
			if (actual == expected) {
				ReportHelper.log(LogStatus.PASS, "TotalHit Returned is Zero ", new Integer(actual).toString());
			} else {
				ReportHelper.log(LogStatus.FAIL, "TotalHit Returned are ", new Integer(actual).toString());
			}
			Assert.assertEquals(actual, expected,"Total Hit must be returned as 0");
			LoggerUtil.log("Total Hit must be zero for Invalid search Text","0", "SubStep", null);
			
			// 2. Validating the Invalid response
			final String expInvalidResponse = data_Search.get(Constants.expJsonRes).trim();
			if (!expInvalidResponse.equals(Constants.emptyString)) {
				ResponseValidator.compareJsonResponse(response, expInvalidResponse);
			}
			
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * CM Meta data search API for an invalid search text
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CM_METADATA_SEARCH_API_with_Invalid_searchText(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
			
			final String URI = APIHelper.URIgenerator_CM(data_Search);
		
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			final Response response = webCredentials_rest.getCallWithParameters(parameters, URI);

			APIHelper.validaStatusCode(response, data_Search);		

			final int actual = eTextPO.returnTotalHit(response);
			final int expected = 0;
			if (actual == expected) {
				ReportHelper.log(LogStatus.PASS, "TotalHit Returned is Zero ", new Integer(actual).toString());
			} else {
				ReportHelper.log(LogStatus.FAIL, "TotalHit Returned are ", new Integer(actual).toString());
			}
			Assert.assertEquals(actual, expected,"Total Hit must be returned as 0");
			LoggerUtil.log("Total Hit must be zero for Invalid search Text","0", "SubStep", null);
			
			// 2. Validating the Invalid response
			final String expInvalidResponse = data_Search.get(Constants.expJsonRes).trim();
			if (!expInvalidResponse.equals(Constants.emptyString)) {
				ResponseValidator.compareJsonResponse(response, expInvalidResponse);
			}
			
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * CM Meta data search filter API
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CM_METADATA_SEARCH_FILTER_API(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());
			
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			final Response response = webCredentials_rest.getCallWithParameters(parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);
			final List<String> actualcountasString = APIHelper.FeildToReturnasArray("$..[*]", response.getBody().asString());
			if (actualcountasString.isEmpty()) {
				ReportHelper.log(LogStatus.FAIL, "IndexID Returned is Zero - Please verify the search text", actualcountasString.toString());
			} else if (!actualcountasString.isEmpty()) {
				ReportHelper.log(LogStatus.PASS, "IndexIDs are Returned ", actualcountasString.toString());
				final String firstIndexIDFromResponse = APIHelper.retriveValuefromJson("$..[0]", response.getBody().asString());
				final String API2URLFromEXCEL = data_Search.get("api2RequestBody").trim().replace("^^", firstIndexIDFromResponse);
				final JSONObject json = Template.convertStringtoJsonObject(API2URLFromEXCEL);
				final String URIForAPI2 = URI.replace("/metadata/filter", "");
				final Response responsefromAPI2 = webCredentials_rest.postCallWithBodyParam(json, URIForAPI2);
				final String indexidValidation = APIHelper.retriveValuefromJson("$..totalHits", responsefromAPI2.getBody().asString());
				final int indexidAvilableCount  = Integer.parseInt(indexidValidation);
				if(indexidAvilableCount > 0){
					ReportHelper.log(LogStatus.PASS, "IndexID", "IndexID is Valid");
				}else{
					ReportHelper.log(LogStatus.FAIL, "IndexID", "IndexID is InValid");
				}
					
			}
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
			
		}
	}

	/**
	 * CSG Meta data search filter API
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CSG_METADATA_SEARCH_FILTER_API(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			final Response response = webCredentials_rest.getCallWithParameters(parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);
			final List<String> actualcountasString = APIHelper.FeildToReturnasArray("$..[*]", response.getBody().asString());
			if (actualcountasString.isEmpty()) {
				ReportHelper.log(LogStatus.FAIL, "IndexID Returned is Zero - Please verify the search text", actualcountasString.toString());
			} else if (!actualcountasString.isEmpty()) {
				ReportHelper.log(LogStatus.PASS, "IndexIDs are Returned ", actualcountasString.toString());
				final String firstIndexIDFromResponse = APIHelper.retriveValuefromJson("$..[0]", response.getBody().asString());
				final String API2URLFromEXCEL = data_Search.get("api2RequestBody").trim().replace("^^", firstIndexIDFromResponse);
				final JSONObject json = Template.convertStringtoJsonObject(API2URLFromEXCEL);
				final String URIForAPI2 = URI.replace("/metadata/filter", "");
				final Response responsefromAPI2 = webCredentials_rest.postCallWithBodyParam(json, URIForAPI2);
				final String indexidValidation = APIHelper.retriveValuefromJson("$..totalHits", responsefromAPI2.getBody().asString());
				final int indexidAvilableCount  = Integer.parseInt(indexidValidation);
				if(indexidAvilableCount > 0){
					ReportHelper.log(LogStatus.PASS, "IndexID", "IndexID is Valid");
				}else{
					ReportHelper.log(LogStatus.FAIL, "IndexID", "IndexID is InValid");
				}
					
			}
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
			
		}
	}

	/**
	 * CM Meta data search filter API with invalid search text
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CM_METADATA_SEARCH_FILTER_API_with_Invalid_searchText(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());
			
			final String URI = APIHelper.URIgenerator_CM(data_Search);
		
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			final Response response = webCredentials_rest.getCallWithParameters(parameters, URI);
			// 1. Validating the Status Code
			APIHelper.validaStatusCode(response, data_Search);		
			// 2. Validating the Invalid response
			final String expInvalidResponse = data_Search.get(Constants.expJsonRes).trim();
			if (!expInvalidResponse.equals(Constants.emptyString)) {
				ResponseValidator.compareJsonResponse(response, expInvalidResponse);
			}
			
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * Test to validate CSG Meta data search filter API with invalid search text
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CSG_METADATA_SEARCH_FILTER_API_with_Invalid_searchText(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
		
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			final Response response = webCredentials_rest.getCallWithParameters(parameters, URI);
			// 1. Validating the Status Code
			APIHelper.validaStatusCode(response, data_Search);		
			// 2. Validating the Invalid response
			final String expInvalidResponse = data_Search.get(Constants.expJsonRes).trim();
			if (!expInvalidResponse.equals(Constants.emptyString)) {
				ResponseValidator.compareJsonResponse(response, expInvalidResponse);
			}
			
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * Test to validate CM Navigation POST call with valid data 
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
    public void CM_post_Navigation_With_ValidData(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
           try {
                  ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
                  ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
                  ReportHelper.log(LogStatus.INFO, "TestcaseNumber :" + data_Search.get("Num").trim(),data_Search.get("TestCase").trim());
                  final String URI = APIHelper.URIgenerator_CM(data_Search);
                  final String reqBody = APIHelper.GetrequestBody(data_Search);
                  final Response response = APIHelper.postCallwithRequestBody(URI, reqBody);
                  APIHelper.validaStatusCode(response, data_Search);

                  final String content = response.getBody().asString();
                  String indexidValidation = APIHelper.retriveValuefromJson("$..indexId",reqBody);
                  if(indexidValidation.equals(content)){
                        ReportHelper.log(LogStatus.PASS, "IndexID Validation" , "IndexID Matched as Expected");
                  }else{
                        ReportHelper.log(LogStatus.FAIL, "IndexID Validation" , "IndexID Mismatched");
                  }

                  // Validating the JsonSchema file with Json Response
                  final String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
                  if(!jsonSchemaFileName.isEmpty()){
                  ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);}
           }

           catch (Exception e) {
                  LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
                  e.getMessage();
                  e.printStackTrace();
                  Assert.fail(e.getMessage(), e);

           }
    }      

	/**
	 * Test to validate CSG GET glossary content for a valid index ID
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CSG_Get_Glossary_Content_ValidIndexId_01(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final GlossaryControllerRequest glossory = new GlossaryControllerRequest(data_Search);
			final boolean result = glossory.jsonPathvalidateKeyValidation(response, data_Search);
			if(result){
				soft.assertTrue(result);
			}else{
				soft.assertTrue(result);
			}
		
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
	
	/**
	 * Test to validate CSG GET glossary content for a valid index ID, no key
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CSG_Get_Glossary_Content_ValidIndexId_01_noKeyValue(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final GlossaryControllerRequest glossory = new GlossaryControllerRequest(data_Search);
			final boolean result = glossory.jsonPathValidationWithNoKey(response, data_Search);
			if(result){
				soft.assertTrue(result);
			}else{
				soft.assertTrue(result);
			}		
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

	/**
	 * Test to validate CSG GET glossary content for a valid index ID and invalid glossary key
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CSG_Get_Glossary_Content_ValidIndexId_And_InvalidGlossaryKey_04(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final String expected = APIHelper.retriveValuefromJson("$..glossary", response.getBody().asString());
			Assert.assertEquals(null, expected);	
			
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
	
	/**
	 * Test to validate CSG GET glossary content for an invalid index ID
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CSG_Get_Glossary_Content_inValidIndexId(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final String expected = APIHelper.retriveValuefromJson("$..glossary", response.getBody().asString());
			Assert.assertEquals(null, expected);	
			
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
	
	/**
	 * Test to validate CSG GET glossary content with no index ID
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CSG_Get_Glossary_Content_noIndexId(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Test to validate CM GET glossary content for a valid index ID
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CM_Get_Glossary_Content_ValidIndexId_01(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final GlossaryControllerRequest glossory = new GlossaryControllerRequest(data_Search);
			final boolean result = glossory.jsonPathvalidateKeyValidation(response, data_Search);
			if(result){
				soft.assertTrue(result);
			}else{
				soft.assertTrue(result);
			}
		
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
	
	
	/**
	 * Test to validate CM GET glossary content with valid index ID and no key
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CM_Get_Glossary_Content_ValidIndexId_01_noKeyValue(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final GlossaryControllerRequest glossory = new GlossaryControllerRequest(data_Search);
			final boolean result = glossory.jsonPathValidationWithNoKey(response, data_Search);
			if(result){
				soft.assertTrue(result);
			}else{
				soft.assertTrue(result);
			}		
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

	/**
	 * Test to validate CM GET glossary content with valid index ID and invalid glossary key
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CM_Get_Glossary_Content_ValidIndexId_And_InvalidGlossaryKey_04(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final String expected = APIHelper.retriveValuefromJson("$..glossary", response.getBody().asString());
			Assert.assertEquals(null, expected);	
			
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
	
	/**
	 * Test to validate CM GET glossary content for invalid index ID
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CM_Get_Glossary_Content_inValidIndexId(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final String expected = APIHelper.retriveValuefromJson("$..glossary", response.getBody().asString());
			Assert.assertEquals(null, expected);	
			
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
	
	/**
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void Get_Glossary_Content_ValidIndexId_And_validGlossaryKey_02(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final GlossaryControllerRequest glossory = new GlossaryControllerRequest(data_Search);
			glossory.jsonPathvalidateKeyValidation(response, data_Search);

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * Test to validate CM GET glossary content with no index ID
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void CM_Get_Glossary_Content_noIndexId(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * To validate Multi-lingual glossary content GET request with valid index ID 
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void Get_multilingual_2_1_Glossary_Content_ValidIndexId_01(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			String URI = APIHelper.URIgenerator_CSG(data_Search);
			Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			// Validating the JsonSchema file with Json Response
			String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * To validate Multi-lingual glossary content GET request with valid index ID and valid Glossary key
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void Get_multilingual_Glossary_Content_ValidIndexId_And_validGlossaryKey_02(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			String URI = APIHelper.URIgenerator_CSG(data_Search);
			Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final GlossaryControllerRequest glossory = new GlossaryControllerRequest(data_Search);
			final boolean result = glossory.jsonPathvalidateKeyValidation(response, data_Search);
			if(result){
				soft.assertTrue(result);
			}else{
				soft.assertTrue(result);
			}

			// Validating the JsonSchema file with Json Response
			String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * To validate Multi-lingual glossary content GET request with valid language
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void Get_multilingual_2_1_Glossary_Valid_Lang_05(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			String URI = APIHelper.URIgenerator_CSG(data_Search);
			Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final GlossaryControllerRequest glossory = new GlossaryControllerRequest(data_Search);
			final boolean result = glossory.jsonPathvalidateKeyValidation(response, data_Search);
			if(result){
				soft.assertTrue(result);
			}else{
				soft.assertTrue(result);
			}

			// Validating the JsonSchema file with Json Response
			String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * To validate Multi-lingual glossary content CM GET request with valid index ID
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void Get_CM_multilingual_2_1_Glossary_Content_ValidIndexId_01(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			String URI = APIHelper.URIgenerator_CM(data_Search);
			Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			// Validating the JsonSchema file with Json Response
			String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * To validate Multi-lingual glossary content CM GET request with valid index ID and glossary key
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void Get_CM_multilingual_Glossary_Content_ValidIndexId_And_validGlossaryKey_02(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			String URI = APIHelper.URIgenerator_CM(data_Search);
			Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final GlossaryControllerRequest glossory = new GlossaryControllerRequest(data_Search);
			final boolean result = glossory.jsonPathvalidateKeyValidation(response, data_Search);
			if(result){
				soft.assertTrue(result);
			}else{
				soft.assertTrue(result);
			}

			// Validating the JsonSchema file with Json Response
			String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * To validate Multi-lingual glossary content CM GET request with valid language
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void Get_CM_multilingual_2_1_Glossary_Valid_Lang_05(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			String URI = APIHelper.URIgenerator_CM(data_Search);
			Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final GlossaryControllerRequest glossory = new GlossaryControllerRequest(data_Search);
			final boolean result = glossory.jsonPathvalidateKeyValidation(response, data_Search);
			if(result){
				soft.assertTrue(result);
			}else{
				soft.assertTrue(result);
			}

			// Validating the JsonSchema file with Json Response
			String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void Get_Traditional_Glossary_Content_ValidIndexId_07(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			String URI = APIHelper.URIgenerator_CSG(data_Search);
			Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			// Validating the JsonSchema file with Json Response
			String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * CM Search invalid exception
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void cmSearchInvalidException(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, responseBody);
			APIHelper.validaStatusCode(response, data_Search);
			System.out.println("Response is " + response.asString());
			
			if (response.getBody().asString().contains("Could not read JSON"))
				ReportHelper.log(LogStatus.PASS, "Exception",
						"Error Exception" + "There is something in-correct in the Json Body");
			else{
				ReportHelper.log(LogStatus.FAIL, "Exception",
						"Error Exception" + "There is something in-correct in the Json Body");
				Assert.fail();
			}
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * CSG Search invalid exception
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void csgSearchInvalidException(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber :"+data_Search.get("Num").trim(), data_Search.get("TestCase").trim());

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, responseBody);
			APIHelper.validaStatusCode(response, data_Search);
			System.out.println("Response is " + response.asString());
			
			if (response.getBody().asString().contains("Can not construct instance"))
				ReportHelper.log(LogStatus.PASS, "Exception",
						"Error Exception" + "There is something in-correct in the Json Body");
			else{
				ReportHelper.log(LogStatus.FAIL, "Exception",
						"Error Exception" + "There is something in-correct in the Json Body");
				Assert.fail();
			}
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Unexpected Error",e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
}
