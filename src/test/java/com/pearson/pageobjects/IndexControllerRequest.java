package com.pearson.pageobjects;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import com.jayway.restassured.response.Response;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.RESTServiceBase;
import com.pearson.regression.utilityHelpers.ResponseValidator;
import com.pearson.regression.utilityHelpers.Template;
import net.minidev.json.JSONObject;

public class IndexControllerRequest extends RESTServiceBase {

	static RESTServiceBase webCredentials_rest = new RESTServiceBase();
	@SuppressWarnings("unused")
	private HashMap<String, String> data;
	String authtoken = "";

	public IndexControllerRequest(LinkedHashMap<String, String> data) {
		this.data = data;
	}

	public void post_IndexMetadataValidate(LinkedHashMap<String, String> indexdata) throws Exception {
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
}