package com.sxr.knowledge.temp.parse;

public class DataStore {

	private static String data = "aaaa*aa+*";

	public static char getChar(int pos) {
		return data.charAt(pos);
	}

	public static int getLength() {
		return data.length();
	}
}
