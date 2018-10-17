package cn.strategyFactory;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");

        //cn.simpleFactory Start---------------------------
/*        OperationFactory operationFactory = new OperationFactory();
        Operation operation = operationFactory.createOperate("+");
        double numberA = 123;
        double numberB = 222;
        operation.set_numberA(numberA);
        operation.set_numberB(numberB);
        System.out.println("cn.simpleFactory+运行结果为：" + operation.getResult());

        operation = operationFactory.createOperate("/");
        operation.set_numberA(numberA);
        operation.set_numberB(numberB);
        System.out.println("cn.simpleFactory/运行结果为：" + operation.getResult());
        operation.set_numberB(0);
        System.out.println("cn.simpleFactory/0运行结果为：" + operation.getResult());*/
        //cn.simpleFactory end-----------------------------

        //strategy策略模式 start-----------------------------
        double total = 0.0d;//用于总计
        double price = 199.99;
        double num = 20;
        String condition = "满300返100";
        total = getTotal(price,num,condition,total);

        condition = "打8折";
        price = 200;
        num = 10;
        total = getTotal(price,num,condition,total);
        //strategy策略模式 end-----------------------------



    }

    public static double getTotal(double price,double num,String condition,double total) {
//        CashContextFactory cashContextFactory = new CashContextFactory();
        CashContext cc = null;
        double totalPrice = 0d;
        double orinTotalPrice = price * num;//原始总价格
//        cc = cashContextFactory.createCashContext(condition);
        cc = new CashContext(condition);
        totalPrice = cc.getResult(orinTotalPrice);
        total = total + totalPrice;
        System.out.println("购买数量为:"+num +",单价为:"+ price +",原始单品总价格:"+orinTotalPrice + "," + condition + "打折后单品总价格:" + totalPrice  + ",策略模式总价格为:"+total);
        return total;
    }
}
