package strategyFactory;

/**
 * Created by xiaoni on 2017/2/22.
 */
public class CashContext {
    private CashSuper cs;

    public CashContext(CashSuper cashSuper) {
        this.cs = cashSuper;
    }

    public double getResult(double money) {
        return cs.acceptCash(money);
    }
}
