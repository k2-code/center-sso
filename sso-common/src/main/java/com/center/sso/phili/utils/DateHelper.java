package com.center.sso.phili.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateHelper {
    public static final String DATEFORMAT_STR = "yyyy-MM-dd";
    public static final String YYYY_MM_DATEFORMAT_STR = "yyyy-MM";
    public static final String MMdd = "MM.dd";
    public static final String DATETIMEF_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_STR = "yyyy-MM-dd HH:mm";
    public static final String ZHCN_DATE_FORMAT = "yyyy年MM月dd日";
    public static final String ZHCN_DATETIME_FORMAT = "yyyy年MM月dd日HH时mm分ss秒";
    public static final String ZHCN_DATETIME_FORMAT_4yMMddHHmm = "yyyy年MM月dd日HH时mm分";
    public static final String INFLUX_DB_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String ISO8601 = "yyyy-MM-dd'T'hh:mm:ssZ";
    public static final String ZHCN_EEEE = "EEEE";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String HHMMSS = "HHmmss";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String SSMMHHDDMMYY = "ssmmHHddMMyy";
    public static final Integer HOUR_RADIX = 24;
    public static final Integer MINUTE_RADIX = 60;
    public static final Integer SECOND_RADIX = 60;
    public static final Integer MILLISECOND_RADIX = 1000;
    private static String datePattern = "yyyy-MM-dd";
    private static String timePattern = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
    private static Logger logger = LoggerFactory.getLogger(DateHelper.class);

    public DateHelper() {
    }

    public static String getNowTime() {
        return date2Str(new Date(), timePattern);
    }

    public static String getNowDate() {
        return date2Str(new Date(), datePattern);
    }

    public static String getNowTime(String pattern) {
        return date2Str(new Date(), pattern);
    }

    public static final String str2Str(String strDate, String oldPattern, String newPattern) {
        return date2Str(str2Date(strDate, oldPattern), newPattern);
    }

    public static final String getDateStr(Date date) {
        return date2Str(date, "yyyy-MM-dd");
    }

    public static final String getTimeStr(Date date) {
        return date2Str(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static final String date2Str(Date date, String pattern) {
        return date == null ? "" : (new SimpleDateFormat(pattern)).format(date);
    }

    public static final Date str2Date(String strDate) {
        try {
            return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(strDate);
        } catch (Exception var2) {
            logger.error(var2.getMessage(), var2);
            return null;
        }
    }

    public static final Date str2Date(String strDate, String pattern) {
        try {
            return (new SimpleDateFormat(pattern)).parse(strDate);
        } catch (Exception var3) {
            logger.error(var3.getMessage(), var3);
            return null;
        }
    }

    public static Date getThatDay(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(5, days);
        return calendar.getTime();
    }

    public static Date getThatDay(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, days);
        return calendar.getTime();
    }

    public static String getThatDay(int days, String pattern) {
        return date2Str(getThatDay(days), pattern);
    }

    public static String getThatDay(Date date, int days, String thatPattern) {
        return date2Str(getThatDay(date, days), thatPattern);
    }

    public static Date getThatDay(String strDate, String pattern, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(str2Date(strDate, pattern));
        calendar.add(5, days);
        return calendar.getTime();
    }

    public static int compareDate(Date date1, Date date2) {
        if (date1.getTime() > date2.getTime()) {
            return -1;
        } else {
            return date1.getTime() < date2.getTime() ? 1 : 0;
        }
    }

    public static int compareDate(String date1, String date1Pattern, String date2, String date2Pattern) {
        Date date11 = str2Date(date1, date1Pattern);
        Date date22 = str2Date(date2, date2Pattern);
        return compareDate(date11, date22);
    }

    public static String dvalueToString(Date begin, Date end) {
        long between;
        if (end.getTime() > begin.getTime()) {
            between = (end.getTime() - begin.getTime()) / 1000L;
        } else {
            if (end.getTime() >= begin.getTime()) {
                return "0秒";
            }

            between = (begin.getTime() - end.getTime()) / 1000L;
        }

        return secondToTimeStr(between);
    }

    public static String secondToTimeStr(Long between) {
        long day = between / 86400L;
        long hour = between % 86400L / 3600L;
        long minute = between % 3600L / 60L;
        long second = between % 60L / 60L;
        StringBuffer sb = new StringBuffer();
        if (day != 0L) {
            sb.append(day + "天");
        }

        if (hour != 0L) {
            sb.append(hour + "小时");
        }

        if (minute != 0L) {
            sb.append(minute + "分");
        }

        if (second != 0L) {
            sb.append(second + "秒");
        }

        return sb.toString();
    }

    public static long dvalues(Date begin, Date end) {
        if (begin == null || end == null) {
            logger.error("开始时间[{}]，结束时间[{}]", begin, end);
        }

        long between;
        if (end.getTime() > begin.getTime()) {
            between = (end.getTime() - begin.getTime()) / 1000L;
        } else {
            if (end.getTime() >= begin.getTime()) {
                return 0L;
            }

            between = (begin.getTime() - end.getTime()) / 1000L;
        }

        return between;
    }

    public static Date getCurrentTime(int stuff) {
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(12, stuff);
        Date beforeD = beforeTime.getTime();
        return beforeD;
    }

    public static long calInterval(Date startDate) {
        long a = (new Date()).getTime();
        long b = startDate.getTime();
        long c = (a - b) / 1000L;
        return c;
    }

    public static String getEndTime(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(5, days);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        return getTimeStr(calendar.getTime());
    }

    public static Date getLocalTime(String strDate, String pattern) {
        SimpleDateFormat utcSdf = new SimpleDateFormat(pattern);
        utcSdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            return utcSdf.parse(strDate);
        } catch (Exception var4) {
            logger.error(var4.getMessage(), var4);
            return null;
        }
    }

    public static Date getLocalTime(Date date) {
        String localStr = date2Str(date, "yyyy-MM-dd HH:mm:ss");
        longSdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            return longSdf.parse(localStr);
        } catch (Exception var3) {
            logger.error(var3.getMessage(), var3);
            return null;
        }
    }

    public static String getUTCTimeStr(Date date) {
        longSdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return longSdf.format(date);
    }

    public static String getUTCTimeStr(String dateStr) {
        Date date = str2Date(dateStr);
        longSdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return longSdf.format(date);
    }

    public static String UTC2Str(String UTCTime) {
        Date date = getLocalTime(UTCTime.replace("Z", " UTC"), "yyyy-MM-dd'T'HH:mm:ss");
        return date2Str(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }

        return formatTimeStr;
    }

    public static String getCron(Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date, dateFormat);
    }
}
