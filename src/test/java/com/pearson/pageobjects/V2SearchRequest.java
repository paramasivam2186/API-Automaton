package com.pearson.pageobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.pearson.regression.utilityHelpers.APIHelper;
import com.pearson.regression.utilityHelpers.CSGHelper;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.PropLoad;
import com.pearson.regression.utilityHelpers.RESTServiceBase;
import com.pearson.regression.utilityHelpers.ReportHelper;
import com.pearson.regression.utilityHelpers.ResponseJsonPath;
import com.pearson.regression.utilityHelpers.ResponseValidator;
import com.pearson.regression.utilityHelpers.Template;
import com.pearson.regression.utilityHelpers.Wildcard;
import com.relevantcodes.extentreports.LogStatus;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class V2SearchRequest extends RESTServiceBase {

	static RESTServiceBase webCredentials_rest = new RESTServiceBase();
	@SuppressWarnings("unused")
	private HashMap<String, String> data;
	String authtoken = "";
	private static String qs_String = PropLoad.getTestData("V2Search_queryString_Excel");
	private static String expFieldName = PropLoad.getTestData("V2Search_expFields_Excel");
	private static String xAuthKey = PropLoad.getTestData("Auth_XAuthKey");
	private static String qs_reqField = PropLoad.getTestData("V2Search_qs_reqfield");
	private static String expFieldName_reqField = PropLoad.getTestData("V2Search_expFields_reqfield");

	public V2SearchRequest(LinkedHashMap<String, String> data) {
		this.data = data;
	}

	/**
	 * Functionality to check the single term & wildcard as expected for get method
	 */
	public void getSingleandMultipleTermSearch(LinkedHashMap<String, String> data_Search) throws Exception {
		System.out.println("Getting in to getSingleandMultipleTermSearch method");
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);

		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		// Validating the search query present in JSON response (MatchedFields)
		final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);
		Boolean bSuccess;
		bSuccess = CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
				ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMatchedName_JP,
				response.asString());
		if (!bSuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"em tag match validation");
			Assert.fail();
		}	
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"em tag match validation");
		}
		LoggerUtil.log("Verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
				"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);

		// Verify Field to return present in json
		final String[] expFieldnames = Template.getFieldNamesFromKey(data_Search, expFieldName);
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSource_JP);
	}
	
	
	/**
	 * Functionality to check the single term & wildcard as expected for post method
	 */
	public void postLogicalOperator(LinkedHashMap<String, String> data_Search) throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final String requestbody = data_Search.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		final String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString,
				" Application ID: " + applicationId);

		Boolean bSuccess ;
		bSuccess = CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
				ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMatchedName_JP,
				response.asString());
		if (!bSuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"em tag match validation");
			Assert.fail();
		}	
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"em tag match validation");
		}
		LoggerUtil.log("Verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
				"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);

		// Verify Field to return present in json
		final String[] expFieldnames = Template.getStringArrayValfromJson(reqBodyJson, expFieldName_reqField);
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSource_JP);
	}

	/**
	 * Functionality to check the multiple specific with logical term working as expected for get method
	 */
	public void getMultipleSpecificFieldWildCard_ResponseValidate(LinkedHashMap<String, String> data_Search)
			throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);
		final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString,
				" Application ID: " + applicationId);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		final Map<String, String> specificFieldNames = Template.getMultipleSpecficFieldNames(querySearchString);

		// Verify Specific Field name's wild card data present in json
		final boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
				response.asString(), specificFieldNames, ResponseJsonPath.v2SearchSource_JP, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSource_JP,
				response.asString());
		if (!bSpecsuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			Assert.fail();
		}	
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			LoggerUtil.log("Verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		final String[] expFieldnames = Template.getFieldNamesFromKey(data_Search, expFieldName);
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSource_JP);

	}

	
	/**
	 * Functionality to check the single specific with logical term working as expected for post method
	 */
	public void postSpecificFielsAndLogicResponseValidate(LinkedHashMap<String, String> data_Search) throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final String requestbody = data_Search.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		final String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString,
				" Application ID: " + applicationId);
		final String[] expFieldnames = Template.getStringArrayValfromJson(reqBodyJson, expFieldName_reqField);

		// Validating the search query present in JSON response
		final Map<String, String> specificFieldNames = Template.getSingleSpecficFieldNames(querySearchString);
		// Verify Specific Field name's wild card data present in json
		final boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
				response.asString(), specificFieldNames, ResponseJsonPath.v2SearchSource_JP, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSource_JP,
				response.asString());
		if (!bSpecsuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			Assert.fail();
		}	
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			LoggerUtil.log("Verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSource_JP);

	}

	/**
	 * Functionality to check the single specific with logical term working as expected for get method
	 */
	public void getSingleSpecificFieldsAndLogicResponseValidate(LinkedHashMap<String, String> data_Search)
			throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);
		final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString,
				" Application ID: " + applicationId);
		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		// Validating the search query present in JSON response
		final Map<String, String> specificFieldNames = Template.getSingleSpecficFieldNames(querySearchString);
		// Verify Specific Field name's wild card data present in json
		final boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
				response.asString(), specificFieldNames, ResponseJsonPath.v2SearchSource_JP, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSource_JP,
				response.asString());
		if (!bSpecsuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			Assert.fail();
		}	
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			LoggerUtil.log("Verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		final String[] expFieldnames = Template.getFieldNamesFromKey(data_Search, expFieldName);
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSource_JP);
	}

	
	/**
	 * Functionality to check the multiple specific with logical term working as expected for post method
	 */
	public void postMultiSpecificFieldLogic_SearchResponseValidate(LinkedHashMap<String, String> data_Search)
			throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final String requestbody = data_Search.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
		final String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		final String[] expFieldnames = Template.getStringArrayValfromJson(reqBodyJson, expFieldName_reqField);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString,
				" Application ID: " + applicationId);
		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		// Verify Specific Field name's wild card data present in json
		final boolean bSuccess = CSGHelper.verifyv2SearchMultipleField_SearchQueryString_Logic(response.asString(),
				querySearchString, ResponseJsonPath.v2SearchSource_JP, false);

		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSource_JP,
				response.asString());
		if (!bSuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchMultipleField_SearchQueryString_Logic", querySearchString);
			Assert.fail();
		}	
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMultipleField_SearchQueryString_Logic",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMultipleField_SearchQueryString_Logic", querySearchString);
			LoggerUtil.log("Verifyv2SearchMultipleField_SearchQueryString_Logic",
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSource_JP);
	}

	/**
	 * Functionality to check the single specific with logical term working as expected for get method
	 */
	public void getMultiSpecificFieldLogic_SearchResponseValidate(LinkedHashMap<String, String> data_Search)
			throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);
		final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString,
				" Application ID: " + applicationId);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		final String[] expFieldnames = Template.getFieldNamesFromKey(data_Search, expFieldName);

		// Verify Specific Field name's wild card data present in json
		final boolean bSuccess = CSGHelper.verifyv2SearchMultipleField_SearchQueryString_Logic(response.asString(),
				querySearchString, ResponseJsonPath.v2SearchSource_JP, false);

		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSource_JP,
				response.asString());
		if (!bSuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchMultipleField_SearchQueryString_Logic", querySearchString);
			Assert.fail();
		}	
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMultipleField_SearchQueryString_Logic",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMultipleField_SearchQueryString_Logic", querySearchString);
			LoggerUtil.log("Verifyv2SearchMultipleField_SearchQueryString_Logic",
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSource_JP);
	}

	/**
	 * Functionality to check the single specific with logical & wildcard term working as expected for post method
	 */
	public void postMultipleSpecificFieldWildCard_ResponseValidate(LinkedHashMap<String, String> data_Search)
			throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final String requestbody = data_Search.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		final String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString,
				" Application ID: " + applicationId);
		final Map<String, String> specificFieldNames = Template.getMultipleSpecficFieldNames(querySearchString);
		final String[] expFieldnames = Template.getStringArrayValfromJson(reqBodyJson, expFieldName_reqField);

		// Verify Specific Field name's wild card data present in json
		final boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
				response.asString(), specificFieldNames, ResponseJsonPath.v2SearchSource_JP, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSource_JP,
				response.asString());
		if (!bSpecsuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			Assert.fail();
		}	
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			LoggerUtil.log("Verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSource_JP);
	}

	/**
	 * Functionality to check the multi specific with logical & wildcard term working as expected for post method
	 */
	public void postMultipleSpecificFieldWildCard_ResponseValidate_exactPharse(
			LinkedHashMap<String, String> data_Search) throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final String requestbody = data_Search.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		final String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString,
				" Application ID: " + applicationId);
		final Map<String, String> specificFieldNames = Template.getMultipleSpecficFieldNames_Exact(querySearchString);
		final String[] expFieldnames = Template.getStringArrayValfromJson(reqBodyJson, expFieldName_reqField);

		// Verify Specific Field name's wild card data present in json
		final boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
				response.asString(), specificFieldNames, ResponseJsonPath.v2SearchSource_JP, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSource_JP,
				response.asString());
		if (!bSpecsuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			Assert.fail();
		}	
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			LoggerUtil.log("Verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSource_JP);
	}

	
	/**
	 * Functionality to check the multi specific field with combo logical search term working as expected for post method
	 */
	public void postMultiFieldSearchWithCombLogic(LinkedHashMap<String, String> data_Search) throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final String requestbody = data_Search.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		// Verify Field to return present in json
		final String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString,
				" Application ID: " + applicationId);
		final String[] expFieldnames = Template.getStringArrayValfromJson(reqBodyJson, expFieldName_reqField);

		// Validating the search query present in JSON response
		final boolean bSuccess = CSGHelper.verifyv2SearchMultiFieldSearchWithCombLogic(response.asString(),
				querySearchString, ResponseJsonPath.v2SearchSource_JP, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSource_JP,
				response.asString());
		if (!bSuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchMultipleField_SearchQueryString_Logic", querySearchString);
			Assert.fail();
		}	
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMultipleField_SearchQueryString_Logic",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMultipleField_SearchQueryString_Logic", querySearchString);
			LoggerUtil.log("Verifyv2SearchMultiFieldSearchWithCombLogic",
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSource_JP);

	}

	/**
	 * Functionality to check the multi specific field with combo logical search
	 * term working as expected for get method
	 */
	public void getMultiFieldSearchWithCombLogic(LinkedHashMap<String, String> data_Search) throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);
		final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString,
				" Application ID: " + applicationId);
		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		// Validating the search query present in JSON response
		final boolean bSuccess = CSGHelper.verifyv2SearchMultiFieldSearchWithCombLogic(response.asString(),
				querySearchString, ResponseJsonPath.v2SearchSource_JP, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSource_JP,
				response.asString());
		if (!bSuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchMultipleField_SearchQueryString_Logic", querySearchString);
			Assert.fail();
		}	
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMultipleField_SearchQueryString_Logic",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMultipleField_SearchQueryString_Logic", querySearchString);
			LoggerUtil.log("Verifyv2SearchMultiFieldSearchWithCombLogic",
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		final String[] expFieldnames = Template.getFieldNamesFromKey(data_Search, expFieldName);
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSource_JP);
	}

	/**
	 * Functionality to check the logical operator with exact pharse search term
	 * working as expected for post method
	 */
	public void postLogicalOperator_ExactPharse(LinkedHashMap<String, String> data_Search) throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final String requestbody = data_Search.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}
		final String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString,
				" Application ID: " + applicationId);

		// Verify MatchedField Wild card string present with Em tag
		Boolean bSuccess;
		bSuccess = CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
				ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMatchedName_JP,
				response.asString());
		if (!bSuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"em tag match validation");
			Assert.fail();
		}	
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"em tag match validation");
		}
		LoggerUtil.log("Verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
				"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);

		// Verify Field to return present in json
		final String[] expFieldnames = Template.getStringArrayValfromJson(reqBodyJson, expFieldName_reqField);
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSource_JP);
	}

	
	/**
	 * Functionality to check the logical operator with exact pharse search term working as expected for get method
	 */
	public void getLogicalOperator_ExactPharse(LinkedHashMap<String, String> data_Search) throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);
		final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString,
				" Application ID: " + applicationId);
		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		// Verify MatchedField Wild card string present with Em tag
		Boolean bSuccess ;
		bSuccess = CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
				ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMatchedName_JP,
				response.asString());
		if (!bSuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"em tag match validation");
			Assert.fail();
		}	
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"em tag match validation");
		}
		LoggerUtil.log("Verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
				"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);

		// Verify Field to return present in json
		final String[] expFieldnames = Template.getFieldNamesFromKey(data_Search, expFieldName);
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSource_JP);

	}

	/**
	 * Functionality to check the negative validation search term working as expected for post method
	 */
	public void negative_validation(LinkedHashMap<String, String> data_Search) throws Exception {
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final String requestbody = data_Search.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);
		final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);
		// Validating json response with expected Json Response
		final String expJson = data_Search.get(Constants.expJsonRes).trim();
		ResponseValidator.compareJsonResponse(response, expJson);
	}

	/**
	 * Functionality to check the stopWordCheck
	 */
	public void stopWordCheck(LinkedHashMap<String, String> data_Search, Response response, String querySearchString,
			List<Object> stopWordField) throws Exception {

		final String res = response.toString();
		String stop = null;
		final String[] list = { "a", "an", "and", "are", "as", "at", "be", "but", "by", "for", "if", "in", "into", "is",
				"it", "no", "not", "of", "on", "or", "such", "that", "the", "their", "then", "there", "these", "they",
				"this", "to", "was", "will", "with" };
		final List<String> stopwords = new ArrayList<String>();
		stopwords.addAll(Arrays.asList(list));
		for (String s : stopwords) {
			if (querySearchString.contains((s))) {
				stop = s;
			}
		}
		final String whole = Constants.emprefixtag + stop + Constants.emsuffixtag;
		final boolean val = res.contains(whole);

		if (stopWordField.get(0).toString().contains("true"))
			if (val == false) {
				ReportHelper.log(LogStatus.PASS, "stopwordcheck", "stopwords are not highlighted in em tag");
			} else {
				ReportHelper.log(LogStatus.FAIL, "stopwordcheck", "stopwords are highlighted in em tag");
			}
		else if (stopWordField.get(0).toString().contains("false"))
			if (val == true) {
				ReportHelper.log(LogStatus.PASS, "stopwordcheck", "stopwords are highlighted in em tag");
			} else {
				ReportHelper.log(LogStatus.FAIL, "stopwordcheck", "stopwords are not highlighted in em tag");
			}
	}

	/**
	 * Functionality to validate the StopWord
	 */
	public void validateStopWord(LinkedHashMap<String, String> data_Search, Response response, String querySearchString,
			List<Object> stopWordField) throws Exception {
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		boolean bSuccessStringOne = false;
		boolean bSuccessStringTwo = false;
		boolean stringOne = false;
		boolean stringTwo = false;

		final String[] stopWordsArr = { "a", "an", "and", "are", "as", "at", "be", "but", "by", "for", "if", "in",
				"into", "is", "it", "no", "not", "of", "on", "or", "such", "that", "the", "their", "then", "there",
				"these", "they", "this", "to", "was", "will", "with" };
		final List<String> stopWords = new ArrayList<String>();
		stopWords.addAll(Arrays.asList(stopWordsArr));

		final Set<String> expValue = Template.getQueryStringSets(querySearchString, false);
		final String[] expValueArr = expValue.toArray(new String[expValue.size()]);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMatchedName_JP,
				response.asString());
		if (arrayValuesFromJson.size() == 0) {
			LoggerUtil.log("Count is returned as Zero - please check your QueryString", "FAIL", "SubStep", null);
			ReportHelper.log(LogStatus.FAIL, "getValidateStopWord", "Count is returned as Zero");
		} else {
			for (Object jsonObject : arrayValuesFromJson) {
				@SuppressWarnings("unchecked")
				final Map<String, String> set = (Map<String, String>) jsonObject;
				for (String fValue : set.values()) {
					if (expValueArr[0].contains(Constants.doublequote)) {
						expValueArr[0] = expValueArr[0].replace(Constants.doublequote, Constants.emptyString);
					}
					bSuccessStringOne = Wildcard.emtagmatch(fValue.toLowerCase(), expValueArr[0]);
					if (bSuccessStringOne == true)
						break;

					if (expValueArr[1].contains(Constants.doublequote)) {
						expValueArr[1] = expValueArr[1].replace(Constants.doublequote, Constants.emptyString);
					}
					bSuccessStringTwo = Wildcard.emtagmatch(fValue.toLowerCase(), expValueArr[1]);
					if (bSuccessStringTwo == true)
						break;
				}
			}
			for (String s : stopWords) {
				if (s.contains(expValueArr[0])) {
					stringOne = true;
					break;
				}
				if (s.contains(expValueArr[1])) {
					stringTwo = true;
					break;
				}
			}
			if (stopWordField.get(0).toString().contains("true")) {
				if ((((bSuccessStringOne == true) && (stringOne == false))
						&& ((bSuccessStringTwo == false) && (stringTwo == true)))
						|| (((bSuccessStringOne == false) && (stringOne == true))
								&& ((bSuccessStringTwo == true) && (stringTwo == false)))) {
					ReportHelper.log(LogStatus.PASS, "getValidateStopWord",
							"Query string is present in <em> tag and the stop word is not present in the <em> tag");
				} else {
					ReportHelper.log(LogStatus.FAIL, "getValidateStopWord",
							"Query string and the stop word are present in the <em> tag");
				}
			}
			if (stopWordField.get(0).toString().contains("false")) {
				if ((bSuccessStringOne == true) && (bSuccessStringTwo == true)) {
					ReportHelper.log(LogStatus.PASS, "getValidateStopWord",
							"Query string and the stop word are present in the <em> tag");
				} else {
					ReportHelper.log(LogStatus.FAIL, "getValidateStopWord",
							"Query string and the stop word are not present in the <em> tag");
				}
			}
		}
	}
}