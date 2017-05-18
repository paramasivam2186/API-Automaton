package com.pearson.regression.utilityHelpers;

import org.testng.Assert;

import com.pearson.framework.report.LoggerUtil;

public class ExceptionHandler {

	// Log Exception
	/**
	 * @param e
	 * @param testStep
	 * @param testDescription
	 */
	public static void logException(Exception e,String testStep, String testDescription)  {
		LoggerUtil.log("SubStep "+ testStep+" :" + testDescription+"\n" +"Test Case Failed " +e.getMessage()+" TestStep");
		e.getMessage();
		e.printStackTrace();
		Assert.fail(e.getMessage(), e);
	}
	/**
	 * @param e
	 */
	public static void logException(Exception e)  {
		LoggerUtil.log("Test Case Failed " +e.getMessage()+" TestStep");
		e.getMessage();
		e.printStackTrace();
		Assert.fail(e.getMessage(), e);
	}
}