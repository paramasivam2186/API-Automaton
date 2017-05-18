package com.pearson.pageobjects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.testng.Reporter;

import com.jayway.restassured.response.Response;
import com.pearson.regression.utilityHelpers.APIHelper;
import com.pearson.regression.utilityHelpers.CSGHelper;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.PropLoad;
import com.pearson.regression.utilityHelpers.RESTServiceBase;
import com.pearson.regression.utilityHelpers.ReportHelper;
import com.pearson.regression.utilityHelpers.ResponseJsonPath;
import com.pearson.regression.utilityHelpers.ResponseValidator;
import com.pearson.regression.utilityHelpers.Template;
import com.relevantcodes.extentreports.LogStatus;

import bsh.ParseException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class V2SearchDataIntegrityTestRequest extends RESTServiceBase {

	static RESTServiceBase webCredentials_rest = new RESTServiceBase();
	@SuppressWarnings("unused")
	private HashMap<String, String> data;
	String authtoken = "";

	private static String xAuthKey = PropLoad.getTestData("Auth_XAuthKey");
	private static String qs_reqField = PropLoad.getTestData("V2Search_qs_reqfield");
	private static String expFieldName_reqField = PropLoad.getTestData("V2Search_expFields_reqfield");

	public V2SearchDataIntegrityTestRequest(LinkedHashMap<String, String> data) {
		this.data = data;
	}

	public void datintegrityValidation(LinkedHashMap<String, String> data_Search) throws Exception {
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final String requestbody = data_Search.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);
		// String appId = Template.getSingleValue(data_Search, "appId_header");

		final Response actResponse = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
		final String[] expFieldnames = Template.getStringArrayValfromJson(reqBodyJson, expFieldName_reqField);
		final String elasticSearchURL = Constants.apiElasticSearchUrl + data_Search.get(Constants.elasticSearchUrl);
		final String dataStoreURL = Constants.apiDataStoreSearchUrl + data_Search.get(Constants.dataStoreSearchUrl);

		final String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		ReportHelper.log(LogStatus.INFO, "Response ", actResponse.asString());
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);

		// 1. Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(actResponse, expStatus_code);

		// 2. Verify fields to return value with elastic search & data store
		// responses
		// i>> get v2Search API response
		final String actRescount = APIHelper.getJsonValue("count", actResponse);
		final Map<String, Map<String, String>> actResNodeValue = Template.getResponseNodeValues(expFieldnames, actResponse,
				".searchResults[*]", "source", "productId");

		// ii>> get DatStore (Profile) API response
		final Response dataStoreResponse = webCredentials_rest.getCall(dataStoreURL);
		final Map<String, Map<String, String>> dataStoreResNodeValue = Template.getDataStoreResponseNodeValues(expFieldnames,
				dataStoreResponse, ".products[*]", actResNodeValue, "productId");

		// iii>> get Elastic Search API response
		final Response elasticSearchResponse = webCredentials_rest.getCall(elasticSearchURL);
		final String dsRescount = "[" + APIHelper.getJsonValue("total", elasticSearchResponse).split(",")[1];
		final Map<String, Map<String, String>> esResNodeValue = Template.getElasticSearchResponse_NodeValues(actResNodeValue,
				Template.getResponseNodeValues(expFieldnames, elasticSearchResponse, "hits.hits[*]", "_source", "_id"));

		// iv.>> Comparing the V2Search ,DataStore & Elastic search response
		CSGHelper.validateDataStoreValues(actResNodeValue, esResNodeValue, dataStoreResNodeValue, expFieldnames,
				actRescount, dsRescount);

		// 3. Validating the response with expected schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(actResponse, expSchemafileName);
		}

		// 4. Verify Field to return present in json
		APIHelper.fieldtoReturn_post(actResponse, expFieldnames, ResponseJsonPath.v2SearchSource_JP);
	}

	public void datintegrityValidation_JsonPath(LinkedHashMap<String, String> data_Search) throws Exception {
		final String BaseDababaseURL = "http://10.199.0.189:9200/exchangev2index/_search?q=";
		final String givenProductDatabaseURL = BaseDababaseURL + data_Search.get("Category");

		// Given product ID and the JSONKey (i.e _id)
		final String productIdKeyJson = data_Search.get("Category").split(":")[0];
		final String productIdValueJson = data_Search.get("Category").split(":")[1];

		// Pull the response from the Database
		final Response DataBaseresponse = webCredentials_rest.getCall(givenProductDatabaseURL);
		APIHelper.validaStatusCode(DataBaseresponse, data_Search);
		ReportHelper.log(LogStatus.PASS, productIdValueJson,
				"Given" + productIdValueJson + "is Present in response for " + "\n" + givenProductDatabaseURL);

		// Validate given product in requestURL is returned in the response of
		// Database
		final String actualproductIdValueJson = APIHelper.retriveValuefromJson("$.." + productIdKeyJson,
				DataBaseresponse.getBody().asString());
		Assert.assertEquals(actualproductIdValueJson, productIdValueJson);

		// Validate in the profile URL(holds all product and details of
		// V2Search) contains the product ID - (profile url ::
		// https://productization-stg.pearsoned.com/us-api/v2/products)
		final Response CompleteProductresponse = webCredentials_rest
				.getCall("https://productization-stg.pearsoned.com/us-api/v2/products");
		final List<Object> AllProduct = APIHelper.retrivejsonArrayFromJson("$..productId",
				CompleteProductresponse.getBody().asString());
		int ProductIdPosition = -1;
		for (Object a : AllProduct) {
			ProductIdPosition++;
			if (a.toString().equals(productIdValueJson)) {
				break;
			}
			break;
		}

		// Pull the response from the CSG V2Search API
		final String requestBody = APIHelper.GetrequestBody(data_Search);
		final JSONObject jsonrequestBody = Template.convertStringtoJsonObject(requestBody);
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);

		final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, jsonrequestBody,
				"https://content-service.stg-prsn.com/csg/api/v2/search");
		ReportHelper.log(LogStatus.PASS, "Response", response.getBody().asString());

		// Validate Field To return given in request is present in both Database
		// and V2SearchAPI and profile URL
		final List<String> fieldToreturn = APIHelper.FeildToReturnasArray("$.fieldsToReturn", data_Search.get("api_reqBody"));
		String FirsteachExactFieldToreturn;
		for (String s1 : fieldToreturn) {
			FirsteachExactFieldToreturn = PropLoad.getTestData(s1).split(",")[1].trim();
			if (FirsteachExactFieldToreturn.contains(".") == true) {
				final String jsonpath = FirsteachExactFieldToreturn.replaceAll("\\.", "\\..");
				final String value = APIHelper.retriveValuefromJson("$.." + jsonpath, response.getBody().asString());
				final String value1 = APIHelper.retriveValuefromJson("$.." + jsonpath, DataBaseresponse.getBody().asString());

				String completeDataJsonPath = null;
				if (jsonpath.contains("schema:")) {
					completeDataJsonPath = jsonpath.replace("schema:", "");
				}
				ReportHelper.log(LogStatus.INFO,"Status","******************* wholedatabase" + "$..products[" + ProductIdPosition + "].."
						+ completeDataJsonPath);

				final String value2 = APIHelper
						.retriveValuefromJson("$..products[" + ProductIdPosition + "].." + completeDataJsonPath,
								webCredentials_rest
										.getCall("https://productization-stg.pearsoned.com/us-api/v2/products")
										.getBody().asString());
				Assert.assertEquals(value.toString(), value1.toString(),
						"There is mismatch in DataBase and V2SearchAPI");
				Assert.assertEquals(value.toString(), value2.toString(),
						"There is mismatch in DataBase and V2SearchAPI");
				// AllProduct.contains(value);
				ReportHelper.log(LogStatus.PASS, s1, "Actual response:" + s1 + value + "\n" + "Data Store Response" + s1
						+ value1 + "\n" + "Elastic Search Response" + s1 + value2);
			} else {
				final String jsonpath = FirsteachExactFieldToreturn;
				final String value = APIHelper.retriveValuefromJson("$.." + jsonpath, response.getBody().asString());
				final String value1 = APIHelper.retriveValuefromJson("$.." + jsonpath, DataBaseresponse.getBody().asString());

				String completeDataJsonPath = null;
				if (jsonpath.contains("schema:")) {
					completeDataJsonPath = jsonpath.replace("schema:", "");
				}
				final String value2 = APIHelper
						.retriveValuefromJson("$..products[" + ProductIdPosition + "].." + completeDataJsonPath,
								webCredentials_rest
										.getCall("https://productization-stg.pearsoned.com/us-api/v2/products")
										.getBody().asString());
				Assert.assertEquals(value.toString(), value1.toString(),
						"There is mismatch in DataBase and V2SearchAPI");
				Assert.assertEquals(value.toString(), value2.toString(),
						"There is mismatch in DataBase and V2SearchAPI");
				ReportHelper.log(LogStatus.PASS, s1, "Actual response:" + s1 + value + "\n" + "Data Store Response" + s1
						+ value1 + "\n" + "Elastic Search Response" + s1 + value2);
			}
		}
	}

	public void collection(LinkedHashMap<String, String> data_Search) throws Exception {
		final String requestBody = APIHelper.GetrequestBody(data_Search);
		final JSONObject json = Template.convertStringtoJsonObject(requestBody);
		final Response rr = webCredentials_rest.postCallWithBodyParam(json,
				"https://dragonfly.stg-openclass.com:443/pxereader-cm/api/cm/ingest");
		final String expectedIndexid = APIHelper.FeildToReturnString("$.indexId", requestBody);
		final String actualIndexid = APIHelper.FeildToReturnString("$.indexId", rr.getBody().asString());
		Assert.assertEquals(actualIndexid, expectedIndexid);

		final Response elasticSearch = webCredentials_rest
				.getCall("http://10.199.0.189:9200/collectionindex/_search?q=_id:" + expectedIndexid);
		final JSONArray arrayValuesFromJson = APIHelper.retrivejsonArrayFromJson("$..metadata", requestBody);

		for (Object jsonObject : arrayValuesFromJson) {
			@SuppressWarnings("unchecked")
			final
			Map<String, String> set = (Map<String, String>) jsonObject;
			@SuppressWarnings("rawtypes")
			final
			Iterator it = set.entrySet().iterator();

			while (it.hasNext()) {
				@SuppressWarnings("rawtypes")
				final
				Map.Entry pair = (Map.Entry) it.next();
				final String ResponseEachKey = pair.getKey().toString();
				String dynamicJson = null;
				dynamicJson = PropLoad.getTestData("collection." + ResponseEachKey).trim();
				if (!"".equals(dynamicJson)) {
					final String actualedValue = APIHelper.retriveValuefromJson(dynamicJson,
							elasticSearch.getBody().asString());
					final String expectedVAlue = APIHelper.retriveValuefromJson("$.." + ResponseEachKey, requestBody);
					ReportHelper.log(LogStatus.INFO,"Comparison", 
							actualedValue + " is returned as actual " + expectedVAlue + " is returned as expected ");
					Assert.assertEquals(actualedValue, expectedVAlue);
					ReportHelper.log(LogStatus.PASS, ResponseEachKey,
							"Actual " + actualedValue + " Expected " + expectedVAlue);
				}
			}
		}
	}

	public void exchange_Disp(LinkedHashMap<String, String> data_Search) throws Exception {
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final String requestbody = data_Search.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);
		// String appId = Template.getSingleValue(data_Search, "appId_header");
		final Response actResponse = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);

		final String[] expFieldnames = Template.getStringArrayValfromJson(reqBodyJson, expFieldName_reqField);

		final String elasticSearchURL = Constants.apiElasticSearchUrl + data_Search.get(Constants.elasticSearchUrl);
		final String dataStoreURL = Constants.apiDataStoreSearchUrl + data_Search.get(Constants.dataStoreSearchUrl);

		final String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		ReportHelper.log(LogStatus.INFO, "Response ", actResponse.asString());
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);

		// 1. Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(actResponse, expStatus_code);
		// i>> get v2Search API response
		final Map<String, Map<String, String>> actResNodeValue = Template.getResponseNodeValues_exchangeDisp(expFieldnames,
				actResponse);
		// ii>> get DatStore (Profile) API response
		final Response elasticSearchResponse = webCredentials_rest.getCall(elasticSearchURL);
		final Map<String, Map<String, String>> esResNodeValue = Template
				.getResponseNodeValues_exchangeDisp_elasticSearch(expFieldnames, elasticSearchResponse);
		// ii>> get DatStore (Profile) API response
		final Set<String> actResNodeValueKeys = actResNodeValue.keySet();
		final Response dataStoreResponse = webCredentials_rest.getCall(dataStoreURL);
		final Map<String, Map<String, String>> dataStoreResNodeValue = Template
				.getResponseNodeValues_exchangeDisp_dataStoreSearch(expFieldnames, dataStoreResponse,
						actResNodeValueKeys);
		// iv.>> Comparing the V2Search ,DataStore & Elastic search response
		CSGHelper.validateDataStoreValues_exchangeDisp(actResNodeValue, esResNodeValue, dataStoreResNodeValue,
				expFieldnames, "1", "1");

	}
}