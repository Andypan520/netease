package common;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_WEEK = "yyyy-MM-dd HH:mm:ss E";
    public static final String DATE_TIME_MILSEC = "yyyy-MM-dd HH:mm:ss:SSS";
    public static final String DATE_TIME_MILSEC_WEEK = "yyyy-MM-dd HH:mm:ss:SSS E";
    public static final String yyyyMMdd = "yyyyMMdd";

    public static String now() {
        return now(DATE_TIME);
    }

    public static String now(String formatPattern) {
        LocalDateTime dateTime =

                LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
        String formattedDate = dateTime.format(formatter);
        return formattedDate;
    }


    public static LocalDateTime parse(String dateTimeStr, String formatPattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        return dateTime;
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


    public static Long string2Millis(String dateStr, String formatStr) {
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(formatStr)).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
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


    public static void main(String[] args) {
//        // System.out.println(string2Millis("2018-09-01", "yyyy-mm-dd"));
//        System.out.println(localDate2Millis(LocalDate.of(2018, 9, 1)));
//        System.out.println(localDateTime2Millis(LocalDateTime.of(2018, 9, 1, 0, 0, 0)));
    }


}
