package com.pearson.regression.utilityHelpers;

import java.io.FileReader;
import java.util.Iterator;

import org.testng.Assert;

import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.relevantcodes.extentreports.LogStatus;

public class ResponseValidator {

	/**
	 * Validate actual response with expected response code
	 * @param response
	 * @param expStatusCode
	 */
	public static void validateResponseCode(Response response, int expStatusCode) {
		try {
			boolean bSuccess = false;
			System.out.println("Response Code: " + response.getStatusCode());
			System.out.println("\n Response Code: " + response.asString());
			if (response.getStatusCode() == expStatusCode) {
				bSuccess = true;
			}
			if (!bSuccess) {
				ReportHelper.log(LogStatus.FAIL, "Validate Response code", Integer.toString(expStatusCode));
			}
			Assert.assertTrue(bSuccess);
			ReportHelper.log(LogStatus.PASS, "Validate Response code", Integer.toString(expStatusCode));
			LoggerUtil.log("Validate Response code", Integer.toString(expStatusCode), Constants.subStep, null);
		} catch (Exception e) {
			ExceptionHandler.logException(e);
		}
	}
 
	/**
	 * Validate actual response Schema with expected response Schema file
	 * @param response
	 * @param expSchemafileName
	 * @throws Exception
	 */
	public static void validateJsonSchema(Response response, String expSchemafileName) throws Exception {
		System.out.println("schema name:" + expSchemafileName);
		System.out.println("response:" + response.asString());
		boolean bSuccess = false;
		final JsonParser parser = new JsonParser();
		final String responseText = response.asString();
		final Object fileObj = parser.parse(new FileReader("src/test/resources/" + expSchemafileName));
		final String schematxt = fileObj.toString();
		ProcessingMessage message = null;
		ProcessingReport report = ValidationUtils.getjsonValReport(schematxt, responseText);
		if (report.isSuccess()) {
			bSuccess = true;
			System.out.println("Response is Valid!");
		} else {
			Iterator<ProcessingMessage> itr = report.iterator();
			while (itr.hasNext()) {
				message = (ProcessingMessage) itr.next();
				System.out.println("Message" + message.asJson().get("message").asText());
			}
			bSuccess = false;
		}
		if (!bSuccess) {
			ReportHelper.log(LogStatus.FAIL, "validateResponseSchema",
					"Actual response is not matched with the expected json schema " + expSchemafileName + message);
		}
		Assert.assertTrue(bSuccess);
		ReportHelper.log(LogStatus.PASS, "validateResponseSchema",
				"Actual response is matched with the expected json schema " + expSchemafileName);
	}

	/**
	 * Compare Actual response with expected Response
	 * @param response
	 * @param expJsonResponse
	 */
	public static void compareJsonResponse(Response response, String expJsonResponse) {
		try {
			boolean bSuccess;
			String bodyResponse = response.asString();
			JsonParser parser = new JsonParser();
			final JsonElement o1 = parser.parse(bodyResponse);
			final JsonElement o2 = parser.parse(expJsonResponse);
			bSuccess = o1.equals(o2);

			if (!bSuccess)
				bSuccess = bodyResponse.contains(expJsonResponse);

			if (bSuccess == false) {
				ReportHelper.log(LogStatus.FAIL, "Compare Json Response",
						"Actual response is not matching with expected response" + " Actual Respose " + bodyResponse);
				LoggerUtil.log("Compare Json Response",
						"Actual response is not matching with expected response " + expJsonResponse, Constants.subStep,
						null);
			}
			Assert.assertTrue(bSuccess);
			ReportHelper.log(LogStatus.PASS, "Compare Json Response",
					"Actual response is matching with expected response " + " Actual Respose " + bodyResponse);
			LoggerUtil.log("Compare Json Response",
					"Actual response is matching with expected response " + expJsonResponse, Constants.subStep, null);

		} catch (Exception e) {
			ExceptionHandler.logException(e);
		}
	}

	/**
	 * Compare actual response count with expected count 
	 * @param expResponseCount
	 * @param actResponseCount
	 */
	public static void compareResponseCount(int expResponseCount, int actResponseCount ) {
		if (actResponseCount <= expResponseCount) {
			ReportHelper.log(LogStatus.PASS, "Compare Response Count",
					"Actual response count is matched / less than the expected response count " + expResponseCount);
		} else {
			ReportHelper.log(LogStatus.FAIL, "Compare Response Count",
					"Actual response count is not matching with expected response count" + expResponseCount);
			Assert.fail();
		}
	}

}