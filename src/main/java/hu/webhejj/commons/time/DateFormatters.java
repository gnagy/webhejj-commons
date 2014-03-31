/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0; 
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons.time;

/**
 * Singleton container for common date formats
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateFormatters {

	public static final String FORMAT_DATE = "yyyy-MM-dd";
	public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_DATE_TIME_SEC = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_DATE_TIME_SEC_MILLIS = "yyyy-MM-dd HH:mm:ss.S";
	public static final String FORMAT_DATE_FILENAME = "yyyyMMdd_HHmmss";

	private static DateFormat dateFormat;
	private static DateFormat dateTimeFormat;
	private static DateFormat dateTimeSecFormat;
	private static DateFormat dateTimeSecMillisFormat;
	private static DateFormat dateFormatFileName;
	
	
	public static DateFormat getDateFormat() {
		if(dateFormat == null) {
			synchronized (DateFormatters.class) {
				if(dateFormat == null) {
					dateFormat = new SimpleDateFormat(FORMAT_DATE);
				}
			}
		}
		return dateFormat;
	}

	public static DateFormat getDateTimeFormat() {
		if(dateTimeFormat == null) {
			synchronized (DateFormatters.class) {
				if(dateTimeFormat == null) {
					dateTimeFormat = new SimpleDateFormat(FORMAT_DATE_TIME);
				}
			}
		}
		return dateTimeFormat;
	}

	public static DateFormat getDateTimeSecFormat() {
		if(dateTimeSecFormat == null) {
			synchronized (DateFormatters.class) {
				if(dateTimeSecFormat == null) {
					dateTimeSecFormat = new SimpleDateFormat(FORMAT_DATE_TIME_SEC);
				}
			}
		}
		return dateTimeSecFormat;
	}
	
	public static DateFormat getDateTimeSecMillisFormat() {
		if(dateTimeSecMillisFormat == null) {
			synchronized (DateFormatters.class) {
				if(dateTimeSecMillisFormat == null) {
					dateTimeSecMillisFormat = new SimpleDateFormat(FORMAT_DATE_TIME_SEC_MILLIS);
				}
			}
		}
		return dateTimeSecMillisFormat;
	}
	
	public static DateFormat getDateFormatFileName() {
		if(dateFormatFileName == null) {
			synchronized (DateFormatters.class) {
				if(dateFormatFileName == null) {
					dateFormatFileName = new SimpleDateFormat(FORMAT_DATE_FILENAME);
				}
			}
		}
		return dateFormatFileName;
	}
}
