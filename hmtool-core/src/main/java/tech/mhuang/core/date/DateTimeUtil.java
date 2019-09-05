package tech.mhuang.core.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间转换工具类
 *
 * @author mhuang
 * @since 1.0.0
 */
public class DateTimeUtil {

    private static ZoneId zone;

    static {
        zone = ZoneId.systemDefault();
    }


    private DateTimeUtil() {

    }

    /**
     * 将Data转换成JDK8中的LocalDateTime对象
     *
     * @param date 需要转换的Data对象
     * @return LocalDateTime 返回转换后的LocalDateTime对象
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 将Data转换成JDK8中的LocalDate对象
     *
     * @param date 需要转换的Data对象
     * @return LocalDate 返回转换后的LocalDate对象
     */
    public static LocalDate dateToLocalDate(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTime.toLocalDate();
    }

    /**
     * 将LocalDateTime转换成JDK8中的Date对象
     *
     * @param localDateTime 需要转换的LocalDateTime对象
     * @return Date 返回转换后的Date对象
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * LocalDate转换成Data、时间取00:00:00
     *
     * @param localDate 需要转换的LocalDate
     * @return Date     转换后的Date
     */
    public static Date LocalDateToStartDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * LocalDate转换成Data、时间取23:59:59
     *
     * @param localDate 需要转换的LocalDate
     * @return Date     转换后的Date
     */
    public static Date LocalDateToEndDate(LocalDate localDate) {
        Instant instant = LocalDateTime.of(localDate, LocalTime.MAX).atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 将字符串按照{@code yyyy-MM-dd HH:mm:ss}转换成LocalDateTime
     *
     * @param dateTimeStr 转换的字符串
     * @return LocalDateTime 转换后的LocalDateTime对象
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DatePattern.NORM_DATETIME_FORMAT);
    }

    /**
     * 将字符串按照{@code yyyy-MM-dd HH:mm:ss}转成Date对象
     *
     * @param dateTimeStr 转换的字符串
     * @return Date 转换后的Date对象
     */
    public static Date parseDateTimeToDate(String dateTimeStr) {
        LocalDateTime localDateTime = parseDateTime(dateTimeStr);
        return localDateTimeToDate(localDateTime);
    }

    /**
     * 将LocalDateTime对象转换成字符串、格式为:{@code yyyy-MM-dd HH:mm:ss}
     *
     * @param localDateTime 转换的对象
     * @return String 转换后存放的字符串
     */
    public static String fromDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(DatePattern.NORM_DATETIME_FORMAT);
    }

    /**
     * 将LocalDateTime对象转换成字符串、格式可自定义、比如:{@code yyyy-MM-dd HH:mm:ss}
     *
     * @param localDateTime 转换的对象
     * @param formatString  转换的格式
     * @return String 转换后存放的字符串
     */
    public static String fromDateTime(LocalDateTime localDateTime, String formatString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatString);
        return localDateTime.format(formatter);
    }

    /**
     * 将LocalDateTime对象转换成字符串、格式可自定义、比如:{@code DateTimeFormatter.ofPattern(yyyy-MM-dd HH:mm:ss)}
     *
     * @param localDateTime 转换的对象
     * @param formatter     转换的格式类
     * @return String 转换后存放的字符串
     */
    public static String fromDateTime(LocalDateTime localDateTime, DateTimeFormatter formatter) {
        return localDateTime.format(formatter);
    }


    /**
     * 将unit时间戳转换成LocalDateTime对象
     *
     * @param unit 时间戳（毫秒级 13位）
     * @return LocalDateTime 转换后的时间对象
     */
    public static LocalDateTime unitToDateTime(Long unit) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(unit), zone);
    }

    /**
     * 将LocalDateTime转换成unit时间戳、毫秒级
     *
     * @param localDateTime 转换的对象
     * @return long 转换后的时间戳毫秒级
     */
    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * 获取当前的Data
     *
     * @return 返回当前的Data对象
     */
    public static Date currentDate() {
        return Date.from(Instant.now());
    }
}
