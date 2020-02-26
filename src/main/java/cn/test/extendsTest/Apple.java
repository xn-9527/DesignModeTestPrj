package cn.test.extendsTest;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * Created by xiaoni on 2019/1/17.
 */
@Slf4j
public class Apple extends Fruit {

    public void ripe(Date time, int sweet) {
        log.info("apple riped on: time-{}, {} sweet.", time, sweet);
    }

    public void ripe(Date time, int weight, int sweet, String name) {
        log.info("name {} apple riped on: time-{},{} kg, {} sweet.", name, time, weight, sweet);
    }

    public static void main(String[] args) {
        Fruit fruit = new Fruit();
        fruit.ripe(new Date(), 2, 1);
        Apple apple = new Apple();
        apple.ripe(new Date(), 3);
        apple.ripe(new Date(), 4, 2, "红富士");
        apple.ripe(new Date(), 5, 3);
    }
}
