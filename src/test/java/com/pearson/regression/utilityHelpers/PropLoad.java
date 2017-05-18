package com.pearson.regression.utilityHelpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

public class PropLoad {
	private String _path;

	private static Properties prop = new Properties();

	/**
	 * @param _path
	 */
	private PropLoad(String _path) {
		super();
		this._path = _path;

		try {
			prop.load(new FileInputStream(this._path));
		} catch (IOException e) {
			ExceptionHandler.logException(e);
		}

	}

	/**
	 * @param _reportString
	 * @param _arg
	 * @return
	 */
	public static String crMsg(String _reportString, String _arg) {
		String errorMsg = null;
		new PropLoad("CustomReports.properties");

		if (_arg != null) {
			errorMsg = prop.getProperty(_reportString);
			errorMsg = MessageFormat.format(errorMsg, _arg);

			return errorMsg.replace("\"", "&quote;");
		} else {
			errorMsg = prop.getProperty(_reportString);

			return errorMsg;
		}
	}

	/**
	 * To get environment Base URl 
	 * @return
	 */
	public static String getEnvironmentBaseUrl() {
		String value = null;
		try {
			File sourceFilePath = new File("./Automation_TestNgSuites", "configuration.properties");
			new PropLoad(sourceFilePath.getAbsolutePath());
		} catch (Exception e) {
			ExceptionHandler.logException(e);
		}
		value = prop.getProperty("Environment") + "_BaseURL";
		return prop.getProperty(value);
	}

	/**
	 * To get Content API URL
	 * @return
	 */
	public static String getcontentAPiUrl() {
		String value = null;
		try {
			File sourceFilePath = new File("./Automation_TestNgSuites", "configuration.properties");
			new PropLoad(sourceFilePath.getAbsolutePath());
		} catch (Exception e) {
			ExceptionHandler.logException(e);
		}
		value = prop.getProperty("Environment") + "_ContentApiURL";
		return prop.getProperty(value);
	}

	/**
	 * To get elastic search URL
	 * @return
	 */
	public static String getelasticSearchUrl() {
		String value = null;
		try {
			File sourceFilePath = new File("./Automation_TestNgSuites", "configuration.properties");
			new PropLoad(sourceFilePath.getAbsolutePath());
		} catch (Exception e) {
			ExceptionHandler.logException(e);
		}
		value = prop.getProperty("Environment") + "_elasticSearchURL";
		return prop.getProperty(value);
	}
	
	/**
	 * @return
	 */
	public static String getdatStoreSearchUrl() {
		String value = null;
		try {
			File sourceFilePath = new File("./Automation_TestNgSuites", "configuration.properties");
			new PropLoad(sourceFilePath.getAbsolutePath());
		} catch (Exception e) {
			ExceptionHandler.logException(e);
		}
		value = prop.getProperty("Environment") + "_profileURL";
		return prop.getProperty(value);
	}

	/**
	 * To get Profile URL
	 * @return
	 */
	public static String getprofileUrl() {
		String value = null;
		try {
			File sourceFilePath = new File("./Automation_TestNgSuites", "configuration.properties");
			new PropLoad(sourceFilePath.getAbsolutePath());
		} catch (Exception e) {
			ExceptionHandler.logException(e);
		}
		value = prop.getProperty("Environment") + "_profileURL";
		return prop.getProperty(value);
	}

	/**
	 * To get confirguration data
	 * @param keyName
	 * @return
	 */
	public static String getConfigData(String keyName) {
		try {
			File sourceFilePath = new File("./Automation_TestNgSuites", "configuration.properties");
			new PropLoad(sourceFilePath.getAbsolutePath());
		} catch (Exception e) {
			ExceptionHandler.logException(e);
		}
		return prop.getProperty(keyName);
	}

	/**
	 * To get Test data
	 * @param keyName
	 * @return
	 */
	public static String getTestData(String keyName) {
		try {
			File sourceFilePath = new File("./Automation_TestData", "TestData.properties");
			new PropLoad(sourceFilePath.getAbsolutePath());
		} catch (Exception e) {
			ExceptionHandler.logException(e);
		}
		return prop.getProperty(keyName);
	}

	/**
	 * @param _reportString
	 * @return
	 */
	public static String crMsg(String _reportString) {
		return crMsg(_reportString, null);
	}

}