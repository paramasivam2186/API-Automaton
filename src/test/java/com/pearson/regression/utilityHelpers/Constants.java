package com.pearson.regression.utilityHelpers;

public class Constants {

	// Region CSG
	public static String additionProperty = "additionalProperties";
	public static String asc = "ascending";
	public static String header = "header";
	public static String qsParam = "qsparam";
	public static String emprefixtag = "<em>";
	public static String emsuffixtag = "</em>";
	public static String testStep = "TestStep";
	public static String subStep = "SubStep";
	public static String contentAPIUrl = PropLoad.getcontentAPiUrl().toString().trim();
	public static String apiBaseUrl = PropLoad.getEnvironmentBaseUrl().toString().trim();
	public static String apiElasticSearchUrl = PropLoad.getelasticSearchUrl().toString().trim();
	public static String apiDataStoreSearchUrl = PropLoad.getdatStoreSearchUrl().toString().trim();

	public static String apiProfileUrl = PropLoad.getprofileUrl().toString().trim();
	public static String notFound = "notfound";
	public static String count = "count";
	public static String invalid = "invalid";
	public static String[] expField_datachk = { "count", "productId" };
	public static String profileUrl = PropLoad.getTestData("V2Search_profileUrl_excel");
	public static String elasticSearchUrl = PropLoad.getTestData("V2Search_elasticSearchUrl_excel");
	public static String dataStoreSearchUrl = PropLoad.getTestData("V2Search_dataStoreSearchUrl_excel");
	public static String expJsonRes = PropLoad.getTestData("API_jsonresponse_Excel");
	public static String reqUrl = PropLoad.getTestData("API_reqURL_Excel");
	public static String expStatusCode = PropLoad.getTestData("API_expStatusCode_Excel");
	public static String expjsonSchemafileName = PropLoad.getTestData("API_expSchemafilename_Excel");
	public static String reqBody = PropLoad.getTestData("V2Search_Api_reqBody_excel");
	public static String urlprofile = PropLoad.getTestData("profile_url_excel");

	// End

	// Region Status Codes
	public static String spaceChar = " ";
	public static String commaChar = ",";
	public static String equalChar = "=";
	public static String emptyString = "";
	public static String underScore = "_";
	public static String doubleColon = "::";
	public static String singleColon = ":";
	public static String logicalAND = "AND";
	public static String logicalOR = "OR";
	public static String logicalNOT = "NOT";
	public static String logicalnot = "not";
	public static String combAND = ") AND (";
	public static String combOR = ") OR (";
	public static String combNOT = ") NOT (";
	public static String notStar = "NOT*";
	public static String emPattern = "<em>([^<]*)</em>";
	public static String excatPharse = "\\";
	public static String doublequote = "\"";
	public static String backslash = "\\";
	public static String forwardslash = "//";
	public static String openbrace = "(";
	public static String hypen = "-";
	public static String closebrace = ")";
	// End

	// V2Search KeyNames
	public static String[] v2SearchSourcearrayKeyNames = { "keywords" };
	// End

	// Region Number Values
	public static int zeroVal = 0;
	// End

}