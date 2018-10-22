package cn.simpleFactory;

/**
 * Created by xiaoni on 2018/10/22.
 */
public class Main {
    public static void main(String[] args) {
        try {
            Operation operation;
            operation = OperationFactory.createOperate("+");
            operation.set_numberA(1);
            operation.set_numberB(2);
            double result = operation.getResult();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
}
