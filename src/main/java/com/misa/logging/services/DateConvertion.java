package com.misa.logging.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConvertion {
	// convert timestamp to calendar
	public static String timestampToDate(long timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp * 1000);

		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		return format.format(calendar.getTime());

	}
/*
	// convert input date to timestamp
	// return start date in timestamp
	public static long startTimestamp(Date date) {
		// intitialize calendar
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE,0) ;
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND,0);
		
		return cal.getTimeInMillis();

	}

	// convert unput date to timestamp
	// return end date in timestamp
	public static long endTimestamp(Date date) {
		// intitialize calendar
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE,59) ;
		cal.set(Calendar.SECOND, 59);
		
		return cal.getTimeInMillis();

	}
	// return the day before inputed date
	public static String getDayAfter(long timestamp) throws ParseException {
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp*1000);
		cal.add(Calendar.DATE, 1);
		
		// format date
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");

		return format.format(cal.getTime());
	}
	*/
	
}
