package com.pearson.tests;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.pearson.pageobjects.V2SearchRequest;
import com.pearson.pageobjects.V2SearchSanity;
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

public class V2SearchSanityOneStop extends BaseTest {

	private RESTServiceBase webCredentials_rest = new RESTServiceBase();
	String authtoken = "";
	String TestDescription = Constants.emptyString;
	private static String xAuthKey = PropLoad.getTestData("Auth_XAuthKey");
	private static String qs_String = PropLoad.getTestData("V2Search_queryString_Excel");
	private static String qs_reqField = PropLoad.getTestData("V2Search_qs_reqfield");
	
	@Test(dataProvider = "dp")
	public void get_SinglewordSearch(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
	public void post_SinglewordSearch(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String reqBodyJson = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(reqBodyJson);

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, reqBodyJson);
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
	public void get_SinglewordSearch_LogicalOperator(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			v2.get_logicalOperatorValidation(response, parameters);
			v2.fieldtoReturn_get(response, parameters);
			//v2.emValidationMatchedFieldLogical_get(response, ".searchResults[*].matchedFields", parameters);
			
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
	public void post_SinglewordSearch_LogicalOperator(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			v2.post_logicalOperatorValidation(response, requestBody);
			v2.fieldtoReturn_post(response, requestBody);
			//v2.emValidationMatchedFieldLogical_post(response, ".searchResults[*].matchedFields", requestBody);
			
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
	public void get_specificWordSearch(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			v2.get_specificField(response, parameters);
			v2.fieldtoReturn_get(response, parameters);
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void get_specificWordSearch_LogicalOperator(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			v2.get_specificField(response, parameters);
			v2.fieldtoReturn_get(response, parameters);

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void post_specificWordSearch(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			v2.post_specificField(response, requestBody);
			v2.fieldtoReturn_post(response, requestBody);
			

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void post_specificWordSearch_LogicalOperator(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			v2.post_specificField(response, requestBody);
			v2.fieldtoReturn_post(response, requestBody);


		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void post_Single_OR_MultiLevel_Sorting(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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
			Assert.assertTrue(v2.post_Sorting(response, requestBody));
			Assert.assertTrue(v2.sortField(response, requestBody));
			v2.fieldtoReturn_post(response, requestBody);

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
/*	
	@Test(dataProvider = "dp")
	public void post_singleFacetandSorting(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			String URI = APIHelper.URIgenerator_CSG(data_Search);
			String requestBody = APIHelper.GetrequestBody(data_Search);
			JSONObject json = Template.convertStringtoJsonObject(requestBody);

			authtoken = CSGHelper.generate_PI_AuthToken();
			Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			V2SearchSanity v2 = new V2SearchSanity();
			v2.singleFacet_validation(response, requestBody);
			v2.fieldtoReturn_post(response, requestBody);
			
			Assert.assertTrue(v2.isSortedField(response, requestBody));
			if(v2.isSortedField(response, requestBody)==true)
				ReportHelper.log(LogStatus.PASS, "Single Facet", "Single Facet Sorting Validation" );
			else
				ReportHelper.log(LogStatus.FAIL, "Single Facet", "Single Facet Sorting Validation" );
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}*/
	
	@Test(dataProvider = "dp")
	public void responseSize_StartIndex(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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

			final int product = APIHelper.retrivejsonArrayFromJson("$.searchResults[*].productId", response.asString())
					.size();
			final List<String> firstResponse = APIHelper.FeildToReturnasArray("$..searchResults[*]..productId",
					response.getBody().asString());

			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers1 = Template.getRequestData(data_Search, Constants.header);
			headers1.put(xAuthKey, authtoken);
			final Map<String, String> parameters1 = new HashMap<String, String>();
			parameters1.put("queryString", "revel");
			parameters1.put("startIndex", "5");
			parameters1.put("responseSize", "5");
			parameters1.put("fieldsToReturn", "name,description,authorFamilyName");
			final Response response1 = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters1, URI);
			APIHelper.validaStatusCode(response1, data_Search);
			final int product1 = APIHelper.retrivejsonArrayFromJson("$.searchResults[*].productId", response.asString())
					.size();
			final List<String> firstResponse2 = APIHelper.FeildToReturnasArray("$..searchResults[*]..productId",
					response1.getBody().asString());
			Assert.assertTrue(firstResponse.retainAll(firstResponse2), "First Response " + product + "Second Response "
					+ product1 + " But the value of productid are different");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	//single facet and sorting
	@Test(dataProvider = "dp")
	public void post_singleFacetandSorting(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			//ReportHelper.initTest(m.getName()+"-"+data_Search.get("testDataRow"),"API");
			//ReportHelper.assignCatogory(data_Search.get("testDataRow"));
			if(data_Search.get("testDataRow")!=null)
			{
			ReportHelper.log(LogStatus.INFO, "Iteration with -"+ data_Search.get("testDataRow"),"");
			}
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final JSONObject requestBody = APIHelper.GetrequestBodyasJson(data_Search);
			//requestBody.
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);
			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, requestBody, URI);
			APIHelper.validaStatusCode(response, data_Search);
			final V2SearchSanity v2 = new V2SearchSanity();
			v2.multiLevelFacet_validation(response, requestBody);
			//v2.fieldtoReturn_post(response, requestBody);
			//
			//Assert.assertTrue(v2.isSortedField(response, requestBody));
			//if(v2.isSortedField(response, requestBody)==true)
			//ReportHelper.log(LogStatus.PASS, "Single Facet", "Single Facet Sorting Validation" );
			//else
			//ReportHelper.log(LogStatus.FAIL, "Single Facet", "Single Facet Sorting Validation" );
			Assert.assertTrue(v2.isSortedField1(response, requestBody.toString()));
			if(v2.isSortedField1(response, requestBody.toString())==true) {
				ReportHelper.log(LogStatus.PASS, "Single Facet", "Single Facet Sorting Validation");
			} else {
				ReportHelper.log(LogStatus.FAIL, "Single Facet", "Single Facet Sorting Validation");
			}
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	@Test(dataProvider = "dp")
	public void post_Single_OR_MultiLevel_Sorting1(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {			ReportHelper.log(LogStatus.INFO, "Iteration with -"+ data_Search.get("testDataRow"),"");

			boolean b=false;
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

//			json path methods
			/*List<String> list=new ArrayList<String>();
			
			JsonPath json1 = response.sort_asc("$..searchResults.sort_asc(value.cnt:slug)");
			 $.searchResults..cnt:slug   */   
			
			final V2SearchSanity v2 = new V2SearchSanity();
			Assert.assertTrue(v2.post_Sorting(response, requestBody));
			final String fieldtobeSorted = APIHelper.retriveValuefromJson("$..fields", requestBody);			
    		final String[] Field = fieldtobeSorted.split(",");
            final int n=Field.length;
			if(n==1)
			{		
				b=false;
			}
			else
			{
				 b=true;
	
			}
			Assert.assertTrue(v2.sortField1(response, requestBody,b));
			v2.fieldtoReturn_post(response, requestBody);

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	//multifacet&single facet
		@Test(dataProvider = "dp")
		public void post_multiLevelFacet_singlelevel_facet(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {			
	//ReportHelper.log(LogStatus.INFO, "Iteration with -"+ data_Search.get("testDataRow"),"");

		//ReportHelper.initTest(m.getName()+"-"+data_Search.get("testDataRow"),"API");
		//ReportHelper.assignCatogory(data_Search.get("testDataRow"));
		ReportHelper.log(LogStatus.INFO, "Iteration with -"+ data_Search.get("testDataRow"),"");
		ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
		ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

		final String URI = APIHelper.URIgenerator_CSG(data_Search);
		final JSONObject requestBody = APIHelper.GetrequestBodyasJson(data_Search);
		//requestBody.
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, requestBody, URI);
		APIHelper.validaStatusCode(response, data_Search);
		final V2SearchSanity v2 = new V2SearchSanity();
		v2.multiLevelFacet_validation(response, requestBody);
		//v2.fieldtoReturn_post(response, requestBody);
		//
		//Assert.assertTrue(v2.isSortedField(response, requestBody));
		//if(v2.isSortedField(response, requestBody)==true)
		//ReportHelper.log(LogStatus.PASS, "Single Facet", "Single Facet Sorting Validation" );
		//else
		//ReportHelper.log(LogStatus.FAIL, "Single Facet", "Single Facet Sorting Validation" );

		} catch (Exception e) {
		LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
		e.getMessage();
		e.printStackTrace();
		Assert.fail(e.getMessage(), e);
		}
		}
		//stop words
		@Test(dataProvider = "dp")
		public void stopwordsValidationPost(Method m, LinkedHashMap<String, String> data_Search,ITestContext ctx) {
			try {			
				ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
				ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
				V2SearchSanity v2 = new V2SearchSanity();
				final String url = data_Search.get(Constants.urlprofile);
				final Response response = webCredentials_rest.getCall(url);
				final List<Object> stopWordField=APIHelper.FeildToReturnasObject("$..isStopwordEnabled", response.getBody().asString());

				final V2SearchRequest v2SearchRequest = new V2SearchRequest(data_Search);
				final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
				authtoken = CSGHelper.generate_PI_AuthToken();

				final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
				headers.put(xAuthKey, authtoken);
				final String requestbody = data_Search.get(Constants.reqBody);
				final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

				final Response response1 = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
				final String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		
				v2SearchRequest.validateStopWord(data_Search, response1, querySearchString, stopWordField);				
				v2SearchRequest.stopWordCheck(data_Search, response1, querySearchString, stopWordField);
				v2.fieldtoReturn_post(response1, requestbody);
			}
			catch (Exception e) {
				LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
				e.getMessage();
				e.printStackTrace();
				Assert.fail(e.getMessage(), e);
			}
		}
	
		@Test(dataProvider = "dp")
		public void stopwordsValidationGet(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
			try{			
				ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
				ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
				
				V2SearchSanity v2 = new V2SearchSanity();				
				final String url = data_Search.get(Constants.urlprofile);
				final Response response = webCredentials_rest.getCall(url);
				final List<Object> stopWordField = APIHelper.FeildToReturnasObject("$..isStopwordEnabled", response.getBody().asString());
				
				final V2SearchRequest v2SearchRequest = new V2SearchRequest(data_Search);				
				final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
				authtoken = CSGHelper.generate_PI_AuthToken();
						
				final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
				headers.put(xAuthKey, authtoken);
				final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
				
				final Response response1 = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);
				final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);

				v2SearchRequest.validateStopWord(data_Search, response1, querySearchString, stopWordField);				
				v2SearchRequest.stopWordCheck(data_Search, response1, querySearchString, stopWordField);
				v2.fieldtoReturn_get(response1, parameters);
			}
			
			catch (Exception e) {
				LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
				e.getMessage();
				e.printStackTrace();
				Assert.fail(e.getMessage(), e);
			}
		}
		//multi-value search
		@Test(dataProvider = "dp")
		public void multi_value_validation(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
			try {
				ReportHelper.log(LogStatus.INFO, "Iteration with -"+ data_Search.get("testDataRow"),"");

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
				v2.multi_validation(response, requestBody);
							
			} catch (Exception e) {
				LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
				e.getMessage();
				e.printStackTrace();
				Assert.fail(e.getMessage(), e);
			}
		}	
	
	/*// V2 Search with Special Character
	@Test(dataProvider = "dp")
	public void get_validate_specialcharacter(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
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

			final int expStatusCode = Integer
					.parseInt(data_Search.get(PropLoad.getTestData("API_expStatusCode_Excel")));

			if (expStatusCode != 500) {
				final V2SearchSanity v2 = new V2SearchSanity();
				v2.returnCountV2Search_PostorGet(response);
				v2.fieldtoReturn_get(response, parameters);

				final String querySearchString = Template.getParametersRemovingSpecialCharacters(parameters);
				v2.stringValidationWithSpecialCharacters(response, querySearchString);
			}
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}*/
			
		@Test(dataProvider = "dp")
		public void post_autocomplete_2to8characters(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) throws Exception{
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String reqBodyJson = APIHelper.GetrequestBody(data_Search);
			JSONObject json = Template.convertStringtoJsonObject(reqBodyJson);
			
			
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnCountV2Search_PostorGet(response);
			v2.fieldtoReturn_post(response, reqBodyJson);
			
			final String querySearchString = Template.getFieldValfromJson(json, qs_reqField);
			Assert.assertTrue(CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false));
		}
		
		@Test(dataProvider = "dp")
		public void post_autocomplete_not2to8characters(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) throws Exception{
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final String reqBodyJson = APIHelper.GetrequestBody(data_Search);
			JSONObject json = Template.convertStringtoJsonObject(reqBodyJson);			
			
			authtoken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authtoken);

			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final V2SearchSanity v2 = new V2SearchSanity();
			v2.returnNoCountV2Search_PostorGet(response);
			
		}
	
	
}
