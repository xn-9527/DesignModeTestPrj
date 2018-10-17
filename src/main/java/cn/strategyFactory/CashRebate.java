package cn.strategyFactory;

/**
 * Created by xiaoni on 2017/2/22.
 * 打折收费子类
 */
public class CashRebate extends CashSuper {
    private double moneyRebate = 1d;

    public CashRebate( double moneyRebate){
        this.moneyRebate = moneyRebate;
    }

    @Override
    public double acceptCash(double money) {
        return money * moneyRebate;
    }
}
