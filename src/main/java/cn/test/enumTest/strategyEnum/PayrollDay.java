package cn.test.enumTest.strategyEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by xiaoni on 2019/4/26.
 * 策略模式枚举类：更加安全，更加灵活；比switch case书写麻烦。
 *
 * 根据天计算加班工资(1.5倍正常工资)，周一到周五是8小时外的时间，周末都算加班。
 */
@Slf4j
public enum PayrollDay {
    MONDAY(PayType.WEEKDAY),
    TUESDAY(PayType.WEEKDAY),
    WEDNESDAY(PayType.WEEKDAY),
    THURSDAY(PayType.WEEKDAY),
    FRIDAY(PayType.WEEKDAY),
    SATURDAY(PayType.WEEKDAY),
    SUNDAY(PayType.WEEKDAY),
    ;

    private final PayType payType;
    PayrollDay(PayType payType) {
        this.payType = payType;
    }

    private enum PayType {
        WEEKDAY {
            @Override
            double overtimePay(double hours, double payRate) {
                return hours <= HOURS_PER_SHIFT ? 0 :
                        (hours - HOURS_PER_SHIFT) * payRate / 2;
            }
        },
        WEEKEND {
            @Override
            double overtimePay(double hours, double payRate) {
                return hours * payRate / 2;
            }
        };
        //每天正常工作时长
        private static final int HOURS_PER_SHIFT = 8;

        /**
         * 计算加班小时多出的那0.5部分工资
         *
         * @param hours
         * @param payRate
         * @return
         */
        abstract double overtimePay(double hours, double payRate);

        /**
         * 计算总应付工资
         * @param hoursWorked 工作时长
         * @param payRate 工资
         * @return
         */
        double pay(double hoursWorked, double payRate) {
            double basePay = hoursWorked * payRate;
            return basePay + overtimePay(hoursWorked, payRate);
        }
    }

    public static void main(String[] args) {
        double hoursWorked = 10;
        double payRate = 1;
        log.info("{}的工作时长{}的加班工资多出的部分为{}", PayrollDay.FRIDAY.toString(), hoursWorked, PayrollDay.FRIDAY.payType.overtimePay(hoursWorked, payRate));
        log.info("{}的工作时长{}的应付总工资为{}", PayrollDay.FRIDAY.toString(), hoursWorked,PayrollDay.FRIDAY.payType.pay(hoursWorked, payRate));
    }
}
