package com.pearson.tests;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;
import com.pearson.framework.report.LoggerUtil;
import com.pearson.pageobjects.NavigationController;
import com.pearson.regression.utilityHelpers.APIHelper;
import com.pearson.regression.utilityHelpers.Constants;
import com.pearson.regression.utilityHelpers.ResponseValidator;

public class CSG477GetNavigation extends BaseTest {

	LinkedHashMap<String, LinkedHashMap<String, String>> hashMap = null;
	String TestDescription = "";

	@Test(dataProvider = "dp")
	public void Get_Navigation_With_ValidIndexID_and_type_01(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);

			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final String content = response.getBody().asString();
			final NavigationController navcontrol = new NavigationController(data_Search);
			navcontrol.ValidateMediaarrayjsonPath(response, data_Search, content);

			// Validating the JsonSchema file with Json Response
			final String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void Get_Navigation_With_InvalidIndexID_02(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);

			APIHelper.validaStatusCode(response, data_Search);

			final String expected = APIHelper.retriveValuefromJson("$..totalHits", response.getBody().asString());
			Assert.assertEquals("0", expected);
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
	public void Get_Navigation_With_InvalidType_03(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final String expected = APIHelper.retriveValuefromJson("$..totalHits", response.getBody().asString());
			Assert.assertEquals("0", expected);
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
	public void Get_Navigation_Without_indexId(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			APIHelper.validaErrorMessage(response, data_Search);
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
	public void Get_Navigation_Without_type(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			APIHelper.validaErrorMessage(response, data_Search);
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
	public void CSG_Get_Navigation_With_Invalid_Hits_Counts_05(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

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
	public void CSG_Get_Navigation_With_Invalid_Hits_Start_Index_04(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);

			APIHelper.validaStatusCode(response, data_Search);

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
	public void CSG_Get_Navigation_Without_Hits_Start_Index_08(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

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
	public void CSG_Get_Navigation_Without_Hits_Counts_09(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CSG(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);

			APIHelper.validaStatusCode(response, data_Search);

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
	public void CM_Get_Navigation_With_ValidIndexID_and_type_01(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);

			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final String content = response.getBody().asString();
			final NavigationController navcontrol = new NavigationController(data_Search);
			navcontrol.ValidateMediaarrayjsonPath(response, data_Search, content);
			APIHelper.validaStatusCode(response, data_Search);

			// Validating the JsonSchema file with Json Response
			final String jsonSchemaFileName = data_Search.get(Constants.expjsonSchemafileName);
			ResponseValidator.validateJsonSchema(response, jsonSchemaFileName);

		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void CM_Get_Navigation_With_InvalidIndexID_02(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);

			APIHelper.validaStatusCode(response, data_Search);

			final String expected = APIHelper.retriveValuefromJson("$..totalHits", response.getBody().asString());
			Assert.assertEquals("0", expected);
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
	public void CM_Get_Navigation_With_InvalidType_03(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			final String expected = APIHelper.retriveValuefromJson("$..totalHits", response.getBody().asString());
			Assert.assertEquals("0", expected);
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
	public void CM_Get_Navigation_Without_indexId(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			// APIHelper.validaErrorMessage(response, data_Search);
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
	public void CM_Get_Navigation_Without_type(Method m, LinkedHashMap<String, String> data_Search, ITestContext ctx) {
		try {

			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

			// APIHelper.validaErrorMessage(response, data_Search);
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
	public void Get_Navigation_With_Invalid_Hits_Counts_05(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);
			APIHelper.validaStatusCode(response, data_Search);

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
	public void Get_Navigation_With_Invalid_Hits_Start_Index_04(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);

			APIHelper.validaStatusCode(response, data_Search);

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
	public void Get_Navigation_Without_Hits_Start_Index_08(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);

			APIHelper.validaStatusCode(response, data_Search);

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
	public void Get_Navigation_Without_Hits_Counts_09(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			
			ctx.getCurrentXmlTest().addParameter("Testcase_Name", data_Search.get("Testcase_Name").trim());
			ctx.getCurrentXmlTest().addParameter("Testcase_ID", data_Search.get("Testcase_ID").trim());

			LoggerUtil.log(m.getName(), "", Constants.testStep, null);
			final String URI = APIHelper.URIgenerator_CM(data_Search);
			final Response response = APIHelper.api_getCallWithParameter(data_Search, URI);

			APIHelper.validaStatusCode(response, data_Search);

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

}
