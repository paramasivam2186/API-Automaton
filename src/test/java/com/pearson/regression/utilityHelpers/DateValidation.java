package com.pearson.regression.utilityHelpers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jayway.restassured.response.Response;
import com.relevantcodes.extentreports.LogStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

@SuppressWarnings("unused")
public class DateValidation {
	Map<Integer, String> mapStrWithKey = new HashMap<Integer, String>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

	// static String actualString =
	// "ACTIVE) AND childQuery:offerStartDate:[2016-08-12 TO 2016-10-21] > {OR}
	// >= AND < <= offerEndDate:[2016-12-10 TO *] ,amountis
	// equalto:rEndDate:[2016-12-10 TO * ]";
	// static String actualString
	// ="ACTIVE) AND childQuery:offerStartDate:2015-09-12";
	// static String actualString =
	// "ACTIVE) AND childQuery:offerStartDate:>=2015-09-12";
	// static String actualString = "ACTIVE) AND childQuery:offerstartDate:[
	// 2015-09-12 TO 2016-07-10 ]";
	// static String actualString = "ACTIVE) AND childQuery:offerstartDate:[
	// 2015-09-12 TO * ]";
	// static String actualString = "ACTIVE) AND childQuery:offerendDate:[ *TO
	// 2015-09-12 ]";
	// static String actualString = "ACTIVE) AND childQuery:offerEndDate:{
	// 2015-09-12 TO 2016-07-10 }";
	// static String actualString = "ACTIVE) AND
	// childQuery:offerStartDate:<2015-05-21 OR offerstartDate:>=2017-08-21";
	// static String actualString = "childQuery:offerStartDate:[2017-10-13 TO
	// *]";
	static String actualString = "childQuery:offerEndDate:<=2017-08-23 AND offerEndDate:>=2017-08-11";
	// static String actualString = "childQuery:offerStartDate:>=2015-11-30 OR
	// offerEndDate:<=2018-11-30";
	Date date1 = new Date();
	String oldDate = "2000-01-01";
	String curDate = "3025-01-01"; // sdf.format(date1);
	String futrDate = "3025-01-01"; // please use this format

	int offerStartDate;
	int offerEndDate;
	int andOp;
	int orOp;
	int curlyOpen;
	int curlyClose;
	int squreBraceOpen;
	int squreBraceClose;
	int to;
	int star;
	int semiColon;
	int greaterThan;
	int equalTo;
	int lessThan;
	int hyphen;
	int fSlash;

	/*
	 * String tempArrStr, tempArrFnl; int tempArrInt; int tempDateInt = 0; int
	 * sizeOfTotalVal = mapStrWithKey.size(); //
	 * System.err.println(sizeOfTotalVal); ArrayList<Integer> strArr1 = new
	 * ArrayList<Integer>(); ArrayList<Integer> strArr2 = new
	 * ArrayList<Integer>();
	 */
	/*	public static void main(String[] args) {
		DateValidation scn = new DateValidation();
		boolean test = scn.dateValidationFinalCheck(actualString, "2017-08-11", "oeDate");
		System.out.println("FINAL RESULT : - " + test);
	}*/

	/**
	 * Date validation
	 * @param inputQueryString
	 * @param searchDateStr
	 * @param offerDateInfo
	 * @return
	 */
	public boolean dateValidationFinalCheck(String inputQueryString, String searchDateStr, String offerDateInfo) {

		
		String tempArrStr = null;
		String tempArrFnl = null;
		String tempArrStr1 = null;
		String tempArrStr2 = null;
		int tempArrInt = 0;
		int tempDateInt = 0;
		int sizeOfTotalVal = -1;
		ArrayList<Integer> strArr1 = null;
		ArrayList<Integer> strArr2 = null;
		String tempArrFnl1 = null;
		String tempArrFnl2 = null;
		ArrayList<Integer> strArr3 = null;
		ArrayList<Integer> strArr4 = null;

		try {
			// StrCutNew scn = new StrCutNew();
			// String actualString =
			// "ACTIVE) AND childQuery:offerStartDate:[2016-08-12 TO 2016-10-21]
			// > {OR} >= AND < <= offerEndDate:[2016-12-10 TO *] ,amountis
			// equalto:rEndDate:[2016-12-10 TO * ]";
			// String actualString =
			// "ACTIVE) AND childQuery:offerStartDate:2015-09-12";
			String expString = this.cutStringFromSentence(inputQueryString, "childQuery:", ",");
			// int i = this.wordCountCheck("Offerenddate", expString);
			// System.out.println(i);

			offerStartDate = this.wordCountCheck("offerStartDate", expString);
			offerEndDate = this.wordCountCheck("offerEndDate", expString);
			andOp = this.wordCountCheck("AND", expString);
			orOp = this.wordCountCheck("OR", expString);
			curlyOpen = this.specialCharCheck("{", expString);
			curlyClose = this.specialCharCheck("}", expString);
			squreBraceOpen = this.specialCharCheck("[", expString);
			squreBraceClose = this.specialCharCheck("]", expString);
			to = this.wordCountCheck("TO", expString);
			star = this.specialCharCheck("*", expString);
			semiColon = this.specialCharCheck(":", expString);
			greaterThan = this.specialCharCheck(">", expString);
			equalTo = this.specialCharCheck("=", expString);
			lessThan = this.specialCharCheck("<", expString);
			hyphen = this.specialCharCheck("-", expString);
			fSlash = this.specialCharCheck("/", expString);

			System.out.println(expString);
			System.out.println("offerStartDate :" + offerStartDate);
			System.out.println("offerEndDate :" + offerEndDate);
			System.out.println("andOp :" + andOp);
			System.out.println("orOp :" + orOp);
			System.out.println("curlyOpen :" + curlyOpen);
			System.out.println("curlyClose :" + curlyClose);
			System.out.println("squreBraceOpen :" + squreBraceOpen);
			System.out.println("squreBraceClose :" + squreBraceClose);
			System.out.println("to :" + to);
			System.out.println("star :" + star);
			System.out.println("semiColon :" + semiColon);
			System.out.println("greaterThan :" + greaterThan);
			System.out.println("equalTo :" + equalTo);
			System.out.println("lessThan :" + lessThan);
			System.out.println("hyphen :" + hyphen);
			System.out.println("fSlash :" + fSlash);
			this.setenceSplit(expString);

			// FUNCTION CREATED ON 02/01/2017 -ST

			if (curlyOpen == 0 && curlyClose == 0 && squreBraceOpen == 0 && squreBraceClose == 0 && to == 0 && star == 0
					&& (hyphen >= 0 || fSlash >= 0) && (offerStartDate == 2 || offerEndDate == 2)
					&& (andOp == 1 || orOp == 1)) {
				// 2 offerStartDate function - start
				if ((mapStrWithKey.size() >= 1) && (offerDateInfo.equalsIgnoreCase("osDate"))) {

					boolean result1 = false;
					boolean result2 = false;
					
					tempArrStr = null;
					tempArrFnl = null;
					tempArrFnl1 = null;
					tempArrFnl2 = null;
					tempArrStr1 = null;
					tempArrStr2 = null;
					tempArrInt = 0;
					tempDateInt = 0;
					sizeOfTotalVal = mapStrWithKey.size();
					int ofstdate = 0;
					int ofeddate = 0;
					int tempgt = 0;
					int templs = 0;
					// int k=0;
					// System.err.println(sizeOfTotalVal);
					strArr1 = new ArrayList<Integer>();
					strArr2 = new ArrayList<Integer>();
					strArr3 = new ArrayList<Integer>();
					strArr4 = new ArrayList<Integer>();
					int andOR = 0; // if 1 then AND condition if 2 then OR
									// condition
					int gt = 0;
					int gtKey = 0;
					int ls = 0;
					int lsKey = 0;
					int eq = 0;
					int eqKey = 0;
					// int eq1 = 0;
					// int eqKey1 = 0;
					int hy = 0;
					int hyKey = 0;
					int k=0;
					int gteq = 0;
					int lseq = 0;
					
				//	if(offerDateInfo.equalsIgnoreCase("osDate") && offerStartDate == 2){}
					if (offerStartDate == 2 && lessThan == 1 && greaterThan == 1) {
				//		if (lessThan == 1 && greaterThan == 1) {
						System.out.println("2 OfferStart Date + No equal to + One Less than + One Greater than present");
						for (k = 0; sizeOfTotalVal > k; k++) {
							// System.out.print(k);
							tempArrStr = mapStrWithKey.get(k).toString();
							if (tempArrStr.toLowerCase().contains(">".toLowerCase())) {
								tempgt = k;
							}
							if (tempArrStr.toLowerCase().contains("<".toLowerCase())) {
								templs = k;
							}
							if (tempArrStr.toLowerCase().contains("-".toLowerCase())) {
								strArr1.add(k);}
							if (tempArrStr.toLowerCase().contains("=".toLowerCase())) {
								strArr2.add(k);}
							}
						//System.out.println("=============="+ strArr1.get(0).toString());
						//System.out.println("=============="+ strArr1.get(1).toString());
						//System.out.println(	"tempgt " + ofstdate + " templs " + ofeddate + "sizeofAnArray " + strArr1.size());
						tempArrStr1 = mapStrWithKey.get(Integer.parseInt(strArr1.get(0).toString())).toString();
						tempArrStr2 = mapStrWithKey.get(Integer.parseInt(strArr1.get(1).toString())).toString();
											
						
						//System.out.println(tempArrStr1 +" sam "+ tempArrStr2);
						if ((tempgt > templs) && (andOp == 1) && (equalTo == 0))
						{
							boolean tempVal1 = dateValidation(tempArrStr2, tempArrStr1, searchDateStr,"dBetween");
							return tempVal1;
						}
						else if ((tempgt < templs) && (andOp == 1)&& (equalTo == 0))
						{
							boolean tempVal1 = dateValidation(tempArrStr1, tempArrStr2, searchDateStr,"dBetween");
							return tempVal1;
						}
						else if ((tempgt > templs) && (orOp == 1)&& (equalTo == 0))
						{
							return true;
						}
						else if ((tempgt < templs) && (orOp == 1)&& (equalTo == 0))
						{
							return true;
						}
						else if ((tempgt > templs) && (andOp == 1) && (equalTo == 1))
						{
							if(tempgt == Integer.parseInt(strArr2.get(0).toString())-1){gteq=1; System.out.println("Greater than equal to - is present");}
							else if (templs == Integer.parseInt(strArr2.get(0).toString())-1){lseq=1; System.out.println("Less than equal to - is present");}
							if (gteq==1)
							{
							boolean tempVal1 = dateValidation(tempArrStr2, tempArrStr1, searchDateStr,"sDateEqualTo");
							boolean tempVal2 = dateValidation(tempArrStr2, tempArrStr1, searchDateStr,"dBetween");
							if (tempVal1 == true || tempVal2 == true)
							{
								return true;
							}
							else
							{
								return false;
							}
							}
							if (lseq==1)
							{
							boolean tempVal1 = dateValidation(tempArrStr2, tempArrStr1, searchDateStr,"eDateEqualTo");
							boolean tempVal2 = dateValidation(tempArrStr2, tempArrStr1, searchDateStr,"dBetween");
							if (tempVal1 == true || tempVal2 == true)
							{
								return true;
							}
							else
							{
								return false;
							}
							}
						}
						else if ((tempgt < templs) && (andOp == 1) && (equalTo == 1))
						{
							if(tempgt == Integer.parseInt(strArr2.get(0).toString())-1){gteq=1; System.out.println("Greater than equal to - is present");}
							else if (templs == Integer.parseInt(strArr2.get(0).toString())-1){lseq=1; System.out.println("Less than equal to - is present");}
							if (gteq==1)
							{
							boolean tempVal1 = dateValidation(tempArrStr1, tempArrStr2, searchDateStr,"sDateEqualTo");
							boolean tempVal2 = dateValidation(tempArrStr1, tempArrStr2, searchDateStr,"dBetween");
							if (tempVal1 == true || tempVal2 == true)
							{
								return true;
							}
							else
							{
								return false;
							}
							}
							if (lseq==1)
							{
							boolean tempVal1 = dateValidation(tempArrStr1, tempArrStr2, searchDateStr,"eDateEqualTo");
							boolean tempVal2 = dateValidation(tempArrStr1, tempArrStr2, searchDateStr,"dBetween");
							if (tempVal1 == true || tempVal2 == true)
							{
								return true;
							}
							else
							{
								return false;
							}
							}
						}
						else if ((tempgt > templs) && (orOp == 1)&& (equalTo == 1))
						{
							return true;
						}
						else if ((tempgt < templs) && (orOp == 1)&& (equalTo == 1))
						{
							return true;
						}
						else if ((tempgt > templs) && (andOp == 1) && (equalTo == 2))
						{
						boolean tempVal1 = dateValidation(tempArrStr2, tempArrStr1, searchDateStr,"sDateEqualTo");
						boolean tempVal2 = dateValidation(tempArrStr2, tempArrStr1, searchDateStr,"eDateEqualTo");
						boolean tempVal3 = dateValidation(tempArrStr2, tempArrStr1, searchDateStr,"dBetween");
						if (tempVal1 == true || tempVal2 == true || tempVal3 == true)
						{
							return true;
						}
						else
						{
							return false;
						}
						}
						else if ((tempgt < templs) && (andOp == 1)&& (equalTo == 2))
						{
							boolean tempVal1 = dateValidation(tempArrStr1, tempArrStr2, searchDateStr,"sDateEqualTo");
							boolean tempVal2 = dateValidation(tempArrStr1, tempArrStr2, searchDateStr,"eDateEqualTo");
							boolean tempVal3 = dateValidation(tempArrStr1, tempArrStr2, searchDateStr,"dBetween");
							if (tempVal1 == true || tempVal2 == true || tempVal3 == true)
							{
								return true;
							}
							else
							{
								return false;
							}
						}
						else if ((tempgt > templs) && (orOp == 1)&& (equalTo == 2))
						{
						return true;
						}
						else if ((tempgt < templs) && (orOp == 1)&& (equalTo == 2))
						{
							return true;
						}
						
						else
						{
							System.out.println("None of the conditinos are met");
							return false;
						}
					}	
					System.out.println("testing =============");
				}
				// 2 offerStartDate function - End
				
				//	2 offerEndDate function - Start
				if ((mapStrWithKey.size() >= 1) && (offerDateInfo.equalsIgnoreCase("oeDate"))) {
					boolean result1 = false;
					boolean result2 = false;
					
					tempArrStr = null;
					tempArrFnl = null;
					tempArrFnl1 = null;
					tempArrFnl2 = null;
					tempArrStr1 = null;
					tempArrStr2 = null;
					tempArrInt = 0;
					tempDateInt = 0;
					sizeOfTotalVal = mapStrWithKey.size();
					int ofstdate = 0;
					int ofeddate = 0;
					int tempgt = 0;
					int templs = 0;
					// int k=0;
					// System.err.println(sizeOfTotalVal);
					strArr1 = new ArrayList<Integer>();
					strArr2 = new ArrayList<Integer>();
					strArr3 = new ArrayList<Integer>();
					strArr4 = new ArrayList<Integer>();
					int andOR = 0; // if 1 then AND condition if 2 then OR
									// condition
					int gt = 0;
					int gtKey = 0;
					int ls = 0;
					int lsKey = 0;
					int eq = 0;
					int eqKey = 0;
					// int eq1 = 0;
					// int eqKey1 = 0;
					int hy = 0;
					int hyKey = 0;
					int k=0;
					int gteq = 0;
					int lseq = 0;
				
				if (offerEndDate == 2 && lessThan == 1 && greaterThan == 1) {
					
					System.out.println("2 OfferEnd Date(s) + No equal to + One Less than + One Greater than present");
					for (k = 0; sizeOfTotalVal > k; k++) {
						// System.out.print(k);
						tempArrStr = mapStrWithKey.get(k).toString();
						if (tempArrStr.toLowerCase().contains(">".toLowerCase())) {
							tempgt = k;
						}
						if (tempArrStr.toLowerCase().contains("<".toLowerCase())) {
							templs = k;
						}
						if (tempArrStr.toLowerCase().contains("-".toLowerCase())) {
							strArr1.add(k);}
						if (tempArrStr.toLowerCase().contains("=".toLowerCase())) {
							strArr2.add(k);}
						}
					//System.out.println("=============="+ strArr1.get(0).toString());
					//System.out.println("=============="+ strArr1.get(1).toString());
					//System.out.println(	"tempgt " + ofstdate + " templs " + ofeddate + "sizeofAnArray " + strArr1.size());
					tempArrStr1 = mapStrWithKey.get(Integer.parseInt(strArr1.get(0).toString())).toString();
					tempArrStr2 = mapStrWithKey.get(Integer.parseInt(strArr1.get(1).toString())).toString();
										
					
					//System.out.println(tempArrStr1 +" sam "+ tempArrStr2);
					if ((tempgt > templs) && (andOp == 1) && (equalTo == 0))
					{
						boolean tempVal1 = dateValidation(tempArrStr2, tempArrStr1, searchDateStr,"dBetween");
						return tempVal1;
					}
					else if ((tempgt < templs) && (andOp == 1)&& (equalTo == 0))
					{
						boolean tempVal1 = dateValidation(tempArrStr1, tempArrStr2, searchDateStr,"dBetween");
						return tempVal1;
					}
					else if ((tempgt > templs) && (orOp == 1)&& (equalTo == 0))
					{
						return true;
					}
					else if ((tempgt < templs) && (orOp == 1)&& (equalTo == 0))
					{
						return true;
					}
					else if ((tempgt > templs) && (andOp == 1) && (equalTo == 1))
					{
						if(tempgt == Integer.parseInt(strArr2.get(0).toString())-1){gteq=1; System.out.println("Greater than equal to - is present");}
						else if (templs == Integer.parseInt(strArr2.get(0).toString())-1){lseq=1; System.out.println("Less than equal to - is present");}
						if (gteq==1)
						{
						boolean tempVal1 = dateValidation(tempArrStr2, tempArrStr1, searchDateStr,"sDateEqualTo");
						boolean tempVal2 = dateValidation(tempArrStr2, tempArrStr1, searchDateStr,"dBetween");
						if (tempVal1 == true || tempVal2 == true)
						{
							return true;
						}
						else
						{
							return false;
						}
						}
						if (lseq==1)
						{
						boolean tempVal1 = dateValidation(tempArrStr2, tempArrStr1, searchDateStr,"eDateEqualTo");
						boolean tempVal2 = dateValidation(tempArrStr2, tempArrStr1, searchDateStr,"dBetween");
						if (tempVal1 == true || tempVal2 == true)
						{
							return true;
						}
						else
						{
							return false;
						}
						}
					}
					else if ((tempgt < templs) && (andOp == 1) && (equalTo == 1))
					{
						if(tempgt == Integer.parseInt(strArr2.get(0).toString())-1){gteq=1; System.out.println("Greater than equal to - is present");}
						else if (templs == Integer.parseInt(strArr2.get(0).toString())-1){lseq=1; System.out.println("Less than equal to - is present");}
						if (gteq==1)
						{
						boolean tempVal1 = dateValidation(tempArrStr1, tempArrStr2, searchDateStr,"sDateEqualTo");
						boolean tempVal2 = dateValidation(tempArrStr1, tempArrStr2, searchDateStr,"dBetween");
						if (tempVal1 == true || tempVal2 == true)
						{
							return true;
						}
						else
						{
							return false;
						}
						}
						if (lseq==1)
						{
						boolean tempVal1 = dateValidation(tempArrStr1, tempArrStr2, searchDateStr,"eDateEqualTo");
						boolean tempVal2 = dateValidation(tempArrStr1, tempArrStr2, searchDateStr,"dBetween");
						if (tempVal1 == true || tempVal2 == true)
						{
							return true;
						}
						else
						{
							return false;
						}
						}
					}
					else if ((tempgt > templs) && (orOp == 1)&& (equalTo == 1))
					{
						return true;
					}
					else if ((tempgt < templs) && (orOp == 1)&& (equalTo == 1))
					{
						return true;
					}
					else if ((tempgt > templs) && (andOp == 1) && (equalTo == 2))
					{
					boolean tempVal1 = dateValidation(tempArrStr2, tempArrStr1, searchDateStr,"sDateEqualTo");
					boolean tempVal2 = dateValidation(tempArrStr2, tempArrStr1, searchDateStr,"eDateEqualTo");
					boolean tempVal3 = dateValidation(tempArrStr2, tempArrStr1, searchDateStr,"dBetween");
					if (tempVal1 == true || tempVal2 == true || tempVal3 == true)
					{
						return true;
					}
					else
					{
						return false;
					}
					}
					else if ((tempgt < templs) && (andOp == 1)&& (equalTo == 2))
					{
						boolean tempVal1 = dateValidation(tempArrStr1, tempArrStr2, searchDateStr,"sDateEqualTo");
						boolean tempVal2 = dateValidation(tempArrStr1, tempArrStr2, searchDateStr,"eDateEqualTo");
						boolean tempVal3 = dateValidation(tempArrStr1, tempArrStr2, searchDateStr,"dBetween");
						if (tempVal1 == true || tempVal2 == true || tempVal3 == true)
						{
							return true;
						}
						else
						{
							return false;
						}
					}
					else if ((tempgt > templs) && (orOp == 1)&& (equalTo == 2))
					{
					return true;
					}
					else if ((tempgt < templs) && (orOp == 1)&& (equalTo == 2))
					{
						return true;
					}
					
					else
					{
						System.out.println("None of the conditinos are met");
						return false;
					}
				}	
				System.out.println("testing =============");
			}
			// 2 offerStartDate function - End
			}
			// FUNCTION CREATED ON 02/01/2017 -ED

			// Only Date
			if (andOp == 0 && orOp == 0 && curlyOpen == 0 && curlyClose == 0 && squreBraceOpen == 0
					&& squreBraceClose == 0 && to == 0 && star == 0 && greaterThan == 0 && equalTo == 0
					&& lessThan == 0) {
				if (hyphen >= 0 || fSlash >= 0) {
					if (offerStartDate > 0 || offerEndDate > 0) {
						if (mapStrWithKey.size() == 1) {
							System.err.println("Only Date Present");
							String tempStr = mapStrWithKey.get(0).toString();
							String finalDate = null;
							// System.err.println(tempStr);
							if (offerDateInfo.equalsIgnoreCase("osDate")) {
								if (offerStartDate > 0) {
									finalDate = tempStr.replace("offerStartDate", "").replace(":", "").replace(";", "");
									// finalDate = tempStr.replace(":","");
									System.err.println(finalDate);
									// Date tempDate=sdf.parse(finalDate);
									// System.err.println(tempDate);
									boolean tempVal1 = dateValidation(finalDate, finalDate, searchDateStr,
											"sDateEqualTo");
									return tempVal1;
								} else {
									return false;
								}
								// System.out.println(tempStr.split(":"));
							}
							if (offerDateInfo.equalsIgnoreCase("oeDate")) {
								if (offerEndDate > 0) {
									finalDate = tempStr.replace("offerEndDate", "").replace(":", "").replace(";", "");
									// finalDate = tempStr.replace(":","");
									System.err.println(finalDate);
									// Date tempDate=sdf.parse(finalDate);
									// System.err.println(tempDate);
									boolean tempVal1 = dateValidation(finalDate, finalDate, searchDateStr,
											"eDateEqualTo");
									return tempVal1;
								} else {
									return false;
								}
							}
						}
					}
				}
			}
			// Greater than Validation
			if (andOp == 0 && orOp == 0 && curlyOpen == 0 && curlyClose == 0 && squreBraceOpen == 0
					&& squreBraceClose == 0 && to == 0 && star == 0 && equalTo == 0 && lessThan == 0) {
				if (hyphen >= 0 || fSlash >= 0) {
					if (offerStartDate > 0 || offerEndDate > 0) {
						// Offer Start Date Validation for > - Start
						if ((mapStrWithKey.size() >= 1) && (offerDateInfo.equalsIgnoreCase("osDate"))) {
							if (greaterThan >= 1) {
								System.err.println("Only one Greater than symbol Present");

								tempArrStr = null;
								tempArrFnl = null;
								tempArrInt = 0;
								tempDateInt = 0;
								sizeOfTotalVal = mapStrWithKey.size();
								strArr1 = new ArrayList<Integer>();
								strArr2 = new ArrayList<Integer>();

								for (int k = 0; sizeOfTotalVal > k; k++) {
									// System.out.print(k);
									tempArrStr = mapStrWithKey.get(k).toString();
									if (tempArrStr.toLowerCase().contains("offerEndDate".toLowerCase())) {
										return false;
									}
									tempArrInt = k;
									// System.out.println(tempStr);
									if (tempArrStr.equalsIgnoreCase(">")) {
										strArr1.add(tempArrInt);
										// System.out.println(tempArrInt);
									}
									if (tempArrStr.contains("-")) {
										strArr1.add(tempArrInt);
										tempDateInt = tempArrInt;
									}
								}
								if (strArr1.size() >= 1) {
									tempArrFnl = mapStrWithKey.get(tempDateInt).toString();
									// System.out.println(tempArrFnl);
									Date date = new Date();
									// System.out.println(sdf.format(date));
									//String eDate = sdf.format(date);
									String eDate = futrDate;
									String sDate = tempArrFnl;
									boolean tempVal1 = dateValidation(sDate, eDate, searchDateStr, "sDateEqualTo");
									boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");
									boolean tempVal3 = dateValidation(sDate, eDate, searchDateStr, "eDateEqualTo");

									if (tempVal1 == false && (tempVal2 == true || tempVal3 == true)) {
										System.out.println("Success");
										return true;
									}

								} else {
									return false;
								}
							}
						}
						// Offer Start Date Validation for > - End

						// Offer End Date Validation for > - Start
						if ((mapStrWithKey.size() >= 1) && (offerDateInfo.equalsIgnoreCase("oeDate"))) {
							if (greaterThan >= 1) {
								System.err.println("Only one Greater than symbol Present");

								tempArrStr = null;
								tempArrFnl = null;
								tempArrInt = 0;
								tempDateInt = 0;
								sizeOfTotalVal = mapStrWithKey.size();
								// System.err.println(sizeOfTotalVal);
								strArr1 = new ArrayList<Integer>();
								strArr2 = new ArrayList<Integer>();

								for (int k = 0; sizeOfTotalVal > k; k++) {
									// System.out.print(k);
									tempArrStr = mapStrWithKey.get(k).toString();
									if (tempArrStr.toLowerCase().contains("offerStartDate".toLowerCase())) {
										return false;
									}
									tempArrInt = k;
									// System.out.println(tempStr);
									if (tempArrStr.equalsIgnoreCase(">")) {
										strArr1.add(tempArrInt);
										// System.out.println(tempArrInt);
									}
									if (tempArrStr.contains("-")) {
										strArr1.add(tempArrInt);
										tempDateInt = tempArrInt;
									}
								}
								if (strArr1.size() >= 1) {
									tempArrFnl1 = mapStrWithKey.get(tempDateInt).toString();
									// System.out.println(tempArrFnl);
									Date date = new Date();
									// System.out.println(sdf.format(date));
									//String eDate = sdf.format(date);
									String eDate = futrDate;
									String sDate = tempArrFnl1;
									boolean tempVal1 = dateValidation(sDate, eDate, searchDateStr, "sDateEqualTo");
									boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");
									boolean tempVal3 = dateValidation(sDate, eDate, searchDateStr, "eDateEqualTo");

									if (tempVal1 == false && (tempVal2 == true || tempVal3 == true)) {
										System.out.println("Success");
										return true;
									}

								} else {
									return false;
								}
							}
						}
						// Offer End Date Validation for > - End
					}
				}
			}

			// less than Validation
			if (andOp == 0 && orOp == 0 && curlyOpen == 0 && curlyClose == 0 && squreBraceOpen == 0
					&& squreBraceClose == 0 && to == 0 && star == 0 && equalTo == 0 && greaterThan == 0) {
				if (hyphen >= 0 || fSlash >= 0) {
					if (offerStartDate > 0 || offerEndDate > 0) {
						// Offer Start Date Validation for < - Start
						if ((mapStrWithKey.size() >= 1) && (offerDateInfo.equalsIgnoreCase("osDate"))) {
							if (lessThan == 1) {
								System.err.println("Only one less than symbol Present");

								tempArrStr = null;
								tempArrFnl = null;
								tempArrInt = 0;
								tempDateInt = 0;
								sizeOfTotalVal = mapStrWithKey.size();
								// System.err.println(sizeOfTotalVal);
								strArr1 = new ArrayList<Integer>();

								for (int k = 0; sizeOfTotalVal > k; k++) {
									// System.out.print(k);
									tempArrStr = mapStrWithKey.get(k).toString();
									if (tempArrStr.toLowerCase().contains("offerEndDate".toLowerCase())) {
										return false;
									}
									tempArrInt = k;
									// System.out.println(tempStr);
									if (tempArrStr.equalsIgnoreCase("<")) {
										strArr1.add(tempArrInt);
										// System.out.println(tempArrInt);
									}
									if (tempArrStr.contains("-")) {
										strArr1.add(tempArrInt);
										tempDateInt = tempArrInt;
									}
								}
								if (strArr1.size() >= 1) {
									tempArrFnl = mapStrWithKey.get(tempDateInt).toString();
									// System.out.println(tempArrFnl);
									Date date = new Date();
									// System.out.println(sdf.format(date));
									String sDate = oldDate;
									String eDate = tempArrFnl;
									boolean tempVal1 = dateValidation(sDate, eDate, searchDateStr, "eDateEqualTo");
									boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");
									/*
									 * boolean tempVal3 = dateValidation(sDate,
									 * eDate, searchDateStr, "eDateEqualTo");
									 */
									if ((tempVal1 == false) && (tempVal2 == true)) {
										System.out.println("Success");
										return true;
									}

								} else {
									return false;
								}
							}
						}
						// Offer Start Date Validation for < - End

						// Offer End Date Validation for < - Start
						if ((mapStrWithKey.size() >= 1) && (offerDateInfo.equalsIgnoreCase("oeDate"))) {
							if (lessThan == 1) {
								System.err.println("Only one less than symbol Present");

								tempArrStr = null;
								tempArrFnl = null;
								tempArrInt = 0;
								tempDateInt = 0;
								sizeOfTotalVal = mapStrWithKey.size();
								// System.err.println(sizeOfTotalVal);
								strArr1 = new ArrayList<Integer>();
								// ArrayList<Integer> strArr2 = new
								// ArrayList<Integer>();

								for (int k = 0; sizeOfTotalVal > k; k++) {
									// System.out.print(k);
									tempArrStr = mapStrWithKey.get(k).toString();
									if (tempArrStr.toLowerCase().contains("offerStartDate".toLowerCase())) {
										return false;
									}
									tempArrInt = k;
									// System.out.println(tempStr);
									if (tempArrStr.equalsIgnoreCase("<")) {
										strArr1.add(tempArrInt);
										// System.out.println(tempArrInt);
									}
									if (tempArrStr.contains("-")) {
										strArr1.add(tempArrInt);
										tempDateInt = tempArrInt;
									}
								}
								if (strArr1.size() >= 1) {
									tempArrFnl = mapStrWithKey.get(tempDateInt).toString();
									// System.out.println(tempArrFnl);
									Date date = new Date();
									// System.out.println(sdf.format(date));
									String sDate = oldDate;
									String eDate = tempArrFnl;
									boolean tempVal1 = dateValidation(sDate, eDate, searchDateStr, "eDateEqualTo");
									boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");
									/*
									 * boolean tempVal3 = dateValidation(sDate,
									 * eDate, searchDateStr, "eDateEqualTo");
									 */
									if ((tempVal1 == false) && (tempVal2 == true)) {
										System.out.println("Success");
										return true;
									}

								} else {
									return false;
								}
							}
						}
						// Offer End Date Validation for < - End
					}
				}
			}
			// less than = Validation
			if (andOp == 0 && orOp == 0 && curlyOpen == 0 && curlyClose == 0 && squreBraceOpen == 0
					&& squreBraceClose == 0 && to == 0 && star == 0 && greaterThan == 0) {
				if (hyphen >= 0 || fSlash >= 0) {
					if (offerStartDate > 0 || offerEndDate > 0) {
						// Offer Start Date Validation for < = Start
						if ((mapStrWithKey.size() >= 1) && (offerDateInfo.equalsIgnoreCase("osDate"))) {
							if (lessThan == 1 && equalTo == 1) {
								System.err.println("Only one less than symbol Present");

								tempArrStr = null;
								tempArrFnl = null;
								tempArrInt = 0;
								tempDateInt = 0;
								sizeOfTotalVal = mapStrWithKey.size();
								// System.err.println(sizeOfTotalVal);
								strArr1 = new ArrayList<Integer>();
								// ArrayList<Integer> strArr2 = new
								// ArrayList<Integer>();

								for (int k = 0; sizeOfTotalVal > k; k++) {
									// System.out.print(k);
									tempArrStr = mapStrWithKey.get(k).toString();
									if (tempArrStr.toLowerCase().contains("offerEndDate".toLowerCase())) {
										return false;
									}
									tempArrInt = k;
									// System.out.println(tempStr);
									if (tempArrStr.equalsIgnoreCase("<")) {
										strArr1.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt + 1).toString();
										if (extraVal.contains("=")) {
											System.out.println("The = is found");
										} else {
											return false;
										}
										// System.out.println(tempArrInt);
									}
									if (tempArrStr.contains("-")) {
										strArr1.add(tempArrInt);
										tempDateInt = tempArrInt;
									}
								}
								if (strArr1.size() >= 1) {
									tempArrFnl = mapStrWithKey.get(tempDateInt).toString();
									// System.out.println(tempArrFnl);
									Date date = new Date();
									// System.out.println(sdf.format(date));
									String sDate = oldDate;
									String eDate = tempArrFnl;
									boolean tempVal1 = dateValidation(sDate, eDate, searchDateStr, "eDateEqualTo");
									boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");
									/*
									 * boolean tempVal3 = dateValidation(sDate,
									 * eDate, searchDateStr, "eDateEqualTo");
									 */
									if ((tempVal1 == true) || (tempVal2 == true)) {
										System.out.println("Success");
										return true;
									}

								} else {
									return false;
								}
							}
						}
						// Offer Start Date Validation for <= - End

						// Offer End Date Validation for < = Start
						if ((mapStrWithKey.size() >= 1) && (offerDateInfo.equalsIgnoreCase("oeDate"))) {
							if (lessThan == 1 && equalTo == 1) {
								System.err.println("Only one less than symbol Present");

								tempArrStr = null;
								tempArrFnl = null;
								tempArrInt = 0;
								tempDateInt = 0;
								sizeOfTotalVal = mapStrWithKey.size();
								// System.err.println(sizeOfTotalVal);
								strArr1 = new ArrayList<Integer>();
								// ArrayList<Integer> strArr2 = new
								// ArrayList<Integer>();

								for (int k = 0; sizeOfTotalVal > k; k++) {
									// System.out.print(k);
									tempArrStr = mapStrWithKey.get(k).toString();
									if (tempArrStr.toLowerCase().contains("offerStartDate".toLowerCase())) {
										return false;
									}
									tempArrInt = k;
									// System.out.println(tempStr);
									if (tempArrStr.equalsIgnoreCase("<")) {
										strArr1.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt + 1).toString();
										if (extraVal.contains("=")) {
											System.out.println("The = is found");
										} else {
											return false;
										}
										// System.out.println(tempArrInt);
									}
									if (tempArrStr.contains("-")) {
										strArr1.add(tempArrInt);
										tempDateInt = tempArrInt;
									}
								}
								if (strArr1.size() >= 1) {
									tempArrFnl = mapStrWithKey.get(tempDateInt).toString();
									// System.out.println(tempArrFnl);
									Date date = new Date();
									// System.out.println(sdf.format(date));
									String sDate = oldDate;
									String eDate = tempArrFnl;
									boolean tempVal1 = dateValidation(sDate, eDate, searchDateStr, "eDateEqualTo");
									boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");
									/*
									 * boolean tempVal3 = dateValidation(sDate,
									 * eDate, searchDateStr, "eDateEqualTo");
									 */
									if ((tempVal1 == true) || (tempVal2 == true)) {
										System.out.println("Success");
										return true;
									}

								} else {
									return false;
								}
							}
						}
						// Offer Start Date Validation for <= - End

					}
				}
			}
			// End
			// Greater than = Validation
			if (andOp == 0 && orOp == 0 && curlyOpen == 0 && curlyClose == 0 && squreBraceOpen == 0
					&& squreBraceClose == 0 && to == 0 && star == 0 && lessThan == 0) {
				if (hyphen >= 0 || fSlash >= 0) {
					if (offerStartDate > 0 || offerEndDate > 0) {
						// Offer Start Date Validation for < = Start
						if ((mapStrWithKey.size() >= 1) && (offerDateInfo.equalsIgnoreCase("osDate"))) {
							if (greaterThan == 1 && equalTo == 1) {
								System.err.println("Only one greaterThan symbol Present");

								tempArrStr = null;
								tempArrFnl = null;
								tempArrInt = 0;
								tempDateInt = 0;
								sizeOfTotalVal = mapStrWithKey.size();
								// System.err.println(sizeOfTotalVal);
								strArr1 = new ArrayList<Integer>();
								// ArrayList<Integer> strArr2 = new
								// ArrayList<Integer>();

								for (int k = 0; sizeOfTotalVal > k; k++) {
									// System.out.print(k);
									tempArrStr = mapStrWithKey.get(k).toString();
									if (tempArrStr.toLowerCase().contains("offerEndDate".toLowerCase())) {
										return false;
									}
									tempArrInt = k;
									// System.out.println(tempStr);
									if (tempArrStr.equalsIgnoreCase(">")) {
										strArr1.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt + 1).toString();
										if (extraVal.contains("=")) {
											System.out.println("The = is found");
										} else {
											return false;
										}
										// System.out.println(tempArrInt);
									}
									if (tempArrStr.contains("-")) {
										strArr1.add(tempArrInt);
										tempDateInt = tempArrInt;
									}
								}
								if (strArr1.size() >= 1) {
									tempArrFnl = mapStrWithKey.get(tempDateInt).toString();
									// System.out.println(tempArrFnl);
									Date date = new Date();
									// System.out.println(sdf.format(date));
									String sDate = tempArrFnl;
									// String eDate = sdf.format(date);
									String eDate = futrDate;
									boolean tempVal1 = dateValidation(sDate, eDate, searchDateStr, "sDateEqualTo");
									boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");
									/*
									 * boolean tempVal3 = dateValidation(sDate,
									 * eDate, searchDateStr, "eDateEqualTo");
									 */
									if ((tempVal1 == true) || (tempVal2 == true)) {
										System.out.println("Success");
										return true;
									}

								} else {
									return false;
								}
							}
						}
						// Offer Start Date Validation for >= End

						// Offer End Date Validation for > = Start
						if ((mapStrWithKey.size() >= 1) && (offerDateInfo.equalsIgnoreCase("oeDate"))) {
							if (greaterThan == 1 && equalTo == 1) {
								System.err.println("Only one greaterThan symbol Present");

								tempArrStr = null;
								tempArrFnl = null;
								tempArrInt = 0;
								tempDateInt = 0;
								sizeOfTotalVal = mapStrWithKey.size();
								// System.err.println(sizeOfTotalVal);
								strArr1 = new ArrayList<Integer>();
								// ArrayList<Integer> strArr2 = new
								// ArrayList<Integer>();

								for (int k = 0; sizeOfTotalVal > k; k++) {
									// System.out.print(k);
									tempArrStr = mapStrWithKey.get(k).toString();
									if (tempArrStr.toLowerCase().contains("offerStartDate".toLowerCase())) {
										return false;
									}
									tempArrInt = k;
									// System.out.println(tempStr);
									if (tempArrStr.equalsIgnoreCase(">")) {
										strArr1.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt + 1).toString();
										if (extraVal.contains("=")) {
											System.out.println("The = is found");
										} else {
											return false;
										}
										// System.out.println(tempArrInt);
									}
									if (tempArrStr.contains("-")) {
										strArr1.add(tempArrInt);
										tempDateInt = tempArrInt;
									}
								}
								if (strArr1.size() >= 1) {
									tempArrFnl = mapStrWithKey.get(tempDateInt).toString();
									// System.out.println(tempArrFnl);
									Date date = new Date();
									// System.out.println(sdf.format(date));
									String sDate = tempArrFnl;
									String eDate = futrDate;
									// String eDate = sdf.format(date);
									boolean tempVal1 = dateValidation(sDate, eDate, searchDateStr, "sDateEqualTo");
									boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");
									/*
									 * boolean tempVal3 = dateValidation(sDate,
									 * eDate, searchDateStr, "eDateEqualTo");
									 */
									if ((tempVal1 == true) || (tempVal2 == true)) {
										System.out.println("Success");
										return true;
									}

								} else {
									return false;
								}
							}
						}
						// Offer End Date Validation for >= - End

					}
				}
			}
			// End
			// [sdate TO eDate]
			if (andOp == 0 && orOp == 0 && curlyOpen == 0 && curlyClose == 0 && star == 0 && lessThan == 0
					&& greaterThan == 0 && equalTo == 0) {
				if (hyphen >= 0 || fSlash >= 0) {
					if (offerStartDate > 0 || offerEndDate > 0) {
						// Offer Start Date Validation for [Start to End]
						if ((mapStrWithKey.size() >= 1) && (offerDateInfo.equalsIgnoreCase("osDate"))) {
							if (squreBraceOpen == 1 && squreBraceClose == 1 && to == 1) {
								System.err.println("[] & TO symbol(s) Present");
								tempArrStr = null;
								tempArrFnl = null;
								tempArrFnl1 = null;
								tempArrFnl2 = null;
								tempArrInt = 0;
								tempDateInt = 0;
								sizeOfTotalVal = mapStrWithKey.size();
								// System.err.println(sizeOfTotalVal);
								strArr1 = new ArrayList<Integer>();
								strArr2 = new ArrayList<Integer>();
								strArr3 = new ArrayList<Integer>();

								for (int k = 0; sizeOfTotalVal > k; k++) {
									// System.out.print(k);
									tempArrStr = mapStrWithKey.get(k).toString();
									if (tempArrStr.toLowerCase().contains("offerEndDate".toLowerCase())) {
										return false;
									}
									tempArrInt = k;
									// System.out.println(tempStr);
									if (tempArrStr.equalsIgnoreCase("[")) {
										strArr1.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt).toString();
										// if(extraVal.contains("To")){System.out.println("The
										// = is found");}
										// else{return false;}
										// System.out.println(tempArrInt);
									}
									if (tempArrStr.equalsIgnoreCase("]")) {
										strArr2.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt).toString();
										// if(extraVal.contains("To")){System.out.println("The
										// = is found");}
										// else{return false;}
										// System.out.println(tempArrInt);
									}

									if (tempArrStr.contains("-")) {
										strArr2.add(tempArrInt);
										tempDateInt = tempArrInt;
									}
								}
								int z = findElementPlaceR("[");
								int y = findElementPlaceR("]");
								if (y - z >= 2) {
									System.out.println("[] Verification Done");
								}

								if (strArr1.size() >= 1) {
									tempArrFnl1 = mapStrWithKey.get(z + 1).toString();
									tempArrFnl2 = mapStrWithKey.get(y - 1).toString();
									// System.err.println(tempArrFnl1
									// +" "+tempArrFnl2);
									// System.out.println(tempArrFnl);
									// Date date = new Date();
									// System.out.println(sdf.format(date));

									String sDate = tempArrFnl1;
									String eDate = tempArrFnl2;
									// String eDate = sdf.format(date);
									boolean tempVal1 = dateValidation(sDate, eDate, searchDateStr, "sDateEqualTo");
									boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");

									boolean tempVal3 = dateValidation(sDate, eDate, searchDateStr, "eDateEqualTo");

									if ((tempVal1 == true) || (tempVal2 == true) || tempVal3 == true) {
										System.out.println("Success");
										return true;
									}

								} else {
									return false;
								}
							}
						}
						// Offer Start Date Validation for [Start to End]

						// Offer end Date Validation for [Start to End]
						if ((mapStrWithKey.size() >= 1) && (offerDateInfo.equalsIgnoreCase("oeDate"))) {
							if (squreBraceOpen == 1 && squreBraceClose == 1 && to == 1) {
								System.err.println("[] & TO symbol(s) Present");
								tempArrStr = null;
								tempArrFnl = null;
								tempArrFnl1 = null;
								tempArrFnl2 = null;
								tempArrInt = 0;
								tempDateInt = 0;
								sizeOfTotalVal = mapStrWithKey.size();
								// System.err.println(sizeOfTotalVal);
								strArr1 = new ArrayList<Integer>();
								strArr2 = new ArrayList<Integer>();
								strArr3 = new ArrayList<Integer>();

								for (int k = 0; sizeOfTotalVal > k; k++) {
									// System.out.print(k);
									tempArrStr = mapStrWithKey.get(k).toString();
									if (tempArrStr.toLowerCase().contains("offerStartDate".toLowerCase())) {
										return false;
									}
									tempArrInt = k;
									// System.out.println(tempStr);
									if (tempArrStr.equalsIgnoreCase("[")) {
										strArr1.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt).toString();
										// if(extraVal.contains("To")){System.out.println("The
										// = is found");}
										// else{return false;}
										// System.out.println(tempArrInt);
									}
									if (tempArrStr.equalsIgnoreCase("]")) {
										strArr2.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt).toString();
										// if(extraVal.contains("To")){System.out.println("The
										// = is found");}
										// else{return false;}
										// System.out.println(tempArrInt);
									}

									if (tempArrStr.contains("-")) {
										strArr2.add(tempArrInt);
										tempDateInt = tempArrInt;
									}
								}
								int z = findElementPlaceR("[");
								int y = findElementPlaceR("]");
								if (y - z >= 2) {
									System.out.println("[] Verification Done");
								}

								if (strArr1.size() >= 1) {
									tempArrFnl1 = mapStrWithKey.get(z + 1).toString();
									tempArrFnl2 = mapStrWithKey.get(y - 1).toString();
									// System.err.println(tempArrFnl1
									// +" "+tempArrFnl2);
									// System.out.println(tempArrFnl);
									// Date date = new Date();
									// System.out.println(sdf.format(date));

									String sDate = tempArrFnl1;
									String eDate = tempArrFnl2;
									// String eDate = sdf.format(date);
									boolean tempVal1 = dateValidation(sDate, eDate, searchDateStr, "sDateEqualTo");
									boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");

									boolean tempVal3 = dateValidation(sDate, eDate, searchDateStr, "eDateEqualTo");

									if ((tempVal1 == true) || (tempVal2 == true) || tempVal3 == true) {
										System.out.println("Success");
										return true;
									}

								} else {
									return false;
								}
							}
						}
						// Offer Start Date Validation for [Start to End]

					}
				}
			}
			// End

			/*
			 * 
			 * NEWLY ADDED FUNCTION BY ANTONY ON 01/30/2017 - ST
			 * 
			 */

			// {sdate TO eDate}
			if (andOp == 0 && orOp == 0 && squreBraceOpen == 0 && squreBraceClose == 0 && star == 0 && lessThan == 0
					&& greaterThan == 0 && equalTo == 0) {
				if (hyphen >= 0 || fSlash >= 0) {
					if (offerStartDate > 0 || offerEndDate > 0) {
						// Offer Start Date Validation for [Start to End]
						if ((mapStrWithKey.size() >= 1) && (offerDateInfo.equalsIgnoreCase("osDate"))) {
							if (curlyOpen == 1 && curlyClose == 1 && to == 1) {
								System.err.println("{} & TO symbol(s) Present");
								tempArrStr = null;
								tempArrFnl = null;
								tempArrFnl1 = null;
								tempArrFnl2 = null;
								tempArrInt = 0;
								tempDateInt = 0;
								sizeOfTotalVal = mapStrWithKey.size();
								// System.err.println(sizeOfTotalVal);
								strArr1 = new ArrayList<Integer>();
								strArr2 = new ArrayList<Integer>();
								strArr3 = new ArrayList<Integer>();

								for (int k = 0; sizeOfTotalVal > k; k++) {
									// System.out.print(k);
									tempArrStr = mapStrWithKey.get(k).toString();
									if (tempArrStr.toLowerCase().contains("offerEndDate".toLowerCase())) {
										return false;
									}
									tempArrInt = k;
									// System.out.println(tempStr);

									if (tempArrStr.equalsIgnoreCase("{")) {
										strArr1.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt).toString();
										// if(extraVal.contains("To")){System.out.println("The
										// = is found");}
										// else{return false;}
										// System.out.println(tempArrInt);
									}
									if (tempArrStr.equalsIgnoreCase("}")) {
										strArr2.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt).toString();
										// if(extraVal.contains("To")){System.out.println("The
										// = is found");}
										// else{return false;}
										// System.out.println(tempArrInt);
									}

									if (tempArrStr.contains("-")) {
										strArr2.add(tempArrInt);
										tempDateInt = tempArrInt;
									}
								}
								int z = findElementPlaceR("{");
								int y = findElementPlaceR("}");
								if (y - z >= 2) {
									System.out.println("{} Verification Done");
								}

								if (strArr1.size() >= 1) {
									tempArrFnl1 = mapStrWithKey.get(z + 1).toString();
									tempArrFnl2 = mapStrWithKey.get(y - 1).toString();
									System.err.println(tempArrFnl1 + " " + tempArrFnl2);
									// System.out.println(tempArrFnl);
									// Date date = new Date();
									// System.out.println(sdf.format(date));

									String sDate = tempArrFnl1;
									String eDate = tempArrFnl2;
									// String eDate = sdf.format(date);
									boolean tempVal1 = dateValidation(sDate, sDate, searchDateStr, "sDateEqualTo");
									/*
									 * boolean tempVal2 = dateValidation(eDate,
									 * eDate, searchDateStr, "dBetween");
									 */

									boolean tempVal3 = dateValidation(eDate, eDate, searchDateStr, "eDateEqualTo");

									if ((tempVal1 == false) && tempVal3 == false) {
										System.out.println("Success");
										return true;
									}

								} else {
									return false;
								}
							}
						}
						// Offer start Date Validation for {Start to End}

						// Offer end Date Validation for {Start to End}
						if ((mapStrWithKey.size() >= 1) && (offerDateInfo.equalsIgnoreCase("oeDate"))) {
							if (curlyOpen == 1 && curlyClose == 1 && to == 1) {
								System.err.println("{} & TO symbol(s) Present");
								tempArrStr = null;
								tempArrFnl = null;
								tempArrFnl1 = null;
								tempArrFnl2 = null;
								tempArrInt = 0;
								tempDateInt = 0;
								sizeOfTotalVal = mapStrWithKey.size();
								// System.err.println(sizeOfTotalVal);
								strArr1 = new ArrayList<Integer>();
								strArr2 = new ArrayList<Integer>();
								strArr3 = new ArrayList<Integer>();

								for (int k = 0; sizeOfTotalVal > k; k++) {
									// System.out.print(k);
									tempArrStr = mapStrWithKey.get(k).toString();
									if (tempArrStr.toLowerCase().contains("offerStartDate".toLowerCase())) {
										return false;
									}
									tempArrInt = k;
									// System.out.println(tempStr);

									if (tempArrStr.equalsIgnoreCase("{")) {
										strArr1.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt).toString();
										// if(extraVal.contains("To")){System.out.println("The
										// = is found");}
										// else{return false;}
										// System.out.println(tempArrInt);
									}
									if (tempArrStr.equalsIgnoreCase("}")) {
										strArr2.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt).toString();
										// if(extraVal.contains("To")){System.out.println("The
										// = is found");}
										// else{return false;}
										// System.out.println(tempArrInt);
									}

									if (tempArrStr.contains("-")) {
										strArr2.add(tempArrInt);
										tempDateInt = tempArrInt;
									}
								}
								int z = findElementPlaceR("{");
								int y = findElementPlaceR("}");
								if (y - z >= 2) {
									System.out.println("{} Verification Done");
								}

								if (strArr1.size() >= 1) {
									tempArrFnl1 = mapStrWithKey.get(z + 1).toString();
									tempArrFnl2 = mapStrWithKey.get(y - 1).toString();
									// System.err.println(tempArrFnl1
									// +" "+tempArrFnl2);
									// System.out.println(tempArrFnl);
									// Date date = new Date();
									// System.out.println(sdf.format(date));

									String sDate = tempArrFnl1;
									String eDate = tempArrFnl2;
									// String eDate = sdf.format(date);
									boolean tempVal1 = dateValidation(sDate, sDate, searchDateStr, "sDateEqualTo");
									/*
									 * boolean tempVal2 = dateValidation(eDate,
									 * eDate, searchDateStr, "dBetween");
									 */

									boolean tempVal3 = dateValidation(eDate, eDate, searchDateStr, "eDateEqualTo");

									if ((tempVal1 == false) && tempVal3 == false) {
										System.out.println("Success");
										return true;

									} else {
										return false;
									}
								}

							}

						}
						// Offer end Date Validation for {Start to End}

					}
				}
			}
			// End

			// [sdate TO eDate}
			if (andOp == 0 && orOp == 0 && curlyOpen == 0 && squreBraceClose == 0 && star == 0 && lessThan == 0
					&& greaterThan == 0 && equalTo == 0) {
				if (hyphen >= 0 || fSlash >= 0) {
					if (offerStartDate > 0 || offerEndDate > 0) {
						// Offer Start Date Validation for [Start to End]
						if ((mapStrWithKey.size() >= 1) && (offerDateInfo.equalsIgnoreCase("osDate"))) {
							if (curlyClose == 1 && squreBraceOpen == 1 && to == 1) {
								System.err.println("[} & TO symbol(s) Present");
								tempArrStr = null;
								tempArrFnl = null;
								tempArrFnl1 = null;
								tempArrFnl2 = null;
								tempArrInt = 0;
								tempDateInt = 0;
								sizeOfTotalVal = mapStrWithKey.size();
								// System.err.println(sizeOfTotalVal);
								strArr1 = new ArrayList<Integer>();
								strArr2 = new ArrayList<Integer>();
								strArr3 = new ArrayList<Integer>();

								for (int k = 0; sizeOfTotalVal > k; k++) {
									// System.out.print(k);
									tempArrStr = mapStrWithKey.get(k).toString();
									if (tempArrStr.toLowerCase().contains("offerEndDate".toLowerCase())) {
										return false;
									}
									tempArrInt = k;
									// System.out.println(tempStr);

									if (tempArrStr.equalsIgnoreCase("[")) {
										strArr1.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt).toString();
										// if(extraVal.contains("To")){System.out.println("The
										// = is found");}
										// else{return false;}
										// System.out.println(tempArrInt);
									}
									if (tempArrStr.equalsIgnoreCase("}")) {
										strArr2.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt).toString();
										// if(extraVal.contains("To")){System.out.println("The
										// = is found");}
										// else{return false;}
										// System.out.println(tempArrInt);
									}

									if (tempArrStr.contains("-")) {
										strArr2.add(tempArrInt);
										tempDateInt = tempArrInt;
									}
								}
								int z = findElementPlaceR("[");
								int y = findElementPlaceR("}");
								if (y - z >= 2) {
									System.out.println("[} Verification Done");
								}

								if (strArr1.size() >= 1) {
									tempArrFnl1 = mapStrWithKey.get(z + 1).toString();
									tempArrFnl2 = mapStrWithKey.get(y - 1).toString();
									System.err.println(tempArrFnl1 + " " + tempArrFnl2);
									// System.out.println(tempArrFnl);
									// Date date = new Date();
									// System.out.println(sdf.format(date));

									String sDate = tempArrFnl1;
									String eDate = tempArrFnl2;
									// String eDate = sdf.format(date);
									boolean tempVal1 = dateValidation(sDate, sDate, searchDateStr, "sDateEqualTo");
									boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");

									/*
									 * boolean tempVal3 = dateValidation(eDate,
									 * eDate, searchDateStr, "eDateEqualTo");
									 */
									if ((tempVal1 == true) || tempVal2 == true) {
										System.out.println("Success");
										return true;
									}

								} else {
									return false;
								}
							}
						}
						// Offer start Date Validation for [Start to End}

						// Offer end Date Validation for [Start to End}
						if ((mapStrWithKey.size() >= 1) && (offerDateInfo.equalsIgnoreCase("oeDate"))) {
							if (curlyClose == 1 && squreBraceOpen == 1 && to == 1) {
								System.err.println("[} & TO symbol(s) Present");
								tempArrStr = null;
								tempArrFnl = null;
								tempArrFnl1 = null;
								tempArrFnl2 = null;
								tempArrInt = 0;
								tempDateInt = 0;
								sizeOfTotalVal = mapStrWithKey.size();
								// System.err.println(sizeOfTotalVal);
								strArr1 = new ArrayList<Integer>();
								strArr2 = new ArrayList<Integer>();
								strArr3 = new ArrayList<Integer>();

								for (int k = 0; sizeOfTotalVal > k; k++) {
									// System.out.print(k);
									tempArrStr = mapStrWithKey.get(k).toString();
									if (tempArrStr.toLowerCase().contains("offerStartDate".toLowerCase())) {
										return false;
									}
									tempArrInt = k;
									// System.out.println(tempStr);

									if (tempArrStr.equalsIgnoreCase("[")) {
										strArr1.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt).toString();
										// if(extraVal.contains("To")){System.out.println("The
										// = is found");}
										// else{return false;}
										// System.out.println(tempArrInt);
									}
									if (tempArrStr.equalsIgnoreCase("}")) {
										strArr2.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt).toString();
										// if(extraVal.contains("To")){System.out.println("The
										// = is found");}
										// else{return false;}
										// System.out.println(tempArrInt);
									}

									if (tempArrStr.contains("-")) {
										strArr2.add(tempArrInt);
										tempDateInt = tempArrInt;
									}
								}
								int z = findElementPlaceR("[");
								int y = findElementPlaceR("}");
								if (y - z >= 2) {
									System.out.println("[} Verification Done");
								}

								if (strArr1.size() >= 1) {
									tempArrFnl1 = mapStrWithKey.get(z + 1).toString();
									tempArrFnl2 = mapStrWithKey.get(y - 1).toString();
									// System.err.println(tempArrFnl1 +"
									// "+tempArrFnl2);
									// System.out.println(tempArrFnl);
									// Date date = new Date();
									// System.out.println(sdf.format(date));

									String sDate = tempArrFnl1;
									String eDate = tempArrFnl2;
									// String eDate = sdf.format(date);
									boolean tempVal1 = dateValidation(sDate, sDate, searchDateStr, "sDateEqualTo");
									boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");

									/*
									 * boolean tempVal3 = dateValidation(eDate,
									 * eDate, searchDateStr, "eDateEqualTo");
									 */
									if ((tempVal1 == true) || tempVal2 == true) {
										System.out.println("Success");
										return true;

									} else {
										return false;
									}
								}

							}

						}
						// Offer end Date Validation for [Start to End}
					}
				}
			}
			// End

			// {sdate TO eDate]
			if (andOp == 0 && orOp == 0 && curlyClose == 0 && squreBraceOpen == 0 && star == 0 && lessThan == 0
					&& greaterThan == 0 && equalTo == 0) {
				if (hyphen >= 0 || fSlash >= 0) {
					if (offerStartDate > 0 || offerEndDate > 0) {
						// Offer Start Date Validation for {Start to End]
						if ((mapStrWithKey.size() >= 1) && (offerDateInfo.equalsIgnoreCase("osDate"))) {
							if (curlyOpen == 1 && squreBraceClose == 1 && to == 1) {
								System.err.println("{] & TO symbol(s) Present");
								tempArrStr = null;
								tempArrFnl = null;
								tempArrFnl1 = null;
								tempArrFnl2 = null;
								tempArrInt = 0;
								tempDateInt = 0;
								sizeOfTotalVal = mapStrWithKey.size();
								// System.err.println(sizeOfTotalVal);
								strArr1 = new ArrayList<Integer>();
								strArr2 = new ArrayList<Integer>();
								strArr3 = new ArrayList<Integer>();

								for (int k = 0; sizeOfTotalVal > k; k++) {
									// System.out.print(k);
									tempArrStr = mapStrWithKey.get(k).toString();
									if (tempArrStr.toLowerCase().contains("offerEndDate".toLowerCase())) {
										return false;
									}
									tempArrInt = k;
									// System.out.println(tempStr);

									if (tempArrStr.equalsIgnoreCase("{")) {
										strArr1.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt).toString();
										// if(extraVal.contains("To")){System.out.println("The
										// = is found");}
										// else{return false;}
										// System.out.println(tempArrInt);
									}
									if (tempArrStr.equalsIgnoreCase("]")) {
										strArr2.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt).toString();
										// if(extraVal.contains("To")){System.out.println("The
										// = is found");}
										// else{return false;}
										// System.out.println(tempArrInt);
									}

									if (tempArrStr.contains("-")) {
										strArr2.add(tempArrInt);
										tempDateInt = tempArrInt;
									}
								}
								int z = findElementPlaceR("{");
								int y = findElementPlaceR("]");
								if (y - z >= 2) {
									System.out.println("{] Verification Done");
								}

								if (strArr1.size() >= 1) {
									tempArrFnl1 = mapStrWithKey.get(z + 1).toString();
									tempArrFnl2 = mapStrWithKey.get(y - 1).toString();
									System.err.println(tempArrFnl1 + " " + tempArrFnl2);
									// System.out.println(tempArrFnl);
									// Date date = new Date();
									// System.out.println(sdf.format(date));

									String sDate = tempArrFnl1;
									String eDate = tempArrFnl2;
									// String eDate = sdf.format(date);
									boolean tempVal1 = dateValidation(eDate, eDate, searchDateStr, "eDateEqualTo");
									boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");

									/*
									 * boolean tempVal3 = dateValidation(eDate,
									 * eDate, searchDateStr, "eDateEqualTo");
									 */

									if ((tempVal1 == true) || tempVal2 == true) {
										System.out.println("Success");
										return true;
									}

								} else {
									return false;
								}
							}
						}
						// Offer start Date Validation for {Start to End]

						// Offer end Date Validation for {Start to End]
						if ((mapStrWithKey.size() >= 1) && (offerDateInfo.equalsIgnoreCase("oeDate"))) {
							if (curlyOpen == 1 && squreBraceClose == 1 && to == 1) {
								System.err.println("{] & TO symbol(s) Present");
								tempArrStr = null;
								tempArrFnl = null;
								tempArrFnl1 = null;
								tempArrFnl2 = null;
								tempArrInt = 0;
								tempDateInt = 0;
								sizeOfTotalVal = mapStrWithKey.size();
								// System.err.println(sizeOfTotalVal);
								strArr1 = new ArrayList<Integer>();
								strArr2 = new ArrayList<Integer>();
								strArr3 = new ArrayList<Integer>();

								for (int k = 0; sizeOfTotalVal > k; k++) {
									// System.out.print(k);
									tempArrStr = mapStrWithKey.get(k).toString();
									if (tempArrStr.toLowerCase().contains("offerStartDate".toLowerCase())) {
										return false;
									}
									tempArrInt = k;
									// System.out.println(tempStr);

									if (tempArrStr.equalsIgnoreCase("{")) {
										strArr1.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt).toString();
										// if(extraVal.contains("To")){System.out.println("The
										// = is found");}
										// else{return false;}
										// System.out.println(tempArrInt);
									}
									if (tempArrStr.equalsIgnoreCase("]")) {
										strArr2.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt).toString();
										// if(extraVal.contains("To")){System.out.println("The
										// = is found");}
										// else{return false;}
										// System.out.println(tempArrInt);
									}

									if (tempArrStr.contains("-")) {
										strArr2.add(tempArrInt);
										tempDateInt = tempArrInt;
									}
								}
								int z = findElementPlaceR("{");
								int y = findElementPlaceR("]");
								if (y - z >= 2) {
									System.out.println("{] Verification Done");
								}

								if (strArr1.size() >= 1) {
									tempArrFnl1 = mapStrWithKey.get(z + 1).toString();
									tempArrFnl2 = mapStrWithKey.get(y - 1).toString();
									// System.err.println(tempArrFnl1
									// +" "+tempArrFnl2);
									// System.out.println(tempArrFnl);
									// Date date = new Date();
									// System.out.println(sdf.format(date));

									String sDate = tempArrFnl1;
									String eDate = tempArrFnl2;
									// String eDate = sdf.format(date);
									boolean tempVal1 = dateValidation(eDate, eDate, searchDateStr, "eDateEqualTo");
									boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");

									/*
									 * boolean tempVal3 = dateValidation(eDate,
									 * eDate, searchDateStr, "eDateEqualTo");
									 */

									if ((tempVal1 == true) || tempVal2 == true) {
										System.out.println("Success");
										return true;

									} else {
										return false;
									}
								}

							}

						}
						// Offer end Date Validation for {Start to End]
					}
				}
			}
			// End

			/*
			 * NEWLY ADDED FUNCTION BY ANTONY ON 01/30/2017 - END
			 */

			// [sdate TO *]
			if (andOp == 0 && orOp == 0 && curlyOpen == 0 && curlyClose == 0 && lessThan == 0 && greaterThan == 0
					&& equalTo == 0) {
				if (hyphen >= 0 || fSlash >= 0) {
					if (offerStartDate > 0 || offerEndDate > 0) {
						// Offer Start Date Validation for [Start to *]
						if ((mapStrWithKey.size() >= 1) && (offerDateInfo.equalsIgnoreCase("osDate"))) {
							if (squreBraceOpen == 1 && squreBraceClose == 1 && to == 1 && star == 1) {
								System.err.println("[] & TO & * symbol(s) Present");
								tempArrStr = null;
								tempArrFnl = null;
								tempArrFnl1 = null;
								tempArrFnl2 = null;
								tempArrInt = 0;
								tempDateInt = 0;
								sizeOfTotalVal = mapStrWithKey.size();
								// System.err.println(sizeOfTotalVal);
								strArr1 = new ArrayList<Integer>();
								strArr2 = new ArrayList<Integer>();
								strArr3 = new ArrayList<Integer>();

								for (int k = 0; sizeOfTotalVal > k; k++) {
									// System.out.print(k);
									tempArrStr = mapStrWithKey.get(k).toString();
									if (tempArrStr.toLowerCase().contains("offerEndDate".toLowerCase())) {
										return false;
									}
									tempArrInt = k;
									// System.out.println(tempStr);
									if (tempArrStr.equalsIgnoreCase("[")) {
										strArr1.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt).toString();
										// if(extraVal.contains("To")){System.out.println("The
										// = is found");}
										// else{return false;}
										// System.out.println(tempArrInt);
									}
									if (tempArrStr.equalsIgnoreCase("]")) {
										strArr2.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt).toString();
										// if(extraVal.contains("To")){System.out.println("The
										// = is found");}
										// else{return false;}
										// System.out.println(tempArrInt);
									}

									if (tempArrStr.contains("-")) {
										strArr2.add(tempArrInt);
										tempDateInt = tempArrInt;
									}
								}
								int z = findElementPlaceS("-");
								int y = findElementPlaceS("*");
								// if (y - z > 0) {
								// System.out.println(z +"--"+ y);
								// System.out.println("[] Verification Done");
								// }

								if (strArr1.size() >= 1 && (y - z > 0)) {
									tempArrFnl1 = mapStrWithKey.get(z).toString();
									tempArrFnl2 = curDate;
									// System.err.println(tempArrFnl1
									// +" "+tempArrFnl2);
									// System.out.println(tempArrFnl);
									// Date date = new Date();
									// System.out.println(sdf.format(date));

									String sDate = tempArrFnl1;
									String eDate = tempArrFnl2;
									// String eDate = sdf.format(date);
									boolean tempVal1 = dateValidation(sDate, eDate, searchDateStr, "sDateEqualTo");
									boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");
									boolean tempVal3 = dateValidation(sDate, eDate, searchDateStr, "eDateEqualTo");

									if ((tempVal1 == true) || (tempVal2 == true) || tempVal3 == true) {
										System.out.println("Success");
										return true;
									}

								} else if (strArr1.size() >= 1 && (z - y > 0)) {
									tempArrFnl1 = mapStrWithKey.get(z).toString();
									tempArrFnl2 = oldDate;
									// System.err.println(tempArrFnl1
									// +" "+tempArrFnl2);
									// System.out.println(tempArrFnl);
									// Date date = new Date();
									// System.out.println(sdf.format(date));

									String sDate = tempArrFnl2;
									String eDate = tempArrFnl1;
									// String eDate = sdf.format(date);
									boolean tempVal1 = dateValidation(sDate, eDate, searchDateStr, "sDateEqualTo");
									boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");
									boolean tempVal3 = dateValidation(sDate, eDate, searchDateStr, "eDateEqualTo");

									if ((tempVal1 == true) || (tempVal2 == true) || tempVal3 == true) {
										System.out.println("Success");
										return true;
									}
								}
							}
						}
						// Offer Start Date Validation for [Start to *]

						// Offer end Date Validation for [Start to *]
						if ((mapStrWithKey.size() >= 1) && (offerDateInfo.equalsIgnoreCase("oeDate"))) {
							if (squreBraceOpen == 1 && squreBraceClose == 1 && to == 1 && star == 1) {
								System.err.println("[] & TO & * symbol(s) Present");
								tempArrStr = null;
								tempArrFnl = null;
								tempArrFnl1 = null;
								tempArrFnl2 = null;
								tempArrInt = 0;
								tempDateInt = 0;
								sizeOfTotalVal = mapStrWithKey.size();
								// System.err.println(sizeOfTotalVal);
								strArr1 = new ArrayList<Integer>();
								strArr2 = new ArrayList<Integer>();
								strArr3 = new ArrayList<Integer>();

								for (int k = 0; sizeOfTotalVal > k; k++) {
									// System.out.print(k);
									tempArrStr = mapStrWithKey.get(k).toString();
									if (tempArrStr.toLowerCase().contains("offerStartDate".toLowerCase())) {
										return false;
									}
									tempArrInt = k;
									// System.out.println(tempStr);
									if (tempArrStr.equalsIgnoreCase("[")) {
										strArr1.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt).toString();
										// if(extraVal.contains("To")){System.out.println("The
										// = is found");}
										// else{return false;}
										// System.out.println(tempArrInt);
									}
									if (tempArrStr.equalsIgnoreCase("]")) {
										strArr2.add(tempArrInt);
										String extraVal = mapStrWithKey.get(tempArrInt).toString();
										// if(extraVal.contains("To")){System.out.println("The
										// = is found");}
										// else{return false;}
										// System.out.println(tempArrInt);
									}

									if (tempArrStr.contains("-")) {
										strArr2.add(tempArrInt);
										tempDateInt = tempArrInt;
									}
								}
								int z = findElementPlaceS("-");
								int y = findElementPlaceS("*");
								// if (y - z > 0) {
								// System.out.println(z +"--"+ y);
								// System.out.println("[] Verification Done");
								// }

								if (strArr1.size() >= 1 && (y - z > 0)) {
									tempArrFnl1 = mapStrWithKey.get(z).toString();
									tempArrFnl2 = curDate;
									// System.err.println(tempArrFnl1
									// +" "+tempArrFnl2);
									// System.out.println(tempArrFnl);
									// Date date = new Date();
									// System.out.println(sdf.format(date));

									String sDate = tempArrFnl1;
									String eDate = tempArrFnl2;
									// String eDate = sdf.format(date);
									boolean tempVal1 = dateValidation(sDate, eDate, searchDateStr, "sDateEqualTo");
									boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");

									boolean tempVal3 = dateValidation(sDate, eDate, searchDateStr, "eDateEqualTo");

									if ((tempVal1 == true) || (tempVal2 == true) || tempVal3 == true) {
										System.out.println("Success");
										return true;
									}

								} else if (strArr1.size() >= 1 && (z - y > 0)) {
									tempArrFnl1 = mapStrWithKey.get(z).toString();
									tempArrFnl2 = oldDate;
									// System.err.println(tempArrFnl1
									// +" "+tempArrFnl2);
									// System.out.println(tempArrFnl);
									// Date date = new Date();
									// System.out.println(sdf.format(date));

									String sDate = tempArrFnl2;
									String eDate = tempArrFnl1;
									// String eDate = sdf.format(date);
									boolean tempVal1 = dateValidation(sDate, eDate, searchDateStr, "sDateEqualTo");
									boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");

									boolean tempVal3 = dateValidation(sDate, eDate, searchDateStr, "eDateEqualTo");

									if ((tempVal1 == true) || (tempVal2 == true) || tempVal3 == true) {
										System.out.println("Success");
										return true;
									}
								}
							}
						}
						// Offer End Date Validation for [Start to *]

					}
				}
			}
			// End

			// > or >= or < or <= with AND or OR condition
			if (curlyOpen == 0 && curlyClose == 0 && squreBraceOpen == 0 && squreBraceClose == 0 && to == 0
					&& offerStartDate == 1 && offerEndDate == 1 && (hyphen == 4 || fSlash == 4) && star == 0) {

				// offer start date check

				if ((mapStrWithKey.size() >= 1) && (offerDateInfo.equalsIgnoreCase("osDate"))) {
					System.err.println("TESTING - OFFER START DATE");
					tempArrStr = null;
					tempArrFnl = null;
					tempArrFnl1 = null;
					tempArrFnl2 = null;
					tempArrInt = 0;
					tempDateInt = 0;
					sizeOfTotalVal = mapStrWithKey.size();
					int ofstdate = 0;
					int ofeddate = 0;
					int k = 0;
					// System.err.println(sizeOfTotalVal);
					strArr1 = new ArrayList<Integer>();
					strArr2 = new ArrayList<Integer>();
					strArr3 = new ArrayList<Integer>();
					strArr4 = new ArrayList<Integer>();
					int gt = 0;
					int gtKey = 0;
					int ls = 0;
					int lsKey = 0;
					int eq = 0;
					int eqKey = 0;
					int hy = 0;
					int hyKey = 0;
					for (k = 0; sizeOfTotalVal > k; k++) {
						// System.out.print(k);
						tempArrStr = mapStrWithKey.get(k).toString();
						if (tempArrStr.toLowerCase().contains("offerStartDate".toLowerCase()))
							ofstdate = k;
						if (tempArrStr.toLowerCase().contains("offerEndDate".toLowerCase()))
							ofeddate = k;
						/*
						 * if(tempArrStr.toLowerCase().contains("<".toLowerCase(
						 * ))){ strArr2.add(k); }
						 * if(tempArrStr.toLowerCase().contains("=".toLowerCase(
						 * ))){ strArr3.add(k); }
						 */
					}
					if (ofstdate < ofeddate) {
						for (k = ofstdate; ofeddate > k; k++) {
							// System.out.print(k);
							tempArrStr = mapStrWithKey.get(k).toString();
							System.out.println(tempArrStr);
							if (tempArrStr.toLowerCase().contains(">".toLowerCase())) {
								gt = 1;
								gtKey = k;
							}
							if (tempArrStr.toLowerCase().contains("<".toLowerCase())) {
								ls = 1;
								lsKey = k;
							}
							if (tempArrStr.toLowerCase().contains("=".toLowerCase())) {
								eq = 1;
								eqKey = k;
							}
							if (tempArrStr.toLowerCase().contains("-".toLowerCase())) {
								hy = 1;
								hyKey = k;
							}
						}
					} else if (ofstdate > ofeddate) {
						for (k = ofstdate; sizeOfTotalVal > k; k++) {
							// System.out.print(k);
							tempArrStr = mapStrWithKey.get(k).toString();
							System.out.println(tempArrStr);
							if (tempArrStr.toLowerCase().contains(">".toLowerCase())) {
								gt = 1;
								gtKey = k;
							}
							if (tempArrStr.toLowerCase().contains("<".toLowerCase())) {
								ls = 1;
								lsKey = k;
							}
							if (tempArrStr.toLowerCase().contains("=".toLowerCase())) {
								eq = 1;
								eqKey = k;
							}
							if (tempArrStr.toLowerCase().contains("-".toLowerCase())) {
								hy = 1;
								hyKey = k;
							}

						}
					}
					if (gt == 1 && ls == 0 && eq == 0 && hy == 1) {
						System.out.println("greater than symbol + Hyphen present");
						tempArrStr = mapStrWithKey.get(hyKey).toString();
						System.out.println("The date is :" + tempArrStr);
						tempArrFnl1 = tempArrStr;
						tempArrFnl2 = ">";
					}
					if (gt == 0 && ls == 1 && eq == 0 && hy == 1) {
						System.out.println("less than symbol + Hyphen present");
						tempArrStr = mapStrWithKey.get(hyKey).toString();
						System.out.println("The date is :" + tempArrStr);
						tempArrFnl1 = tempArrStr;
						tempArrFnl2 = "<";
					}
					if (gt == 0 && ls == 1 && eq == 1 && hy == 1) {
						System.out.println("less than = symbol + Hyphen present");
						tempArrStr = mapStrWithKey.get(hyKey).toString();
						System.out.println("The date is :" + tempArrStr);
						tempArrFnl1 = tempArrStr;
						tempArrFnl2 = "<=";
					}
					if (gt == 1 && ls == 0 && eq == 1 && hy == 1) {
						System.out.println("greater than = symbol + Hyphen present");
						tempArrStr = mapStrWithKey.get(hyKey).toString();
						System.out.println("The date is :" + tempArrStr);
						tempArrFnl1 = tempArrStr;
						tempArrFnl2 = ">=";
					}
					System.out.println("FINAL CONDITION CHECK");
					/*
					 * System.out.println(ofstdate);
					 * System.out.println(ofeddate);
					 * System.out.println(sizeOfTotalVal);
					 */
					// final CONDITION CHECK
					if (tempArrFnl2.equalsIgnoreCase(">")) {
						String eDate = futrDate;
						String sDate = tempArrFnl1;
						boolean tempVal1 = dateValidation(sDate, eDate, searchDateStr, "sDateEqualTo");
						boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");
						/*
						 * boolean tempVal3 = dateValidation(sDate, eDate,
						 * searchDateStr, "eDateEqualTo");
						 */

						if (tempVal1 == false && tempVal2 == true) {
							System.out.println("Success");
							return true;
						}
					} else if (tempArrFnl2.equalsIgnoreCase("<")) {
						String eDate = tempArrFnl1;
						String sDate = oldDate;
						boolean tempVal1 = dateValidation(sDate, eDate, searchDateStr, "eDateEqualTo");
						boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");
						/*
						 * boolean tempVal3 = dateValidation(sDate, eDate,
						 * searchDateStr, "eDateEqualTo");
						 */

						if (tempVal1 == false && tempVal2 == true) {
							System.out.println("Success");
							return true;
						}
					} else if (tempArrFnl2.equalsIgnoreCase(">=")) {
						String eDate = futrDate;
						String sDate = tempArrFnl1;
						boolean tempVal1 = dateValidation(sDate, eDate, searchDateStr, "sDateEqualTo");
						boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");
						/*
						 * boolean tempVal3 = dateValidation(sDate, eDate,
						 * searchDateStr, "eDateEqualTo");
						 */

						if (tempVal1 == true || tempVal2 == true) {
							System.out.println("Success");
							return true;
						}
					} else if (tempArrFnl2.equalsIgnoreCase("<=")) {
						String eDate = tempArrFnl1;
						String sDate = oldDate;
						boolean tempVal1 = dateValidation(sDate, eDate, searchDateStr, "eDateEqualTo");
						boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");
						/*
						 * boolean tempVal3 = dateValidation(sDate, eDate,
						 * searchDateStr, "eDateEqualTo");
						 */

						if (tempVal1 == true || tempVal2 == true) {
							System.out.println("Success");
							return true;
						}
					} else
						return false;
				}
				// offer end date check

				if ((mapStrWithKey.size() >= 1) && (offerDateInfo.equalsIgnoreCase("oeDate"))) {
					System.err.println("TESTING - OFFER END DATE");
					tempArrStr = null;
					tempArrFnl = null;
					tempArrFnl1 = null;
					tempArrFnl2 = null;
					tempArrInt = 0;
					tempDateInt = 0;
					sizeOfTotalVal = mapStrWithKey.size();
					int ofstdate = 0;
					int ofeddate = 0;
					// int k=0;
					// System.err.println(sizeOfTotalVal);
					strArr1 = new ArrayList<Integer>();
					strArr2 = new ArrayList<Integer>();
					strArr3 = new ArrayList<Integer>();
					strArr4 = new ArrayList<Integer>();
					int gt = 0;
					int gtKey = 0;
					int ls = 0;
					int lsKey = 0;
					int eq = 0;
					int eqKey = 0;
					int hy = 0;
					int hyKey = 0;
					for (int k = 0; sizeOfTotalVal > k; k++) {
						// System.out.print(k);
						tempArrStr = mapStrWithKey.get(k).toString();
						if (tempArrStr.toLowerCase().contains("offerStartDate".toLowerCase()))
							ofeddate = k;
						if (tempArrStr.toLowerCase().contains("offerEndDate".toLowerCase()))
							ofstdate = k;
						/*
						 * if(tempArrStr.toLowerCase().contains("<".toLowerCase(
						 * ))){ strArr2.add(k); }
						 * if(tempArrStr.toLowerCase().contains("=".toLowerCase(
						 * ))){ strArr3.add(k); }
						 */
					}
					if (ofstdate < ofeddate) {
						for (int k = ofstdate; ofeddate > k; k++) {
							// System.out.print(k);
							tempArrStr = mapStrWithKey.get(k).toString();
							System.out.println(tempArrStr);
							if (tempArrStr.toLowerCase().contains(">".toLowerCase())) {
								gt = 1;
								gtKey = k;
							}
							if (tempArrStr.toLowerCase().contains("<".toLowerCase())) {
								ls = 1;
								lsKey = k;
							}
							if (tempArrStr.toLowerCase().contains("=".toLowerCase())) {
								eq = 1;
								eqKey = k;
							}
							if (tempArrStr.toLowerCase().contains("-".toLowerCase())) {
								hy = 1;
								hyKey = k;
							}
						}
					} else if (ofstdate > ofeddate) {
						for (int k = ofstdate; sizeOfTotalVal > k; k++) {
							// System.out.print(k);
							tempArrStr = mapStrWithKey.get(k).toString();
							System.out.println(tempArrStr);
							if (tempArrStr.toLowerCase().contains(">".toLowerCase())) {
								gt = 1;
								gtKey = k;
							}
							if (tempArrStr.toLowerCase().contains("<".toLowerCase())) {
								ls = 1;
								lsKey = k;
							}
							if (tempArrStr.toLowerCase().contains("=".toLowerCase())) {
								eq = 1;
								eqKey = k;
							}
							if (tempArrStr.toLowerCase().contains("-".toLowerCase())) {
								hy = 1;
								hyKey = k;
							}

						}
					}
					if (gt == 1 && ls == 0 && eq == 0 && hy == 1) {
						System.out.println("greater than symbol + Hyphen present");
						tempArrStr = mapStrWithKey.get(hyKey).toString();
						System.out.println("The date is :" + tempArrStr);
						tempArrFnl1 = tempArrStr;
						tempArrFnl2 = ">";
					}
					if (gt == 0 && ls == 1 && eq == 0 && hy == 1) {
						System.out.println("less than symbol + Hyphen present");
						tempArrStr = mapStrWithKey.get(hyKey).toString();
						System.out.println("The date is :" + tempArrStr);
						tempArrFnl1 = tempArrStr;
						tempArrFnl2 = "<";
					}
					if (gt == 0 && ls == 1 && eq == 1 && hy == 1) {
						System.out.println("less than = symbol + Hyphen present");
						tempArrStr = mapStrWithKey.get(hyKey).toString();
						System.out.println("The date is :" + tempArrStr);
						tempArrFnl1 = tempArrStr;
						tempArrFnl2 = "<=";
					}
					if (gt == 1 && ls == 0 && eq == 1 && hy == 1) {
						System.out.println("greater than = symbol + Hyphen present");
						tempArrStr = mapStrWithKey.get(hyKey).toString();
						System.out.println("The date is :" + tempArrStr);
						tempArrFnl1 = tempArrStr;
						tempArrFnl2 = ">=";
					}
					System.out.println("FINAL CONDITION CHECK");
					/*
					 * System.out.println(ofstdate);
					 * System.out.println(ofeddate);
					 * System.out.println(sizeOfTotalVal);
					 */
					// final CONDITION CHECK
					if (tempArrFnl2.equalsIgnoreCase(">")) {
						String eDate = futrDate;
						String sDate = tempArrFnl1;
						boolean tempVal1 = dateValidation(sDate, eDate, searchDateStr, "sDateEqualTo");
						boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");
						/*
						 * boolean tempVal3 = dateValidation(sDate, eDate,
						 * searchDateStr, "eDateEqualTo");
						 */

						if (tempVal1 == false && tempVal2 == true) {
							System.out.println("Success");
							return true;
						}
					} else if (tempArrFnl2.equalsIgnoreCase("<")) {
						String eDate = tempArrFnl1;
						String sDate = oldDate;
						boolean tempVal1 = dateValidation(sDate, eDate, searchDateStr, "eDateEqualTo");
						boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");
						/*
						 * boolean tempVal3 = dateValidation(sDate, eDate,
						 * searchDateStr, "eDateEqualTo");
						 */

						if (tempVal1 == false && tempVal2 == true) {
							System.out.println("Success");
							return true;
						}
					} else if (tempArrFnl2.equalsIgnoreCase(">=")) {
						String eDate = futrDate;
						String sDate = tempArrFnl1;
						boolean tempVal1 = dateValidation(sDate, eDate, searchDateStr, "sDateEqualTo");
						boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");
						/*
						 * boolean tempVal3 = dateValidation(sDate, eDate,
						 * searchDateStr, "eDateEqualTo");
						 */

						if (tempVal1 == true || tempVal2 == true) {
							System.out.println("Success");
							return true;
						}
					} else if (tempArrFnl2.equalsIgnoreCase("<=")) {
						String eDate = tempArrFnl1;
						String sDate = oldDate;
						boolean tempVal1 = dateValidation(sDate, eDate, searchDateStr, "eDateEqualTo");
						boolean tempVal2 = dateValidation(sDate, eDate, searchDateStr, "dBetween");
						/*
						 * boolean tempVal3 = dateValidation(sDate, eDate,
						 * searchDateStr, "eDateEqualTo");
						 */

						if (tempVal1 == true || tempVal2 == true) {
							System.out.println("Success");
							return true;
						}
					} else
						return false;
				} // Offer End date steps
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return false;
	}

	/**
	 * @param inputString
	 * @param removeFrom
	 * @param removeTo
	 * @return
	 */
	public String cutStringFromSentence(String inputString, String removeFrom, String removeTo) {
		try {
			// String removeFrom = "childQuery:";
			// String removeTo = ",";
			String subStringOfActual_1 = inputString.substring(inputString.indexOf(removeFrom));
			String subStringOfActual_2 = subStringOfActual_1.split(removeTo)[0];
			String subStringOfActual_3 = subStringOfActual_2.replace("\"", "");
			String subStringOfActual_4 = subStringOfActual_3.replace(removeFrom, "");
			return subStringOfActual_4;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * To check the number of words
	 * @param subStr
	 * @param inputString
	 * @return
	 */
	public int wordCountCheck(String subStr, String inputString) {
		try {
			int i = 0;
			Pattern p = Pattern.compile(subStr, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(inputString);
			while (m.find()) {
				i++;
			}
			return i;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	/**
	 * Special character check
	 * @param subStr
	 * @param inputString
	 * @return
	 */
	public int specialCharCheck(String subStr, String inputString) {
		try {
			char ch = inputString.charAt(inputString.indexOf(subStr));
			int count = 0;
			for (int i = 0; i < inputString.length(); i++) {
				char charSpc = subStr.charAt(0);
				if (inputString.charAt(i) == charSpc) {
					count++;
				}
			}
			return count;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	/**
	 * @param inputString
	 */
	public void setenceSplit(String inputString) {
		ArrayList<String> strArray = new ArrayList<String>();
		StringTokenizer strTok = new StringTokenizer(inputString, ">//<//=//[//]//{//}//,//TO// ", true);

		while (strTok.hasMoreElements()) {
			String stringArray = strTok.nextElement().toString().trim();
			// System.err.println(stringArray);
			strArray.add(stringArray);
		}
		int i = 0, j = 0;
		for (String strArraytest : strArray) {
			if (strArraytest != null && strArraytest.length() > 0) {
				// System.out.println("strArraytest[" + i + "] :" +
				// strArraytest);
				mapStrWithKey.put(i++, strArraytest);
			}
			Iterator iterator = mapStrWithKey.keySet().iterator();
		}
		for (Entry<Integer, String> entry : mapStrWithKey.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}

	/**
	 * @param searchStr
	 */
	public void findElementPlace(String searchStr) {
		try {
			// if(returnVal.equalsIgnoreCase(""))
			for (Entry<Integer, String> e : mapStrWithKey.entrySet()) {
				if (e.getValue().contentEquals(searchStr)) {
					System.out.println("The Search Value is :" + searchStr + " and the key is :" + e.getKey());
					String keyVal = e.getKey().toString();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @param searchStr
	 * @return
	 */
	public int findElementPlaceR(String searchStr) {
		try {
			// if(returnVal.equalsIgnoreCase(""))
			for (Entry<Integer, String> e : mapStrWithKey.entrySet()) {
				if (e.getValue().contentEquals(searchStr)) {
					System.out.println("The Search Value is :" + searchStr + " and the key is :" + e.getKey());
					String keyVal = e.getKey().toString();
					return Integer.parseInt(keyVal.toString());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	/**
	 * @param searchStr
	 * @return
	 */
	public int findElementPlaceS(String searchStr) {
		try {
			// if(returnVal.equalsIgnoreCase(""))
			for (Entry<Integer, String> e : mapStrWithKey.entrySet()) {
				if (e.getValue().contains(searchStr)) {
					System.out.println("The Search Value is :" + searchStr + " and the key is :" + e.getKey());
					String keyVal = e.getKey().toString();
					return Integer.parseInt(keyVal.toString());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	/**
	 * Date validation
	 * @param sDate
	 * @param eDate
	 * @param dateToCheck
	 * @param info
	 * @return
	 */
	public boolean dateValidation(String sDate, String eDate, String dateToCheck, String info) {
		try {
			Date sDatemdf = sdf.parse(sDate);
			Date eDatemdf = sdf.parse(eDate);
			Date dateToCheckmdf = sdf.parse(dateToCheck);

			if (info.equalsIgnoreCase("sDateEqualTo")) {
				boolean sDateEqualTo = dateToCheckmdf.equals(sDatemdf);
				return sDateEqualTo;
			}
			if (info.equalsIgnoreCase("eDateEqualTo")) {
				boolean eDateEqualTo = dateToCheckmdf.equals(eDatemdf);
				return eDateEqualTo;
			}
			if (info.equalsIgnoreCase("dBetween")) {
				boolean dBetween = ((dateToCheckmdf.after(sDatemdf)) && (dateToCheckmdf.before(eDatemdf)));
				return dBetween;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	/**
	 * @param dda
	 * @param response
	 * @return
	 */
	public Boolean dd(String dda, Response response){
		
		
		DateValidation scn = new DateValidation();
		String resStartDate  = APIHelper.retriveValuefromJson("$..schema:offerStartDate", response.getBody().asString());
		String resEndDate = APIHelper.retriveValuefromJson("$..schema:offerEndDate", response.getBody().asString());
		
		if(resStartDate.contains("T"))
			resStartDate = resStartDate.split("T")[0];
		if(resEndDate.contains("T"))
			resEndDate = resEndDate.split("T")[0];
		
		ReportHelper.log(LogStatus.INFO, "QueryString",dda);	
		ReportHelper.log(LogStatus.INFO, "Start Date in response",resStartDate);	
		ReportHelper.log(LogStatus.INFO, "End Date in response",resEndDate);	
		
		boolean test = false;
		boolean test1 = false;
		boolean test2 = false;
		
		if(dda.contains("offerEndDate")==true && dda.contains("offerStartDate")==false){
		 test = scn.dateValidationFinalCheck(dda, resEndDate, "oeDate");
		}
		else if (dda.contains("offerStartDate")==true && dda.contains("offerEndDate")==false){
			System.out.println("(((((((((((((((((((((" + dda);
			System.out.println("(((((((((((((((((((((" + resStartDate);
			test = scn.dateValidationFinalCheck(dda, resStartDate, "osDate");
		}
		else if (dda.contains("offerStartDate")==true && dda.contains("offerEndDate")==true && dda.contains("AND")==true)
		{
			test1 = scn.dateValidationFinalCheck(dda, resStartDate, "osDate");
			test2 = scn.dateValidationFinalCheck(dda, resEndDate, "oeDate");
			if (test1==true && test2==true)		
				test = true;
		}
		else if (dda.contains("offerStartDate")==true && dda.contains("offerEndDate")==true && dda.contains("OR")==true)
		{
			test1 = scn.dateValidationFinalCheck(dda.split("OR")[0], resStartDate, "osDate");
 			test2 = scn.dateValidationFinalCheck("childQuery:"+dda.split("OR")[1], resEndDate, "oeDate");
		if (test1==true || test2==true)		
			test = true;
		}
	
		System.out.println("FINAL RESULT : - " + test);
		return test;
	}

}
