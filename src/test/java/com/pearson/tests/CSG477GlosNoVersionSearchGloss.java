package com.pearson.tests;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.pearson.pageobjects.GlossaryControllerRequest;
import com.pearson.regression.utilityHelpers.APIHelper;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.ResponseValidator;

public class CSG477GlosNoVersionSearchGloss extends BaseTest {

	LinkedHashMap<String, LinkedHashMap<String, String>> hashMap = null;
	String TestDescription = "";
	SoftAssert soft = new SoftAssert();

	@Test(dataProvider = "dp")
	public void CSG_Get_Glossary_Content_ValidIndexId_01(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final GlossaryControllerRequest glossory = new GlossaryControllerRequest(data_Search);
			final boolean result = glossory.jsonPathvalidateKeyValidation(response, data_Search);
			if(result){
				soft.assertTrue(result);
			}else{
				soft.assertTrue(result);
			}
		
			// Validating the JsonSchema file with Json Response
			final String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);
			soft.assertAll();
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CSG_Get_Glossary_Content_ValidIndexId_01_noKeyValue(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final GlossaryControllerRequest glossory = new GlossaryControllerRequest(data_Search);
			final boolean result = glossory.jsonPathValidationWithNoKey(response, data_Search);
			if(result){
				soft.assertTrue(result);
			}else{
				soft.assertTrue(result);
			}		
			// Validating the JsonSchema file with Json Response
			final String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);
			soft.assertAll();
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void CSG_Get_Glossary_Content_ValidIndexId_And_InvalidGlossaryKey_04(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final String expected = APIHelper.retriveValuefromJson("$..glossary", response.getBody().asString());
			Assert.assertEquals(null, expected);	
			
			// 2. Validating the Invalid response
			final String expInvalidResponse = data_Search.get(Constants.expJsonRes).trim();
			if (!expInvalidResponse.equals(Constants.emptyString)) {
				ResponseValidator.compareJsonResponse(response, expInvalidResponse);
			}
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CSG_Get_Glossary_Content_inValidIndexId(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final String expected = APIHelper.retriveValuefromJson("$..glossary", response.getBody().asString());
			Assert.assertEquals(null, expected);	
			
			// 2. Validating the Invalid response
			final String expInvalidResponse = data_Search.get(Constants.expJsonRes).trim();
			if (!expInvalidResponse.equals(Constants.emptyString)) {
				ResponseValidator.compareJsonResponse(response, expInvalidResponse);
			}
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CSG_Get_Glossary_Content_noIndexId(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void CM_Get_Glossary_Content_ValidIndexId_01(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final GlossaryControllerRequest glossory = new GlossaryControllerRequest(data_Search);
			final boolean result = glossory.jsonPathvalidateKeyValidation(response, data_Search);
			if(result){
				soft.assertTrue(result);
			}else{
				soft.assertTrue(result);
			}
		
			// Validating the JsonSchema file with Json Response
			final String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);
			soft.assertAll();
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	
	@Test(dataProvider = "dp")
	public void CM_Get_Glossary_Content_ValidIndexId_01_noKeyValue(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final GlossaryControllerRequest glossory = new GlossaryControllerRequest(data_Search);
			final boolean result = glossory.jsonPathValidationWithNoKey(response, data_Search);
			if(result){
				soft.assertTrue(result);
			}else{
				soft.assertTrue(result);
			}		
			// Validating the JsonSchema file with Json Response
			final String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);
			soft.assertAll();
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void CM_Get_Glossary_Content_ValidIndexId_And_InvalidGlossaryKey_04(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final String expected = APIHelper.retriveValuefromJson("$..glossary", response.getBody().asString());
			Assert.assertEquals(null, expected);	
			
			// 2. Validating the Invalid response
			final String expInvalidResponse = data_Search.get(Constants.expJsonRes).trim();
			if (!expInvalidResponse.equals(Constants.emptyString)) {
				ResponseValidator.compareJsonResponse(response, expInvalidResponse);
			}

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CM_Get_Glossary_Content_inValidIndexId(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final String expected = APIHelper.retriveValuefromJson("$..glossary", response.getBody().asString());
			Assert.assertEquals(null, expected);	
			
			// 2. Validating the Invalid response
			final String expInvalidResponse = data_Search.get(Constants.expJsonRes).trim();
			if (!expInvalidResponse.equals(Constants.emptyString)) {
				ResponseValidator.compareJsonResponse(response, expInvalidResponse);
			}
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	
	@Test(dataProvider = "dp")
	public void Get_Glossary_Content_ValidIndexId_And_validGlossaryKey_02(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final GlossaryControllerRequest glossory = new GlossaryControllerRequest(data_Search);
			glossory.jsonPathvalidateKeyValidation(response, data_Search);

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void CM_Get_Glossary_Content_noIndexId(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
}
