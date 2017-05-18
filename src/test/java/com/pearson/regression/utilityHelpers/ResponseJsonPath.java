package com.pearson.regression.utilityHelpers;

public class ResponseJsonPath {

	// v2SearchSingle Facet Region Start
	public static String v2SearchMatchedName_JP = ".searchResults[*].matchedFields";
	public static String v2SearchSingleFacetMatchedName_JP = ".searchResults[*].productsList[*].matchedFields";	
	public static String v2SearchSingleFacet_Source_JP=".searchResults[*].productsList[*].source";
	public static String v2SearchMultiFacetMatchedName_JP = ".searchResults[*].aggregationBuckets[*].productsList[*].matchedFields";
	public static String v2SearchMultiFacetSource_JP = ".searchResults[*].aggregationBuckets[*].productsList[*].source";
	
	public static String v2SearchSource_JP=".searchResults[*].source";
	public static String v2SearchDS_Source_JP="._source";
	public static String v2SearchSingleFacetSource_JP="schema:";
	public static String v2Searchauthor_JP="author";
	public static String v2Searchpublisher_JP="publisher";	
	public static String v2SearchSourceISBN="isbn13";	
	public static String v2SearchSourcebrandname="brand";	
	public static String v2SearchSourceProvidername="provider";	
	public static String v2SearchreSourceType="learning";	
	public static String v2SearchSourceISBN_JP="bf:isbn13";
	public static String v2SearchSource_Brandname="schema:brand";
	public static String v2SearchSource_Providername="cnt:auxiliaryFields";
	public static String v2Search_reSourceType="@type";	

	
	public static String v2SearchSingleFacetSourceFieldName_JP = ".searchResults[*].source.schema:";
	public static String v2SearchSingleFacetSourceName_JP = ".searchResults[*].source.schema:name";
	public static String v2SearchSingleFacetSourceDescription_JP =  ".searchResults[*].source.schema:description";
	public static String v2SearchSingleFacetSourceAbout_JP = ".searchResults[*].source.schema:about";
	public static String v2SearchSingleFacetSourceAuthorFamilyName_JP = "searchResults[*].source.schema:author[*].schema:familyName";
	public static String v2SearchSingleFacetSourcePublisherName_JP = "searchResults[*].source.schema:publisher.schema:name";
	public static String v2SearchSingleFacetSourceISBNName_JP = "searchResults[*].source.bf:isbn13";
	
	public static String v2SearchProduct_JP=".searchResults[*]";
	public static String v2SearchSFProduct_JP=".searchResults[*].productsList[*]";
	public static String v2SearchMFProduct_JP=".searchResults[*].aggregationBuckets[*].productsList[*]";
	
	// Region End
}
