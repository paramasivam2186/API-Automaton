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

public class V2SearchMultiFacetRequest extends RESTServiceBase {

	static RESTServiceBase webCredentials_rest = new RESTServiceBase();
	@SuppressWarnings("unused")
	private HashMap<String, String> data;
	String authtoken = "";
	private static String qs_String = PropLoad.getTestData("V2Search_queryString_Excel");
	private static String expFieldName = PropLoad.getTestData("V2Search_expFields_Excel");
	private static String xAuthKey = PropLoad.getTestData("Auth_XAuthKey");
	private static String qs_reqField = PropLoad.getTestData("V2Search_qs_reqfield");
	private static String expFieldName_reqField = PropLoad.getTestData("V2Search_expFields_reqfield");

	public V2SearchMultiFacetRequest(LinkedHashMap<String, String> data) {
		this.data = data;
	}

	public void postLogicalOperator_MF(LinkedHashMap<String, String> data_Search) throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());

		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final String requestbody = data_Search.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		final String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);
		final String[] expFieldnames = Template.getStringArrayValfromJson(reqBodyJson, expFieldName_reqField);

		// Verify MatchedField Wild card string present with Em tag
		final boolean bSuccess = CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
				ResponseJsonPath.v2SearchMultiFacetMatchedName_JP, response.asString(), querySearchString, false);
		final JSONArray arrayValuesFromJson = Template
				.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMultiFacetMatchedName_JP, response.asString());
		if (!bSuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON", "");
		}
		Assert.assertTrue(bSuccess);
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON", "");

			LoggerUtil.log("verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchMultiFacetSource_JP);
	}

	public void postSpecificFielsAndLogicResponseValidate_MF(LinkedHashMap<String, String> data_Search)
			throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final String requestbody = data_Search.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		final String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);
		final String[] expFieldnames = Template.getStringArrayValfromJson(reqBodyJson, expFieldName_reqField);
		// Validating the search query present in JSON response
		final Map<String, String> specificFieldNames = Template.getSingleSpecficFieldNames(querySearchString);

		// Validating the search query present in JSON response
		final boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
				response.asString(), specificFieldNames, ResponseJsonPath.v2SearchMultiFacetSource_JP, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMultiFacetSource_JP,
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
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchMultiFacetSource_JP);

	}

	public void postMultipleSpecificFieldWildCard_ResponseValidate_MF(LinkedHashMap<String, String> data_Search)
			throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final String requestbody = data_Search.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		final String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);
		final Map<String, String> specificFieldNames = Template.getMultipleSpecficFieldNames(querySearchString);
		final String[] expFieldnames = Template.getStringArrayValfromJson(reqBodyJson, expFieldName_reqField);

		// Validating the search query present in JSON response
		final boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
				response.asString(), specificFieldNames, ResponseJsonPath.v2SearchMultiFacetSource_JP, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMultiFacetSource_JP,
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
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}
		// Verify Field to return present in json
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchMultiFacetSource_JP);
	}

	public void postMultiSpecificFieldLogic_SearchResponseValidate_MF(LinkedHashMap<String, String> data_Search)
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
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);
		final String[] expFieldnames = Template.getStringArrayValfromJson(reqBodyJson, expFieldName_reqField);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		// Verify Specific Field name's wild card data present in json
		final boolean bSuccess = CSGHelper.verifyv2SearchMultipleField_SearchQueryString_Logic(response.asString(),
				querySearchString, ResponseJsonPath.v2SearchMultiFacetSource_JP, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMultiFacetSource_JP,
				response.asString());
		if (!bSuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchMultipleField_SearchQueryString_Logic", querySearchString);
		}
		Assert.assertTrue(bSuccess);
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMultipleField_SearchQueryString_Logic",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMultipleField_SearchQueryString_Logic", querySearchString);
		}


		LoggerUtil.log("Verifyv2SearchMultipleField_SearchQueryString_Logic",
				"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);

		// Verify Field to return present in json
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchMultiFacetSource_JP);
	}

	public void getLogicalOperator_MF(LinkedHashMap<String, String> data_Search) throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);

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
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);
		// Verify MatchedField Wild card string present with Em tag
		final boolean bSuccess = CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
				ResponseJsonPath.v2SearchMultiFacetMatchedName_JP, response.asString(), querySearchString, false);
		final JSONArray arrayValuesFromJson = Template
				.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMultiFacetMatchedName_JP, response.asString());
		if (!bSuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON", "");
		}
		Assert.assertTrue(bSuccess);
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON", "");

			LoggerUtil.log("verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		final String[] expFieldnames = Template.getFieldNamesFromKey(data_Search, expFieldName);
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchMultiFacetSource_JP);
	}

	public void getMultipleSpecificFieldWildCard_ResponseValidate_MF(LinkedHashMap<String, String> data_Search)
			throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);
		final Map<String, String> specificFieldNames = Template.getMultipleSpecficFieldNames(querySearchString);
		// Validating the search query present in JSON response
		final boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
				response.asString(), specificFieldNames, ResponseJsonPath.v2SearchMultiFacetSource_JP, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMultiFacetSource_JP,
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
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		final String[] expFieldnames = Template.getFieldNamesFromKey(data_Search, expFieldName);
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchMultiFacetSource_JP);

	}

	public void getSpecificFielsAndLogicResponseValidate_MF(LinkedHashMap<String, String> data_Search)
			throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);
		// Validating the search query present in JSON response
		final Map<String, String> specificFieldNames = Template.getSingleSpecficFieldNames(querySearchString);
		// Validating the search query present in JSON response
		final boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
				response.asString(), specificFieldNames, ResponseJsonPath.v2SearchMultiFacetSource_JP, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMultiFacetSource_JP,
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
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		final String[] expFieldnames = Template.getFieldNamesFromKey(data_Search, expFieldName);
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchMultiFacetSource_JP);
	}

	public void getMultiSpecificFieldLogic_SearchResponseValidate_MF(LinkedHashMap<String, String> data_Search)
			throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);

		// Verify Specific Field name's wild card data present in json
		final boolean bSuccess = CSGHelper.verifyv2SearchMultipleField_SearchQueryString_Logic(response.asString(),
				querySearchString, ResponseJsonPath.v2SearchMultiFacetSource_JP, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMultiFacetSource_JP,
				response.asString());
		if (!bSuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchMultipleField_SearchQueryString_Logic", querySearchString);
		}
		Assert.assertTrue(bSuccess);
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMultipleField_SearchQueryString_Logic",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMultipleField_SearchQueryString_Logic", querySearchString);
		}

		LoggerUtil.log("Verifyv2SearchMultipleField_SearchQueryString_Logic",
				"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);

		// Verify Field to return present in json
		final String[] expFieldnames = Template.getFieldNamesFromKey(data_Search, expFieldName);
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchMultiFacetSource_JP);

	}

	public void getSpecificFielsAndLogicResponseValidate_MF_ExactPharse(LinkedHashMap<String, String> data_Search)
			throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);
		// Validating the search query present in JSON response
		final Map<String, String> specificFieldNames = Template.getSingleSpecficFieldNames_ExactPharse(querySearchString);
		// Validating the search query present in JSON response
		final boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
				response.asString(), specificFieldNames, ResponseJsonPath.v2SearchMultiFacetSource_JP, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMultiFacetSource_JP,
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
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		final String[] expFieldnames = Template.getFieldNamesFromKey(data_Search, expFieldName);
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchMultiFacetSource_JP);

	}

	public void postSpecificFielsAndLogicResponseValidate_MF_ExactPharse(LinkedHashMap<String, String> data_Search)
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
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);
		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		// Validating the search query present in JSON response
		final Map<String, String> specificFieldNames = Template.getSingleSpecficFieldNames_ExactPharse(querySearchString);

		// Validating the search query present in JSON response
		final boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
				response.asString(), specificFieldNames, ResponseJsonPath.v2SearchMultiFacetSource_JP, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMultiFacetSource_JP,
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
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchMultiFacetSource_JP);

	}

	public void getMultipleSpecificFieldWildCard_ResponseValidate_exactPharse_MF(
			LinkedHashMap<String, String> data_Search) throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);
		final Map<String, String> specificFieldNames = Template.getMultipleSpecficFieldNames_Exact(querySearchString);
		final String[] expFieldnames = Template.getFieldNamesFromKey(data_Search, expFieldName);

		// Validating the search query present in JSON response
		final boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
				response.asString(), specificFieldNames, ResponseJsonPath.v2SearchMultiFacetSource_JP, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMultiFacetSource_JP,
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
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchMultiFacetSource_JP);
	}

	public void postMultipleSpecificFieldWildCard_ResponseValidate_exactPharse_MF(
			LinkedHashMap<String, String> data_Search) throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final String requestbody = data_Search.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);
		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}
		final String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		final Map<String, String> specificFieldNames = Template.getMultipleSpecficFieldNames_Exact(querySearchString);
		final String[] expFieldnames = Template.getStringArrayValfromJson(reqBodyJson, expFieldName_reqField);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);
		// Validating the search query present in JSON response
		final boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
				response.asString(), specificFieldNames, ResponseJsonPath.v2SearchMultiFacetSource_JP, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMultiFacetSource_JP,
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
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in json
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchMultiFacetSource_JP);
	}

}