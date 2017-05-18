
package com.pearson.regression.utilityHelpers;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import java.util.Map;
import java.util.Properties;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * Rest service methods written based on the input parameters and header information
 * @URL
 * @headers
 * @jsonObject
 */
public class RESTServiceBase {
	protected Properties properties = new Properties();

	
	public Response getCall(String URL2) {
		return RestAssured.get((String) URL2, (Object[]) new Object[0]);
	}
	
	public Response getCallWithHeaderParam(Map<String, String> headers, String URL2) throws Exception {
		return (Response) RestAssured.given().log().all().relaxedHTTPSValidation().headers(headers).log().all()
				.contentType(this.getContentType()).log().all().request().when().log().all().get(URL2, new Object[0]);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Response postCallWithFormParam(JSONObject jsonObject, String URL2) throws Exception {
		return (Response) RestAssured.given().log().all().relaxedHTTPSValidation().formParameters((Map) jsonObject)
				.contentType(this.getContentType()).log().all().request().when().log().all().post(URL2, new Object[0]);
	}
	
	public Response postCallWithReqBodyAsJsonObject(JSONObject jsonObject, String URL2) throws Exception {
		return (Response) RestAssured.given().log().all().relaxedHTTPSValidation().body(jsonObject)
				.contentType(this.getContentType()).log().all().request().when().log().all().post(URL2, new Object[0]);
	}
	
	public Response postCallWithReqBodyAsJsonArray(JSONArray jsonArray, String URL2) throws Exception {
		return (Response) RestAssured.given().log().all().relaxedHTTPSValidation().body(jsonArray)
				.contentType(this.getContentType()).log().all().request().when().log().all().post(URL2, new Object[0]);
	}
	
	public Response postCallWithHeaderAndBodyParam(Map<String, String> headers, JSONObject jsonObject, String URL2)
			throws Exception {
		System.out.println("headers"+headers+"jsonobj:"+jsonObject+"urls**************:"+URL2);
		return (Response) RestAssured.given().log().all().relaxedHTTPSValidation().headers(headers).log().all()
				.contentType(this.getContentType()).request().log().all().body((Object) jsonObject).when().log().all()
				.post(URL2, new Object[0]);
	}
	
	public Response postCallWithBodyParam(JSONObject jsonObject, String URL2) throws Exception {
		return (Response) RestAssured.given().log().all().relaxedHTTPSValidation().contentType(this.getContentType())
				.request().log().all().body((Object) jsonObject).when().log().all().post(URL2, new Object[0]);
	}
	
	public Response postCallWithHeaderAndBodyParamAndContentType(Map<String, String> headers, byte[] req, String URL2) {
		return (Response) RestAssured.given().log().all().headers(headers).log().all().body(req).when().log().all()
				.post(URL2, new Object[0]);
	}
	
	public Response postCallWithHeaderAndBodyParamAsString(Map<String, String> headers, String bodyParam, String URL2)
			throws Exception {
		return (Response) RestAssured.given().log().all().relaxedHTTPSValidation().headers(headers).log().all()
				.contentType(this.getContentType()).request().log().all().body(bodyParam).when().log().all()
				.post(URL2, new Object[0]);
	}
	
	public Response PostCallWithHeaderParam(Map<String, String> headers, String URL2) throws Exception {
		return (Response) RestAssured.given().log().all().relaxedHTTPSValidation().headers(headers).log().all()
				.contentType(this.getContentType()).request().log().all().when().log().all().post(URL2, new Object[0]);
	}
	
	public Response DeleteCallWithHeaderAndPathParam(Map<String, String> headers, JSONObject jsonObject, String URL2)
			throws Exception {
		return (Response) RestAssured.given().log().all().relaxedHTTPSValidation().headers(headers).log().all()
				.contentType(this.getContentType()).request().log().all().body((Object) jsonObject).when().log().all()
				.delete(URL2, new Object[0]);
	}
	
	public Response PutCallWithHeaderAndBodyParam(Map<String, String> headers, JSONObject jsonObject, String URL2)
			throws Exception {
		return (Response) RestAssured.given().log().all().relaxedHTTPSValidation().headers(headers).log().all()
				.contentType(this.getContentType()).request().log().all().body((Object) jsonObject).when().log().all()
				.put(URL2, new Object[0]);
	}
	
	public Response getCallWithHeaderAndParameters(Map<String, String> headers, Map<String, String> parameters,
			String requestURL) throws Exception {
		return (Response) RestAssured.given().log().all().parameters(parameters).relaxedHTTPSValidation()
				.headers(headers).log().all().contentType(this.getContentType()).log().all().request().when().log()
				.all().get(requestURL);
	}
	
	public Response getCallWithParameters(Map<String, String> parameters, String requestURL) throws Exception {
		return (Response) RestAssured.given().log().all().parameters(parameters).relaxedHTTPSValidation().log().all()
				.contentType(this.getContentType()).log().all().request().when().log().all().get(requestURL);
	}
	
	public Response getCallWithParameterswithoriginaldatatypes(Map<String, Object> parameters, String requestURL) throws Exception {
		return (Response) RestAssured.given().log().all().parameters(parameters).relaxedHTTPSValidation().log().all()
				.contentType(this.getContentType()).log().all().request().when().log().all().get(requestURL);
	}


	private ContentType getContentType() throws Exception {
		String CONTENT_TYPE = "json";
		if (CONTENT_TYPE.equals("json")) {
			return ContentType.JSON;
		}
		if (CONTENT_TYPE.equals("urlenc")) {
			return ContentType.URLENC;
		}
		if (CONTENT_TYPE.equals("text")) {
			return ContentType.TEXT;
		}
		if (CONTENT_TYPE.equals("html")) {
			return ContentType.HTML;
		}
		if (CONTENT_TYPE.equals("binary")) {
			return ContentType.BINARY;
		}
		if (CONTENT_TYPE.equals("xml")) {
			return ContentType.XML;
		}
		throw new Exception("Not a valid Content Type, Please set the valid Content Type");
	}

}
