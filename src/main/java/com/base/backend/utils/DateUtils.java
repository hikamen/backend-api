package com.base.backend.utils;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    private final static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    // 补全时间后面的（时分秒）部分
    public final static int DATETIME_PADEND_HHMMSS = 1;
    // 补全时间后面的（秒）分
    public final static int DATETIME_PADEND_SS = 2;

    // 最大和最小的时间日期
    public final static Date MIN_DATE;
    public final static Date MAX_DATE;
    public final static Timestamp MIN_TIMESTAMP;
    public final static Timestamp MAX_TIMESTAMP;

    static {
        MIN_DATE = new DateTime(1970, 1, 1, 0, 0, 0, 0).toDate();
        MAX_DATE = new DateTime(9999, 12, 31, 23, 59, 59, 0).toDate();

        MIN_TIMESTAMP = new Timestamp(MIN_DATE.getTime());
        MAX_TIMESTAMP = new Timestamp(MAX_DATE.getTime());
    }

    // 默认时区
    public final static String DEFAULT_TIME_ZONE = "GMT+8";
    // 默认时间格式
    public final static String DEFAULT_TIME_PATTEN = "HH:mm:ss";
    // 默认日期格式
    public final static String DEFAULT_DATE_PATTEN = "yyyy-MM-dd";
    // 默认日期时间格式
    public final static String DEFAULT_DATETIME_PATTEN = "yyyy-MM-dd HH:mm";
    // 默认时间戳格式
    public final static String DEFAULT_TIMESTAMP_PATTEN = "yyyy-MM-dd HH:mm:ss.SSS";

    // 当前支持的时间格式
    private final static String[] SUPPORT_PATTENS = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
            "yyyy年MM月dd日"
    };

    /**
     * 格式化日期（yyyy-MM-dd）
     */
    public static String formatDate(Date date) {
        return format(date, DEFAULT_DATE_PATTEN);
    }

    /**
     * 格式化日期（yyyy-MM-dd HH:mm）
     */
    public static String formatDatetime(Date date) {
        return format(date, DEFAULT_DATETIME_PATTEN);
    }

    /**
     * 格式化日期（yyyy-MM-dd HH:mm:ss.SSS）
     */
    public static String formatTimestamp(Date date) {
        return format(date, DEFAULT_TIMESTAMP_PATTEN);
    }

    /**
     * 格式化日期
     *
     * @param date    日期
     * @param pattern 格式
     * @return String
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        if (pattern == null) {
            return formatDate(date);
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 获取时间字符串（yyyy-MM-dd HH:mm:ss.SSS）
     */
    public static String getDateTime() {
        return getDateTime(new Date(), DEFAULT_DATETIME_PATTEN);
    }

    /**
     * 获取时间字符串（yyyy-MM-dd HH:mm:ss.SSS）
     */
    public static String getDateTime(Date data, String pattern) {
        return format(data, pattern);
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate(new Date());
    }

    public static String getDate(Date date) {
        return format(date, DEFAULT_DATE_PATTEN);
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return format(new Date(), DEFAULT_TIME_PATTEN);
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return getYear(new Date());
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear(Date data) {
        return format(data, "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return getMonth(new Date());
    }

    /**
     * 得到指定时间的月份字符串 格式（MM）
     */
    public static String getMonth(Date date) {
        return format(date, "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return getDay(new Date());
    }

    /**
     * 得到指定时间的日期字符串 格式（MM）
     */
    public static String getDay(Date date) {
        return format(date, "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return getWeek(new Date());
    }

    /**
     * 得到指定时间的星期字符串 格式（E）星期几
     */
    public static String getWeek(Date date) {
        return format(date, "E");
    }

    /**
     * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy.MM.dd",
     * "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDateTime(Object str) {
        return parse(str, DEFAULT_DATETIME_PATTEN);
    }

    public static Date parseTimestamp(Object str) {
        return parse(str, DEFAULT_TIMESTAMP_PATTEN);
    }

    public static Date parseDate(Object str) {
        return parse(str, DEFAULT_DATE_PATTEN);
    }

    public static Date parse(Object str, String patten) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), patten);
        } catch (ParseException e) {
            e.printStackTrace();

            // 默认解析错误后，尝试其他格式
            try {
                return parseDate(str.toString(), SUPPORT_PATTENS);
            } catch (ParseException ee) {
                logger.error("unsupprt date patten. [{} - {}]", str, patten, ee);
                ee.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取过去的天数
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     */
    public static String formatDatetime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 转换总时间（天，时:分:秒）
     */
    public static String formatTotalTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s;
    }

    /**
     * 获取两个日期之间的天数
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    public static String formatTimeLength(Integer lengthInSec) {
        int ONE_HOUR = 60 * 60, ONE_MINUTE = 60;
        if (lengthInSec == null || lengthInSec == 0)
            return "--";
        StringBuilder sb = new StringBuilder();
        int hour = lengthInSec / ONE_HOUR;
        if (hour > 0) {
            sb.append(hour).append("小时");
        }
        int minute = (lengthInSec - ONE_HOUR * hour) / ONE_MINUTE;
        if (minute > 0) {
            sb.append(minute).append("分钟");
        }
        int second = lengthInSec - ONE_HOUR * hour - ONE_MINUTE * minute;
        sb.append(second).append("秒");
        return sb.toString();
    }

    /**
     * 以友好的方式显示时间
     */
    public static String friendlyTime(Date time) {
        if (time == null) {
            return null;
        }

        long mul = System.currentTimeMillis() - time.getTime();
        long seconds = mul / 1000;

        if (seconds < 10) {
            return "刚刚";
        } else if (seconds < 60) {
            return seconds + "秒前";
        } else if (seconds < 3600) {
            return (seconds / 60) + "分钟前";
        } else if (seconds < 86400) {
            return (seconds / 3600) + "小时前";
        } else if (seconds < 604800) {
            return (seconds / 86400) + "天前";
        } else if (seconds < 2592000) {
            return (seconds / 604800) + "周前";
        } else if (seconds < 31536000) {
            return (seconds / 2592000) + "个月前";
        } else {
            return (seconds / 31536000) + "年前";
        }
    }

    /**
     * 是否同一年
     */
    public static boolean isSameYear(final Date date1, final Date date2) {
        Calendar c1 = toCalendar(date1);
        Calendar c2 = toCalendar(date2);
        return isSameYear(c1, c2);
    }

    /**
     * 是否同一年
     */
    public static boolean isSameYear(final Calendar c1, final Calendar c2) {
        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) {
            return true;
        }
        return false;
    }

    /**
     * 是否同一年同一个星期
     */
    public static boolean isSameWeekInSameYear(final Date date1, final Date date2) {
        Calendar c1 = toCalendar(date1);
        Calendar c2 = toCalendar(date2);
        return isSameWeekInSameYear(c1, c2);
    }

    /**
     * 是否同一年同一个星期
     */
    public static boolean isSameWeekInSameYear(final Calendar c1, final Calendar c2) {
        if (c1.get(Calendar.WEEK_OF_YEAR) == c2.get(Calendar.WEEK_OF_YEAR)) {
            if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否同一个星期, 不比较年份
     */
    public static boolean isSameWeek(final Date date1, final Date date2) {
        Calendar c1 = toCalendar(date1);
        Calendar c2 = toCalendar(date2);
        return isSameWeek(c1, c2);
    }

    /**
     * 是否同一个星期, 不比较年份
     */
    public static boolean isSameWeek(final Calendar c1, final Calendar c2) {
        if (c1.get(Calendar.WEEK_OF_YEAR) == c2.get(Calendar.WEEK_OF_YEAR)) {
            return true;
        }
        return false;
    }

    /**
     * 是否相同月份， 不比较年份
     */
    public static boolean isSameMonth(final Date date1, final Date date2) {
        Calendar c1 = toCalendar(date1);
        Calendar c2 = toCalendar(date2);
        return isSameMonth(c1, c2);
    }

    /**
     * 是否相同月份， 不比较年份
     */
    public static boolean isSameMonth(final Calendar c1, final Calendar c2) {
        if (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)) {
            return true;
        }
        return false;
    }

    /**
     * 是否相同月份， 并且年份相同
     */
    public static boolean isSameMonthInSameYear(final Date date1, final Date date2) {
        Calendar c1 = toCalendar(date1);
        Calendar c2 = toCalendar(date2);
        return isSameMonthInSameYear(c1, c2);
    }

    /**
     * 是否相同月份， 并且年份相同
     */
    public static boolean isSameMonthInSameYear(final Calendar c1, final Calendar c2) {
        if (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)) {
            if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 间隔时间
     */
    public static Long getTimeBetween(Date startTime, Date endTime) {
        return endTime.getTime() - startTime.getTime();
    }

    /**
     * 间隔天数
     */
    public static Integer getDayBetween(Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);

        long n = end.getTimeInMillis() - start.getTimeInMillis();
        return (int) (n / (60 * 60 * 24 * 1000l));
    }

    /**
     * 间隔月
     */
    public static Integer getMonthBetween(Date startDate, Date endDate) {
        if (startDate == null || endDate == null || !startDate.before(endDate)) {
            return null;
        }
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        int year1 = start.get(Calendar.YEAR);
        int year2 = end.get(Calendar.YEAR);
        int month1 = start.get(Calendar.MONTH);
        int month2 = end.get(Calendar.MONTH);
        int n = (year2 - year1) * 12;
        n = n + month2 - month1;
        return n;
    }

    /**
     * 间隔月，多一天就多算一个月
     */
    public static Integer getMonthBetweenWithDay(Date startDate, Date endDate) {
        if (startDate == null || endDate == null || !startDate.before(endDate)) {
            return null;
        }
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        int year1 = start.get(Calendar.YEAR);
        int year2 = end.get(Calendar.YEAR);
        int month1 = start.get(Calendar.MONTH);
        int month2 = end.get(Calendar.MONTH);
        int n = (year2 - year1) * 12;
        n = n + month2 - month1;
        int day1 = start.get(Calendar.DAY_OF_MONTH);
        int day2 = end.get(Calendar.DAY_OF_MONTH);
        if (day1 <= day2) {
            n++;
        }
        return n;
    }

    /**
     * 日期计算
     */
    public static Date addDate(Date date, int field, int amount) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 判断日期是否是最小的时间戳，只判断日期部分，忽略时间部分
     */
    public static boolean isMinDate(Date date) {
        return date == null
                || formatDate(MIN_DATE).equalsIgnoreCase(formatDate(date))
                || new DateTime(date).isBefore(new DateTime(MIN_DATE));
    }

    /**
     * 判断时间戳是否是最小的时间戳，只判断日期部分，忽略时间部分
     */
    public static boolean isMinTimestamp(Timestamp timestamp) {
        return timestamp == null
                || formatDate(MIN_TIMESTAMP).equalsIgnoreCase(formatDate(timestamp))
                || new DateTime(timestamp).isBefore(new DateTime(MIN_TIMESTAMP));
    }

    /**
     * 判断日期是否是最大的时间戳，只判断日期部分，忽略时间部分
     */
    public static boolean isMaxDate(Date date) {
        return date == null
                || formatDate(MAX_DATE).equalsIgnoreCase(formatDate(date))
                || new DateTime(date).isAfter(new DateTime(MAX_DATE));
    }

    /**
     * 判断时间戳是否是最大的时间戳，只判断日期部分，忽略时间部分
     */
    public static boolean isMaxTimestamp(Timestamp timestamp) {
        return timestamp == null
                || formatDate(MAX_TIMESTAMP).equalsIgnoreCase(formatDate(timestamp))
                || new DateTime(timestamp).isAfter(new DateTime(MAX_TIMESTAMP));
    }

    /**
     * 处理开始日期
     * 1. 如果开始日期为空，则返回默认最小的日期
     * 2. 如果开始日期不为空，默认处理时间部分为00:00:00.000
     *
     * @param date 开始日期
     * @return Date
     */
    public static Date processStartDate(Date date) {
        return processStartDate(date, DATETIME_PADEND_HHMMSS);
    }

    /**
     * 处理开始日期
     * 1. 如果开始日期为空，则返回默认最小的日期
     * 2. 如果开始日期不为空，则按补全类型处理时间部分
     *
     * @param date       结束日期
     * @param padEndType 补全类型
     * @return Timestamp
     */
    public static Date processStartDate(Date date, final int padEndType) {
        if (date == null) {
            return MIN_DATE;
        }
        if (DATETIME_PADEND_HHMMSS == padEndType) {
            date = new DateTime(date).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).toDate();
        } else if (DATETIME_PADEND_SS == padEndType) {
            date = new DateTime(date).withSecondOfMinute(0).withMillisOfSecond(0).toDate();
        }
        return date;
    }

    /**
     * 处理结束日期
     * 1. 如果结束日期为空，则返回默认最大的日期
     * 2. 如果结束日期不为空，默认处理时间部分为00:00:00.000
     *
     * @param date 开始日期
     * @return Date
     */
    public static Date processEndDate(Date date) {
        return processEndDate(date, DATETIME_PADEND_HHMMSS);
    }

    /**
     * 处理结束日期
     * 1. 如果结束日期为空，则返回默认最大的日期
     * 2. 如果结束日期不为空，则按补全类型处理时间部分
     *
     * @param date       结束日期
     * @param padEndType 补全类型
     * @return Timestamp
     */
    public static Date processEndDate(Date date, final int padEndType) {
        if (date == null) {
            return MAX_DATE;
        }
        if (DATETIME_PADEND_HHMMSS == padEndType) {
            date = new DateTime(date)
                    .withHourOfDay(23)
                    .withMinuteOfHour(59)
                    .withSecondOfMinute(59)
                    .withMillisOfSecond(0).toDate();
        } else if (DATETIME_PADEND_SS == padEndType) {
            date = new DateTime(date).withSecondOfMinute(59).withMillisOfSecond(0).toDate();
        }
        return date;
    }

    /**
     * 处理开始时间戳
     * 1. 如果开始时间为空，则返回默认最小的时间戳
     * 2. 如果开始时间不为空，默认处理时间部分为00:00:00.000
     *
     * @param timestamp 结束时间
     * @return Timestamp
     */
    public static Timestamp processStartTimestamp(Timestamp timestamp) {
        return processStartTimestamp(timestamp, DATETIME_PADEND_HHMMSS);
    }

    /**
     * 处理开始时间戳
     * 1. 如果开始时间为空，则返回默认最小的时间戳
     * 2. 如果开始时间不为空，则按补全类型处理时间部分
     *
     * @param timestamp  结束时间
     * @param padEndType 补全类型
     * @return Timestamp
     */
    public static Timestamp processStartTimestamp(Timestamp timestamp, final int padEndType) {
        if (timestamp == null) {
            return MIN_TIMESTAMP;
        }
        if (DATETIME_PADEND_HHMMSS == padEndType) {
            timestamp = new Timestamp(new DateTime(timestamp)
                    .withHourOfDay(0)
                    .withMinuteOfHour(0)
                    .withSecondOfMinute(0)
                    .withMillisOfSecond(0).getMillis()
            );
        } else if (DATETIME_PADEND_SS == padEndType) {
            timestamp = new Timestamp(new DateTime(timestamp)
                    .withSecondOfMinute(0)
                    .withMillisOfSecond(0).getMillis()
            );
        }
        return timestamp;
    }

    /**
     * 处理结束时间戳
     * 1. 如果结束时间戳为空，则返回默认最大的时间戳
     * 2. 如果结束时间戳不为空，默认处理时间部分为23:59:59.999
     *
     * @param timestamp 结束时间
     * @return Timestamp
     */
    public static Timestamp processEndTimestamp(Timestamp timestamp) {
        return processEndTimestamp(timestamp, DATETIME_PADEND_HHMMSS);
    }

    /**
     * 处理结束时间戳
     * 1. 如果结束时间戳为空，则返回默认最大的时间戳
     * 2. 如果结束时间戳不为空，则按补全类型处理时间部分
     *
     * @param timestamp  结束时间
     * @param padEndType 补全类型
     * @return Timestamp
     */
    public static Timestamp processEndTimestamp(Timestamp timestamp, final int padEndType) {
        if (timestamp == null) {
            return MAX_TIMESTAMP;
        }

        if (DATETIME_PADEND_HHMMSS == padEndType) {
            timestamp = new Timestamp(
                    new DateTime(timestamp)
                            .withHourOfDay(23)
                            .withMinuteOfHour(59)
                            .withSecondOfMinute(59)
                            .withMillisOfSecond(0).getMillis()
            );
        } else if (DATETIME_PADEND_SS == padEndType) {
            timestamp = new Timestamp(
                    new DateTime(timestamp)
                            .withSecondOfMinute(59)
                            .withMillisOfSecond(0).getMillis()
            );
        }
        return timestamp;
    }
}
