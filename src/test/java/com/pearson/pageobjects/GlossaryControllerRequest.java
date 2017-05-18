package com.pearson.pageobjects;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.pearson.regression.utilityHelpers.APIHelper;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.PropLoad;
import com.pearson.regression.utilityHelpers.RESTServiceBase;
import com.pearson.regression.utilityHelpers.ReportHelper;
import com.pearson.regression.utilityHelpers.ResponseValidator;
import com.pearson.regression.utilityHelpers.Template;
import com.relevantcodes.extentreports.LogStatus;

import bsh.ParseException;
import net.minidev.json.JSONObject;

public class GlossaryControllerRequest extends RESTServiceBase {

	static RESTServiceBase webCredentials_rest = new RESTServiceBase();
	@SuppressWarnings("unused")
	private HashMap<String, String> data;

	private static String glossarySearchKey = PropLoad.getTestData("GlossarySearch_Key_Excel");
	private static String glossaryKey = PropLoad.getTestData("GlossarySearch_glossaryKey");
	SoftAssert soft = new SoftAssert();
	
	
	public GlossaryControllerRequest(HashMap<String, String> data) {
		this.data = data;
	}

	public void cm_GlossarySearchResponseValidate(LinkedHashMap<String, String> data_glossary) throws Exception {
		final String reqURL = Constants.apiBaseUrl + data_glossary.get(Constants.reqUrl).trim();
		final Map<String, String> parameters = Template.getRequestData(data_glossary, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithParameters(parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_glossary.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the JsonSchema file with Json Response
		final String jsonSchemaFileName = data_glossary.get(Constants.expjsonSchemafileName);
		ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);
	}

	public void cm_GlossarySearchWithInvalidIndexIdResponseValidate(LinkedHashMap<String, String> data_glossary)
			throws Exception {
		final String reqURL = Constants.apiBaseUrl + data_glossary.get(Constants.reqUrl).trim();
		final Map<String, String> parameters = Template.getRequestData(data_glossary, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithParameters(parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_glossary.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating json response with expected Json Response
		final String expJson = data_glossary.get(Constants.expJsonRes).trim();
		ResponseValidator.compareJsonResponse(response, expJson);
	}

	public void cm_GlossarySearchWithNullIndexIdResponseValidate(LinkedHashMap<String, String> data_glossary)
			throws Exception {
		final String reqURL = Constants.apiBaseUrl + data_glossary.get(Constants.reqUrl).trim();
		final Map<String, String> parameters = Template.getRequestData(data_glossary, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithParameters(parameters, reqURL);

		 // Validating the response code
		final int expStatus_code = Integer.parseInt(data_glossary.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating json response with expected Json Response
		final String expJson = data_glossary.get(Constants.expJsonRes).trim();
		ResponseValidator.compareJsonResponse(response, expJson);
	}

	public void cm_GlossarySearchWithSpecificKeyResponseValidate(LinkedHashMap<String, String> data_glossary)
			throws Exception {
		final String reqURL = Constants.apiBaseUrl + data_glossary.get(Constants.reqUrl).trim();
		final Map<String, String> parameters = Template.getRequestData(data_glossary, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithParameters(parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_glossary.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating json response with expected Json Response
		final String expJson = data_glossary.get(Constants.expJsonRes).trim();
		ResponseValidator.compareJsonResponse(response, expJson);
	}

	public void cm_GlossarySearch_2_1_ValidIndexId(LinkedHashMap<String, String> data_glossary) throws Exception {
		final String reqURL = Constants.apiBaseUrl + data_glossary.get(Constants.reqUrl).trim();
		final Map<String, String> parameters = Template.getRequestData(data_glossary, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithParameters(parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_glossary.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);
	}

	public void cm_GlossaryContentWithValidIndexIdandGlossaryKey(LinkedHashMap<String, String> data_glossary)
			throws Exception {
		final String reqURL = Constants.apiBaseUrl + data_glossary.get(Constants.reqUrl).trim();
		final Map<String, String> parameters = Template.getRequestData(data_glossary, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithParameters(parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_glossary.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the Search key Present in Response
		Assert.assertTrue(verifySearchKeyJSONObject(data_glossary, response, glossaryKey, glossarySearchKey));
	}

	public void csg_GlossaryContentValidate(LinkedHashMap<String, String> data_glossary)
			throws Exception {
		final String reqURL = Constants.contentAPIUrl + data_glossary.get(Constants.reqUrl).trim();
		final Map<String, String> parameters = Template.getRequestData(data_glossary, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithParameters(parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_glossary.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the Search key Present in Response
		Assert.assertTrue(verifySearchKeyJSONObject(data_glossary, response, glossaryKey, glossarySearchKey));
	}
	
	public void csg_GlossaryAPIValidate(LinkedHashMap<String, String> data_glossary)
			throws Exception {
		final String reqURL = Constants.contentAPIUrl + data_glossary.get(Constants.reqUrl).trim();
		final Map<String, String> parameters = Template.getRequestData(data_glossary, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithParameters(parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_glossary.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating json response with expected Json Response
		final String expJson = data_glossary.get(Constants.expJsonRes).trim();
		ResponseValidator.compareJsonResponse(response, expJson);
	}	
	
	private boolean verifySearchKeyJSONObject(LinkedHashMap<String, String> data_glossary, Response response,
			String glossaryKey, String glossarySearchKey) throws ParseException {
		boolean bSuccess = false;
		final JSONObject resJson = Template.convertStringtoJsonObject(response.asString());
		final String[] searchKeyNames = Template.getFieldNamesFromKey(data_glossary, glossarySearchKey);
		for (String searchkey : searchKeyNames) {
			final JSONObject json = (JSONObject) resJson.get(glossaryKey);
			final Set<String> keySets = Template.getSetofKeysinJSONResponse(json);
			if (keySets.contains(searchkey)) {
				bSuccess = true;
			}
			if (bSuccess == false) {
				break;
			}
		}
		return bSuccess;
	}

	public void cm_mul_GlossaryContentWithInValidIndexId_03_ResponseValidate(
			LinkedHashMap<String, String> data_glossary) throws Exception {
		final String reqURL = Constants.apiBaseUrl + data_glossary.get(Constants.reqUrl).trim();
		final Map<String, String> parameters = Template.getRequestData(data_glossary, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithParameters(parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_glossary.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating json response with expected Json Response
		final String expJson = data_glossary.get(Constants.expJsonRes).trim();
		ResponseValidator.compareJsonResponse(response, expJson);
	}

	public void cm_GlossaryContentWithInValidLang_06_ResponseValidate(LinkedHashMap<String, String> data_glossary)
			throws Exception {
		final String reqURL = Constants.apiBaseUrl + data_glossary.get(Constants.reqUrl).trim();
		final Map<String, String> parameters = Template.getRequestData(data_glossary, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithParameters(parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_glossary.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating json response with expected Json Response
		final String expJson = data_glossary.get(Constants.expJsonRes).trim();
		ResponseValidator.compareJsonResponse(response, expJson);
	}

	public void cm_GlossaryValidIndexIdAndInvalidGlossaryKey_04_ResponseValidate(String testStep,
			String testDescription, LinkedHashMap<String, String> data_glossary) throws Exception {
		final String reqURL = Constants.apiBaseUrl + data_glossary.get(Constants.reqUrl).trim();
		final Map<String, String> parameters = Template.getRequestData(data_glossary, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithParameters(parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_glossary.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating json response with expected Json Response
		final String expJson = data_glossary.get(Constants.expJsonRes).trim();
		ResponseValidator.compareJsonResponse(response, expJson);
	}

	public void cm_GlossaryValidLang_05_ResponseValidate(LinkedHashMap<String, String> data_glossary) throws Exception {
		final String reqURL = Constants.apiBaseUrl + data_glossary.get(Constants.reqUrl).trim();
		final Map<String, String> parameters = Template.getRequestData(data_glossary, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithParameters(parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_glossary.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the JsonSchema file with Json Response
		final String jsonSchemaFileName = data_glossary.get(Constants.expjsonSchemafileName);
		ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);
	}

	public void cm_GlossaryContentWithValidIndexId_01(LinkedHashMap<String, String> data_glossary) throws Exception {
		final String reqURL = Constants.apiBaseUrl + data_glossary.get(Constants.reqUrl).trim();
		final Map<String, String> parameters = Template.getRequestData(data_glossary, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithParameters(parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_glossary.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the JsonSchema file with Json Response
		final String jsonSchemaFileName = data_glossary.get(Constants.expjsonSchemafileName);
		ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);
	}

	public void cm_GlossaryContentWithValidIndexIdAndvalidGlossaryKey_02(LinkedHashMap<String, String> data_glossary)
			throws Exception {
		final String reqURL = Constants.apiBaseUrl + data_glossary.get(Constants.reqUrl).trim();
		final Map<String, String> parameters = Template.getRequestData(data_glossary, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithParameters(parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_glossary.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the JsonSchema file with Json Response
		final String jsonSchemaFileName = data_glossary.get(Constants.expjsonSchemafileName);
		ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);
	}

	public void cm_GlossaryContentWithValidIndexId_01Validate(LinkedHashMap<String, String> data_glossary)
			throws Exception {
		final String reqURL = Constants.apiBaseUrl + data_glossary.get(Constants.reqUrl).trim();
		final Map<String, String> parameters = Template.getRequestData(data_glossary, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithParameters(parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_glossary.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the JsonSchema file with Json Response
		final String jsonSchemaFileName = data_glossary.get(Constants.expjsonSchemafileName);
		ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);
	}

	public void cm_GlossaryContentWithValidIndexIdAndInvalidGlossaryKey_04(LinkedHashMap<String, String> data_glossary)
			throws Exception {
		final String reqURL = Constants.apiBaseUrl + data_glossary.get(Constants.reqUrl).trim();
		final Map<String, String> parameters = Template.getRequestData(data_glossary, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithParameters(parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_glossary.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating json response with expected Json Response
		final String expJson = data_glossary.get(Constants.expJsonRes).trim();
		ResponseValidator.compareJsonResponse(response, expJson);
	}

	public void cm_GlossaryContentWithValidIndexIdAndValidGlossaryKey_02(LinkedHashMap<String, String> data_glossary)
			throws Exception {
		final String reqURL = Constants.apiBaseUrl + data_glossary.get(Constants.reqUrl).trim();
		final Map<String, String> parameters = Template.getRequestData(data_glossary, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithParameters(parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_glossary.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the JsonSchema file with Json Response
		final String jsonSchemaFileName = data_glossary.get(Constants.expjsonSchemafileName);
		ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);
	}

	public void cm_GlossaryContentWithInvalidIndexId_03_ResponseValidate(LinkedHashMap<String, String> data_glossary)
			throws Exception {
		final String reqURL = Constants.apiBaseUrl + data_glossary.get(Constants.reqUrl).trim();
		final Map<String, String> parameters = Template.getRequestData(data_glossary, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithParameters(parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_glossary.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating json response with expected Json Response
		final String expJson = data_glossary.get(Constants.expJsonRes).trim();
		ResponseValidator.compareJsonResponse(response, expJson);
	}

	public void cm_trd_GlossaryContentWithValidIndexId_07ResponseValidate(LinkedHashMap<String, String> data_glossary)
			throws Exception {
		final String reqURL = Constants.apiBaseUrl + data_glossary.get(Constants.reqUrl).trim();
		final Map<String, String> parameters = Template.getRequestData(data_glossary, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithParameters(parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_glossary.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the JsonSchema file with Json Response
		final String jsonSchemaFileName = data_glossary.get(Constants.expjsonSchemafileName);
		ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);
	}
	

	public boolean jsonPathvalidateKeyValidation(Response response, LinkedHashMap<String, String> data_Search) throws Exception {
		// Validate wordHits has search value
		boolean result = false;
		final String content = response.getBody().asString();
		final String expectedkeyvalue = Template.getSingleValue(data_Search, PropLoad.getTestData("GlossarySearch_Key_Excel"));
		final Boolean a = response.getBody().asString().contains(expectedkeyvalue);
		LoggerUtil.log("Search Key ", "Search Key present in response", "SubStep", null);
	
		final String indexid, term;
		String meaning = "";
		indexid  = APIHelper.retriveValuefromJson("$..indexId", content);
		term  = APIHelper.retriveValuefromJson("$..term", content);
		meaning  = APIHelper.retriveValuefromJson("$..meaning", content);
		
		if(indexid==null || term==null || meaning==null || a.equals(!true)){
		
			ReportHelper.log(LogStatus.FAIL,"Empty values returned", "Empty Values returned from response");
		
			result =  false;
			
		}
		else if(indexid.isEmpty()==false && term.isEmpty()==false && meaning.isEmpty()==false && a.equals(true))
		{
			ReportHelper.log(LogStatus.PASS,"Media Field are returned", "indexId, term, meaning type are returned");	
			result =  true;
		}	
		LoggerUtil.log("are returned", "term and meaning are returned", "SubStep", null);
		return result;
		
	}
	
	public boolean jsonPathValidationWithNoKey(Response response, LinkedHashMap<String, String> data_Search) throws Exception {
		// Validate wordHits has search value
		boolean result = false;
		final String content = response.getBody().asString();
			
		final String indexid, term;
		String meaning = "";
		indexid  = APIHelper.retriveValuefromJson("$..indexId", content);
		term  = APIHelper.retriveValuefromJson("$..term", content);
		meaning  = APIHelper.retriveValuefromJson("$..meaning", content);
		
		if(indexid==null || term==null || meaning==null){
		
			ReportHelper.log(LogStatus.FAIL,"Empty values returned", "Empty Values returned from response");
		
			result =  false;			
		}
		else if(indexid.isEmpty()==false && term.isEmpty()==false && meaning.isEmpty()==false)
		{
			ReportHelper.log(LogStatus.PASS,"Media Field are returned", "indexId, term, meaning type are returned");	
			result =  true;
		}	
		LoggerUtil.log("are returned", "term and meaning are returned", "SubStep", null);
		return result;		
	}
	
	public void jsonPathvalidateKeyValidation(Response response, LinkedHashMap<String, String> data_Search, Map<String, String> parameters) throws Exception {
		// Validate wordHits has search value
		
		final String content = response.getBody().asString();
		final String expSerachFromPArameter = APIHelper.getValueforKey_SingleSearch(parameters, "key");
		//String expectedkeyvalue = Template.getSingleValue(data_Search, PropLoad.getTestData("GlossarySearch_Key_Excel"));
		final Boolean a = response.getBody().asString().contains(expSerachFromPArameter);
		
		final JsonPath term = JsonPath.compile("$..term");
		final List<Object> arrcgetPageUrlret = term.read(content);
		for (Object o : arrcgetPageUrlret) {
			soft.assertNotNull(o);
		}	
		final JsonPath meaning = JsonPath.compile("$..meaning");
		final List<Object> arrgetTyperet = meaning.read(content);
		for (Object o : arrgetTyperet) {
			soft.assertNotNull(o);
		}	
		
	}
	
	

}
