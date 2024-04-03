package cn.test.time;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

/**
 * @Author: Blackknight
 * @Date: 2024/2/1 22:59
 * @Description:
 */
@Slf4j
public class DateTimeUtil {

    public static String DATE_FORMAT="yyyy-MM-dd";
    public static String TIME_FORMAT="yyyy-MM-dd HH:mm:ss";
    public static String TIME_MINUTE_FORMAT="yyyy-MM-dd HH:mm";
    public static String TIME_HOUR_FORMAT="yyyy-MM-dd HH";
    public static String TIME_DAY_FORMAT="yyyy-MM-dd";
    public static String TIME_MONTH_FORMAT="yyyy-MM";
    public static String TIME_YEAR_FORMAT="yyyy";

    public static String GMT="GMT";
    public static String UTC="UTC";

    /**
     * 一分钟的毫秒数
     */
    public static final long ONE_MINUTE_MILLIS = 60000L;
    public static final long ONE_HOUR_MILLIS = 60 * ONE_MINUTE_MILLIS;
    public static final long ONE_DAY_MILLIS = 24 * ONE_HOUR_MILLIS;

    public static String format(Long date, String pattern, String timezoneId) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(TimeZone.getTimeZone(timezoneId));
        return format.format(new Date(date));
    }

    public static String format(Date date, String pattern, String timezoneId) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(TimeZone.getTimeZone(timezoneId));
        return format.format(date);
    }

    public static String format(Long date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(date));
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }


    /**
     * 处理季数据
     *
     * @param startTime
     * @param endTime
     * @return 输出开始时间到结束时间的季度数据，比如 2024-Q1
     */
    public static Set<String> getAllXValueByQuarter(Long startTime, Long endTime) {
        Set<String> formattedTime = new HashSet<>();

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(startTime);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        while (cal.getTimeInMillis() <= endTime) {
            int month = cal.get(Calendar.MONTH);
            int quarter = (month / 3) + 1;

            String yearQuarter = cal.get(Calendar.YEAR) + "-Q" + quarter;
            if (!formattedTime.contains(yearQuarter)) {
                formattedTime.add(yearQuarter);
            }

            cal.add(Calendar.MONTH, 1);
        }

        log.info("getAllXValueByQuarter formattedTime: {}", JSON.toJSONString(formattedTime));
        return formattedTime;
    }

    /**
     * 处理月数据
     * 自然月天数不一样，所以不能直接复用减多少时间的周期
     *
     * @param startTime
     * @param endTime
     * @return 输出开始时间到结束时间的月数据，比如 2024-01
     */
    public static  Set<String> getAllXValueByMonth(Long startTime, Long endTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(startTime);

        Set<String> formattedTime = new HashSet<>();
        while (cal.getTimeInMillis() <= endTime) {
            String formattedTimeStr = format(cal.getTime(), TIME_MONTH_FORMAT);
            formattedTime.add(formattedTimeStr);
            cal.add(Calendar.MONTH, 1);
        }
        log.info("getAllXValueByMonth formattedTime: {}", JSON.toJSONString(formattedTime));
        return formattedTime;
    }

    /**
     * 处理周数据
     *
     * @param startTime
     * @param endTime
     * @param startWeekDay 一周从周几开始，1-7表示周一到周日
     * @return
     */
    public static  Set<String> getAllXValueByWeek(Long startTime, Long endTime, int startWeekDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(startTime);

        int calendarWeekDay = 0;
        switch (startWeekDay) {
            case 1:
                calendarWeekDay = Calendar.MONDAY;
                break;
            case 2:
                calendarWeekDay = Calendar.TUESDAY;
                break;
            case 3:
                calendarWeekDay = Calendar.WEDNESDAY;
                break;
            case 4:
                calendarWeekDay = Calendar.THURSDAY;
                break;
            case 5:
                calendarWeekDay = Calendar.FRIDAY;
                break;
            case 6:
                calendarWeekDay = Calendar.SATURDAY;
                break;
            case 7:
                calendarWeekDay = Calendar.SUNDAY;
                break;
            default:
        }
        while (cal.get(Calendar.DAY_OF_WEEK) != calendarWeekDay) {
            cal.add(Calendar.DATE, 1);
        }

        Set<String> formattedTime = new HashSet<>();
        while (cal.getTimeInMillis() <= endTime) {
            String formattedTimeStr = format(cal.getTime(), TIME_DAY_FORMAT);
            formattedTime.add(formattedTimeStr);
            cal.add(Calendar.DATE, 7);
        }
        log.info("getAllXValueByWeek formattedTime: {}", JSON.toJSONString(formattedTime));
        return formattedTime;
    }

    /**
     * 处理年数据
     * 自然年天数不一样，所以不能直接复用减多少时间的周期
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static Set<String> getAllXValueByYear(Long startTime, Long endTime) {
        String startYear = format(startTime, TIME_YEAR_FORMAT);
        String endYear = format(endTime, TIME_YEAR_FORMAT);
        if (StringUtils.isEmpty(endYear) || StringUtils.isEmpty(startYear)) {
            log.info("startYear:{} or endYear:{} is empty", startYear, endYear);
            return Collections.emptySet();
        }

        Set<String> formattedTime = new HashSet<>();
        int startYearInt = Integer.parseInt(startYear);
        int endYearInt = Integer.parseInt(endYear);
        int currentYearInt = endYearInt;
        while (currentYearInt >= startYearInt) {
            formattedTime.add(String.valueOf(currentYearInt));
            currentYearInt --;
        }
        log.info("getAllXValueByYear formattedTime: {}", JSON.toJSONString(formattedTime));
        return formattedTime;
    }

    /**
     * 处理天数据
     *
     * @param startTime
     * @param endTime
     * @param dayNum
     * @return
     */
    public static Set<String> getAllXValueByDay(Long startTime, Long endTime, Long dayNum) {
        return getAllXValueByTime(startTime, endTime, dayNum, TIME_DAY_FORMAT, ONE_DAY_MILLIS);
    }

    /**
     * 处理小时数据
     *
     * @param startTime
     * @param endTime
     * @param hourNum
     * @return
     */
    public static Set<String> getAllXValueByHour(Long startTime, Long endTime, Long hourNum) {
        return getAllXValueByTime(startTime, endTime, hourNum, TIME_HOUR_FORMAT, ONE_HOUR_MILLIS);
    }

    /**
     * 处理分钟数据
     *
     *
     * @param startTime
     * @param endTime
     * @param minuteNum 间隔分钟数
     * @return
     */
    public static Set<String> getAllXValueByMinute(Long startTime, Long endTime, Long minuteNum) {
        return getAllXValueByTime(startTime, endTime, minuteNum, TIME_MINUTE_FORMAT, ONE_MINUTE_MILLIS);
    }

    /**
     * 处理分钟数据，
     * 从 0 开始以多少分钟聚合，比如每 5 分钟，则 1-5 算 5，6-10 算 10，56-0 算 0。
     *
     * @param startTime
     * @param endTime
     * @param minuteNum 间隔分钟数
     * @return
     */
    public static Set<String> getAllXValueByMinuteStartFromZero(Long startTime, Long endTime, Long minuteNum) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(endTime);
        int minutes = calendar.get(Calendar.MINUTE);
        // minuteNum 分钟周期，计算新的分钟数
        Long roundedMinutes = (minutes / minuteNum) * minuteNum + minuteNum;
        // 如果计算结果为 60，小时增加1，分钟重置为 0
        if (roundedMinutes == 60L) {
            roundedMinutes = 0L;
            calendar.add(Calendar.HOUR, 1);
        }
        calendar.set(Calendar.MINUTE, roundedMinutes.intValue());
        //以校正过的结束时间作为结束时间
        return getAllXValueByTime(startTime, calendar.getTimeInMillis(), minuteNum, TIME_MINUTE_FORMAT, ONE_MINUTE_MILLIS);
    }

    /**
     * 通用处理时间范围分段数据
     *
     * @param startTime
     * @param endTime
     * @param extTimeData 时间额外参数，比如分钟数
     * @param timeFormat 时间格式，比如：yyyy-MM-dd HH:mm:ss
     * @param timeParticleIntervalMillis 时间单位间隔的毫秒数，比如：1分钟为60000毫秒
     * @return
     */
    public static Set<String> getAllXValueByTime(Long startTime,
                                           Long endTime, Long extTimeData,
                                           String timeFormat, Long timeParticleIntervalMillis) {
        if (startTime == null || endTime == null) {
            log.info("getAllXValueByTime request time range is null");
            return Collections.emptySet();
        }
        Set<String> formattedTime = new HashSet<>();
        Long timeIntervalMillis = extTimeData * timeParticleIntervalMillis;
        long currentTimeMillis = endTime;
        while (currentTimeMillis >= startTime) {
            formattedTime.add(format(currentTimeMillis, timeFormat));
            currentTimeMillis -= timeIntervalMillis;
        }
        log.info("getAllXValueByTime formattedTime: {}", JSON.toJSONString(formattedTime));
        return formattedTime;
    }

    /**
     * 计算到到期时间还剩多少天，如果 > 1，则取整数，如果 < 1 则取2位小数
     *
     * @param dueDateTimestamp
     * @return
     */
    public static BigDecimal calculateDaysUntilDueDate(long dueDateTimestamp) {
        LocalDate currentDate = LocalDate.now();
        LocalDate dueDate = Instant.ofEpochMilli(dueDateTimestamp).atZone(ZoneId.systemDefault()).toLocalDate();
        long daysBetween = ChronoUnit.DAYS.between(currentDate, dueDate);
        BigDecimal days = new BigDecimal(daysBetween);
        if (days.compareTo(BigDecimal.ONE) > 0) {
            return days.setScale(0, RoundingMode.HALF_UP);
        } else {
            return days.setScale(2, RoundingMode.HALF_UP);
        }
    }

    public static void main(String[] args) {
        getAllXValueByWeek(1706284800000L, 1706889599000L, 1);
        getAllXValueByWeek(1706284800000L, 1706889599000L, 2);
        getAllXValueByWeek(1696166546000L, 1706793746287L, 2);
        getAllXValueByWeek(1696166546000L, 1706793746287L, 4);
        getAllXValueByYear(1696166546000L, 1706793746287L);
        getAllXValueByMonth(1696166546000L, 1706793746287L);
        getAllXValueByQuarter(1696166546000L, 1706793746287L);
        getAllXValueByMinute(1706792746287L, 1706793746287L, 10L);
        getAllXValueByMinuteStartFromZero(1706792746287L, 1706793746287L, 10L);
        getAllXValueByMinute(1706792746287L, 1706793746287L, 3L);
        getAllXValueByMinuteStartFromZero(1706792746287L, 1706793746287L, 3L);
        getAllXValueByHour(1706692746287L, 1706793746287L, 1L);
        System.out.println(format(System.currentTimeMillis(),TIME_FORMAT,GMT+"+13"));
        System.out.println(format(System.currentTimeMillis(),TIME_FORMAT,GMT+"+12"));
        System.out.println(format(System.currentTimeMillis(),TIME_FORMAT,GMT+"+8"));
        System.out.println(format(System.currentTimeMillis(),TIME_FORMAT,GMT+"+1"));
        System.out.println(format(System.currentTimeMillis(),TIME_FORMAT,GMT+"+0"));
        System.out.println(calculateDaysUntilDueDate(1713097945000L));
    }
}
