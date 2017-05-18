package com.pearson.regression.utilityHelpers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.HttpStatus;
import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.relevantcodes.extentreports.LogStatus;

import bsh.ParseException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

public class CSGHelper extends RESTServiceBase {

	private static String userNameKey = PropLoad.getTestData("Auth_UserNameKey");
	private static String userNameVal = PropLoad.getTestData("Auth_UserNameValue");
	private static String passwordKey = PropLoad.getTestData("Auth_PasswordKey");
	private static String passwordVal = PropLoad.getTestData("Auth_PasswordValue");
	private static String auth_APIUrl = PropLoad.getTestData("Auth_APIUrl");

	/**
	 * Common reusable methods generated for CSG search functionality validation methods
	 */

	// Generating authentication token
	public static String generate_PI_AuthToken() throws Exception {
		String auth_token = null;
		try {
			final RESTServiceBase webCredentials_rest = new RESTServiceBase();
			final JSONObject myParentJsonObject = new JSONObject();
			myParentJsonObject.put(userNameKey, userNameVal);
			myParentJsonObject.put(passwordKey, passwordVal);
			final Response response = webCredentials_rest.postCallWithBodyParam(myParentJsonObject, auth_APIUrl);
			response.then().assertThat().statusCode(HttpStatus.SC_CREATED);
			auth_token = response.then().extract().path("data");
			return auth_token;
		} catch (Exception e) {
			ReportHelper.log(LogStatus.INFO, "Error Message", e.getMessage());
		}
		return auth_token;

	}

	// Verify Specific Field name's search data present in json
	public static boolean verifyv2SearchSpecificField_SearchQueryString_PresentinJSON(String responseasString,
			Map<String, String> specificFieldNames, String fieldPath, boolean bexactPharse) throws Exception {
		boolean bSuccess = false;
		final StringBuilder testdata = new StringBuilder();

		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(fieldPath, responseasString);
		int count = arrayValuesFromJson.size();
		if (arrayValuesFromJson.size() == 0) {
			LoggerUtil.log("Count is returned as Zero - please check your QueryString", "PASS", "SubStep", null);
			bSuccess = true;
		} else {
			for (Object jsonObject : arrayValuesFromJson) {
				@SuppressWarnings("unchecked")
				final Map<String, Object> set = (Map<String, Object>) jsonObject;
				for (Map.Entry<String, String> specficFieldNamesentry : specificFieldNames.entrySet()) {
					final String specificFieldNameKey = specficFieldNamesentry.getKey().toString();
					final String expkeyName = APIHelper.getExpectedFieldValue(specificFieldNameKey);
					testdata.append(specficFieldNamesentry.getKey() + Constants.commaChar);
					for (Map.Entry<String, Object> sourceArrayEntry : set.entrySet()) {
						final String sourceKey = sourceArrayEntry.getKey().toString();
						if (sourceKey.equals(expkeyName)) {
							final String searchData = specficFieldNamesentry.getValue().toLowerCase().toString();
							final Object sourceField = sourceArrayEntry.getValue();
							final String sourceFieldVal = sourceField.toString().toLowerCase();
							bSuccess = CSGHelper.verifySearchdataPresentinJSON(searchData, sourceFieldVal,
									responseasString, bexactPharse);
							if (bSuccess == true) {
								break;
							}
						}
					}
					if (bSuccess == true) {
						break;
					}
				}
				if (bSuccess == false) {
					break;
				}
			}
		}
		return bSuccess;
	}

	// Verify Specific Field name's search data present in json
	public static boolean verifyv2SearchSizeCount(String responseasString, String fieldPath, String expcountVal,
			String name) throws Exception {
		boolean bSuccess = false;

		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(fieldPath, responseasString);
		final int count = arrayValuesFromJson.size();
		final String actCount = Integer.toString(count);
		if (count == 0) {
			LoggerUtil.log("Count is returned as Zero - please check your QueryString", "PASS", "SubStep", null);
			bSuccess = true;
		} else if (actCount.equalsIgnoreCase(expcountVal)) {
			bSuccess = true;
		}
		if (bSuccess == true) {
			if (name == "group") {
				if (arrayValuesFromJson.size() == 0) {
					ReportHelper.log(LogStatus.PASS, "verifyv2SearchGroupSizeCount",
							"Count is returned as Zero - please check your QueryString");
				} else {
					ReportHelper.log(LogStatus.PASS, "verifyv2SearchGroupSizeCount", expcountVal);
				}
			} else {
				if (arrayValuesFromJson.size() == 0) {
					ReportHelper.log(LogStatus.PASS, "verifyv2SearcResponseSizeCount",
							"Count is returned as Zero - please check your QueryString");
				} else {
					ReportHelper.log(LogStatus.PASS, "verifyv2SearcResponseSizeCount", expcountVal);
				}
			}

		} else {
			if ("group".equals(name)) {
				ReportHelper.log(LogStatus.FAIL, "verifyv2SearchGroupSizeCount", expcountVal);
			} else {
				ReportHelper.log(LogStatus.FAIL, "verifyv2SearcResponseSizeCount", expcountVal);
			}
		}

		return bSuccess;

	}

	// Check whether the given data present in string based on the logic
	public static boolean verifySearchdataPresentinJSON(String qsString, String fieldValue, String responseAsString,
			boolean bexactPharse) throws Exception {
		boolean bSuccess = false;
		final boolean isLogicalAND = qsString.contains(Constants.logicalAND);
		final boolean isLogicalNOT = qsString.contains(Constants.logicalNOT);
		boolean logicNot = false;
		final Set<String> expValue = Template.getQueryStringSets(qsString, false);
		fieldValue = Template.getUpdatedVal(fieldValue);
		for (String expval : expValue) {
			expval = Template.getUpdatedVal(expval);
			if (isLogicalNOT) {
				fieldValue = Template.removeopencloseBrace(fieldValue);
				if (Wildcard.match(fieldValue, Constants.notStar)) {
					fieldValue = fieldValue.replaceAll(Constants.logicalNOT, Constants.emptyString);
					logicNot = true;
				}
			}
			bSuccess = verifySearchData_Present(fieldValue, expval);
			if (isLogicalNOT) {
				if (logicNot) {
					if (bSuccess == false) {
						bSuccess = true;
					}
					if (bSuccess == true) {
						break;
					}
				} else {
					if (bSuccess == false) {
						break;
					}
				}
			} else if (isLogicalAND) {
				if (bSuccess == false) {
					break;
				}
			} else if (bexactPharse) {
				if (bSuccess == false) {
					break;
				}
			} else {
				if (bSuccess == true) {
					break;
				}
			}
		}
		return bSuccess;
	}

	//Verify the given querystring present in the json response 
	public static boolean verifyV2SearchQueryStringPresentinJSON(String qsString, String fieldPath,
			String responseAsString, boolean bexactPharse) throws Exception {
		boolean bSuccess = false;
		if (Template.checkfieldPathType(fieldPath)) {
			final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(fieldPath, responseAsString);
			for (Object jsonObject : arrayValuesFromJson) {
				final String val = jsonObject.toString();
				bSuccess = verifySearchdataPresentinJSON(qsString, val, responseAsString, bexactPharse);
				if (bSuccess == false) {
					break;
				}
			}
		} else {
			final List<String> valuesFromJson = Template.retriveValueFromJson(fieldPath, responseAsString);
			for (String val : valuesFromJson) {
				bSuccess = verifySearchdataPresentinJSON(qsString, val, responseAsString, bexactPharse);
				if (bSuccess == false) {
					break;
				}
			}
		}
		return bSuccess;
	}

	//Verify the given query string present in the matched field with <em> tag (JSON response)
	public static boolean verifyv2SearchMatchedField_SearchQueryString_PresentinJSON(String fieldPath,
			String responseAsString, String querySearchString, boolean bExactPharse) throws Exception {
		final boolean isLogicalAND = querySearchString.contains(Constants.logicalAND);
		final boolean isLogicalOR = querySearchString.contains(Constants.logicalOR);
		boolean bSuccess = false;
		final boolean isLogicalNOT = querySearchString.contains(Constants.logicalNOT);
		final Set<String> expValue = Template.getQueryStringSets(querySearchString, bExactPharse);

		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(fieldPath, responseAsString);
		if (arrayValuesFromJson.size() == 0) {
			LoggerUtil.log("Count is returned as Zero - please check your QueryString", "PASS", "SubStep", null);
			bSuccess = true;
		} else {
			if (isLogicalNOT) {
				if (arrayValuesFromJson.size() == 0) {
					bSuccess = true;
					LoggerUtil.log("Count is returned as Zero - please check your QueryString", "PASS", "SubStep",
							null);
				}
			}
			for (Object jsonObject : arrayValuesFromJson) {
				@SuppressWarnings("unchecked")
				final Map<String, String> set = (Map<String, String>) jsonObject;
				final Set<String> fvalues = new HashSet<String>();
				for (String fValue : set.values()) {
					fvalues.addAll(Wildcard.wildCardData(fValue));
					if (isLogicalNOT) {
						for (String expval : expValue) {
							if (expval.contains(Constants.logicalNOT) || expval.contains(Constants.logicalnot)) {
								expval = expval.replaceAll(Constants.logicalNOT, Constants.emptyString);
								bSuccess = Wildcard.emtagmatch(fValue.toLowerCase(), expval);
								if (bSuccess == false) {
									bSuccess = true;
								}
								if (bSuccess == true) {
									break;
								}
							} else {
								bSuccess = Wildcard.emtagmatch(fValue.toLowerCase(), expval);
							}
						}
						if (bSuccess == false) {
							break;
						}
					} else if (bExactPharse) {
						for (String expval : expValue) {
							if (expval.contains(Constants.doublequote)) {
								expval = expval.replace(Constants.doublequote, Constants.emptyString);
							}
							bSuccess = fValue.toLowerCase().contains(expval);
							if (bSuccess == true) {
								break;
							}
						}
					} else {
						System.out.println("Entering in to plain else loop");
						for (String expval : expValue) {
							if (expval.contains(Constants.doublequote)) {
								expval = expval.replace(Constants.doublequote, Constants.emptyString);
							}
							bSuccess = Wildcard.emtagmatch(fValue.toLowerCase(), expval);
							// if (bSuccess == true) {
							// break;
							// }
						}
					}
					System.out.println("bSuccess value outside the plain else loop is " + bSuccess);
					if (bSuccess == false) {
						break;
					}
				}

				if (!bExactPharse) {
					if (isLogicalAND) {
						for (String eval : expValue) {
							if (eval.contains(Constants.doublequote)) {
								eval = eval.replace(Constants.doublequote, Constants.emptyString);
							}
							bSuccess = fvalues.contains(eval.toLowerCase());
							if (bSuccess == false) {
								break;
							}
						}
					} else if (isLogicalOR) {
						for (String eval : expValue) {
							bSuccess = fvalues.contains(eval.toLowerCase());
							if (bSuccess == true) {
								break;
							}
						}
					}
				}
			}
		}
		return bSuccess;
	}

	//Comparing expected value matches with actual value (including wildcard)
	public static boolean verifySearchData_Present(String fValue, String expval) {
		boolean bSuccess = false;
		if (Wildcard.checkWildCard(expval)) {
			bSuccess = Wildcard.match_space(fValue, expval);
		}
		// check Exact Pharse present in json
		else if (Wildcard.checkExactPharse(expval)) {
			expval = expval.replaceAll(Constants.excatPharse, Constants.emptyString);
			bSuccess = fValue.equals(expval);
		}
		// check given text present in json
		else {
			bSuccess = fValue.contains(expval);
		}
		System.out.println("expval--" + expval + "-----------fValue" + fValue);
		return bSuccess;
	}

	// Verify Multiple Field name's with Logical Operator search data present in
	// json
	public static boolean verifyv2SearchMultipleField_SearchQueryString_Logic(String responseasString,
			String searchQueryString, String fieldPath, boolean bexactPharse) throws Exception {
		boolean bSuccess = false;
		final boolean isLogicalAND = searchQueryString.contains(Constants.logicalAND);
		final boolean isLogicalNOT = searchQueryString.contains(Constants.logicalNOT);
		final boolean isLogicalOR = searchQueryString.contains(Constants.logicalOR);
		Map<String, String> specificFieldValues = new HashMap<String, String>();
		boolean logicNot = false;
		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson(fieldPath, responseasString);
		if (arrayValuesFromJson.size() == 0) {
			LoggerUtil.log("Count is returned as Zero - please check your QueryString", "PASS", "SubStep", null);
			bSuccess = true;
		} else {
			for (Object jsonObject : arrayValuesFromJson) {
				@SuppressWarnings("unchecked")
				final Map<String, Object> set = (Map<String, Object>) jsonObject;
				if (isLogicalNOT) {
					searchQueryString = searchQueryString.replace(Constants.logicalNOT + Constants.spaceChar,
							Constants.logicalNOT);
					specificFieldValues = Template.getMultipleSpecficFieldNames(searchQueryString);
				} else if (isLogicalOR) {
					specificFieldValues = Template.getMultiSpecficFieldNames_Logic(searchQueryString,
							Constants.logicalOR);
				} else if (isLogicalAND) {
					specificFieldValues = Template.getMultiSpecficFieldNames_Logic(searchQueryString,
							Constants.logicalAND);
				}
				for (Map.Entry<String, String> specficFieldNamesentry : specificFieldValues.entrySet()) {
					for (Map.Entry<String, Object> sourceArrayEntry : set.entrySet()) {
						final String sourceKey = sourceArrayEntry.getKey().toString();
						String specificFieldNameKey = specficFieldNamesentry.getKey().toString();
						if (specificFieldNameKey.contains(Constants.logicalNOT)
								|| specificFieldNameKey.contains(Constants.logicalnot)) {
							specificFieldNameKey = specificFieldNameKey.replaceAll(Constants.logicalNOT,
									Constants.emptyString);
							logicNot = true;
						}
						final String expkeyName = APIHelper.getExpectedFieldValue(specificFieldNameKey); // CSGHelper.getExpField_JsonSourcePath(specificFieldNameKey);
						if (sourceKey.equals(expkeyName)) {
							final String searchData = specficFieldNamesentry.getValue().toLowerCase().toString();
							final Object sourceField = sourceArrayEntry.getValue();
							final String sourceFieldVal = sourceField.toString().toLowerCase();
							bSuccess = CSGHelper.verifySearchdataPresentinJSON(searchData, sourceFieldVal,
									responseasString, bexactPharse);
							if (isLogicalAND) {
								if (bSuccess == false) {
									break;
								}
							} else if (isLogicalOR) {
								if (bSuccess == true) {
									break;
								}
							} else if (isLogicalNOT) {
								if (logicNot) {
									if (bSuccess == false) {
										bSuccess = true;
									}
									if (bSuccess == true) {
										break;
									}
								} else {
									if (bSuccess == false) {
										break;
									}
								}
							}
						}
					}
					if (isLogicalOR) {
						if (bSuccess == true) {
							break;
						}
					} else {
						if (bSuccess == false) {
							break;
						}
					}
				}
				if (bSuccess == false) {
					break;
				}
			}
		}
		return bSuccess;
	}

	// Verify multiple Specific fields names with Combinational Logic operators
	public static boolean verifyv2SearchMultiFieldSearchWithCombLogic(String responseasString, String querySearchString,
			String fieldPath, boolean bexactPharse) throws Exception {
		boolean bSuccess = false;
		final boolean isLogicalAND = querySearchString.contains(Constants.combAND);
		final boolean isLogicalNOT = querySearchString.contains(Constants.combNOT);
		boolean logicNot = false;
		final boolean isLogicalOR = querySearchString.contains(Constants.combOR);
		String[] multifields = null;
		final JSONObject res = Template.convertStringtoJsonObject(responseasString);
		final int countVal = Integer.parseInt(Template.getFieldValfromJson(res, "count"));
		if (isLogicalNOT) {
			querySearchString = querySearchString.replace(Constants.combNOT, Constants.combNOT + Constants.logicalNOT);
			multifields = Template.getMultiSpecficFieldNames_CombinationalLogic(querySearchString, Constants.combNOT);
		} else if (isLogicalAND) {
			multifields = Template.getMultiSpecficFieldNames_CombinationalLogic(querySearchString, Constants.combAND);
		} else if (isLogicalOR) {
			multifields = Template.getMultiSpecficFieldNames_CombinationalLogic(querySearchString, Constants.combOR);
		}
		try {
			for (String field : multifields) {
				if (field.contains(Constants.logicalNOT) || field.contains(Constants.logicalnot)) {
					if (Wildcard.match(field, Constants.notStar)) {
						field = field.replaceAll(Constants.logicalNOT, Constants.emptyString);
						logicNot = true;
					}
				}
				field = Template.removeopencloseBrace(field);
				bSuccess = verifyv2SearchMultipleField_SearchQueryString_Logic(responseasString, field, fieldPath,
						bexactPharse);
				if (isLogicalAND) {
					if (bSuccess == false) {
						break;
					}
				} else if (isLogicalOR) {
					if (bSuccess == true) {
						break;
					}
				} else if (isLogicalNOT) {
					if (logicNot) {
						if (bSuccess == false) {
							bSuccess = true;
						}
						if (bSuccess == true) {
							break;
						}
					} else {
						if (bSuccess == false) {
							break;
						}
					}
				}
			}
		} catch (NullPointerException n) {
			ReportHelper.log(LogStatus.INFO, "verifyv2SearchMultiFieldSearchWithCombLogic ---> NullPointerException",
					n.getMessage());
		}
		if (bSuccess == true) {
			if (countVal == 0) {
				ReportHelper.log(LogStatus.PASS, "Verifyv2SearchMultiFieldSearchWithCombLogic",
						"Count is returned as Zero - please check your QueryString");
			} else {
				ReportHelper.log(LogStatus.PASS, "Verifyv2SearchMultiFieldSearchWithCombLogic", querySearchString);
			}
		} else {
			ReportHelper.log(LogStatus.FAIL, "Verifyv2SearchMultiFieldSearchWithCombLogic", querySearchString);
		}

		return bSuccess;
	}

	//Get the actual primary and secondary field name from the given actual field name
	public static String getdataStorefieldName(String expFieldName) {
		@SuppressWarnings("unused")
		String datStorePrimExactFieldName = Constants.emptyString;
		String datStoreSecExactFieldName = Constants.emptyString;
		String primExpFieldName = Constants.emptyString;
		String secExpFieldName = Constants.emptyString;
		String dataStoreFieldName = Constants.emptyString;
		if (expFieldName.contains(".") == true) {
			final String[] ExactFieldToreturnArray = expFieldName.split("\\.");
			primExpFieldName = ExactFieldToreturnArray[0];
			datStorePrimExactFieldName = Template.getsuffixname(primExpFieldName);
			secExpFieldName = ExactFieldToreturnArray[1];
			datStoreSecExactFieldName = Template.getsuffixname(secExpFieldName);
			dataStoreFieldName = datStoreSecExactFieldName;
		} else {
			dataStoreFieldName = Template.getsuffixname(expFieldName);
		}
		return dataStoreFieldName;
	}

	//Get Product information based on the product ID
	public static Map<String, String> getResonseNodevalue_ProdId_FromMap(String productId,
			Map<String, Map<String, String>> resNodeValue) {
		Map<String, String> respnseValue = new HashMap<String, String>();
		for (Map.Entry<String, Map<String, String>> responseEntry : resNodeValue.entrySet()) {
			final String resProductId = responseEntry.getKey().toString();
			if (productId.contains(resProductId)) {
				respnseValue = responseEntry.getValue();
			}
		}
		return respnseValue;
	}

	//Comparing data store values between elastic search ,profiler & v2Search response's
	public static void validateDataStoreValues(Map<String, Map<String, String>> actResNodeValue,
			Map<String, Map<String, String>> esResNodeValue, Map<String, Map<String, String>> dataStoreResNodeValue,
			String[] expFieldnames, String actResCount, String esResCount) throws Exception {
		final boolean bCountresult = Template.compareStringValues(actResCount, esResCount);

		// Count Result
		if (bCountresult == true) {
			ReportHelper.log(LogStatus.PASS, "DataIntegrityCheck",
					"V2Search actual Response count & Elastic search Response count is equal::<br>Actual response Count:<br>"
							+ Constants.doublequote + actResCount + Constants.doublequote + "<br>"
							+ "Elastic Search response Count:<br>" + Constants.doublequote + esResCount
							+ Constants.doublequote);
		} else {
			ReportHelper.log(LogStatus.PASS, "DataIntegrityCheck",
					"V2Search actual Response count & Elastic search Response count is not equal::<br>Actual response Count:<br>"
							+ Constants.doublequote + actResCount + Constants.doublequote + "<br>"
							+ "Elastic Search response Count:<br>" + Constants.doublequote + esResCount
							+ Constants.doublequote);
		}

		// Comparison between V2Search response ,Elastic search Response &
		// DataStore (profile) response
		final List<String> exactFieldNames = APIHelper.getExpectedField_ExactMathches(expFieldnames);
		Map<String, String> actualResponseValue = new HashMap<String, String>();
		Map<String, String> elasticSearchResponseValue = new HashMap<String, String>();
		Map<String, String> dataStoreResponseValue = new HashMap<String, String>();

		final Set<String> actualresponse_ProdId = actResNodeValue.keySet();
		for (String actProdId : actualresponse_ProdId) {
			actualResponseValue = getResonseNodevalue_ProdId_FromMap(actProdId, actResNodeValue);
			elasticSearchResponseValue = getResonseNodevalue_ProdId_FromMap(actProdId, esResNodeValue);
			dataStoreResponseValue = getResonseNodevalue_ProdId_FromMap(actProdId, dataStoreResNodeValue);
			for (String expFieldName : exactFieldNames) {
				final Boolean bElasticSearchSuccess = Template.compareStringValues(
						actualResponseValue.get(expFieldName), elasticSearchResponseValue.get(expFieldName));
				final Boolean bDataStoreSearchSuccess = Template.compareStringValues(
						dataStoreResponseValue.get(expFieldName), actualResponseValue.get(expFieldName));
				final String dataStoreFieldName = getdataStorefieldName(expFieldName);
				if (bElasticSearchSuccess == true && bDataStoreSearchSuccess == true) {
					ReportHelper.log(LogStatus.PASS, "DataIntegrityCheck",
							"Matches found Successfully::<br>Actual response:<br>" + expFieldName + ":"
									+ Constants.doublequote + actualResponseValue.get(expFieldName)
									+ Constants.doublequote + "<br>" + "Elastic Search Response:<br>" + expFieldName
									+ ":" + Constants.doublequote + elasticSearchResponseValue.get(expFieldName)
									+ Constants.doublequote + "<br>" + "Data Store Response:<br>" + dataStoreFieldName
									+ ":" + Constants.doublequote + dataStoreResponseValue.get(expFieldName)
									+ Constants.doublequote);

				} else {
					ReportHelper.log(LogStatus.FAIL, "DataIntegrityCheck",
							"Invalid Matches::<br>Actual response:<br>" + expFieldName + ":" + Constants.doublequote
									+ actualResponseValue.get(expFieldName) + Constants.doublequote + "<br>"
									+ "Elastic Search Response:<br>" + expFieldName + ":" + Constants.doublequote
									+ elasticSearchResponseValue.get(expFieldName) + Constants.doublequote + "<br>"
									+ "Data Store Response:<br>" + dataStoreFieldName + ":" + Constants.doublequote
									+ dataStoreResponseValue.get(expFieldName) + Constants.doublequote);
				}
			}
		}
	}

	public static Map<String, String> getContentValues(Map<String, String> res)
			throws ParseException, bsh.ParseException, net.minidev.json.parser.ParseException {
		final Map<String, String> arrKeyVal = new HashMap<String, String>();
		final JSONArray jArray = (JSONArray) JSONValue.parseWithException(res.get("contents"));
		for (int i = 0; i < jArray.size(); i++) {
			final String arrContent = jArray.get(i).toString();
			final JSONObject json = Template.convertStringtoJsonObject(arrContent);
			final Map<String, Object> jsonItems = (Map<String, Object>) json;
			for (Map.Entry<String, Object> sourceArray : jsonItems.entrySet()) {
				final String sourceKey = sourceArray.getKey().toString();
				final String sourceVal = sourceArray.getValue().toString();
				final Set<String> keysSet = arrKeyVal.keySet();
				if (keysSet.contains(sourceKey)) {
					final String val = arrKeyVal.get(sourceKey).toString() + "," + sourceVal;
					arrKeyVal.put(sourceKey, val);
				} else {
					arrKeyVal.put(sourceKey, sourceVal);
				}
			}
		}
		return arrKeyVal;
	}
	
	//Comparing data store values between elastic search ,profiler & v2Search response's for exchange discipiline ID's	
	public static void validateDataStoreValues_exchangeDisp(Map<String, Map<String, String>> actResNodeValue,
			Map<String, Map<String, String>> esResNodeValue, Map<String, Map<String, String>> dataStoreResNodeValue,
			String[] expFieldnames, String actResCount, String esResCount) throws Exception {
		final boolean bCountresult = Template.compareStringValues(actResCount, esResCount);

		// Count Result
		if (bCountresult == true) {
			ReportHelper.log(LogStatus.PASS, "DataIntegrityCheck",
					"V2Search actual Response count & Elastic search Response count is equal::<br>Actual response Count:<br>"
							+ Constants.doublequote + actResCount + Constants.doublequote + "<br>"
							+ "Elastic Search response Count:<br>" + Constants.doublequote + esResCount
							+ Constants.doublequote);
		} else {
			ReportHelper.log(LogStatus.PASS, "DataIntegrityCheck",
					"V2Search actual Response count & Elastic search Response count is not equal::<br>Actual response Count:<br>"
							+ Constants.doublequote + actResCount + Constants.doublequote + "<br>"
							+ "Elastic Search response Count:<br>" + Constants.doublequote + esResCount
							+ Constants.doublequote);
		}

		// Comparison between V2Search response ,Elastic search Response &
		// DataStore (profile) response
		// List<String> exactFieldNames =
		// APIHelper.getExpectedField_ExactMathches(expFieldnames);
		Map<String, String> actualResponseValue = new HashMap<String, String>();
		Map<String, String> elasticSearchResponseValue = new HashMap<String, String>();
		Map<String, String> dataStoreResponseValue = new HashMap<String, String>();

		@SuppressWarnings("unchecked")
		final Set<String> actualresponse_ProdId = actResNodeValue.keySet();
		for (String actProdId : actualresponse_ProdId) {
			final Map<String, String> arrKeyVal = new HashMap<String, String>();
			actualResponseValue = getResonseNodevalue_ProdId_FromMap(actProdId, actResNodeValue);
			elasticSearchResponseValue = getResonseNodevalue_ProdId_FromMap(actProdId, esResNodeValue);
			dataStoreResponseValue = getResonseNodevalue_ProdId_FromMap(actProdId, dataStoreResNodeValue);
			Boolean bElasticSearchSuccess = false;
			Boolean bDataStoreSearchSuccess = false;
			for (String expFieldName : expFieldnames) {
				if (expFieldName.contains("contents")) {
					final Map<String, String> arrKeyVal_act = getContentValues(actualResponseValue);
					final Map<String, String> arrKeyVal_es = getContentValues(elasticSearchResponseValue);
					final Map<String, String> arrKeyVal_ds = getContentValues(dataStoreResponseValue);
					for (Map.Entry<String, String> sourceArr : arrKeyVal_act.entrySet()) {
						final String sourceKey_act = sourceArr.getKey().toString();
						final String sourceVal_act = sourceArr.getValue().toString();
						final String sourceVal_es = arrKeyVal_es.get(sourceKey_act);
						final String sourceVal_ds = arrKeyVal_ds.get(sourceKey_act);
						bElasticSearchSuccess = Template.compareStringValues(sourceVal_act, sourceVal_es);
						bDataStoreSearchSuccess = Template.compareStringValues(sourceVal_act, sourceVal_ds);
						if (bElasticSearchSuccess == false || bDataStoreSearchSuccess == false) {
							break;
						}
					}
				} else {
					bElasticSearchSuccess = Template.compareStringValues(actualResponseValue.get(expFieldName),
							elasticSearchResponseValue.get(expFieldName));
					bDataStoreSearchSuccess = Template.compareStringValues(dataStoreResponseValue.get(expFieldName),
							actualResponseValue.get(expFieldName));
				}
				final String dataStoreFieldName = getdataStorefieldName(expFieldName);

				if (bElasticSearchSuccess == true && bDataStoreSearchSuccess == true) {
					ReportHelper.log(LogStatus.PASS, "DataIntegrityCheck",
							"Matches found Successfully::<br>Actual response:<br>" + expFieldName + ":"
									+ Constants.doublequote + actualResponseValue.get(expFieldName)
									+ Constants.doublequote + "<br>" + "Elastic Search Response:<br>" + expFieldName
									+ ":" + Constants.doublequote + elasticSearchResponseValue.get(expFieldName)
									+ Constants.doublequote + "<br>" + "Data Store Response:<br>" + dataStoreFieldName
									+ ":" + Constants.doublequote + dataStoreResponseValue.get(expFieldName)
									+ Constants.doublequote);

				} else {
					ReportHelper.log(LogStatus.FAIL, "DataIntegrityCheck",
							"Invalid Matches::<br>Actual response:<br>" + expFieldName + ":" + Constants.doublequote
									+ actualResponseValue.get(expFieldName) + Constants.doublequote + "<br>"
									+ "Elastic Search Response:<br>" + expFieldName + ":" + Constants.doublequote
									+ elasticSearchResponseValue.get(expFieldName) + Constants.doublequote + "<br>"
									+ "Data Store Response:<br>" + dataStoreFieldName + ":" + Constants.doublequote
									+ dataStoreResponseValue.get(expFieldName) + Constants.doublequote);
				}
			}
		}
	}

	//Validating the child query's specific field search query string present in json response
	public static boolean verifyv2SearchchildquerySpecificField_SearchQueryString_PresentinJSON(String responseasString,
			Map<String, String> specificFieldNames, boolean bexactPharse) throws Exception {
		boolean bSuccess = false;
		final StringBuilder testdata = new StringBuilder();

		final JSONArray arrayValuesFromJson = Template.retrivejsonArrayFromJson("", responseasString);
		if (arrayValuesFromJson.size() == 0) {
			LoggerUtil.log("Count is returned as Zero - please check your QueryString", "PASS", "SubStep", null);
			bSuccess = true;
		} else {
			for (Object jsonObject : arrayValuesFromJson) {
				@SuppressWarnings("unchecked")
				final Map<String, Object> set = (Map<String, Object>) jsonObject;
				for (Map.Entry<String, String> specficFieldNamesentry : specificFieldNames.entrySet()) {
					final String specificFieldNameKey = specficFieldNamesentry.getKey().toString();
					final String expkeyName = APIHelper.getExpectedFieldValue(specificFieldNameKey);
					testdata.append(specficFieldNamesentry.getKey() + Constants.commaChar);
					for (Map.Entry<String, Object> sourceArrayEntry : set.entrySet()) {
						final String sourceKey = sourceArrayEntry.getKey().toString();
						if (sourceKey.equals(expkeyName)) {
							final String searchData = specficFieldNamesentry.getValue().toLowerCase().toString();
							final Object sourceField = sourceArrayEntry.getValue();
							final String sourceFieldVal = sourceField.toString().toLowerCase();
							bSuccess = CSGHelper.verifySearchdataPresentinJSON(searchData, sourceFieldVal,
									responseasString, bexactPharse);
							if (bSuccess == true) {
								break;
							}
						}
					}
					if (bSuccess == true) {
						break;
					}
				}
				if (bSuccess == false) {
					break;
				}
			}
		}
		return bSuccess;
	}
}