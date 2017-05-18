package com.pearson.tests;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class XMLGenerator {

	static XmlSuite suite;

	static XmlTest test;

	static XmlClass xmlClaz;
	static List<XmlClass> xmlClassesList = new ArrayList<XmlClass>();
	static String basepkg = "com.pearson.tests";

	static MultiMap multiMap = new MultiValueMap();

	// Convert String to Array
	public static void main(String[] args)
			throws EncryptedDocumentException, InvalidFormatException, FileNotFoundException, IOException {
//System.out.println("Inside the class");
		runModeUpdateInTestDataSheet();
		xmlInit();
		scanExcel();
		suiteGen();
		String xml = getXML();
		writeToXML(xml);

	}
	
	private static void runModeUpdateInTestDataSheet() 
            throws EncryptedDocumentException, InvalidFormatException, FileNotFoundException, IOException{
     Workbook wb;
     File sourceFilePath = new File("./Automation_TestData", "Automation_TestData.xlsx");
     //InputStream inp = new FileInputStream(sourceFilePath);
     wb = WorkbookFactory.create(new FileInputStream(sourceFilePath));
     try {
            Sheet home_Sheet  = wb.getSheet("Home");
            int home_sheet_last_Column = home_Sheet.getRow(0).getLastCellNum();
            
            for(int home_sheet_row_Iterator = 1; home_sheet_row_Iterator<=home_Sheet.getLastRowNum();home_sheet_row_Iterator++){
                  Cell getClassNameobj = home_Sheet.getRow(home_sheet_row_Iterator).getCell(1);
                  String getClassName =getClassNameobj.getStringCellValue();
                  Cell getClassRunModeobj = home_Sheet.getRow(home_sheet_row_Iterator).getCell(home_sheet_last_Column-1);
                  String getClassRunMode =getClassRunModeobj.getStringCellValue();
            if(getClassRunMode.trim().equals("Run")){
                  Sheet classNameSheet = wb.getSheet(getClassName);
                  int getMethodRunMode =classNameSheet.getRow(0).getLastCellNum();
                  for (int rowNumber = 1; rowNumber <= classNameSheet.getLastRowNum(); rowNumber++) {
                         classNameSheet.getRow(rowNumber).getCell(getMethodRunMode-1).setCellValue("Yes");
                  }}
                  else if(getClassRunMode.trim().equals("No")){
                         Sheet classNameSheet = wb.getSheet(getClassName);
                         int getMethodRunMode =classNameSheet.getRow(0).getLastCellNum();
                         for (int rowNumber = 1; rowNumber <= classNameSheet.getLastRowNum(); rowNumber++) {
                                classNameSheet.getRow(rowNumber).getCell(getMethodRunMode-1).setCellValue("No");
                         }      
                  }
                  FileOutputStream fileOut = new FileOutputStream(sourceFilePath);
                  wb.write(fileOut);
                  fileOut.close();
            }
       }
     
     catch (Exception e) {
            System.out.println(e.getMessage());
     }
}


	private static void xmlInit() {
		// TODO Auto-generated method stub
		
		System.out.println("Inside the Init");
		suite = new XmlSuite();
		suite.setName("API Suite");
		test = new XmlTest(suite);
		test.setName("API Test");
		xmlClassesList = new ArrayList<XmlClass>();
	}

	private static void scanExcel()
			throws EncryptedDocumentException, InvalidFormatException, FileNotFoundException, IOException {
		Workbook wb;
		File sourceFilePath = new File("./Automation_TestData", "Automation_TestData.xlsx");
		wb = WorkbookFactory.create(new FileInputStream(sourceFilePath));
		Sheet sheet = null;
		try {
			int noSheets = wb.getNumberOfSheets();
//			System.out.println("noSheets" + noSheets);
//			System.out.println(sheet.ggetSheetName());
			for (int sheetIterator = 0; sheetIterator < noSheets; sheetIterator++) {
				sheet = wb.getSheetAt(sheetIterator);
				String strToRun;
				for (int rowNumber = 1; rowNumber <= sheet.getLastRowNum(); rowNumber++) {
//					System.out.println(sheet.getSheetName());
					int excuteColumn = sheet.getRow(rowNumber).getLastCellNum();
					Cell cell = sheet.getRow(rowNumber).getCell(excuteColumn - 1);
					strToRun = cell.getStringCellValue();
					if(strToRun.trim().equalsIgnoreCase("Yes")== true) 
					{
						//System.out.println("rowNumber----"+rowNumber);
						String strClassName = wb.getSheetName(sheetIterator);
						String strMethodName = sheet.getRow(rowNumber).getCell(0).getStringCellValue();
						if(multiMap.containsValue(strMethodName)==false)
						{
							multiMap.put(strClassName, strMethodName);//rClassName, strmdName->strMethodName);
						}					
					} 
					
				}
			}
			}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	private static void suiteGen() 
	{
		
		@SuppressWarnings("unchecked")
		Set<String> keys = multiMap.keySet();
		
		for (String key : keys) 
		{
			String clazName = basepkg + "." + key;
			xmlClaz = new XmlClass(clazName);
			@SuppressWarnings("unchecked")
			Collection<Object> values=(Collection<Object>) multiMap.get(key);
			
			for(Object keyValue:values)
			{
				List<XmlInclude> methodIncludes = new ArrayList<XmlInclude>();
				methodIncludes = xmlClaz.getIncludedMethods();
				XmlInclude methodInc = new XmlInclude(keyValue.toString());
				methodIncludes.add(methodInc);	
			}
			xmlClassesList.add(xmlClaz);
		}
		test.setXmlClasses(xmlClassesList);

		}

	private static String getXML() {
		String listener="com.pearson.framework.report.CustomListeners";
		suite.addListener(listener);
		Map<String, String> parameters = new HashMap<>();
		parameters.put("TestingType", "api");
		parameters.put("ExecutionEnvironment", "local");
		parameters.put("SaucelabsUsername", "api");
		parameters.put("SaucelabsAPIKEY", "api");
		parameters.put("ApplicationEnvironment", "qa");
		parameters.put("RESTAPIContentType", "json");
		parameters.put("gridURL", "api");
		parameters.put("ApplicationToTest", "csg");
		parameters.put("RetryAttempt", "0");
		
		suite.setParameters(parameters);
		System.out.println("testNG"+suite.toXml());
		return suite.toXml();
	}

	private static void writeToXML(String xml) {
		String path = System.getProperty("user.dir");
		System.out.println("user.dir + testng is " + path + "//TestNgAPI.xml");
		try (Writer writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(path + "/TestNgAPI.xml"), "utf-8"))) {
			writer.write(xml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}