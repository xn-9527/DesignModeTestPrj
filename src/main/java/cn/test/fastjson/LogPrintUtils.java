package cn.test.fastjson;

import cn.test.equals.User;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.util.function.Consumer;

/**
 * @Author: c
 * @Date: 2024/11/25 17:27
 * 1.开关控制是否打印日志, print.test.log.online.switch 如果线上也需要临时打印，可以打开该开关，默认线上不打印。
 * 2.占位符 object 入参如果是 string 则会直接打印，如果是其他对象，则会用 fastJSON 序列化打印。
 */
@Slf4j
public class LogPrintUtils {

    public static void main(String[] args) {
        User user = new User("a", "male", "18");
        User user1 = new User("b", "female", "19");
        User user2 = new User("c", "female", "20");
        LogPrintUtils.info(log, "test log, obj1:{}, obj2:{}, obj3:{}", user, user1, user2);
    }

    /**
     * 根据开关控制是否执行函数
     *
     * @param open
     * @param logFunction
     */
    public static void executeIfOpen(boolean open, Consumer<Void> logFunction) {
        try {
            if (open) {
                logFunction.accept(null);
            }
        } catch (Exception e) {
            log.error("executeIfOpen error", e);
        }
    }

    /**
     * 直接执行函数
     *
     * @param logFunction
     */
    private static void printLog(Consumer<Void> logFunction) {
        try {
            logFunction.accept(null);
        } catch (Exception e) {
            log.error("onlyPrintInPre error", e);
        }
    }

    /**
     * 是否打印日志开关
     *
     * @return
     */
    private static boolean isPrintLog() {
        return true;
    }

    private static String formatString(Object var) {
        if (var instanceof String) {
            return (String) var;
        }
        return JSON.toJSONString(var);
    }

    private static String[] formatString(Object... var) {
        String[] result = new String[var.length];
        for (int i = 0; i < var.length; i++) {
            result[i] = formatString(var[i]);
        }
        return result;
    }

    /**
     * ****************************************************************************
     * 以下是 org.slf4j.Logger 打印日志的方法
     *
     * @param logger
     * @param var1
     */
    public static void info(Logger logger, String var1) {
        printLog((v) -> logger.info(var1));
    }

    public static void info(Logger logger, String var1, Object var2) {
        if (!isPrintLog()) {
            return;
        }
        printLog((v) -> logger.info(var1, formatString(var2)));
    }

    public static void info(Logger logger, String var1, Object var2, Object var3) {
        if (!isPrintLog()) {
            return;
        }
        printLog((v) -> logger.info(var1, formatString(var2), formatString(var3)));
    }

    public static void info(Logger logger, String var1, Object... var2) {
        if (!isPrintLog()) {
            return;
        }
        printLog((v) -> logger.info(var1, formatString(var2)));
    }

    public static void info(Logger logger, String var1, Throwable var2) {
        if (!isPrintLog()) {
            return;
        }
        printLog((v) -> logger.info(var1, var2));
    }

    public static void warn(Logger logger, String var1) {
        if (!isPrintLog()) {
            return;
        }
        printLog((v) -> logger.warn(var1));
    }

    public static void warn(Logger logger, String var1, Object var2) {
        if (!isPrintLog()) {
            return;
        }
        printLog((v) -> logger.warn(var1, formatString(var2)));
    }

    public static void warn(Logger logger, String var1, Object var2, Object var3) {
        if (!isPrintLog()) {
            return;
        }
        printLog((v) -> logger.warn(var1, formatString(var2), formatString(var3)));
    }

    public static void warn(Logger logger, String var1, Object... var2) {
        if (!isPrintLog()) {
            return;
        }
        printLog((v) -> logger.warn(var1, formatString(var2)));
    }

    public static void warn(Logger logger, String var1, Throwable var2) {
        if (!isPrintLog()) {
            return;
        }
        printLog((v) -> logger.warn(var1, var2));
    }

    public static void error(Logger logger, String var1) {
        if (!isPrintLog()) {
            return;
        }
        printLog((v) -> logger.error(var1));
    }

    public static void error(Logger logger, String var1, Object var2) {
        if (!isPrintLog()) {
            return;
        }
        printLog((v) -> logger.error(var1, formatString(var2)));
    }

    public static void error(Logger logger, String var1, Object var2, Object var3) {
        if (!isPrintLog()) {
            return;
        }
        printLog((v) -> logger.error(var1, formatString(var2), formatString(var3)));
    }

    public static void error(Logger logger, String var1, Object... var2) {
        if (!isPrintLog()) {
            return;
        }
        printLog((v) -> logger.error(var1, formatString(var2)));
    }

    public static void error(Logger logger, String var1, Throwable var2) {
        if (!isPrintLog()) {
            return;
        }
        printLog((v) -> logger.error(var1, var2));
    }
}
