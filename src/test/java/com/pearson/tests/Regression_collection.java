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
import com.pearson.pageobjects.Regression_collectionPO;
import com.pearson.pageobjects.Regression_exchangeV3PO;
import com.pearson.regression.utilityHelpers.APIHelper;
import com.pearson.regression.utilityHelpers.CSGHelper;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.PropLoad;
import com.pearson.regression.utilityHelpers.RESTServiceBase;
import com.pearson.regression.utilityHelpers.ReportHelper;
import com.pearson.regression.utilityHelpers.ResponseJsonPath;
import com.pearson.regression.utilityHelpers.Template;
import com.relevantcodes.extentreports.LogStatus;

import net.minidev.json.JSONObject;

public class Regression_collection extends BaseTest {

	String authToken = "";
	String TestDescription = Constants.emptyString;

	private RESTServiceBase webCredentials_rest = new RESTServiceBase();
	private static String xAuthKey = PropLoad.getTestData("Auth_XAuthKey");
	private Regression_collectionPO collectionPO = new Regression_collectionPO();
	private static String qs_String = PropLoad.getTestData("V2Search_queryString_Excel");
	private static String qs_reqField = PropLoad.getTestData("V2Search_qs_reqfield");

	/**
	 * Normal and Wildcard search for a POST call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	
	@Test(dataProvider = "dp")
	public void normal_and_wildCard_V2Search_POST(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final Regression_collectionPO regression_collectionPO = new Regression_collectionPO(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			regression_collectionPO.postLogicalOperator(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Normal and Wildcard search for a GET call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void normal_and_wildCard_V2Search_GET(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final Regression_collectionPO regression_collectionPO = new Regression_collectionPO(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			regression_collectionPO.getSingleandMultipleTermSearch(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}	
	
	/**
	 * Single and Multi facet validation for POST call
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

			authToken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authToken);

			// Send the POST or GET request
			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);

			// Validate status code using the expected status code present in
			// excel
			APIHelper.validaStatusCode(response, data_Search);

			// Single Facet Validation
			final JSONObject requestBodyJSON = APIHelper.GetrequestBodyasJson(data_Search);
			collectionPO.multiLevelFacet_validation(response, requestBodyJSON);

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Single and Multi facet validation for GET call
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
			authToken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authToken);

			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);

			collectionPO.get_multiLevelFacet_validation(response, parameters);

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());

			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Single and Multi facet Sorting for POST call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void post_single_multi_Facet_Sorting(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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

			authToken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authToken);

			// Send the POST or GET request
			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);

			// Validate status code using the expected status code present in
			// excel
			APIHelper.validaStatusCode(response, data_Search);

			// Single Facet Validation
			final JSONObject requestBodyJSON = APIHelper.GetrequestBodyasJson(data_Search);
			collectionPO.multiLevelFacet_validation(response, requestBodyJSON);

			// Sorting Validation
			collectionPO.fieldtoReturn_post(response, requestBody);

			if (collectionPO.isSortedField1(response, requestBody)) {
				ReportHelper.log(LogStatus.PASS, "Sorting", "Sorting is successful");
				Assert.assertTrue(true);
			} else {
				ReportHelper.log(LogStatus.FAIL, "Sorting", "$..sort field - Sorting is not successful");
				Assert.assertTrue(false);
			}

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Single and Multi facet Sorting for GET call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void get_single_multi_Facet_Sorting(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
			// Hp Alm update parameter are sent to After Method present in
			// BaseTest
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			// Create URI, request Body and Headers
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authToken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authToken);

			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);

			// Send the POST or GET request
			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);

			// Validate status code using the expected status code present in
			// excel
			APIHelper.validaStatusCode(response, data_Search);

			// Single Facet Validation
			collectionPO.get_multiLevelFacet_validation(response, parameters);

			// Sorting Validation

			collectionPO.get_fieldtoReturn_post(response, parameters);

			if (collectionPO.get_isSortedField1(response, parameters)) {
				ReportHelper.log(LogStatus.PASS, "Sorting", "Sorting is successful");
				Assert.assertTrue(true);
			} else {
				ReportHelper.log(LogStatus.FAIL, "Sorting", "$..sort field - Sorting is not successful");
				Assert.assertTrue(false);
			}

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Single and Multi facet Boosting for GET call
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
			authToken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authToken);

			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);

			String queryString = parameters.get("queryString");
			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);

			// Validate status code using the expected status code present in
			// excel
			APIHelper.validaStatusCode(response, data_Search);

			// Validating the Response Jason Body
			ArrayList<String> expectedQuery = collectionPO.toFindbiggestNumberinQueryString(queryString);
			collectionPO.jsonResponseValidationWithQueryString(response, expectedQuery);

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Special character validation for GET call
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
			authToken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authToken);

			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter without SC Value", parameters.toString(), "SubStep", null);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);

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
	 * Stop words validation for POST call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void stopwordsValidationPost(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			Regression_exchangeV3PO v3po = new Regression_exchangeV3PO(data_Search);
			final String url = data_Search.get(Constants.urlprofile);
			final Response response = webCredentials_rest.getCall(url);
			final List<Object> stopWordField = APIHelper.FeildToReturnasObject("$..isStopwordEnabled",
					response.getBody().asString());

			final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
			authToken = CSGHelper.generate_PI_AuthToken();

			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authToken);
			final String requestbody = data_Search.get(Constants.reqBody);
			final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

			final Response response1 = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
			final String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);

			v3po.validateStopWord(data_Search, response1, querySearchString, stopWordField);
			v3po.stopWordCheck(data_Search, response1, querySearchString, stopWordField);
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
	 * Stop words validation for GET call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void stopwordsValidationGet(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			Regression_exchangeV3PO v3po = new Regression_exchangeV3PO(data_Search);
			final String url = data_Search.get(Constants.urlprofile);
			final Response response = webCredentials_rest.getCall(url);
			final List<Object> stopWordField = APIHelper.FeildToReturnasObject("$..isStopwordEnabled",
					response.getBody().asString());

			final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
			authToken = CSGHelper.generate_PI_AuthToken();

			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authToken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response1 = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);

			v3po.validateStopWord(data_Search, response1, querySearchString, stopWordField);
			v3po.stopWordCheck(data_Search, response1, querySearchString, stopWordField);
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
	 * Auto complete validation
	 * @param m
	 * @param data_Search
	 * @param ctx
	 * @throws Exception
	 */
	@Test(dataProvider = "dp")
	public void autoCompletePost2to8Characters(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx)
			throws Exception {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String reqBodyJson = APIHelper.GetrequestBody(data_Search);
			JSONObject json = Template.convertStringtoJsonObject(reqBodyJson);

			final Regression_exchangeV3PO v3po = new Regression_exchangeV3PO(data_Search);
			authToken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authToken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);

			v3po.returnCountV2Search_PostorGet(response);
			v3po.fieldtoReturn_post(response, reqBodyJson);

			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
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
	 * Auto complete validation for an invalid request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 * @throws Exception
	 */
	@Test(dataProvider = "dp")
	public void autoCompletePostnot2to8Characters(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) throws Exception {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String reqBodyJson = APIHelper.GetrequestBody(data_Search);
			JSONObject json = Template.convertStringtoJsonObject(reqBodyJson);

			authToken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authToken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final Regression_collectionPO collectionpo = new Regression_collectionPO(data_Search);
			collectionpo.returnNoCountV2SearchPostorGet(response);
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
	 * Escape character validation for POST call
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
			authToken = CSGHelper.generate_PI_AuthToken();

			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authToken);
			final String requestbody = data_Search.get(Constants.reqBody);
			final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

			final Response response1 = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
			final String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);

			v3po.validateStopWord(data_Search, response1, querySearchString, stopWordField);
			v3po.stopWordCheck(data_Search, response1, querySearchString, stopWordField);
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
	 * Escape character validation for GET call
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
			authToken = CSGHelper.generate_PI_AuthToken();

			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authToken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response1 = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);

			v3po.validateStopWord(data_Search, response1, querySearchString, stopWordField);
			v3po.stopWordCheck(data_Search, response1, querySearchString, stopWordField);
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
	 * All fields verification for POST call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 * @throws Exception
	 */
	@Test(dataProvider = "dp")
	public void allFieldVerificationPost(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx)
			throws Exception {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String reqBodyJson = APIHelper.GetrequestBody(data_Search);
			JSONObject json = Template.convertStringtoJsonObject(reqBodyJson);

			final Regression_exchangeV3PO v3po = new Regression_exchangeV3PO(data_Search);
			authToken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authToken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			System.out.println(response.asString());
			APIHelper.validaStatusCode(response, data_Search);

			v3po.fieldtoReturn_post(response, reqBodyJson);
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
	 * All fields verification for GET call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void allFieldVerificationGet(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authToken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authToken);

			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter without SC Value", parameters.toString(), "SubStep", null);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			System.out.println(response.asString());
			APIHelper.validaStatusCode(response, data_Search);
			
			final Regression_exchangeV3PO v3po = new Regression_exchangeV3PO(data_Search);
			v3po.fieldtoReturn_get(response, parameters);
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
	 * Date validation for POST call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void post_date_Validation(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
			
			final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
			authToken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authToken);
			
			final String requestbody = data_Search.get(Constants.reqBody);
			final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);
			final String queryString = Template.getFieldValfromJson(reqBodyJson, "queryString");
			
			//API1 Request
			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
			APIHelper.validaStatusCode(response, data_Search);
			String josanPathforProductID = "$..searchResults..schema:datePublished";
			List<String> datepublishedactual = APIHelper.FeildToReturnasArray(josanPathforProductID,response.asString());
			
			//get the dates form Querystring in API1
			List<String> datePublished = collectionPO.startDateAndEndDateSearch(queryString);
			collectionPO.dateOublishValidationwithActualDates(datePublished, datepublishedactual);
			 

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	/**
	 * Date validation for GET request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void get_date_Validation(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authToken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authToken);
			Regression_exchangeV3PO v2 = new Regression_exchangeV3PO(data_Search);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			String queryString = parameters.get("queryString").trim();
			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			//API1 Request
			String josanPathforProductID = "$..searchResults..schema:datePublished";
			List<String> datepublishedactual = APIHelper.FeildToReturnasArray(josanPathforProductID,response.asString());
			
			//get the dates form Querystring in API1
			List<String> datePublished = collectionPO.startDateAndEndDateSearch(queryString);
			collectionPO.dateOublishValidationwithActualDates(datePublished, datepublishedactual);
			 

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * Normal search for POST request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void post_normal_Serach(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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

			authToken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authToken);

			// Send the POST or GET request
			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);

			// Validate status code using the expected status code present in
			// excel
			APIHelper.validaStatusCode(response, data_Search);

			// Sorting Validation
			collectionPO.fieldtoReturn_post(response, requestBody);


		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * Normal Search for GET request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void get_normal_Serach(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
			// Hp Alm update parameter are sent to After Method present in
			// BaseTest
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authToken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authToken);
			Regression_exchangeV3PO v2 = new Regression_exchangeV3PO(data_Search);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);

			collectionPO.get_fieldtoReturn_post(response, parameters);
			
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
}
