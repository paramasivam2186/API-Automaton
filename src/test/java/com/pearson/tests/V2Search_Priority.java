package com.pearson.tests;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
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

public class V2Search_Priority extends BaseTest {

	LinkedHashMap<String, LinkedHashMap<String, String>> hashMap = null;
	private RESTServiceBase webCredentials_rest = new RESTServiceBase();
	private Assertion as = new Assertion();
	String authtoken = "";
	private static String xAuthKey = PropLoad.getTestData("Auth_XAuthKey");

	// JSONElement to Be used for V2 search
	private static String schemanameJsonPath = PropLoad.getTestData("schemaname");

	@Test(dataProvider = "dp")
	public void Specific_Field_Search_Exact_Pharse_with_SingleLevelFacet_10(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			final String searchContent = APIHelper.getCallexactPharseSplitter(data_Search);
			final String schemaname = APIHelper.retriveValuefromJson(schemanameJsonPath, response.getBody().asString());
			final String keyValue = APIHelper.retriveValuefromJson("$..key", response.getBody().asString());
			if(keyValue.contains(searchContent) && schemaname.contains(searchContent)){
				as.assertTrue(keyValue.contains(searchContent));
				as.assertTrue(schemaname.contains(searchContent));
				LoggerUtil.log("schemaname", schemaname, Constants.subStep, null);				
				LoggerUtil.log("keyValue", keyValue, Constants.subStep, null);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);	
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(keyValue.contains(searchContent));
				as.assertTrue(schemaname.contains(searchContent));
				LoggerUtil.log("schemaname", schemaname, Constants.subStep, null);				
				LoggerUtil.log("keyValue", keyValue, Constants.subStep, null);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);	
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
	public void groupSize_as_zero_SingleLevelFacet_01(Method m, LinkedHashMap<String, String> data_Search,
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
			final String returnedValue = APIHelper.retriveValuefromJson("$..productsList", response.getBody().asString());
			
			if("".equals(returnedValue)){
				as.assertEquals(null, returnedValue);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertEquals(null, returnedValue);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
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
	public void Valid_value_In_Header_01(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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

			final String searchResults = APIHelper.retriveValuefromJson("$..searchResults..key",
					response.getBody().asString());
			
			if("".equals(searchResults)){
				as.assertEquals(null, searchResults);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertEquals(null, searchResults);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
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
	public void Invalid_value_In_Header_02(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);

			authtoken = "invalideAuthKey";
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);

			
			APIHelper.validaStatusCode(response, data_Search);
			APIHelper.validaErrorMessage(response, data_Search);
			LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			
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

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
	
			APIHelper.validaStatusCode(response, data_Search);
			APIHelper.validaErrorMessage(response, data_Search);
			LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			
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

			authtoken = "invalidToken";
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);

			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);
			
			APIHelper.validaStatusCode(response, data_Search);
			APIHelper.validaErrorMessage(response, data_Search);
			LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			
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
	public void Single_Value_Fields_Sorting_04(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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

			final Boolean ASCSortKeySorted = APIHelper.IsJsonObjectDateSortedAsc("$..sort", response.getBody().asString());
			final Boolean ASCBookEditionSorted = APIHelper.IsJsonObjectSortedAsc("$..source..datePublished",
					response.getBody().asString());

			if(ASCSortKeySorted && ASCBookEditionSorted){
				as.assertTrue(ASCSortKeySorted);
				as.assertTrue(ASCBookEditionSorted);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(ASCSortKeySorted);
				as.assertTrue(ASCBookEditionSorted);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
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
	public void Single_Value_Fields_Sorting_05(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			final Boolean ASCBookEditionSorted = APIHelper.IsJsonObjectSortedAsc("$..source..schema:numberOfPages",
					response.getBody().asString());
			
			if(ASCSortKeySorted && ASCBookEditionSorted){
				as.assertTrue(ASCSortKeySorted);
				as.assertTrue(ASCBookEditionSorted);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(ASCSortKeySorted);
				as.assertTrue(ASCBookEditionSorted);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
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
	public void Single_Value_Fields_Sorting_07(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			final Boolean ASCvalue = APIHelper.IsJsonObjectSortedAsc("$..source..schema:about",
					response.getBody().asString());

		
			if(ASCSortKeySorted && ASCvalue){
				as.assertTrue(ASCSortKeySorted);
				as.assertTrue(ASCvalue);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(ASCSortKeySorted);
				as.assertTrue(ASCvalue);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
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
	public void Single_Value_Fields_Sorting_09(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			// Boolean ASCvalue =
			// APIHelper.IsJsonObjectSortedAsc("$..schema:about",
			// response.getBody().asString());
			if(ASCSortKeySorted){
				as.assertTrue(ASCSortKeySorted);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(ASCSortKeySorted);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
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
	public void Single_Value_Fields_Sorting_08(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			final Boolean ASCvalue =APIHelper.IsJsonObjectSortedAsc("$..schema:about",response.getBody().asString());

			
			if(ASCSortKeySorted && ASCvalue){
				as.assertTrue(ASCSortKeySorted);
				as.assertTrue(ASCvalue);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(ASCSortKeySorted);
				as.assertTrue(ASCvalue);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
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
	public void Single_Value_Fields_Sorting_10(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			if(ASCSortKeySorted){
				as.assertTrue(ASCSortKeySorted);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");	
			}else{
				as.assertTrue(ASCSortKeySorted);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
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
	public void Multi_Value_Fields_Sorting_12(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			final Boolean ASCBookEditionSorted =APIHelper.IsJsonObjectSortedAsc("$..schema:about",response.getBody().asString());

			// Need to implement
			// $..searchResults[0]..source..schema:author..schema:givenName
			if(ASCSortKeySorted && ASCBookEditionSorted){
				as.assertTrue(ASCSortKeySorted);
				as.assertTrue(ASCBookEditionSorted);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(ASCSortKeySorted);
				as.assertTrue(ASCBookEditionSorted);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
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
	public void Invalid_Sorting_Constant_29(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			APIHelper.validaErrorMessage(response, data_Search);

			LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Exact_Phrase_031(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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

			final String searchContent = APIHelper.getCallexactPharseSplitter(data_Search);

			final String schemaname = APIHelper.retriveValuefromJson("$..source..schema:name", response.getBody().asString());
			final String Des = APIHelper.retriveValuefromJson("$..source..schema:description", response.getBody().asString());
			final String fam = APIHelper.retriveValuefromJson("$..source..schema:familyName", response.getBody().asString());
			
			if(Des.contains(searchContent) && schemaname.contains(searchContent) && fam.contains(searchContent)){
				as.assertTrue(Des.contains(searchContent));
				as.assertTrue(schemaname.contains(searchContent));
				as.assertTrue(fam.contains(searchContent));
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(Des.contains(searchContent));
				as.assertTrue(schemaname.contains(searchContent));
				as.assertTrue(fam.contains(searchContent));
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
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
	public void Exact_Phrase_034(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);

			final String searchContenttobeSplited = APIHelper.retriveValuefromJson("$..queryString", responseBody);
			final String expectedsearch = APIHelper.pullQueryStringExactPhrasefromJsonBody(searchContenttobeSplited);

			final JSONObject json = Template.convertStringtoJsonObject(responseBody);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final String schemaname = APIHelper.retriveValuefromJson("$..source..schema:name", response.getBody().asString());
			
			final String Des = APIHelper.retriveValuefromJson("$..source..schema:description", response.getBody().asString());
			if(schemaname.contains(expectedsearch) && Des.contains(expectedsearch)){
				as.assertTrue(schemaname.contains(expectedsearch));
				as.assertTrue(Des.contains(expectedsearch));
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(schemaname.contains(expectedsearch));
				as.assertTrue(Des.contains(expectedsearch));
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
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
	public void Exact_Pharse_Search_with_SingleLevelFacet_03(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String responseBody = APIHelper.GetrequestBody(data_Search);

			final String searchContenttobeSplited = APIHelper.retriveValuefromJson("$..queryString", responseBody);
			final String expectedsearch = APIHelper.pullQueryStringExactPhrasefromJsonBody(searchContenttobeSplited);

			final JSONObject json = Template.convertStringtoJsonObject(responseBody);
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final String schemaname = APIHelper.retriveValuefromJson("$..source..schema:name", response.getBody().asString());
			final String Des = APIHelper.retriveValuefromJson("$..source..schema:description", response.getBody().asString());
			if(schemaname.contains(expectedsearch) && Des.contains(expectedsearch)){
				as.assertTrue(schemaname.contains(expectedsearch));
				as.assertTrue(Des.contains(expectedsearch));
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(schemaname.contains(expectedsearch));
				as.assertTrue(Des.contains(expectedsearch));
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
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
	public void Single_Value_Fields_Sorting_01(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			final Boolean ASCvalue = APIHelper.IsJsonObjectSortedAsc("$..source..schema:name", response.getBody().asString());
			if(ASCSortKeySorted && ASCvalue){
				as.assertTrue(ASCSortKeySorted);
				as.assertTrue(ASCvalue);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(ASCSortKeySorted);
				as.assertTrue(ASCvalue);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
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
	public void Single_Value_Fields_Sorting_02(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			final Boolean ASCBookEditionSorted = APIHelper.IsJsonObjectSortedAsc("$..source..schema:bookEdition",
					response.getBody().asString());
			
			if(ASCSortKeySorted && ASCBookEditionSorted){
				as.assertTrue(ASCSortKeySorted);
				as.assertTrue(ASCBookEditionSorted);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(ASCSortKeySorted);
				as.assertTrue(ASCBookEditionSorted);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
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
	public void Single_Value_Fields_Sorting_03(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			if(ASCSortKeySorted){
				as.assertTrue(ASCSortKeySorted);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");	
			}else{
				as.assertTrue(ASCSortKeySorted);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
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
	public void Single_Value_Fields_Sorting_06(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			final Boolean description = APIHelper.IsJsonObjectSortedAsc("$..source..schema:description",
					response.getBody().asString());
			if(ASCSortKeySorted && description){
				as.assertTrue(ASCSortKeySorted);
				as.assertTrue(description);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(ASCSortKeySorted);
				as.assertTrue(description);
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
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
	public void Multi_Value_Fields_Sorting_13(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			
			if(APIHelper.MultiPhaseSort("$..searchResults[*]..source..schema:keywords",
					response.getBody().asString()) && APIHelper.IsJsonObjectSortedAsc("$..searchResults..sort[0]", response.getBody().asString())){
				as.assertTrue(APIHelper.MultiPhaseSort("$..searchResults[*]..source..schema:keywords",response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults..sort[0]", response.getBody().asString()));
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");	
			}else{
				as.assertTrue(APIHelper.MultiPhaseSort("$..searchResults[*]..source..schema:keywords",response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..searchResults..sort[0]", response.getBody().asString()));
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
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
	public void Multi_Level_Sorting_26(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			if(APIHelper.IsJsonObjectDateSortedAsc("$..searchResults[*]..sort[0]", response.getBody().asString()) &&
					APIHelper.IsJsonObjectDateSortedAsc("$.searchResults[*].source.schema:datePublished",
							response.getBody().asString())){
				as.assertTrue(
						APIHelper.IsJsonObjectDateSortedAsc("$..searchResults[*]..sort[0]", response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectDateSortedAsc("$.searchResults[*].source.schema:datePublished",
						response.getBody().asString()));
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(
						APIHelper.IsJsonObjectDateSortedAsc("$..searchResults[*]..sort[0]", response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectDateSortedAsc("$.searchResults[*].source.schema:datePublished",
						response.getBody().asString()));
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
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
	public void Multi_Level_Sorting_27(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			if(APIHelper.IsJsonObjectDateSortedAsc("$..searchResults[*]..sort[0]", response.getBody().asString()) &&
					APIHelper.IsJsonObjectDateSortedAsc("$.searchResults[*].source.schema:datePublished",
							response.getBody().asString())){
				as.assertTrue(
						APIHelper.IsJsonObjectDateSortedAsc("$..searchResults[*]..sort[0]", response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectDateSortedAsc("$.searchResults[*].source.schema:datePublished",
						response.getBody().asString()));
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(
						APIHelper.IsJsonObjectDateSortedAsc("$..searchResults[*]..sort[0]", response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectDateSortedAsc("$.searchResults[*].source.schema:datePublished",
						response.getBody().asString()));
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
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
	public void Multi_Level_Sorting_28(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			if(APIHelper.IsJsonObjectDateSortedAsc("$..searchResults[*]..sort[0]", response.getBody().asString()) && 
					APIHelper.IsJsonObjectDateSortedAsc("$.searchResults[*].source.schema:datePublished",response.getBody().asString())){
				as.assertTrue(
						APIHelper.IsJsonObjectDateSortedAsc("$..searchResults[*]..sort[0]", response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectDateSortedAsc("$.searchResults[*].source.schema:datePublished",
						response.getBody().asString()));
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(
						APIHelper.IsJsonObjectDateSortedAsc("$..searchResults[*]..sort[0]", response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectDateSortedAsc("$.searchResults[*].source.schema:datePublished",
						response.getBody().asString()));
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
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
	public void SpecificField_Search_Sorting_14(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			if(APIHelper.IsJsonObjectSortedAsc("$..sort", response.getBody().asString()) && APIHelper.IsJsonObjectSortedAsc("$.searchResults[*].source.schema:name",
					response.getBody().asString())){
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..sort", response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$.searchResults[*].source.schema:name",
						response.getBody().asString()));
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			}else{
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..sort", response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$.searchResults[*].source.schema:name",
						response.getBody().asString()));
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
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
	public void MultiFields_Search_Sorting_22(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			if(APIHelper.IsJsonObjectSortedAsc("$..sort", response.getBody().asString()) && APIHelper.IsJsonObjectSortedAsc("$.searchResults[*].source.schema:name",
					response.getBody().asString())){
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..sort", response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$.searchResults[*].source.schema:name",
						response.getBody().asString()));
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");	
			}else{
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$..sort", response.getBody().asString()));
				as.assertTrue(APIHelper.IsJsonObjectSortedAsc("$.searchResults[*].source.schema:name",
						response.getBody().asString()));
				LoggerUtil.log(m.getName(), "STATUS", Constants.testStep, null);
				ReportHelper.log(LogStatus.FAIL, m.getName(), m.getName()+" test validation is failed");
			}
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

}
