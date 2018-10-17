package cn.simpleFactory;


/**
 * Created by xiaoni on 2017/2/8.
 */
public class OperationFactory {

    public static Operation createOperate(String operate) {
        Operation oper = null;
        switch (operate) {
            case "+":
                oper = new OperationAdd();
                break;
            case "/":
                oper = new OperationDiv();
                break;
        }
        return oper;
    }
}
