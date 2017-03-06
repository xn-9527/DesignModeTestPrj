package strategyFactory;

/**
 * Created by xiaoni on 2017/2/22.
 */
public class CashNormal extends CashSuper{
    @Override
    public double acceptCash(double money) {
        return money;//正常收费，原价返回
    }
}
