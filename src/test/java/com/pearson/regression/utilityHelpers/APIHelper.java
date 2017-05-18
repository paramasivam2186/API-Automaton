package com.pearson.regression.utilityHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;

import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.relevantcodes.extentreports.LogStatus;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

public class APIHelper {

	private static RESTServiceBase webCredentials_rest = new RESTServiceBase();
	private static Response response;

	/**
	 * To get the response for a POST call with request body
	 * @param URI
	 * @param jsonBody
	 * @return
	 */
	public static Response postCallwithRequestBody(String URI, String jsonBody) {
		try {
			// convert the request_body to json
			final JSONObject json = Template.convertStringtoJsonObject(jsonBody);
			// returns the response for the current method
			response = webCredentials_rest.postCallWithBodyParam(json, URI);
		} catch (Exception e) {
		}
		return response;
	}

	/**
	 * To get the response for a POST call with request body and header
	 * @param URI
	 * @param jsonBody
	 * @param data_Search
	 * @return
	 */
	public static Response postCallwithRequestBodyandheader(String URI, String jsonBody,
			LinkedHashMap<String, String> data_Search) {
		try {

			final JSONObject json = Template.convertStringtoJsonObject(jsonBody);
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json, URI);
			return response;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * To get the response for a POST call with array request body
	 * @param URI
	 * @param jsonBody
	 * @return
	 */
	public static Response postCallwithArrayRequestBody(String URI, String jsonBody) {
		try {
			final JSONArray jsonarr = Template.convertStringtoJsonarray(jsonBody);
			response = webCredentials_rest.postCallWithReqBodyAsJsonArray(jsonarr, URI);
			return response;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * This Method is to generate URI for CSG
	 * @param data_Search
	 * @return
	 */
	public static String URIgenerator_CSG(LinkedHashMap<String, String> data_Search) {
		try {
			final String contentAPIUrl = PropLoad.getConfigData("QA_BaseURL").trim();
			// Get request URI
			final String reqURL = data_Search.get(PropLoad.getTestData("API_reqURL_Excel")).trim();
			// append request and base URI
			final String FinalAPIURI = contentAPIUrl + reqURL;
			LoggerUtil.log("URI", FinalAPIURI, "SubStep", null);

			return FinalAPIURI;
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
		}
		return null;
	}

	/**
	 * This method is to generate URI for CM
	 * @param data_Search
	 * @return
	 */
	public static String URIgenerator_CM(LinkedHashMap<String, String> data_Search) {
		try {
			final String contentAPIUrl = PropLoad.getConfigData("cmURL");
			// Get request URI
			final String reqURL = data_Search.get(PropLoad.getTestData("API_reqURL_Excel")).trim();
			// append request and base URI
			final String FinalAPIURI = contentAPIUrl + reqURL;
			LoggerUtil.log("URI", FinalAPIURI, "SubStep", null);
			return FinalAPIURI;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * This method is to get the request body from the data search
	 * @param data_Search
	 * @return
	 */
	public static String GetrequestBody(LinkedHashMap<String, String> data_Search) {
		try {
			final String responseBodyString = data_Search.get(PropLoad.getTestData("API_requestbody"));
			LoggerUtil.log("responseBodyString", responseBodyString, "SubStep", null);
			return responseBodyString;

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
		}
		return null;
	}

	/**
	 * This method is to validate the status code
	 * @param response
	 * @param data_Search
	 */
	public static void validaStatusCode(Response response, LinkedHashMap<String, String> data_Search) {
		try {
			final int expStatusCode = Integer.parseInt(data_Search.get(PropLoad.getTestData("API_expStatusCode_Excel")));
			if(expStatusCode==response.getStatusCode()){
			LoggerUtil.log("Validate Response code", Integer.toString(expStatusCode), Constants.subStep, null);
			ReportHelper.log(LogStatus.PASS, "Validate Response code", Integer.toString(expStatusCode) + "");
			Assert.assertTrue(true);
			}
			else
			{
				LoggerUtil.log("Validate Response code", Integer.toString(expStatusCode), Constants.subStep, null);
				ReportHelper.log(LogStatus.FAIL, "Validate Response code " + Integer.toString(expStatusCode), "Does not matched with expected " + response.getStatusCode());
				Assert.assertTrue(false);
			}
				
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
		}
	
	}

	/**
	 * Method to validate the error message
	 * @param response
	 * @param data_Search
	 */
	public static void validaErrorMessage(Response response, LinkedHashMap<String, String> data_Search) {
		try {
			final String errorStatus = APIHelper.retriveValuefromJson("$..message", response.getBody().asString());
			Assert.assertEquals(data_Search.get("errorMessage"), errorStatus);
			ReportHelper.log(LogStatus.PASS, "errorMessage",
					"errorMessage retunred as expected " + data_Search.get("errorMessage"));
		} catch (Exception e) {
		}
	}

	/**
	 * To get response for a GET call with paramter
	 * @param data_Search
	 * @param contentAPIUrl
	 * @return
	 * @throws Exception
	 */
	public static Response api_getCallWithParameter(LinkedHashMap<String, String> data_Search, String contentAPIUrl)
			throws Exception {
		final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);
		LoggerUtil.log("api_getCallWithParameter", parameters.toString(), Constants.subStep, null);
		response = webCredentials_rest.getCallWithParameters(parameters, contentAPIUrl);
		return response;

	}

	/**
	 * Method to retrieve value from JSON
	 * @param jsonpath
	 * @param content
	 * @return
	 */
	public static String retriveValuefromJson(String jsonpath, String content) {
		try {
			String valuefromJson = null;
			final JsonPath json = JsonPath.compile(jsonpath);
			final List<Object> value = json.read(content);
			for (Object o : value) {
				valuefromJson = o.toString();
			}
			return valuefromJson;

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
		}
		return null;

	}

	/**
	 * To get group size
	 * @param jsonpath
	 * @param content
	 * @return
	 */
	public static int groupSize(String jsonpath, String content) {
		final JsonPath json = JsonPath.compile(jsonpath);
		final List<Object> value = json.read(content);
		return value.size();
	}

	/**
	 * Method to get the list of fields to return in array
	 * @param jsonpath
	 * @param content
	 * @return
	 */
	public static List<String> FeildToReturnasArray(String jsonpath, String content) {
		List<String> value = new ArrayList<String>();
		final JsonPath json = JsonPath.compile(jsonpath);
		value = json.read(content);
		return value;
	}

	/**
	 * Method to get the list of fields to return as string
	 * @param jsonpath
	 * @param content
	 * @return
	 */
	public static String FeildToReturnString(String jsonpath, String content) {
		final JsonPath json = JsonPath.compile(jsonpath);
		final String value = json.read(content);
		return value;
	}

	/**
	 * To validate the fields to return
	 * @param response
	 * @param fieldToreturn
	 * @return
	 */
	public static Boolean validateFieldToreturnForPOST(Response response, List<String> fieldToreturn) {
		try {
			Boolean success = false;
			String retunredValue = "";

			for (String o : fieldToreturn) {
				String schemafield = "";
				String locator = "";

				if (o.contains("author")) {
					schemafield = "schema:author";
				} else if (o.contains("provider")) {
					schemafield = "_providerSystem";
				} else if (o.contains("_source")) {
					schemafield = "_source";
				} else if (o.contains("_permissions")) {
					schemafield = "_permissions";
				} else if (o.contains("availability")) {
					schemafield = "availability";
				} else if (o.contains("price")) {
					schemafield = "price";
				} else if (o.contains("priceCurrency")) {
					schemafield = "priceCurrency";
				} else {
					schemafield = "schema:" + o;
				}

				if (o.contains("author")) {
					locator = "$..schema:author";
				} else if (o.contains("_providerSystem")) {
					locator = "$.._providerSystem..name";
				} else if (o.contains("_source")) {
					locator = "$.._source";
				} else if (o.contains("_permissions")) {
					locator = "$.._permissions[*]";
				} else if (o.contains("availability")) {
					locator = "$..availability";
				} else if (o.contains("price")) {
					locator = "$..price";
				} else if (o.contains("priceCurrency")) {
					locator = "$..priceCurrency";
				} else {
					locator = "$..schema:" + o;
				}
				if (response.getBody().asString().contains(schemafield)) {
					retunredValue = APIHelper.retriveValuefromJson(locator, response.getBody().asString());
					if (retunredValue.isEmpty()) {
						ReportHelper.log(LogStatus.INFO, "There is no value returned", "There is no value returned");
					} else {
						ReportHelper.log(LogStatus.INFO, "Field that are not Empty", "+++++++++++++++++++Field that are not Empty++++++++++++++++++++++++" + schemafield);
						// LoggerUtil.log("validateFieldToreturn",schemafield,
						// Constants.subStep, null);
						// count =count + 1;
						success = true;
						break;
					}
				}
			}
			// if(count == fieldtoreTurnCount ) success=true; else
			// success=false;
			return success;
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Exception", "The Field to retun may be null in this cases check them manually");
		}
		return false;
	}

	/**
	 * To verify if the json object is sorted in ASC order
	 * @param jsonpath
	 * @param content
	 * @return
	 */
	public static boolean IsJsonObjectSortedAsc(String jsonpath, String content) {
		final JsonPath json = JsonPath.compile(jsonpath);
		final List<Object> value = json.read(content);
		return isSorted(value);
	}

	/**
	 * To verify if the json object is sorted in DES order
	 * @param jsonpath
	 * @param content
	 * @return
	 */
	public static boolean IsJsonObjectSortedDesc(String jsonpath, String content) {
		final JsonPath json = JsonPath.compile(jsonpath);
		final List<Object> value = json.read(content);
		return isSortedDesc(value);
	}

	/**
	 * To verify if the date sorted in ASC order
	 * @param jsonpath
	 * @param content
	 * @return
	 */
	public static boolean IsJsonObjectDateSortedAsc(String jsonpath, String content) {
		final JsonPath json = JsonPath.compile(jsonpath);
		final List<Object> value = json.read(content);
		return dateSorted(value);

	}

	/**
	 * To get the value of a specific field from the data search
	 * @param data_Search
	 * @param fieldName
	 * @return
	 */
	public static String getSingleValue(LinkedHashMap<String, String> data_Search, String fieldName) {
		fieldName = data_Search.get(fieldName.trim());
		final String array = fieldName.split("::")[1];
		return array;
	}

	/**To lit the query string
	 * @param data_Search
	 * @return
	 */
	public static String getCallexactPharseSplitter(LinkedHashMap<String, String> data_Search) {
		try {
			final String searchContenttobeSplited = getSingleValue(data_Search,
					PropLoad.getTestData("V2Search_queryString_Excel"));
			String temp = searchContenttobeSplited.split(":")[1];
			temp = temp.replaceAll("\\\\", "");
			temp = temp.replace("\"\"", "");
			LoggerUtil.log("URI", temp, "SubStep", null);
			return temp.trim();
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * To pull query string from the json body
	 * @param searchContenttobeSplited
	 * @return
	 */
	public static String pullQueryStringExactPhrasefromJsonBody(String searchContenttobeSplited) {
		String temp = searchContenttobeSplited.replaceAll("\\\\", "");
		temp = temp.replace("\"\"", "");
		temp = temp.replace("\"", "");
		return temp.trim();
	}

	/**
	 * To pull query string for specific field
	 * @param jsonpath
	 * @param content
	 * @return
	 */
	public static String pullQueryStringExactPhraseforSpecificField(String jsonpath, String content) {
		final String searchContenttobeSplited;
		String valuefromJson = null;
		final JsonPath json = JsonPath.compile(jsonpath);
		final List<Object> value = json.read(content);
		for (Object o : value) {
			valuefromJson = o.toString();
		}

		searchContenttobeSplited = valuefromJson;
		String temp = searchContenttobeSplited.replaceAll("\\\\", "");
		temp = temp.replace("\"\"", "");
		temp = temp.replace("\"", "");
		temp = temp.split(":")[1];
		temp = temp.trim();
		return temp.trim();
	}

	/**
	 * To verify sort
	 * @param ls
	 * @return
	 */
	public static boolean isSorted(List<Object> ls) {
		boolean isSorted = true;
		for (int i = 0; i < ls.size() - 1; i++) {
			// current String is > than the next one (if there are equal list is
			// still sorted)
			if (ls.get(i).toString().compareTo(ls.get(i + 1).toString()) > 0) {
				isSorted = false;
				break;
			}
		}
		return isSorted;
	}

	/**
	 * To verify Sort in descending order
	 * @param ls
	 * @return
	 */
	public static boolean isSortedDesc(List<Object> ls) {

		boolean isSorteddesc = true;
		for (int i = 0; i < ls.size() - 1; i++) {
			// current String is > than the next one (if there are equal list is
			// still sorted)
			if (ls.get(i).toString().compareTo(ls.get(i + 1).toString()) > 0) {
				isSorteddesc = true;
			} else {
				isSorteddesc = false;
				// System.out.println(isSorted);
				break;
			}
		}
		return isSorteddesc;

	}

	/**
	 * @param ls
	 * @return
	 */
	public static boolean isSortedDescString(List<String> ls) {

		boolean isSorteddesc = true;
		for (int i = 0; i < ls.size() - 15; i++) {
			// current String is > than the next one (if there are equal list is
			// still sorted)
			if (ls.get(i).compareTo(ls.get(i + 1)) > 0) {
				isSorteddesc = true;
			} else {
				isSorteddesc = false;
				// System.out.println(isSorted);
				break;
			}
		}
		return isSorteddesc;

	}

	/**
	 * To verify the date sort
	 * @param dategiven
	 * @return
	 */
	public static boolean dateSorted(List<Object> dategiven) {
		boolean dateSorted = true;
		for (int i = 0; i < dategiven.size() - 1; i++) {
			// current String is > than the next one (if there are equal list is
			// still sorted)
			final String given = dategiven.get(i).toString().split("T")[0];
			final String previous = dategiven.get(i + 1).toString().split("T")[0];
			if ((given.compareToIgnoreCase(previous)) > 0) {
				dateSorted = false;
				break;
			}
		}
		return dateSorted;
	}

	/**
	 * @param jsonpath
	 * @param content
	 * @return
	 */
	public static String specificFieldOR(String jsonpath, String content) {
		String valuefromJson = null;
		final JsonPath json = JsonPath.compile(jsonpath);
		final List<Object> value = json.read(content);
		for (Object o : value) {
			valuefromJson = o.toString();
		}
		return valuefromJson;
	}

	/**
	 * Method to perform Multi-phase sort
	 * @param jsonpath
	 * @param content
	 * @return
	 */
	public static boolean MultiPhaseSort(String jsonpath, String content) {

		final String json1stpart = jsonpath.split("\\*")[0];
		final String json2ndpart = jsonpath.split("\\*")[1];

		final JsonPath json1 = JsonPath.compile(jsonpath);
		final List<Object> value = json1.read(content);

		Boolean finalsuccess = false;

		for (int i = 0; i < value.size(); i++) {
			final Boolean issorted = APIHelper.IsJsonObjectSortedAsc(json1stpart + i + json2ndpart, content);
			if (issorted == true) {
				finalsuccess = true;
				continue;
			} else {
				finalsuccess = false;
				break;
			}
		}
		return finalsuccess;
	}

	/**
	 * To get response for a GET or POST call
	 * @param data_Search
	 * @return
	 */
	public static Response get_or_post_call(LinkedHashMap<String, String> data_Search) {
		try {
			final String FinalAPIURI;
			final Response response;

			if (data_Search.get("request_body").trim().isEmpty() == true) {
				final String contentAPIUrl = PropLoad.getConfigData("cmURL");
				final String reqURL = data_Search.get("URL").trim();
				FinalAPIURI = contentAPIUrl + reqURL;
				response = webCredentials_rest.getCall(FinalAPIURI);
			} else {
				final String contentAPIUrl = PropLoad.getConfigData("cmURL");
				final String reqURL = data_Search.get("URL").trim();
				FinalAPIURI = contentAPIUrl + reqURL;
				final String requestBody = GetrequestBody(data_Search);
				final JSONObject json = Template.convertStringtoJsonObject(requestBody);
				response = webCredentials_rest.postCallWithBodyParam(json, FinalAPIURI);
			}
			return response;

		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * Method to retrieve value for a specific key
	 * @param parameters
	 * @param Key
	 * @return
	 */
	public static String getValueforKey_SingleSearch(Map<String, String> parameters, String Key) {
		String expected = null;
		@SuppressWarnings("rawtypes")
		final
		Iterator it = parameters.entrySet().iterator();
		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			final
			Map.Entry pair = (Map.Entry) it.next();
			if (pair.getKey().equals(Key)) {
				expected = (String) pair.getValue();
			}
		}
		return expected;
	}

	/**
	 * Method to get the fields to be returned 
	 * @param parameters
	 * @return
	 */
	public static List<String> getValueFieldToreturn(Map<String, String> parameters) {
		// String[] expectedFieldToreturn = new String[10]();
		String[] expectedFieldToreturn = new String[50];
		String expected = null;
		@SuppressWarnings("rawtypes")
		final
		Iterator it = parameters.entrySet().iterator();
		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			final
			Map.Entry pair = (Map.Entry) it.next();
			if (pair.getKey().equals("fieldsToReturn")) {
				expected = (String) pair.getValue();
			}
			expectedFieldToreturn = expected.split(",");
		}
		final List<String> stringList = new ArrayList<String>(Arrays.asList(expectedFieldToreturn));
		return stringList;
	}

	/**
	 * Method to get the fields to be returned
	 * @param FieldToReturn
	 * @param response
	 * @return
	 */
	public static Boolean postValueFieldToreturn(String FieldToReturn, Response response) {
		final JSONArray arrayValuesFromJson = APIHelper.retrivejsonArrayFromJson("$.searchResults[*].source",
				response.asString());
		Boolean success = false;
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
				if (pair.getKey().toString().toLowerCase().contains(FieldToReturn.toLowerCase()) == true) {
					success = true;
					break;
				}
			}
			if (success == true) {
				break;
			}
		}
		return success;
	}

	/**
	 * @param upToNCharacters
	 * @param N
	 * @return
	 */
	public static String upToNCharacters(String upToNCharacters, int N) {
		final String retrunString = upToNCharacters.substring(0, Math.min(upToNCharacters.length(), N));
		return retrunString;
	}

	/**
	 * @param jsonpath
	 * @param responseContent
	 * @return
	 */
	public static JSONArray retrivejsonArrayFromJson(String jsonpath, String responseContent) {
		final JsonPath json = JsonPath.compile(jsonpath);
		final JSONArray value = json.read(responseContent);
		return value;
	}

	/**
	 * To split the string
	 * @param parameters
	 * @return
	 */
	public static List<String> get_SplitStringForLogicalOperator(Map<String, String> parameters) {
		String[] expectedFieldToreturn = new String[50];
		List<String> stringList = new ArrayList<String>();

		final String withLogicalOperatorSearchText = getValueforKey_SingleSearch(parameters, "queryString");

		if (withLogicalOperatorSearchText.contains("AND")) {
			expectedFieldToreturn = withLogicalOperatorSearchText.split("AND");
			stringList = new ArrayList<String>(Arrays.asList(expectedFieldToreturn));
		} else if (withLogicalOperatorSearchText.contains("OR")) {
			expectedFieldToreturn = withLogicalOperatorSearchText.split("OR");
			stringList = new ArrayList<String>(Arrays.asList(expectedFieldToreturn));
		}

		else if (withLogicalOperatorSearchText.contains("NOT")) {
			expectedFieldToreturn = withLogicalOperatorSearchText.split("NOT");
			stringList = new ArrayList<String>(Arrays.asList(expectedFieldToreturn));
		}
		return stringList;
	}

	/**
	 * To split the string based on the logical operator
	 * @param requestBody
	 * @return
	 */
	public static List<String> post_SplitStringForLogicalOperator(String requestBody) {
		String[] expectedFieldToreturn = new String[50];
		List<String> stringList = new ArrayList<String>();

		final String withLogicalOperatorSearchText = retriveValuefromJson("$..queryString", requestBody);

		if (withLogicalOperatorSearchText.contains("AND")) {
			expectedFieldToreturn = withLogicalOperatorSearchText.split("AND");
			stringList = new ArrayList<String>(Arrays.asList(expectedFieldToreturn));
		} else if (withLogicalOperatorSearchText.contains("OR")) {
			expectedFieldToreturn = withLogicalOperatorSearchText.split("OR");
			stringList = new ArrayList<String>(Arrays.asList(expectedFieldToreturn));
		} else if (withLogicalOperatorSearchText.contains("NOT")) {
			expectedFieldToreturn = withLogicalOperatorSearchText.split("NOT");
			stringList = new ArrayList<String>(Arrays.asList(expectedFieldToreturn));
		}
		return stringList;
	}

	/**
	 * to return the count field valule
	 * @param fullContentJSONpath
	 * @param response
	 * @param Key
	 * @return
	 */
	public static int returnCount(String fullContentJSONpath, Response response, String Key) {

		int count = 0;
		final JSONArray arrayValuesFromJson = APIHelper.retrivejsonArrayFromJson(fullContentJSONpath, response.asString());
		@SuppressWarnings("unused")
		final
		Boolean success = false;

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
				if (pair.getKey().toString().contains(Key)) {
					count = count + 1;
					break;
				}
			}
		}
		ReportHelper.log(LogStatus.INFO,"Count", "count of search in specific field from the response" + count);
		return count;
	}

	/**
	 * To get the fields to return as object
	 * @param jsonpath
	 * @param content
	 * @return
	 */
	public static List<Object> FeildToReturnasObject(String jsonpath, String content) {
		final JsonPath json = JsonPath.compile(jsonpath);
		final List<Object> value = json.read(content);
		return value;
	}

	/**
	 * To retrieve the value for a specific jsonpath
	 * @param jsonpath
	 * @param res
	 * @return
	 */
	public static String getJsonValue(String jsonpath, Response res) {
		final JsonPath json = JsonPath.compile("$.." + jsonpath);
		final List<Object> value = json.read(res.asString());
		return value.toString();
	}

	/**
	 * To return the count value as json object
	 * @param fullContentJSONpath
	 * @param response
	 * @param Key
	 * @return
	 */
	public static int returnCountJsonObject(String fullContentJSONpath, Response response, String Key) {

		int count = 0;
		final List<Object> arrayValuesFromJson = APIHelper.FeildToReturnasObject(fullContentJSONpath, response.asString());
		@SuppressWarnings("unused")
		final
		Boolean success = false;

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
				if (pair.getKey().toString().contains(Key)) {
					count = count + 1;
					break;
				}
			}
		}
		ReportHelper.log(LogStatus.INFO, "FeildToReturnasObject--->Count", "count of search in specific field from the response" + count);
		return count;
	}

	/**
	 * To retrieve the value of a specific field
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public static String getExpectedFieldValue(String fieldName) throws Exception {
		String expVal = Constants.emptyString;
		String[] ExactFieldToreturnArray = new String[30];
		final String FirsteachExactFieldToreturn = PropLoad.getTestData(fieldName).split(",")[1].trim();
		if (FirsteachExactFieldToreturn.contains(".") == true) {
			ExactFieldToreturnArray = FirsteachExactFieldToreturn.split("\\.");
			for (int i = 0; i < ExactFieldToreturnArray.length; i++) {
				expVal = ExactFieldToreturnArray[i];
			/*	ReportHelper.log(LogStatus.INFO, "getExpectedFieldValue--->Property ", "Data pulled from property file ------" + FirsteachExactFieldToreturn
						+ "-----------------  this field has two validation Property");*/
				if (!"".equals(expVal)) {
					break;
				}
			}
		} else if (FirsteachExactFieldToreturn.contains(".") == false
				&& FirsteachExactFieldToreturn.contains(fieldName) == true) {
			expVal = FirsteachExactFieldToreturn;
			/*ReportHelper.log(LogStatus.INFO,"getExpectedFieldValue--->Property File" , "Data pulled from property file ------" + FirsteachExactFieldToreturn
					+ " ----------------- this field has one validation Property");*/
		}
		return expVal;
	}

	/**
	 * To retrieve the value of a specific field
	 * @param fieldToreturn
	 * @return
	 * @throws Exception
	 */
	public static List<String> getExpectedField(String[] fieldToreturn) throws Exception {
		String[] ExactFieldToreturnArray = new String[30];
		final List<String> ExactFieldToreturn = new ArrayList<String>();
		String FirsteachExactFieldToreturn;
		try {
			for (String s1 : fieldToreturn) {
				FirsteachExactFieldToreturn = PropLoad.getTestData(s1).split(",")[1].trim();
				if (FirsteachExactFieldToreturn.contains(".") == true) {
					ExactFieldToreturnArray = FirsteachExactFieldToreturn.split("\\.");
					ExactFieldToreturn.add(ExactFieldToreturnArray[0]);
				/*	ReportHelper.log(LogStatus.INFO,"getExpectedField--->Property File" , "Data pulled from property file ------" + FirsteachExactFieldToreturn
							+ "-----------------  this field has two validation Property");*/

				} else if (FirsteachExactFieldToreturn.contains(".") == false
						&& FirsteachExactFieldToreturn.contains(s1) == true) {
					ExactFieldToreturn.add(FirsteachExactFieldToreturn);
				/*	ReportHelper.log(LogStatus.INFO,"getExpectedField--->Property File" , "Data pulled from property file ------" + FirsteachExactFieldToreturn
							+ " ----------------- this field has one validation Property");*/
				}
			}
		} catch (Exception e) {
			LoggerUtil.log("Expected field name not present in the test data property file", "PASS", "SubStep", null);
		}
		return ExactFieldToreturn;
	}

	/**
	 * @param fieldToreturn
	 * @return
	 * @throws Exception
	 */
	public static List<String> getExpectedField_ExactMathches(String[] fieldToreturn) throws Exception {
		final List<String> ExactFieldToreturn = new ArrayList<String>();
		String FirsteachExactFieldToreturn;
		try {
			for (String s1 : fieldToreturn) {
				FirsteachExactFieldToreturn = PropLoad.getTestData(s1).split(",")[1].trim();
				if (FirsteachExactFieldToreturn.contains(".") == true) {
					ExactFieldToreturn.add(FirsteachExactFieldToreturn);
				} else if (FirsteachExactFieldToreturn.contains(".") == false
						&& FirsteachExactFieldToreturn.contains(s1) == true) {
					ExactFieldToreturn.add(FirsteachExactFieldToreturn);
					ReportHelper.log(LogStatus.INFO,"getExpectedField_ExactMathches--->Property File" , "Data pulled from property file ------" + FirsteachExactFieldToreturn
							+ " ----------------- this field has one validation Property");
				}
			}
			ReportHelper.log(LogStatus.INFO,"Final Validation List in the JSON array","Final Validation List in the JSON array----------------- " + ExactFieldToreturn);
		} catch (Exception e) {
			LoggerUtil.log("Expected field name not present in the test data property file", "PASS", "SubStep", null);
		}
		return ExactFieldToreturn;
	}

	/**
	 * To get the fields to return for a POST call
	 * @param response
	 * @param fieldToreturn
	 * @param fieldPath
	 * @throws Exception
	 */
	public static void fieldtoReturn_post(Response response, String[] fieldToreturn, String fieldPath)
			throws Exception {
		final List<String> ExactFieldToreturn = getExpectedField(fieldToreturn);
		Boolean FinalSuccess = false;
		final StringBuilder td = new StringBuilder();
		final StringBuilder tdfail = new StringBuilder();
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(fieldPath, response.asString());
		final int countVal = arrayValuesFromJson.size();
		if (countVal == 0) {
			FinalSuccess = true;
			LoggerUtil.log("Count is returned as Zero - please check your QueryString", "PASS", "SubStep", null);
		} else {
			for (String newValue : ExactFieldToreturn) {
				final JsonPath json = JsonPath.compile("$.." + newValue);
				final List<Object> value = json.read(response.getBody().asString());
				if (value.size() > 0) {
					FinalSuccess = true;
					td.append(newValue + ",");
				} else {
					FinalSuccess = false;
					tdfail.append(newValue + ",");
				}
			}
		}
		if (FinalSuccess == true) {
			ReportHelper.log(LogStatus.PASS, "fieldtoReturn is present in the response ", td.toString());
			LoggerUtil.log("fieldtoReturn present in the response " + td.toString(), "PASS", "SubStep", null);
		} else {
			ReportHelper.log(LogStatus.FAIL, "fieldtoReturn is not present in the response ", tdfail.toString());
			LoggerUtil.log("fieldtoReturn is not present in the response " + tdfail.toString(), "FAIL", "SubStep", null);
		}
	}

	/**
	 * @param fieldToreturn
	 * @param appID
	 * @return
	 * @throws Exception
	 */
	public static String[] getExpectedFieldsWithAppId(String[] fieldToreturn, String appID) throws Exception {
		final List<String> ExactFieldToreturn = new ArrayList<String>();
		try {
			for (String s1 : fieldToreturn) {
				final String sName = appID + s1;
				ExactFieldToreturn.add(sName);
			}
		} catch (Exception e) {
			LoggerUtil.log("Expected field name not present in the test data property file", "PASS", "SubStep", null);
		}
		final String[] arr = ExactFieldToreturn.toArray(new String[ExactFieldToreturn.size()]);
		return arr;
	}

	/**
	 * @param fieldToreturn
	 * @return
	 * @throws Exception
	 */
	public static List<String> getExpectedField_ExactMathches_exDisp(String[] fieldToreturn) throws Exception {
		final List<String> ExactFieldToreturn = new ArrayList<String>();
		String FirsteachExactFieldToreturn;
		try {
			for (String s1 : fieldToreturn) {
				FirsteachExactFieldToreturn = PropLoad.getTestData(s1).split(",")[1].trim();
				if (FirsteachExactFieldToreturn.contains(".") == true) {
					ExactFieldToreturn.add(FirsteachExactFieldToreturn);
				} else if (FirsteachExactFieldToreturn.contains(".") == false
						|| FirsteachExactFieldToreturn.contains(s1) == true) {
					ExactFieldToreturn.add(FirsteachExactFieldToreturn);
				}
			}
		} catch (Exception e) {
			LoggerUtil.log("Expected field name not present in the test data property file", "PASS", "SubStep", null);
		}
		return ExactFieldToreturn;
	}
	// new method for csg-excel uri generator

	/**
	 * To generate URI
	 * @param data_Search
	 * @return
	 */
	public static String URIgenerator_Excel(LinkedHashMap<String, String> data_Search) {
		try {
			final String contentAPIUrl = data_Search.get(PropLoad.getTestData("API_baseURL_Excel")).trim();
			// Get request URI
			final String reqURL = data_Search.get(PropLoad.getTestData("API_reqURL_Excel")).trim();
			// append request and base URI
			final String FinalAPIURI = contentAPIUrl + reqURL;
			// LoggerUtil.log("URI", FinalAPIURI, "SubStep", null);
			ReportHelper.log(LogStatus.INFO, "API URL : ", FinalAPIURI);

			return FinalAPIURI;
		} catch (Exception e) {
		}
		return null;
	}

	// ingestion
	/**
	 * To validate the index value
	 * @param response
	 * @return
	 */
	public static boolean validateIsindexedvalue(Response response) {

		final String trueval = response.toString();
		final boolean val;
		if ("true".equals(trueval)) {
			val = true;
		} else {
			val = false;
		}
		return val;
	}

	// get req body as json
	/**
	 * To get request body as json
	 * @param data_Search
	 * @return
	 */
	public static JSONObject GetrequestBodyasJson(LinkedHashMap<String, String> data_Search) {
		JSONObject element = null;
		try {
			final String responseBodyString1 = data_Search.get(PropLoad.getTestData("API_requestbody"));
			final JSONParser jsonParser = new JSONParser();
			element = (JSONObject) jsonParser.parse(responseBodyString1);
			// System.out.println(element);
			// GetrequestBody.put(responseBodyString1, value)
			//// LoggerUtil.log("responseBodyString", responseBodyString,
			// "SubStep", null);
			//// System.out.println("********************************" +
			// responseBodyString);
			// return responseBodyString;

		} catch (Exception e) {
		}
		return element;
	}

	/**
	 * Creates dynamic jsonpath
	 * @param groupByFields
	 * @param partialJSONPath
	 * @return
	 */
	public static String dynamicJSONPathCreation(int groupByFields, String partialJSONPath) {
		final String JSONPath, aggregationBuckets;
		aggregationBuckets = "aggregationBuckets[*].";
		if (groupByFields == 0) {
			JSONPath = partialJSONPath + ".key";
		} else {
			JSONPath = partialJSONPath + "." + StringUtils.repeat(aggregationBuckets, groupByFields - 1) + "key";
		}
		return JSONPath;
	}

	/**
	 * @param JSONPath
	 * @param response
	 * @param groupBy
	 * @return
	 */
	public static Map<Object, Integer> getKeysCount(String JSONPath, Response response, JSONArray groupBy) {
		int hierarchyCount = 0, flag = 0;
		@SuppressWarnings("unchecked")
		final
		// ArrayList<Integer> keysCount = new ArrayList();
		Map<Object, Integer> keysCount = new HashMap<Object, Integer>();
		final String findStr = "aggregationBuckets[*]";
		hierarchyCount = StringUtils.countMatches(JSONPath, findStr);
		flag = hierarchyCount + 1;
		// Map<Object, List<Object>> keys = new HashMap<>();
		int i = 0;
		if (flag == 1) {
			keysCount.put(groupBy.get(i),
					APIHelper.FeildToReturnasObject(JSONPath, response.getBody().asString()).size());
		} else {
			while (flag != 0) {
				keysCount.put(groupBy.get(i),
						APIHelper.FeildToReturnasObject(JSONPath, response.getBody().asString()).size());
				JSONPath = JSONPath.replace("aggregationBuckets[*].", "");
				flag--;
				i++;
			}

		}
		return keysCount;
	}

	/**
	 * To get the key's values
	 * @param JSONPath
	 * @param response
	 * @param groupBy
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "null" })
	public static Map<Object, List<Object>> getKeys(String JSONPath, Response response, JSONArray groupBy) {
		int hierarchyCount = 0, flag = 0;
		@SuppressWarnings("unchecked")
		final
		ArrayList<Integer> keysCount = new ArrayList();
		final String findStr = "aggregationBuckets[*]";
		hierarchyCount = StringUtils.countMatches(JSONPath, findStr);
		flag = hierarchyCount + 1;
		final Map<Object, List<Object>> keys = new HashMap<>();
		int i = 0;
		if (flag == 1) {
			keysCount.add(APIHelper.FeildToReturnasObject(JSONPath, response.getBody().asString()).size());
			keys.put(groupBy.get(i), APIHelper.FeildToReturnasObject(JSONPath, response.getBody().asString()));
		} else {
			while (flag != 0) {
				// System.out.println("JSONPath"+JSONPath);
				keysCount.add(APIHelper.FeildToReturnasObject(JSONPath, response.getBody().asString()).size());
				keys.put(groupBy.get(i), APIHelper.FeildToReturnasObject(JSONPath, response.getBody().asString()));
				// System.out.println("APIHelper.FeildToReturnasObject(JSONPath,
				// response.getBody().asString())"+APIHelper.FeildToReturnasObject(JSONPath,
				// response.getBody().asString()));
				// JSONPath = JSONPath.replace("aggregationBuckets[*].", "");
				JSONPath = StringUtils.replaceOnce(JSONPath, "aggregationBuckets[*].", "");
				flag--;
				i++;

				// System.out.println("keyskeyskeyskeys"+keys.get(0));
				// System.out.println("flagflagflagflagflag" + flag + "iiii" +
				// i);
			}

		}
		return keys;
	}

	// mukli&singl facet
	/**
	 * To get json value from json
	 * @param jsonpath
	 * @param content
	 * @return
	 */
	@SuppressWarnings("null")
	public static JSONArray jsonValuefromJson(String jsonpath, JSONObject content) {

		JSONArray valuefromJson = null;
		try {
			final JsonPath json = JsonPath.compile(jsonpath);
			valuefromJson = json.read(content);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
		}
		return valuefromJson;
	}
}
