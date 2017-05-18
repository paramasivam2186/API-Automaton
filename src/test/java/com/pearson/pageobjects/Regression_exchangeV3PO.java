package com.pearson.pageobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.testng.Assert;

import com.google.common.collect.Ordering;
import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.pearson.regression.utilityHelpers.APIHelper;
import com.pearson.regression.utilityHelpers.CSGHelper;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.DateValidation;
import com.pearson.regression.utilityHelpers.PropLoad;
import com.pearson.regression.utilityHelpers.RESTServiceBase;
import com.pearson.regression.utilityHelpers.ReportHelper;
import com.pearson.regression.utilityHelpers.ResponseJsonPath;
import com.pearson.regression.utilityHelpers.ResponseValidator;
import com.pearson.regression.utilityHelpers.Template;
import com.pearson.regression.utilityHelpers.Wildcard;
import com.relevantcodes.extentreports.LogStatus;

import bsh.ParseException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class Regression_exchangeV3PO extends RESTServiceBase {
	@SuppressWarnings("unused")
	private HashMap<String, String> data;
	String authtoken = "";
	static RESTServiceBase webCredentials_rest = new RESTServiceBase();
	private static String xAuthKey = PropLoad.getTestData("Auth_XAuthKey");
	private static String qs_reqField = PropLoad.getTestData("V2Search_qs_reqfield");
	private static String expFieldName_reqField = PropLoad.getTestData("V2Search_expFields_reqfield");

	/**
	 * Constructor
	 * @param data
	 */
	public Regression_exchangeV3PO(LinkedHashMap<String, String> data) {
		this.data = data;
	}

	/**
	 * POST call sorting
	 * @param response
	 * @param requestBody
	 * @return
	 * @throws Exception
	 */
	public boolean post_Sorting(Response response, String requestBody) throws Exception {

		boolean sorted = false;
		try {
			final String fieldtobeSorted = APIHelper.retriveValuefromJson("$..fields", requestBody);
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
		}
		return sorted;
	}

	/**
	 * POST call multi-level sort
	 * @param response
	 * @param requestBody
	 * @return
	 * @throws Exception
	 */
	public boolean post_MultiLevelSort(Response response, String requestBody) throws Exception {

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
	}

	/**
	 * Sorting
	 * @param finalField
	 * @param response
	 * @return
	 */
	public List<String> sort_reusable(String finalField, Response response) {

		System.out.println("****************************" + finalField);
		String FirsteachExactFieldToreturn = PropLoad.getTestData(finalField).split(",")[1].trim();
		System.out.println("*******************************" + FirsteachExactFieldToreturn);
		System.out.println(response.getBody().asString());
		List<String> sortedField = APIHelper.FeildToReturnasArray("$..source" + FirsteachExactFieldToreturn,
				response.getBody().asString());
		return sortedField;
	}

	/**
	 * Sort fields
	 * @param response
	 * @param requestBody
	 * @return
	 * @throws Exception
	 */
	public boolean sortField(Response response, String requestBody) throws Exception {
		boolean sorted = false;
		try {
			final String fieldtobeSorted = APIHelper.retriveValuefromJson("$..fields", requestBody);
			final List<Object> sort = APIHelper.FeildToReturnasObject("$..sort[0]", response.getBody().asString());
			final List<String> sortedValue = new ArrayList<String>();
			for (Object s : sort) {
				sortedValue.add(s.toString());
			}

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
		}
		return sorted;
	}

	/**
	 * To get Fields to return for a POST call
	 * @param response
	 * @param jsonRequestBody
	 * @throws Exception
	 */
	public void fieldtoReturn_post(Response response, String jsonRequestBody) throws Exception {

		final List<String> fieldToreturn = APIHelper.FeildToReturnasArray("$.fieldsToReturn", jsonRequestBody);

		final StringBuilder td = new StringBuilder();
		Boolean FinalSuccess = false;
		final StringBuilder tdfail = new StringBuilder();
		String FirsteachExactFieldToreturn;

		for (String s1 : fieldToreturn) {
			FirsteachExactFieldToreturn = PropLoad.getTestData(s1).split(",")[1].trim();
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
			ReportHelper.log(LogStatus.PASS, "fieldtoReturn", "fieldtoReturn present in the response " + td.toString());
		} else {
			LoggerUtil.log("fieldtoReturn is not present in the response" + tdfail.toString(), "FAIL", "SubStep", null);
			ReportHelper.log(LogStatus.PASS, "fieldtoReturn", "fieldtoReturn present in the response " + td.toString());
		}

	}

	/**
	 * To find biggest number in query string
	 * @param queryString
	 * @return
	 */
	public ArrayList<String> toFindbiggestNumberinQueryString(String queryString) {
		String[] qString = null;
		ArrayList<String> returntheBigtoSmallestOrder = new ArrayList<String>();
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
		return returntheBigtoSmallestOrder;
	}

	/**
	 * To validate query string in the json response
	 * @param response
	 * @param expectedQuery
	 */
	public void jsonResponseValidationWithQueryString(Response response, ArrayList<String> expectedQuery) {
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
				System.out.println("++++++++++_____________" + listDistinct.size());
				String distinctvalueFromList = (String) listDistinct.get(indexofResponseValue);
				indexofResponseValue++;
				System.out.println(distinctvalueFromList + "============" + queryStringvalueFinal);
				if (distinctvalueFromList.contains(queryStringvalueFinal)) {
					ReportHelper.log(LogStatus.PASS, "Boosting - Values Verification",
							"Expected value in " + indexofResponseValue + " level is " + queryStringvalueFinal
									+ " .Actual Value is " + distinctvalueFromList);
				} else {
					ReportHelper.log(LogStatus.FAIL, "Boosting - Values Verification",
							"Expected value in " + indexofResponseValue + " level is " + queryStringvalueFinal
									+ " .Actual Value is " + distinctvalueFromList);
				}
				if (listDistinct.size() == 1) {
					break;
				}
			} else {
				ReportHelper.log(LogStatus.INFO, "Response Body",
						"Check the QueryString - Response body is Not contain any QueryString Field");
			}
		}

	}

	/**
	 * @param data_Search
	 * @throws Exception
	 */
	public void postMultipleSpecificFieldWildCard_ResponseValidate_childQuery_MF(
			LinkedHashMap<String, String> data_Search) throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final String requestbody = data_Search.get("api_reqBody");

		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		final Response parentResponse = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson,
				reqURL);
		String parentquerySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(parentResponse, expStatus_code);
		final boolean isLogicalAND = parentquerySearchString.contains(Constants.logicalAND);
		final boolean isLogicalNOT = parentquerySearchString.contains(Constants.logicalNOT);
		final boolean isLogicalOR = parentquerySearchString.contains(Constants.logicalOR);
		boolean bParentSpecsuccess = false;
		boolean bChildSpecsuccess = false;
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(
				".searchResults[*].aggregationBuckets[*].productsList", parentResponse.asString());
		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(parentResponse, expSchemafileName);
		}
		if (arrayValuesFromJson.size() > 0) {
			JSONArray productID = Template.retrivejsonArrayFromJson(
					".searchResults[0].aggregationBuckets[0].productsList[0].productId", parentResponse.asString());
			String prodID = productID.get(0).toString();
			String sub_APIreqBody = data_Search.get("sub_APIreqBody").replace("###", prodID).trim();
			final JSONObject sub_APIreqBodyJson = Template.convertStringtoJsonObject(sub_APIreqBody);
			final Response sub_APIResponse = webCredentials_rest.postCallWithHeaderAndBodyParam(headers,
					sub_APIreqBodyJson, reqURL);
			ResponseValidator.validateResponseCode(sub_APIResponse, expStatus_code);

			System.out.println(sub_APIResponse.asString());
			String api2ReqBody = data_Search.get("api2RequestBody").replace("^^", prodID).trim();
			final JSONObject reqBodyJson2 = Template.convertStringtoJsonObject(api2ReqBody);
			final Response childResponse = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson2,
					reqURL);
			ResponseValidator.validateResponseCode(childResponse, expStatus_code);

			String childquerySearchString = Template.getFieldValfromJson(reqBodyJson2, qs_reqField);
			final String applicationId = data_Search.get("appId_header").trim();
			ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + parentquerySearchString, applicationId);

			final Map<String, String> childspecificFieldNames = Template.getchidQueryFieldName(childquerySearchString);
			final Map<String, String> parentspecificFieldNames = Template
					.getParentQueryFieldName(parentquerySearchString);

			// Validating the search query present in JSON response
			bParentSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
					sub_APIResponse.asString(), parentspecificFieldNames, ResponseJsonPath.v2SearchMultiFacetSource_JP,
					false);

			// Validating the search query present in JSON response
			bChildSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
					childResponse.asString(), childspecificFieldNames, ResponseJsonPath.v2SearchSource_JP, false);
		}

		boolean bSuccess = false;
		if (isLogicalAND) {
			if (bParentSpecsuccess == true && bChildSpecsuccess == true)
				bSuccess = true;
		} else if (isLogicalOR) {
			if (bParentSpecsuccess == true || bChildSpecsuccess == true) {
				bSuccess = true;
			}
		} else if (isLogicalNOT) {
			if (bParentSpecsuccess == false && bChildSpecsuccess == true) {
				bSuccess = true;
			}
		}
		if (!bSuccess)
			ReportHelper.log(LogStatus.FAIL, "postMultipleSpecificFieldWildCard_ResponseValidate_childQuery_MF", "");
		Assert.assertTrue(bSuccess);
		ReportHelper.log(LogStatus.PASS, "postMultipleSpecificFieldWildCard_ResponseValidate_childQuery_MF", "");
	}

	/**
	 * @param data_Search
	 * @throws Exception
	 */
	public void postMultipleExactPharse_ResponseValidate_childQuery_MF(LinkedHashMap<String, String> data_Search)
			throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		final String requestbody = data_Search.get("api_reqBody");

		final JSONObject reqBodyJson = Template.convertStringtoJsonObject(requestbody);

		final Response parentResponse = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson,
				reqURL);
		String parentquerySearchString = Template.getFieldValfromJson(reqBodyJson, qs_reqField);
		// Validating the response code
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(parentResponse, expStatus_code);
		String logicName = "";
		final boolean isLogicalAND = parentquerySearchString.contains(Constants.logicalAND);
		if (isLogicalAND) {
			logicName = " AND ";
		}
		final boolean isLogicalNOT = parentquerySearchString.contains(Constants.logicalNOT);
		if (isLogicalNOT) {
			logicName = " NOT ";
		}
		final boolean isLogicalOR = parentquerySearchString.contains(Constants.logicalOR);
		if (isLogicalOR) {
			logicName = " OR ";
		}
		boolean bParentSpecsuccess = false;
		boolean bChildSpecsuccess = false;
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(
				".searchResults[*].aggregationBuckets[*].productsList", parentResponse.asString());
		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(parentResponse, expSchemafileName);
		}
		if (arrayValuesFromJson.size() > 0) {
			JSONArray productID = Template.retrivejsonArrayFromJson(
					".searchResults[0].aggregationBuckets[0].productsList[0].productId", parentResponse.asString());
			String prodID = productID.get(0).toString();
			String sub_APIreqBody = data_Search.get("sub_APIreqBody").replace("###", prodID).trim();
			final JSONObject sub_APIreqBodyJson = Template.convertStringtoJsonObject(sub_APIreqBody);
			final Response sub_APIResponse = webCredentials_rest.postCallWithHeaderAndBodyParam(headers,
					sub_APIreqBodyJson, reqURL);
			ResponseValidator.validateResponseCode(sub_APIResponse, expStatus_code);

			System.out.println(sub_APIResponse.asString());
			String api2ReqBody = data_Search.get("api2RequestBody").replace("^^", prodID).trim();
			final JSONObject reqBodyJson2 = Template.convertStringtoJsonObject(api2ReqBody);
			final Response childResponse = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, reqBodyJson2,
					reqURL);
			ResponseValidator.validateResponseCode(childResponse, expStatus_code);

			String childquerySearchString = Template.getFieldValfromJson(reqBodyJson2, qs_reqField);
			final String applicationId = data_Search.get("appId_header").trim();
			ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + parentquerySearchString, applicationId);

			final Map<String, String> childspecificFieldNames = Template.getchidQueryFieldName(childquerySearchString);

			final Map<String, String> parentspecificFieldNames = Template
					.getParentQueryFieldName_Logic(parentquerySearchString, logicName);

			// Validating the search query present in JSON response
			bParentSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
					sub_APIResponse.asString(), parentspecificFieldNames, ResponseJsonPath.v2SearchMultiFacetSource_JP,
					true);

			// Validating the search query present in JSON response
			bChildSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
					childResponse.asString(), childspecificFieldNames, ResponseJsonPath.v2SearchSource_JP, false);
		}

		boolean bSuccess = false;
		if (isLogicalAND) {
			if (bParentSpecsuccess == true && bChildSpecsuccess == true)
				bSuccess = true;
		} else if (isLogicalOR) {
			if (bParentSpecsuccess == true || bChildSpecsuccess == true) {
				bSuccess = true;
			}
		} else if (isLogicalNOT) {
			if (bParentSpecsuccess == false && bChildSpecsuccess == true) {
				bSuccess = true;
			}
		}
		if (!bSuccess)
			ReportHelper.log(LogStatus.FAIL, "postMultipleExactPharse_ResponseValidate_childQuery_MF", "");
		Assert.assertTrue(bSuccess);
		ReportHelper.log(LogStatus.PASS, "postMultipleExactPharse_ResponseValidate_childQuery_MF", "");
	}

	/**
	 * @param data_Search
	 * @throws Exception
	 */
	public void getMultipleSpecificFieldWildCard_ResponseValidate_childQuery_MF(
			LinkedHashMap<String, String> data_Search) throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		String parentreq_Param = data_Search.get("api_qsParam");
		String parentquerySearchString = StringUtils.substringBetween(parentreq_Param, "queryString=", "&");

		String parentreqURL = reqURL + parentreq_Param;
		final Response parentResponse = webCredentials_rest.getCallWithHeaderParam(headers, parentreqURL);
		final boolean isLogicalAND = parentquerySearchString.contains(Constants.logicalAND);
		final boolean isLogicalNOT = parentquerySearchString.contains(Constants.logicalNOT);
		final boolean isLogicalOR = parentquerySearchString.contains(Constants.logicalOR);
		boolean bParentSpecsuccess = false;
		boolean bChildSpecsuccess = false;
		// Validating the response code
		int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(parentResponse, expStatus_code);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(
				".searchResults[*].aggregationBuckets[*].productsList", parentResponse.asString());
		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(parentResponse, expSchemafileName);
		}
		System.out.println(arrayValuesFromJson.size());
		if (arrayValuesFromJson.size() > 0) {
			JSONArray productID = Template.retrivejsonArrayFromJson(
					".searchResults[0].aggregationBuckets[0].productsList[0].productId", parentResponse.asString());
			String prodID = productID.get(0).toString();
			String subreq_Param = data_Search.get("subApi_qsParam").replace("###", prodID).trim();
			;
			String sub_reqURL = reqURL + subreq_Param;
			final Response sub_APIResponse = webCredentials_rest.getCallWithHeaderParam(headers, sub_reqURL);
			// Validating the response code

			ResponseValidator.validateResponseCode(sub_APIResponse, expStatus_code);

			String childreq_Param = data_Search.get("Child_qsParam").replace("###", prodID).trim();
			;
			String chils_reqURL = reqURL + childreq_Param;
			final Response child_APIResponse = webCredentials_rest.getCallWithHeaderParam(headers, chils_reqURL);
			String childquerySearchString = StringUtils.substringBetween(childreq_Param, "queryString=", "&");

			ResponseValidator.validateResponseCode(child_APIResponse, expStatus_code);

			final String applicationId = data_Search.get("appId_header").trim();
			ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + parentquerySearchString, applicationId);

			final Map<String, String> childspecificFieldNames = Template.getchidQueryFieldName(childquerySearchString);
			final Map<String, String> parentspecificFieldNames = Template
					.getParentQueryFieldName(parentquerySearchString);

			// Validating the search query present in JSON response
			bParentSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
					sub_APIResponse.asString(), parentspecificFieldNames, ResponseJsonPath.v2SearchMultiFacetSource_JP,
					false);

			// Validating the search query present in JSON response
			bChildSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
					child_APIResponse.asString(), childspecificFieldNames, ResponseJsonPath.v2SearchSource_JP, false);

			boolean bSuccess = false;
			if (isLogicalAND) {
				if (bParentSpecsuccess == true && bChildSpecsuccess == true)
					bSuccess = true;
			} else if (isLogicalOR) {
				if (bParentSpecsuccess == true || bChildSpecsuccess == true) {
					bSuccess = true;
				}
			} else if (isLogicalNOT) {
				if (bParentSpecsuccess == false && bChildSpecsuccess == true) {
					bSuccess = true;
				}
			}
			if (!bSuccess)
				ReportHelper.log(LogStatus.FAIL, "getMultipleSpecificFieldWildCard_ResponseValidate_childQuery_MF", "");
			Assert.assertTrue(bSuccess);
			ReportHelper.log(LogStatus.PASS, "getMultipleSpecificFieldWildCard_ResponseValidate_childQuery_MF", "");
		} else {
			ReportHelper.log(LogStatus.FAIL, "getMultipleSpecificFieldWildCard_ResponseValidate_childQuery_MF",
					"Count is 0");
		}

	}

	/**
	 * @param data_Search
	 * @throws Exception
	 */
	public void getMultipleSpecificFieldExactPharse_ResponseValidate_childQuery_MF(
			LinkedHashMap<String, String> data_Search) throws Exception {
		ReportHelper.log(LogStatus.INFO, "TestcaseNumber ", data_Search.get("Num").trim());
		final String reqURL = Constants.contentAPIUrl + data_Search.get(Constants.reqUrl).trim();
		authtoken = CSGHelper.generate_PI_AuthToken();
		final Map<String, String> headers = Template.getRequestData(data_Search, Constants.header);
		headers.put(xAuthKey, authtoken);
		String parentreq_Param = data_Search.get("api_qsParam");
		String parentquerySearchString = StringUtils.substringBetween(parentreq_Param, "queryString=", "&");

		String parentreqURL = reqURL + parentreq_Param;
		final Response parentResponse = webCredentials_rest.getCallWithHeaderParam(headers, parentreqURL);
		String logicName = "";
		final boolean isLogicalAND = parentquerySearchString.contains(Constants.logicalAND);
		if (isLogicalAND) {
			logicName = " AND ";
		}
		final boolean isLogicalNOT = parentquerySearchString.contains(Constants.logicalNOT);
		if (isLogicalNOT) {
			logicName = " NOT ";
		}
		final boolean isLogicalOR = parentquerySearchString.contains(Constants.logicalOR);
		if (isLogicalOR) {
			logicName = " OR ";
		}
		boolean bParentSpecsuccess = false;
		boolean bChildSpecsuccess = false;
		// Validating the response code
		int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(parentResponse, expStatus_code);
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(
				".searchResults[*].aggregationBuckets[*].productsList", parentResponse.asString());
		// Validating the response schema
		final String expSchemafileName = data_Search.get(Constants.expjsonSchemafileName).trim();
		if (!expSchemafileName.equals(Constants.emptyString)) {
			ResponseValidator.validateJsonSchema(parentResponse, expSchemafileName);
		}
		System.out.println(arrayValuesFromJson.size());
		if (arrayValuesFromJson.size() > 0) {
			JSONArray productID = Template.retrivejsonArrayFromJson(
					".searchResults[0].aggregationBuckets[0].productsList[0].productId", parentResponse.asString());
			String prodID = productID.get(0).toString();
			String subreq_Param = data_Search.get("subApi_qsParam").replace("###", prodID).trim();
			;
			String sub_reqURL = reqURL + subreq_Param;
			final Response sub_APIResponse = webCredentials_rest.getCallWithHeaderParam(headers, sub_reqURL);
			// Validating the response code

			ResponseValidator.validateResponseCode(sub_APIResponse, expStatus_code);

			String childreq_Param = data_Search.get("Child_qsParam").replace("###", prodID).trim();
			;
			String chils_reqURL = reqURL + childreq_Param;
			final Response child_APIResponse = webCredentials_rest.getCallWithHeaderParam(headers, chils_reqURL);
			String childquerySearchString = StringUtils.substringBetween(childreq_Param, "queryString=", "&");

			ResponseValidator.validateResponseCode(child_APIResponse, expStatus_code);

			final String applicationId = data_Search.get("appId_header").trim();
			ReportHelper.log(LogStatus.INFO, "SearchQueryString: " + parentquerySearchString, applicationId);

			final Map<String, String> childspecificFieldNames = Template.getchidQueryFieldName(childquerySearchString);
			final Map<String, String> parentspecificFieldNames = Template
					.getParentQueryFieldName_Logic(parentquerySearchString, logicName);

			// Validating the search query present in JSON response
			bParentSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
					sub_APIResponse.asString(), parentspecificFieldNames, ResponseJsonPath.v2SearchMultiFacetSource_JP,
					true);

			// Validating the search query present in JSON response
			bChildSpecsuccess = CSGHelper.verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(
					child_APIResponse.asString(), childspecificFieldNames, ResponseJsonPath.v2SearchSource_JP, false);

			boolean bSuccess = false;
			if (isLogicalAND) {
				if (bParentSpecsuccess == true && bChildSpecsuccess == true)
					bSuccess = true;
			} else if (isLogicalOR) {
				if (bParentSpecsuccess == true || bChildSpecsuccess == true) {
					bSuccess = true;
				}
			} else if (isLogicalNOT) {
				if (bParentSpecsuccess == false && bChildSpecsuccess == true) {
					bSuccess = true;
				}
			}
			if (!bSuccess)
				ReportHelper.log(LogStatus.FAIL, "getMultipleSpecificFieldWildCard_ResponseValidate_childQuery_MF", "");
			Assert.assertTrue(bSuccess);
			ReportHelper.log(LogStatus.PASS, "getMultipleSpecificFieldWildCard_ResponseValidate_childQuery_MF", "");
		} else {
			ReportHelper.log(LogStatus.FAIL, "getMultipleSpecificFieldWildCard_ResponseValidate_childQuery_MF",
					"Count is 0");
		}

	}

	/**
	 * Negative Sort
	 * @param response
	 * @param statusCode
	 * @return
	 * @throws Exception
	 */
	public boolean negativeSort(Response response, int statusCode) throws Exception {
		boolean success = false;
		try {
			if (statusCode == 500) {
				String errCode = APIHelper.retriveValuefromJson("$..status", response.getBody().asString());
				if (errCode.equalsIgnoreCase("error")) {
					success = true;
				}
			}

			if (statusCode == 200) {
				String Sort = APIHelper.retriveValuefromJson("$..sort", response.getBody().asString());
				if (Sort.contains("null")) {
					success = true;
				}
			}
			return success;
		} 
		catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getStackTrace().toString());
		}
		return success;
	}

	/**
	 * Offer Code status validation
	 * @param headers
	 * @param reqURL
	 * @param josanPathfromQueryString
	 * @param response
	 * @param api2ReqBody
	 * @param webCredentials_rest
	 * @param offerStatus
	 * @throws Exception
	 */
	public void offerCodeStatusValidation(Map<String, String> headers, String reqURL, String josanPathfromQueryString,
			Response response, String api2ReqBody, RESTServiceBase webCredentials_rest, String offerStatus)
			throws Exception {
		List<String> productIDfrom_Resopnse_Body = APIHelper.FeildToReturnasArray(josanPathfromQueryString,
				response.getBody().asString());
		String jasonPathforstausID = "$.searchResults..source..schema:offerStatus";
		// PropLoad.getTestData("productStage").split(",")[1].trim();
		String jasonPathforstaus = "$.searchResults..productId";
		if (productIDfrom_Resopnse_Body.size() > 1) {
			for (int productid = 0; productid < 2; productid++) {
				String ap12Reqbody_afterReplaceProductID = api2ReqBody.replace("^^",
						productIDfrom_Resopnse_Body.get(productid));
				JSONObject reqBodyJson_forAPI2 = Template.convertStringtoJsonObject(ap12Reqbody_afterReplaceProductID);
				Response response_fromAPI2 = webCredentials_rest.postCallWithHeaderAndBodyParam(headers,
						reqBodyJson_forAPI2, reqURL);
				List<String> offerIDfrom_Resopnse_Body = APIHelper.FeildToReturnasArray(jasonPathforstausID,
						response_fromAPI2.getBody().asString());
				List<String> statusfrom_Resopnse_Body = APIHelper.FeildToReturnasArray(jasonPathforstaus,
						response_fromAPI2.getBody().asString());
				for (int allStatus = 0; allStatus < statusfrom_Resopnse_Body.size(); allStatus++) {
					if (offerIDfrom_Resopnse_Body.get(allStatus).equalsIgnoreCase(offerStatus)) {
						ReportHelper.log(LogStatus.PASS, "ProductID , Status and Offer ID",
								productIDfrom_Resopnse_Body.get(productid) + " - "
										+ offerIDfrom_Resopnse_Body.get(allStatus) + " - "
										+ statusfrom_Resopnse_Body.get(allStatus));
					} else {
						ReportHelper.log(LogStatus.FAIL, "Status Mismatch - ProductID , Status and Offer ID",
								productIDfrom_Resopnse_Body.get(productid) + " - "
										+ offerIDfrom_Resopnse_Body.get(allStatus) + " - "
										+ statusfrom_Resopnse_Body.get(allStatus));
					}
				}
				if (statusfrom_Resopnse_Body.size() == 0) {
					ReportHelper.log(LogStatus.INFO, "Product Offer Status Validation",
							"No Offer IDs are available. Please check the Query");
				}
			}
		} else if (productIDfrom_Resopnse_Body.size() == 1) {
			String ap12Reqbody_afterReplaceProductID = api2ReqBody.replace("^^", productIDfrom_Resopnse_Body.get(0));
			JSONObject reqBodyJson_forAPI2 = Template.convertStringtoJsonObject(ap12Reqbody_afterReplaceProductID);
			Response response_fromAPI2 = webCredentials_rest.postCallWithHeaderAndBodyParam(headers,
					reqBodyJson_forAPI2, reqURL);
			List<String> offerIDfrom_Resopnse_Body = APIHelper.FeildToReturnasArray(jasonPathforstausID,
					response_fromAPI2.getBody().asString());
			List<String> statusfrom_Resopnse_Body = APIHelper.FeildToReturnasArray(jasonPathforstaus,
					response_fromAPI2.getBody().asString());
			for (int allStatus = 0; allStatus < statusfrom_Resopnse_Body.size(); allStatus++) {
				if (offerIDfrom_Resopnse_Body.get(allStatus).equalsIgnoreCase(offerStatus)) {
					ReportHelper.log(LogStatus.PASS, "ProductID , Status and Offer ID",
							productIDfrom_Resopnse_Body.get(0) + " - " + offerIDfrom_Resopnse_Body.get(allStatus)
									+ " - " + statusfrom_Resopnse_Body.get(allStatus));
				} else {
					ReportHelper.log(LogStatus.FAIL, "Status Mismatch - ProductID , Status and Offer ID",
							productIDfrom_Resopnse_Body.get(0) + " - " + offerIDfrom_Resopnse_Body.get(allStatus)
									+ " - " + statusfrom_Resopnse_Body.get(allStatus));
				}
			}
			if (statusfrom_Resopnse_Body.size() == 0) {
				ReportHelper.log(LogStatus.INFO, "Product Offer Status Validation",
						"No Offer IDs are available. Please check the Query");
			}
		} else if (productIDfrom_Resopnse_Body.size() == 0) {
			ReportHelper.log(LogStatus.INFO, "Product Offer Status Validation",
					"No Product IDs are available. Please check the Query");
		}
	}

	/**
	 * To get the offer status field value
	 * @param queryStringAPI1
	 * @param api2ReqBody
	 * @return
	 * @throws ParseException
	 */
	public String getOfferStatus(String queryStringAPI1, String api2ReqBody) throws ParseException {
		String offerCodeStatus_forAPI1 = "";
		if (queryStringAPI1.contains("childQuery:offerStatus")) {
			String[] requestbodyAPI1_Split = queryStringAPI1.split("childQuery:offerStatus:");
			offerCodeStatus_forAPI1 = requestbodyAPI1_Split[1].trim();
		}
		return offerCodeStatus_forAPI1;
	}

	// Single and Multi Level Facet Validation
	/**
	 * Multi facet validation
	 * @param response
	 * @param requestBody
	 */
	public void multiLevelFacet_validation(Response response, JSONObject requestBody) {
		try {
			final String dynamicJSONPath;
			JSONArray groupBy = null;
			groupBy = APIHelper.jsonValuefromJson("$..groupBy[*]", requestBody);
			// System.out.println(groupBy.size());
			dynamicJSONPath = APIHelper.dynamicJSONPathCreation(groupBy.size(), "$..searchResults[*]");
			final Map<Object, Integer> keysCount = APIHelper.getKeysCount(dynamicJSONPath, response, groupBy);
			final Map<Object, List<Object>> keysWithValues = APIHelper.getKeys(dynamicJSONPath, response, groupBy);
			Facet_Reusable(groupBy, keysCount, keysWithValues, response);
		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Facet Fields values from Response",
					"All the values are getting from Response successfully");
		}
	}

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
		for (int l = 0; l < groupByFields.size(); l++) {
			final String FirsteachExactFieldToreturn = PropLoad.getTestData(groupByFields.get(k).toString())
					.split(",")[1].trim();
			final String FormLocator;
			if (FirsteachExactFieldToreturn.contains(".") == true) {
				FormLocator = FirsteachExactFieldToreturn.replaceAll("\\.", "\\..");
			} else {
				FormLocator = FirsteachExactFieldToreturn;
			}
			final String Locator = FirstPartLocator + secondPartLocator + FormLocator;
			final List<Object> keys = APIHelper.FeildToReturnasObject(Locator, response.getBody().asString());
			// System.out.println("keys Size" + keys.size());
			final List<Object> keyValuefromResponse = keyswithValues.get(groupByFields.get(l));
			// System.out.println("keyValuefromResponse Size" +
			// keyValuefromResponse.size());
			try {
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
					ReportHelper.log(LogStatus.FAIL, groupByFields.get(k) + " Check your request and application ID",
							"Check your request and application ID / OR the fields to return is not present in the reposnse");
				}

			} catch (Exception e) {
				ReportHelper.log(LogStatus.FAIL, groupByFields.get(k) + "Facet Validations",
						"Facet is not working properly");
			}
		}
	}

	// GET multi-facet&single facet
	/**
	 * Multi facet validation
	 * @param response
	 * @param parameters
	 */
	public void multiLevelFacet_validation(Response response, Map<String, String> parameters) {
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
			Facet_Reusable(groupBy, keysCount, keysWithValues, response);

		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Facet Fields values from Response",
					"All the values are getting from Response succssfully");
		}
	}

	/**
	 * Date validation
	 * @param response
	 * @param requestBody
	 * @param data_Search
	 * @param headers
	 * @param URI
	 */
	public void date_Validation(Response response, String requestBody, LinkedHashMap<String, String> data_Search,
			Map<String, String> headers, String URI) {
		try {

			if (!APIHelper.retriveValuefromJson("$..count", response.getBody().asString()).equals("0")) {
				RESTServiceBase webCredentials_rest = new RESTServiceBase();
				String productID = APIHelper.retriveValuefromJson("$..productId", response.getBody().asString());
				String api2ReqBody = data_Search.get("api2RequestBody").replace("^^", productID).trim();
				final JSONObject json1 = Template.convertStringtoJsonObject(api2ReqBody);
				// Send the POST or GET request
				final Response response1 = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json1, URI);
				DateValidation scn = new DateValidation();
				String dd = APIHelper.retriveValuefromJson("$..queryString", requestBody);

				if (APIHelper.retriveValuefromJson("$..count", response1.getBody().asString()).equals("0")) {
					ReportHelper.log(LogStatus.INFO, "Second part of response is empty",
							"Second part of response is empty");
				} else {
					Boolean success = scn.dd(dd, response1);
					if (success)
						ReportHelper.log(LogStatus.PASS, "Date Validation", "Offer Date Validation is success");
					else
						ReportHelper.log(LogStatus.FAIL, "Date Validation", "Offer Date Validation failed");
				}
			} else
				ReportHelper.log(LogStatus.INFO, "Response is empty", "Response is empty");

		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Date Validation", "Offer Date Validation");
			System.out.println("***********************" + e.getMessage());
		}
	}

	/**
	 * Date validation for GET call
	 * @param response
	 * @param dd
	 * @param data_Search
	 * @param headers
	 * @param URI
	 */
	public void get_date_Validation(Response response, String dd, LinkedHashMap<String, String> data_Search,
			Map<String, String> headers, String URI) {
		try {
			if (!APIHelper.retriveValuefromJson("$..count", response.getBody().asString()).equals("0")) {

				RESTServiceBase webCredentials_rest = new RESTServiceBase();
				String productID = APIHelper.retriveValuefromJson("$..productId", response.getBody().asString());
				String api2ReqBody = data_Search.get("api2RequestBody").replace("^^", productID).trim();
				final JSONObject json1 = Template.convertStringtoJsonObject(api2ReqBody);

				// Send the POST or GET request
				final Response response1 = webCredentials_rest.postCallWithHeaderAndBodyParam(headers, json1, URI);

				if (APIHelper.retriveValuefromJson("$..count", response1.getBody().asString()).equals("0")) {
					ReportHelper.log(LogStatus.INFO, "Second part of response is empty",
							"Second part of response is empty");
				} else {
					DateValidation scn = new DateValidation();
					Boolean success = scn.dd(dd, response1);
					if (success)
						ReportHelper.log(LogStatus.PASS, "Date Validation", "Offer Date Validation is success");
					else
						ReportHelper.log(LogStatus.FAIL, "Date Validation", "Offer Date Validation failed");
				}
			} else {
				ReportHelper.log(LogStatus.INFO, "Response is empty", "Response is empty");
			}

		} catch (Exception e) {
			ReportHelper.log(LogStatus.FAIL, "Date Validation", "Offer Date Validation");
		}
	}

	/**
	 * @param response
	 * @param parameters
	 * @return
	 */
	public boolean get_isSortedField1(Response response, Map<String, String> parameters) {
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
	}

	/**
	 * @param response
	 * @param requestBody
	 * @return
	 */
	public boolean isSortedField1(Response response, String requestBody) {
		final String order = APIHelper.retriveValuefromJson("$..order", requestBody);
		final String field = APIHelper.retriveValuefromJson("$..field", requestBody);
		Boolean sorted = false;
		System.out.println(field + "===================+order" + order);
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
	}

	/**
	 * Returns the actual count value
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public int returnCountV2Search_PostorGet(Response response) throws Exception {
		final String actual = APIHelper.retriveValuefromJson("$..count", response.getBody().asString());
		final int actualcount = Integer.parseInt(actual);
		if (actualcount == 0) {
			LoggerUtil.log("Count is returned as Zero - please check your QueryString", actual, "SubStep", null);
			ReportHelper.log(LogStatus.FAIL, "Count", "Count is returned as Zero - please check your QueryString");
		} else if (actualcount > 0) {
			LoggerUtil.log("Count is returned as greater than one - product is available for the QueryString", actual,
					"SubStep", null);
			ReportHelper.log(LogStatus.PASS, "Count",
					"Count is returned as greater than one - product is available for the QueryString");
		}
		return actualcount;
	}

	/**
	 * Fields to return for a GET call
	 * @param response
	 * @param parameters
	 * @throws Exception
	 */
	public void fieldtoReturn_get(Response response, Map<String, String> parameters) throws Exception {
	try{
			
		final String fieldToreturn1 = APIHelper.getValueforKey_SingleSearch(parameters, "fieldsToReturn");
		final String[] FieldtoRArray = fieldToreturn1.split(",");

		final List<String> fieldToreturn = new ArrayList<String>(Arrays.asList(FieldtoRArray));

		final StringBuilder td = new StringBuilder();
		Boolean FinalSuccess = false;
		final StringBuilder tdfail = new StringBuilder();
		String FirsteachExactFieldToreturn;

		for (String s1 : fieldToreturn) {
			FirsteachExactFieldToreturn = PropLoad.getTestData(s1).split(",")[1].trim();
			if (FirsteachExactFieldToreturn.contains(".") == true) {
				final String jsonpath = FirsteachExactFieldToreturn.replaceAll("\\.", "\\..");
				final JsonPath json = JsonPath.compile("$.." + jsonpath);
				final List<Object> value = json.read(response.getBody().asString());
				if (value.size() > 0) {
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
				if (value.size() > 0) {
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
			ReportHelper.log(LogStatus.PASS, "fieldtoReturn", "fieldtoReturn present in the response " + td.toString());
		} else {
			LoggerUtil.log("fieldtoReturn is not present in the response" + tdfail.toString(), "FAIL", "SubStep", null);
			ReportHelper.log(LogStatus.FAIL, "fieldtoReturn",
					"fieldtoReturn is not present in the response " + tdfail.toString());
		}
	}
	catch(Exception e){
		ReportHelper.log(LogStatus.FAIL, "Error Message", e.getMessage());
		e.getMessage();
		e.printStackTrace();
	}
}

	/**
	 * String validation with special characters
	 * @param response
	 * @param queryString
	 * @throws Exception
	 */
	public void stringValidationWithSpecialCharacters(Response response, String queryString) throws Exception {
		final JSONArray arrayValuesFromJson = APIHelper.retrivejsonArrayFromJson("$.searchResults[*]",
				response.asString());
		if (queryString != null) {
			if (queryString.contains(",")) {
				final String[] splittedText = queryString.split(",");
				final List<String> splitText = Arrays.asList(splittedText);
				boolean first = false;
				boolean second = false;
				first = arrayValuesFromJson.toString().toLowerCase().contains(splitText.get(0).toLowerCase());
				second = arrayValuesFromJson.toString().toLowerCase().contains(splitText.get(1).toLowerCase());
				if (first == true && second == true) {
					LoggerUtil.log("String is present in the response", "PASS", "SubStep", null);
					ReportHelper.log(LogStatus.PASS, "Search String",
							"Special character validation is working as expected and both strings "
									+ splitText.get(0).toLowerCase() + " and " + splitText.get(1).toLowerCase()
									+ " are present");
				} else {
					LoggerUtil.log("String is not present in the response", "FAIL", "SubStep", null);
					ReportHelper.log(LogStatus.FAIL, "Search String",
							"Special character validation is not working as expected since both strings "
									+ splitText.get(0).toLowerCase() + " and " + splitText.get(1).toLowerCase()
									+ " are not present");
				}
			} else {
				if (arrayValuesFromJson.toString().toLowerCase().contains(queryString.toLowerCase())) {
					LoggerUtil.log("String is present in the response", "PASS", "SubStep", null);
					ReportHelper.log(LogStatus.PASS, "Search String",
							"Special character validation is working as expected and the search string "
									+ queryString.toLowerCase() + " is present");
				} else {
					LoggerUtil.log("String is not present in the response", "FAIL", "SubStep", null);
					ReportHelper.log(LogStatus.FAIL, "Search String",
							"Special character validation is not working as expected since the search string "
									+ queryString.toLowerCase() + " is not present");

				}
			}
		}
	}

	/**
	 * Stop word validation
	 * @param data_Search
	 * @param response
	 * @param querySearchString
	 * @param stopWordField
	 * @throws Exception
	 */
	public void validateStopWord(LinkedHashMap<String, String> data_Search, Response response, String querySearchString, List<Object> stopWordField) throws Exception {
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		boolean bSuccessQueryString = false;
		boolean bSuccessStopWord = false;
		
		final String[] stopWordsArr = { "a", "an", "and", "are", "as", "at", "be", "but", "by", "for", "if", "in", "into", "is", "it","no", "not", "of", "on", "or", "such", "that", "the", "their", "then", "there", "these", "they", "this","to", "was", "will", "with" };
		final List<String> stopWords = new ArrayList<String>();
		stopWords.addAll(Arrays.asList(stopWordsArr));
		
		final Set<String> expValue = Template.getQueryStringSets(querySearchString, false);
		final String[] expValueArr = expValue.toArray(new String[expValue.size()]);
		Set<String> queryStrings = new HashSet<String>();
		Set<String> stopWordStrings = new HashSet<String>();
		String eachStringNoChar = "";
		final StringBuilder queryStringNotFound = new StringBuilder();
		final StringBuilder stopWordNotFound = new StringBuilder();
		
		for (String eachString : expValueArr){
			for (char eachChar : eachString.toCharArray()){
				int asciiValue = (int) eachChar;
				if((asciiValue >= 65 && asciiValue <= 90) || (asciiValue >= 97 && asciiValue <= 122))
				{
					eachStringNoChar = eachStringNoChar + eachChar;
				}
				else
				{
					queryStrings.add(eachStringNoChar);
					eachStringNoChar = "";
				}
			}
		queryStrings.add(eachStringNoChar);
		eachStringNoChar = "";
		}
		
		for (String s : stopWords) {
			for(String eachQuery : queryStrings){
				if (s.equals(eachQuery))
					stopWordStrings.add(eachQuery);
			}
		}
		queryStrings.removeAll(stopWordStrings);
		
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMatchedName_JP, response.asString());
		if (arrayValuesFromJson.size() == 0) {
			LoggerUtil.log("Count is returned as Zero - please check your QueryString", "FAIL", "SubStep", null);
			ReportHelper.log(LogStatus.FAIL, "getValidateStopWord", "Count is returned as Zero");
		} 
		else 
		{
			for (String eachQueryString: queryStrings){
				for (Object jsonObject : arrayValuesFromJson) {
				@SuppressWarnings("unchecked")
				final Map<String, String> set = (Map<String, String>) jsonObject;
					for (String fValue : set.values()) {					
						bSuccessQueryString = Wildcard.emtagmatch(fValue.toLowerCase(), eachQueryString);
						if (bSuccessQueryString)
							break;
					}
					if (bSuccessQueryString)
						break;
				}
				if(!bSuccessQueryString)
					queryStringNotFound.append(eachQueryString + ",");
			}
			
			for (String eachStopWord: stopWordStrings){
				for (Object jsonObject : arrayValuesFromJson) {
				@SuppressWarnings("unchecked")
				final Map<String, String> set = (Map<String, String>) jsonObject;
					for (String fValue : set.values()) {					
						bSuccessStopWord = Wildcard.emtagmatch(fValue.toLowerCase(), eachStopWord);
						if (bSuccessStopWord)
							break;
					}
					if (bSuccessStopWord)
						break;
				}
				if(!bSuccessStopWord)
					stopWordNotFound.append(eachStopWord + ",");
			}
			
			if (stopWordField.get(0).toString().contains("true"))
			{
				if ((bSuccessQueryString) && (!bSuccessStopWord))
				{
					ReportHelper.log(LogStatus.PASS, "getValidateStopWord","Query strings are present in <em> tag and the stop words are not present in the <em> tag");
				}
				else
				{
					ReportHelper.log(LogStatus.FAIL, "getValidateStopWord","Unexpected content in the <em> tag QueryString");
				}
			}
			if (stopWordField.get(0).toString().contains("false"))
			{
				if ((bSuccessQueryString) && (bSuccessStopWord))
				{
					ReportHelper.log(LogStatus.PASS, "getValidateStopWord","Query string and the stop word are present in the <em> tag");
				}
				else
				{
					ReportHelper.log(LogStatus.FAIL, "getValidateStopWord","Query string and the stop word are not present in the <em> tag");
				}					
			}				
		}
	}
	
	/**
	 * Stop word validation for exchangeV3
	 * @param data_Search
	 * @param response
	 * @param querySearchString
	 * @param stopWordField
	 * @throws Exception
	 */
	public void validateStopWordExchangeV3(LinkedHashMap<String, String> data_Search, Response response, String querySearchString, List<Object> stopWordField) throws Exception {
		final int expStatus_code = Integer.parseInt(data_Search.get(Constants.expStatusCode));
		ResponseValidator.validateResponseCode(response, expStatus_code);

		boolean bSuccessQueryString = false;
		boolean bSuccessStopWord = false;
		
		final String[] stopWordsArr = { "a", "an", "and", "are", "as", "at", "be", "but", "by", "for", "if", "in", "into", "is", "it","no", "not", "of", "on", "or", "such", "that", "the", "their", "then", "there", "these", "they", "this","to", "was", "will", "with" };
		final List<String> stopWords = new ArrayList<String>();
		stopWords.addAll(Arrays.asList(stopWordsArr));
		
		final Set<String> expValue = Template.getQueryStringSets(querySearchString, false);
		final String[] expValueArr = expValue.toArray(new String[expValue.size()]);
		Set<String> queryStrings = new HashSet<String>();
		Set<String> stopWordStrings = new HashSet<String>();
		String eachStringNoChar = "";
				
		for (String eachString : expValueArr){
			for (char eachChar : eachString.toCharArray()){
				int asciiValue = (int) eachChar;
				if((asciiValue >= 65 && asciiValue <= 90) || (asciiValue >= 97 && asciiValue <= 122))
				{
					eachStringNoChar = eachStringNoChar + eachChar;
				}
				else
				{
					queryStrings.add(eachStringNoChar);
					eachStringNoChar = "";
				}
			}
		queryStrings.add(eachStringNoChar);
		eachStringNoChar = "";
		}
		
		for (String s : stopWords) {
			for(String eachQuery : queryStrings){
				if (s.equals(eachQuery))
					stopWordStrings.add(eachQuery);
			}
		}
		queryStrings.removeAll(stopWordStrings);
		
		final StringBuilder queryStringNotFound = new StringBuilder();
		final StringBuilder stopWordNotFound = new StringBuilder();
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(ResponseJsonPath.v2SearchMatchedName_JP, response.asString());
		if (arrayValuesFromJson.size() == 0) {
			LoggerUtil.log("Count is returned as Zero - please check your QueryString", "FAIL", "SubStep", null);
			ReportHelper.log(LogStatus.FAIL, "getValidateStopWord", "Count is returned as Zero");
		} 
		else 
		{
			for (String eachQueryString: queryStrings){
				for (Object jsonObject : arrayValuesFromJson) {
				@SuppressWarnings("unchecked")
				final Map<String, String> set = (Map<String, String>) jsonObject;
					for (String fValue : set.values()) {					
						bSuccessQueryString = Wildcard.emtagmatch(fValue.toLowerCase(), eachQueryString);
						if (bSuccessQueryString)
							break;
					}
					if (bSuccessQueryString)
						break;
				}
				if(!bSuccessQueryString)
					queryStringNotFound.append(eachQueryString + ",");
			}
			
			for (String eachStopWord: stopWordStrings){
				for (Object jsonObject : arrayValuesFromJson) {
				@SuppressWarnings("unchecked")
				final Map<String, String> set = (Map<String, String>) jsonObject;
					for (String fValue : set.values()) {					
						bSuccessStopWord = Wildcard.emtagmatch(fValue.toLowerCase(), eachStopWord);
						if (bSuccessStopWord)
							break;
					}
					if (bSuccessStopWord)
						break;
				}
				if(!bSuccessStopWord)
					stopWordNotFound.append(eachStopWord + ",");
			}
			if ((bSuccessQueryString) && (bSuccessStopWord))
			{
				ReportHelper.log(LogStatus.PASS, "getValidateStopWord","Query string and the stop word are present in the <em> tag");
			}
			else
			{
				ReportHelper.log(LogStatus.FAIL, "getValidateStopWord","Either Query string or stop word or the both not present in the <em> tag");
			}					
		}
	}
	
	/**
	 * Stop word check
	 * @param data_Search
	 * @param response
	 * @param querySearchString
	 * @param stopWordField
	 * @throws Exception
	 */
	public void stopWordCheck(LinkedHashMap<String, String> data_Search, Response response, String querySearchString, List<Object> stopWordField)
			throws Exception {

		final String res = response.getBody().asString();
		String stop = null;
		final String[] list = { "a", "an", "and", "are", "as", "at", "be", "but", "by", "for", "if", "in", "into", "is", "it","no", "not", "of", "on", "or", "such", "that", "the", "their", "then", "there", "these", "they", "this","to", "was", "will", "with" };
		final List<String> stopwords = new ArrayList<String>();
		stopwords.addAll(Arrays.asList(list));
		for (String s : stopwords) {
			if (querySearchString.toLowerCase().contains((s))) {
				stop = s;
			}
		}
		final String whole = Constants.emprefixtag + stop + "<" ;
		final boolean val = res.toLowerCase().trim().contains(whole.toLowerCase().trim());
		
		if (stopWordField.get(0).toString().contains("true"))
			if (!val) {
				ReportHelper.log(LogStatus.PASS, "stopwordcheck" ,"stopwords are not highlighted in em tag");
			} else {
				ReportHelper.log(LogStatus.FAIL,"stopwordcheck" ,"stopwords are highlighted in em tag");
			}
		else if (stopWordField.get(0).toString().contains("false")) 
			if (val) {
				ReportHelper.log(LogStatus.PASS,"stopwordcheck" ,"stopwords are highlighted in em tag");
			} else {
				ReportHelper.log(LogStatus.FAIL, "stopwordcheck", "stopwords are not highlighted in em tag");
			}
	}
	
	/**
	 * Stop word check for exchangeV3
	 * @param data_Search
	 * @param response
	 * @param querySearchString
	 * @param stopWordField
	 * @throws Exception
	 */
	public void stopWordCheckExchangeV3(LinkedHashMap<String, String> data_Search, Response response, String querySearchString, List<Object> stopWordField)
			throws Exception {
		boolean val = false;
		final String res = response.getBody().asString();
		final String[] list = { "a", "an", "and", "are", "as", "at", "be", "but", "by", "for", "if", "in", "into", "is", "it","no", "not", "of", "on", "or", "such", "that", "the", "their", "then", "there", "these", "they", "this","to", "was", "will", "with" };
		final List<String> stopwords = new ArrayList<String>();
		stopwords.addAll(Arrays.asList(list));
		Set<String> stopWords = getStopWordfromQueryString(stopwords, querySearchString);
		
		for (String s : stopWords) {
			final String whole = Constants.emprefixtag + s + "<" ;
			val = res.toLowerCase().trim().contains(whole.toLowerCase().trim());
			if (val == false);
			break;
		}
		
		if (val) {
			ReportHelper.log(LogStatus.PASS,"stopwordcheck" ,"stopwords are highlighted in em tag");
		} else {
			ReportHelper.log(LogStatus.FAIL, "stopwordcheck", "stopwords are not highlighted in em tag");
		}
	}
	
	/**
	 * Specific field validation for POST call
	 * @param response
	 * @param requestBody
	 * @throws Exception
	 */
	public void post_specificField(Response response, String requestBody) throws Exception {

		final String specificSearch = APIHelper.retriveValuefromJson("$..queryString", requestBody);
		List<String> twoString = new ArrayList<String>();
		twoString = APIHelper.post_SplitStringForLogicalOperator(requestBody);
		
		final int product = APIHelper.retrivejsonArrayFromJson("$.searchResults[*].productId", response.asString()).size();
		final int countfirstSpecificField;
		
		if (specificSearch.contains("AND") == true) {
			countfirstSpecificField = specfic_reusable(response, twoString.get(0));
			if (countfirstSpecificField == product){
				LoggerUtil.log("AND Specific Operation works as expected", "PASS", "SubStep", null);
				ReportHelper.log(LogStatus.PASS, "AND", "AND Operation works as expected");
			}
			else
			{
				LoggerUtil.log("AND Specific Operation works as expected", "FAIL", "SubStep", null);
				ReportHelper.log(LogStatus.FAIL, "AND", "AND Operation works as expected");
			}
		} else if (specificSearch.contains("OR") == true) {
			countfirstSpecificField = specfic_reusable(response, twoString.get(0));
			if (countfirstSpecificField > 1 && product > 1){
				LoggerUtil.log("OR Specific Operation works as expected", "PASS", "SubStep", null);
				ReportHelper.log(LogStatus.PASS, "OR", "OR Operation works as expected");
			}
			else{
				LoggerUtil.log("OR Specific Operation works as expected", "FAIL", "SubStep", null);
				ReportHelper.log(LogStatus.FAIL, "OR", "OR Operation works as expected");
				}
			
			
		} else {
			countfirstSpecificField = specfic_reusable(response, specificSearch);
			if (countfirstSpecificField == product){
				LoggerUtil.log("Specific Operation works as expected", "PASS", "SubStep", null);
				ReportHelper.log(LogStatus.PASS, "Specific Operation", "Specific Operation works as expected");
			}
			else{
				Assert.assertTrue(false, "Specific Operation not works - might be empty response - please check");
				ReportHelper.log(LogStatus.FAIL, "Specific Operation", "Specific Operation works as expected");
			}
		}
		
	}	
	
	/**
	 * Specific field validation for GET call
	 * @param response
	 * @param parameters
	 * @throws Exception
	 */
	public void get_specificField(Response response, Map<String, String> parameters) throws Exception {

		final String specificSearch = APIHelper.getValueforKey_SingleSearch(parameters, "queryString");
		List<String> twoString = new ArrayList<String>();
		twoString = APIHelper.get_SplitStringForLogicalOperator(parameters);
		final int product = APIHelper.retrivejsonArrayFromJson("$.searchResults[*].productId", response.asString()).size();
		final int countfirstSpecificField;
		
		if (specificSearch.contains("AND") == true) {
			countfirstSpecificField = specfic_reusable(response, twoString.get(0));
			if (countfirstSpecificField == product){
				LoggerUtil.log("AND Specific Operation works as expected", "PASS", "SubStep", null);
				ReportHelper.log(LogStatus.PASS, "AND", "AND Operation works as expected");
			}
			else
			{
				LoggerUtil.log("AND Specific Operation works as expected", "FAIL", "SubStep", null);
				ReportHelper.log(LogStatus.FAIL, "AND", "AND Operation works as expected");
			}
		} else if (specificSearch.contains("OR") == true) {
			countfirstSpecificField = specfic_reusable(response, twoString.get(0));
			if (countfirstSpecificField > 1 && product > 1){
				LoggerUtil.log("OR Specific Operation works as expected", "PASS", "SubStep", null);
				ReportHelper.log(LogStatus.PASS, "OR", "OR Operation works as expected");
			}
			else{
				LoggerUtil.log("OR Specific Operation works as expected", "FAIL", "SubStep", null);
				ReportHelper.log(LogStatus.FAIL, "OR", "OR Operation works as expected");
				}
			
			
		} else {
			countfirstSpecificField = specfic_reusable(response, specificSearch);
			if (countfirstSpecificField == product){
				LoggerUtil.log("Specific Operation works as expected", "PASS", "SubStep", null);
				ReportHelper.log(LogStatus.PASS, "Specific Operation", "Specific Operation works as expected");
			}
			else{
				Assert.assertTrue(false, "Specific Operation not works - might be empty response - please check");
				ReportHelper.log(LogStatus.FAIL, "Specific Operation", "Specific Operation works as expected");
			}
		}
		
	}
	
	/**
	 * @param response
	 * @param specificSearch
	 * @return
	 * @throws Exception
	 */
	public int specfic_reusable(Response response, String specificSearch) throws Exception {
		//FirsteachExactFieldToreturn = PropLoad.getTestData(specificSearch).split(",")[1].trim();
		
		final String[] splitSearch = specificSearch.split(":");
		final List<String> stringList = new ArrayList<String>(Arrays.asList(splitSearch));
		final String expectedKey = stringList.get(0);
		
		String FirsteachExactFieldToreturn;
		int count = 0 ;

			FirsteachExactFieldToreturn = PropLoad.getTestData(expectedKey).split(",")[1].trim();
			if (FirsteachExactFieldToreturn.contains(".") == true) {
				final String jsonpath = FirsteachExactFieldToreturn.replaceAll("\\.", "\\..");
				final JsonPath json = JsonPath.compile("$.." + jsonpath);
				final List<Object> value = json.read(response.getBody().asString());
				count =value.size();
				
			} else {
				final String jsonpath = FirsteachExactFieldToreturn;
				final JsonPath json = JsonPath.compile("$.." + jsonpath);
				final List<Object> value = json.read(response.getBody().asString());
				count =value.size();
			}
			return count;
		}

	
		/*final String[] splitSearch = specificSearch.split(":");
		final List<String> stringList = new ArrayList<String>(Arrays.asList(splitSearch));
		final String expectedKey = stringList.get(0).toLowerCase().trim();
		final String expectedValue = stringList.get(1).toLowerCase().trim();

		int count = 0;
		final JSONArray arrayValuesFromJson = APIHelper.retrivejsonArrayFromJson("$.searchResults[*].source",response.asString());
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
				if (pair.getKey().toString().contains(expectedKey)
						&& pair.getValue().toString().toLowerCase().contains(expectedValue)) {
					count = count + 1;
					break;
				}
			}
		}
		return count;

	}*/
	
	/**
	 * To get the stop words from the query string
	 * @param stopWords
	 * @param querySearchString
	 * @return
	 */
	public Set<String> getStopWordfromQueryString(List<String> stopWords, String querySearchString){
		final Set<String> expValue = Template.getQueryStringSets(querySearchString, false);
		final String[] expValueArr = expValue.toArray(new String[expValue.size()]);
		Set<String> queryStrings = new HashSet<String>();
		Set<String> stopWordStrings = new HashSet<String>();
		String eachStringNoChar = "";
				
		for (String eachString : expValueArr){
			for (char eachChar : eachString.toCharArray()){
				int asciiValue = (int) eachChar;
				if((asciiValue >= 65 && asciiValue <= 90) || (asciiValue >= 97 && asciiValue <= 122))
				{
					eachStringNoChar = eachStringNoChar + eachChar;
				}
				else
				{
					queryStrings.add(eachStringNoChar);
					eachStringNoChar = "";
				}
			}
		queryStrings.add(eachStringNoChar);
		eachStringNoChar = "";
		}
		
		for (String s : stopWords) {
			for(String eachQuery : queryStrings){
				if (s.equals(eachQuery))
					stopWordStrings.add(eachQuery);
			}
		}
		queryStrings.removeAll(stopWordStrings);
		return stopWordStrings;
	}	
}
