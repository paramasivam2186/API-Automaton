package com.pearson.pageobjects;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.Assert;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.pearson.regression.utilityHelpers.APIHelper;
import com.pearson.regression.utilityHelpers.AssertValidation;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.ExceptionHandler;
import com.pearson.regression.utilityHelpers.RESTServiceBase;
import com.pearson.regression.utilityHelpers.ReportHelper;
import com.pearson.regression.utilityHelpers.Template;
import com.relevantcodes.extentreports.LogStatus;

public class Regression_eTextPO {

	private static RESTServiceBase webCredentials_rest = new RESTServiceBase();
	private static Response response;

	/**
	 * Compare json response 
	 * @param response
	 * @param expJsonResponse
	 */
	public void compareJsonResponse(Response response, String expJsonResponse) {
		try {
			String bodyResponse = response.asString();
			JsonParser parser = new JsonParser();
			final JsonElement o1 = parser.parse(bodyResponse);
			final JsonElement o2 = parser.parse(expJsonResponse);
			boolean bSuccess = AssertValidation.assertEquals(o1, o2);
			if (bSuccess == false) {
				ReportHelper.log(LogStatus.FAIL, "Compare Json Response",
						"Actual response is not matching with expected response" + expJsonResponse);
			}
			Assert.assertTrue(bSuccess);
			ReportHelper.log(LogStatus.PASS, "Compare Json Response",
					"Actual response is matching with expected response " + expJsonResponse);
		} catch (Exception e) {
			ExceptionHandler.logException(e);
		}
	}

	/**
	 * Get request data for navigation
	 * @param data
	 * @param filter
	 * @return
	 */
	public Map<String, Object> getRequestDatafornavigation(LinkedHashMap<String, String> data, String filter) {
		Map<String, Object> requests = new HashMap<String, Object>();

		for (String key : data.keySet()) {
			if (key.contains(Constants.underScore)) {
				String[] setList = key.split(Constants.underScore);
				if (setList[1].toLowerCase().contains(filter)) {
					String value = data.get(key);
					if (!value.trim().equals(Constants.emptyString)) {
						String[] valueList = value.split(Constants.doubleColon);
						if (valueList.length == 1) {
							requests.put(valueList[0].trim(), Constants.emptyString);
						} else {
							if (isStringInt(valueList[1].trim())) {
								int StringToint = Integer.parseInt(valueList[1]);
								requests.put(valueList[0].trim(), Integer.valueOf(StringToint));
							} else {
								requests.put(valueList[0].trim(), valueList[1]);
							}
						}
					}
				}
			}
		}
		return requests;
	}

	/**
	 * @param s
	 * @return
	 */
	public boolean isStringInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	/**
	 * To get a response for a GET call with parameter for Navigation
	 * @param data_Search
	 * @param contentAPIUrl
	 * @return
	 * @throws Exception
	 */
	public Response api_getCallWithParameterforNavigation(LinkedHashMap<String, String> data_Search,
			String contentAPIUrl) throws Exception {
		final Map<String, Object> parameters = getRequestDatafornavigation(data_Search, Constants.qsParam);
		LoggerUtil.log("api_getCallWithParameter", parameters.toString(), Constants.subStep, null);
		response = webCredentials_rest.getCallWithParameterswithoriginaldatatypes(parameters, contentAPIUrl);
		return response;

	}

	/**
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public int returnTotalHit(Response response) throws Exception {
		int actualcount = 0;
		try {
			final String actual = APIHelper.retriveValuefromJson("$..totalHits", response.getBody().asString());
			actualcount = Integer.parseInt(actual);
			return actualcount;
		} catch (Exception e) {
			ReportHelper.log(LogStatus.PASS, "Error Message", e.getMessage());
		}
		return actualcount;
	}

}
