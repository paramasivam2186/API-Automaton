package com.pearson.regression.utilityHelpers;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Wildcard {
	
	/**
	 * Common reusable methods generated for CSG search wildcard logic
	 * @querySearchstring
	 * @pattern
	 */
	
	//Verify the given string is present with <em> tag
	public static boolean emtagmatch(String querySearchstring, String pattern) {
		Pattern p = Pattern.compile(Constants.emPattern, Pattern.MULTILINE | Pattern.DOTALL);
		boolean bSuccess = false;
		for (Matcher m = p.matcher(querySearchstring); m.find();) {
			String patternContent = m.group(1);
			bSuccess = match(patternContent, pattern, 0, 0);
			if (bSuccess == true)
				break;
		}
		return bSuccess;
	}

	//Verify the querystring contains space character
	public static boolean match_space(String querySearchstring, String pattern) {
		boolean bSuccess = false;
		String[] s = querySearchstring.split(Constants.spaceChar);
		for (String s1 : s) {
			bSuccess = match(s1, pattern);
			if (bSuccess == true)
				break;
		}
		return bSuccess;
	}

	public static boolean getmatch(String querySearchstring, String fieldVal) {
		Pattern p = Pattern.compile(querySearchstring);
		boolean bSuccess = false;
		for (Matcher m = p.matcher(fieldVal); m.find();) {
			String patternContent = m.group(0);
			bSuccess = match(patternContent, querySearchstring, 0, 0);
			if (bSuccess == false)
				break;
		}
		return bSuccess;
	}

	public static boolean match(String fieldValue, String querySearchstring) {
		boolean bSuccess = match(fieldValue, querySearchstring, 0, 0);
		return bSuccess;
	}

	//get the em tag value from the given string
	public static Set<String> wildCardData(String querySearchstring) {
		Pattern p = Pattern.compile(Constants.emPattern, Pattern.MULTILINE | Pattern.DOTALL);
		String patternContent = Constants.emptyString;
		Set<String> fvalues = new HashSet<String>();
		for (Matcher m = p.matcher(querySearchstring); m.find();) {
			patternContent = m.group(1);
			fvalues.add(patternContent.toLowerCase());
		}
		return fvalues;
	}

	public static boolean equalsOrMatch(String string, String pattern) {
		if (string.equals(pattern) == true) {
			return true;
		}
		return match(string, pattern, 0, 0);
	}

	//Validating wildcard String present with actual response string value
	private static boolean match(String string, String pattern, int stringStartNdx, int patternStartNdx) {
		int pNdx = patternStartNdx;
		int sNdx = stringStartNdx;
		int pLen = pattern.length();
		if (pLen == 1) {
			if (pattern.charAt(0) == '*') {
				return true;
			}
		}
		int sLen = string.length();
		boolean nextIsNotWildcard = false;

		while (true) {
			if ((sNdx >= sLen) == true) {

				while ((pNdx < pLen) && (pattern.charAt(pNdx) == '*')) {
					pNdx++;
				}
				return pNdx >= pLen;
			}
			if (pNdx >= pLen) {
				return false;
			}
			char p = pattern.charAt(pNdx); // pattern char

			// perform logic
			if (nextIsNotWildcard == false) {

				if (p == '\\') {
					pNdx++;
					nextIsNotWildcard = true;
					continue;
				}
				if (p == '?') {
					sNdx++;
					pNdx++;
					continue;
				}
				if (p == '*') {
					char pnext = 0; // next pattern char
					if (pNdx + 1 < pLen) {
						pnext = pattern.charAt(pNdx + 1);
					}
					if (pnext == '*') { // double '*' have the same effect as
										// one '*'
						pNdx++;
						continue;
					}
					int i;
					pNdx++;

					// find recursively if there is any substring from the end
					// of the
					// line that matches the rest of the pattern !!!
					for (i = string.length(); i >= sNdx; i--) {
						if (match(string, pattern, i, pNdx) == true) {
							return true;
						}
					}
					return false;
				}
			} else {
				nextIsNotWildcard = false;
			}

			// check if pattern char and string char are equals
			if (p != string.charAt(sNdx)) {
				return false;
			}

			// everything matches for now, continue
			sNdx++;
			pNdx++;
		}
	}

	//Verify string contains wildcard
	public static boolean checkWildCard(String emtagVal) {
		boolean bFound = false;
		String[] matches = new String[] { "*", "?", "|", "$", "[abc]", "[a-z]", "[^az]", "*~" };
		for (String s : matches) {
			if (emtagVal.contains(s)) {
				bFound = true;
			}
			if (bFound == true)
				break;
		}
		return bFound;
	}

	/**
	 * Matches string to at least one pattern. Returns index of matched pattern,
	 */
	public static int matchOne(String src, String[] patterns) {
		for (int i = 0; i < patterns.length; i++) {
			if (match(src, patterns[i]) == true) {
				return i;
			}
		}
		return -1;
	}

	//Verify query string contains exact pharse
	public static boolean checkExactPharse(String qsString) {
		boolean bFound = false;
		String match = Constants.excatPharse;
		if (qsString.contains(match)) {
			bFound = true;
		}
		return bFound;
	}

}
