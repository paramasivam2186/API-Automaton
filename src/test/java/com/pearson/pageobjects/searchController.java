package com.pearson.pageobjects;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.pearson.regression.utilityHelpers.APIHelper;
import com.pearson.regression.utilityHelpers.PropLoad;
import com.pearson.regression.utilityHelpers.RESTServiceBase;
import com.pearson.regression.utilityHelpers.ReportHelper;
import com.relevantcodes.extentreports.LogStatus;

public class searchController extends RESTServiceBase {

	static RESTServiceBase webCredentials_rest = new RESTServiceBase();
	String reqURL = "";
	static Response response;
	@SuppressWarnings("unused")
	private HashMap<String, String> data;
	String contentAPIUrl = PropLoad.getEnvironmentBaseUrl().toString().trim();
	String contentAPIUrlstg = PropLoad.getcontentAPiUrl().toString().trim();
	String authtoken = "";
	SoftAssert soft = new SoftAssert();

	public searchController(LinkedHashMap<String, String> data) {
		this.data = data;

	}

	public boolean emTagValidation(Response response, LinkedHashMap<String, String> data_Search,
			Map<String, String> parameters) {
		Boolean success = false;
		try {
			final String expectedWordHit = APIHelper.getValueforKey_SingleSearch(parameters, "q");
			final String totalHits = APIHelper.retriveValuefromJson("$..totalHits", response.getBody().asString());
			final String emSearchWord = "<em>" + expectedWordHit.toLowerCase().trim() + "</em>";

			if ("*".equals(expectedWordHit)) {
				ReportHelper.log(LogStatus.FAIL, "Query String is * ", "emTag Validation not possible ");
				success = false;
			}

			else if ("0".equals(totalHits)) {
				ReportHelper.log(LogStatus.FAIL, "TotalHit returned as zero", "emTag Validation not possible ");
				success = false;
			}

			else {

				final List<String> emTagWordList = APIHelper.FeildToReturnasArray("$..contentPreview",
						response.getBody().asString());
				for (String e : emTagWordList) {
					e = e.toLowerCase();
					success = e.contains(emSearchWord);

					if (success.equals(true)) {
						continue;
					} else {
						break;
					}
				}
				if (success.equals(true))
					ReportHelper.log(LogStatus.PASS, "emTag Validation", "<em> Tag Validation is complete");
				else {
					ReportHelper.log(LogStatus.FAIL, "emTag Validation", "<em> Tag Validation is Failed");
				}								
				return success;
			}
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return success;
	}

	public boolean emTagValidation(Response response, LinkedHashMap<String, String> data_Search, String reqBody) {
		Boolean success = false;
		try {
			final String expectedWordHit = APIHelper.retriveValuefromJson("$..query", reqBody);
			final String totalHits = APIHelper.retriveValuefromJson("$..totalHits", response.getBody().asString());
			final String emSearchWord = "<em>" + expectedWordHit.toLowerCase().trim() + "</em>";

			if ("*".equals(expectedWordHit)) {
				ReportHelper.log(LogStatus.PASS, "Query String is * ", "emTag Validation not possible ");
				success = true;
			}

			else if ("0".equals(totalHits)) {
				ReportHelper.log(LogStatus.FAIL, "TotalHit returned as zero", "emTag Validation not possible ");
				success = false;
			}

				else {
	
					final List<String> emTagWordList = APIHelper.FeildToReturnasArray("$..contentPreview",
							response.getBody().asString());
					for (String e : emTagWordList) {
						e = e.toLowerCase();
						success = e.contains(emSearchWord);
	
						if (success.equals(true)) {
							continue;
						} else {
							break;
						}
					}
					if (success.equals(true))
						ReportHelper.log(LogStatus.PASS, "emTag Validation", "<em> Tag Validation is complete");
					else {
						ReportHelper.log(LogStatus.FAIL, "emTag Validation", "<em> Tag Validation is Failed");
					}								
					return success;
				}
			
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}

	public boolean jsonPathvalidateSingleWordHit(Response response, LinkedHashMap<String, String> data_Search,
			Map<String, String> parameters) throws Exception {

		Boolean WorhitStatus = false;

		try {
			String actualWordHit = APIHelper.retriveValuefromJson("$..wordHits[0]", response.getBody().asString());
			String[] actualWordHitSplit = actualWordHit.split(",");
			actualWordHit = actualWordHitSplit[0].replaceAll("[^\\w\\s]", "").trim();
			final String totalHits = APIHelper.retriveValuefromJson("$..totalHits", response.getBody().asString());
			final String expectedWordHit = APIHelper.getValueforKey_SingleSearch(parameters, "q");

			if ("*".equals(expectedWordHit)) {
				ReportHelper.log(LogStatus.FAIL, "Query String is * ",
						"Worhit will return empty as the querystring is * ");
				WorhitStatus = false;
			}

			else if ("0".equals(totalHits)) {
				ReportHelper.log(LogStatus.FAIL, "TotalHit returned as zero",
						"TotalHit returned as zero - WordHit Validation is not possible");
				WorhitStatus = false;
			}

			else if (actualWordHit.equalsIgnoreCase(expectedWordHit)) {
				ReportHelper.log(LogStatus.PASS, "Query String matched with wordHit",
						"Query String matched with wordHit - Validation");
				WorhitStatus = true;
			} else if (expectedWordHit.toLowerCase().contains(actualWordHit.toLowerCase())){
				ReportHelper.log(LogStatus.PASS, "Query String matched with wordHit",
						"Query String matched with wordHit - Validation");
				WorhitStatus = true;
			}
			return WorhitStatus;
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return WorhitStatus;

	}

	public boolean jsonPathvalidateSingleWordHit(Response response, LinkedHashMap<String, String> data_Search,
			String reqBody) throws Exception {
		Boolean WorhitStatus = false;

		try {
			String actualWordHit = APIHelper.retriveValuefromJson("$..wordHits", response.getBody().asString());
			String[] actualWordHitSplit = actualWordHit.split(",");
			actualWordHit = actualWordHitSplit[0].replaceAll("[^\\w\\s]", "").trim();
			final String totalHits = APIHelper.retriveValuefromJson("$..totalHits", response.getBody().asString());
			final String expectedWordHit = APIHelper.retriveValuefromJson("$..query", reqBody);

			if ("0".equals(totalHits)) {
				ReportHelper.log(LogStatus.FAIL, "TotalHit returned as zero",
						"TotalHit returned as zero - Specfic Validation is not possible");
				Assert.fail();
			}

			if ("*".equals(expectedWordHit)) {
				ReportHelper.log(LogStatus.PASS, "Query String is * ",
						"Wordhit will return empty as the querystring is * ");
				WorhitStatus = true;
			} else if (actualWordHit.equalsIgnoreCase(expectedWordHit)) {
				ReportHelper.log(LogStatus.PASS, "Query String matched with wordHit",
						"Query String matched with wordHit - Validation");
				WorhitStatus = true;
			} else if (expectedWordHit.toLowerCase().contains(actualWordHit.toLowerCase())){
				ReportHelper.log(LogStatus.PASS, "Query String matched with wordHit",
						"Query String matched with wordHit - Validation");
				WorhitStatus = true;
			}
			return WorhitStatus;
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return WorhitStatus;

	}

	public boolean jsonPathvalidateKeyValidation(Response response) throws Exception {
		// Validate wordHits has search value
		boolean result = false;
		try {
			final String content = response.getBody().asString();

			final String url, title, contentPreview, orderId, noteText, selectedText;
			url = APIHelper.retriveValuefromJson("$..url", content);
			title = APIHelper.retriveValuefromJson("$..title", content);
			contentPreview = APIHelper.retriveValuefromJson("$..contentPreview", content);
			orderId = APIHelper.retriveValuefromJson("$..orderId", content);
			noteText = APIHelper.retriveValuefromJson("$..noteText", content);
			selectedText = APIHelper.retriveValuefromJson("$..selectedText", content);

			if (url == null || title == null || contentPreview == null || orderId == null || noteText == null
					|| selectedText == null) {

				ReportHelper.log(LogStatus.FAIL, "Empty values returned", "Empty Values returned from response");

				result = false;

			} else if (url.isEmpty() == false && title.isEmpty() == false && contentPreview.isEmpty() == false
					&& orderId.isEmpty() == false && noteText.isEmpty() == false && selectedText.isEmpty() == false) {
				ReportHelper.log(LogStatus.PASS, "Search API Validation",
						"url, title, contentPreview, orderId,noteText, selectedText  type are returned");
				result = true;
			}
			LoggerUtil.log("are returned", "term and meaning are returned", "SubStep", null);
			return result;
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}
	
	public boolean jsonPathvalidateKeyValidationTypeContent(Response response) throws Exception {
		// Validate wordHits has search value
		boolean result = false;
		try {
			final String content = response.getBody().asString();

			final String url, title, contentPreview, orderId;
			url = APIHelper.retriveValuefromJson("$..url", content);
			title = APIHelper.retriveValuefromJson("$..title", content);
			contentPreview = APIHelper.retriveValuefromJson("$..contentPreview", content);
			orderId = APIHelper.retriveValuefromJson("$..orderId", content);

			if (url == null || title == null || contentPreview == null || orderId == null ) {

				ReportHelper.log(LogStatus.FAIL, "Empty values returned", "Empty Values returned from response");

				result = false;

			} else 
				ReportHelper.log(LogStatus.PASS, "Search API Validation",
						"url, title, contentPreview, orderId are returned");
				result = true;
			LoggerUtil.log("are returned", "term and meaning are returned", "SubStep", null);
			return result;
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	public void returnTotalHit(Response response) throws Exception {
		try {
			final String actual = APIHelper.retriveValuefromJson("$..totalHits", response.getBody().asString());
			if (("0").equals(actual)) {
				LoggerUtil.log("TotalHit Returned is Zero - Please verify the search text", actual, "SubStep", null);
				ReportHelper.log(LogStatus.FAIL, "TotalHit Returned is Zero - Please verify the search text", actual);
				Assert.fail();
			} else {
				LoggerUtil.log("TotalHit is greater than zero - result for search text are returned", actual, "SubStep",
						null);
				ReportHelper.log(LogStatus.PASS, "TotalHit is greater than zero - result for search text are returned",
						"Total Count - " + actual);
			}
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
		}
	}
}
