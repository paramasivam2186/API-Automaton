package com.pearson.tests;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.pearson.regression.utilityHelpers.APIHelper;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.RESTServiceBase;
import com.pearson.regression.utilityHelpers.ReportHelper;
import com.pearson.regression.utilityHelpers.Template;
import com.relevantcodes.extentreports.LogStatus;

import net.minidev.json.JSONObject;

/**
 * 
 * Profile Controller API
 * 
 * 		We create new profile for any application(like exchangeV2, Revel..etc ) using Profile Controller API.
 * 		Once new profile is created we verify the created ID present in the response. 
 * 		
 * 		After completion we also make sure whether the profile is created in the corresponding application.  
 *
 */

public class Regression_profileCreation extends BaseTest {

	private RESTServiceBase webCredentials_rest = new RESTServiceBase();
	String TestDescription = Constants.emptyString;
	

	@Test(dataProvider = "dp")
	public void profileCreation(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
			
			//Hp Alm update parameter are sent to After Method present in BaseTest
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			//Create URI, request Body and Headers 
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String requestBody = APIHelper.GetrequestBody(data_Search);
			final JSONObject json = Template.convertStringtoJsonObject(requestBody);

		
			// Send the POST or GET request
			final Response response = webCredentials_rest.postCallWithBodyParam(json, URI);
			
			//Validate status code using the expected status code present in excel
			APIHelper.validaStatusCode(response, data_Search);
			
			final String actual = APIHelper.retriveValuefromJson("$..id", response.getBody().asString());
			final String expected = APIHelper.retriveValuefromJson("$..id", requestBody);
			
			
			if(actual.trim().equals(expected.trim())){
				ReportHelper.log(LogStatus.PASS, "Profile Created", "New Profile created for id " + actual);
			}
			else
			{
				ReportHelper.log(LogStatus.FAIL, "Profile not created", "New Profile not created for id " + actual);
				Assert.fail();
			}
			
			final Response getResponse = webCredentials_rest.getCall("https://dragonfly-qa.stg-openclass.com:443/pxereader-cm/api/cm/profiles");
			
			if(getResponse.getBody().asString().contains(expected.trim())){
				ReportHelper.log(LogStatus.PASS, "New Profile is present", "New Profile created for id " + actual + " is returned");
			}else
			{
				ReportHelper.log(LogStatus.FAIL, "New Profile is not present", "New Profile created for id " + actual + " is not returned");
				Assert.fail();
			}
			
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Exception", e.getMessage());
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}	

}
