package com.misa.logging.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

// convert timestamp to calendar
public class TimestampToDate {
	public String timestampConvert(long timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp*1000);
		
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return format.format(calendar.getTime());
		
	}
}
