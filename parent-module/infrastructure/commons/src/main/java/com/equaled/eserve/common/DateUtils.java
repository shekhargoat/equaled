package com.equaled.eserve.common;
import java.time.Instant;

public class DateUtils {

	public static Long getCurrentDate(){
		Instant instant = Instant.now();
		return instant.toEpochMilli();
	}
	public static String getCurrentStringDate(){
		Instant instant = Instant.now();
		return String.valueOf(instant.toEpochMilli());
	}
}
