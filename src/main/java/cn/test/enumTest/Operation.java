package cn.test.enumTest;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoni on 2019/4/26.
 */
@Slf4j
public enum Operation {
    PLUS("+") {
        @Override
        double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS("-") {
        @Override
        double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES("*") {
        @Override
        double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDES("/") {
        @Override
        double apply(double x, double y) {
            return x / y;
        }
    };

    private final String symbol;
    Operation(String symbol) {
        this.symbol = symbol;
    }

    /**
     * 特定于常量的方法实现：将不同的行为与每个枚常量关联起来。
     * 比普通的switch case来区分业务逻辑的好处：
     * 这样新增枚举常量的时候，如果忘记复写apply方法，编译不会通过。
     *
     * @param x
     * @param y
     * @return
     */
    abstract double apply(double x, double y);

    //将符号转换成枚举类
    private static final Map<String, Operation> stringToEnum = new HashMap<>();
    //初始化将符号转换成枚举类
    static {
        for(Operation operation : values()) {
            stringToEnum.put(operation.toString(), operation);
        }
    }

    /**
     * 从符号获取枚举类对象
     *
     * @param symbol
     * @return
     */
    public static Operation fromString(String symbol) {
        return stringToEnum.get(symbol);
    }

    @Override
    public String toString() {
        return symbol;
    }

    public static void main(String[] args) {
        double x = 1.2234;
        double y = 2.98;
        for (Operation operation : Operation.values()) {
            log.info("{} {} {} = {}", x, operation, y, operation.apply(x, y));
        }
    }
}
