package cn.test.time;

import com.sun.tools.javac.util.Pair;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Blackknight
 * @Date: 2023/12/26 16:47
 * @Description:
 */
public class TimeTest {

    public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static void main(String[] args) {
        // 你的开始时间戳
        long startTime = System.currentTimeMillis();
        // 5小时后的时间戳
        long endTime = startTime + 5 * 60 * 60 * 1000;

        List<Pair<Long, Long>> timePairs = splitTimeRange(startTime, endTime, 4 * 60 * 60 * 1000);

        // 打印结果
        for (Pair<Long, Long> pair : timePairs) {
            System.out.println("Start Time: " + formatTimestamp(pair.fst, YYYY_MM_DD_HH_MM_SS) + ", End Time: " + formatTimestamp(pair.snd, YYYY_MM_DD_HH_MM_SS));
        }
    }

    /**
     * 按照时间间隔拆分给定的时间范围，比如以 4 小时拆分
     *
     * @param startTime 开始时间戳
     * @param endTime 结束时间戳
     * @param splitInterval 拆分的时间间隔
     * @return
     */
    private static List<Pair<Long, Long>> splitTimeRange(long startTime, long endTime, long splitInterval) {
        List<Pair<Long, Long>> result = new ArrayList<>();

        long currentTime = startTime;

        while (currentTime < endTime) {
            long nextTime = currentTime + splitInterval;

            // 如果拆分后的结束时间超过了原始的结束时间，将拆分后的结束时间设为原始的结束时间
            if (nextTime > endTime) {
                nextTime = endTime;
            }

            result.add(new Pair<>(currentTime, nextTime));
            currentTime = nextTime;
        }

        return result;
    }

    /**
     * 格式化时间戳
     *
     * @param timestamp
     * @param pattern
     * @return
     */
    public static String formatTimestamp(long timestamp, String pattern) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }
}
