package strategyFactory;

/**
 * Created by xiaoni on 2017/2/22.
 * 策略工厂模式
 */
public class CashContext {
    private CashSuper cs;

//    public CashContext(CashSuper cashSuper) {
//        this.cs = cashSuper;
//    }

    //与简单工厂模式结合
    public CashContext(String type) {
        switch (type) {
            case "正常收费":
                cs = new CashNormal();
                break;
            case "满300返100":
                cs = new CashReturn(300d,100d);
                break;
            case "打8折":
                cs = new CashRebate(0.8d);
                break;
        }
    }

    public double getResult(double money) {
        return cs.acceptCash(money);
    }
}
