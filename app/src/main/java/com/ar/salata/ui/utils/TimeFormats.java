package com.ar.salata.ui.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeFormats {
    public static String convertToArabicString(String pattern, long time) {
        Date date = new Date(time * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("ar", "EG"));
        return simpleDateFormat.format(date);
    }

    public static String convertToEnglishString(String pattern, long time) {
        Date date = new Date(time * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.US);
        return simpleDateFormat.format(date);
    }

    public static String convertToAPIFormat(String oldPattern, String time) {
        Date date = null;
        String result = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SimpleDateFormat simpleArabicDateFormat = new SimpleDateFormat(oldPattern, new Locale("ar", "EG"));
        try {
            date = simpleArabicDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null)
            result = simpleDateFormat.format(date);

        return result;
    }

    public static String getArabicDay(long time) {
        Date date = new Date(time * 1000);
        SimpleDateFormat simpleArabicDateFormat = new SimpleDateFormat("E", new Locale("ar", "EG"));
        return simpleArabicDateFormat.format(date);
    }

    public static String getArabicHour(long time) {
        Date date = new Date(time * 1000);
        SimpleDateFormat simpleArabicDateFormat = new SimpleDateFormat("hh:mm a", new Locale("ar", "EG"));
        return simpleArabicDateFormat.format(date);
    }

    public static long convertToLong(String oldPattern, String time) {
        Date date = null;
        SimpleDateFormat simpleArabicDateFormat = new SimpleDateFormat(oldPattern, new Locale("ar", "EG"));
        try {
            date = simpleArabicDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }
}
