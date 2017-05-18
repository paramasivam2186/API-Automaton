package com.pearson.pageobjects;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
import com.relevantcodes.extentreports.LogStatus;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class V2SearchSingleFacetRequest extends RESTServiceBase {

	static RESTServiceBase webCredentials_rest = new RESTServiceBase();
	@SuppressWarnings("unused")
	private HashMap<String, String> data;
	String authtoken = "";

	private static String qs_String = PropLoad.getTestData("V2Search_queryString_Excel");
	private static String expFieldName = PropLoad.getTestData("V2Search_expFields_Excel");
	private static String xAuthKey = PropLoad.getTestData("Auth_XAuthKey");
	private static String qs_reqField = PropLoad.getTestData("V2Search_qs_reqfield");
	private static String expFieldName_reqField = PropLoad.getTestData("V2Search_expFields_reqfield");

	public V2SearchSingleFacetRequest(LinkedHashMap<String, String> data) {
		this.data = data;
	}

	/**
	 * Functionality to check the multi specific with logical & wildcard term
	 * working as expected for single facet post method
	 */
	public void postMultiSpecificFieldLogic_SearchResponseValidate_SF_SchemaVerification(
			LinkedHashMap<String, String> data_Search) throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		String requestbody = data_Search.get(Constants.reqBody);
		JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
		String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);

		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);
		// 1. Validating the response code
		int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// 2. Validating the response schema
		String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString))
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
	}

	/**
	 * Functionality to check the invalid header value input with post method
	 */

	public void postInvalidValueinHeader(LinkedHashMap<String, String> data_Search) throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		String requestbody = data_Search.get(Constants.reqBody);
		JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
		String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);

		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);
		// 1. Validating the response code
		int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// 2. Validating the Invalid response
		String expInvalidResponse = data_Search.get(Constants.expJsonRes).trim();
		if (!expInvalidResponse.equals(Constants.emptyString))
			ResponseValidator.compareJsonResponse(response, expInvalidResponse);
	}

	/**
	 * Functionality to check the single term & wildcard as expected for
	 * SingleFacet get post method
	 */
	public void getLogicalOperator_SF(LinkedHashMap<String, String> data_Search) throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
		boolean bSuccess ;
		Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);

		// Validating the response code
		int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);
		String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString))
			ResponseValidator.validateJsonSchema(response, expSchemafileName);

		// Validating the search query present in JSON response (MatchedFields)
		String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);

		// Validating em tag in MatchedField
		bSuccess = CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
				ResponseJsonPath.v2SearchSingleFacetMatchedName_JP, response.asString(), querySearchString, false);
		JSONArray arrayValuesFromJson = Template
				.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSingleFacetMatchedName_JP, response.asString());
		if (!bSuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"em tag match validation");
			Assert.fail();
		}
		if (arrayValuesFromJson.size() == 0)
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
		else
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"em tag match validation");
		LoggerUtil.log("Verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
				"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);

		// Verify Field to return present in json
		String[] expFieldnames = Template.getFieldNamesFromKey(data_Search, expFieldName);
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSFProduct_JP);

		// String expgrpSizeCount = Template.getSingleValue(data_Search,
		// "grpSize_qsparam");
		// Verify V2Search Response & Group Size Count
		// Assert.assertTrue(CSGHelper.verifyv2SearchSizeCount(response.asString(),
		// ResponseJsonPath.v2SearchProduct_JP,
		// expgrpSizeCount, "group"));
		// String responseSizeCount = Template.getSingleValue(data_Search,
		// "responseSize_qsparam");
		// Assert.assertTrue(CSGHelper.verifyv2SearchSizeCount(response.asString(),ResponseJsonPath.v2SearchSFProduct_JP,responseSizeCount,"response"));
	}

	/**
	 * Functionality to check the single term & wildcard as expected for
	 * SingleFacet with post method
	 */

	public void postLogicalOperator_SF(LinkedHashMap<String, String> data_Search) throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		String requestbody = data_Search.get(Constants.reqBody);
		JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);

		// Validating the response code
		int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString))
			ResponseValidator.validateJsonSchema(response, expSchemafileName);

		String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);
		String[] expFieldnames = Template.getStringArrayValfromJson(reqBodyJson, expFieldName_reqField);

		// Verify MatchedField Wild card string present with Em tag
		Boolean bSuccess ;
		bSuccess = CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
				ResponseJsonPath.v2SearchSingleFacetMatchedName_JP, response.asString(), querySearchString, false);
		JSONArray arrayValuesFromJson = Template
				.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSingleFacetMatchedName_JP, response.asString());
		if (!bSuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"em tag match validation");
			Assert.fail();
		}
		if (arrayValuesFromJson.size() == 0)
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
		else
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"em tag match validation");
		LoggerUtil.log("Verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
				"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);

		// Verify Field to return present in json
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSFProduct_JP);
	}

	/**
	 * Functionality to check the single specific with logical  term
	 * working as expected for single facet with post method
	 */
	public void postSpecificFielsAndLogicResponseValidate_SF(LinkedHashMap<String, String> data_Search)
			throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		String requestbody = data_Search.get(Constants.reqBody);
		JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);

		// Validating the response code
		int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString))
			ResponseValidator.validateJsonSchema(response, expSchemafileName);

		// Verify Field to return present in json
		String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);
		String[] expFieldnames = Template.getStringArrayValfromJson(reqBodyJson, expFieldName_reqField);

		// Validating the search query present in JSON response
		Map<String, String> specificFieldNames = Template.getSingleSpecficFieldNames(querySearchString);
		boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
				response.asString(), specificFieldNames, ResponseJsonPath.v2SearchSingleFacet_Source_JP, false);
		JSONArray arrayValuesFromJson = Template
				.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSingleFacet_Source_JP, response.asString());
		if (!bSpecsuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			Assert.fail();
		}
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			System.out.println("Verifyv2SearchSpecificField_SearchQueryString_PresentinJSON " + querySearchString);
			LoggerUtil.log("Verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSFProduct_JP);
	}

	/**
	 * Functionality to check the multiple specific with logical & wildcard term
	 * working as expected for single facet with post method
	 */
	public void postMultipleSpecificFieldWildCard_ResponseValidate_SF(LinkedHashMap<String, String> data_Search)
			throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		String requestbody = data_Search.get(Constants.reqBody);
		JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);

		// Validating the response code
		int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString))
			ResponseValidator.validateJsonSchema(response, expSchemafileName);

		String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);
		Map<String, String> specificFieldNames = Template.getMultipleSpecficFieldNames(querySearchString);
		String[] expFieldnames = Template.getStringArrayValfromJson(reqBodyJson, expFieldName_reqField);

		// Validating the search query present in JSON response
		boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
				response.asString(), specificFieldNames, ResponseJsonPath.v2SearchSingleFacet_Source_JP, false);
		JSONArray arrayValuesFromJson = Template
				.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSingleFacet_Source_JP, response.asString());
		if (!bSpecsuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			Assert.fail();
		}
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			System.out.println("Verifyv2SearchSpecificField_SearchQueryString_PresentinJSON " + querySearchString);
			LoggerUtil.log("Verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSFProduct_JP);
	}

	/**
	 * Functionality to check the multiple specific with logical & wildcard term working as expected for single facet with post method
	 */
	public void postMultiSpecificFieldLogic_SearchResponseValidate_SF(LinkedHashMap<String, String> data_Search)
			throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		String requestbody = data_Search.get(Constants.reqBody);
		JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
		String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		String[] expFieldnames = Template.getStringArrayValfromJson(reqBodyJson, expFieldName_reqField);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);
		// Validating the response code
		int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString))
			ResponseValidator.validateJsonSchema(response, expSchemafileName);

		// Verify Specific Field name's wild card data present in json
		boolean bSuccess = CSGHelper.verifyv2SearchMultipleField_SearchQueryString_Logic(response.asString(),
				querySearchString, ResponseJsonPath.v2SearchSingleFacet_Source_JP, false);
		JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMultiFacetSource_JP,
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

			System.out.println("VerifyV2SearchMultipleField_SearchQueryString_Logic " + querySearchString);
			LoggerUtil.log("VerifyV2SearchMultipleField_SearchQueryString_Logic",
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSFProduct_JP);

	}
	
	/**
	 * Functionality to check the logical & wildcard search term working as expected for single facet with get method
	 */

	public void getSpecificFielsAndLogicResponseValidate_SF(LinkedHashMap<String, String> data_Search)
			throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

		Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);

		// Validating the response code
		int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);
		String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);
		// Validating the response schema
		String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString))
			ResponseValidator.validateJsonSchema(response, expSchemafileName);

		// Validating the search query present in JSON response
		Map<String, String> specificFieldNames = Template.getSingleSpecficFieldNames(querySearchString);
		boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
				response.asString(), specificFieldNames, ResponseJsonPath.v2SearchSingleFacet_Source_JP, false);
		JSONArray arrayValuesFromJson = Template
				.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSingleFacet_Source_JP, response.asString());
		if (!bSpecsuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			Assert.fail();
		}
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			System.out.println("Verifyv2SearchSpecificField_SearchQueryString_PresentinJSON " + querySearchString);
			LoggerUtil.log("Verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		String[] expFieldnames = Template.getFieldNamesFromKey(data_Search, expFieldName);
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSFProduct_JP);
	}
	
	/**
	 * Functionality to check the multi specific with logical search term working as expected for single facet with get method
	 */

	public void getMultiSpecificFieldLogic_SearchResponseValidate_SF(LinkedHashMap<String, String> data_Search)
			throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

		Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);

		// Validating the response code
		int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);
		String querySearchString = Template.getSearchQueryString(data_Search, qs_String);

		// Validating the response schema
		String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString))
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);

		// Verify Specific Field name's wild card data present in json
		boolean bSuccess = CSGHelper.verifyv2SearchMultipleField_SearchQueryString_Logic(response.asString(),
				querySearchString, ResponseJsonPath.v2SearchSingleFacet_Source_JP, false);
		JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMultiFacetSource_JP,
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
			System.out.println("Verifyv2SearchMultipleField_SearchQueryString_Logic " + querySearchString);
			LoggerUtil.log("Verifyv2SearchMultipleField_SearchQueryString_Logic",
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		String[] expFieldnames = Template.getFieldNamesFromKey(data_Search, expFieldName);
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSFProduct_JP);

	}

	/**
	 * Functionality to check the multi specific with logical & wildcard search term working as expected for single facet with get method
	 */
	public void getMultipleSpecificFieldWildCard_ResponseValidate_SF(LinkedHashMap<String, String> data_Search)
			throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

		Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);

		// Validating the response code
		int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);
		String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);
		// Validating the response schema
		String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString))
			ResponseValidator.validateJsonSchema(response, expSchemafileName);

		Map<String, String> specificFieldNames = Template.getMultipleSpecficFieldNames(querySearchString);
		// Verify Specific Field name's wild card data present in json
		boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
				response.asString(), specificFieldNames, ResponseJsonPath.v2SearchSingleFacet_Source_JP, false);
		JSONArray arrayValuesFromJson = Template
				.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSingleFacet_Source_JP, response.asString());
		if (!bSpecsuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			Assert.fail();
		}
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			System.out.println("Verifyv2SearchSpecificField_SearchQueryString_PresentinJSON " + querySearchString);
			LoggerUtil.log("Verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		String[] expFieldnames = Template.getFieldNamesFromKey(data_Search, expFieldName);
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSFProduct_JP);

	}

	/**
	 * Functionality to check the multi specific with logical & wildcard & exact pharse search term working as expected for single facet with get method
	 */
	public void getMultipleSpecificFieldWildCard_ResponseValidate_exactPharse_SF(
			LinkedHashMap<String, String> data_Search) throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

		Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);

		// Validating the response code
		int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);
		String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);
		// Validating the response schema
		String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString))
			ResponseValidator.validateJsonSchema(response, expSchemafileName);

		Map<String, String> specificFieldNames = Template.getMultipleSpecficFieldNames_Exact(querySearchString);
		String[] expFieldnames = Template.getFieldNamesFromKey(data_Search, expFieldName);

		// Validating the search query present in JSON response
		boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
				response.asString(), specificFieldNames, ResponseJsonPath.v2SearchSingleFacet_Source_JP, false);
		JSONArray arrayValuesFromJson = Template
				.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSingleFacet_Source_JP, response.asString());
		if (!bSpecsuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			Assert.fail();
		}
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			System.out.println("Verifyv2SearchSpecificField_SearchQueryString_PresentinJSON " + querySearchString);
			LoggerUtil.log("Verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSFProduct_JP);
	}

	/**
	 * Functionality to check the multi specific with logical & wildcard search term working as expected for single facet with Post method
	 */
	public void postMultipleSpecificFieldWildCard_ResponseValidate_exactPharse_SF(
			LinkedHashMap<String, String> data_Search) throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		String requestbody = data_Search.get(Constants.reqBody);
		JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);

		// Validating the response code
		int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString))
			ResponseValidator.validateJsonSchema(response, expSchemafileName);

		String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);
		Map<String, String> specificFieldNames = Template.getMultipleSpecficFieldNames_Exact(querySearchString);
		String[] expFieldnames = Template.getStringArrayValfromJson(reqBodyJson, expFieldName_reqField);

		// Validating the search query present in JSON response
		boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
				response.asString(), specificFieldNames, ResponseJsonPath.v2SearchSingleFacet_Source_JP, false);
		JSONArray arrayValuesFromJson = Template
				.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSingleFacet_Source_JP, response.asString());
		if (!bSpecsuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			Assert.fail();
		}
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			System.out.println("Verifyv2SearchSpecificField_SearchQueryString_PresentinJSON " + querySearchString);
			LoggerUtil.log("Verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSFProduct_JP);

	}

}