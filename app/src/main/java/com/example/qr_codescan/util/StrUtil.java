package com.example.qr_codescan.util;

public class StrUtil {

	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static boolean isEquals(String str1, String str2) {
		if (str1 == str2) {
			return true;
		}
		if (str1 != null) {
			return str1.equals(str2);
		} else {
			return false;
		}
	}

}
