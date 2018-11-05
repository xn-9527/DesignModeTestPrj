package cn.strategyFactory;

/**
 * Created by xiaoni on 2017/2/22.
 */
public class CashReturn extends CashSuper {
    private double moneyCondition = 0.0d;
    private double moneyReturn = 0.0d;

    /**
     * 返利收费，初始化时必需输入返利条件和返利值，比如满300返100
     * @param moneyCondition
     * @param moneyReturn
     */
    public CashReturn(double moneyCondition,double moneyReturn){
        this.moneyCondition = moneyCondition;
        this.moneyReturn = moneyReturn;
    }

    @Override
    public double acceptCash(double money) {
        double result = money;
        if(money >= moneyCondition) {
            result = money - Math.floor(money / moneyCondition) * moneyReturn;
        }
        return result;
    }
}
