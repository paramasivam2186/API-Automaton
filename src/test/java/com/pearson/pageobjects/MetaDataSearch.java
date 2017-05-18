package com.pearson.pageobjects;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.pearson.regression.utilityHelpers.APIHelper;
import com.pearson.regression.utilityHelpers.PropLoad;
import com.pearson.regression.utilityHelpers.RESTServiceBase;
import com.pearson.regression.utilityHelpers.ReportHelper;
import com.relevantcodes.extentreports.LogStatus;

public class MetaDataSearch extends RESTServiceBase {

	static RESTServiceBase webCredentials_rest = new RESTServiceBase();
	String contentAPIUrl = PropLoad.getEnvironmentBaseUrl().toString().trim();
	String cmcontentAPiUrl = PropLoad.getConfigData("cmURL");

	public int returnTotalHit(Response response) throws Exception {
		final String actual = APIHelper.retriveValuefromJson("$..totalHits", response.getBody().asString());
		final int actualcount = Integer.parseInt(actual);
		if (actualcount == 0){
			LoggerUtil.log("TotalHit Returned is Zero - Please verify the search text", actual, "SubStep", null);
			ReportHelper.log(LogStatus.PASS, "TotalHit Returned is Zero - Please verify the search text", actual);
		}
		else if (actualcount > 0){
			LoggerUtil.log("TotalHit is greater than zero - result for search text are returned", actual, "SubStep",
					null);
			ReportHelper.log(LogStatus.FAIL, "TotalHit Returned is Zero - Please verify the search text", actual);
		}
		return actualcount;
	}
	
	public void WordHitValidation_singleWord_post(Response response, String requestBody) throws Exception {
		final List<String> actual = APIHelper.FeildToReturnasArray("$.wordHits", response.getBody().asString());
		final String expected = APIHelper.retriveValuefromJson("$..query", requestBody);	
		Assert.assertEquals(actual.get(0).toLowerCase().trim(), expected.toLowerCase().trim(), "Comparing WordHit and Query search Text");
		LoggerUtil.log("Word Hit returned is same as search Text", expected, "SubStep", null);
		ReportHelper.log(LogStatus.PASS, "Word Hit returned is same as search Text", expected);
	}
	
	public void WordHitValidation_singleWord_get(Response response, Map<String, String>  parameters) throws Exception {
		final List<String> actual = APIHelper.FeildToReturnasArray("$.wordHits", response.getBody().asString());
		String expected = null;
		@SuppressWarnings("rawtypes")
		final
		Iterator it = parameters.entrySet().iterator();
		while (it.hasNext()) {
	        @SuppressWarnings("rawtypes")
			final
			Map.Entry pair = (Map.Entry)it.next();
	       if(pair.getKey().equals("q")) {
			expected = (String) pair.getValue();
		}
	    }
		Assert.assertEquals(actual.get(0).toLowerCase().trim(), expected.toLowerCase().trim(), "Comparing WordHit and Query search Text");
		LoggerUtil.log("Word Hit returned is same as search Text", expected, "SubStep", null);
		ReportHelper.log(LogStatus.PASS, "Word Hit returned is same as search Text", expected);
	}

}
