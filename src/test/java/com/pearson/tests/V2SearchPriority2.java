package com.pearson.tests;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.pearson.regression.utilityHelpers.APIHelper;
import com.pearson.regression.utilityHelpers.CSGHelper;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.PropLoad;
import com.pearson.regression.utilityHelpers.RESTServiceBase;
import com.pearson.regression.utilityHelpers.ReportHelper;
import com.pearson.regression.utilityHelpers.ResponseValidator;
import com.pearson.regression.utilityHelpers.Template;
import com.relevantcodes.extentreports.LogStatus;

import net.minidev.json.JSONObject;

public class V2SearchPriority2 extends BaseTest {

	LinkedHashMap<String, LinkedHashMap<String, String>> hashMap = null;
	private RESTServiceBase webCredentials_rest = new RESTServiceBase();
	String TestDescription = Constants.emptyString;
	String methodName = Constants.emptyString;
	private Assertion as = new Assertion();
	String authtoken = "";
	private static String xAuthKey = PropLoad.getTestData("Auth_XAuthKey");

	// private static String qs_reqField =
	// PropLoad.getTestData("V2Search_qs_reqfield");
	// private static String expFieldName =
	// PropLoad.getTestData("V2Search_expFields_Excel");
	// private static String qs_String =
	// PropLoad.getTestData("V2Search_queryString_Excel");

	@Test(dataProvider = "dp")
	public void Valid_value_In_Header_012(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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

			final String Countreturned = APIHelper.retriveValuefromJson("$..count", response.getBody().asString());
			if(Countreturned.isEmpty()){
				as.assertFalse(Countreturned.isEmpty());
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is Failed");
			}else{
				as.assertFalse(Countreturned.isEmpty());
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Invalid_value_In_Header_02(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = "InvalidTokenKey";
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final String code = APIHelper.retriveValuefromJson("$..code", response.getBody().asString());
			if(code.contains("401-UNAUTHORIZED")){
				as.assertTrue(code.contains("401-UNAUTHORIZED"));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(code.contains("401-UNAUTHORIZED"));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is Failed");
			}		
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
	public void Invalid_value_In_Header_03(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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

			final String code = APIHelper.retriveValuefromJson("$..status", response.getBody().asString());
			if(code.contains("error")){
				as.assertTrue(code.contains("error"));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(code.contains("error"));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is Failed");
			}	
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
	public void Invalid_value_In_Header_04(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			authtoken = "InvalidTokenKey";
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final String code = APIHelper.retriveValuefromJson("$..code", response.getBody().asString());
			if(code.contains("401-UNAUTHORIZED")){
				as.assertTrue(code.contains("401-UNAUTHORIZED"));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(code.contains("401-UNAUTHORIZED"));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is Failed");
			}
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
	public void Exact_Phrase_Specific_Field_Search_53(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);

			final List<String> fieldToreturn = APIHelper.FeildToReturnasArray("$.fieldsToReturn", responseBody);

			final String expectedsearch = APIHelper.pullQueryStringExactPhraseforSpecificField("$..queryString",
					responseBody);

			final JSONObject json = Template.convertStringtoJsonObject(responseBody);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			final String schemaname = APIHelper.retriveValuefromJson("$..source..schema:familyName",
					response.getBody().asString());
			if(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn) && schemaname.contains(expectedsearch)){
				as.assertTrue(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn));
				as.assertTrue(schemaname.contains(expectedsearch));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");	
			}else{
				as.assertTrue(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn));
				as.assertTrue(schemaname.contains(expectedsearch));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is Failed");	
			}
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Specific_Field_Searches_1(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);

			final List<String> fieldToreturn = APIHelper.FeildToReturnasArray("$.fieldsToReturn", responseBody);

			final JSONObject json = Template.convertStringtoJsonObject(responseBody);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			if(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn)){
				as.assertTrue(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");	
			}else{
				as.assertTrue(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is Failed");
			}

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void default_groupSize_SingleLevelFacet_02(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);
			if(10==APIHelper.groupSize("$..searchResults..productsList", response.getBody().asString())){
				as.assertEquals(10, APIHelper.groupSize("$..searchResults..productsList", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertEquals(10, APIHelper.groupSize("$..searchResults..productsList", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is Failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Verify_Normal_Search_default_Response_01(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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

			final String Countreturned = APIHelper.retriveValuefromJson("$..count", response.getBody().asString());
			if(Countreturned.isEmpty()){
			as.assertFalse(Countreturned.isEmpty());
			LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
			ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");}
			else{
				as.assertFalse(Countreturned.isEmpty());
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Verify_Normal_Search_default_Response_01_01(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			final List<String> fieldToreturn = APIHelper.FeildToReturnasArray("$.fieldsToReturn", responseBody);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			if(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn)){
				as.assertTrue(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");	
			}else{
				as.assertTrue(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is Failed");
			}
		

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Validate_source_application_specific_field(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			final List<String> fieldToreturn = APIHelper.FeildToReturnasArray("$.fieldsToReturn", responseBody);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			if(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn)){
				as.assertTrue(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is Failed");
			}
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Validate_permissions_application_specific_field(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			final List<String> fieldToreturn = APIHelper.FeildToReturnasArray("$.fieldsToReturn", responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			if(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn)){
				as.assertTrue(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is Failed");
			}
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Validate__providerSystemname_application_specific_field(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			final List<String> fieldToreturn = APIHelper.FeildToReturnasArray("$.fieldsToReturn", responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			if(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn)){
				as.assertTrue(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is Failed");
			}
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Validate_availability_application_specific_field(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			final List<String> fieldToreturn = APIHelper.FeildToReturnasArray("$.fieldsToReturn", responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			if(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn)){
				as.assertTrue(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");	
			}else{
				as.assertTrue(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Validate_price_application_specific_field(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			final List<String> fieldToreturn = APIHelper.FeildToReturnasArray("$.fieldsToReturn", responseBody);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			if(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn)){
				as.assertTrue(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}


		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Validate_priceCurrency_application_specific_field(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			final List<String> fieldToreturn = APIHelper.FeildToReturnasArray("$.fieldsToReturn", responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			if(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn)){
				as.assertTrue(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Validate_MultiLevel_Facet_with_source(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			final List<String> fieldToreturn = APIHelper.FeildToReturnasArray("$.fieldsToReturn", responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			final String auxiliaryFields = APIHelper.retriveValuefromJson("$..cnt:auxiliaryFields",
					response.getBody().asString());
			final String aggregationBuckets = APIHelper.retriveValuefromJson("$..aggregationBuckets..count",
					response.getBody().asString());
			
			
			if(!aggregationBuckets.isEmpty() && !auxiliaryFields.isEmpty() && APIHelper.validateFieldToreturnForPOST(response, fieldToreturn)){
				as.assertTrue(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn));
				as.assertFalse(auxiliaryFields.isEmpty());
				as.assertFalse(aggregationBuckets.isEmpty());
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn));
				as.assertFalse(auxiliaryFields.isEmpty());
				as.assertFalse(aggregationBuckets.isEmpty());
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Validate_MultiLevel_Facet_with_App(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(responseBody);

			final List<String> fieldToreturn = APIHelper.FeildToReturnasArray("$.fieldsToReturn", responseBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			final String auxiliaryFields = APIHelper.retriveValuefromJson("$..cnt:auxiliaryFields",
					response.getBody().asString());
			final String aggregationBuckets = APIHelper.retriveValuefromJson("$..aggregationBuckets..count",
					response.getBody().asString());
			
		
			if(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn) && !auxiliaryFields.isEmpty() && !aggregationBuckets.isEmpty()){
				as.assertTrue(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn));
				as.assertFalse(auxiliaryFields.isEmpty());	
				as.assertFalse(aggregationBuckets.isEmpty());
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(APIHelper.validateFieldToreturnForPOST(response, fieldToreturn));
				as.assertFalse(auxiliaryFields.isEmpty());	
				as.assertFalse(aggregationBuckets.isEmpty());
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Validate_multilevel_facetwith_offersfield(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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

			final String auxiliaryFields = APIHelper.retriveValuefromJson("$..count", response.getBody().asString());
			if(!auxiliaryFields.isEmpty()){
				as.assertFalse(auxiliaryFields.isEmpty());
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertFalse(auxiliaryFields.isEmpty());
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Validate_multilevel_facetwith_offersfield_02(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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
			final String aggregationBuckets = APIHelper.retriveValuefromJson("$..aggregationBuckets",
					response.getBody().asString());
			final String key = APIHelper.retriveValuefromJson("$..key", response.getBody().asString());
			if(!key.isEmpty() && aggregationBuckets.contains("[]")){
				as.assertFalse(key.isEmpty());
				as.assertTrue(aggregationBuckets.contains("[]"));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertFalse(key.isEmpty());
				as.assertTrue(aggregationBuckets.contains("[]"));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void SingleFieldSorting_with_appSpecificAttr_01(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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

			final Boolean ASCSortKeySorted = APIHelper.IsJsonObjectSortedAsc("$..sort", response.getBody().asString());
			final Boolean returnedSortedValue = APIHelper.IsJsonObjectSortedAsc("$.._providerSystem..name",
					response.getBody().asString());
			if(ASCSortKeySorted && returnedSortedValue){
				as.assertTrue(ASCSortKeySorted);
				as.assertTrue(returnedSortedValue);
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(ASCSortKeySorted);
				as.assertTrue(returnedSortedValue);
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
		
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void MultiFieldSorting_with_appSpecificAttr_02(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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

			final Boolean ASCKeySorted1array = APIHelper.IsJsonObjectSortedAsc("$..sort[0]", response.getBody().asString());
			final Boolean ASCKeySorted2array = APIHelper.IsJsonObjectSortedAsc("$..sort[1]", response.getBody().asString());
			final Boolean returnedSortedValue = APIHelper.IsJsonObjectSortedAsc("$.._providerSystem..name",
					response.getBody().asString());
			if(ASCKeySorted1array && ASCKeySorted2array &&  returnedSortedValue){
				as.assertTrue(ASCKeySorted1array);
				as.assertTrue(ASCKeySorted2array);
				as.assertTrue(returnedSortedValue);
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(ASCKeySorted1array);
				as.assertTrue(ASCKeySorted2array);
				as.assertTrue(returnedSortedValue);
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Sorting_count_ASC_SingleLevelFacet_08(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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

			final Boolean ASCSortKeySorted = APIHelper.IsJsonObjectSortedAsc("$..searchResults..key",
					response.getBody().asString());
			if(ASCSortKeySorted){
				as.assertTrue(ASCSortKeySorted);
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(ASCSortKeySorted);
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Sorting_count_DESC_SingleLevelFacet_09(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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

			final Boolean ASCSortKeySorted = APIHelper.IsJsonObjectSortedAsc("$..searchResults..key",
					response.getBody().asString());
			
			if(ASCSortKeySorted){
				as.assertTrue(ASCSortKeySorted);
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(ASCSortKeySorted);
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Default_Sorting_Order_SingleLevelFacet_01(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);

			APIHelper.validaStatusCode(response, data_Search);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);

			// Default Search will check the count is descending
			if(!APIHelper.IsJsonObjectSortedAsc("$..count", response.getBody().asString())){
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..count", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..count", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Default_Sorting_Order_SingleLevelFacet_01_post(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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

			// Default Search will check the count is descending
			if(!APIHelper.IsJsonObjectSortedAsc("$..count", response.getBody().asString())){
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..count", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..count", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Sorting_term_ASC_MultiLevelFacet_15(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);

			APIHelper.validaStatusCode(response, data_Search);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			if(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()) && APIHelper.MultiPhaseSort(".searchResults[*].aggregationBuckets[0].key",
					response.getBody().asString())){
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()));
				as.assertTrue(APIHelper.MultiPhaseSort(".searchResults[*].aggregationBuckets[0].key",
						response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");	
			}else{
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()));
				as.assertTrue(APIHelper.MultiPhaseSort(".searchResults[*].aggregationBuckets[0].key",
						response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Sorting_term_ASC_MultiLevelFacet_15_post(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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
			
			if(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()) && APIHelper.MultiPhaseSort(".searchResults[*].aggregationBuckets[0].key",
					response.getBody().asString())){
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()));
				as.assertTrue(APIHelper.MultiPhaseSort(".searchResults[*].aggregationBuckets[0].key",
						response.getBody().asString()));

				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()));
				as.assertTrue(APIHelper.MultiPhaseSort(".searchResults[*].aggregationBuckets[0].key",
						response.getBody().asString()));

				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Sorting_term_DESC_MultiLevelFacet_16(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);

			APIHelper.validaStatusCode(response, data_Search);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			if(!APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()) && !APIHelper.MultiPhaseSort(".searchResults[*].aggregationBuckets[0].key",
					response.getBody().asString())){
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()));
				as.assertFalse(APIHelper.MultiPhaseSort(".searchResults[*].aggregationBuckets[0].key",
						response.getBody().asString()));

				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()));
				as.assertFalse(APIHelper.MultiPhaseSort(".searchResults[*].aggregationBuckets[0].key",
						response.getBody().asString()));

				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Sorting_term_DESC_MultiLevelFacet_16_post(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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
			if(!APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString())){
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");	
			}else{
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");	
			}
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Sorting_count_DESC_MultiLevelFacet_17(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);

			APIHelper.validaStatusCode(response, data_Search);
			if(!APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()) && !APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].count", response.getBody().asString())){
				LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()));
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].count", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");	
			}else{
				LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()));
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].count", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
		

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Sorting_count_DESC_MultiLevelFacet_17_post(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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
			if(!APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()) && !APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].count", response.getBody().asString())){
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()));
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].count", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()));
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].count", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Sorting_count_ASC_MultiLevelFacet_17(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);

			APIHelper.validaStatusCode(response, data_Search);
			
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			if(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()) && APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].count", response.getBody().asString())){
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].count", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");	
			}else{
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].count", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");	
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	public void Sorting_count_ASC_MultiLevelFacet_17_post(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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
			if(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()) && APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].count", response.getBody().asString())){
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].count", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].key", response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].count", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void SingleFieldSorting_with_LogicalOperators_03(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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
			if(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].sort", response.getBody().asString()) && APIHelper.IsJsonObjectSortedAsc("$.searchResults[*].source.cnt:auxiliaryFields._permissions",
					response.getBody().asString())){
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].sort", response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$.searchResults[*].source.cnt:auxiliaryFields._permissions",
						response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].sort", response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$.searchResults[*].source.cnt:auxiliaryFields._permissions",
						response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void SingleFieldSorting_with_SpecialCharacters_04(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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
			if(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].sort", response.getBody().asString()) && APIHelper.IsJsonObjectSortedAsc("$.searchResults[*].source.cnt:auxiliaryFields._permissions",
					response.getBody().asString())){
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].sort", response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$.searchResults[*].source.cnt:auxiliaryFields._permissions",
						response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");	
			}else{
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].sort", response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$.searchResults[*].source.cnt:auxiliaryFields._permissions",
						response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");	
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void SingleFieldSorting_with_Wildcard_05(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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
			if(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].sort", response.getBody().asString()) && APIHelper.IsJsonObjectSortedAsc("$.searchResults[*].source.cnt:auxiliaryFields._permissions",
					response.getBody().asString())){
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].sort", response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$.searchResults[*].source.cnt:auxiliaryFields._permissions",
						response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].sort", response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$.searchResults[*].source.cnt:auxiliaryFields._permissions",
						response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void SingleFieldSorting_with_LogicalOperators_06(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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
			if(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].sort", response.getBody().asString()) && APIHelper.IsJsonObjectSortedAsc("$.searchResults[*].source.cnt:auxiliaryFields._permissions",
					response.getBody().asString())){
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].sort", response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$.searchResults[*].source.cnt:auxiliaryFields._permissions",
						response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].sort", response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$.searchResults[*].source.cnt:auxiliaryFields._permissions",
						response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void MultiFieldSorting_with_LogicalOperators_07(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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

			// Need to verify
			// as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*]..sort[0]",
			// response.getBody().asString()));
			if(APIHelper.IsJsonObjectSortedAsc(
					"$.searchResults[*].source.cnt:auxiliaryFields._permissions[0]", response.getBody().asString())){
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc(
						"$.searchResults[*].source.cnt:auxiliaryFields._permissions[0]", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc(
						"$.searchResults[*].source.cnt:auxiliaryFields._permissions[0]", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Sorting_term_ASC_SingleLevelFacet_06_post(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			// Excel value - Pull all data from excel for this current method

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

			// Need to verify
			// as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*]..sort[0]",
			// response.getBody().asString()));
			if(APIHelper.IsJsonObjectSortedAsc("$..key", response.getBody().asString())){
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..key", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..key", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Sorting_term_DESC_SingleLevelFacet_07_post(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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

			// Need to verify
			// as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*]..sort[0]",
			// response.getBody().asString()));
			if(!APIHelper.IsJsonObjectSortedAsc("$..key", response.getBody().asString())){
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..key", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");	
			}else{
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..key", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Default_Sorting_Order_MultiLevelFacet_10_post(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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

			// Need to verify
			// as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*]..sort[0]",
			// response.getBody().asString()));
			if(!APIHelper.IsJsonObjectSortedDesc("$..searchResults[*].count", response.getBody().asString())){
				as.assertFalse(
						APIHelper.IsJsonObjectSortedDesc("$..searchResults[*].count", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertFalse(
						APIHelper.IsJsonObjectSortedDesc("$..searchResults[*].count", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Sorting_count_DESC_MultiLevelFacet_18_post(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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

			// Need to verify
			// as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*]..sort[0]",
			// response.getBody().asString()));
			if(APIHelper.IsJsonObjectSortedAsc("$..sort", response.getBody().asString())){
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..sort", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..sort", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void MultiFieldSorting_with_SpecialCharacters_08(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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

			// Need to verify
			// as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*]..sort[0]",
			// response.getBody().asString()));
			if(APIHelper.IsJsonObjectSortedAsc("$..sort[0]", response.getBody().asString())){
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..sort[0]", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..sort[0]", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void MultiFieldSorting_with_Wildcard_09(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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
			if(APIHelper.IsJsonObjectSortedAsc("$..sort[0]", response.getBody().asString())){
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..sort[0]", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..sort[0]", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void MultiFieldSorting_with_LogicalOperators_10(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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

			// Need to verify
			// as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*]..sort[0]",
			// response.getBody().asString()));
			if(APIHelper.IsJsonObjectSortedAsc("$..sort[0]", response.getBody().asString())){
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..sort[0]", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..sort[0]", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Sorting_appSpecificStandardMetadata_11(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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

			// Need to verify
			// as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*]..sort[0]",
			// response.getBody().asString()));
			if(APIHelper.IsJsonObjectSortedAsc("$..sort[0]", response.getBody().asString())){
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..sort[0]", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");	
			}else{
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..sort[0]", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Sorting_term_ASC_SingleLevelFacet_06(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);

			APIHelper.validaStatusCode(response, data_Search);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			if(APIHelper.IsJsonObjectSortedAsc("$..key", response.getBody().asString())){
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..key", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..key", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Sorting_term_DESC_SingleLevelFacet_07(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);

			APIHelper.validaStatusCode(response, data_Search);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			if(!APIHelper.IsJsonObjectSortedAsc("$..key", response.getBody().asString())){
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..key", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..key", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Default_Sorting_Order_MultiLevelFacet_10(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);

			APIHelper.validaStatusCode(response, data_Search);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			
			if(!APIHelper.IsJsonObjectSortedAsc("$..searchResults..key", response.getBody().asString())){
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..searchResults..key", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..searchResults..key", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Sorting_count_DESC_MultiLevelFacet_18(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);

			APIHelper.validaStatusCode(response, data_Search);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			
			if(!APIHelper.IsJsonObjectSortedAsc("$..searchResults..count", response.getBody().asString())){
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..searchResults..count", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertFalse(APIHelper.IsJsonObjectSortedAsc("$..searchResults..count", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Sorting_appSpecific_StandardMetadata_11(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);

			APIHelper.validaStatusCode(response, data_Search);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			if(APIHelper.IsJsonObjectSortedAsc("$..sort[0]", response.getBody().asString())){
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..sort[0]", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..sort[0]", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Without_QueryParam_10(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);

			APIHelper.validaStatusCode(response, data_Search);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);

			final String errorCode = APIHelper.retriveValuefromJson("$..errorCode", response.getBody().asString());
			if("400".equals(errorCode)){
				as.assertEquals(errorCode, "400");
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertEquals(errorCode, "400");
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Without_Payload_10(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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

			final String errorCode = APIHelper.retriveValuefromJson("$..errorCode", response.getBody().asString());
			if("400".equals(errorCode)){
				as.assertEquals(errorCode, "400");
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertEquals(errorCode, "400");
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void default_groupSize_MultiLevelFacet_07(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);
			if(20==APIHelper.groupSize("$.searchResults[0].aggregationBuckets[0].productsList[*].productId",
							response.getBody().asString())){
				as.assertEquals(20,
						APIHelper.groupSize("$.searchResults[0].aggregationBuckets[0].productsList[*].productId",
								response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");	
			}else{
				as.assertEquals(20,
						APIHelper.groupSize("$.searchResults[0].aggregationBuckets[0].productsList[*].productId",
								response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void groupSize_as_zero_MultiLevelFacet_06(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);
			LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void default_groupSize_SingleLevelFacet_02_post(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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

			// System.out.println("&&&&&&&&&&&&&&&&&&"+APIHelper.groupSize("$..searchResults..key",
			// response.getBody().asString()));
			if(20==APIHelper.groupSize("$..searchResults..key", response.getBody().asString())){
				as.assertEquals(20, APIHelper.groupSize("$..searchResults..key", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertEquals(20, APIHelper.groupSize("$..searchResults..key", response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void groupSize_as_zero_MultiLevelFacet_06_post(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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
			LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void default_groupSize_MultiLevelFacet_07_post(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
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

			// System.out.println("&&&&&&&&&&&&&&&&&&"+APIHelper.groupSize("$..searchResults..key",
			// response.getBody().asString()));
			if(20==APIHelper.groupSize("$..searchResults[0].aggregationBuckets[0].productsList[*].productId",
							response.getBody().asString())){
				as.assertEquals(20,
						APIHelper.groupSize("$..searchResults[0].aggregationBuckets[0].productsList[*].productId",
								response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertEquals(20,
						APIHelper.groupSize("$..searchResults[0].aggregationBuckets[0].productsList[*].productId",
								response.getBody().asString()));
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void set_size_of_resources_or_product_within_eachGroup_SingleLevelFacet_03(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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

			if (APIHelper.groupSize("$..searchResults[*].productsList[0].productId",
					response.getBody().asString()) <= 25) {
				as.assertTrue(true);
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			} else {
				as.assertTrue(false);
			}
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void set_size_of_resources_product_within_eachGroup_MultiLevelFacet_08(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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

			if (APIHelper.groupSize("$..searchResults[*].productsList[0].productId",
					response.getBody().asString()) <= 25) {
				as.assertTrue(true);
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			} else {
				as.assertTrue(false);
			}
			    LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
			    ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void set_size_of_resources_or_product_within_eachGroup_SingleLevelFacet_03_post(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);

			if (APIHelper.groupSize("$..searchResults[*].productsList[0].productId",
					response.getBody().asString()) <= 25) {
				as.assertTrue(true);
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			} else {
				as.assertTrue(false);
			}
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void set_size_of_resources_product_within_eachGroup_MultiLevelFacet_08_post(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);

			if (APIHelper.groupSize("$..searchResults[*].productsList[0].productId",
					response.getBody().asString()) <= 25) {
				as.assertTrue(true);
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			} else {
				as.assertTrue(false);
			}
			LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
			ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	// This is Notebook and we need ingestion in QA environment
	@Test(dataProvider = "dp")
	public void Valid_indexType_01(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final String requestId = APIHelper.retriveValuefromJson("$..requestId", response.getBody().asString());
			if(!requestId.isEmpty()){
				as.assertFalse(requestId.isEmpty());
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertFalse(requestId.isEmpty());
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");

			}
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Valid_invalid_indexType_02(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final String count = APIHelper.retriveValuefromJson("$..count", response.getBody().asString());
			if("0".equals(count)){
				as.assertEquals(count, "0");
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertEquals(count, "0");
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Valid_null_as_indexType_03(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final String errorCode = APIHelper.retriveValuefromJson("$..errorCode", response.getBody().asString());
			if("400".equals(errorCode)){
				as.assertEquals(errorCode, "400");
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertEquals(errorCode, "400");
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void without_searchString_13(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final String errorCode = APIHelper.retriveValuefromJson("$..errorCode", response.getBody().asString());
			if("400".equals(errorCode)){
				as.assertEquals(errorCode, "400");
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertEquals(errorCode, "400");
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void groupSize_as_zero_FacetSearch_09(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final String count = APIHelper.retriveValuefromJson("$..count", response.getBody().asString());
			final String ProductId = APIHelper.retriveValuefromJson("$..productId", response.getBody().asString());
			final String key = APIHelper.retriveValuefromJson("$..key", response.getBody().asString());
			
			if(!key.isEmpty() && !count.isEmpty() && "".equals(ProductId)){
				as.assertFalse(key.isEmpty());
				as.assertFalse(count.isEmpty());
				as.assertEquals(ProductId, null);
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");	
			}else{
				as.assertFalse(key.isEmpty());
				as.assertFalse(count.isEmpty());
				as.assertEquals(ProductId, null);
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");	
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void SpecificField_or_appSpecificField_Search_04(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);
			LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void SingleLevel_Faceting_07(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final String key = APIHelper.retriveValuefromJson("$..searchResults..key", response.getBody().asString());
			final String tags = APIHelper.retriveValuefromJson("$..tags", response.getBody().asString());
			
			if(key.equals(tags)){
				as.assertEquals(key, tags);
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");	
			}else{
				as.assertEquals(key, tags);
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");	
			}
				
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void MultiLevel_Faceting_08(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final String key = APIHelper.retriveValuefromJson("$.searchResults[*].key", response.getBody().asString());
			final String tags = APIHelper.retriveValuefromJson("$..tags", response.getBody().asString());

			final String aggregationBucketskey = APIHelper.retriveValuefromJson("$..aggregationBuckets..key",
					response.getBody().asString());
			final String promptId = APIHelper.retriveValuefromJson("$..promptId", response.getBody().asString());
			if(key.equals(tags) && aggregationBucketskey.equals(promptId)){
				as.assertEquals(key, tags);
				as.assertEquals(aggregationBucketskey, promptId);
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");	
			}else{
				as.assertEquals(key, tags);
				as.assertEquals(aggregationBucketskey, promptId);
				LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");	
			}
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void fieldsToReturn_06(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);
			LoggerUtil.log(methodName, "STATUS", Constants.testStep, null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	// @Test(dataProvider ="dp")
	// public void Default_Sorting_Order_singleFacet_10_post() {
	// try {
	// // Excel value - Pull all data from excel for this current method
	// hashMap = ExcelRead.getData(this.getClass().getSimpleName());
	// LinkedHashMap<String, String> data_Search = new LinkedHashMap<String,
	// String>();
	// String methodName = new Object() {
	// }.getClass().getEnclosingMethod().getName();
	// data_Search = hashMap.get(methodName);
	//
	// ctx.getCurrentXmlTest().addParameter("Testcase_Name",data_Search.get("Testcase_Name").trim());ctx.getCurrentXmlTest().addParameter("Testcase_ID",data_Search.get("Testcase_ID").trim());String
	// URI = APIHelper.URIgenerator_CSG(data_Search);
	// String responseBody = APIHelper.GetrequestBody(data_Search);
	// JSONObject json = Template.convertStringtoJsonObject(responseBody);
	//
	// authtoken = CSGHelper.generate_PI_AuthToken();
	//
	// Map<String, String> headers = Template.getRequestData(data_Search,
	// Constants.header);
	// headers.put(xAuthKey, authtoken);
	//
	// Response response =
	// webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
	// APIHelper.validaStatusCode(response, data_Search);
	//
	// //Need to verify
	// //as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*]..sort[0]",
	// response.getBody().asString()));
	// as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults[*].count",
	// response.getBody().asString()));
	// LoggerUtil.log(methodName,"STATUS", Constants.testStep, null);
	//
	// } catch (Exception e) {
	// LoggerUtil.log("Test Case Failed", "" + e.getMessage() +
	// Constants.testStep);
	// e.getMessage();
	// e.printStackTrace();
	// Assert.fail(e.getMessage(), e);
	// }
	// }
	/*
	 * @Test() public void stringverification() { ArrayList<Integer> elements =
	 * new ArrayList<>();
	 * 
	 * elements.add(10); elements.add(35); elements.add(19); int n = 0; boolean
	 * isSorted = true; for (int i = 0; i < elements.size()-1; i++) { // current
	 * String is > than the next one (if there are equal list is // still
	 * sorted) n=elements.get(0).toString().compareTo(elements.get(i +
	 * 1).toString()); if (n > 0) { isSorted = false; break; } }
	 * System.out.println("*******value******"+n); }
	 */

}
