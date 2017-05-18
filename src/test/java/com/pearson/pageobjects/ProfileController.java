package com.pearson.pageobjects;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.PropLoad;
import com.pearson.regression.utilityHelpers.RESTServiceBase;
import com.pearson.regression.utilityHelpers.ResponseValidator;
import com.pearson.regression.utilityHelpers.Template;

public class ProfileController extends RESTServiceBase{
	 
	static RESTServiceBase webCredentials_rest = new RESTServiceBase();
	String reqURL = "";
	static Response response;
	@SuppressWarnings("unused")
	private HashMap<String, String> data;
	String contentAPIUrl = PropLoad.getEnvironmentBaseUrl().toString().trim();
	String authtoken = "";
	//private static String Totalcount = PropLoad.getTestData("NavigationControl_totalHitsKey");
	SoftAssert soft = new SoftAssert();
	Assertion asserts = new Assertion();

	public ProfileController(LinkedHashMap<String, String> data) {
		this.data = data;

	}

	public Response statusRequestwithParameter(LinkedHashMap<String, String> data_Search) throws Exception {
		reqURL = data_Search.get("API_reqURL").trim();
		contentAPIUrl = contentAPIUrl + reqURL;
		final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
		response = webCredentials_rest.getCallWithParameters(parameters, contentAPIUrl);
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);
		LoggerUtil.log("URI", contentAPIUrl, "SubStep", null);
		LoggerUtil.log("Parameter", parameters.toString(), "SubStep", null);
		// LoggerUtil.log("Response", response.body().toString(), "SubStep",
		// null);
		return response;
	}

	
}
