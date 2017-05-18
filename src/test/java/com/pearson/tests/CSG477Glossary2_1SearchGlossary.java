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

public class CSG477Glossary2_1SearchGlossary extends BaseTest{

	LinkedHashMap<String, LinkedHashMap<String, String>> hashMap = null;
	String TestDescription = "";
	SoftAssert soft = new SoftAssert();
	
	@Test(dataProvider = "dp")
	public void Get_multilingual_2_1_Glossary_Content_ValidIndexId_01(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			String URI = APIHelper.URIgenerator_CSG(data_Search);
			Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			// Validating the JsonSchema file with Json Response
			String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Get_multilingual_Glossary_Content_ValidIndexId_And_validGlossaryKey_02(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			String URI = APIHelper.URIgenerator_CSG(data_Search);
			Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final GlossaryControllerRequest glossory = new GlossaryControllerRequest(data_Search);
			final boolean result = glossory.jsonPathvalidateKeyValidation(response, data_Search);
			if(result){
				soft.assertTrue(result);
			}else{
				soft.assertTrue(result);
			}

			// Validating the JsonSchema file with Json Response
			String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Get_multilingual_2_1_Glossary_Valid_Lang_05(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			String URI = APIHelper.URIgenerator_CSG(data_Search);
			Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final GlossaryControllerRequest glossory = new GlossaryControllerRequest(data_Search);
			final boolean result = glossory.jsonPathvalidateKeyValidation(response, data_Search);
			if(result){
				soft.assertTrue(result);
			}else{
				soft.assertTrue(result);
			}

			// Validating the JsonSchema file with Json Response
			String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
	
	@Test(dataProvider = "dp")
	public void Get_CM_multilingual_2_1_Glossary_Content_ValidIndexId_01(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			String URI = APIHelper.URIgenerator_CM(data_Search);
			Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			// Validating the JsonSchema file with Json Response
			String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Get_CM_multilingual_Glossary_Content_ValidIndexId_And_validGlossaryKey_02(Method m,
			LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			String URI = APIHelper.URIgenerator_CM(data_Search);
			Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final GlossaryControllerRequest glossory = new GlossaryControllerRequest(data_Search);
			final boolean result = glossory.jsonPathvalidateKeyValidation(response, data_Search);
			if(result){
				soft.assertTrue(result);
			}else{
				soft.assertTrue(result);
			}

			// Validating the JsonSchema file with Json Response
			String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Get_CM_multilingual_2_1_Glossary_Valid_Lang_05(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			String URI = APIHelper.URIgenerator_CM(data_Search);
			Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);
			
			final GlossaryControllerRequest glossory = new GlossaryControllerRequest(data_Search);
			final boolean result = glossory.jsonPathvalidateKeyValidation(response, data_Search);
			if(result){
				soft.assertTrue(result);
			}else{
				soft.assertTrue(result);
			}

			// Validating the JsonSchema file with Json Response
			String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Get_Traditional_Glossary_Content_ValidIndexId_07(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			String URI = APIHelper.URIgenerator_CSG(data_Search);
			Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			// Validating the JsonSchema file with Json Response
			String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

}
