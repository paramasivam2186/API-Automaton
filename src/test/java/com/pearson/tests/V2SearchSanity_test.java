package com.pearson.tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.google.common.collect.Ordering;
import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.pearson.pageobjects.V2SearchSanity;
import com.pearson.regression.utilityHelpers.APIHelper;
import com.pearson.regression.utilityHelpers.CSGHelper;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.PropLoad;
import com.pearson.regression.utilityHelpers.RESTServiceBase;
import com.pearson.regression.utilityHelpers.ResponseJsonPath;
import com.pearson.regression.utilityHelpers.Template;

import net.minidev.json.JSONObject;

public class V2SearchSanity_test extends BaseTest{

	private RESTServiceBase webCredentials_rest = new RESTServiceBase();
	String authtoken = "";
	String TestDescription = Constants.emptyString;
	private static String xAuthKey = PropLoad.getTestData("Auth_XAuthKey");
	private static String qs_String = PropLoad.getTestData("V2Search_queryString_Excel");
	private static String qs_reqField = PropLoad.getTestData("V2Search_qs_reqfield");

	@Test(dataProvider = "dp")
	public void get_SingleSearch1_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch2_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch3_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch4_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch5_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch6_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch1_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch2_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch3_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch4_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch5_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch6_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch1_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch2_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch3_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch4_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch5_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch6_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch1_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch2_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch3_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch4_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch5_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch6_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch1_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch2_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch3_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch4_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch5_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch6_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch1_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch2_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch3_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch4_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch5_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch6_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch1_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch2_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch3_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch4_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch5_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch6_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch1_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch2_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch3_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch4_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch5_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch6_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void get_SingleSearch1_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch2_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch3_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch4_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch5_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch6_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch1_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch2_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch3_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch4_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch5_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch6_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	
	@Test(dataProvider = "dp")
	public void get_SingleSearch1_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch2_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch3_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch4_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch5_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SingleSearch6_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedField_get(response, ".searchResults[*].matchedFields", parameters);
			
			final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch1_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch2_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch3_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch4_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch5_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SingleSearch6_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, responseBody);
			//v2.emValidationMatchedField_post(response, ".searchResults[*].matchedFields", responseBody);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search1_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search2_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search3_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search4_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search5_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search6_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search1_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search2_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search3_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search4_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search5_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search6_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search1_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search2_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search3_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search4_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search5_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search6_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search1_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search2_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search3_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search4_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search5_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search6_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search1_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search2_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search3_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search4_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search5_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search6_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search1_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search2_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search3_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search4_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search5_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search6_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search1_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search2_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search3_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search4_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search5_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search6_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search1_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search2_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search3_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search4_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search5_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search6_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	

	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search1_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search2_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search3_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search4_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search5_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search6_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search1_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search2_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search3_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search4_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search5_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search6_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search1_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search2_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search3_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search4_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search5_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_LogicalOperator_Search6_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_logicalOperatorValidation(response, parameters);	
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search1_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search2_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search3_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search4_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search5_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_LogicalOperator_Search6_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.post_logicalOperatorValidation(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search1_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search2_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search3_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search4_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search5_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search6_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search1_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search2_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search3_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search4_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search5_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search6_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search1_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search2_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search3_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search4_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search5_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search6_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search1_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search2_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search3_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search4_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search5_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search6_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search1_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search2_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search3_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search4_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search5_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search6_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search1_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search2_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search3_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search4_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search5_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search6_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search1_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search2_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search3_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search4_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search5_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void get_SpecificField_Search6_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

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
						
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_get(response, parameters);v2.get_specificField(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search1_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search2_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search3_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search4_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search5_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_SpecificField_Search6_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response); v2.fieldtoReturn_post(response, requestBody);v2.post_specificField(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting1_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting2_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting3_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting4_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting5_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting6_exchange(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting1_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting2_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting3_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting4_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting5_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting6_exchangeV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting1_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting2_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting3_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting4_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting5_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting6_exchangeV3(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting1_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting2_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting3_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting4_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting5_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting6_exchangeDiscipline(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting1_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting2_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting3_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting4_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting5_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting6_exchangeDisciplineV2(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting1_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String reqURL = data_Search.get(PropLoad.getTestData("API_reqURL_Excel")).trim();
			
			//String reqURL = data_Search.get(PropLoad.getTestData("API_reqURL_Excel")).trim();
			//System.out.println("***************************************" + reqURL);
			
			/*String URI = APIHelper.URIgenerator_CSG(data_Search);
			String requestBody = APIHelper.GetrequestBody(data_Search);
			JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));*/
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting2_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			
			final String reqURL = data_Search.get(PropLoad.getTestData("API_reqURL_Excel")).trim();
			
			/*String URI = APIHelper.URIgenerator_CSG(data_Search);
			String requestBody = APIHelper.GetrequestBody(data_Search);
			JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));*/
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting3_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting4_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting5_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
					
			final V2SearchSanity v2 = new V2SearchSanity();			
			v2.returnCountV2Search_PostorGet(response);v2.fieldtoReturn_post(response, requestBody);Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void post_Sorting6_collection(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.singleFacet_validation(response, requestBody);
			final List<String> getAlley = APIHelper.FeildToReturnasArray("$..searchResults[*]..key", response.getBody().asString());
			final Boolean sorted = Ordering.natural().reverse().isOrdered(getAlley);
			
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void test_method(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final FileReader fr=new FileReader("d:\\sample1.txt");
			final BufferedReader br= new BufferedReader(fr);
			final String x="";
//			while ((x=br.readLine()) != null)
//			{
//			System.out.println(x +"\n");
//			}
			br.close();
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	
	
}
