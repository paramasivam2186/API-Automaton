package com.pearson.tests;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.pearson.pageobjects.IngestControllerRequest;
import com.pearson.regression.utilityHelpers.APIHelper;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.PropLoad;
import com.pearson.regression.utilityHelpers.RESTServiceBase;
import com.pearson.regression.utilityHelpers.ReportHelper;
import com.relevantcodes.extentreports.LogStatus;

public class Ingestion_Controller_2_1 extends BaseTest {

	LinkedHashMap<String, LinkedHashMap<String, String>> hashMap = null;
	private RESTServiceBase webCredentials_rest = new RESTServiceBase();
	private SoftAssert as = new SoftAssert();

	// @Test(testName = "CSG Test Cases", groups = "validCase")
	@Test(dataProvider = "dp")
	public void Ingest_Multiple_pageURL_04(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String requestbody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, requestbody);
			APIHelper.validaStatusCode(response, data_Search);

			final IngestControllerRequest ingestsion = new IngestControllerRequest(data_Search);

			final String expected = ingestsion.generatedIndexId(response);
			final boolean isIndexed = ingestsion.validateisIndexIded(expected);

			final SoftAssert as = new SoftAssert();
			as.assertTrue(isIndexed);

			LoggerUtil.log(m.getName(), m.getName()+" Validation", "SubStep", null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Ingest_Single_pageURL_05(Method m, LinkedHashMap<String, String> data_Search) {
		try {

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String requestbody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, requestbody);
			APIHelper.validaStatusCode(response, data_Search);

			final IngestControllerRequest ingestsion = new IngestControllerRequest(data_Search);

			final String expected = ingestsion.generatedIndexId(response);
			final boolean isIndexed = ingestsion.validateisIndexIded(expected);

			final SoftAssert as = new SoftAssert();
			as.assertTrue(isIndexed);
			LoggerUtil.log(m.getName(), m.getName()+" Validation", "SubStep", null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Ingest_Single_pageURL_with_metadata_01(Method m, LinkedHashMap<String, String> data_Search) {
		try {

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String requestbody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBodyandheader(URI, requestbody, data_Search);
			APIHelper.validaStatusCode(response, data_Search);

			final IngestControllerRequest ingestsion = new IngestControllerRequest(data_Search);

			final String expected = ingestsion.generatedIndexId(response);
			final boolean isIndexed = ingestsion.validateisIndexIded(expected);

			final SoftAssert as = new SoftAssert();
			as.assertTrue(isIndexed);
			LoggerUtil.log(m.getName(), m.getName()+" Validation", "SubStep", null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Ingest_Multiple_pageURL_with_metadata_02(Method m, LinkedHashMap<String, String> data_Search) {
		try {

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String requestbody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBodyandheader(URI, requestbody, data_Search);
			APIHelper.validaStatusCode(response, data_Search);

			final IngestControllerRequest ingestsion = new IngestControllerRequest(data_Search);

			final String expected = ingestsion.generatedIndexId(response);
			final boolean isIndexed = ingestsion.validateisIndexIded(expected);

			final SoftAssert as = new SoftAssert();
			as.assertTrue(isIndexed);
			LoggerUtil.log(m.getName(), m.getName()+" Validation", "SubStep", null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Ingest_Same_pageURL_with_metadata_03(Method m, LinkedHashMap<String, String> data_Search) {
		try {

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String requestbody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, requestbody);
			APIHelper.validaStatusCode(response, data_Search);

			final IngestControllerRequest ingestsion = new IngestControllerRequest(data_Search);

			final String expected = ingestsion.generatedIndexId(response);
			final boolean isIndexed = ingestsion.validateisIndexIded(expected);

			final SoftAssert as = new SoftAssert();
			as.assertTrue(isIndexed);
			LoggerUtil.log(m.getName(), m.getName()+" Validation", "SubStep", null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Ingest_only_metadata_05(Method m, LinkedHashMap<String, String> data_Search) {
		try {

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String requestbody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBodyandheader(URI, requestbody, data_Search);
			APIHelper.validaStatusCode(response, data_Search);

			final IngestControllerRequest ingestsion = new IngestControllerRequest(data_Search);

			final String expected = ingestsion.generatedIndexId(response);
			final boolean isIndexed = ingestsion.validateisIndexIded(expected);

			final SoftAssert as = new SoftAssert();
			as.assertTrue(isIndexed);
			LoggerUtil.log(m.getName(), m.getName()+" Validation", "SubStep", null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Ingest_pageURL_and_metadata_without_applicationId_06(Method m,
			LinkedHashMap<String, String> data_Search) {
		try {

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String requestbody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, requestbody);
			APIHelper.validaStatusCode(response, data_Search);
			LoggerUtil.log(m.getName(), m.getName()+" Validation", "SubStep", null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Ingest_pageURL_and_metadata_with_Invalid_applicationId_07(Method m,
			LinkedHashMap<String, String> data_Search) {
		try {

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String requestbody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, requestbody);
			APIHelper.validaStatusCode(response, data_Search);
			LoggerUtil.log(m.getName(), m.getName()+" Validation", "SubStep", null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Ingest_Multiple_pageURL_and_metadata_without_IndexId_08(Method m,
			LinkedHashMap<String, String> data_Search) {
		try {

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String requestbody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBodyandheader(URI, requestbody, data_Search);
			APIHelper.validaStatusCode(response, data_Search);
			LoggerUtil.log(m.getName(), m.getName()+" Validation", "SubStep", null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Ingest_pageURL_and_metadata_with_Null_IndexId_09(Method m, LinkedHashMap<String, String> data_Search) {
		try {

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String requestbody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, requestbody);
			APIHelper.validaStatusCode(response, data_Search);
			LoggerUtil.log(m.getName(), m.getName()+" Validation", "SubStep", null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Ingest_metadata_without_IndexId_10(Method m, LinkedHashMap<String, String> data_Search) {
		try {

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String requestbody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, requestbody);
			APIHelper.validaStatusCode(response, data_Search);
			LoggerUtil.log(m.getName(), m.getName()+" Validation", "SubStep", null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Ingest_only_pageURL_04(Method m, LinkedHashMap<String, String> data_Search) {
		try {

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String requestbody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, requestbody);
			APIHelper.validaStatusCode(response, data_Search);

			final IngestControllerRequest ingestsion = new IngestControllerRequest(data_Search);

			final String expected = ingestsion.generatedIndexId(response);
			final boolean isIndexed = ingestsion.validateisIndexIded(expected);

			final SoftAssert as = new SoftAssert();
			as.assertTrue(isIndexed);
			LoggerUtil.log(m.getName(), m.getName()+" Validation", "SubStep", null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Ingest_SIngle_bookURL_01(Method m, LinkedHashMap<String, String> data_Search) {
		try {

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String requestbody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, requestbody);
			APIHelper.validaStatusCode(response, data_Search);

			final IngestControllerRequest ingestsion = new IngestControllerRequest(data_Search);
			final String expected = ingestsion.generatedIndexId(response);
			as.assertFalse(expected.isEmpty());
			LoggerUtil.log(m.getName(), m.getName()+" Validation", "SubStep", null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Ingest_Multiple_bookURL_02(Method m, LinkedHashMap<String, String> data_Search) {
		try {

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String requestbody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithRequestBody(URI, requestbody);
			APIHelper.validaStatusCode(response, data_Search);
			LoggerUtil.log(m.getName(), m.getName()+" Validation", "SubStep", null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	
	@Test(dataProvider = "dp")
	public void CM_Update_Annotations_with_valid_mandatory_fields(Method m, LinkedHashMap<String, String> data_Search) {
		try {

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String requestbody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithArrayRequestBody(URI, requestbody);
			APIHelper.validaStatusCode(response, data_Search);

			final String content = data_Search.get(PropLoad.getTestData("API_requestbody"));

			final String contextId = APIHelper.retriveValuefromJson("$..contextId", content);
			final String expectedNoteText = APIHelper.retriveValuefromJson("$..text", content);
			final String identityId = APIHelper.retriveValuefromJson("$..identityId", content);

			final IngestControllerRequest ingestsion = new IngestControllerRequest(data_Search);
			final Response searchresponse = ingestsion.connectingSearch(expectedNoteText, contextId, identityId);

			final String actaulNotedText = APIHelper.retriveValuefromJson("$..noteText", searchresponse.getBody().asString());
			as.assertEquals(actaulNotedText, expectedNoteText);
			LoggerUtil.log(m.getName(), m.getName()+" Validation", "SubStep", null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void CM_Update_Annotations_with_missing_mandatory_fields(Method m,
			LinkedHashMap<String, String> data_Search) {
		try {

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String requestbody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithArrayRequestBody(URI, requestbody);
			APIHelper.validaStatusCode(response, data_Search);

			final String error = APIHelper.retriveValuefromJson("$..error", response.getBody().asString());

			final boolean status;

			if (error != null) {
				status = true;
			} else {
				status = false;
			}

			as.assertTrue(status);
			LoggerUtil.log(m.getName(), m.getName()+" Validation", "SubStep", null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Ingest_Annotation_01(Method m, LinkedHashMap<String, String> data_Search) {
		try {

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final String requestbody = APIHelper.GetrequestBody(data_Search);
			final Response response = APIHelper.postCallwithArrayRequestBody(URI, requestbody);
			APIHelper.validaStatusCode(response, data_Search);

			final String content = data_Search.get(PropLoad.getTestData("API_requestbody"));

			final String contextId = APIHelper.retriveValuefromJson("$..contextId", content);
			final String expectedNoteText = APIHelper.retriveValuefromJson("$..tt", content);
			final String identityId = APIHelper.retriveValuefromJson("$..identityId", content);

			final IngestControllerRequest ingestsion = new IngestControllerRequest(data_Search);
			final Response searchResponse = ingestsion.connectingSearch(expectedNoteText, contextId, identityId);

			final String actualtt = APIHelper.retriveValuefromJson("$..tt", searchResponse.getBody().asString());
			System.out
					.println("ActualAlternative" + actualtt);
			LoggerUtil.log(m.getName(), m.getName()+" Validation", "SubStep", null);ReportHelper.log(LogStatus.PASS, m.getName(), m.getName()+" test validation is complete");

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

}
