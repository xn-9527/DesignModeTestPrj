package cn.test.extendsTest;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * Created by xiaoni on 2019/1/17.
 */
@Slf4j
public class Fruit {

    public void ripe(Date time, int weight, int sweet) {
        log.info("riped on: time-{},{} kg, {} sweet.", time, weight, sweet);
    }

}
