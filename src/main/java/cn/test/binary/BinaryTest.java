package cn.test.binary;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: x
 * @Date: 2022/10/27 3:25 下午
 * @Description: 测试二进制
 */
@Slf4j
public class BinaryTest {



    public static void main(String[] args) {
        int b = 0B001000000100;
        log.info("十进制：{}", b);
        int firstBit = b & 0x01;
        log.info("firstBit:{}", firstBit);
        int thirdBit = b & 0x04;
        log.info("thirdBit:{}", thirdBit);
        int thirdBit1 = b & (1 << 2);
        log.info("thirdBit1:{}", thirdBit1);
        log.info("(1 << 3):{}", (1 << 3));
    }

}
