package common;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@UtilityClass
public class DateUtil {
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_WEEK = "yyyy-MM-dd HH:mm:ss E";
    public static final String DATE_TIME_MILSEC = "yyyy-MM-dd HH:mm:ss:SSS";
    public static final String DATE_TIME_MILSEC_WEEK = "yyyy-MM-dd HH:mm:ss:SSS E";
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String yyyy_MM_dd_ZH = "yyyy年MM月dd日";
    public static final String HHmm = "HH:mm";
    public static final String YYYY_MM = "YYYY-MM";

    public static String now() {
        return now(DATE_TIME);
    }

    public static String now(String formatPattern) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
        String formattedDate = dateTime.format(formatter);
        return formattedDate;
    }

    /**
     * 返回今天凌晨0点的时间戳
     *
     * @return hzliyong
     */
    public static long getTodayEarliestTimestamp() {
        return LocalDate.now().atStartOfDay(ZoneOffset.ofHours(8)).toEpochSecond() * 1000;
    }

    /**
     * 返回当前时间的昨天凌晨0点的时间戳
     *
     * @return hzliyong
     */
    public static long getYesterdayEarliestTimestamp() {
        return LocalDate.now().minusDays(1).atStartOfDay(ZoneOffset.ofHours(8)).toEpochSecond() * 1000;
    }

    /**
     * @Description: 获取本周第一天的凌晨0点的时间戳
     * @param: []
     * @Auth: hzliyong
     * @return: long
     * @Date: 2019/1/17 0017
     * @Time: 20:52
     */
    public static long getCurrentWeekFirstDayEarliestTimestamp() {
        return getCurrentWeekAnyDayEarlieastTimestamp(Calendar.MONDAY);
    }

    /**
     * @Description: 获取本周任意一天的对应的凌晨0点时间戳，dayOfWeek传入值为Calendar.MONDAY,Calendar.THURSDAY...
     * @param: [dayOfWeek]
     * @Auth: hzliyong
     * @return: long
     * @Date: 2019/1/20 0020
     * @Time: 12:10
     */
    public static long getCurrentWeekAnyDayEarlieastTimestamp(int dayOfWeek) {
        TimeZone currentTimeZone = TimeZone.getTimeZone("GMT+8");
        Calendar cal = Calendar.getInstance(currentTimeZone);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return cal.getTimeInMillis();
    }

    /**
     * @Description: 获取本周任意一天的对应的23:59:59的时间戳，dayOfWeek传入值为Calendar.MONDAY,Calendar.THURSDAY...
     * @param: [dayOfWeek]
     * @Auth: hzliyong
     * @return: long
     * @Date: 2019/1/20 0020
     * @Time: 12:13
     */
    public static long getCurrentWeekAnyDayLatestlieastTimestamp(int dayOfWeek) {
        TimeZone currentTimeZone = TimeZone.getTimeZone("GMT+8");
        Calendar cal = Calendar.getInstance(currentTimeZone);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return cal.getTimeInMillis();
    }

    /**
     * @Description: 获取本周最后一天的23:59:59的时间戳
     * @param: []
     * @Auth: hzliyong
     * @return: long
     * @Date: 2019/1/17 0017
     * @Time: 21:48
     */
    public static long getCurrentWeekLastDayLatestliestTimestamp() {
        return getCurrentWeekAnyDayLatestlieastTimestamp(Calendar.SUNDAY);
    }

    /**
     * 获取今天的日期，格式为formatePattern
     *
     * @return hzliyong
     */
    public static String getTodayDate(String formatPattern) {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
        return localDate.format(formatter);
    }

    /**
     * 将时间戳转化成formatPattern格式的时间
     *
     * @param timestamp
     * @param formatPattern
     * @return hzliyong
     */
    public static String millis2StringDate(long timestamp, String formatPattern) {
        LocalDateTime dateTime = converToLocalDatetime(timestamp);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
        return dateTime.format(formatter);
    }

    public static LocalDateTime parseToLoaclDataTime(String dateTimeStr, String formatPattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        return dateTime;
    }

    public static LocalDate parseToLocalDate(String dateTxt, String formatPattern) {
        return LocalDate.parse(dateTxt, DateTimeFormatter.ofPattern(formatPattern));
    }


    /**
     * convert Date before jdk8 to LocalDate
     */
    public static String convertDateToLocalDateString(Date date, String pattern) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String formattedDate = localDateTime.format(formatter);
        return formattedDate;
    }


    public static Date convertToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
//       精确到秒
        return Date.from(Instant.ofEpochSecond(zdt.toInstant().getEpochSecond()));
    }

    public static LocalDateTime converToLocalDatetime(long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp / 1000, 0, ZoneOffset.ofHours(8));
    }


    public static Date convertLongToDate(long timestamp) {
        return convertToDate(converToLocalDatetime(timestamp));
    }

    public static String dateTime2String(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    public static String date2Format(LocalDate localDate, String format) {
        return localDate.format(DateTimeFormatter.ofPattern(format));
    }


    public static Long string2Millis(String dateStr, String formatStr) {
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(formatStr)).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    /**
     * 将字符串的日期转化成秒，如dateStr="2018-12-09"， formatStr="yyyy-MM-dd"
     * 转成秒时为当前日期的00:00:00
     *
     * @param dateStr
     * @param formatStr
     * @return hzliyong
     */
    public static Long stringDate2Seconds(String dateStr, String formatStr) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(formatStr)).atStartOfDay(ZoneOffset.ofHours(8)).toEpochSecond();
    }

    /**
     * 将字符串的日期转化成毫秒，如dateStr="2018-12-09"， formatStr="yyyy-MM-dd"
     * 转成秒时为当前日期的00:00:00 000
     *
     * @param dateStr
     * @param formatStr
     * @return hzliyong
     */
    public static Long stringDate2Millis(String dateStr, String formatStr) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(formatStr)).atStartOfDay(ZoneOffset.ofHours(8)).toEpochSecond() * 1000;
    }


    public static LocalDateTime millis2LocalDateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static LocalDate millis2LocalDate(long timestamp) {
        return DateUtil.millis2LocalDateTime(timestamp).toLocalDate();
    }


    public static Long getMillis() {
        return Instant.now().toEpochMilli();
    }


    public static Long localDateTime2Millis(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    public static Long localDate2Millis(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.MIN).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }


    public static Long clock2Millis(Clock clock) {
        return clock.millis();
    }


    public static Long zoneDateTime2Millis(ZonedDateTime zonedDateTime) {
        return zonedDateTime.toLocalDateTime().toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    public static String format(LocalDate localDate, String formatPattern) {
        return localDate.format(DateTimeFormatter.ofPattern(formatPattern));
    }

    /**
     * 获取指定日期某个整点时刻毫秒值
     */
    public static Long getSpecificHourMiles(LocalDate localDate, long hour) {
        return LocalDateTime.of(localDate, LocalTime.MIN)
                .plusHours(hour)
                .toInstant(ZoneOffset.ofHours(8))
                .toEpochMilli();
    }

    public static LocalDate parse(String date, String formatter) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(formatter));
    }

    public static boolean isDuringTheRange(LocalDate specificDate, LocalDate start, LocalDate end) {
        long thatDay = specificDate.toEpochDay();
        return thatDay >= start.toEpochDay() && thatDay <= end.toEpochDay();
    }


    /**
     * 一个日期 加上 多少 周
     *
     * @param date
     * @param weeks
     * @return
     */

    public static LocalDate plusWeeks(LocalDate date, int weeks) {
        return date.plus(weeks, ChronoUnit.WEEKS);
    }

    /**
     * 获取给定年份的第一个星期一
     *
     * @param year
     * @return
     */
    public static LocalDate getFirstMondayOfYear(int year) {
        LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);

        DayOfWeek dayOfWeek = firstDayOfYear.getDayOfWeek();

        switch (dayOfWeek) {
            case MONDAY:
                break;
            case TUESDAY:
                firstDayOfYear = firstDayOfYear.plusDays(6);
                break;
            case WEDNESDAY:
                firstDayOfYear = firstDayOfYear.plusDays(5);
                break;
            case THURSDAY:
                firstDayOfYear = firstDayOfYear.plusDays(4);
                break;
            case FRIDAY:
                firstDayOfYear = firstDayOfYear.plusDays(3);
                break;
            case SATURDAY:
                firstDayOfYear = firstDayOfYear.plusDays(2);
                break;
            case SUNDAY:
                firstDayOfYear = firstDayOfYear.plusDays(1);
                break;
            default:
                break;
        }
        return firstDayOfYear;
    }

    /**
     * 定位这一天是*当年*的第几周
     * 比如 2019-01-07 是 2019年第一周，返回1，但是如果是 2019-01-01，则返回0.因为从星期的角度看，他还属于2018.
     *
     * @param day
     * @return
     */
    public static int getWeekOfCurrentYear(LocalDate day) {
        LocalDate firstMondy = getFirstMondayOfYear(day.getYear());
        int day2 = day.getDayOfYear();
        int day1 = firstMondy.getDayOfYear();
        if (day1 > day2) {
            return 0;
        }
        return (day2 - day1) / 7 + 1;
    }

    /**
     * 例如 2019-01-01. 他是53周（2018年的）
     *
     * @return
     */
    public static int getWeekOfCrossYear() {
        LocalDate localDate = LocalDate.now();
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 7);
        return localDate.get(weekFields.weekOfWeekBasedYear());
    }

    /**
     * @Description: 判断当前日期是周几，从周一返回1
     * @param: []
     * @Auth: hzliyong
     * @return: int
     * @Date: 2019/1/19 0019
     * @Time: 10:33
     */
    public static int getWeekNumber() {
        LocalDate now = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.CHINA);
        //本周的第一天是周日，所以要减1
        return now.get(weekFields.dayOfWeek()) - 1;
    }

    /**
     * 一天的结束时间
     *
     * @return "yyyy-MM-ddT59:59"
     */
    public static LocalDateTime getEndOfDay(@NonNull LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.MAX);
    }

    /**
     * 本周周日
     *
     * @return LocalDate "yyyy-MM-dd"
     */
    public static LocalDate getEndOfWeek() {
        return getEndOfWeek(LocalDate.now());
    }

    /**
     * 周日
     *
     * @return LocalDate "yyyy-MM-dd"
     */
    public static LocalDate getEndOfWeek(@NonNull LocalDate localDate) {
        return localDate.with(ChronoField.DAY_OF_WEEK, 7);
    }

}
