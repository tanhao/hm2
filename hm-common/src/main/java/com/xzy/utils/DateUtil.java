package com.xzy.utils;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * hm定制时间格式
 */
public class DateUtil {
    //系统开始小时
    private static long initHours = 7;

    //获取当前时间
    public static LocalDate getAccountDay() {
        LocalDateTime nowTime = LocalDateTime.now();
        return nowTime.minusHours(initHours).toLocalDate();
    }

    public static final String ACCOUNT_DAY_PATTERN="yyyy-MM-dd";
/*
    public static void main(String[] args) {
        System.out.println(DateUtil.getAccountDay());
        System.out.println("昨天日期" + DateUtil.getYesterday());
        System.out.println("上周第一天" + DateUtil.getLastWeekMonday());
        System.out.println("上周最后一天" + DateUtil.getLastWeekSunday());
        System.out.println("本周第一天" + DateUtil.getThisWeekMonday());
        System.out.println("本周最后一天" + DateUtil.getThisWeekSunday());
        System.out.println("本月第一天" + DateUtil.getThisMonthStartDay());
        System.out.println("本月最后一天" + DateUtil.getThisMonthEndDay());
    }*/

    /**
     * 获取周一日期
     * @auther yzy
     * @date 2019/8/13 18:01
     */

    public static LocalDate getMonday(){
        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
        int day = 1-localDate.getDayOfWeek().getValue();
        return localDate.plusDays(day);
    }

    public static LocalDate memberWeekGetMonday(){
        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
        LocalTime localTime = LocalTime.now();
        String[] timeArr = localTime.toString().split(":");
        if(Integer.parseInt(timeArr[0])<7){
            localDate = localDate.plusDays(-1);
        }
        int day = 1-localDate.getDayOfWeek().getValue();
        return localDate.plusDays(day);
    }

    //获得昨天日期
    public static LocalDate getYesterday() {
        LocalDateTime nowTime = LocalDateTime.now();
        return nowTime.minusDays(1).minusHours(initHours).toLocalDate();
    }

    //获得本周星期一
    public static LocalDate getThisWeekMonday() {
        LocalDateTime nowTime = LocalDateTime.now();
        return nowTime.minusHours(initHours).with(DayOfWeek.MONDAY).toLocalDate();
    }

    //获得本周星期日
    public static LocalDate getThisWeekSunday() {
        LocalDateTime nowTime = LocalDateTime.now();
        return nowTime.minusHours(initHours).with(DayOfWeek.SUNDAY).toLocalDate();
    }

    //获得上周星期一
    public static LocalDate getLastWeekMonday() {
        LocalDateTime nowTime = LocalDateTime.now();
        return nowTime.minusWeeks(1).minusHours(initHours).with(DayOfWeek.MONDAY).toLocalDate();
    }

    /**
     * 两个日期的相差天数
     *@auther yzy
     *@date 2019/8/14 10:44
     */
    public static int getDayDiff(LocalDate source,LocalDate target){
        return Period.between(source,target).getDays();
    }


    //获得上周星期日
    public static LocalDate getLastWeekSunday() {
        LocalDateTime nowTime = LocalDateTime.now();
        return nowTime.minusWeeks(1).minusHours(initHours).with(DayOfWeek.SUNDAY).toLocalDate();
    }

    //获得本月第一天
    public static LocalDate getThisMonthStartDay() {
        LocalDateTime nowTime = LocalDateTime.now();
        return nowTime.minusHours(initHours).with(TemporalAdjusters.firstDayOfMonth()).toLocalDate();
    }

    //获得本月最后一天
    public static LocalDate getThisMonthEndDay() {
        LocalDateTime nowTime = LocalDateTime.now();
        return nowTime.minusHours(initHours).with(TemporalAdjusters.lastDayOfMonth()).toLocalDate();
    }

    public static Boolean checkToday(String day){
        LocalDate today = DateUtil.getAccountDay();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ACCOUNT_DAY_PATTERN);
        LocalDate parsedDate = LocalDate.parse(day, formatter);
        return !today.isAfter(parsedDate);
    }

    public static Boolean checkLastMonth(String day){
        LocalDate today = DateUtil.getThisMonthStartDay();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ACCOUNT_DAY_PATTERN);
        LocalDate parsedDate = LocalDate.parse(day, formatter);
        return today.isAfter(parsedDate);
    }

}
