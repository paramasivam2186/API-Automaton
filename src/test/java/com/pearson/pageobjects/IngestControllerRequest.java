package com.pearson.pageobjects;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.response.Response;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.PropLoad;
import com.pearson.regression.utilityHelpers.RESTServiceBase;
import com.pearson.regression.utilityHelpers.ResponseValidator;
import com.pearson.regression.utilityHelpers.Template;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class IngestControllerRequest extends RESTServiceBase {

	static RESTServiceBase webCredentials_rest = new RESTServiceBase();
	@SuppressWarnings("unused")
	private HashMap<String, String> data;
	String authtoken = "";

	public IngestControllerRequest(LinkedHashMap<String, String> data) {
		this.data = data;
	}

	public void post_IngestionValidate(LinkedHashMap<String, String> indexdata) throws Exception {
		final String reqURL = Constants.apiBaseUrl + indexdata.get(Constants.reqUrl).trim();
		final Map<String, String> headers = Template.getRequestData(indexdata, Constants.header);
		final String requestbody = indexdata.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
		 Thread.sleep(2000);
		// Validating the response code
		final int expStatus_code = Integer.parseInt(indexdata.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating json response with expected Json Response
		final String expJson = indexdata.get(Constants.expJsonRes).trim();
		ResponseValidator.compareJsonResponse(response, expJson);
	}

	public void post_IngestAnnotateValidate_2_1(LinkedHashMap<String, String> indexdata) throws Exception {
		final String reqURL = Constants.apiBaseUrl + indexdata.get(Constants.reqUrl).trim();
		final String requestbody = indexdata.get(Constants.reqBody);
		final JSONArray reqBodyJson = Template.convertStringtoJsonarray(requestbody);

		final Response response = webCredentials_rest.postCallWithReqBodyAsJsonArray(reqBodyJson, reqURL);
        Thread.sleep(2000);
		// Validating the response code
		final int expStatus_code = Integer.parseInt(indexdata.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating json response with expected Json Response
		final String expJson = indexdata.get(Constants.expJsonRes).trim();
		ResponseValidator.compareJsonResponse(response, expJson);
	}
	
	public void post_IngestAnnotateValidate(LinkedHashMap<String, String> indexdata) throws Exception {
		final String reqURL = Constants.apiBaseUrl + indexdata.get(Constants.reqUrl).trim();
		final String requestbody = indexdata.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		final Response response = webCredentials_rest.postCallWithReqBodyAsJsonObject(reqBodyJson, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(indexdata.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating json response with expected Json Response
		final String expJson = indexdata.get(Constants.expJsonRes).trim();
		ResponseValidator.compareJsonResponse(response, expJson);
	}
	
	public void post_IngestIsIndexed(LinkedHashMap<String, String> indexdata) throws Exception {
		final String reqURL = Constants.apiBaseUrl + indexdata.get(Constants.reqUrl).trim();
		final String requestbody = indexdata.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);
		final Map<String, String> headers = Template.getRequestData(indexdata, Constants.header);
		final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(indexdata.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);
	}

	public void get_IngestIsIndexed(LinkedHashMap<String, String> indexdata) throws Exception {
		final String reqURL = Constants.apiBaseUrl + indexdata.get(Constants.reqUrl).trim();
		final Map<String, String> headers = Template.getRequestData(indexdata, Constants.header);
		final Map<String, String> parameters = Template.getRequestData(indexdata, Constants.qsParam);
		final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(indexdata.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);
	}

	public void get_Ingestqueuesize(LinkedHashMap<String, String> indexdata) throws Exception {
		final String reqURL = Constants.apiBaseUrl + indexdata.get(Constants.reqUrl).trim();

		final Response response = webCredentials_rest.getCall(reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(indexdata.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating json response with expected Json Response
		final String expJson = indexdata.get(Constants.expJsonRes).trim();
		ResponseValidator.compareJsonResponse(response, expJson);
	}

	public void get_Ingestreset(LinkedHashMap<String, String> indexdata) throws Exception {
		final String reqURL = Constants.apiBaseUrl + indexdata.get(Constants.reqUrl).trim();
		final Map<String, String> headers = Template.getRequestData(indexdata, Constants.header);
		final Map<String, String> parameters = Template.getRequestData(indexdata, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(indexdata.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);
	}

	public void post_MetadataUpdate(LinkedHashMap<String, String> indexdata) throws Exception {
		final String reqURL = Constants.apiBaseUrl + indexdata.get(Constants.reqUrl).trim();
		final String requestbody = indexdata.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);
		final Map<String, String> headers = Template.getRequestData(indexdata, Constants.header);
		final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);

		// Validating the response code
		final int expStatus_code = Integer.parseInt(indexdata.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating json response with expected Json Response
		final String expJson = indexdata.get(Constants.expJsonRes).trim();
		ResponseValidator.compareJsonResponse(response, expJson);
	}
	
	public String generatedIndexId(Response response){
		String expected = null;
		final String content = response.getBody().asString();
		final JsonPath json = JsonPath.compile("$..indexId");
		final List<Object> value = json.read(content);
		for (Object o : value) {
			expected = o.toString();
		}
		return expected;
	}
	
	public boolean validateisIndexIded(String expected) throws Exception{
		
		final LinkedHashMap<String, String> myParentJsonObject = new LinkedHashMap<String, String>();
		myParentJsonObject.put("application-id", "collection");
		myParentJsonObject.put("indexId",expected);
		final Response response1 = webCredentials_rest.getCallWithParameters(myParentJsonObject, PropLoad.getTestData("validateisIndexIded"));
		response1.statusCode();
		
		return response1.getBody().asString().toLowerCase().contains("true");
	}
	
	
	public Response connectingSearch(String expectedNoteText, String contextId, String identityId) throws Exception{
		
		final LinkedHashMap<String, String> myParentJsonObject = new LinkedHashMap<String, String>();
		
		myParentJsonObject.put("indexId", "f552f2f0c1cabf333b3f57ace8d72db4");
		myParentJsonObject.put("q","*");
		myParentJsonObject.put("type","annotation");
		myParentJsonObject.put("contextId",contextId);
		myParentJsonObject.put("identityId",identityId);
		
		final Response response1 = webCredentials_rest.getCallWithParameters(myParentJsonObject, PropLoad.getTestData("connectingSearch"));
		response1.statusCode();
		
		return response1;
		
		
						
	}

}