package com.pearson.pageobjects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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


/**
 * 
 * Collection Application
 * 
 * 		V2 Search functionality tested for the application - collection
 * 		Different functionality like facet, multifacet, sorting and fields to return are being tested in this Page Object 
 *
 */


public class Regression_collectionPO {

	static RESTServiceBase webCredentials_rest = new RESTServiceBase();
	@SuppressWarnings("unused")
	private HashMap<String, String> data;
	String authToken = "";
	private static String qs_String = PropLoad.getTestData("V2Search_queryString_Excel");
	private static String expFieldName = PropLoad.getTestData("V2Search_expFields_Excel");
	private static String xAuthKey = PropLoad.getTestData("Auth_XAuthKey");
	private static String qs_reqField = PropLoad.getTestData("V2Search_qs_reqfield");
	private static String expFieldName_reqField = PropLoad.getTestData("V2Search_expFields_reqfield");

	/**
	 * Constructor
	 * @param data
	 */
	public Regression_collectionPO(LinkedHashMap<String, String> data) {
		this.data = data;
	}

	public Regression_collectionPO() {
	}
	
	/**
	 * Functionality to check the Logical Operation works as expected 
	 * @param data_Search
	 */
	public void postLogicalOperator(LinkedHashMap<String, String> data_Search) {
		try {
			ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
			final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
			authToken = CSGHelper.generate_PI_AuthToken();
			final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
			headers.put(xAuthKey, authToken);
			final String requestBody = data_Search.get(Constants.reqBody);
			final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestBody);

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
			
			 Boolean bSuccess = CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
					ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false);
			final JSONArray arrayValuesFromJson = Template
					.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMatchedName_JP, response.asString());
			
			if (!bSuccess) {
				ReportHelper.log(LogStatus.FAIL, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
						"em tag match validation");
				Assert.fail();
			}
			
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
		} catch (NumberFormatException e) {
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (net.minidev.json.parser.ParseException e) {
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Get call for single term and single term with wild card (Revel, revel*, Revel?...) functionality Testing
	 * @param data_Search
	 * @throws Exception
	 */
	public void getSingleandMultipleTermSearch(LinkedHashMap<String, String> data_Search) throws Exception {
		
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authToken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authToken);
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

		
		 Boolean bSuccess = CSGHelper.verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(
				ResponseJsonPath.v2SearchMatchedName_JP, response.asString(), querySearchString, false);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMatchedName_JP,
				response.asString());

		if (!bSuccess) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"em tag match validation");
			Assert.fail();
		}
		
		
		if (arrayValuesFromJson.size() == 0) {
			ReportHelper.log(LogStatus.FAIL, "verifyv2SearchMatchedField_SearchQueryString_PresentinJSON",
					"Count is returned as Zero - please check your QueryString");
			Assert.fail();
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
	 * Multi-Facet validation - List of values present in 'Key' in response should match with the group By Values present in corresponding response
	 * @param response
	 * @param requestBody
	 */
	public void multiLevelFacet_validation(Response response, JSONObject requestBody) {
		try {
			final String dynamicJSONPath;
			//JSONArray groupBy = null;
			JSONArray groupBy = APIHelper.jsonValuefromJson("$..groupBy[*]", requestBody);
			dynamicJSONPath = APIHelper.dynamicJSONPathCreation(groupBy.size(), "$..searchResults[*]");
			//final Map<Object, Integer> keysCount = APIHelper.getKeysCount(dynamicJSONPath, response, groupBy);
			final Map<Object, List<Object>> keysWithValues = APIHelper.getKeys(dynamicJSONPath, response, groupBy);
			//facet_Reusable(groupBy, keysCount, keysWithValues, response);
			facet_Reusable(groupBy, keysWithValues, response);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
			ReportHelper.log(LogStatus.FAIL, "Facet Fields values from Response",
					"All the values are getting from Response succssfully");
		}
	}

	/**
	 * Facet validation so that it can be used for GET, POST and single or multiple Facet
	 * @param groupByFields
	 * @param keyswithValues
	 * @param response
	 */
	public void facet_Reusable(JSONArray groupByFields, Map<Object, List<Object>> keyswithValues, Response response) {
		final String firstPartLocator = "$..searchResults[";
		final String secondPartLocator = "*]..productsList[*]..";
		int k = groupByFields.size() - 1;
		try {
			for (int l = 0; l < groupByFields.size(); l++) {
				final String firsteachExactFieldToreturn = PropLoad
						.getTestData(
								"collection_Regression." + groupByFields.get(k).toString().replace(".multi_value", ""))
						.split(",")[1].trim();
				final String formLocator;
				if (firsteachExactFieldToreturn.contains(".")) {
					formLocator = firsteachExactFieldToreturn.replaceAll("\\.", "\\..");
				} else {
					formLocator = firsteachExactFieldToreturn;
				}
				final String locator = firstPartLocator + secondPartLocator + formLocator;
				final List<Object> keys = APIHelper.FeildToReturnasObject(locator, response.getBody().asString());
				final List<Object> keyValuefromResponse = keyswithValues.get(groupByFields.get(l));
				// Code for find the group by field having pipe| or \ BackSlash
				// for multi value
				List<Object> multikeyvalues = new ArrayList<>();
				for (int keysindex = 0; keysindex < keys.size(); keysindex++) {
					String Keyvalue = (String) keys.get(keysindex);
					if (Keyvalue.trim().contains("|")) {
						String[] SplitKeyvalues = Keyvalue.trim().split("\\|");
						for (String value : SplitKeyvalues) {
							multikeyvalues.add(value);
						}
					} else if (Keyvalue.trim().contains("\\")) {
						String[] SplitKeyvalues = Keyvalue.trim().split("\\|");
						for (String value : SplitKeyvalues) {
							multikeyvalues.add(value);
						}
					} else {
						multikeyvalues.add(Keyvalue);
					}

				}
				keys.clear();
				keys.addAll(multikeyvalues);
				if (keys.size() != 0L && keyValuefromResponse.size() != 0) {
					if (keys.size() == keyValuefromResponse.size()) {
						if (CollectionUtils.isEqualCollection(keys, keyValuefromResponse)) {
							ReportHelper.log(LogStatus.PASS, groupByFields.get(k) + "- Field Facet",
									"Facet Validation working as Expected");
						} else {
							ReportHelper.log(LogStatus.FAIL, groupByFields.get(k) + "- Field Facet",
									"Facet Validation Failed");
						}

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
			ReportHelper.log(LogStatus.FAIL, groupByFields.get(k) + "Facet Validations",
					"Facet is not working properly");
		}
	}
			
	/**
	 * Multi Level Facet validation so that it can be used for GET, POST and single or multiple Facet
	 * @param response
	 * @param parameters
	 */
	public void get_multiLevelFacet_validation(Response response, Map<String, String> parameters) {
		try {
			final String dynamicJSONPath;
			String groupBy_get = APIHelper.getValueforKey_SingleSearch(parameters, "groupBy");
			String mStringArray[] = groupBy_get.split(",");
			JSONArray groupBy = new JSONArray();
			for (String m : mStringArray) {
				groupBy.add(m);
			}

			dynamicJSONPath = APIHelper.dynamicJSONPathCreation(groupBy.size(), "$..searchResults[*]");
			final Map<Object, Integer> keysCount = APIHelper.getKeysCount(dynamicJSONPath, response, groupBy);
			final Map<Object, List<Object>> keysWithValues = APIHelper.getKeys(dynamicJSONPath, response, groupBy);
			get_Facet_Reusable(groupBy, keysCount, keysWithValues, response);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			ReportHelper.log(LogStatus.FAIL, "Facet Fields values from Response",
					"All the values are getting from Response succssfully");
		}
	}

	/**
	 * Facet validation so that it can be used for GET, POST and single or multiple Facet
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
			final String FirsteachExactFieldToreturn = PropLoad.getTestData("collection_Regression."+groupByFields.get(k).toString().replace(".multi_value", "")).split(",")[1].trim();
			final String FormLocator;
			if (FirsteachExactFieldToreturn.contains(".") == true) {
				FormLocator = FirsteachExactFieldToreturn.replaceAll("\\.", "\\..");
			} else {
				FormLocator = FirsteachExactFieldToreturn;
			}
			final String Locator = FirstPartLocator + secondPartLocator + FormLocator;
			List<Object> keys = APIHelper.FeildToReturnasObject(Locator, response.getBody().asString());
			final List<Object> keyValuefromResponse = keyswithValues.get(groupByFields.get(l));
				//Code for find the group by field having pipe| or \ BackSlash for multi value
					List<Object> multikeyvalues = new ArrayList<>();
					for(int keysindex=0;keysindex<keys.size();keysindex++){
						String Keyvalue = (String) keys.get(keysindex);
						if(Keyvalue.trim().contains("|")){
							String[] SplitKeyvalues = Keyvalue.trim().split("\\|");
						for(String value : SplitKeyvalues){
							multikeyvalues.add(value);
						}}
						else if(Keyvalue.trim().contains("\\")){
							String[] SplitKeyvalues = Keyvalue.trim().split("\\|");
							for(String value : SplitKeyvalues){
								multikeyvalues.add(value);
							}}
						else{
							multikeyvalues.add(Keyvalue);	
						} 
					
					}
					keys.clear();
					keys.addAll(multikeyvalues);
					
				if (keys.size() != 0L && keyValuefromResponse.size() != 0) {
					if (keys.size() == keyValuefromResponse.size()) {
						if(CollectionUtils.isEqualCollection(keys, keyValuefromResponse)){
							ReportHelper.log(LogStatus.PASS, groupByFields.get(k) + "- Field Facet",
									"Facet Validation working as Expected");
						}else{
							ReportHelper.log(LogStatus.FAIL, groupByFields.get(k) + "- Field Facet",
									"Facet Validation Failed");
						}
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
				}}

			} catch (Exception e) {
				ReportHelper.log(LogStatus.FAIL, groupByFields.get(k) + "Facet Validations",
						"Facet is not working properly");
			}
		
	}

	/**
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
				FirsteachExactFieldToreturn = PropLoad.getTestData("collection_Regression." + s1).split(",")[1].trim();
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
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
		}

	}

	/**
	 * @param response
	 * @param parameters
	 * @return
	 */
	public boolean get_isSortedField1(Response response, Map<String, String> parameters) {
		Boolean sorted = false;

		try {
			final String order = (String) parameters.get("groupBySortOrder").trim();
			final String field = (String) parameters.get("groupBySortField").trim();

			if (field.contains("term")) {
				final List<String> getAlley = APIHelper.FeildToReturnasArray("$..searchResults[*]..key",
						response.getBody().asString());
				if (order.equalsIgnoreCase("DESC")) {
					sorted = Ordering.natural().reverse().isOrdered(getAlley);
				} else {
					sorted = Ordering.natural().isOrdered(getAlley);
				}
			} else {
				final List<String> getAlley = APIHelper.FeildToReturnasArray("$..count", response.getBody().asString());
				getAlley.remove(0);
				if (order.equalsIgnoreCase("DESC")) {
					sorted = Ordering.natural().reverse().isOrdered(getAlley);
				} else {
					sorted = Ordering.natural().isOrdered(getAlley);
				}
			}
		}

		catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
		}
		return sorted;

	}

	/**
	 * @param response
	 * @param parameters
	 * @throws Exception
	 */
	public void get_fieldtoReturn_post(Response response, Map<String, String> parameters) throws Exception {
		try {
			String[] fieldToreturnString = parameters.get("fieldsToReturn").split(",");
			final List<String> fieldToreturn = new ArrayList<String>(Arrays.asList(fieldToreturnString));

			final StringBuilder td = new StringBuilder();
			Boolean FinalSuccess = false;
			final StringBuilder tdfail = new StringBuilder();
			String FirsteachExactFieldToreturn;

			for (String s1 : fieldToreturn) {
				FirsteachExactFieldToreturn = PropLoad.getTestData("collection_Regression." + s1).split(",")[1].trim();
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
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
		}

	}

	/**
	 * @param response
	 * @param requestBody
	 * @return
	 */
	public boolean isSortedField1(Response response, String requestBody) {
		Boolean sorted = false;
		try {
			final String order = APIHelper.retriveValuefromJson("$..order", requestBody);
			final String field = APIHelper.retriveValuefromJson("$..field", requestBody);

			if (field.contains("term")) {
				final List<String> getAlley = APIHelper.FeildToReturnasArray("$..searchResults[*]..key",
						response.getBody().asString());
				if (order.equalsIgnoreCase("DESC")) {
					sorted = Ordering.natural().reverse().isOrdered(getAlley);
				} else {
					sorted = Ordering.natural().isOrdered(getAlley);
				}
			} else {
				final List<String> getAlley = APIHelper.FeildToReturnasArray("$..count", response.getBody().asString());
				getAlley.remove(0);
				if (order.equalsIgnoreCase("DESC")) {
					sorted = Ordering.natural().reverse().isOrdered(getAlley);
				} else {
					sorted = Ordering.natural().isOrdered(getAlley);
				}
			}
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
		}
		return sorted;
	}

	/**
	 * Method to find the biggest number in the query string
	 * @param queryString
	 * @return
	 */
	public ArrayList<String> toFindbiggestNumberinQueryString(String queryString) {
		ArrayList<String> returntheBigtoSmallestOrder = new ArrayList<String>();

		try {
			String[] qString = null;

			int firstset_QueryString_number = 0;
			int Secoundset_QueryString_number = 0;
			if (queryString.contains("OR")) {
				qString = queryString.split("OR");

				if (qString[0].contains("^")) {
					String[] split_qstring = qString[0].split("\\^");
					String firstset_QueryString = split_qstring[1];
					firstset_QueryString_number = Integer.parseInt(firstset_QueryString.trim());
				}
				if (qString[1].contains("^")) {
					String[] split_qstring2 = qString[1].split("\\^");
					String Secoundset_QueryString = split_qstring2[1];
					Secoundset_QueryString_number = Integer.parseInt(Secoundset_QueryString.trim());
				}
			} else if (queryString.contains("AND")) {
				qString = queryString.split("AND");
				if (qString[0].contains("^")) {
					String[] split_qstring = qString[0].split("\\^");
					String firstset_QueryString = split_qstring[1];
					firstset_QueryString_number = Integer.parseInt(firstset_QueryString.trim());
				}
				if (qString[1].contains("^")) {
					String[] split_qstring2 = qString[1].split("\\^");
					String Secoundset_QueryString = split_qstring2[1];
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
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
		}

		return returntheBigtoSmallestOrder;
	}

	/**
	 * Validate query string in the response
	 * @param response
	 * @param expectedQuery
	 */
	
	public void jsonResponseValidationWithQueryString(Response response, ArrayList<String> expectedQuery) {
		try {
			String josanPathforExpectedResult = "";
			String queryStringvalueFinal = "";
			int indexofResponseValue = 0;
			for (int i = 0; i < expectedQuery.size(); i++) {
				String[] jasonFieldnameArray = expectedQuery.get(i).split("\\:");
				String jasonFieldnameFromExcel = jasonFieldnameArray[0].replace("(", "").trim();
				String jasonFieldname = PropLoad.getTestData(jasonFieldnameFromExcel).split(",")[1].trim();
				String queryStringvalue = jasonFieldnameArray[1].replace(")", "");
				if (queryStringvalue.contains("^")) {
					String[] QueryStringvalueWithNumber = queryStringvalue.split("\\^");
					queryStringvalueFinal = QueryStringvalueWithNumber[0];
				} else {
					queryStringvalueFinal = queryStringvalue;
				}
				josanPathforExpectedResult = "$.searchResults.." + jasonFieldname;
				List<String> Resopnse_Body = APIHelper.FeildToReturnasArray(josanPathforExpectedResult,
						response.getBody().asString());
				if (Resopnse_Body.size() > 0) {
					List<Object> listDistinct = Resopnse_Body.stream().distinct().collect(Collectors.toList());
					String distinctvalueFromList = (String) listDistinct.get(indexofResponseValue);
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
			ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
		}

	}
	
	/**
	 * Method to find the count returned in the response
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public int returnNoCountV2SearchPostorGet(Response response) throws Exception{
		final String actual = APIHelper.retriveValuefromJson("$..count", response.getBody().asString());
		final int actualCount = Integer.parseInt(actual);
		if (actualCount == 0){
			LoggerUtil.log("Count is returned as Zero - please check your QueryString", actual, "SubStep", null);
			ReportHelper.log(LogStatus.PASS, "Count", "Count is returned as Zero as expected");
		}
		else if (actualCount > 0){
			LoggerUtil.log("Count is returned as greater than one - product is available for the QueryString", actual,
					"SubStep", null);
			ReportHelper.log(LogStatus.FAIL, "Count", "Count is returned as greater than one - product is available for the QueryString");
		}
		return actualCount;

	}
	
		/**
		 * @param queryString
		 * @return
		 */
		public List<String> startDateAndEndDateSearch(String queryString) {
			try {
				List<String> datePublishedFromTo = new ArrayList<>();
				if (queryString.contains("datePublished:")) {
					String datePublished = queryString.replace("datePublished:", "").replace("[", "").replace("]", "")
							.replace("{", "").replace("}", "");
					if (datePublished.contains("TO")) {
						datePublishedFromTo = Arrays.asList(datePublished.split("TO"));
					} else if (datePublished.contains(">=")) {
						datePublishedFromTo.add(">=");
						datePublishedFromTo.add(datePublished.replace(">=", ""));
					} else if (datePublished.contains("<=")) {
						datePublishedFromTo.add("<=");
						datePublishedFromTo.add(datePublished.replace("<=", ""));
					} else if (datePublished.contains("<")) {
						datePublishedFromTo.add("<");
						datePublishedFromTo.add(datePublished.replace("<", ""));
					} else if (datePublished.contains(">")) {
						datePublishedFromTo.add(">");
						datePublishedFromTo.add(datePublished.replace(">", ""));
					} else {
						datePublishedFromTo.add(datePublished);
					}
				}
				return datePublishedFromTo;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * @param datePublished
		 * @param datePublishedforProduct
		 */
		public void dateOublishValidationwithActualDates(List<String> datePublished,
				List<String> datePublishedforProduct) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				String datePublishedFrom = (String) datePublished.get(0);
				if (datePublished.size() > 1) {
					// Date dateFrom, dateTo;
					String datePublishedTO = (String) datePublished.get(1);
					if (Regression_collectionPO.stringContainsdate(datePublishedFrom.trim())
							&& Regression_collectionPO.stringContainsdate(datePublishedTO.trim())) {
						Date dateFrom = dateFormat.parse(datePublishedFrom);
						Date dateTo = dateFormat.parse(datePublishedTO);
						for (int actualdateindex = 0; actualdateindex < datePublishedforProduct
								.size(); actualdateindex++) {
							if (dateFrom.equals(dateFormat.parse(datePublishedforProduct.get(actualdateindex)))
									|| dateFrom
											.after((dateFormat.parse(datePublishedforProduct.get(actualdateindex))))) {
								ReportHelper.log(LogStatus.PASS, "Date Validation Passed",
										"Actual date : " + dateFormat.format(dateFormat.parse(datePublishedforProduct.get(actualdateindex)))
												+ " is equal/after the Expected Date :" + dateFormat.format(dateFrom));
							} else if (dateTo.equals(dateFormat.parse(datePublishedforProduct.get(actualdateindex)))
									|| dateFrom
											.before((dateFormat.parse(datePublishedforProduct.get(actualdateindex))))) {
								ReportHelper.log(LogStatus.PASS, "Date Validation Passed",
										"Actual date : " + dateFormat.format(dateFormat.parse(datePublishedforProduct.get(actualdateindex)))
												+ " is equal/before the Expected Date :" + dateFormat.format(dateTo));
							} else {
								ReportHelper.log(LogStatus.FAIL, "Date Validation Failed",
										"Actual date is :" + dateFormat.format(dateFormat.parse(datePublishedforProduct.get(actualdateindex))));
							}
						}
					} else if (datePublishedFrom.trim().equals("*")) {
						Date dateTo = dateFormat.parse(datePublishedTO);
						for (int actualdateindex = 0; actualdateindex < datePublishedforProduct
								.size(); actualdateindex++) {
							if (dateTo.equals(dateFormat.parse(datePublishedforProduct.get(actualdateindex)))
									|| dateTo.after((dateFormat.parse(datePublishedforProduct.get(actualdateindex))))) {
								ReportHelper.log(LogStatus.PASS, "Date Validation",
										"Actual date : " + dateFormat.format(dateFormat.parse(datePublishedforProduct.get(actualdateindex)))
												+ " is equal/before the Expected Date :" + dateFormat.format(dateTo));
							} else {
								ReportHelper.log(LogStatus.FAIL, "Date Validation Failed",
										"Actual date is :" + dateFormat.format(dateFormat.parse(datePublishedforProduct.get(actualdateindex))));
							}
						}
					} else if (datePublishedFrom.trim().equals(">=")) {
						Date dateTo = dateFormat.parse(datePublishedTO);
						for (int actualdateindex = 0; actualdateindex < datePublishedforProduct
								.size(); actualdateindex++) {
							if (dateTo.equals(dateFormat.parse(datePublishedforProduct.get(actualdateindex)))
									|| dateTo.before((dateFormat.parse(datePublishedforProduct.get(actualdateindex))))) {
								ReportHelper.log(LogStatus.PASS, "Date Validation Passed",
										"Actual date :" + dateFormat.format(dateFormat.parse(datePublishedforProduct.get(actualdateindex)))
												+ " is equal/after the Expected Date :" + dateFormat.format(dateTo));
							} else {
								ReportHelper.log(LogStatus.FAIL, "Date Validation Failed",
										"Actual date is :" + dateFormat.format(dateFormat.parse(datePublishedforProduct.get(actualdateindex))));
							}
						}
					} else if (datePublishedFrom.trim().equals("<=")) {
						Date dateTo = dateFormat.parse(datePublishedTO);
						for (int actualdateindex = 0; actualdateindex < datePublishedforProduct
								.size(); actualdateindex++) {
							if (dateTo.equals(dateFormat.parse(datePublishedforProduct.get(actualdateindex)))
									|| dateTo.after(dateFormat.parse(datePublishedforProduct.get(actualdateindex)))) {
								ReportHelper.log(LogStatus.PASS, "Date Validation Passed",
										"Actual date : " + dateFormat.format(dateFormat.parse(datePublishedforProduct.get(actualdateindex)))
												+ " is equal/ before the Expected Date :" + dateFormat.format(dateTo));
							} else {
								ReportHelper.log(LogStatus.FAIL, "Date Validation Failed",
										"Actual date is :" + dateFormat.format(dateFormat.parse(datePublishedforProduct.get(actualdateindex)))
												+ "Expected Date :" + dateFormat.format(dateTo));
							}
						}
					} else if (datePublishedFrom.trim().equals("<")) {
						Date dateTo = dateFormat.parse(datePublishedTO);
						for (int actualdateindex = 0; actualdateindex < datePublishedforProduct
								.size(); actualdateindex++) {
							if (dateTo.after((dateFormat.parse(datePublishedforProduct.get(actualdateindex))))) {
								ReportHelper.log(LogStatus.PASS, "Date Validation Passed",
										"Actual date  :" + dateFormat.format(dateFormat.parse(datePublishedforProduct.get(actualdateindex)))
												+ " is before the Expected Date :" + dateFormat.format(dateTo));
							} else {
								ReportHelper.log(LogStatus.FAIL, "Date Validation Failed",
										"Actual date is :" + dateFormat.format(dateFormat.parse(datePublishedforProduct.get(actualdateindex))));
							}
						}
					} else if (datePublishedFrom.trim().equals(">")) {
						Date dateTo = dateFormat.parse(datePublishedTO);
						for (int actualdateindex = 0; actualdateindex < datePublishedforProduct
								.size(); actualdateindex++) {
							if (dateTo.before((dateFormat.parse(datePublishedforProduct.get(actualdateindex))))) {
								ReportHelper.log(LogStatus.PASS, "Date Validation Passed",
										"Actual date : " + dateFormat.format(dateFormat.parse(datePublishedforProduct.get(actualdateindex)))
												+ " is after the Expected Date :" + dateFormat.format(dateTo));
							} else {
								ReportHelper.log(LogStatus.FAIL, "Date Validation Failed",
										"Actual date is :" + datePublishedforProduct.get(actualdateindex));
							}
						}
					} else if (datePublishedTO.trim().equals("*")) {
						Date dateFrom = dateFormat.parse(datePublishedFrom);
						for (int actualdateindex = 0; actualdateindex < datePublishedforProduct
								.size(); actualdateindex++) {
							if (dateFrom.equals(dateFormat.parse(datePublishedforProduct.get(actualdateindex)))
									|| dateFrom
											.before((dateFormat.parse(datePublishedforProduct.get(actualdateindex))))) {
								ReportHelper.log(LogStatus.PASS, "Date Validation Passed",
										"Actual date :" + dateFormat.format(dateFormat.parse(datePublishedforProduct.get(actualdateindex)))
												+ " is equal/after the Expected Date :" + dateFormat.format(dateFrom));
							} else {
								ReportHelper.log(LogStatus.FAIL, "Date Validation Failed",
										"Actual date is :" + datePublishedforProduct.get(actualdateindex));
							}
						}
					}
				}
				if (datePublished.size() == 1) {
					if (Regression_collectionPO.stringContainsdate(datePublishedFrom)) {
						Date date = dateFormat.parse(datePublishedFrom);
						for (int actualdateindex = 0; actualdateindex < datePublishedforProduct
								.size(); actualdateindex++) {
							if (date.equals(dateFormat.parse(datePublishedforProduct.get(actualdateindex)))) {
								ReportHelper.log(LogStatus.PASS, "Date Validation Passed",
										"Actual date :" + dateFormat.format(dateFormat.parse(datePublishedforProduct.get(actualdateindex)))
												+ " is equal to Expected Date  :" + dateFormat.format(date));
							} else {
								ReportHelper.log(LogStatus.FAIL, "Date Validation Failed",
										"Actual date is :" + datePublishedforProduct.get(actualdateindex)
												+ "Expected Date is :" + dateFormat.format(date));
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}



		public static boolean stringContainsdate(String date) {
			boolean dateFound = false;
			try{
			
			Pattern p = Pattern.compile("\\d{4}-\\d{1,}-\\d{1,}");
			Matcher m = p.matcher(date);
			if (m.find()) {
				dateFound = true;
			}
			return dateFound;
			}
			catch(Exception e ){
				
			}
			return dateFound;
		}
	
}
