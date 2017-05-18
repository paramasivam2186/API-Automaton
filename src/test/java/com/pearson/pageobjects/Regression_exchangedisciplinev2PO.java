package com.pearson.pageobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.testng.Assert;

import com.google.common.collect.Ordering;
import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
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

public class Regression_exchangedisciplinev2PO {

	static RESTServiceBase webCredentials_rest = new RESTServiceBase();
	@SuppressWarnings("unused")
	private HashMap<String, String> data;
	String authtoken = "";

	private static String xAuthKey = PropLoad.getTestData("Auth_XAuthKey");
	private static String qs_reqField = PropLoad.getTestData("V2Search_qs_reqfield");
	private static String expFieldName = PropLoad.getTestData("V2Search_expFields_Excel");
	private static String qs_String = PropLoad.getTestData("V2Search_queryString_Excel");
	private static String expFieldName_reqField = PropLoad.getTestData("V2Search_expFields_reqfield");

	/**
	 * Verify Sort
	 * @param response
	 * @param requestBody
	 * @return
	 */
	public boolean isSortedField1(Response response, String requestBody) {
		try {
			final String order = APIHelper.retriveValuefromJson("$..order", requestBody);
			final String field = APIHelper.retriveValuefromJson("$..field", requestBody);
			Boolean sorted = false;
			if (field.contains("term")) {
				final List<String> getAlley = APIHelper.FeildToReturnasArray("$..searchResults[*]..key",
						response.getBody().asString());
				if (order.contains("DESC") == true) {
					sorted = Ordering.natural().reverse().isOrdered(getAlley);
				} else {
					sorted = Ordering.natural().isOrdered(getAlley);
				}
			} else {
				final List<String> getAlley = APIHelper.FeildToReturnasArray("$..count", response.getBody().asString());
				getAlley.remove(0);
				if (order.contains("DESC") == true) {
					sorted = Ordering.natural().reverse().isOrdered(getAlley);
				} else {
					sorted = Ordering.natural().isOrdered(getAlley);
				}
			}
			return sorted;
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
		return false;
	}

	/**
	 * Single and Multiple term search
	 * @param data_Search
	 * @throws Exception
	 */
	public void getSingleandMultipleTermSearch(LinkedHashMap<String, String> data_Search) throws Exception {
		System.out.println("Getting in to getSingleandMultipleTermSearch method");
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);

		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		// Validating the search query present in JSON response (MatchedFields)
		final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString, applicationId);

		Boolean bSuccess = false;
		bSuccess = CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
				ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMatchedName_JP,
				response.asString());
		if (!bSuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"em tag match validation");
		}
		Assert.assertTrue(bSuccess);
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"em tag match validation");
		}
		LoggerUtil.log("Verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
				"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);

		// Verify Field to return present in json
		final String[] expFieldnames = Template.getFieldNamesFromKey(data_Search, expFieldName);
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSource_JP);

	}

	/**
	 * Logical operator operation for POST call
	 * @param data_Search
	 * @throws Exception
	 */
	public void postLogicalOperator(LinkedHashMap<String, String> data_Search) throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final String requestbody = data_Search.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		final String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString,
				" Application ID: " + applicationId);

		Boolean bSuccess = false;
		bSuccess = CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
				ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMatchedName_JP,
				response.asString());
		if (!bSuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"em tag match validation");
		}
		Assert.assertTrue(bSuccess);
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"em tag match validation");
		}
		LoggerUtil.log("Verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
				"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);

		// Verify Field to return present in json
		final String[] expFieldnames = Template.getStringArrayValfromJson(reqBodyJson, expFieldName_reqField);
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSource_JP);
	}

	/**
	 * To find the list of fields to be returned for a POST call
	 * @param response
	 * @param jsonRequestBody
	 * @throws Exception
	 */
	public void fieldtoReturn_post(Response response, String jsonRequestBody) throws Exception {
		try {
			final List<String> fieldToreturn = APIHelper.FeildToReturnasArray("$.fieldsToReturn", jsonRequestBody);
			final StringBuilder td = new StringBuilder();
			Boolean FinalSuccess = false;
			final StringBuilder tdfail = new StringBuilder();
			String FirsteachExactFieldToreturn;

			for (String s1 : fieldToreturn) {
				FirsteachExactFieldToreturn = PropLoad.getTestData("ExchDisp." + s1).split(",")[1].trim();
				if (FirsteachExactFieldToreturn.contains(".") == true) {
					final String jsonpath = FirsteachExactFieldToreturn.replaceAll("\\.", "\\..");
					final JsonPath json = JsonPath.compile("$.." + jsonpath);
					final List<Object> value = json.read(response.getBody().asString());
					if (value.size() >= 1) {
						FinalSuccess = true;
						td.append(jsonpath + ",");
					} else {
						FinalSuccess = false;
						tdfail.append(jsonpath + ",");
					}
				} else {
					final String jsonpath = FirsteachExactFieldToreturn;
					final JsonPath json = JsonPath.compile("$.." + jsonpath);
					final List<Object> value = json.read(response.getBody().asString());
					if (value.size() >= 1) {
						FinalSuccess = true;
						td.append(jsonpath + ",");
					} else {
						FinalSuccess = false;
						tdfail.append(jsonpath + ",");
					}
				}
			}
			if (FinalSuccess == true) {
				LoggerUtil.log("fieldtoReturn present in the response " + td.toString(), "PASS", "SubStep", null);
				ReportHelper.log(LogStatus.PASS, "fieldtoReturn",
						"fieldtoReturn present in the response " + td.toString());
			} else {
				LoggerUtil.log("fieldtoReturn is not present in the response" + tdfail.toString(), "FAIL", "SubStep",
						null);
				ReportHelper.log(LogStatus.PASS, "fieldtoReturn",
						"fieldtoReturn present in the response " + td.toString());
			}
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * Multi-level facet validation
	 * @param response
	 * @param requestBody
	 */
	public void multiLevelFacet_validation(Response response, JSONObject requestBody) {
		try {
			final String dynamicJSONPath;
			JSONArray groupBy = null;
			groupBy = APIHelper.jsonValuefromJson("$..groupBy[*]", requestBody);
			dynamicJSONPath = APIHelper.dynamicJSONPathCreation(groupBy.size(), "$..searchResults[*]");
			final Map<Object, Integer> keysCount = APIHelper.getKeysCount(dynamicJSONPath, response, groupBy);
			final Map<Object, List<Object>> keysWithValues = APIHelper.getKeys(dynamicJSONPath, response, groupBy);
			Facet_Reusable(groupBy, keysCount, keysWithValues, response);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	// Single and Multi Level Facet Validation
	/**
	 * @param groupByFields
	 * @param keysCount
	 * @param keyswithValues
	 * @param response
	 */
	public void Facet_Reusable(JSONArray groupByFields, Map<Object, Integer> keysCount,
			Map<Object, List<Object>> keyswithValues, Response response) {
		final String FirstPartLocator = "$..searchResults[";
		final String secondPartLocator = "*]..productsList[*]..";
		int k = groupByFields.size() - 1;
		try {
			for (int l = 0; l < groupByFields.size(); l++) {
				final String FirsteachExactFieldToreturn = PropLoad
						.getTestData("ExchDisp." + groupByFields.get(k).toString()).split(",")[1].trim();
				final String FormLocator;
				if (FirsteachExactFieldToreturn.contains(".") == true) {
					FormLocator = FirsteachExactFieldToreturn.replaceAll("\\.", "\\..");
				} else {
					FormLocator = FirsteachExactFieldToreturn;
				}
				final String Locator = FirstPartLocator + secondPartLocator + FormLocator;
				final List<Object> keys = APIHelper.FeildToReturnasObject(Locator, response.getBody().asString());
				final List<Object> keyValuefromResponse = keyswithValues.get(groupByFields.get(l));

				if (keys.size() != 0L && keyValuefromResponse.size() != 0) {
					if (keys.size() == keyValuefromResponse.size()) {
						// Assert.assertTrue(CollectionUtils.isEqualCollection(keys,
						// keyValuefromResponse));
						ReportHelper.log(LogStatus.PASS, groupByFields.get(k) + "- Field Facet",
								"Facet Validation working as Expected");
					} else if (keys.size() > keyValuefromResponse.size()) {
						// Assert.assertTrue(CollectionUtils.containsAny(keyValuefromResponse,
						// keys));
						ReportHelper.log(LogStatus.PASS, groupByFields.get(k) + "- Field Facet",
								"Facet Validation working as Expected");
					} else if (keys.size() == 0) {
						ReportHelper.log(LogStatus.FAIL, groupByFields.get(k) + "- Field Facet",
								"Please check your Fields To Return in the query");
					}
					k--;
				} else {
					ReportHelper.log(LogStatus.FAIL, groupByFields.get(k) + "Check your request and application ID",
							"");
				}
			}
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			Assert.fail(e.getMessage(), e);
		}
	}

	/**
	 * @param response
	 * @param parameters
	 * @return
	 */
	public boolean get_isSortedField1(Response response, Map<String, String> parameters) {
		try {
			final String order = (String) parameters.get("groupBySortOrder").trim();
			final String field = (String) parameters.get("groupBySortField").trim();
			Boolean sorted = false;

			if (field.contains("term")) {
				final List<String> getAlley = APIHelper.FeildToReturnasArray("$..searchResults[*]..key",
						response.getBody().asString());
				if (order.contains("DESC") == true) {
					sorted = Ordering.natural().reverse().isOrdered(getAlley);
				} else {
					sorted = Ordering.natural().isOrdered(getAlley);
				}
			} else {
				final List<String> getAlley = APIHelper.FeildToReturnasArray("$..count", response.getBody().asString());
				getAlley.remove(0);
				if (order.contains("DESC") == true) {
					sorted = Ordering.natural().reverse().isOrdered(getAlley);
				} else {
					sorted = Ordering.natural().isOrdered(getAlley);
				}
			}
			return sorted;
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
		return false;
	}

	/**
	 * @param response
	 * @param parameters
	 * @throws Exception
	 */
	public void get_fieldtoReturn_post(Response response, Map<String, String> parameters) throws Exception {
		try {
			final String[] fieldToreturnString = parameters.get("fieldsToReturn").split(",");
			final List<String> fieldToreturn = new ArrayList<String>(Arrays.asList(fieldToreturnString));

			final StringBuilder td = new StringBuilder();
			Boolean FinalSuccess = false;
			final StringBuilder tdfail = new StringBuilder();
			String FirsteachExactFieldToreturn;

			for (String s1 : fieldToreturn) {
				FirsteachExactFieldToreturn = PropLoad.getTestData("ExchDisp." + s1).split(",")[1].trim();
				if (FirsteachExactFieldToreturn.contains(".") == true) {
					final String jsonpath = FirsteachExactFieldToreturn.replaceAll("\\.", "\\..");
					final JsonPath json = JsonPath.compile("$.." + jsonpath);
					final List<Object> value = json.read(response.getBody().asString());
					if (value.size() >= 1) {
						FinalSuccess = true;
						td.append(jsonpath + ",");
					} else {
						FinalSuccess = false;
						tdfail.append(jsonpath + ",");
					}
				} else {
					final String jsonpath = FirsteachExactFieldToreturn;
					final JsonPath json = JsonPath.compile("$.." + jsonpath);
					final List<Object> value = json.read(response.getBody().asString());
					if (value.size() >= 1) {
						FinalSuccess = true;
						td.append(jsonpath + ",");
					} else {
						FinalSuccess = false;
						tdfail.append(jsonpath + ",");
					}
				}

			}
			if (FinalSuccess == true) {
				LoggerUtil.log("fieldtoReturn present in the response " + td.toString(), "PASS", "SubStep", null);
				ReportHelper.log(LogStatus.PASS, "fieldtoReturn",
						"fieldtoReturn present in the response " + td.toString());
			} else {
				LoggerUtil.log("fieldtoReturn is not present in the response" + tdfail.toString(), "FAIL", "SubStep",
						null);
				ReportHelper.log(LogStatus.PASS, "fieldtoReturn",
						"fieldtoReturn present in the response " + td.toString());
			}
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}

	}

	/**
	 * @param response
	 * @param parameters
	 */
	public void get_multiLevelFacet_validation(Response response, Map<String, String> parameters) {
		try {
			final String dynamicJSONPath;
			final String groupBy_get = APIHelper.getValueforKey_SingleSearch(parameters, "groupBy");
			final String[] mStringArray = groupBy_get.split(",");
			final JSONArray groupBy = new JSONArray();
			for (String m : mStringArray) {
				groupBy.add(m);
			}

			dynamicJSONPath = APIHelper.dynamicJSONPathCreation(groupBy.size(), "$..searchResults[*]");
			final Map<Object, Integer> keysCount = APIHelper.getKeysCount(dynamicJSONPath, response, groupBy);
			final Map<Object, List<Object>> keysWithValues = APIHelper.getKeys(dynamicJSONPath, response, groupBy);
			get_Facet_Reusable(groupBy, keysCount, keysWithValues, response);

		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	// Single and Multi Level Facet Validation
	/**
	 * @param groupByFields
	 * @param keysCount
	 * @param keyswithValues
	 * @param response
	 */
	public void get_Facet_Reusable(JSONArray groupByFields, Map<Object, Integer> keysCount,
			Map<Object, List<Object>> keyswithValues, Response response) {
		final String FirstPartLocator = "$..searchResults[";
		final String secondPartLocator = "*]..productsList[*]..";
		int k = groupByFields.size() - 1;
		try {
			for (int l = 0; l < groupByFields.size(); l++) {
				final String FirsteachExactFieldToreturn = PropLoad
						.getTestData("ExchDisp." + groupByFields.get(k).toString()).split(",")[1].trim();
				final String FormLocator;
				if (FirsteachExactFieldToreturn.contains(".") == true) {
					FormLocator = FirsteachExactFieldToreturn.replaceAll("\\.", "\\..");
				} else {
					FormLocator = FirsteachExactFieldToreturn;
				}
				final String Locator = FirstPartLocator + secondPartLocator + FormLocator;
				final List<Object> keys = APIHelper.FeildToReturnasObject(Locator, response.getBody().asString());
				final List<Object> keyValuefromResponse = keyswithValues.get(groupByFields.get(l));

				if (keys.size() != 0L && keyValuefromResponse.size() != 0) {
					if (keys.size() == keyValuefromResponse.size()) {
						Assert.assertTrue(CollectionUtils.isEqualCollection(keys, keyValuefromResponse));
						ReportHelper.log(LogStatus.PASS, groupByFields.get(k) + "- Field Facet",
								"Facet Validation working as Expected");
					} else if (keys.size() > keyValuefromResponse.size()) {
						Assert.assertTrue(CollectionUtils.containsAny(keyValuefromResponse, keys));
						ReportHelper.log(LogStatus.PASS, groupByFields.get(k) + "- Field Facet",
								"Facet Validation working as Expected");
					} else if (keys.size() == 0) {
						ReportHelper.log(LogStatus.FAIL, groupByFields.get(k) + "- Field Facet",
								"Please check your Fields To Return in the query");
					}
					k--;
				} else {
					ReportHelper.log(LogStatus.FAIL, groupByFields.get(k) + "Check your request and application ID",
							"");
				}
			}
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}

	}

	/**
	 * @param response
	 * @param requestBody
	 * @return
	 * @throws Exception
	 */
	public boolean sortField(Response response, String requestBody) throws Exception {
		try {
			final String fieldtobeSorted = APIHelper.retriveValuefromJson("$..fields", requestBody);
			final List<Object> sort = APIHelper.FeildToReturnasObject("$..sort[0]", response.getBody().asString());
			final List<String> sortedValue = new ArrayList<String>();
			for (Object s : sort) {
				sortedValue.add(s.toString());
			}
			boolean sorted = false;
			if (fieldtobeSorted.contains("ASC")) {
				sorted = Ordering.natural().isOrdered(sortedValue);
			} else if (fieldtobeSorted.contains("DESC")) {
				sorted = Ordering.natural().reverse().isOrdered(sortedValue);
			}
			if (sorted == true) {
				ReportHelper.log(LogStatus.PASS, "Sorting", "Sorting is successful");
			} else {
				ReportHelper.log(LogStatus.FAIL, "Sorting", "$..sort field - Sorting is not successful");
			}
			return sorted;
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
		return false;
	}

	/**
	 * @param response
	 * @param requestBody
	 * @return
	 * @throws Exception
	 */
	public boolean post_Sorting(Response response, String requestBody) throws Exception {

		try {
			final String fieldtobeSorted = APIHelper.retriveValuefromJson("$..fields", requestBody);
			boolean sorted = false;
			final String[] Field = fieldtobeSorted.split("=");
			final List<String> stringList = new ArrayList<String>(Arrays.asList(Field));
			final String finalField = stringList.get(0).replaceAll("[^\\w\\s]", "").trim();

			List<String> ascList = new ArrayList<String>();
			List<String> desList = new ArrayList<String>();

			// Checks request is ASC or DESC and the single level validation is
			// done
			if (fieldtobeSorted.contains("ASC") == true && fieldtobeSorted.contains(",") == false) {
				ascList = sort_reusable(finalField, response);
				sorted = Ordering.natural().isOrdered(ascList);
			} else if (fieldtobeSorted.contains("DESC") == true && fieldtobeSorted.contains(",") == false) {
				desList = sort_reusable(finalField, response);
				sorted = Ordering.natural().reverse().isOrdered(desList);
			} else {
				// Checks request is ASC or DESC and the multi level validation
				// is
				// done
				sorted = post_MultiLevelSort(response, requestBody);
			}

			if (sorted == true) {
				ReportHelper.log(LogStatus.PASS, "Sorting", "Single/Multi Level Sorting is successful");
			} else {
				ReportHelper.log(LogStatus.FAIL, "Sorting", "Single/Multi Level  Sorting is not successful");
			}
			return sorted;
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
		return false;
	}

	/**
	 * Sort reusable method
	 * @param finalField
	 * @param response
	 * @return
	 */
	public List<String> sort_reusable(String finalField, Response response) {

		try {
			final String FirsteachExactFieldToreturn = PropLoad.getTestData("ExchDisp." + finalField).split(",")[1]
					.trim();
			final List<String> sortedField = APIHelper.FeildToReturnasArray("$..source" + FirsteachExactFieldToreturn,
					response.getBody().asString());
			return sortedField;
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Multi-level sort for POST call
	 * @param response
	 * @param requestBody
	 * @return
	 * @throws Exception
	 */
	public boolean post_MultiLevelSort(Response response, String requestBody) throws Exception {

		try {
			final String fieldtobeSorted1 = APIHelper.retriveValuefromJson("$..fields", requestBody);
			final String fieldtobeSorted = fieldtobeSorted1.split(",")[0];

			boolean sorted = false;
			final String[] Field = fieldtobeSorted.split("=");

			final List<String> stringList = new ArrayList<String>(Arrays.asList(Field));
			final String finalField = stringList.get(0).replaceAll("[^\\w\\s]", "").trim();

			List<String> ascList = new ArrayList<String>();
			List<String> desList = new ArrayList<String>();

			if (fieldtobeSorted.contains("ASC")) {
				ascList = sort_reusable(finalField, response);
				sorted = Ordering.natural().isOrdered(ascList);
			} else if (fieldtobeSorted.contains("DESC")) {
				desList = sort_reusable(finalField, response);
				sorted = Ordering.natural().reverse().isOrdered(desList);
			}
			return sorted;
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
		return false;
	}

	/**
	 * To find biggest number in the query string
	 * @param queryString
	 * @return
	 */
	public ArrayList<String> toFindbiggestNumberinQueryString(String queryString) {
		try {
			String[] qString = null;
			final ArrayList<String> returntheBigtoSmallestOrder = new ArrayList<String>();
			int firstset_QueryString_number = 0;
			int Secoundset_QueryString_number = 0;
			if (queryString.contains("OR")) {
				qString = queryString.split("OR");

				if (qString[0].contains("^")) {
					final String[] split_qstring = qString[0].split("\\^");
					final String firstset_QueryString = split_qstring[1];
					firstset_QueryString_number = Integer.parseInt(firstset_QueryString.trim());
				}
				if (qString[1].contains("^")) {
					final String[] split_qstring2 = qString[1].split("\\^");
					final String Secoundset_QueryString = split_qstring2[1];
					Secoundset_QueryString_number = Integer.parseInt(Secoundset_QueryString.trim());
				}
			} else if (queryString.contains("AND")) {
				qString = queryString.split("AND");
				if (qString[0].contains("^")) {
					final String[] split_qstring = qString[0].split("\\^");
					final String firstset_QueryString = split_qstring[1];
					firstset_QueryString_number = Integer.parseInt(firstset_QueryString.trim());
				}
				if (qString[1].contains("^")) {
					final String[] split_qstring2 = qString[1].split("\\^");
					final String Secoundset_QueryString = split_qstring2[1];
					Secoundset_QueryString_number = Integer.parseInt(Secoundset_QueryString.trim());
				}
			}
			if (firstset_QueryString_number > Secoundset_QueryString_number) {
				returntheBigtoSmallestOrder.add(qString[0].trim());
				returntheBigtoSmallestOrder.add(qString[1].trim());
			} else if (firstset_QueryString_number < Secoundset_QueryString_number) {
				returntheBigtoSmallestOrder.add(qString[1].trim());
				returntheBigtoSmallestOrder.add(qString[0].trim());
			} else {
				returntheBigtoSmallestOrder.add(queryString);
			}
			return returntheBigtoSmallestOrder;
		} catch (NumberFormatException e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * To validate the query string in the response
	 * @param response
	 * @param expectedQuery
	 */
	public void jsonResponseValidationWithQueryString(Response response, ArrayList<String> expectedQuery) {
		try {
			String josanPathforExpectedResult = "";
			String queryStringvalueFinal = "";
			int indexofResponseValue = 0;
			for (int i = 0; i < expectedQuery.size(); i++) {
				final String[] jasonFieldnameArray = expectedQuery.get(i).split("\\:");
				final String jasonFieldnameFromExcel = jasonFieldnameArray[0].replace("(", "").trim();
				final String jasonFieldname = PropLoad.getTestData(jasonFieldnameFromExcel).split(",")[1].trim();
				final String queryStringvalue = jasonFieldnameArray[1].replace(")", "");
				if (queryStringvalue.contains("^")) {
					final String[] QueryStringvalueWithNumber = queryStringvalue.split("\\^");
					queryStringvalueFinal = QueryStringvalueWithNumber[0];
				} else {
					queryStringvalueFinal = queryStringvalue;
				}
				josanPathforExpectedResult = "$.searchResults.." + jasonFieldname;
				final List<String> Resopnse_Body = APIHelper.FeildToReturnasArray(josanPathforExpectedResult,
						response.getBody().asString());
				if (Resopnse_Body.size() > 0) {
					final List<Object> listDistinct = Resopnse_Body.stream().distinct().collect(Collectors.toList());
					final String distinctvalueFromList = (String) listDistinct.get(indexofResponseValue);
					indexofResponseValue++;
					if (distinctvalueFromList.contains(queryStringvalueFinal)) {
						ReportHelper.log(LogStatus.PASS, "Boosting - Values Verification",
								"Expected value in level " + indexofResponseValue + " is " + queryStringvalueFinal
										+ " .Actual Value is " + distinctvalueFromList);
						Assert.assertTrue(true);
					} else {
						ReportHelper.log(LogStatus.FAIL, "Boosting - Values Verification",
								"Expected value in level " + indexofResponseValue + " is " + queryStringvalueFinal
										+ " .Actual Value is " + distinctvalueFromList);
						Assert.assertTrue(false);
					}
					if (listDistinct.size() == 1) {
						break;
					}
				} else {
					ReportHelper.log(LogStatus.INFO, "Response Body",
							"Check the QueryString - Response body is Not contain any QueryString Field");
				}
			}
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}

	}

	/**
	 * Single specific field wild card search for POST call
	 * @param data_Search
	 * @throws Exception
	 */
	public void postSingleSpecificFieldWildCard_ResponseValidate_exactPharse(LinkedHashMap<String, String> data_Search)
			throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final String requestbody = data_Search.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		final String querySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString,
				" Application ID: " + applicationId);
		final Map<String, String> specificFieldNames = Template.getMultipleSpecficFieldNames_Exact(querySearchString);

		// Verify Specific Field name's wild card data present in json
		final boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
				response.asString(), specificFieldNames, ResponseJsonPath.v2SearchSource_JP, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSource_JP,
				response.asString());
		if (!bSpecsuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
		}
		Assert.assertTrue(bSpecsuccess);
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
			LoggerUtil.log("Verifyv2SearchSpecificField_SearchQueryString_PresentinJSON",
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}
	}

	/**
	 * Single specific field wild card search for GET call
	 * @param data_Search
	 * @throws Exception
	 */
	public void getSingleSpecificFieldWildCard_ResponseValidate_exactPharse(LinkedHashMap<String, String> data_Search)
			throws Exception {
		System.out.println("Getting in to getSingleandMultipleTermSearch method");
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);

		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}
		String qs_String = PropLoad.getTestData("V2Search_queryString_Excel");
		final String querySearchString = Template.getSearchQueryString(data_Search, qs_String);
		final String applicationId = data_Search.get("appId_header").trim();
		ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + querySearchString,
				" Application ID: " + applicationId);
		final Map<String, String> specificFieldNames = Template.getMultipleSpecficFieldNames_Exact(querySearchString);

		// Verify Specific Field name's wild card data present in json
		final boolean bSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
				response.asString(), specificFieldNames, ResponseJsonPath.v2SearchSource_JP, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSource_JP,
				response.asString());
		if (!bSpecsuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchSpecificField_SearchQueryString_PresentinJSON", "");
		}
		Assert.assertTrue(bSpecsuccess);
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.PASS, "getSingleSpecificFieldWildCard_ResponseValidate_exactPharse",
					"Count is returned as Zero - please check your QueryString");
		} else {
			ReportHelper.log(LogStatus.PASS, "getSingleSpecificFieldWildCard_ResponseValidate_exactPharse", "");
			LoggerUtil.log("getSingleSpecificFieldWildCard_ResponseValidate_exactPharse",
					"Field value is matching with given query string : " + querySearchString, Constants.subStep, null);
		}

		// Verify Field to return present in JSON
		final String[] expFieldnames = Template.getFieldNamesFromKey(data_Search, expFieldName);
		APIHelper.fieldtoReturn_post(response, expFieldnames, ResponseJsonPath.v2SearchSource_JP);
	}

	/**
	 * GET call negative validation
	 * @param data_Search
	 * @throws Exception
	 */
	public void getNegativeValidation(LinkedHashMap<String, String> data_Search) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Getting in to getSingleandMultipleTermSearch method");
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);

		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the JSON response
		final String expJsonResponse = data_Search.get("expJSONResponse").trim();
		if (!expJsonResponse.equals(Constants.emptyString)) {
			ResponseValidator.compareJsonResponse(response, expJsonResponse);
		}

		// Validating the response count
		final String expResponseCount = data_Search.get("expResponseCount").trim();
		if (!expResponseCount.equals(Constants.emptyString)) {
			final int expResponseValCount = Integer.parseInt(expResponseCount);
			final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSource_JP,
					response.asString());
			final int actResponseCount = arrayValuesFromJson.size();
			ResponseValidator.compareResponseCount(expResponseValCount, actResponseCount);
		}
	}

	/**
	 * POST call negative validation
	 * @param data_Search
	 * @throws Exception
	 */
	public void postNegativeValidation(LinkedHashMap<String, String> data_Search) throws Exception {
		// TODO Auto-generated method stub
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final String requestbody = data_Search.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		// Validating the JSON response
		final String expJsonResponse = data_Search.get("expJSONResponse").trim();
		if (!expJsonResponse.equals(Constants.emptyString)) {
			ResponseValidator.compareJsonResponse(response, expJsonResponse);
		}

		// Validating the response count
		final String expResponseCount = data_Search.get("expResponseCount").trim();
		if (!expResponseCount.equals(Constants.emptyString)) {
			final int expResponseValCount = Integer.parseInt(expResponseCount);
			final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSource_JP,
					response.asString());
			final int actResponseCount = arrayValuesFromJson.size();
			ResponseValidator.compareResponseCount(expResponseValCount, actResponseCount);
		}
	}

	/**
	 * POST call expired token test
	 * @param data_Search
	 * @throws Exception
	 */
	public void postexpiredTokenTest(LinkedHashMap<String, String> data_Search) throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = "eyJhbGciOiJSUzUxMiIsImtpZCI6ImsxJKY5NDgxOTAifQ.eyJleHAiOjE0ODc3NzIzNTgsInN1YiI6ImZmZmZmZmZmNTNhNDg0OTVlNGIwN2JhM2UyYTcxZjdjIiwic2Vzc2lkIjoiNDgwMGFhOTQ1YWE3NDk0OGFmM2I1NDlkOTAzMjgwMTUiLCJ0eXBlIjoiYXQiLCJpYXQiOjE0ODc3NjE1NTh9.RLdwIQJqUD9hMV4H-adY-gw1nZ8S-8znlAcyzmClF9JrLI1SE11f579195q-romeeQTgOBG4hWnxbVCx-f4u7_9nxVuJ9c6K9__6ksnGmBLrrsoVa5eYIRr2tAl-q4YcAV2iKVuJ_KzgZbQFTgxbtkdlzYQxslSsaBf7Tqd68AI";
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final String requestbody = data_Search.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the JSON response
		final String expJsonResponse = data_Search.get("expJSONResponse").trim();
		if (!expJsonResponse.equals(Constants.emptyString)) {
			ResponseValidator.compareJsonResponse(response, expJsonResponse);
		}
	}

	/**
	 * GET call expired token test
	 * @param data_Search
	 * @throws Exception
	 */
	public void getExpiredTokenTest(LinkedHashMap<String, String> data_Search) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Getting in to getSingleandMultipleTermSearch method");
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = "eyJhbGciOiJSUzUxMiIsImtpZCI6ImsxJKY5NDgxOTAifQ.eyJleHAiOjE0ODc3NzIzNTgsInN1YiI6ImZmZmZmZmZmNTNhNDg0OTVlNGIwN2JhM2UyYTcxZjdjIiwic2Vzc2lkIjoiNDgwMGFhOTQ1YWE3NDk0OGFmM2I1NDlkOTAzMjgwMTUiLCJ0eXBlIjoiYXQiLCJpYXQiOjE0ODc3NjE1NTh9.RLdwIQJqUD9hMV4H-adY-gw1nZ8S-8znlAcyzmClF9JrLI1SE11f579195q-romeeQTgOBG4hWnxbVCx-f4u7_9nxVuJ9c6K9__6ksnGmBLrrsoVa5eYIRr2tAl-q4YcAV2iKVuJ_KzgZbQFTgxbtkdlzYQxslSsaBf7Tqd68AI";
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);
	}
	
	/**
	 * GET call negative validation
	 * @param data_Search
	 * @throws Exception
	 */
	public void getNegativeValidation2(LinkedHashMap<String, String> data_Search) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Getting in to getSingleandMultipleTermSearch method");
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final Map<String, String> parameters = Template.getRequestData(data_Search, Constants.qsParam);

		final Response response = webCredentials_rest.getCallWithHeaderAndParameters(headers, parameters, reqURL);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());

		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response count
		final String expResponseCount = data_Search.get("expResponseCount").trim();
		if (!expResponseCount.equals(Constants.emptyString)) {
			final int expResponseValCount = Integer.parseInt(expResponseCount);
			final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSource_JP,
					response.asString());
			final int actResponseCount = arrayValuesFromJson.size();
			ResponseValidator.compareResponseCount(expResponseValCount, actResponseCount);
		}
	}
	
	
	/**
	 * POST call negative validation
	 * @param data_Search
	 * @throws Exception
	 */
	public void postNegativeValidation2(LinkedHashMap<String, String> data_Search) throws Exception {
		// TODO Auto-generated method stub
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final String requestbody = data_Search.get(Constants.reqBody);
		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		final Response response = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson, reqURL);
		ReportHelper.log(LogStatus.INFO, "Response ", response.asString());
		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(response, expSchemafileName);
		}

		// Validating the response count
		final String expResponseCount = data_Search.get("expResponseCount").trim();
		if (!expResponseCount.equals(Constants.emptyString)) {
			final int expResponseValCount = Integer.parseInt(expResponseCount);
			final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchSource_JP,
					response.asString());
			final int actResponseCount = arrayValuesFromJson.size();
			ResponseValidator.compareResponseCount(expResponseValCount, actResponseCount);
		}
	}
	
}
