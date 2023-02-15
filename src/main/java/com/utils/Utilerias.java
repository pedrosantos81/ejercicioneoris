package com.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Utilerias {
	
	private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	 
	private final static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 
	public static java.sql.Date parseDate(String date) {
	    try {
	        return new Date(DATE_FORMAT.parse(date).getTime());
	    } catch (ParseException e) {
	        throw new IllegalArgumentException(e);
	    }
	}
	 
	public static java.sql.Timestamp parseTimestamp(String timestamp) {
	    try {
	        return new Timestamp(DATE_TIME_FORMAT.parse(timestamp).getTime());
	    } catch (ParseException e) {
	        throw new IllegalArgumentException(e);
	    }
	}
	
	public static int comparar(double d1,double d2) {
		int retval = Double.compare(d1, d2);
	    
	      if(retval > 0) {
	         System.out.println("d1 is greater than d2");
	         return 1;
	      } else if(retval < 0) {
	        System.out.println("d1 is less than d2");
	        return 2;
	      } else {
	         System.out.println("d1 is equal to d2");
	         return 0;
	      }
	}

}
