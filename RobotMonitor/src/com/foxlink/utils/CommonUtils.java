package com.foxlink.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
	private static String dateFormat="yyyy/MM/dd"; 
	public static Date ConvertString2Date(String dateTime) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.parse(dateTime);
	}
}
