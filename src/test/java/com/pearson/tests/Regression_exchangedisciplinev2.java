package com.pearson.tests;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.pearson.pageobjects.Regression_exchangedisciplinev2PO;
import com.pearson.pageobjects.V2SearchRequest;
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

public class Regression_exchangedisciplinev2 extends BaseTest {
	private RESTServiceBase webCredentials_rest = new RESTServiceBase();
	String authToken = "";
	String TestDescription = Constants.emptyString;
	private static String xAuthKey = PropLoad.getTestData("Auth_XAuthKey");
	final Regression_exchangedisciplinev2PO exV2 = new Regression_exchangedisciplinev2PO();

	/**
	 * Test to validate single and multi facet sorting for a POST call
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
			exV2.multiLevelFacet_validation(response, requestBodyJSON);

			// Sorting Validation
			exV2.fieldtoReturn_post(response, requestBody);

			if (exV2.isSortedField1(response, requestBody)) {
				ReportHelper.log(LogStatus.PASS, "Sorting", "Sorting is successful");
			} else {
				ReportHelper.log(LogStatus.FAIL, "Sorting", "$..sort field - Sorting is not successful");
				Assert.fail();
			}

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Test to validate single and multi facet sorting for a GET call
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
			exV2.get_multiLevelFacet_validation(response, parameters);

			// Sorting Validation

			exV2.get_fieldtoReturn_post(response, parameters);

			if (exV2.get_isSortedField1(response, parameters)) {
				ReportHelper.log(LogStatus.PASS, "Sorting", "Sorting is successful");
			} else {
				ReportHelper.log(LogStatus.FAIL, "Sorting", "$..sort field - Sorting is not successful");
				Assert.fail();
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
	 * Test to validate single or multi level sorting for a POST call
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

			authToken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authToken);
			// Send the POST or GET request
			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);

			// Validate status code using the expected status code present in
			// excel
			APIHelper.validaStatusCode(response, data_Search);

			// Validating the response schema
			final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
			if (!expSchemafileName.equals(Constants.emptyString)) {
				ResponseValidator.validateJsonSchema(response, expSchemafileName);
			}
			// Sorting Validation
			// Single level validation
			Assert.assertTrue(exV2.post_Sorting(response, requestBody));

			// Sort values from response is being validated
			if (exV2.sortField(response, requestBody)) {
				ReportHelper.log(LogStatus.PASS, "Single or Multi Level Sorting",
						"Single or Multi Level Sorting is successful");
			} else {
				ReportHelper.log(LogStatus.FAIL, "Single or Multi Level Sorting",
						"Single or Multi Level Sorting is successful");
				Assert.fail();
			}
			// fields to return is present in response
			exV2.fieldtoReturn_post(response, requestBody);

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Test to validate single and multi facet Boosting for a POST call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void post_Single_MultiLevel_Boosting(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);

			// Validate status code using the expected status code present in
			// excel
			APIHelper.validaStatusCode(response, data_Search);

			// Validating the Response Jason Body
			final ArrayList<String> expectedQuery = exV2.toFindbiggestNumberinQueryString(queryString);
			exV2.jsonResponseValidationWithQueryString(response, expectedQuery);

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Test to validate single and multi facet Boosting for a GET call
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

			final String queryString = parameters.get("queryString");
			final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, URI);

			// Validate status code using the expected status code present in
			// excel
			APIHelper.validaStatusCode(response, data_Search);

			// Validating the Response Jason Body
			final ArrayList<String> expectedQuery = exV2.toFindbiggestNumberinQueryString(queryString);
			exV2.jsonResponseValidationWithQueryString(response, expectedQuery);

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Test to validate single and multi facet for a POST call
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
			exV2.multiLevelFacet_validation(response, requestBodyJSON);

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Test to validate single and multi facet for a GET call
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

			exV2.get_multiLevelFacet_validation(response, parameters);

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Test to validate specific field V2Search for a POST call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void singleSpecificField_ExactPharse_V2Search_POST(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			exV2.postSingleSpecificFieldWildCard_ResponseValidate_exactPharse(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Test to validate specific field V2Search for a GET call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void singleSpecificField_ExactPharse_V2Search_GET(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			exV2.getSingleSpecificFieldWildCard_ResponseValidate_exactPharse(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Test to validate normal and wild card V2Search for a GET request 
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

			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			exV2.getSingleandMultipleTermSearch(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Test to validate normal and wild card V2Search for a POST request
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

			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			exV2.postLogicalOperator(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * Negative validation for a GET request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void negativeValidation_Get(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			exV2.getNegativeValidation(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * Negative validation for a POST request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void negativeValidation_POST(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			exV2.postNegativeValidation(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * Expired token validation for a POST request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void expiredTokenTest_POST(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			exV2.postexpiredTokenTest(data_Search);			
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * Expired token validation for a GET request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void expiredTokenTest_GET(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			exV2.getExpiredTokenTest(data_Search);			
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * Test to validate Multi specific field V2 Search for a POST call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void multiSpecificField_V2Search_POST(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchRequest v2SearchRequest = new V2SearchRequest(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchRequest.postMultipleSpecificFieldWildCard_ResponseValidate(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Test to validate Multi specific field V2 Search for a GET call
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void multiSpecificField_V2Search_GET(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final V2SearchRequest v2SearchRequest = new V2SearchRequest(data_Search);
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			v2SearchRequest.getMultipleSpecificFieldWildCard_ResponseValidate(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	/**
	 * Negative validation for a POST request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void negativeValidation_POST2(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			exV2.postNegativeValidation2(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
		
	/**
	 * Negative Validation for GET request
	 * @param m
	 * @param data_Search
	 * @param ctx
	 */
	@Test(dataProvider = "dp")
	public void negativeValidation_Get2(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			TestDescription = m.getName();
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			exV2.getNegativeValidation2(data_Search);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, TestDescription, "");
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

}
