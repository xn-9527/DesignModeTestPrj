package cn.simpleFactory;

/**
 * Created by xiaoni on 2017/2/8.
 */
public class OperationAdd extends Operation {

    @Override
    public double getResult() {
        double result = 0;
        result = get_numberA() + get_numberB();
        return result;
    }
}
