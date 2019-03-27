package cn.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ResourceProperties;

/**
 * Created by xiaoni on 2019/3/27.
 */
@Slf4j
public class Host {
    private static class Strategy {
        private static class Method {
            public void test() {
                log.info("hahaha");
            }

            public static void staticTest() {
                log.info("static hahaha");
            }

            public final static String A = "a";
        }

        public void strategyMethod() {
            log.info("bbbbb");
        }

        public static final Method METHOD = new Method();
    }

    public static final Strategy STRATEGY = new Strategy();

    public static void main(String[] args) {
        //调用静态常量、方法可以使用内部私有类
        Strategy.Method.staticTest();//right
        STRATEGY.METHOD.test();//wrong
        STRATEGY.METHOD.staticTest();//wrong

        log.info(STRATEGY.METHOD.A);//wrong
        log.info(Strategy.Method.A);//right
        STRATEGY.strategyMethod();//right
    }
}
