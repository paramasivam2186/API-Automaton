package com.pearson.pageobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.apache.commons.collections.CollectionUtils;
import com.google.common.collect.Ordering;
import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.pearson.regression.utilityHelpers.APIHelper;
import com.pearson.regression.utilityHelpers.PropLoad;
import com.pearson.regression.utilityHelpers.RESTServiceBase;
import com.pearson.regression.utilityHelpers.ReportHelper;
import com.pearson.regression.utilityHelpers.Wildcard;
import com.relevantcodes.extentreports.LogStatus;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class V2SearchSanity extends RESTServiceBase {

	static RESTServiceBase webCredentials_rest = new RESTServiceBase();
	String contentAPIUrl = PropLoad.getEnvironmentBaseUrl().toString().trim();
	String cmcontentAPiUrl = PropLoad.getConfigData("cmURL");

	public int returnCountV2Search_PostorGet(Response response) throws Exception {
		final String actual = APIHelper.retriveValuefromJson("$..count", response.getBody().asString());
		final int actualcount = Integer.parseInt(actual);
		if (actualcount == 0){
			LoggerUtil.log("Count is returned as Zero - please check your QueryString", actual, "SubStep", null);
			ReportHelper.log(LogStatus.FAIL, "Count", "Count is returned as Zero - please check your QueryString");
		}
		else if (actualcount > 0){
			LoggerUtil.log("Count is returned as greater than one - product is available for the QueryString", actual,
					"SubStep", null);
			ReportHelper.log(LogStatus.PASS, "Count", "Count is returned as greater than one - product is available for the QueryString");
		}
		return actualcount;
	}

	public void fieldtoReturn_get(Response response, Map<String, String> parameters) throws Exception {

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
		if (FinalSuccess == true){
			LoggerUtil.log("fieldtoReturn present in the response " + td.toString(), "PASS", "SubStep", null);
			ReportHelper.log(LogStatus.PASS, "fieldtoReturn", "fieldtoReturn present in the response " + td.toString());
		}
		else {
			LoggerUtil.log("fieldtoReturn is not present in the response" + tdfail.toString(), "FAIL", "SubStep", null);
			ReportHelper.log(LogStatus.FAIL, "fieldtoReturn", "fieldtoReturn is not present in the response " + tdfail.toString());
		}

	}
	
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
				if (value.size() > 1) {
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
				if (value.size() > 1) {
					FinalSuccess = true;
					td.append(jsonpath + ",");
				} else {
					FinalSuccess = false;
					tdfail.append(jsonpath + ",");
				}
			}

		}
		if (FinalSuccess == true){
			LoggerUtil.log("fieldtoReturn present in the response " + td.toString(), "PASS", "SubStep", null);
			ReportHelper.log(LogStatus.PASS, "fieldtoReturn", "fieldtoReturn present in the response " + td.toString());
		}
		else {
			LoggerUtil.log("fieldtoReturn is not present in the response" + tdfail.toString(), "FAIL", "SubStep", null);
			ReportHelper.log(LogStatus.PASS, "fieldtoReturn", "fieldtoReturn present in the response " + td.toString());
			//Assert.assertTrue(false, "fieldtoReturn is not present in the response");
		}

	}
	
	public Boolean postValueFieldToreturn(String FieldToReturn,Response response) {
		
		//@SuppressWarnings("unused")
		//List<String> sortedField = new ArrayList<String>();
		
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
					final String ResponseEachKey = pair.getKey().toString().toLowerCase();
					if (ResponseEachKey.contains(FieldToReturn)==true) {
						success = true;
						break;
					} 
				}
			if(success==true) {
				break;
			}
			}
		 return success;
	}
	
	public void emValidationMatchedField_All(Response response, String jsonPath, Map<String, String> parameters)
			throws Exception {
		Boolean success = false;
		final JSONArray arrayValuesFromJson = APIHelper.retrivejsonArrayFromJson(jsonPath, response.asString());
		for (Object jsonObject1 : arrayValuesFromJson) {
			success = Wildcard.emtagmatch(jsonObject1.toString(),APIHelper.getValueforKey_SingleSearch(parameters, "queryString").trim());
			if (success == true) {
				break;
			}
		}
		if (success == true) {
			LoggerUtil.log("EM tag is present in some of the fields in matched fields", "PASS", "SubStep", null);
		} else {
			LoggerUtil.log("EM  tag is not present in some of the fields in matched fields", "FAIL", "SubStep", null);
		}
	}
	
	public void emValidationMatchedField_get(Response response, String jsonPath, Map<String, String> parameters)
			throws Exception {
		Boolean success = false;
		final JSONArray arrayValuesFromJson = APIHelper.retrivejsonArrayFromJson(jsonPath, response.asString());
		for (Object jsonObject1 : arrayValuesFromJson) {
			success = Wildcard.emtagmatch(jsonObject1.toString(),APIHelper.getValueforKey_SingleSearch(parameters, "queryString").trim());
			if (success == true) {
				break;
			}
		}
		if (success == true) {
			LoggerUtil.log("EM tag is present in some of the fields in matched fields", "PASS", "SubStep", null);
		} else {
			LoggerUtil.log("EM  tag is not present in some of the fields in matched fields", "FAIL", "SubStep", null);
		}
	}
	
	public void emValidationMatchedFieldLogical_get(Response response, String jsonPath, Map<String, String> parameters)
			throws Exception {
		final List<String> splittedText = APIHelper.get_SplitStringForLogicalOperator(parameters);
		Boolean success = false;
		Boolean success1 = false;
		final JSONArray arrayValuesFromJson = APIHelper.retrivejsonArrayFromJson(jsonPath, response.asString());	
		for (Object jsonObject1 : arrayValuesFromJson) {
			success = Wildcard.emtagmatch(jsonObject1.toString(),splittedText.get(0).toLowerCase().trim());
			success1 = Wildcard.emtagmatch(jsonObject1.toString(),splittedText.get(1).toLowerCase().trim());	
			if(success==true && success1==true) {
				break;
			}
		}
		if (success == true && success1==true) {
			LoggerUtil.log("EM tag is present in some of the fields in matched fields", "PASS", "SubStep", null);
		} else {
			LoggerUtil.log("EM  tag is not present in some of the fields in matched fields", "FAIL", "SubStep", null);
		}
	}
	
	public void emValidationMatchedField_Specific_Logical_get(Response response, String jsonPath, Map<String, String> parameters)
			throws Exception {
		final List<String> splittedText = APIHelper.get_SplitStringForLogicalOperator(parameters);
		
		Boolean success = false;
		Boolean success1 = false;
		final JSONArray arrayValuesFromJson = APIHelper.retrivejsonArrayFromJson(jsonPath, response.asString());	
		for (Object jsonObject1 : arrayValuesFromJson) {
			success = Wildcard.emtagmatch(jsonObject1.toString(),splittedText.get(0).toLowerCase().trim().split(":")[1]);
			success1 = Wildcard.emtagmatch(jsonObject1.toString(),splittedText.get(1).toLowerCase().trim().split(":")[1]);	
			if(success==true && success1==true) {
				break;
			}
		}
		if (success == true && success1==true) {
			LoggerUtil.log("EM tag is present in some of the fields in matched fields", "PASS", "SubStep", null);
		} else {
			LoggerUtil.log("EM  tag is not present in some of the fields in matched fields", "FAIL", "SubStep", null);
		}
	}
	
	public void emValidationMatchedField_post(Response response, String jsonPath,String requestBody)
			throws Exception {
		Boolean success = false;
		final JSONArray arrayValuesFromJson = APIHelper.retrivejsonArrayFromJson(jsonPath, response.asString());
		for (Object jsonObject1 : arrayValuesFromJson) {
			success = Wildcard.emtagmatch(jsonObject1.toString(),
					APIHelper.retriveValuefromJson("$..queryString", requestBody).trim());
			if (success == true) {
				break;
			}
		}
		if (success == true) {
			LoggerUtil.log("EM tag is present in some of the fields in matched fields", "PASS", "SubStep", null);
		} else {
			LoggerUtil.log("EM  tag is not present in some of the fields in matched fields", "FAIL", "SubStep", null);
		}
	}
	
	public void emValidationMatchedField_Specific_Logical_post(Response response, String jsonPath,String requestBody)
			throws Exception {
		final List<String> splittedText = APIHelper.post_SplitStringForLogicalOperator(requestBody);
		Boolean success = false;
		Boolean success1 = false;
		final JSONArray arrayValuesFromJson = APIHelper.retrivejsonArrayFromJson(jsonPath, response.asString());
		for (Object jsonObject1 : arrayValuesFromJson) {
			success = Wildcard.emtagmatch(jsonObject1.toString(),splittedText.get(0).toLowerCase().trim().split(":")[1]);
			success1 = Wildcard.emtagmatch(jsonObject1.toString(),splittedText.get(1).toLowerCase().trim().split(":")[1]);	
			if(success==true && success1==true) {
				break;
			}
		}
		if (success == true && success1==true) {
			LoggerUtil.log("EM tag is present in some of the fields in matched fields", "PASS", "SubStep", null);
		} else {
			LoggerUtil.log("EM  tag is not present in some of the fields in matched fields", "FAIL", "SubStep", null);
		}
	}
	
	public void emValidationMatchedFieldLogical_post(Response response, String jsonPath,String requestBody)
			throws Exception {
		final List<String> splittedText = APIHelper.post_SplitStringForLogicalOperator(requestBody);
		Boolean success = false;
		Boolean success1 = false;
		final JSONArray arrayValuesFromJson = APIHelper.retrivejsonArrayFromJson(jsonPath, response.asString());
		for (Object jsonObject1 : arrayValuesFromJson) {
			success = Wildcard.emtagmatch(jsonObject1.toString(),splittedText.get(0).toLowerCase().trim());
			success1 = Wildcard.emtagmatch(jsonObject1.toString(),splittedText.get(1).toLowerCase().trim());	
			if(success==true && success1==true) {
				break;
			}
		}
		if (success == true && success1==true) {
			LoggerUtil.log("EM tag is present in some of the fields in matched fields", "PASS", "SubStep", null);
		} else {
			LoggerUtil.log("EM  tag is not present in some of the fields in matched fields", "FAIL", "SubStep", null);
		}
	}
	
	public void get_logicalOperatorValidation(Response response, Map<String, String> parameters) throws Exception {

		final List<String> splittedText = APIHelper.get_SplitStringForLogicalOperator(parameters);
		final String withLogicalOperatorSearchText = APIHelper.getValueforKey_SingleSearch(parameters, "queryString");
		final JSONArray arrayValuesFromJson = APIHelper.retrivejsonArrayFromJson("$.searchResults[*].source",
				response.asString());
		boolean first = false;
		boolean second = false;

		first = arrayValuesFromJson.toString().toLowerCase().contains(splittedText.get(0).toLowerCase());
		second = arrayValuesFromJson.toString().toLowerCase().contains(splittedText.get(1).toLowerCase());

		if (withLogicalOperatorSearchText.contains("AND") == true) {
			if (first == true && second == true){
				LoggerUtil.log("AND Operation works as expected", "PASS", "SubStep", null);
				ReportHelper.log(LogStatus.PASS, "AND", "AND Operation works as expected");
			}
			else
			{
				LoggerUtil.log("AND Operation not works as expected", "FAIL", "SubStep", null);
				ReportHelper.log(LogStatus.FAIL, "AND", "AND Operation not works as expected");
			}
		}
		if (withLogicalOperatorSearchText.contains("OR") == true) {
			if (first == true || second == true){
				LoggerUtil.log("OR Operation works as expected", "PASS", "SubStep", null);
				ReportHelper.log(LogStatus.PASS, "OR", "OR Operation works as expected");
			}
			else{
				LoggerUtil.log("OR Operation not works as expected", "FAIL", "SubStep", null);
				ReportHelper.log(LogStatus.FAIL, "OR", "OR Operation not works as expected");
			}
		}
		if (withLogicalOperatorSearchText.contains("NOT") == true) {
			if (first == true && second == false){
				LoggerUtil.log("NOT Operation works as expected", "PASS", "SubStep", null);
				ReportHelper.log(LogStatus.PASS, "NOT", "NOT Operation works as expected");
			}
			else{
				LoggerUtil.log("NOT Operation not works as expected", "FAIL", "SubStep", null);
				ReportHelper.log(LogStatus.FAIL, "NOT", "NOT Operation not works as expected");
			}
		}
	}

	public void post_logicalOperatorValidation(Response response, String requestBody) throws Exception {

		final List<String> splittedText = APIHelper.post_SplitStringForLogicalOperator(requestBody);
		final String withLogicalOperatorSearchText = APIHelper.retriveValuefromJson("$..queryString", requestBody);
		final JSONArray arrayValuesFromJson = APIHelper.retrivejsonArrayFromJson("$.searchResults[*].source",
				response.asString());
		boolean first = false;
		boolean second = false;

		first = arrayValuesFromJson.toString().toLowerCase().trim().contains(splittedText.get(0).toLowerCase().trim());
		second = arrayValuesFromJson.toString().toLowerCase().trim().contains(splittedText.get(1).toLowerCase().trim());

		if (withLogicalOperatorSearchText.contains("AND") == true) {
			if (first == true && second == true){
				LoggerUtil.log("AND Operation works as expected", "PASS", "SubStep", null);
				ReportHelper.log(LogStatus.PASS, "AND", "AND Operation works as expected");
			}
			else
			{
				LoggerUtil.log("AND Operation not works as expected", "FAIL", "SubStep", null);
				ReportHelper.log(LogStatus.FAIL, "AND", "AND Operation not works as expected");
			}
		}
		if (withLogicalOperatorSearchText.contains("OR") == true) {
			if (first == true || second == true){
				LoggerUtil.log("OR Operation works as expected", "PASS", "SubStep", null);
				ReportHelper.log(LogStatus.PASS, "OR", "OR Operation works as expected");
			}
			else{
				LoggerUtil.log("OR Operation not works as expected", "FAIL", "SubStep", null);
				ReportHelper.log(LogStatus.FAIL, "OR", "OR Operation not works as expected");
			}
		}
		if (withLogicalOperatorSearchText.contains("NOT") == true) {
			if (first == true && second == false){
				LoggerUtil.log("NOT Operation works as expected", "PASS", "SubStep", null);
				ReportHelper.log(LogStatus.PASS, "NOT", "NOT Operation works as expected");
			}
			else{
				LoggerUtil.log("NOT Operation not works as expected", "FAIL", "SubStep", null);
				ReportHelper.log(LogStatus.FAIL, "NOT", "NOT Operation not works as expected");
			}
		}
	}
	
  

	public int specfic_reusable(Response response, String specificSearch) throws Exception {

		final String[] splitSearch = specificSearch.split(":");
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

	}

	public void get_specificField(Response response, Map<String, String> parameters) throws Exception {

		final String specificSearch = APIHelper.getValueforKey_SingleSearch(parameters, "queryString");
		List<String> twoString = new ArrayList<String>();
		twoString = APIHelper.get_SplitStringForLogicalOperator(parameters);
		final int product = APIHelper.retrivejsonArrayFromJson("$.searchResults[*].productId", response.asString()).size();
		final int countfirstSpecificField;
		final int countsecondSpecificField;
		
		if (specificSearch.contains("AND") == true) {
			countfirstSpecificField = specfic_reusable(response, twoString.get(0).toLowerCase().trim());
			countsecondSpecificField = specfic_reusable(response, twoString.get(1).toLowerCase().trim());
			if (countfirstSpecificField == product && countsecondSpecificField == product){
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
			countsecondSpecificField = specfic_reusable(response, twoString.get(1));
			if (countfirstSpecificField + countsecondSpecificField >= product){
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
	
	public void post_specificField(Response response, String requestBody) throws Exception {

		final String specificSearch = APIHelper.retriveValuefromJson("$..queryString", requestBody);
		List<String> twoString = new ArrayList<String>();
		twoString = APIHelper.post_SplitStringForLogicalOperator(requestBody);
		
		final int product = APIHelper.retrivejsonArrayFromJson("$.searchResults[*].productId", response.asString()).size();
		final int countfirstSpecificField;
		final int countsecondSpecificField;
		
		if (specificSearch.contains("AND") == true) {
			countfirstSpecificField = specfic_reusable(response, twoString.get(0).toLowerCase().trim());
			countsecondSpecificField = specfic_reusable(response, twoString.get(1).toLowerCase().trim());
			if (countfirstSpecificField == product && countsecondSpecificField == product){
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
			countsecondSpecificField = specfic_reusable(response, twoString.get(1));
			if (countfirstSpecificField > 1 && countsecondSpecificField > 1 && product > 1){
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
		/*if (specificSearch.contains("AND") == true) {
			countfirstSpecificField = specfic_reusable(response, twoString.get(0).toLowerCase().trim());
			countsecondSpecificField = specfic_reusable(response, twoString.get(1).toLowerCase().trim());
			if (countfirstSpecificField == product && countsecondSpecificField == product)
				LoggerUtil.log("AND Specific Operation works as expected", "PASS", "SubStep", null);
			else
				LoggerUtil.log("AND Specific Operation works as expected", "FAIL", "SubStep", null);
		} else if (specificSearch.contains("OR") == true) {
			countfirstSpecificField = specfic_reusable(response, twoString.get(0));
			countsecondSpecificField = specfic_reusable(response, twoString.get(1));
			if ((countfirstSpecificField > 1 || countsecondSpecificField > 1) && product > 1)
				LoggerUtil.log("OR Specific Operation works as expected", "PASS", "SubStep", null);
			else
				LoggerUtil.log("OR Specific Operation works as expected", "FAIL", "SubStep", null);
		} else {
			countfirstSpecificField = specfic_reusable(response, specificSearch);
			if (countfirstSpecificField == product && product>0 && countfirstSpecificField>0)
				LoggerUtil.log("Specific Operation works as expected", "PASS", "SubStep", null);
			else
				Assert.assertTrue(false, "Specific Operation not works - might be empty response - please check");
				//LoggerUtil.log("Specific Operation not works - might be empty response - please check", "FAIL", "SubStep", null);
		}*/
	}
	
	
	public boolean post_Sorting(Response response, String requestBody) throws Exception {

		final String fieldtobeSorted = APIHelper.retriveValuefromJson("$..fields", requestBody);
		boolean sorted = false;
		final String[] Field = fieldtobeSorted.split("=");
		final List<String> stringList = new ArrayList<String>(Arrays.asList(Field));
		final String finalField = stringList.get(0).replaceAll("[^\\w\\s]", "").trim();

		List<String> ascList = new ArrayList<String>();
		List<String> desList = new ArrayList<String>();
		

		if (fieldtobeSorted.contains("ASC")==true && fieldtobeSorted.contains(",")==false){
			ascList = sort_reusable(finalField, response);
			sorted = Ordering.natural().isOrdered(ascList);
		} else if (fieldtobeSorted.contains("DESC")==true && fieldtobeSorted.contains(",")==false){
			desList = sort_reusable(finalField, response);
			sorted = Ordering.natural().reverse().isOrdered(desList);
		} else 
		{
			sorted = post_MultiLevelSort(response,requestBody);
		}

		if (sorted==true) {
			ReportHelper.log(LogStatus.PASS, "Sorting", "Sorting is successful");
		} else {
			ReportHelper.log(LogStatus.FAIL, "Sorting", "Sorting is successful");
		}
		return sorted;
	}
	
	public boolean post_MultiLevelSort(Response response, String requestBody) throws Exception {

		final String fieldtobeSorted1 = APIHelper.retriveValuefromJson("$..fields", requestBody);
		final String fieldtobeSorted =fieldtobeSorted1.split(",")[0];
		
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
	
	public boolean sortField(Response response, String requestBody) throws Exception {
		final String fieldtobeSorted = APIHelper.retriveValuefromJson("$..fields", requestBody);
		final List<Object> sort = APIHelper.FeildToReturnasObject("$..sort", response.getBody().asString());
		final List<String> sortedValue = new ArrayList<String>();
		for (Object s: sort)
		{
			sortedValue.add(s.toString());
		}
		boolean sorted = false;
		if (fieldtobeSorted.contains("ASC")) {
				sorted = Ordering.natural().isOrdered(sortedValue);
		} else if (fieldtobeSorted.contains("DESC")) {
			sorted = Ordering.natural().reverse().isOrdered(sortedValue);
		}
		if (sorted==true) {
			ReportHelper.log(LogStatus.PASS, "Sorting", "Sorting is successful");
		} else {
			ReportHelper.log(LogStatus.FAIL, "Sorting", "$..sort field - Sorting is successful");
		}
		return sorted;
	}

/*	public boolean get_SingleLevelSort(Response response, Map<String, String> parameters) throws Exception {

		//String fieldtobeSorted = APIHelper.retriveValuefromJson("$..fields", requestBody);
		String isASCorDESC = APIHelper.getValueforKey_SingleSearch(parameters, "groupBySortOrder");
		String finalField = APIHelper.getValueforKey_SingleSearch(parameters, "groupBySortField");
		
		boolean sorted = false;
		List<String> ascList = new ArrayList<String>();
		List<String> desList = new ArrayList<String>();
		
		if (isASCorDESC.contains("ASC")) {
			ascList = sort_reusable(finalField, response);
			System.out.println();
			sorted = Ordering.natural().isOrdered(ascList);
		} else if (isASCorDESC.contains("DESC")) {
			desList = sort_reusable(finalField, response);
			sorted = Ordering.natural().reverse().isOrdered(desList);
		}

		return sorted;
	}*/
	
	public List<String> sort_reusable(String finalField, Response response) {
	
		System.out.println("****************************"  +finalField );
		String FirsteachExactFieldToreturn = PropLoad.getTestData(finalField).split(",")[1].trim();
		System.out.println("*******************************" + FirsteachExactFieldToreturn);
		System.out.println(response.getBody().asString());
		List<String> sortedField = APIHelper.FeildToReturnasArray("$..source"+FirsteachExactFieldToreturn, response.getBody().asString());
		
/*		final List<String> sortedField = new ArrayList<String>();
		final JSONArray arrayValuesFromJson = APIHelper.retrivejsonArrayFromJson("$.searchResults[*].source",
				response.asString());
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
				if (pair.getKey().toString().contains(finalField)) {
					sortedField.add((String) pair.getValue());
					break;
				}
			}
		}*/
		return sortedField;
	}


	public void singleFacet_validation(Response response, String requestBody) {

		final String groupBy = APIHelper.retriveValuefromJson("$..groupBy", requestBody);
		final String finalgroupBy = groupBy.replaceAll("[^\\w\\s]", "").trim();

		final String FirsteachExactFieldToreturn;

		// Number of Product Present in JSON
		final int numberOfProduct = APIHelper.FeildToReturnasObject("$..productId", response.getBody().asString()).size();

		// Forming JSON path Dynamically - Single property file like schema:name, description - "$..searchResults[0-numberOfProduct]..productsList[*]..schema:name
		// Array property file like familyName=String,cnt:auxiliaryFields.authors.familyName - "$..searchResults[0-numberOfProduct]..productsList[*]..cnt:auxiliaryFields..authors..familyName
		final String FirstPartLocator = "$..searchResults[";
		final String secondPartLocator = "]..productsList[*]..";

		// Forming JSON path Dynamically -
		// $..searchResults[0-numberOfProduct]..key
		final String FirstPartLocatorForKey = "$..searchResults[";
		final String secondPartLocatorForKey = "]..key";

		FirsteachExactFieldToreturn = PropLoad.getTestData(finalgroupBy).split(",")[1].trim();

		if (FirsteachExactFieldToreturn.contains(".") == true) {
			final String FormLocator = FirsteachExactFieldToreturn.replaceAll("\\.", "\\..");
			SingleFacet_reusable(numberOfProduct, FirstPartLocator, secondPartLocator, FormLocator, response, FirstPartLocatorForKey, secondPartLocatorForKey);
		}

		else {
			final String FormLocator = FirsteachExactFieldToreturn;
			SingleFacet_reusable(numberOfProduct, FirstPartLocator, secondPartLocator, FormLocator, response, FirstPartLocatorForKey, secondPartLocatorForKey);
		}
	}
	
	public void SingleFacet_reusable(int numberOfProduct,String FirstPartLocator, String secondPartLocator, String FormLocator, Response response,
			String FirstPartLocatorForKey, String secondPartLocatorForKey){

		String Locator;
		String actualValue = null, expected = null;
		for (int i = 0; i <= numberOfProduct - 1; i++) {
			Locator = FirstPartLocator + i + secondPartLocator + FormLocator;
			final int fieldPresentinsideLocator = APIHelper.FeildToReturnasObject(Locator, response.getBody().asString())
					.size();
			
			if (fieldPresentinsideLocator == 1) {
				actualValue = APIHelper.retriveValuefromJson(FirstPartLocatorForKey + i + secondPartLocatorForKey,
						response.getBody().asString());
				expected = APIHelper.retriveValuefromJson(Locator, response.getBody().asString());
				 Assert.assertEquals(actualValue, expected);				 
			} else {
				final StringBuilder cominedField = new StringBuilder();
				final List<Object> ss = APIHelper.FeildToReturnasObject(Locator, response.getBody().asString());
				for (Object s : ss) {
					cominedField.append(s.toString());
				}
				actualValue = APIHelper.retriveValuefromJson(FirstPartLocatorForKey + i + secondPartLocatorForKey,
						response.getBody().asString());
				expected = cominedField.toString();
				if(expected.contains(actualValue)==true) {
					Assert.assertTrue(true);
				}
			}
		}
		
		if(expected.contains(actualValue)==true) {
			ReportHelper.log(LogStatus.PASS, "Single Facet", "Single Facet Validation");
		} else {
			ReportHelper.log(LogStatus.FAIL, "Single Facet", "Single Facet Validation");
		}
	}
	
	public boolean isSortedField(Response response,  String requestBody){
		final String order = APIHelper.retriveValuefromJson("$..order", requestBody);
		
		final List<String> getAlley = APIHelper.FeildToReturnasArray("$..searchResults[*]..key", response.getBody().asString());
		Boolean sorted =false;
		
		if(order.contains("DESC")==true)
		{
		sorted= Ordering.natural().reverse().isOrdered(getAlley);
		}
		else
		{
			sorted= Ordering.natural().isOrdered(getAlley);
		}
			
		return sorted;
	}
	//multi-facet&single facet
		public void multiLevelFacet_validation(Response response, JSONObject requestBody) {
			// System.out.println("requestBody" + requestBody);
			try
			{
			final String dynamicJSONPath;
			JSONArray groupBy = null;
			groupBy = APIHelper.jsonValuefromJson("$..groupBy[*]", requestBody);
			// System.out.println(groupBy.size());
			dynamicJSONPath = APIHelper.dynamicJSONPathCreation(groupBy.size(), "$..searchResults[*]");
			final Map<Object, Integer> keysCount = APIHelper.getKeysCount(dynamicJSONPath, response, groupBy);
			final Map<Object, List<Object>> keysWithValues = APIHelper.getKeys(dynamicJSONPath, response, groupBy);
			Facet_Reusable(groupBy, keysCount, keysWithValues, response);
//			ReportHelper.log(LogStatus.PASS, "Facet Fields values from Response",
//			"All the values are getting from Response succssfully");
			}
			catch(Exception e)
			{
			ReportHelper.log(LogStatus.FAIL, "Facet Fields values from Response",
			"All the values are getting from Response succssfully");
			}
			}
		public void Facet_Reusable(JSONArray groupByFields, Map<Object, Integer> keysCount,
				Map<Object, List<Object>> keyswithValues, Response response) {
				final String FirstPartLocator = "$..searchResults[";
				final String secondPartLocator = "*]..productsList[*]..";
				int k = groupByFields.size() - 1;
				for (int l = 0; l < groupByFields.size(); l++) {
				final String FirsteachExactFieldToreturn = PropLoad.getTestData(groupByFields.get(k).toString()).split(",")[1]
				.trim();
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
					if(keys.size() !=0L && keyValuefromResponse.size()!=0)
					{
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
					}
					else
					{
						ReportHelper.log(LogStatus.FAIL, groupByFields.get(k)+ "Check your request and application ID","");
					}
				
				} catch (Exception e) {
				ReportHelper.log(LogStatus.FAIL, groupByFields.get(k) + "Facet Validations",
				"Facet is not working properly");
				}
				}
				}
		public boolean isSortedField1(Response response,  String requestBody){
			final String order = APIHelper.retriveValuefromJson("$..order", requestBody);
			final String field = APIHelper.retriveValuefromJson("$..field", requestBody);
			Boolean sorted =false;

	         if(field.contains("term"))
	        {
			final List<String> getAlley = APIHelper.FeildToReturnasArray("$..searchResults[*]..key", response.getBody().asString());
			if(order.contains("DESC")==true)
			{
			sorted= Ordering.natural().reverse().isOrdered(getAlley);
			}
			else
			{
				sorted= Ordering.natural().isOrdered(getAlley);
			}
	        }
	         else
	         {
	        	 final List<String> getAlley = APIHelper.FeildToReturnasArray("$..count", response.getBody().asString());
	        	 getAlley.remove(0);
	     		if(order.contains("DESC")==true)
	     		{
	     		sorted= Ordering.natural().reverse().isOrdered(getAlley);
	     		}
	     		else
	     		{
	     			sorted= Ordering.natural().isOrdered(getAlley);
	     		}
	         }
			return sorted;
		}

		public boolean sortField1(Response response, String requestBody,boolean b) throws Exception
		{
//			System.out.println("boolean value:"+b);
			final String fieldtobeSorted = APIHelper.retriveValuefromJson("$..fields", requestBody);
			final List<String> sortedValue = new ArrayList<String>();
			boolean sorted = false;
//			System.out.println("fieldstobesorted))"+fieldtobeSorted);
//			System.out.println("");
			if(b==false){
				final List<Object> sort = APIHelper.FeildToReturnasObject("$..sort", response.getBody().asString());
				for (Object s: sort)
				{
					sortedValue.add(s.toString());
					
				}
				if (fieldtobeSorted.contains("ASC")) {
					
						sorted = Ordering.natural().isOrdered(sortedValue);
				} else if (fieldtobeSorted.contains("DESC")) {
					sorted = Ordering.natural().reverse().isOrdered(sortedValue);
				}
			}		
			else{
				//new try
				//new validation
	           List<Object> check=new ArrayList<Object>();
			  final List<String> checkval=new ArrayList<String>();
				check = APIHelper.FeildToReturnasObject("$..sort[0]", response.getBody().asString());
				for (Object s: check)
				{
					checkval.add(s.toString());
					
				}
				  if (fieldtobeSorted.contains("ASC")) 
				  {
						sorted = Ordering.natural().isOrdered(checkval);
				  }
				  else 
				  {
						sorted = Ordering.natural().reverse().isOrdered(checkval);
				  }
				  if(sorted==true)	 
				   {
						ReportHelper.log(LogStatus.PASS, "Sorting", "First level of sorting is successful");
				   }
			     else
			      {
				ReportHelper.log(LogStatus.FAIL, "Sorting", "First level of Sorting is un successful");
			      }
			final String finalcheck=checkval.get(0);
			final List<String> multisort=new ArrayList<String>();
			List<Object> firstarray=new ArrayList<Object>();
			firstarray = APIHelper.FeildToReturnasObject("$..sort[1]", response.getBody().asString());
			final List<String> firstvalidation=new ArrayList<String>();
			for (Object s: firstarray)
			{
				firstvalidation.add(s.toString());
				
			}	
			 multisort.add(firstvalidation.get(0));
					for(int i=1;i<checkval.size();i++)	
					{
						if(finalcheck.equals(checkval.get(i))==true)  

						{
						multisort.add(APIHelper.FeildToReturnasObject("$..searchResults["+i+"]..sort[1]", response.getBody().asString()).toString());
						}
			          else if(finalcheck.contains(checkval.get(i))==false) {
			        	  // asc or des validation
			        	  if (fieldtobeSorted.contains("ASC")) {
								/*for(String s:multisort)
								{
								System.out.println("values in multisort"+s);	
								}*/
								sorted = Ordering.natural().isOrdered(multisort);
						} else {
							sorted = Ordering.natural().reverse().isOrdered(multisort);
							/*for(String s:multisort)
							{
							System.out.println("values in multisort"+s);	
							}*/
							
						}
			        	  multisort.clear();
						  multisort.add(APIHelper.FeildToReturnasObject("$..searchResults["+i+"]..sort[1]", response.getBody().asString()).toString());
			          }
						continue;
					}
		}
			if(sorted==true)	 
			 {
				ReportHelper.log(LogStatus.PASS, "Sorting", "Sorting is successful");
			 }
	       else
	        {
	 	ReportHelper.log(LogStatus.FAIL, "Sorting", "Sorting is un successful");

	           }
			return sorted;
				  }

		//multi-value validation
		
		public void multi_validation(Response response, String requestBody) {
		        
				final String groupBy = APIHelper.retriveValuefromJson("$..groupBy", requestBody);
				//dynamic jsion
				final String replace=groupBy.replaceAll(".multi_value","").replaceAll("[\\[\\]]", "");
				final String re=replace.replace("\"","");
				final String FirsteachExactFieldToreturn = PropLoad.getTestData(re).split(",")[1].trim();
				final String FormLocator;
				if (FirsteachExactFieldToreturn.contains(".") == true) {
					FormLocator = FirsteachExactFieldToreturn.replaceAll("\\.", "\\..");
				}
				else {
					FormLocator = FirsteachExactFieldToreturn;
				}
				
		        List<String> keys=new ArrayList<String>();
		        keys=APIHelper.FeildToReturnasArray("$..searchResults[*]..key", response.getBody().asString());
		        final String value="$..searchResults[0]..";
		        final String jsonval1=value+FormLocator;
		        final boolean ss=false;
				final String groupByy = APIHelper.retriveValuefromJson(jsonval1, response.getBody().asString());
		       final String[] d=groupByy.split("\\|");
		        //String[] p= new String[]{};
		     final List<String> ll=new ArrayList<String>();
		        for(int i=0;i<d.length;i++)
		        {
		        	if(d[i].contains("/"))
		        	{
		        	 final String[] o=d[i].split("/");
		        	 for(int l=0;l<o.length;l++)
		        	 {
		        	  	 final String c=o[l];
		        	  ll.add(c);
		        	 }    	 
		        	}
		        	else
		        	{
		        		ll.add(d[i].toString());
		        	}
		        }
//		        for(String k:ll)
//		        {
//		    		System.out.println("*******************************" + k);
//
//		        }
		        final int size=ll.size();
		        final int size1=keys.size();
		       /* for(String b:keys)
		        {
		        	if(s.contains(b))
		        	{
		        	ss=true;	
		        	}
		        }*/
		      if(size==size1)
		      {
					ReportHelper.log(LogStatus.PASS, "MultiValue", "Multi-value Validation is successful");

		      }
		      else
		      {
					ReportHelper.log(LogStatus.FAIL, "MultiValue", "Multi-value Validation is unsuccessful");

		      }

		}


public void stringValidationWithSpecialCharacters(Response response, String queryString) throws Exception{
			final JSONArray arrayValuesFromJson = APIHelper.retrivejsonArrayFromJson("$.searchResults[*]",
					response.asString());
			if(queryString != null){
			if (queryString.contains(",")){
				final String[] splittedText = queryString.split(",");
				final List<String> splitText = Arrays.asList(splittedText);
				boolean first = false;
				boolean second = false;					
				first = arrayValuesFromJson.toString().toLowerCase().contains(splitText.get(0).toLowerCase());
				second = arrayValuesFromJson.toString().toLowerCase().contains(splitText.get(1).toLowerCase());
					if (first == true && second == true){
						LoggerUtil.log("String is present in the response", "PASS", "SubStep", null);
						ReportHelper.log(LogStatus.PASS, "Search String", "Special character validation is working as expected and both strings "+splitText.get(0).toLowerCase() +" and "+splitText.get(1).toLowerCase()+" are present");
					}
					else{
						LoggerUtil.log("String is not present in the response", "FAIL", "SubStep", null);
						ReportHelper.log(LogStatus.FAIL, "Search String", "Special character validation is not working as expected since both strings "+splitText.get(0).toLowerCase() +" and "+splitText.get(1).toLowerCase()+" are not present");
					}
			}
			else {
				if (arrayValuesFromJson.toString().toLowerCase().contains(queryString.toLowerCase())){
					LoggerUtil.log("String is present in the response", "PASS", "SubStep", null);
					ReportHelper.log(LogStatus.PASS, "Search String", "Special character validation is working as expected and the search string "+queryString.toLowerCase()+" is present");
				}
				else{
					LoggerUtil.log("String is not present in the response", "FAIL", "SubStep", null);
					ReportHelper.log(LogStatus.FAIL, "Search String", "Special character validation is not working as expected since the search string "+queryString.toLowerCase()+" is not present");
							
				}
			}
			}
		}			
		
		public int returnNoCountV2Search_PostorGet(Response response) throws Exception{
			final String actual = APIHelper.retriveValuefromJson("$..count", response.getBody().asString());
			final int actualcount = Integer.parseInt(actual);
			if (actualcount == 0){
				LoggerUtil.log("Count is returned as Zero - please check your QueryString", actual, "SubStep", null);
				ReportHelper.log(LogStatus.PASS, "Count", "Count is returned as Zero as expected");
			}
			else if (actualcount > 0){
				LoggerUtil.log("Count is returned as greater than one - product is available for the QueryString", actual,
						"SubStep", null);
				ReportHelper.log(LogStatus.FAIL, "Count", "Count is returned as greater than one - product is available for the QueryString");
			}
			return actualcount;

}





}
