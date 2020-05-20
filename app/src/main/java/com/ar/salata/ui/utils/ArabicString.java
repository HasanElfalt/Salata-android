package com.ar.salata.ui.utils;

public class ArabicString {
	public static String toArabic(String s) {
		return s.replaceAll("0", "\u0660")
				.replaceAll("1", "\u0661")
				.replaceAll("2", "\u0662")
				.replaceAll("3", "\u0663")
				.replaceAll("4", "\u0664")
				.replaceAll("5", "\u0665")
				.replaceAll("6", "\u0666")
				.replaceAll("7", "\u0667")
				.replaceAll("8", "\u0668")
				.replaceAll("9", "\u0669");
	}
}
