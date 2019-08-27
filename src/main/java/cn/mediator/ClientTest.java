package cn.mediator;

/**
 * Created by xiaoni on 2019/8/27.
 * 测试类
 *
 * 中介者模式(Mediator Pattern)：用一个中介对象（中介者）来封装一系列的对象交互，中介者使各对象不需要显式地相互引用，
 * 从而使其耦合松散，而且可以独立地改变它们之间的交互。中介者模式又称为调停者模式，它是一种对象行为型模式。
 *
 * 一、中介者模式的主要优点
 1中介者模式简化了对象之间的交互，它用中介者和同事的一对多交互代替了原来同事之间的多对多交互，
 一对多关系更容易理解、维护和扩展，将原本难以理解的网状结构转换成相对简单的星型结构。

 2中介者模式可将各同事对象解耦。中介者有利于各同事之间的松耦合，我们可以独立的改变和复用每一个同事和中介者，
 增加新的中介者和新的同事类都比较方便，更好地符合 “开闭原则”。

 3可以减少子类生成，中介者将原本分布于多个对象间的行为集中在一起，改变这些行为只需生成新的中介者子类即可，
 这使各个同事类可被重用，无须对同事类进行扩展。

 二、中介者模式的主要缺点
 1在具体中介者类中包含了大量同事之间的交互细节，可能会导致具体中介者类非常复杂，使得系统难以维护。（也就是把具体同事类之间的交互复杂性集中到了中介者类中，结果中介者成了最复杂的类）

 三、适用场景
 1系统中对象之间存在复杂的引用关系，系统结构混乱且难以理解。

 2一个对象由于引用了其他很多对象并且直接和这些对象通信，导致难以复用该对象。

 3想通过一个中间类来封装多个类中的行为，而又不想生成太多的子类。可以通过引入中介者类来实现，
 在中介者中定义对象交互的公共行为，如果需要改变行为则可以增加新的具体中介者类。

 四、应用举例
 MVC模式中，Controller 是中介者，根据 View 层的请求来操作 Model 层

 */
public class ClientTest {
    public static void main(String[] args) {
        AbstractMediator syncMediator = new SyncMediator();
        MysqlDatabase mysqlDatabase = new MysqlDatabase(syncMediator);
        RedisDatabase redisDatabase = new RedisDatabase(syncMediator);
        EsDatabase esDatabase = new EsDatabase(syncMediator);

        syncMediator.setMysqlDatabase(mysqlDatabase);
        syncMediator.setRedisDatabase(redisDatabase);
        syncMediator.setEsDatabase(esDatabase);

        System.out.println("\n---------mysql 添加数据 1，将同步到Redis和ES中-----------");
        mysqlDatabase.add("1");
        mysqlDatabase.select();
        redisDatabase.cache();
        esDatabase.count();

        System.out.println("\n---------Redis添加数据 2，将不同步到其它数据库-----------");
        redisDatabase.add("2");
        mysqlDatabase.select();
        redisDatabase.cache();
        esDatabase.count();

        System.out.println("\n---------ES 添加数据 3，只同步到 Mysql-----------");
        esDatabase.add("3");
        mysqlDatabase.select();
        redisDatabase.cache();
        esDatabase.count();
    }

}
