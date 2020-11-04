package com.zgx.common.util;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


public class TimetransUtil {

    /**
     * 传入String类型日期(ISO8601标准时间:yyyy-MM-dd'T'HH:mm:ss.SSS'Z')，返回字符串类型时间(yyyy-MM-dd HH:mm:ss)
     *
     * @return
     */
    public static String getDateStrFromISO8601Timestamp(String timeStr) {
        DateTimeFormatter dtf1 = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmssZ");
        DateTime dt = dtf1.parseDateTime(timeStr);
        DateTimeFormatter dtf2 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        return dt.toString(dtf2);
    }

    public static LocalDateTime getLocalDateTimeFromStr(String ISOdate) {
        java.time.format.DateTimeFormatter dateTimeFormatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime=LocalDateTime.parse(ISOdate,dateTimeFormatter);
        return localDateTime;
    }

    /**
     * 和上面的jar包不一样
     * 传入String类型日期(yyyy-MM-dd HH:mm:ss)，返回字符串类型时间（ISO8601标准时间）
     *
     * @param timestamp
     * @return
     */
    public static String getISO8601TimestampFromDateStr(String timestamp) {
        java.time.format.DateTimeFormatter dtf1 = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(timestamp, dtf1);
        ZoneOffset offset = ZoneOffset.of("+08:00");
        OffsetDateTime date = OffsetDateTime.of(ldt, offset);
        java.time.format.DateTimeFormatter dtf2 = java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmssZ");

        return date.format(dtf2);
    }

    /**
     * 传入LocalDateTime类型日期，返回字符串类型(ISO8601标准时间:yyyy-MM-dd'T'HH:mm:ss.SSS'Z')
     */
    public static String getISO8601TimestampFromDate(LocalDateTime dateTime) {
        ZoneOffset offset = ZoneOffset.of("+08:00");
        OffsetDateTime date = OffsetDateTime.of(dateTime, offset);
        java.time.format.DateTimeFormatter dtf1 = java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmssZ");
        return date.format(dtf1);
    }


    public static void main(String[] args) {
        String t = "19961220T003957Z";
        System.out.println("DateUtil.getDateStrFromISO8601Timestamp(t):" + TimetransUtil.getDateStrFromISO8601Timestamp(t));
        System.out.println("localdatetime:" + LocalDateTime.now());
        String s = TimetransUtil.getISO8601TimestampFromDate(LocalDateTime.now());
        System.out.println("getISO8601TimestampFromDate:" + s);
        LocalDateTime dateFromISO8601TimeStr = TimetransUtil.getLocalDateTimeFromStr("2020-11-04 15:38:48");
        System.out.println("dateFromISO8601TimeStr:" + dateFromISO8601TimeStr);
    }

}