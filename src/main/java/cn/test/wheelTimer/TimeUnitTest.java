package cn.test.wheelTimer;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Created by xiaoni on 2019/11/6.
 */
@Slf4j
public class TimeUnitTest {

    public static int normalizeTicksPerWheel(int ticksPerWheel) {
        int normalizedTicksPerWheel = 1;
        while (normalizedTicksPerWheel < ticksPerWheel) {
            normalizedTicksPerWheel <<= 1;
        }
        return normalizedTicksPerWheel;
    }

    public static void main(String[] args) {

        //转换纳秒
        log.info("MILLISECONDS" + TimeUnit.MILLISECONDS.toNanos(1));
        log.info("MILLISECONDS" + TimeUnit.MILLISECONDS.toNanos(1L));
        log.info("MICROSECONDS" + TimeUnit.MICROSECONDS.toNanos(1L));
        log.info("MINUTES" + TimeUnit.MINUTES.toNanos(1L));

        log.info("test normalizeTicksPerWheel====================");
        log.info("1:" + normalizeTicksPerWheel(1));
        log.info("3:" + normalizeTicksPerWheel(3));
        log.info("100:" + normalizeTicksPerWheel(100));
    }
}
