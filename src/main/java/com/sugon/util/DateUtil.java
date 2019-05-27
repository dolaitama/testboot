package com.sugon.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

/**
 * 日期工具类
 * 
 * 
 */
public class DateUtil {
	public static final String FORMAT_DATE = "yyyy-MM-dd";
	public static final String FORMAT_TIME = "HH:mm:ss";
	public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	private static Logger logger = Logger.getLogger(DateUtil.class);
	
	public static Timestamp now(){
		Date date = new Date();
		return new Timestamp(date.getTime());
	}

	public static String date2timestamp(Date date,String format){
		String result = null;
		if (date == null) {
			return result;
			}
		if (StringUtils.isEmpty(format)){
			format = "yyyy-MM-dd HH:mm:ss";
		}
		try{
			result = DateFormatUtils.format(date, format);
			result = result.replace("-", "");
			result = result.replace(":", "");
			result = result.replace(" ", "");
		} catch (Exception ex) {
			logger.warn("日期转换为字符串错误，日期：" + date.toString() + "， 格式：" + format);
		}
		
			return result;
		
	}
	
	public static String date2Str(Date date){
		return date2Str(date, FORMAT_DATETIME);
	}

	public static String date2Str(Date date, String format) {
		String result = null;
		if (date == null) {
			return result;
		}
		try {
			result = DateFormatUtils.format(date, format);
		} catch (Exception ex) {
			logger.warn("日期转换为字符串错误，日期：" + date.toString() + "， 格式：" + format);
		}

		return result;
	}

	public static Date string2Date(String str, String format) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		Date result = null;
		String[] formats = (String[]) null;
		if (StringUtils.isEmpty(format)) {
			formats = new String[3];
			formats[0] = "yyyy-MM-dd HH:mm:ss";
			formats[1] = "yyyy-MM-dd";
			formats[2] = "HH:mm:ss";
		} else {
			formats = new String[4];
			formats[0] = format;
			formats[1] = "yyyy-MM-dd HH:mm:ss";
			formats[2] = "yyyy-MM-dd";
			formats[3] = "HH:mm:ss";
		}
		try {
			result = DateUtils.parseDate(str, formats);
		} catch (Exception ex) {
			logger.warn("日期或时间格式不正确，日期时间字符串：" + str + "， 格式：" + format);
		}

		return result;
	}

	public static String timestamp2String(Timestamp ts, String format) {
		return ts == null ? null : date2Str(new Date(ts.getTime()), format);
	}

	public static Timestamp string2Timestamp(String str, String format) {
		return string2Date(str, format) == null ? null : new Timestamp(string2Date(str, format).getTime());
	}

	public static Date addYears(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(1, num);
		return cal.getTime();
	}

	public static Date addMonths(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(2, num);
		return cal.getTime();
	}

	public static Date addDays(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(6, num);
		return cal.getTime();
	}

	public static Date getFirstDateOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(6, cal.getActualMinimum(6));
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		return cal.getTime();
	}

	public static Date getFirstDateOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(5, cal.getActualMinimum(5));
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		return cal.getTime();
	}

	public static Date getLastDateOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(6, cal.getActualMaximum(6));
		cal.set(11, 23);
		cal.set(12, 59);
		cal.set(13, 59);
		return cal.getTime();
	}

	public static Date getLastDateOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(5, cal.getActualMaximum(5));
		cal.set(11, 23);
		cal.set(12, 59);
		cal.set(13, 59);
		return cal.getTime();
	}
	
	public static Date getMondayDate(Date date){
		Calendar cal = Calendar.getInstance();  
		cal.setTime(date);
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int day = cal.get(Calendar.DAY_OF_WEEK)==1?8:cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);
		return cal.getTime();
	}
	
	public static Date getSundayDate(Date date){
		Calendar cal = Calendar.getInstance();  
		cal.setTime(date);
		cal.set(11, 23);
		cal.set(12, 59);
		cal.set(13, 59);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int day = cal.get(Calendar.DAY_OF_WEEK)==1?8:cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, 8-day);
		return cal.getTime();
	}

	/**
	 * 计算两个日期之间相差的天数(date1-date2)
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long countDays(Date date1, Date date2) {
		long days = 0;
		days = date1.getTime() - date2.getTime();
		days = days / 1000 / 60 / 60 / 24;
		return days;
	}
	
	public static Date getDayStart(Date date){  
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
        return cal.getTime();  
    }  
      
	public static Date getDayEnd(Date date){  
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
		cal.set(11, 23);
		cal.set(12, 59);
		cal.set(13, 59);
        return cal.getTime();  
    }
	
	
	public static int getDayOfWeek(Date date){  
		Calendar cal = Calendar.getInstance();  
		cal.setTime(date);  
		return cal.get(Calendar.DAY_OF_WEEK);  
	}

	public static String nowStr() {
		return date2Str(new Date());
	}
}
