package com.pearson.pageobjects;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.pearson.regression.utilityHelpers.APIHelper;
import com.pearson.regression.utilityHelpers.PropLoad;
import com.pearson.regression.utilityHelpers.RESTServiceBase;
import com.pearson.regression.utilityHelpers.ReportHelper;
import com.relevantcodes.extentreports.LogStatus;

public class NavigationController extends RESTServiceBase {

	static RESTServiceBase webCredentials_rest = new RESTServiceBase();
	String reqURL = "";
	static Response response;
	@SuppressWarnings("unused")
	private HashMap<String, String> data;
	String contentAPIUrl = PropLoad.getEnvironmentBaseUrl().toString().trim();
	String cmcontentAPiUrl = PropLoad.getConfigData("cmURL");
	String authtoken = "";
	SoftAssert soft = new SoftAssert();
	Assertion asserts = new Assertion();

	public NavigationController(LinkedHashMap<String, String> data) {
		this.data = data;

	}

	public void ValidateMediaarrayjsonPath(Response response, LinkedHashMap<String, String> data_Search, String content)
			throws Exception {
		final String Total = APIHelper.retriveValuefromJson("$..totalHits", content);
		if ("0".equals(Total)) {
			ReportHelper.log(LogStatus.FAIL, "Incorrect Parameter", "Parameter passed is in-correct");
			Assert.assertTrue(false, "Incorrect Parameter");
		} else {
			final String id, indexid, type, url,pageurl;
			id = APIHelper.retriveValuefromJson("$..id", content);
			indexid = APIHelper.retriveValuefromJson("$..indexId", content);
			type = APIHelper.retriveValuefromJson("$..type", content);
			url = APIHelper.retriveValuefromJson("$..url", content);
			pageurl = APIHelper.retriveValuefromJson("$..pageUrl", content);
			if (id.isEmpty() == false && indexid.isEmpty() == false && type.isEmpty() == false && url.isEmpty() == false
					&& pageurl.isEmpty() == false && type.toLowerCase().trim().equals("img")) {
				ReportHelper.log(LogStatus.PASS, "Media Field are returned",
						"id, indexId, type, url, pageUrl, type are returned");
			} else {
				ReportHelper.log(LogStatus.FAIL, "Empty values returned", "Empty Values returned from response");
				LoggerUtil.log("Media Field are returned", "type, url, pageUrl, type are returned", "SubStep", null);
				Assert.assertTrue(false, "Empty Value");
			}
		}
	}
	
	public void navigationTypeimgValidation(Response response, LinkedHashMap<String, String> data_Search, String content)
			throws Exception {
			if (response.getBody().asString().contains("img")==true) {
				ReportHelper.log(LogStatus.PASS, "Navigation Type",
						"Navigation type image validation");
			} else {
				ReportHelper.log(LogStatus.FAIL, "Navigation Type", "Navigation type image validation");
				LoggerUtil.log("Navigation Type", "Navigation type image validation", "SubStep", null);
				Assert.assertTrue(false, "Empty Value");
			}
		}

}
