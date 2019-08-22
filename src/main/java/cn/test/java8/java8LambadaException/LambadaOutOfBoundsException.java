package cn.test.java8.java8LambadaException;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by xiaoni on 2019/8/22.
 */
@Slf4j
public class LambadaOutOfBoundsException {

    public static void main(String[] args) {
        BigBox bigBox = new BigBox();
        bigBox.setBoxes(Arrays.asList(new Box("abc", 1L), new Box("def", 2L)));
        bigBox.getBoxes().forEach(box -> {
            log.info(JSON.toJSONString(box));
        });

        log.info(new HashSet<String>(Arrays.asList("aaa", "bbb")).toString());
    }
}
