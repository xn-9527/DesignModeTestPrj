package simpleFactory;

/**
 * Created by xiaoni on 2017/2/8.
 */
public class OperationDiv extends Operation{
    
    @Override
    public double getResult() throws Exception {
        double result = 0;
        if(get_numberB() == 0) {
            throw new Exception("除数不能为0");
        }
        result = get_numberA() / get_numberB();
        return result;
    }
}
