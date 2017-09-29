package com.misa.logging.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateConvertion {
	// convert timestamp to calendar
	public static String timestampToDate(long timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp * 1000);

		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		return format.format(calendar.getTime());

	}
	// convert timestamp to hour of date
	public static String timestampToHour(long timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp * 1000);

		DateFormat format = new SimpleDateFormat("hh:mm:ss");
		return format.format(calendar.getTime());

	}
	
}
