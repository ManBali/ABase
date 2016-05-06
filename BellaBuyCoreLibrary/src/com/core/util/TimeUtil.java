package com.core.util;

import android.annotation.SuppressLint;
import android.net.ParseException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具
 * @author sufun
 * @createtime 2015年7月20日 17:13:03
 * @describe 时间获取工具
 */
public class TimeUtil {

	public static String getUTCTimeStr() {

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//yyyy-MM-dd hh:mm:ss

		StringBuffer UTCTimeBuffer = new StringBuffer();

		// 1、取得本地时间：

		Calendar cal = Calendar.getInstance();

		// 2、取得时间偏移量：
		int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);

		// 3、取得夏令时差：

		int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);

		// 4、从本地时间里扣除这些差量，即可以取得UTC时间：

		cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));

		int year = cal.get(Calendar.YEAR);

		int month = cal.get(Calendar.MONTH) + 1;

		int day = cal.get(Calendar.DAY_OF_MONTH);

		int hour = cal.get(Calendar.HOUR_OF_DAY);

		int minute = cal.get(Calendar.MINUTE);

		int second = cal.get(Calendar.SECOND);

		UTCTimeBuffer.append(year).append("-").append(month).append("-")

				.append(day);

		UTCTimeBuffer.append(" ").append(hour).append(":").append(minute)

				.append(":").append(second);

		try {

			format.parse(UTCTimeBuffer.toString());

			return UTCTimeBuffer.toString();

		} catch (ParseException e) {

			e.printStackTrace();

		} catch (java.text.ParseException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}
		return null;

	}
	private static SimpleDateFormat sf = null;
    /**
     * 比较出两个时间相差的秒数	
     * @param startTime
     * @param endTime
     * @return
     */
	public static long getTimeDiff(long	 startTime,long endTime)
	{
    	Date sd=new Date(startTime);//
    	Date ed=new Date(endTime);
    	
    	Long diff=ed.getTime()-sd.getTime();
        
    	return diff/1000;
	}
	/**
	 * 根据时间错返回当天的日期
	 * @param time
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String timeCuo2Date(long time)
	{
		   Date d = new Date(time);
		   sf=new SimpleDateFormat("yyyy-MM-dd");
		   return sf.format(d);
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String timeCuo2Time(long time)
	{
	   Date d = new Date(time);
	   sf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	   
	   return sf.format(d);
	}	
	/*将字符串转为时间戳*/
    public static long getStringToDate(String time) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        try{
            try {
				date = sdf.parse(time);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }
    /**将日期转化为时间戳***/
    public static long Date2TimeCuo(String time)
    {
    	SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
    	Date date=null ;
    	 try {
			 date = format.parse(time);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return System.currentTimeMillis();
		}
    	return date.getTime();
    	
    }
    
    public static String getDiffTime(long startTime,long endTime)
    {
    	Date sd=new Date(startTime);//
    	Date ed=new Date(endTime);
    	
    	Long diff=ed.getTime()-sd.getTime();
    	
    	Long day = diff / (1000 * 60 * 60 * 24);          //以天数为单位取整
        Long hour=(diff/(60*60*1000)-day*24);             //以小时为单位取整
        Long min=((diff/(60*1000))-day*24*60-hour*60);    //以分钟为单位取整
        Long secone=(diff/1000-day*24*60*60-hour*60*60-min*60);
    	return hour+":"+min+":"+secone;
    }

	/**
	 * 取得活动还剩下的时间
	 *
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getRestTime(String end_time) {
		try {
			long nowTime = System.currentTimeMillis();
			long endTime = Long.parseLong(end_time);

			long l = endTime - nowTime;//结束时间-现在的时间-时区差 //- zoneOffset
			if(l <= 0){
				return "";
			}
			long day = l / (24 * 60 * 60 * 1000);
			long hour = (l / (60 * 60 * 1000) - day * 24);
			long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

			String minn = min >= 10 ? String.valueOf(min) : "0" + min;
			String ss = s >= 10 ? String.valueOf(s) : "0"+s;

			String result = "";
			if(day == 0){
				result = hour + ":" + minn + ":" + ss;
			} else {
				result = day + " дн. " + hour + ":" + minn + ":" + ss;
			}
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.D("   getRestTime error" + e.toString());
		}

		return "";
	}

	public static String getTimeStamp(String timeStamp){
		if(timeStamp == null){
			return "";
		}
		long currentTime = System.currentTimeMillis();
		long inputTime = Long.parseLong(timeStamp);
		long timeDValue = currentTime - inputTime;

		long day = 0;
		long hour = 0;
		long min = 0;
		long s = 0;

		String returnValue = "";
		if(timeDValue > 0){
			day = timeDValue / (24 * 60 * 60 * 1000);
			hour = (timeDValue / (60 * 60 * 1000) - day * 24);
			min = ((timeDValue / (60 * 1000)) - day * 24 * 60 - hour * 60);
			s = (timeDValue / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		}

		if(day > 0){
			returnValue = String.valueOf(day);
			if(hour > 0){
				returnValue = String.valueOf(day + 1);
			}
			returnValue = returnValue + " дн.";
		} else if(hour > 0){
			returnValue = String.valueOf(hour);
			if(min > 0){
				returnValue = String.valueOf(hour + 1);
			}
			returnValue = returnValue + " час.";
		} else if(min > 0){
			returnValue = String.valueOf(min);
			if(s > 0){
				returnValue = String.valueOf(min + 1);
			}
			returnValue = returnValue + " мин.";
		} else if(s > 0){
			returnValue = "1" + " мин.";;
		}
		return returnValue;
	}


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
		} catch (java.text.ParseException e) {
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
