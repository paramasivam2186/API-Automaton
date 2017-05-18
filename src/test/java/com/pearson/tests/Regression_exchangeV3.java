package com.pearson.tests;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.pearson.pageobjects.Regression_exchangeV3PO;
import com.pearson.pageobjects.V2SearchSanity;
import com.pearson.regression.utilityHelpers.APIHelper;
import com.pearson.regression.utilityHelpers.CSGHelper;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.PropLoad;
import com.pearson.regression.utilityHelpers.RESTServiceBase;
import com.pearson.regression.utilityHelpers.ReportHelper;
import com.pearson.regression.utilityHelpers.ResponseJsonPath;
import com.pearson.regression.utilityHelpers.ResponseValidator;
import com.pearson.regression.utilityHelpers.Template;
import com.relevantcodes.extentreports.LogStatus;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class Regression_exchangeV3 extends BaseTest {

	private RESTServiceBase webCredentials_rest = new RESTServiceBase();
	String authtoken = "";
	String TestDescription = Constants.emptyString;
	private static String xAuthKey = PropLoad.getTestData("Auth_XAuthKey");
	private static String qs_String = PropLoad.getTestData("V2Search_queryString_Excel");
	private static String qs_reqField = PropLoad.getTestData("V2Search_qs_reqfield");

	/**
	 * To validate single or multi level sorting for POST request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void post_Single_OR_MultiLevel_Sorting(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());

			// Hp Alm update parameter are sent to After Method present in
			// BaseTest
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			// Create URI, request Body and Headers
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			// Send the POST or GET request
			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);

			// Validate status code using the expected status code present in
			// excel
			APIHelper.validaStatusCode(response, data_Search);

			// Sorting Validation
			final Regression_exchangeV3PO v2 = new Regression_exchangeV3PO(data_Search);

			// Single level validation
			Assert.assertTrue(v2.post_Sorting(response, requestBody));

			// Sort values from response is being validated
			Assert.assertTrue(v2.sortField(response, requestBody));

			// fields to return is present in response
			v2.fieldtoReturn_post(response, requestBody);
			
			// Validating the response schema
			final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
			if (!expSchemafileName.equals(Constants.emptyString)) {
				ResponseValidator.validateJsonSchema(response, expSchemafileName);
			}

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * To validate single or multi level Boosting for POST request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void post_Single_MultiLevel_Boosting(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
			authtoken = CSGHelper.generate_PI_AuthToken();

			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final String requestbody = data_Search.get(Constants.reqBody);
			final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);
			String queryString = Template.getFieldValfromJson(reqBodyJson, "queryString");

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);

			// Validate status code using the expected status code present in
			// excel
			APIHelper.validaStatusCode(response, data_Search);

			// Validating the response schema
			//final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
			//if (!expSchemafileName.equals(Constants.emptyString)) {
				//ResponseValidator.validateJsonSchema(response, expSchemafileName);
			//}

			// Validating the Response Jason Body
			Regression_exchangeV3PO v2 = new Regression_exchangeV3PO(data_Search);
			ArrayList<String> expectedQuery = v2.toFindbiggestNumberinQueryString(queryString);

			v2.jsonResponseValidationWithQueryString(response, expectedQuery);

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Negative validation for POST request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void post_sort_NegativeValidation(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());

			// Hp Alm update parameter are sent to After Method present in
			// BaseTest
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			// Create URI, request Body and Headers
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			// Send the POST or GET request
			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);

			// Validate status code using the expected status code present in
			// excel
			APIHelper.validaStatusCode(response, data_Search);
			Regression_exchangeV3PO v2 = new Regression_exchangeV3PO(data_Search);
			boolean success = v2.negativeSort(response, response.getStatusCode());

			if (success) {
				ReportHelper.log(LogStatus.INFO, "Sort with author fields-Negative validation",
						"Sort with author fields-Negative validation - PASSED");
			} else {
				ReportHelper.log(LogStatus.INFO, "Sort with author fields-Negative validation",
						"Sort with author fields-Negative validation - FAILED");
			}

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * To validate product offer status for POST request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void post_product_Offer_Status(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());

			// Update in ALM
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			// Setup the Request
			final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final String requestbody = data_Search.get(Constants.reqBody);
			final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);
			String queryString = Template.getFieldValfromJson(reqBodyJson, "queryString");

			// Send the Request
			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);

			// Validating the response code
			final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
			ResponseValidator.validateResponseCode(response, expStatus_code);
/*			// Validating the response schema
			final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
			if (!expSchemafileName.equals(Constants.emptyString)) {
				ResponseValidator.validateJsonSchema(response, expSchemafileName);
			}*/
			// API - 1 - for Getting the Product IDs.
			String josanPathfromQueryString = "$.searchResults..productId";
			String api2ReqBody = data_Search.get("api2RequestBody").trim();
			Regression_exchangeV3PO v2 = new Regression_exchangeV3PO(data_Search);
			String offerStatus = v2.getOfferStatus(queryString, api2ReqBody);

			v2.offerCodeStatusValidation(headers, reqURL, josanPathfromQueryString, response, api2ReqBody,
					webCredentials_rest, offerStatus);

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			ReportHelper.log(LogStatus.FAIL, "LogicalOperations_with_MultiLevel_Boosting_02", "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * To validate single or multi facet for POST request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void post_date_single_Multi_Facet(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());

			// Hp Alm update parameter are sent to After Method present in
			// BaseTest
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			// Create URI, request Body and Headers
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			// Send the POST or GET request
			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);

			// Validate status code using the expected status code present in
			// excel
			APIHelper.validaStatusCode(response, data_Search);
			Regression_exchangeV3PO v2 = new Regression_exchangeV3PO(data_Search);

			// Validating the response schema
			final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
			if (!expSchemafileName.equals(Constants.emptyString)) {
				ResponseValidator.validateJsonSchema(response, expSchemafileName);
			}

			// Single Facet Validation
			final JSONObject requestBodyJSON = APIHelper.GetrequestBodyasJson(data_Search);
			v2.multiLevelFacet_validation(response, requestBodyJSON);
			v2.date_Validation(response, requestBody, data_Search, headers, URI);

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * To validate single and multi facet for a POST request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void post_single_multi_Facet(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
			// Hp Alm update parameter are sent to After Method present in
			// BaseTest
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			// Create URI, request Body and Headers
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			// Send the POST or GET request
			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			Regression_exchangeV3PO v2 = new Regression_exchangeV3PO(data_Search);
			// Validate status code using the expected status code present in
			// excel
			APIHelper.validaStatusCode(response, data_Search);
		
			// Single Facet Validation
			final JSONObject requestBodyJSON = APIHelper.GetrequestBodyasJson(data_Search);
			v2.multiLevelFacet_validation(response, requestBodyJSON);
			
			System.out.println("************************" + response.getBody().asString());
			// Validating the response schema
			final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
			if (!expSchemafileName.equals(Constants.emptyString)) {
				ResponseValidator.validateJsonSchema(response, expSchemafileName);
			}

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * To validate single or multi facet for GET request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void get_single_multi_Facet(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
			// Hp Alm update parameter are sent to After Method present in
			// BaseTest
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			Regression_exchangeV3PO v2 = new Regression_exchangeV3PO(data_Search);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);

			// Validating the response schema
			final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
			if (!expSchemafileName.equals(Constants.emptyString)) {
				ResponseValidator.validateJsonSchema(response, expSchemafileName);
			}
			v2.multiLevelFacet_validation(response, parameters);

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Date validation for a POST request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void post_date_Validation(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());

			// Hp Alm update parameter are sent to After Method present in
			// BaseTest
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			// Create URI, request Body and Headers
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			// Send the POST or GET request
			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			Regression_exchangeV3PO v2 = new Regression_exchangeV3PO(data_Search);
			// Validate status code using the expected status code present in
			// excel
			APIHelper.validaStatusCode(response, data_Search);


			v2.date_Validation(response, requestBody, data_Search, headers, URI);
			
			// Validating the response schema
			final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
			if (!expSchemafileName.equals(Constants.emptyString)) {
				ResponseValidator.validateJsonSchema(response, expSchemafileName);
			}

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Date Validation for GET Request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void get_date_Validation(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
			// Hp Alm update parameter are sent to After Method present in
			// BaseTest
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);

			String dd = APIHelper.getValueforKey_SingleSearch(parameters, "queryString");

			// Validating the response schema
			final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
			if (!expSchemafileName.equals(Constants.emptyString)) {
				ResponseValidator.validateJsonSchema(response, expSchemafileName);
			}
			Regression_exchangeV3PO v2 = new Regression_exchangeV3PO(data_Search);
			v2.get_date_Validation(response, dd, data_Search, headers, URI);

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * To validate single and multi facet for GET request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void get_date_single_multi_Facet(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
			// Hp Alm update parameter are sent to After Method present in
			// BaseTest
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			System.out.println("Response is " + response.asString());
			APIHelper.validaStatusCode(response, data_Search);
			Regression_exchangeV3PO v2 = new Regression_exchangeV3PO(data_Search);
			String dd = APIHelper.getValueforKey_SingleSearch(parameters, "queryString");

			v2.multiLevelFacet_validation(response, parameters);

			v2.get_date_Validation(response, dd, data_Search, headers, URI);
			
			// Validating the response schema
			final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
			if (!expSchemafileName.equals(Constants.emptyString)) {
				ResponseValidator.validateJsonSchema(response, expSchemafileName);
			}

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Sort Negative validation for GET Request 
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void get_sort_NegativeValidation(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			// Hp Alm update parameter are sent to After Method present in
			// BaseTest
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
			// Create URI, request Body and Headers
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);

			// Send the POST or GET request
			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);

			// Validate status code using the expected status code present in
			// excel
			APIHelper.validaStatusCode(response, data_Search);
			Regression_exchangeV3PO v2 = new Regression_exchangeV3PO(data_Search);
			boolean success = v2.negativeSort(response, response.getStatusCode());

			if (success) {
				ReportHelper.log(LogStatus.INFO, "Sort with author fields-Negative validation",
						"Sort with author fields-Negative validation - PASSED");
			} else {
				ReportHelper.log(LogStatus.INFO, "Sort with author fields-Negative validation",
						"Sort with author fields-Negative validation - FAILED");
			}

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * To validate single and multi level boosting for  GET Request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void get_Single_MultiLevel_Boosting(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);

			String queryString = parameters.get("queryString");
			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			Regression_exchangeV3PO v2 = new Regression_exchangeV3PO(data_Search);
			// Validate status code using the expected status code present in
			// excel
			APIHelper.validaStatusCode(response, data_Search);

			// Validating the Response Jason Body
			ArrayList<String> expectedQuery = v2.toFindbiggestNumberinQueryString(queryString);
			v2.jsonResponseValidationWithQueryString(response, expectedQuery);

			// Validating the response schema
			//final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
			//if (!expSchemafileName.equals(Constants.emptyString)) {
				//ResponseValidator.validateJsonSchema(response, expSchemafileName);
			//}
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * To validate Product offer status for GET request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void get_product_Offer_Status(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());

			// Update in ALM
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
			// Setup the Request
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);

			String queryString = parameters.get("queryString");

			// Send the Request
			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);

			// Validating the response code
			final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
			ResponseValidator.validateResponseCode(response, expStatus_code);
			Regression_exchangeV3PO v2 = new Regression_exchangeV3PO(data_Search);
			// API - 1 - for Getting the Product IDs.
			String josanPathfromQueryString = "$.searchResults..productId";
			String api2ReqBody = data_Search.get("api2RequestBody").trim();
			String offerStatus = v2.getOfferStatus(queryString, api2ReqBody);
			v2.offerCodeStatusValidation(headers, URI, josanPathfromQueryString, response, api2ReqBody,
					webCredentials_rest, offerStatus);

			// Validating the response schema
			final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
			if (!expSchemafileName.equals(Constants.emptyString)) {
				ResponseValidator.validateJsonSchema(response, expSchemafileName);
			}
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			ReportHelper.log(LogStatus.FAIL, "LogicalOperations_with_MultiLevel_Boosting_02", "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Test to validate wild card search for POST request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void multiSpecificFields_Wildcard_V2SearchMF_POST(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final Regression_exchangeV3PO regression_exchangeV3PO = new Regression_exchangeV3PO(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			regression_exchangeV3PO.postMultipleSpecificFieldWildCard_ResponseValidate_childQuery_MF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Test to validate multi specific fields V2 Search for GET request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void multiSpecificFields_ExactPharse_V2SearchMF_GET(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final Regression_exchangeV3PO regression_exchangeV3PO = new Regression_exchangeV3PO(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			regression_exchangeV3PO.getMultipleSpecificFieldExactPharse_ResponseValidate_childQuery_MF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Test to validate multi specific fields wild card v2 Search for GET request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void multiSpecificFields_Wildcard_V2SearchMF_GET(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final Regression_exchangeV3PO regression_exchangeV3PO = new Regression_exchangeV3PO(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			regression_exchangeV3PO.getMultipleSpecificFieldWildCard_ResponseValidate_childQuery_MF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Test to validate multi specific fields wild card v2 Search for POST request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void multiSpecificField_ExactPharse_V2Search_POST(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final Regression_exchangeV3PO regression_exchangeV3PO = new Regression_exchangeV3PO(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			regression_exchangeV3PO.postMultipleExactPharse_ResponseValidate_childQuery_MF(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Test to validate single and multi facet Sorting with GroupBy for POST request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void post_single_multi_Facet_Sorting_withGroupBy(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
			// Hp Alm update parameter are sent to After Method present in
			// BaseTest
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			// Create URI, request Body and Headers
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			// Send the POST or GET request
			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);

			// Validate status code using the expected status code present in
			// excel
			APIHelper.validaStatusCode(response, data_Search);

			Regression_exchangeV3PO v2 = new Regression_exchangeV3PO(data_Search);

			// Single Facet Validation
			final JSONObject requestBodyJSON = APIHelper.GetrequestBodyasJson(data_Search);
			v2.multiLevelFacet_validation(response, requestBodyJSON);


			if (v2.isSortedField1(response, requestBody)) {
				ReportHelper.log(LogStatus.PASS, "Sorting", "Sorting is successful");
				Assert.assertTrue(true);
			} else {
				ReportHelper.log(LogStatus.FAIL, "Sorting", "$..sort field - Sorting is not successful");
				Assert.assertTrue(false);
			}
			
			// Validating the response schema
			final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
			if (!expSchemafileName.equals(Constants.emptyString)) {
				ResponseValidator.validateJsonSchema(response, expSchemafileName);
			}

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Test to validate single and multi facet Sorting with GroupBy for GET request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void get_single_multi_Facet_Sorting_withGroupBy(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
			// Hp Alm update parameter are sent to After Method present in
			// BaseTest
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			// Create URI, request Body and Headers
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);

			// Send the POST or GET request
			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);

			// Validate status code using the expected status code present in
			// excel
			APIHelper.validaStatusCode(response, data_Search);

			Regression_exchangeV3PO v2 = new Regression_exchangeV3PO(data_Search);

			// Validating the response schema
			final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
			if (!expSchemafileName.equals(Constants.emptyString)) {
				ResponseValidator.validateJsonSchema(response, expSchemafileName);
			}

			if (v2.get_isSortedField1(response, parameters)) {
				ReportHelper.log(LogStatus.PASS, "Sorting", "Sorting is successful");
				Assert.assertTrue(true);
			} else {
				ReportHelper.log(LogStatus.FAIL, "Sorting", "Sort field - Sorting is not successful");
				Assert.assertTrue(false);
			}

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * V2 Search with special character
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void getValidateSpecialCharacter(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter without SC Value", parameters.toString(), "SubStep", null);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
			if (!expSchemafileName.equals(Constants.emptyString)) {
				ResponseValidator.validateJsonSchema(response, expSchemafileName);
			}

			final int expStatusCode = Integer
					.parseInt(data_Search.get(PropLoad.getTestData("API_expStatusCode_Excel")));

			if (expStatusCode != 500) {
				final Regression_exchangeV3PO v3po = new Regression_exchangeV3PO(data_Search);
				v3po.returnCountV2Search_PostorGet(response);
				v3po.fieldtoReturn_get(response, parameters);

				final String querySearchString = Template.getParametersRemovingSpecialCharacters(parameters);
				v3po.stringValidationWithSpecialCharacters(response, querySearchString);
			}
		} 
		
		catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Test to validate special character for a POST call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void postValidateSpecialCharacter(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final String requestbody = data_Search.get(Constants.reqBody);
			final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter without SC Value", parameters.toString(), "SubStep", null);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
			APIHelper.validaStatusCode(response, data_Search);
			
			final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
			if (!expSchemafileName.equals(Constants.emptyString)) {
				ResponseValidator.validateJsonSchema(response, expSchemafileName);
			}

			final int expStatusCode = Integer
					.parseInt(data_Search.get(PropLoad.getTestData("API_expStatusCode_Excel")));

			if (expStatusCode != 500) {
				final Regression_exchangeV3PO v3po = new Regression_exchangeV3PO(data_Search);
				v3po.returnCountV2Search_PostorGet(response);
				v3po.fieldtoReturn_post(response, requestbody);

				final String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
				final String queryStringNoChar = Template.postParametersRemovingSpecialCharacters(querySearchString);
				v3po.stringValidationWithSpecialCharacters(response, queryStringNoChar);
			}
		} 
		
		catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Test to validate stop word for a POST call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void stopwordsValidationPost(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			final Regression_exchangeV3PO v3po = new Regression_exchangeV3PO(data_Search);
			final String url = data_Search.get(Constants.urlprofile);
			final Response response = webCredentials_rest.getCall(url);
			final List<Object> stopWordField = APIHelper.FeildToReturnasObject("$..isStopwordEnabled",
					response.getBody().asString());

			final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
			authtoken = CSGHelper.generate_PI_AuthToken();

			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final String requestbody = data_Search.get(Constants.reqBody);
			final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

			final Response response1 = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
			final String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
			
			final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
			if (!expSchemafileName.equals(Constants.emptyString)) {
				ResponseValidator.validateJsonSchema(response1, expSchemafileName);
			}

			v3po.validateStopWordExchangeV3(data_Search, response1, querySearchString, stopWordField);
			v3po.stopWordCheckExchangeV3(data_Search, response1, querySearchString, stopWordField);
			v3po.fieldtoReturn_post(response1, requestbody);
		}
		
		catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Test to validate special character for a GET call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void stopwordsValidationGet(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			final Regression_exchangeV3PO v3po = new Regression_exchangeV3PO(data_Search);
			final String url = data_Search.get(Constants.urlprofile);
			final Response response = webCredentials_rest.getCall(url);
			final List<Object> stopWordField = APIHelper.FeildToReturnasObject("$..isStopwordEnabled",
					response.getBody().asString());

			final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
			authtoken = CSGHelper.generate_PI_AuthToken();

			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response1 = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			
			final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
			if (!expSchemafileName.equals(Constants.emptyString)) {
				ResponseValidator.validateJsonSchema(response1, expSchemafileName);
			}
			
			v3po.validateStopWordExchangeV3(data_Search, response1, querySearchString, stopWordField);
			v3po.stopWordCheckExchangeV3(data_Search, response1, querySearchString, stopWordField);
			v3po.fieldtoReturn_get(response1, parameters);
		}

		catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * Test to validate escape characters for a POST call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void escapeCharacterValidationPost(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			Regression_exchangeV3PO v3po = new Regression_exchangeV3PO(data_Search);
			final String url = data_Search.get(Constants.urlprofile);
			final Response response = webCredentials_rest.getCall(url);
			final List<Object> stopWordField = APIHelper.FeildToReturnasObject("$..isStopwordEnabled",
					response.getBody().asString());

			final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
			authtoken = CSGHelper.generate_PI_AuthToken();

			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final String requestbody = data_Search.get(Constants.reqBody);
			final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

			final Response response1 = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
			final String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
			
			final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
			if (!expSchemafileName.equals(Constants.emptyString)) {
				ResponseValidator.validateJsonSchema(response1, expSchemafileName);
			}

			v3po.validateStopWordExchangeV3(data_Search, response1, querySearchString, stopWordField);
			v3po.stopWordCheckExchangeV3(data_Search, response1, querySearchString, stopWordField);
			v3po.fieldtoReturn_post(response1, requestbody);
		} 
		
		catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Test to validate escape Character for a GET call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void escapeCharacterValidationGet(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			Regression_exchangeV3PO v3po = new Regression_exchangeV3PO(data_Search);
			final String url = data_Search.get(Constants.urlprofile);
			final Response response = webCredentials_rest.getCall(url);
			final List<Object> stopWordField = APIHelper.FeildToReturnasObject("$..isStopwordEnabled",
					response.getBody().asString());

			final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
			authtoken = CSGHelper.generate_PI_AuthToken();

			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response1 = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			
			final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
			if (!expSchemafileName.equals(Constants.emptyString)) {
				ResponseValidator.validateJsonSchema(response1, expSchemafileName);
			}

			v3po.validateStopWordExchangeV3(data_Search, response1, querySearchString, stopWordField);
			v3po.stopWordCheckExchangeV3(data_Search, response1, querySearchString, stopWordField);
			v3po.fieldtoReturn_get(response1, parameters);
		}

		catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
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
	public void singleSpecific_Post_product_Offer_Status(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());

			// Update in ALM
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			// Setup the Request
			final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final String requestbody = data_Search.get(Constants.reqBody);
			final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);
			String queryString = Template.getFieldValfromJson(reqBodyJson, "queryString");

			// Send the Request
			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);

			// Validating the response code
			final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
			ResponseValidator.validateResponseCode(response, expStatus_code);

			// API - 1 - for Getting the Product IDs.
			String josanPathfromQueryString = "$.searchResults..productId";
			String api2ReqBody = data_Search.get("api2RequestBody").trim();
			Regression_exchangeV3PO v2 = new Regression_exchangeV3PO(data_Search);
			String offerStatus = v2.getOfferStatus(queryString, api2ReqBody);

			v2.offerCodeStatusValidation(headers, reqURL, josanPathfromQueryString, response, api2ReqBody,
					webCredentials_rest, offerStatus);		
			v2.fieldtoReturn_post(response, requestbody);
			v2.post_specificField(response, requestbody);
						

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			ReportHelper.log(LogStatus.FAIL, "LogicalOperations_with_MultiLevel_Boosting_02", "");
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
	public void singleSpecific_get_product_Offer_Status(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());

			// Update in ALM
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
			// Setup the Request
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);

			String queryString = parameters.get("queryString");

			// Send the Request
			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);

			// Validating the response code
			final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
			ResponseValidator.validateResponseCode(response, expStatus_code);
			Regression_exchangeV3PO v2 = new Regression_exchangeV3PO(data_Search);
			// API - 1 - for Getting the Product IDs.
			String josanPathfromQueryString = "$.searchResults..productId";
			String api2ReqBody = data_Search.get("api2RequestBody").trim();
			String offerStatus = v2.getOfferStatus(queryString, api2ReqBody);
			v2.offerCodeStatusValidation(headers, URI, josanPathfromQueryString, response, api2ReqBody,
					webCredentials_rest, offerStatus);
			
				v2.fieldtoReturn_get(response, parameters);
				v2.get_specificField(response, parameters);
						
			
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			ReportHelper.log(LogStatus.FAIL, "LogicalOperations_with_MultiLevel_Boosting_02", "");
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
	public void multiSpecific_Post_product_Offer_Status(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());

			// Update in ALM
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			// Setup the Request
			final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final String requestbody = data_Search.get(Constants.reqBody);
			final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);
			String queryString = Template.getFieldValfromJson(reqBodyJson, "queryString");

			// Send the Request
			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);

			// Validating the response code
			final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
			ResponseValidator.validateResponseCode(response, expStatus_code);

			// API - 1 - for Getting the Product IDs.
			String josanPathfromQueryString = "$.searchResults..productId";
			String api2ReqBody = data_Search.get("api2RequestBody").trim();
			Regression_exchangeV3PO v2 = new Regression_exchangeV3PO(data_Search);
			String offerStatus = v2.getOfferStatus(queryString, api2ReqBody);

			v2.offerCodeStatusValidation(headers, reqURL, josanPathfromQueryString, response, api2ReqBody,
					webCredentials_rest, offerStatus);		
			v2.fieldtoReturn_post(response, requestbody);
			//v2.post_specificField(response, requestbody);
			
			String queryStringFinal = queryString.split("AND")[0];
			
			final Map<String, String> specificFieldNames = Template.getMultipleSpecficFieldNames(queryStringFinal);

			// Verify Specific Field name's wild card data present in json
			final boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
					response.asString(), specificFieldNames, ResponseJsonPath.v2SearchSource_JP, false);
			final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSource_JP,
					response.asString());
			if (!bSpecsuccess) {
				ReportHelper.log(LogStatus.FAIL, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			}
			Assert.assertTrue(bSpecsuccess);
			if (arrayValuesFromJson.size() == 0) {
				ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
						"Count is returned as Zero - please check your QueryString");
			} else {
				ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
				LoggerUtil.log("Verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
						"Field value is matching with given query string : " + queryStringFinal, Constants.subStep, null);
			}
			

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			ReportHelper.log(LogStatus.FAIL, "LogicalOperations_with_MultiLevel_Boosting_02", "");
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
	public void multiSpecific_get_product_Offer_Status(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());

			// Update in ALM
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			// Setup the Request
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);

			String queryString = parameters.get("queryString");

			// Send the Request
			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);

			// Validating the response code
			final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
			ResponseValidator.validateResponseCode(response, expStatus_code);
			Regression_exchangeV3PO v2 = new Regression_exchangeV3PO(data_Search);
			
			// API - 1 - for Getting the Product IDs.
			String josanPathfromQueryString = "$.searchResults..productId";
			String api2ReqBody = data_Search.get("api2RequestBody").trim();
			String offerStatus = v2.getOfferStatus(queryString, api2ReqBody);
			v2.offerCodeStatusValidation(headers, URI, josanPathfromQueryString, response, api2ReqBody,
					webCredentials_rest, offerStatus);

				
			v2.fieldtoReturn_get(response, parameters);
			// v2.post_specificField(response, requestbody);

			String queryStringFinal = queryString.split("AND")[0];

			final Map<String, String> specificFieldNames = Template.getMultipleSpecficFieldNames(queryStringFinal);

			// Verify Specific Field name's wild card data present in json
			final boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
			response.asString(), specificFieldNames, ResponseJsonPath.v2SearchSource_JP, false);
			final JSONArray arrayValuesFromJson = Template
						.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSource_JP, response.asString());
			if (!bSpecsuccess) {
				ReportHelper.log(LogStatus.FAIL, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
				}
			Assert.assertTrue(bSpecsuccess);
			if (arrayValuesFromJson.size() == 0) {
					ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
							"Count is returned as Zero - please check your QueryString");
			} else {
				ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
				LoggerUtil.log("Verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
							"Field value is matching with given query string : " + queryStringFinal, Constants.subStep,
							null);
				}
			
		
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			ReportHelper.log(LogStatus.FAIL, "LogicalOperations_with_MultiLevel_Boosting_02", "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}



}
