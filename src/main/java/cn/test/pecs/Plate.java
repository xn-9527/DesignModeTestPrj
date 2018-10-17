package cn.test.pecs;

/**
 * @author Created by xiaoni on 2018/3/30.
 *
 * PECS（Producer Extends Consumer Super）原则，已经很好理解了：

频繁往外读取内容的，适合用上界Extends。
经常往里插入的，适合用下界Super。
 */
public class Plate<T> {
    private T item;
    public Plate(T t){item=t;}
    public void set(T t){item=t;}
    public T get(){return item;}

    public static void main(String[] args) {
        //实际上Java编译器不允许这个操作。会报错，“装苹果的盘子”无法转换成“装水果的盘子”。
        //Plate<Fruit> p=new Plate<Apple>(new Apple());

        //上界通配符（Upper Bounds Wildcards）------------------------
        Plate<? extends Fruit> p=new Plate<Apple>(new Apple());

        //不能存入任何元素
//        p.set(new Fruit());    //Error
//        p.set(new Apple());    //Error

        //读取出来的东西只能存放在Fruit或它的基类里。
        Fruit newFruit1=p.get();
        Object newFruit2=p.get();
//        Apple newFruit3=p.get();    //Error

        //下界通配符---------------------------------
        Plate<? super Fruit> p1=new Plate<Fruit>(new Fruit());

        //存入元素正常
        p1.set(new Fruit());
        p1.set(new Apple());

        //读取出来的东西只能存放在Object类里。
//        Apple newFruit21=p1.get();    //Error
//        Fruit newFruit22=p1.get();    //Error
        Object newFruit23=p1.get();
    }
}

