package com.pearson.regression.utilityHelpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.response.Response;
import com.relevantcodes.extentreports.LogStatus;

import bsh.ParseException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

public class Template {

	// Convert String to Array
	public static JSONArray convertStringtoJsonArr(String reqList) {
		JSONArray jsonarr = new JSONArray();
		try {
			String[] reqSet = reqList.split(Constants.commaChar);
			for (int i = 0; i < reqSet.length; i++) {
				jsonarr.add(reqSet[i].trim());
			}
		} catch (Exception e) {
			ExceptionHandler.logException(e);
		}
		return jsonarr;
	}

	// Convert response body to JsonOnject
	public static JSONObject convertStringtoJsonObject(String responseBody) throws ParseException {
		JSONObject jsonObject = new JSONObject();
		if (!responseBody.equals(Constants.emptyString)) {
			@SuppressWarnings("deprecation")
			JSONParser jsonParser = new JSONParser();
			Object object = null;
			try {
				object = jsonParser.parse(responseBody);
			} catch (net.minidev.json.parser.ParseException e) {
				ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
				ExceptionHandler.logException(e);
			}
			jsonObject = (JSONObject) object;
		}
		return jsonObject;
	}

	//Convert String to JSON array
	public static JSONArray convertStringtoJsonarray(String responseBody) throws ParseException {
		responseBody = removeFirstAndLastChar(responseBody);
		JSONArray array = new JSONArray();
		if (!responseBody.contains(Constants.emptyString)) {
			@SuppressWarnings("deprecation")
			JSONParser jsonParser = new JSONParser();
			Object object = null;
			try {
				object = jsonParser.parse(responseBody);
			} catch (net.minidev.json.parser.ParseException e) {
				ExceptionHandler.logException(e);
			}
			array.add(object);
		}
		return array;
	}

	public static String removeFirstAndLastChar(String s) {
		return s.substring(1, s.length() - 1);
	}

	//Compare two string values
	public static boolean compareStringValues(String actValue, String expValue) {
		boolean bSuccess = false;
		if (actValue != null && expValue != null) {
			if (actValue.toLowerCase().trim().contains(expValue.toLowerCase().trim())) {
				bSuccess = true;
			}
			if (bSuccess == false) {
				if (expValue.toLowerCase().trim().contains(actValue.toLowerCase().trim())) {
					bSuccess = true;
				} else {
					bSuccess = false;
				}
			}
		} else {
			bSuccess = false;
		}

		return bSuccess;
	}

	// Get all Key names from Json response
	public static Set<String> getSetofKeysinJSONResponse(JSONObject jsonObject) throws ParseException {
		Set<String> jsonset = jsonObject.keySet();
		return jsonset;
	}

	// Get all Key names count from Json response
	public static int getSetofKeyscountinJSONResponse(JSONObject object) throws ParseException {
		Set<String> jsonset = object.keySet();
		return jsonset.size();
	}

	//Get input header/query string information 
	public static Map<String, String> getRequestData(LinkedHashMap<String, String> data, String filter) {
		Map<String, String> requests = new HashMap<String, String>();

		for (String key : data.keySet()) {
			if (key.contains(Constants.underScore)) {
				String[] setList = key.split(Constants.underScore);
				if (setList[1].toLowerCase().contains(filter)) {
					String value = data.get(key);
					if (!value.trim().equals(Constants.emptyString)) {
						String[] valueList = value.split(Constants.doubleColon);
						if (valueList.length == 1) {
							requests.put(valueList[0].trim(), Constants.emptyString);
						} else {
							requests.put(valueList[0].trim(), valueList[1]);
						}
					}
				}
			}
		}
		return requests;
	}

	//Sorting
	public static boolean sortString(List<String> actualSortingList, List<String> expSortingList, String asc) {
		boolean bSuccess = false;
		String expVal;
		String actVal;
		try {
			Collections.sort(expSortingList);
			String ascending = Constants.asc;
			if (!ascending.toLowerCase().contains(asc.toLowerCase().trim())) {
				Collections.reverse(expSortingList);
			}
			System.out.println("Expected Value                    ActualValue");
			if (expSortingList.size() != 0) {
				for (int i = 0; i < expSortingList.size(); i++) {
					expVal = expSortingList.get(i).toString();
					actVal = actualSortingList.get(i).toString();
					if (expVal.equals(actVal)) {
						System.out.println(expSortingList.get(i) + "  " + actualSortingList.get(i));
						bSuccess = true;
					} else {
						bSuccess = false;
						i = expSortingList.size();
					}
				}
			} else {
				if (expSortingList.equals(actualSortingList))
					bSuccess = true;
			}

		} catch (Exception e) {
			return bSuccess;
		}
		return bSuccess;
	}

	public static String getSingleValue(LinkedHashMap<String, String> data_Search, String fieldName) {
		fieldName = data_Search.get(fieldName.trim());
		String array = fieldName.split("::")[1];
		return array;
	}

	// V2Search Region Start
	// Get Search data from queryString in Get Method (V2Search)
	public static String[] getFieldNamesFromKey(LinkedHashMap<String, String> data, String fieldName) {
		fieldName = data.get(fieldName.trim());
		fieldName = fieldName.split(Constants.doubleColon)[1];
		String[] fieldNameList = fieldName.split(Constants.commaChar);
		return fieldNameList;
	}

	public static String convertStringWithEmtag(String qsString) {
		return Constants.emprefixtag + qsString.toLowerCase().trim() + Constants.emsuffixtag;
	}

	public static String getSearchQueryString(LinkedHashMap<String, String> data, String fieldName) {
		fieldName = data.get(fieldName.trim());
		fieldName = fieldName.split(Constants.doubleColon)[1];
		return fieldName;
	}

	public static String getFieldValfromJson(JSONObject json, String fieldName) {
		String value = json.get(fieldName).toString();
		return value;
	}

	public static String[] getStringArrayValfromJson(JSONObject json, String fieldName)
			throws net.minidev.json.parser.ParseException {
		JSONArray jsonArray = (JSONArray) json.get(fieldName);
		int len = jsonArray.size();
		String[] jsonStringarr = new String[len];
		for (int i = 0; i < len; i++) {
			jsonStringarr[i] = jsonArray.get(i).toString();
		}
		return jsonStringarr;
	}

	public static Set<String> getQueryStringSets(String qsString, boolean bExactPharse) {
		qsString = qsString.replace("-", " ");
		if (Wildcard.checkExactPharse(qsString)) {
			qsString = qsString.replace(Constants.doublequote, Constants.emptyString);
			qsString = qsString.replace(Constants.excatPharse, Constants.emptyString);
		}

		Set<String> expValue = new HashSet<String>();
		if (qsString.trim().contains(Constants.logicalNOT)) {
			qsString = qsString.replace(Constants.logicalNOT + Constants.spaceChar, Constants.logicalNOT);
			String[] qsList = qsString.split(Constants.spaceChar);
			if (bExactPharse) {
				for (String qs : qsList) {
					expValue.add(qs.trim().toLowerCase());
				}

			} else {
				for (String qs : qsList) {
					if (!logicalValidation(qs)) {
						expValue.add(qs.trim().toLowerCase());
					}
				}
			}
		} else {
			String[] qsList = qsString.split(Constants.spaceChar);
			if (bExactPharse) {
				for (String qs : qsList) {
					expValue.add(qs.trim().toLowerCase());
				}
			} else {
				for (String qs : qsList) {
					if (!logicalValidation(qs)) {
						expValue.add(qs.trim().toLowerCase());
					}
				}
			}
		}
		return expValue;
	}

	public static boolean logicalValidation(String qsString) {
		return (((qsString.trim().equals(Constants.logicalAND)) || (qsString.trim().equals(Constants.logicalOR))));
	}

	public static List<String> retriveValueFromJson(String jsonpath, String responseContent) {
		List<String> valuefromJson = new ArrayList<String>();
		JsonPath json = JsonPath.compile(jsonpath);
		valuefromJson = json.read(responseContent);
		return valuefromJson;
	}

	public static JSONArray retrivejsonArrayFromJson(String jsonpath, String responseContent) {
		JsonPath json = JsonPath.compile(jsonpath);
		JSONArray value = json.read(responseContent);
		return value;
	}

	public static String retriveStringValueFromJson(String jsonpath, String responseContent) {
		JsonPath json = JsonPath.compile(jsonpath);
		String value = json.read(responseContent);
		return value;
	}

	//Using JSON path retrive JSON Object from JSON
	public static JSONObject retrivejsonObjectFromJson(String jsonpath, String responseContent) {
		JsonPath json = JsonPath.compile(jsonpath);
		JSONObject value = json.read(responseContent);
		return value;
	}

	//Get specific field name from queryString
	public static Map<String, String> getSingleSpecficFieldNames(String fieldName) {
		System.out.println("filename:" + fieldName);
		String[] fields = null;
		String fieldVal = Constants.emptyString;
		Map<String, String> specificFieldValues = new HashMap<String, String>();

		if (fieldName.contains(Constants.singleColon)) {
			fields = fieldName.split(Constants.singleColon);
		} // else {fieldName = Constants.emptyString;}
		try {
			fieldVal = removeopencloseBrace(fields[1]);
			specificFieldValues.put(fields[0], fieldVal);
		} catch (NullPointerException n) {
			System.out.println(n.getMessage());
		}
		return specificFieldValues;
	}

	
	//Get specific field name from exact pharse query string
	public static Map<String, String> getSingleSpecficFieldNames_ExactPharse(String fieldName) {
		String[] fields = null;
		String fieldVal = Constants.emptyString;
		Map<String, String> specificFieldValues = new HashMap<String, String>();

		if (fieldName.contains(Constants.singleColon)) {
			fields = fieldName.split(Constants.singleColon);
		} // else {fieldName = Constants.emptyString;}
		try {
			fieldVal = removeopencloseBrace(fields[1]);
			if (fieldVal.contains(Constants.doublequote))
				fieldVal = fieldVal.replaceAll(Constants.doublequote, Constants.emptyString);
			specificFieldValues.put(fields[0], fieldVal);
		} catch (NullPointerException n) {
			System.out.println(n.getMessage());
		}
		return specificFieldValues;
	}

	//Get specific field name
	public static String getSpecficFieldName(LinkedHashMap<String, String> data, String fieldName) {
		fieldName = data.get(fieldName.trim());
		fieldName = fieldName.split(Constants.doubleColon)[1];
		if (fieldName.contains(Constants.singleColon)) {
			fieldName = fieldName.split(Constants.singleColon)[0];
		} else {
			fieldName = Constants.emptyString;
		}
		fieldName = removeopencloseBrace(fieldName);
		return fieldName;
	}

	//Get multiple specific field name from query string
	public static Map<String, String> getMultipleSpecficFieldNames(String fieldName) {
		String[] fields = null;
		Map<String, String> specificFieldValues = new HashMap<String, String>();
		if (fieldName.contains(Constants.singleColon)) {
			fields = fieldName.split(Constants.spaceChar);
		} // else {fieldName = Constants.emptyString;}
		try {
			for (String field : fields) {
				if (field.contains(Constants.singleColon)) {
					String[] specificVal = field.split(Constants.singleColon);
					specificVal[1] = removeopencloseBrace(specificVal[1]);
					specificFieldValues.put(specificVal[0].trim(), specificVal[1].trim());
				}
			}
		} catch (NullPointerException n) {
			System.out.println(n.getMessage());
		}
		return specificFieldValues;
	}
	
	//Get Parent query field name
	public static Map<String, String> getParentQueryFieldName(String fieldName) {
		String[] fields = null;
		Map<String, String> specificFieldValues = new HashMap<String, String>();
		if (fieldName.contains(Constants.singleColon)) {
			fields = fieldName.split(Constants.spaceChar);
		} // else {fieldName = Constants.emptyString;}
		try {
			for (String field : fields) {
				if (!field.contains("childQuery:")) {
					if (field.contains(Constants.singleColon)) {
						String[] specificVal = field.split(Constants.singleColon);
						specificVal[1] = removeopencloseBrace(specificVal[1]);
						specificFieldValues.put(specificVal[0].trim(), specificVal[1].trim());
					}
				}
			}
		} catch (NullPointerException n) {
			System.out.println(n.getMessage());
		}
		return specificFieldValues;
	}

	
	//get parent query field name from logical query string value
	public static Map<String, String> getParentQueryFieldName_Logic(String fieldName ,String logicName) {
		String[] fields = null;
		Map<String, String> specificFieldValues = new HashMap<String, String>();
		if (fieldName.contains(Constants.singleColon)) {
			fields = fieldName.split(logicName);
		} // else {fieldName = Constants.emptyString;}
		try {
			for (String field : fields) {
				if (!field.contains("childQuery:")) {
					if (field.contains(Constants.singleColon)) {
						String[] specificVal = field.split(Constants.singleColon);
						specificVal[1] = removeopencloseBrace(specificVal[1]);
						specificFieldValues.put(specificVal[0].trim(), specificVal[1].trim());
					}
				}
			}
		} catch (NullPointerException n) {
			System.out.println(n.getMessage());
		}
		return specificFieldValues;
	}

	//get child query field name from search query string
	public static Map<String, String> getchidQueryFieldName(String fieldName) {
		String[] fields = null;
		Map<String, String> specificFieldValues = new HashMap<String, String>();
		if (fieldName.contains(Constants.singleColon)) {
			fields = fieldName.split(Constants.spaceChar);
		} // else {fieldName = Constants.emptyString;}
		try {
			for (String field : fields) {
				if (field.contains("childQuery:")) {
					field = field.replace("childQuery:", "");

					if (field.contains(Constants.singleColon)) {
						String[] specificVal = field.split(Constants.singleColon);
						specificVal[1] = removeopencloseBrace(specificVal[1]);
						specificFieldValues.put(specificVal[0].trim(), specificVal[1].trim());
					}
				}
			}
		} catch (NullPointerException n) {
			System.out.println(n.getMessage());
		}
		return specificFieldValues;
	}

	//get elastic search response node values based on the product Id
	public static Map<String, Map<String, String>> getElasticSearchResponse_NodeValues(
			Map<String, Map<String, String>> actualResponse, Map<String, Map<String, String>> expResponse)
			throws Exception {
		Map<String, Map<String, String>> expectedSearchResponse = new HashMap<String, Map<String, String>>();
		Set<String> actualresponse_ProdId = actualResponse.keySet();
		for (String actProdId : actualresponse_ProdId) {
			for (Map.Entry<String, Map<String, String>> expResponseEntry : expResponse.entrySet()) {
				String esProductId = expResponseEntry.getKey().toString();
				if (actProdId.contains(esProductId)) {
					expectedSearchResponse.put(esProductId, expResponseEntry.getValue());
					break;
				}
			}
		}
		return expectedSearchResponse;
	}

	//get data store search response node values based on the product Id
	@SuppressWarnings("unchecked")
	public static Map<String, Map<String, String>> getDataStoreResponseNodeValues(String[] expFieldname,
			Response dataStoreResponse, String productlistJsonPath, Map<String, Map<String, String>> actualResponse,
			String chkFieldName) throws Exception {
		String primExpFieldName = Constants.emptyString;
		Map<String, Map<String, String>> finalResList = new HashMap<String, Map<String, String>>();
		String secExpFieldName = Constants.emptyString;
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> mapList = new HashMap<String, String>();
		String[] ExactFieldToreturnArray = new String[30];
		Set<String> actualresponse_ProdId = actualResponse.keySet();
		List<String> exactFieldNames = APIHelper.getExpectedField_ExactMathches(expFieldname);
		JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(productlistJsonPath,
				dataStoreResponse.asString());
		int count = 0;
		for (String prodId : actualresponse_ProdId) {
			for (Object jsonObject : arrayValuesFromJson) {
				@SuppressWarnings("unchecked")
				Map<String, Object> jsonItems = (Map<String, Object>) jsonObject;
				for (Map.Entry<String, Object> sourceArrayEntry : jsonItems.entrySet()) {
					String sourceKey = sourceArrayEntry.getKey().toString();
					if (sourceKey.equals("productId")) {
						String chkFieldVal = sourceArrayEntry.getValue().toString();
						if (prodId.contains(chkFieldVal)) {
							mapList.put(chkFieldName, chkFieldVal);
							for (String efield : exactFieldNames) {
								{
									String datStorePrimExactFieldName = Constants.emptyString;
									String datStoreSecExactFieldName = Constants.emptyString;
									if (efield.contains(".") == true) {
										ExactFieldToreturnArray = efield.split("\\.");
										primExpFieldName = ExactFieldToreturnArray[0];
										datStorePrimExactFieldName = getsuffixname(primExpFieldName);
										secExpFieldName = ExactFieldToreturnArray[1];
										datStoreSecExactFieldName = getsuffixname(secExpFieldName);
									} else {
										primExpFieldName = efield;
										datStorePrimExactFieldName = getsuffixname(efield);
									}
									String key = Constants.emptyString;
									String value = Constants.emptyString;

									// Iteration for products array from search
									// results response
									for (Map.Entry<String, Object> sourceArray : jsonItems.entrySet()) {
										// Expected field Iteration for each
										// product
										map = getexpFieldNodeValues(exactFieldNames, sourceArray,
												datStorePrimExactFieldName, datStoreSecExactFieldName, efield);
										for (Entry<String, String> entry : map.entrySet()) {
											key = entry.getKey();
											value = entry.getValue();
										}
										if ((!key.equals("")) && (!value.equals("")))
											mapList.put(key, value);
									}
								}
							}
						}
					}
				}
			}
			// if ((!prodId.equals(""))&&(!mapList.equals("")))
			if (!prodId.equals(""))
				finalResList.put(prodId, mapList);
			count++;
			if (count == 2)
				break;
		}
		return finalResList;
	}

	//get suffix name of exp field name
	public static String getsuffixname(String primExpFieldName) {
		String[] prefix = { "schema:", "cnt:", "bf:" };
		for (String pre : prefix) {
			if (primExpFieldName.contains(pre)) {
				primExpFieldName = primExpFieldName.replace(pre, "");
				break;
			}
		}
		return primExpFieldName;
	}

	//get response node values based on the expexcted fields
	@SuppressWarnings("unchecked")
	public static Map<String, Map<String, String>> getResponseNodeValues(String[] expfieldName, Response res,
			String array_jp, String jsonfieldpath, String productIdField) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Map<String, String>> finalResList = new HashMap<String, Map<String, String>>();
		Map<String, String> mapList = new HashMap<String, String>();
		String primExpFieldName = Constants.emptyString;
		String secExpFieldName = Constants.emptyString;
		String[] ExactFieldToreturnArray = new String[30];
		String productId = Constants.emptyString;
		int count = 0;
		List<String> exactFieldNames = APIHelper.getExpectedField_ExactMathches(expfieldName);
		// Retrieve mandatory fields values
		JSONArray actarrayValuesFromJson = Template.retrivejsonArrayFromJson(array_jp, res.asString());
		for (Object jsoncomObject : actarrayValuesFromJson) {
			Map<String, Object> jsonItems_par = (Map<String, Object>) jsoncomObject;
			productId = jsonItems_par.get(productIdField).toString();
			Object source = jsonItems_par.get(jsonfieldpath);
			Map<String, Object> jsonItems = (Map<String, Object>) source;
			for (Map.Entry<String, Object> sourceArray : jsonItems.entrySet()) {
				String key = Constants.emptyString;
				String value = Constants.emptyString;
				String sourceKey = sourceArray.getKey().toString();
				for (String expfield : exactFieldNames) {
					// Formatting the expected field name taken from
					// TestData.properties
					if (expfield.contains(".") == true) {
						ExactFieldToreturnArray = expfield.split("\\.");
						primExpFieldName = ExactFieldToreturnArray[0];
						secExpFieldName = ExactFieldToreturnArray[1];
					} else {
						primExpFieldName = expfield;
					}
					if (sourceKey.equals(primExpFieldName)) {
						map = getexpFieldNodeValues(exactFieldNames, sourceArray, primExpFieldName, secExpFieldName,
								expfield);
						for (Entry<String, String> entry : map.entrySet()) {
							key = entry.getKey();
							value = entry.getValue();
							break;
						}
						if ((!key.equals("")))
							mapList.put(key, value);
					}
				}
			}
			finalResList.put(productId, mapList);
			count++;
			if (count == 2)
				break;
		}
		return finalResList;
	}

	
	@SuppressWarnings("unchecked")
	private static Map<String, String> getexpFieldNodeValues(List<String> exactFieldNames,
			Map.Entry<String, Object> sourceArrayEntry, String primExpFieldName, String secExpFieldName,
			String expfield) {
		String sourceKey = sourceArrayEntry.getKey().toString();
		Map<String, String> map = new HashMap<String, String>();
		if (sourceKey.equals(primExpFieldName)) {
			if (!(secExpFieldName == Constants.emptyString)) {
				Object intervention = sourceArrayEntry.getValue();
				String expValue;
				Map<String, String> jsontoMap;
				if (intervention instanceof JSONArray) {
					// It's an array
					JSONArray interventionJsonArray = (JSONArray) intervention;
					for (Object objVal : interventionJsonArray) {
						if (objVal instanceof String) {
							expValue = (String) objVal;
						} else {
							jsontoMap = (Map<String, String>) objVal;
							expValue = jsontoMap.get(secExpFieldName);
						}
						map.put(expfield, expValue);
						if (expValue != null)
							break;
					}
				} else if (intervention instanceof JSONObject) {
					map.put(expfield, intervention.toString());

				} else {
					if (intervention instanceof String) {
						expValue = (String) intervention;
					} else {
						jsontoMap = (Map<String, String>) intervention;

						Object in = jsontoMap.get(secExpFieldName);

						expValue = in.toString();
					}
					map.put(expfield, expValue);
				}
			} else {
				map.put(expfield, sourceArrayEntry.getValue().toString());
			}
		}
		return map;
	}

	//Get multiple specific field name exact pharse query string
	public static Map<String, String> getMultipleSpecficFieldNames_Exact(String fieldName) {
		String[] fields = null;
		fieldName = fieldName.replace("\" ", "##");
		Map<String, String> specificFieldValues = new HashMap<String, String>();

		if (fieldName.contains(Constants.singleColon)) {
			fields = fieldName.split("##");
		} // else {fieldName = Constants.emptyString;}
		try {
			for (String field : fields) {
				if (field.contains(Constants.doublequote))
					field = field.replaceAll(Constants.doublequote, Constants.emptyString);
				String[] specificVal = field.split(Constants.singleColon);
				specificVal[1] = removeopencloseBrace(specificVal[1]);
				specificFieldValues.put(specificVal[0].trim(), specificVal[1].trim());
			}
		} catch (NullPointerException n) {
			System.out.println(n.getMessage());
		}
		return specificFieldValues;
	}

	//get multiple specific field name from logical operator query string
	public static Map<String, String> getMultiSpecficFieldNames_Logic(String fieldName, String logicName) {
		String[] fields = null;
		Map<String, String> specificFieldValues = new HashMap<String, String>();
		fields = fieldName.split(logicName);
		for (String field : fields) {
			String[] specificVal = field.split(Constants.singleColon);
			specificVal[1] = removeopencloseBrace(specificVal[1]);
			specificFieldValues.put(specificVal[0].trim(), specificVal[1].trim());
		}
		return specificFieldValues;
	}
	
   //get multiple specific field name from combinational logic operator query string
	public static String[] getMultiSpecficFieldNames_CombinationalLogic(String fieldName, String logicName) {
		String[] fields = null;
		if (fieldName.contains(logicName)) {
			fieldName = fieldName.replace(logicName, "##");
			fields = fieldName.split("##");
		}
		return fields;
	}

	public static String removeopencloseBrace(String value) {
		if (value.contains(Constants.openbrace) || value.contains(Constants.closebrace)) {
			value = value.replace(Constants.openbrace, Constants.emptyString);
			value = value.replace(Constants.closebrace, Constants.emptyString);
		}
		return value;
	}

	public static String getUpdatedVal(String value) {
		if (value.contains(Constants.openbrace) || value.contains(Constants.closebrace)) {
			value = value.replace(Constants.openbrace, Constants.emptyString);
			value = value.replace(Constants.closebrace, Constants.emptyString);
		}
		if (value.contains(Constants.hypen)) {
			value = value.replaceAll(Constants.hypen, Constants.spaceChar);
		}
		if (value.contains(Constants.doublequote)) {
			value = value.replaceAll(Constants.doublequote, Constants.emptyString);
		}
		if (value.contains(Constants.commaChar)) {
			value = value.replaceAll(Constants.commaChar, Constants.spaceChar);
		}
		if (value.contains(Constants.equalChar)) {
			value = value.replaceAll(Constants.equalChar, Constants.spaceChar);
		}
		if (value.contains("[")) {
			value = value.replace("[", Constants.spaceChar);
		}
		if (value.contains("]")) {
			value = value.replace("]", Constants.spaceChar);
		}
		if (value.contains("|")) {
			value = value.replace("|", Constants.spaceChar);
		}
		if (value.contains(":")) {
			value = value.replace(":", Constants.spaceChar);
		}

		return value.toLowerCase().toString();
	}

	public static boolean checkfieldPathType(String fieldPath) {
		boolean bSuccess = false;
		String[] arrayKeyNames = Constants.v2SearchSourcearrayKeyNames;
		for (String arraykeyName : arrayKeyNames) {
			if (fieldPath.contains(arraykeyName)) {
				bSuccess = true;
				break;
			}
		}
		return bSuccess;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Map<String, String>> getResponseNodeValues_exchangeDisp(String[] expfieldName,
			Response res) throws Exception {
		// Map<String, String> map = new HashMap<String, String>();
		Map<String, Map<String, String>> finalResList = new HashMap<String, Map<String, String>>();
		Map<String, String> mapList = new HashMap<String, String>();
		String primExpFieldName = Constants.emptyString;
		String secExpFieldName = Constants.emptyString;
		String[] ExactFieldToreturnArray = new String[30];
		String productId = Constants.emptyString;
		int count = 0;
		JSONArray productId1 = Template.retrivejsonArrayFromJson(".productId", res.asString());
		productId = productId1.get(0).toString();
		JSONArray actarrayValuesFromJson = Template
				.retrivejsonArrayFromJson(".searchResults[0].source.cnt:auxiliaryFields", res.asString());
		for (Object jsoncomObject : actarrayValuesFromJson) {
			Map<String, Object> jsonItems_par = (Map<String, Object>) jsoncomObject;
			for (Map.Entry<String, Object> sourceArray : jsonItems_par.entrySet()) {
				String value = Constants.emptyString;
				String sourceKey = sourceArray.getKey().toString();
				for (String expfield : expfieldName) {
					// Formatting the expected field name taken from
					// TestData.properties
					if (expfield.contains(".") == true) {
						ExactFieldToreturnArray = expfield.split("\\.");
						primExpFieldName = ExactFieldToreturnArray[0];
						secExpFieldName = ExactFieldToreturnArray[1];
					} else {
						primExpFieldName = expfield;
					}
					if (sourceKey.equals(primExpFieldName)) {
						value = sourceArray.getValue().toString();
						if ((!sourceKey.equals("")))
							mapList.put(sourceKey, value);
					}
				}
			}
			finalResList.put(productId, mapList);
			count++;
			if (count == 2)
				break;
		}
		return finalResList;
	}

	public static Map<String, Map<String, String>> getResponseNodeValues_exchangeDisp_dataStoreSearch(
			String[] expfieldName, Response res, Set<String> actResNodeValueKeys) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Map<String, String>> finalResList = new HashMap<String, Map<String, String>>();
		String productId = Constants.emptyString;
		String productIDVal = Constants.emptyString;
		JSONArray actarrayValuesFromJson = Template.retrivejsonArrayFromJson(".disciplineFeatures[*]", res.asString());
		for (String skey : actResNodeValueKeys) {
			for (Object jsoncomObject : actarrayValuesFromJson) {
				@SuppressWarnings("unchecked")
				Map<String, Object> jsonItems = (Map<String, Object>) jsoncomObject;
				productId = jsonItems.get("disciplineId").toString();
				if (skey.equals(productId)) {
					productIDVal = productId;
					for (Map.Entry<String, Object> sourceArray : jsonItems.entrySet()) {
						String sourceKey = sourceArray.getKey().toString();
						for (String expfield : expfieldName) {
							if (sourceKey.equals(expfield)) {
								map.put(sourceKey, sourceArray.getValue().toString());
								break;
							}
						}
					}
				}
				if (map != null)
					finalResList.put(productIDVal, map);
			}
		}
		return finalResList;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Map<String, String>> getResponseNodeValues_exchangeDisp_elasticSearch(
			String[] expfieldName, Response res) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Map<String, String>> finalResList = new HashMap<String, Map<String, String>>();
		Map<String, String> mapList = new HashMap<String, String>();
		String[] ExactFieldToreturnArray = new String[30];
		String productId = Constants.emptyString;
		int count = 0;
		String[] eFieldNames = APIHelper.getExpectedFieldsWithAppId(expfieldName, "ExchDisp_elas.");
		List<String> exactFieldNames = APIHelper.getExpectedField_ExactMathches_exDisp(eFieldNames);
		JSONArray productId1 = Template.retrivejsonArrayFromJson(".hits.hits[0]._id", res.asString());
		productId = productId1.get(0).toString();
		JSONArray actarrayValuesFromJson = Template.retrivejsonArrayFromJson(".hits.hits[*]", res.asString());
		for (Object jsoncomObject : actarrayValuesFromJson) {
			Map<String, Object> jsonItems_par = (Map<String, Object>) jsoncomObject;
			Object source = jsonItems_par.get("_source");
			Map<String, Object> jsonItems = (Map<String, Object>) source;
			Set<String> keys1 = jsonItems.keySet();
			for (Map.Entry<String, Object> sourceArray : jsonItems.entrySet()) {
				String key = Constants.emptyString;
				String value = Constants.emptyString;
				String sourceKey = sourceArray.getKey().toString();
				for (String expfield : exactFieldNames) {
					// Formatting the expected field name taken from
					// TestData.properties
					String primExpFieldName = Constants.emptyString;
					String secExpFieldName = Constants.emptyString;
					if (expfield.contains(".") == true) {
						ExactFieldToreturnArray = expfield.split("\\.");
						primExpFieldName = ExactFieldToreturnArray[0];
						secExpFieldName = ExactFieldToreturnArray[1];
					} else {
						primExpFieldName = expfield;
					}
					if (sourceKey.equals(primExpFieldName)) {
						map = getexpFieldNodeValues(exactFieldNames, sourceArray, primExpFieldName, secExpFieldName,
								expfield);
						for (Entry<String, String> entry : map.entrySet()) {
							primExpFieldName = getsuffixname(primExpFieldName);
							secExpFieldName = getsuffixname(secExpFieldName);
							value = entry.getValue().toString();
							break;
						}
						if ((secExpFieldName.equals(""))) {
							mapList.put(primExpFieldName, value);
						} else {
							mapList.put(secExpFieldName, value);
						}
					}

				}
			}
			finalResList.put(productId, mapList);
			count++;
			if (count == 2)
				break;
		}
		return finalResList;
	}
	
	public static String getParametersRemovingSpecialCharacters(Map<String, String> parameter){
		String parameterWithoutSpecialCharacter = "";
		String parameterWithSpecialCharacter = parameter.get("queryString");
		boolean splitIndicator = false;
	    String queryStringOne = "", queryStringTwo = "";
	    int asciiValueFirstChar = (int) parameterWithSpecialCharacter.charAt(0);
	    int asciiValueLastChar = (int) parameterWithSpecialCharacter.charAt(parameterWithSpecialCharacter.length()-1);
	    		if(((asciiValueFirstChar < 65 || asciiValueFirstChar > 90) && (asciiValueFirstChar < 97 || asciiValueFirstChar > 122)) || ((asciiValueLastChar < 65 || asciiValueLastChar > 90) && (asciiValueLastChar < 97 || asciiValueLastChar > 122)))
	    		{
	    			for(char eachChar:parameterWithSpecialCharacter.toCharArray()){
	    				int asciiValue = (int) eachChar;
	    				if((asciiValue >= 65 && asciiValue <= 90) || (asciiValue >= 97 && asciiValue <= 122))
	    					parameterWithoutSpecialCharacter = parameterWithoutSpecialCharacter + eachChar; 
	    			}
	    		}
	    		else{
	    			for (int i=0; i<parameterWithSpecialCharacter.length(); i++)
	    				{    				
	    				int asValue = parameterWithSpecialCharacter.charAt(i);
	    				if((asValue >= 65 && asValue <= 90) || (asValue >= 97 && asValue <= 122))
	    					if(!splitIndicator)
	    						queryStringOne = queryStringOne + parameterWithSpecialCharacter.charAt(i);
	    					else
	    						queryStringTwo = queryStringTwo + parameterWithSpecialCharacter.charAt(i);
	    				else    					
	    					splitIndicator = true;
	    				}
	    			System.out.println("First query string after split is " + queryStringOne);
	    			System.out.println("Second query string after split is " + queryStringTwo);
	    			parameterWithoutSpecialCharacter = queryStringOne +"," + queryStringTwo;
	    		}

	    return parameterWithoutSpecialCharacter;
	}
	
	public static String postParametersRemovingSpecialCharacters(String parameterWithSpecialCharacter){
		String parameterWithoutSpecialCharacter = "";		
	    boolean splitIndicator = false;
	    String queryStringOne = "", queryStringTwo = "";
	    int asciiValueFirstChar = (int) parameterWithSpecialCharacter.charAt(0);
	    int asciiValueLastChar = (int) parameterWithSpecialCharacter.charAt(parameterWithSpecialCharacter.length()-1);
	    		if(((asciiValueFirstChar < 65 || asciiValueFirstChar > 90) && (asciiValueFirstChar < 97 || asciiValueFirstChar > 122)) || ((asciiValueLastChar < 65 || asciiValueLastChar > 90) && (asciiValueLastChar < 97 || asciiValueLastChar > 122)))
	    		{
	    			for(char eachChar:parameterWithSpecialCharacter.toCharArray()){
	    				int asciiValue = (int) eachChar;
	    				if((asciiValue >= 65 && asciiValue <= 90) || (asciiValue >= 97 && asciiValue <= 122))
	    					parameterWithoutSpecialCharacter = parameterWithoutSpecialCharacter + eachChar; 
	    			}
	    		}
	    		else{
	    			for (int i=0; i<parameterWithSpecialCharacter.length(); i++)
	    				{    				
	    				int asValue = parameterWithSpecialCharacter.charAt(i);
	    				if((asValue >= 65 && asValue <= 90) || (asValue >= 97 && asValue <= 122))
	    					if(!splitIndicator)
	    						queryStringOne = queryStringOne + parameterWithSpecialCharacter.charAt(i);
	    					else
	    						queryStringTwo = queryStringTwo + parameterWithSpecialCharacter.charAt(i);
	    				else    					
	    					splitIndicator = true;
	    				}
	    			System.out.println("First query string after split is " + queryStringOne);
	    			System.out.println("Second query string after split is " + queryStringTwo);
	    			parameterWithoutSpecialCharacter = queryStringOne +"," + queryStringTwo;
	    		}
	    return parameterWithoutSpecialCharacter;
	}
	// End
}