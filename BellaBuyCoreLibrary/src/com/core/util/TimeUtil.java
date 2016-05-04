package com.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author miaoxin.ye
 * @createdate 2013-12-15 下午2:28:28
 * @Description: 时间工具类
 */
public class TimeUtil {
	
	public static final DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final DateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat sdf4 = new SimpleDateFormat("yyyy年MM月dd日");
	public static final DateFormat sdf5 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

	/**
	 * @Description: 将字符串转成日期
	 * @param df
	 * @param date
	 * @return
	 */
	public static Date parseDate(DateFormat df, String date) {
		try {
			return df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @Description: 日期转换成字符串
	 * @param df
	 * @param date
	 * @return
	 */
	public static String parseDateToString(DateFormat df, Date date) {
		return df.format(date);
	}
	
	/**
	 * @Description: long时间转换为字符串
	 * @param longDate
	 * @param str
	 * @return
	 */
	public static String parseLongToString(long longDate, DateFormat str) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(longDate);
		return str.format(calendar.getTime());
	}
	
	/**
	 * long时间转换为日期
	 * @param longDate
	 * @return
	 */
	public static Date parseLongToDate(long longDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(longDate);
		return calendar.getTime();
	}
	
	/**
	 * 获取xx天xx小时xx分xx秒
	 * @param intervalTime
	 * @return
	 */
	public static String getStringTime(long intervalTime) {
		StringBuilder result = new StringBuilder();
		long interval = intervalTime / 1000;
		final long day = 24 * 60 * 60;
		final long hour = 60 * 60;
		final long minute = 60;
		int detailDay = 0;
		int detailHour = 0;
		int detailMinute = 0;
		int detailSecond = 0;
		if (interval >= day) {
			detailDay = (int) (interval / day);
			interval = interval - detailDay * day;
		}if (interval >= hour) {
			detailHour = (int) (interval / hour);
			interval = interval - hour * detailHour;
		}if (interval >= minute) {
			detailMinute = (int) (interval / minute);
			interval = interval - detailMinute * minute;
		}if (interval > 0) {
			detailSecond = (int) interval;
		}
		result.setLength(0);
		if (detailDay > 0) {
			result.append(detailDay);
			result.append("天");
		}if (detailHour > 0) {
			result.append(detailHour);
			result.append("小时");
		}if (detailMinute > 0) {
			result.append(detailMinute);
			result.append("分");
		}if (detailSecond > 0) {
			result.append(detailSecond);
			result.append("秒");
		}
		return result.toString();
	}

}
