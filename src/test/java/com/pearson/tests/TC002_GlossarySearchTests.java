package com.pearson.tests;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.pearson.framework.report.LoggerUtil;
import com.pearson.pageobjects.GlossaryControllerRequest;
import com.pearson.regression.utilityHelpers.Constants;;

public class TC002_GlossarySearchTests extends BaseTest {
	LinkedHashMap<String, LinkedHashMap<String, String>> hashMap = null;
	String TestDescription = "";

	@Test(dataProvider = "dp") 
	public void cm_getGlossarySearch_API(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			final GlossaryControllerRequest glossaryControllerRequest = new GlossaryControllerRequest(data_Search);
			TestDescription = "Test to validate Glossary Search API";
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			glossaryControllerRequest.cm_GlossarySearchResponseValidate(data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void cm_getGlossarySearchWithInvalidIndexId(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {

			final GlossaryControllerRequest glossaryControllerRequest = new GlossaryControllerRequest(data_Search);
			TestDescription = "Test to validate Glossary Search API  With Invalid IndexId";
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			glossaryControllerRequest.cm_GlossarySearchWithInvalidIndexIdResponseValidate(data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp") 
	public void cm_getGlossarySearchWithNullIndexId(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			final GlossaryControllerRequest glossaryControllerRequest = new GlossaryControllerRequest(data_Search);
			TestDescription = "Test to validate Glossary Search API  With Null IndexId";
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			glossaryControllerRequest.cm_GlossarySearchWithNullIndexIdResponseValidate(data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp") 
	public void cm_get_GlossarySearchWithSpecificKey(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			final GlossaryControllerRequest glossaryControllerRequest = new GlossaryControllerRequest(data_Search);
			TestDescription = "Validate whether user is able to search for Specific key in glossary";
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			glossaryControllerRequest.cm_GlossarySearchWithSpecificKeyResponseValidate(data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp") 
	public void get_mul_GlossaryContentWithValidIndexIdandGlossaryKey_02(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			final GlossaryControllerRequest glossaryControllerRequest = new GlossaryControllerRequest(data_Search);
			TestDescription = "Validate whether user is able to search for Specific key in glossary";
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			glossaryControllerRequest.cm_GlossaryContentWithValidIndexIdandGlossaryKey(data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void get_mul_GlossaryContentWithInvalidIndexId_03(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			final GlossaryControllerRequest glossaryControllerRequest = new GlossaryControllerRequest(data_Search);
			TestDescription = "Validate Glossary Content With Invalid IndexId_03";
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			glossaryControllerRequest.cm_mul_GlossaryContentWithInValidIndexId_03_ResponseValidate(data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp") 
	public void get_mul_GlossaryContentWithInvalidLang_06(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			final GlossaryControllerRequest glossaryControllerRequest = new GlossaryControllerRequest(data_Search);
			TestDescription = "Validate Glossary Content With Invalid IndexId_03";
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			glossaryControllerRequest.cm_GlossaryContentWithInValidLang_06_ResponseValidate(data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp") 
	public void get_mul_GlossaryValidIndexIdAndInvalidGlossaryKey_04(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			final GlossaryControllerRequest glossaryControllerRequest = new GlossaryControllerRequest(data_Search);
			TestDescription = "Validate the Glossary Content With Valid IndexId And InvalidGlossaryKey_04";
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			glossaryControllerRequest.cm_GlossaryValidIndexIdAndInvalidGlossaryKey_04_ResponseValidate(
					Constants.testStep, TestDescription, data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void get_mul_GlossaryValidLang_05(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			final GlossaryControllerRequest glossaryControllerRequest = new GlossaryControllerRequest(data_Search);
			TestDescription = "Validate the Glossary Content With Valid Lang_05";
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			glossaryControllerRequest.cm_GlossaryValidLang_05_ResponseValidate(data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void get_trd_GlossaryContentWithValidIndexId_07(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			final GlossaryControllerRequest glossaryControllerRequest = new GlossaryControllerRequest(data_Search);
			TestDescription = "Validate Traditional  Glossary Content With Valid IndexID 07";
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			glossaryControllerRequest.cm_trd_GlossaryContentWithValidIndexId_07ResponseValidate(data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void get_mul_GlossaryContentWithValidIndexId_01(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			final GlossaryControllerRequest glossaryControllerRequest = new GlossaryControllerRequest(data_Search);
			TestDescription = "Validate the Glossary Content With Valid IndexID 01";
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			glossaryControllerRequest.cm_GlossaryContentWithValidIndexId_01(data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void get_mul_GlossaryContentWithValidIndexIdAndvalidGlossaryKey_02(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			final GlossaryControllerRequest glossaryControllerRequest = new GlossaryControllerRequest(data_Search);
			TestDescription = "Validate the Glossary Content With Valid IndexID And valid GlossaryKey_02";
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			glossaryControllerRequest.cm_GlossaryContentWithValidIndexIdAndvalidGlossaryKey_02(data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp") 
	public void get_GlossaryContentWithValidIndexId_01(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			final GlossaryControllerRequest glossaryControllerRequest = new GlossaryControllerRequest(data_Search);
			TestDescription = "Validate the Glossary Content With Valid IndexID_01";
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			glossaryControllerRequest.cm_GlossaryContentWithValidIndexId_01Validate(data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp") 
	public void get_GlossaryContentWithValidIndexIdAndInvalidGlossaryKey_04(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			final GlossaryControllerRequest glossaryControllerRequest = new GlossaryControllerRequest(data_Search);
			TestDescription = "Validate the Glossary Content With Valid IndexID And Invalid Glossary Key_04";
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			glossaryControllerRequest.cm_GlossaryContentWithValidIndexIdAndInvalidGlossaryKey_04(data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void get_GlossaryContentWithValidIndexIdAndValidGlossaryKey_02(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			final GlossaryControllerRequest glossaryControllerRequest = new GlossaryControllerRequest(data_Search);
			TestDescription = "Validate the Glossary Content With Valid IndexID Valid GlossaryKey_02";
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			glossaryControllerRequest.cm_GlossaryContentWithValidIndexIdAndValidGlossaryKey_02(data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void get_GlossaryContentWithInvalidIndexId_03(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			final GlossaryControllerRequest glossaryControllerRequest = new GlossaryControllerRequest(data_Search);
			TestDescription = "Validate the Glossary Content With Valid IndexID_01";
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			glossaryControllerRequest.cm_GlossaryContentWithInvalidIndexId_03_ResponseValidate(data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp") // (testName = "Validate the Glossary API",
								// groups = "validCase")
	public void csg_getGlossary_API(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			final GlossaryControllerRequest glossaryControllerRequest = new GlossaryControllerRequest(data_Search);
			TestDescription = "Test to validate the Glossary API";
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			glossaryControllerRequest.csg_GlossaryContentValidate(data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void csg_getGlossary_2_0_API(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			final GlossaryControllerRequest glossaryControllerRequest = new GlossaryControllerRequest(data_Search);
			TestDescription = "Test to validate the Glossary  API_2_0";
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			glossaryControllerRequest.csg_GlossaryAPIValidate(data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}

	@Test(dataProvider = "dp")
	public void csg_getGlossary_2_1_API(Method m, LinkedHashMap<String, String> data_Search,
			ITestContext ctx) {
		try {
			final GlossaryControllerRequest glossaryControllerRequest = new GlossaryControllerRequest(data_Search);
			TestDescription = "Test to validate the Glossary  API_2_1";
			LoggerUtil.log(TestDescription, "", Constants.testStep, null);
			glossaryControllerRequest.csg_GlossaryAPIValidate(data_Search);
		} catch (Exception e) {
			LoggerUtil.log("Test Case Failed", "" + e.getMessage() + Constants.testStep);
			e.getMessage();
			e.printStackTrace();
			Assert.fail(e.getMessage(), e);
		}
	}
}
