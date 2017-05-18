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
import com.pearson.pageobjects.MetaDataSearch;
import com.pearson.regression.utilityHelpers.APIHelper;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.RESTServiceBase;
import com.pearson.regression.utilityHelpers.ResponseValidator;
import com.pearson.regression.utilityHelpers.Template;

import net.minidev.json.JSONObject;

public class metaData  extends BaseTest{
	
	private RESTServiceBase webCredentials_rest = new RESTServiceBase();
	private Assertion as = new Assertion();

	@Test(dataProvider = "dp")
	public void CM_METADATA_SEARCH_API_with_Invalid_searchText(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
		
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			final Response response = webCredentials_rest.getCallWithParameters(parameters, URI);
			
		
			APIHelper.validaStatusCode(response, data_Search);
			
			final MetaDataSearch mt = new MetaDataSearch();
			final int actual = mt.returnTotalHit(response);
			final int expected = 0;
			as.assertEquals(actual, expected,"Total Hit must be returned as 0");
			LoggerUtil.log("Total Hit must be zero for Invalid search Text","0", "SubStep", null);
			
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
	public void CM_METADATA_SEARCH_API_with_DataRange(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
		
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			final Response response = webCredentials_rest.getCallWithParameters(parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CM_METADATA_SEARCH_API(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
		
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			final Response response = webCredentials_rest.getCallWithParameters(parameters, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final MetaDataSearch mt = new MetaDataSearch();
			mt.returnTotalHit(response);
			mt.WordHitValidation_singleWord_get(response, parameters);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CM_METADATA_SEARCH_API_with_Invalid_searchText_post(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			
			final String URI = APIHelper.URIgenerator_CSG(data_Search);	
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);
			final Response response = webCredentials_rest.postCallWithBodyParam(json, URI);
			
			APIHelper.validaStatusCode(response, data_Search);
			
			final MetaDataSearch mt = new MetaDataSearch();
			final int actual = mt.returnTotalHit(response);
			
			final int expected = 0;
			as.assertEquals(actual, expected);
			as.assertEquals(actual, expected,"Total Hit must be returned as 0");
			LoggerUtil.log("Total Hit must be zero for Invalid search Text","0", "SubStep", null);
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
	public void CM_METADATA_SEARCH_API_post(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
		
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);
			final Response response = webCredentials_rest.postCallWithBodyParam(json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final MetaDataSearch mt = new MetaDataSearch();
			mt.WordHitValidation_singleWord_post(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CM_METADATA_SEARCH_Filter_API(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			final Response response = webCredentials_rest.getCallWithParameters(parameters, URI);
			
			APIHelper.validaStatusCode(response, data_Search);
			
			final String values = APIHelper.retriveValuefromJson("$..[0]", response.getBody().asString());
			as.assertFalse(values.isEmpty(), "The response is not empty and has indexid returned");
			LoggerUtil.log("The response is not empty and has indexid returned", values, "SubStep",null);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CM_METADATA_SEARCHFILTER_API_with_DataRange(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			final Response response = webCredentials_rest.getCallWithParameters(parameters, URI);
			
			APIHelper.validaStatusCode(response, data_Search);
			
			final String values = APIHelper.retriveValuefromJson("$..[0]", response.getBody().asString());
			as.assertFalse(values.isEmpty(), "The response is not empty and has indexid returned");
			LoggerUtil.log("The response is not empty and has indexid returned", values, "SubStep",null);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CM_METADATA_SEARCH_Filter_API_with_Invalid_searchText(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			final Response response = webCredentials_rest.getCallWithParameters(parameters, URI);
			
			APIHelper.validaStatusCode(response, data_Search);
			
			final String values = APIHelper.retriveValuefromJson("$..", response.getBody().asString());
			// 2. Validating the Invalid response
			final String expInvalidResponse = data_Search.get(Constants.expJsonRes).trim();
			if (!expInvalidResponse.equals(Constants.emptyString)) {
				ResponseValidator.compareJsonResponse(response, expInvalidResponse);
			}
			if(values==null) {
				LoggerUtil.log("The response must be empty for invalid Serach Text", values, "SubStep",null);
			} else {
				as.assertTrue(false, " The response is not empty for invalid search Text");
			}
				
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void SearchContent_with_valid_IndexId_GET(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			final Response response = webCredentials_rest.getCallWithParameters(parameters, URI);
			
			APIHelper.validaStatusCode(response, data_Search);
			final MetaDataSearch mt = new MetaDataSearch();
			mt.returnTotalHit(response);
			mt.WordHitValidation_singleWord_get(response, parameters);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void SearchContent_with_valid_IndexId_POST(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
		
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);
			final Response response = webCredentials_rest.postCallWithBodyParam(json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final MetaDataSearch mt = new MetaDataSearch();
			mt.WordHitValidation_singleWord_post(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CM_SEARCH_API_SearchContent_with_Existing_IndexId(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			final Response response = webCredentials_rest.getCallWithParameters(parameters, URI);
			
			APIHelper.validaStatusCode(response, data_Search);
			final MetaDataSearch mt = new MetaDataSearch();
			mt.returnTotalHit(response);
			mt.WordHitValidation_singleWord_get(response, parameters);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CM_SEARCH_API_SearchContent_with_new_IndexId(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			final Response response = webCredentials_rest.getCallWithParameters(parameters, URI);
			
			APIHelper.validaStatusCode(response, data_Search);
			
			
			final MetaDataSearch mt = new MetaDataSearch();
			mt.returnTotalHit(response);
			mt.WordHitValidation_singleWord_get(response, parameters);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CM_SEARCH_API_SearchContent_With_Null_IndexId(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			final Response response = webCredentials_rest.getCallWithParameters(parameters, URI);
			
			APIHelper.validaStatusCode(response, data_Search);
			
			as.assertEquals("400",APIHelper.retriveValuefromJson("$..errorCode", response.getBody().asString()));
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CM_SEARCH_API_SearchContent_with_Existing_IndexId_post(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
		
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);
			final Response response = webCredentials_rest.postCallWithBodyParam(json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final MetaDataSearch mt = new MetaDataSearch();
			mt.returnTotalHit(response);
			mt.WordHitValidation_singleWord_post(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CM_SEARCH_API_SearchContent_with_new_IndexId_post(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
		
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);
			final Response response = webCredentials_rest.postCallWithBodyParam(json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final MetaDataSearch mt = new MetaDataSearch();
			mt.returnTotalHit(response);
			mt.WordHitValidation_singleWord_post(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
		
	@Test(dataProvider = "dp")
	public void CM_SEARCH_API_SearchContent_With_Null_IndexId_post(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
		
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);
			final Response response = webCredentials_rest.postCallWithBodyParam(json, URI);
			
			APIHelper.validaStatusCode(response, data_Search);
			
			//as.assertEquals("400",APIHelper.retriveValuefromJson("$..errorCode", response.getBody().asString()));
			
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CM_SEARCH_API_SearchContent_With_Invalid_IndexId(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			
			final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
			LoggerUtil.log("Parameter Value", parameters.toString(), "SubStep", null);
			final Response response = webCredentials_rest.getCallWithParameters(parameters, URI);
			
			APIHelper.validaStatusCode(response, data_Search);
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
	public void CM_SEARCH_API_SearchContent_With_Invalid_IndexId_post(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
		
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);
			final Response response = webCredentials_rest.postCallWithBodyParam(json, URI);
			APIHelper.validaStatusCode(response, data_Search);
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
	public void CM_SEARCH_API_SearchContent_with_seriesOf_IndexIds(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
		
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);
			final Response response = webCredentials_rest.postCallWithBodyParam(json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CM_SEARCH_API_SearchContent_With_Mixed_IndexId(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
		
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);
			final Response response = webCredentials_rest.postCallWithBodyParam(json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final MetaDataSearch mt = new MetaDataSearch();
			mt.returnTotalHit(response);
			mt.WordHitValidation_singleWord_post(response, requestBody);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CM_SEARCH_Filter_API_SearchContent_With_valid_IndexId(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
		
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);
			final Response response = webCredentials_rest.postCallWithBodyParam(json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CM_SEARCH_Filter_API_SearchContent_With_valid_multiple_IndexId(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
		
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);
			final Response response = webCredentials_rest.postCallWithBodyParam(json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CM_SEARCH_Filter_API_SearchContent_With_invalid_IndexId(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
		
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);
			final Response response = webCredentials_rest.postCallWithBodyParam(json, URI);
			APIHelper.validaStatusCode(response, data_Search);
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
	public void SearchContentFilter_with_valid_IndexId_POST(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
		
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);
			final Response response = webCredentials_rest.postCallWithBodyParam(json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			as.assertFalse(response.getBody().asString().isEmpty());
			
			LoggerUtil.log("Data returned for the search Filter","" , "SubStep", null);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CM_SEARCH_Autocorrect_API_With_valid_IndexId(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
		
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);
			final Response response = webCredentials_rest.postCallWithBodyParam(json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			if(response.getBody().asString().isEmpty()==true) {
				LoggerUtil.log("Search Key is not present in any book","" , "SubStep", null);
			} else {
				LoggerUtil.log("Search Key is present and the index id displayed in response","" , "SubStep", null);
			}
				
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CM_METADATA_Update_API(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());
			final String URI = APIHelper.URIgenerator_CM(data_Search);
		
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);
			final Response response = webCredentials_rest.postCallWithBodyParam(json, URI);
			APIHelper.validaStatusCode(response, data_Search);
			

			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	
}
