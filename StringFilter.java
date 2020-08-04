package com.systex.ImportIssueFile;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringFilter {

	public static String cleanString(String aString) {
		if (aString == null)
			return null;
		String cleanString = "";
		for (int i = 0; i < aString.length(); ++i) {
			cleanString += cleanChar(aString.charAt(i));
		}
		return cleanString;
	}

	private static char cleanChar(char aChar) {
		// 0 - 9
		for (int i = 48; i < 58; ++i) {
			if (aChar == i)
				return (char) i;
		}
		// 'A' - 'Z'
		for (int i = 65; i < 91; ++i) {
			if (aChar == i)
				return (char) i;
		}
		// 'a' - 'z'
		for (int i = 97; i < 123; ++i) {
			if (aChar == i)
				return (char) i;
		}
		// other valid characters
		switch (aChar) {
		case ':':
			return ':';
		case '\\':
			return '\\';
		case '/':
			return '/';
		case '.':
			return '.';
		case '-':
			return '-';
		case '_':
			return '_';
		case ' ':
			return ' ';
		}
		return '%';
	}

	public static String logFilter(Object log) {
		String aString = String.valueOf(log);
		List<String> logBlackList = new ArrayList<String>();
		logBlackList.add("%0d");
		logBlackList.add("%0D");
		logBlackList.add("\r");
		logBlackList.add("%0a");
		logBlackList.add("%0A");
		logBlackList.add("\n");
		String encode = Normalizer.normalize(aString, Normalizer.Form.NFKC);
		for (int i = 0; i < logBlackList.size(); i++) {
			encode = encode.replace(logBlackList.get(i), "");
		}
		return encode;
	}
}
