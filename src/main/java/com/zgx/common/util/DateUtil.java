package com.zgx.common.util;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


public class DateUtil {

    /**
     * 传入String类型日期(ISO8601标准时间:yyyy-MM-dd'T'HH:mm:ss.SSS'Z')，返回字符串类型时间(yyyy-MM-dd HH:mm:ss)
     *
     * @param ISOdate
     * @return
     */
    public static String getDateStrFromISO8601Timestamp(String ISOdate) {
        DateTimeFormatter dtf1 = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmssZ");
        DateTime dt = dtf1.parseDateTime(ISOdate);
        DateTimeFormatter dtf2 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        return dt.toString(dtf2);
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
        String t="19961220T003957Z";
        System.out.println("DateUtil.getDateStrFromISO8601Timestamp(t):"+ DateUtil.getDateStrFromISO8601Timestamp(t));
        System.out.println(LocalDateTime.now());
        String s=DateUtil.getISO8601TimestampFromDate(LocalDateTime.now());
        System.out.println("s:"+s);

    }

}