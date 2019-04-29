package cn.test.enumTest.interfaceMockEnum;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by xiaoni on 2019/4/29.
 */
@Slf4j
public enum ExtendedOperation implements Operation {
    EXP("^") {
        @Override
        public double apply(double x, double y) {
            return Math.pow(x, y);
        }
    },
    REMAINDER("%") {
        @Override
        public double apply(double x, double y) {
            return x % y;
        }
    };

    private final String symbol;

    ExtendedOperation(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public static void main(String[] args) {
        double x = 4;
        double y = 2;
        test(ExtendedOperation.class, x, y);
        test(BasicOperation.class, x, y);
        test2(Arrays.asList(ExtendedOperation.values()), x, y);
    }

    /**
     * 使用有限制的类型令牌Class<T> opSet
     *
     * @param opSet
     * @param x
     * @param y
     * @param <T>
     */
    private static <T extends Enum<T> & Operation> void test(
            Class<T> opSet, double x, double y) {
        for (Operation operation : opSet.getEnumConstants()) {
            log.info("{} {} {} = {}", x, operation, y, operation.apply(x, y));
        }
    }

    /**
     * 使用有限制的通配符类型Collection<? extends Operation>
     *
     * @param opSet
     * @param x
     * @param y
     */
    private static void test2 (Collection<? extends Operation> opSet,
                               double x, double y) {
        for (Operation operation : opSet) {
            log.info("{} {} {} = {}", x, operation, y, operation.apply(x, y));
        }
    }
}
