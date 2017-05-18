package com.pearson.regression.utilityHelpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelRead {

	/**
	 * getting test data input from excel sheet and formatting the values as key value pairs using column name
	 * @return
	 * @throws Exception
	 */
	public static LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>> getData() throws Exception {

		FileInputStream inStream = null;
		LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>> data = new LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>>();
		try {
			File sourceFilePath = new File("./Automation_TestData", "Automation_TestData.xlsx");
			inStream = new FileInputStream(sourceFilePath);
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook((InputStream) inStream);
			LinkedHashMap<String, String> getData = new LinkedHashMap<String, String>();
			
			XSSFRow headerRow;
			XSSFRow valueRow;
			ArrayList<LinkedHashMap<String, String>> arrayList = null;
			for (org.apache.poi.ss.usermodel.Sheet sheet : workbook) {
				int columnCount = ((XSSFSheet) sheet).getRow(0).getPhysicalNumberOfCells();
				int rowNumber = ((XSSFSheet) sheet).getLastRowNum();
				
				for (int rowItr = 1; rowItr <= rowNumber; rowItr++) {
					String headerString = null;
					int excuteColumn = sheet.getRow(rowItr).getLastCellNum();
					Cell cell = sheet.getRow(rowItr).getCell(excuteColumn - 1);
					String strToRun = cell.getStringCellValue();
					if (strToRun.trim().equalsIgnoreCase("Yes") == true) {
						//System.out.println(rowItr);
						headerRow = ((XSSFSheet) sheet).getRow(0);
						valueRow = ((XSSFSheet) sheet).getRow(rowItr);
						// System.out.println(valueRow);
						headerString = valueRow.getCell(0).getStringCellValue();
						if (data.containsKey(headerString)) {
							getData = getHeader(headerRow, valueRow, columnCount);
//							System.out.println("Existing Data"+getData);
							arrayList=data.get(headerString);
							arrayList.add(getData);	
						} else {
							getData = getHeader(headerRow, valueRow, columnCount);
//							System.out.println("new Data"+getData);
							arrayList = new ArrayList<LinkedHashMap<String, String>>();
							arrayList.add(getData);
							data.put(headerString, arrayList);
//							getData=null;
						}
//						data.put(headerString, arrayList);
					}
					
				}
				inStream.close();
			}
			//System.out.println("Total Data Point"+data);
			return data;
		} catch (FileNotFoundException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		}
	}

	/**
	 * To get the header information
	 * @param header
	 * @param column
	 * @param count
	 * @return
	 */
	public static LinkedHashMap<String, String> getHeader(XSSFRow header, XSSFRow column, int count) {

		LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();

		for (int cellCtr = 1; cellCtr < count; ++cellCtr) {
			String headerValue = null;
			String value = null;
			XSSFCell cell = header.getCell(cellCtr);
			XSSFCell columnCell = column.getCell(cellCtr);
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				headerValue = cell.getRichStringCellValue().getString();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				headerValue = Double.toString(cell.getNumericCellValue());
				break;
			default:
				//System.out.println();
			}
			if (columnCell != null) {
				switch (columnCell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					// System.out.println("Null");
					value = columnCell.getRichStringCellValue().getString();
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					value = Long.toString(poiGetCellValue(columnCell));
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					value = "";
					break;
				case XSSFCell.CELL_TYPE_ERROR:
					value = "";
					break;
				default:
					// System.out.println();
				}
			} else {
				value = "";
			}
			hashMap.put(headerValue, value);
		}
		return hashMap;
	}

	/**
	 * @param cell
	 * @return
	 */
	private static long poiGetCellValue(XSSFCell cell) {
		long x;
		if (cell.getCellType() == 0)
			x = (long) cell.getNumericCellValue();
		else if (cell.getCellType() == 1)
			x = Long.parseLong(cell.getStringCellValue());
		else
			x = -1;
		return x;
	}
}

