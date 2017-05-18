package com.pearson.rest;

import java.util.Properties;

import com.pearson.util.Settings;

public class Constants {
	
	public static Properties properties = Settings.getInstance();

	public static final String HOST = properties.getProperty("HPALMServer");
	public static final String PORT = properties.getProperty("HPALMPort");
	public static final String USERNAME = properties.getProperty("HPALMUserName");
	public static final String PASSWORD = properties.getProperty("HPALMPassword");
	public static final boolean VERSIONED = false;
	public static final String DOMAIN = properties.getProperty("HPALMDomain");
	public static final String PROJECT = properties.getProperty("HPALMProject");

}
