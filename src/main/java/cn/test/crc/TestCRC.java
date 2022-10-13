package cn.test.crc;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Blackknight
 * @Date: 2022/10/13 7:48 下午
 * @Description: 测试CRC校验
 */
@Slf4j
public class TestCRC {
    /**
     * 一个字节占8位
     */
    public static int BITS_OF_BYTE = 8;

    public static int crc16(byte[] bytes) {
         /**
          * CRC多项式,查表得到标准多项式
          */
         int POLYNOMIAL = 0x8005;
         /**
          * CRC寄存器默认初始值
          */
         int INITIAL_VALUE = 0xFFFF;
         int result = INITIAL_VALUE;
         for (byte data : bytes) {
             // byte转int后再计算
             result = result ^ (data & 0xFF);
             for (int i = 0; i < BITS_OF_BYTE; i++) {
                 //最低位如果是1，则右移并与多项式异或；如果是0，则只管右移
                 result = (result & 0x0001) == 1 ? (result >> 1) ^ POLYNOMIAL : result >> 1;
             }
         }
         return result;
     }

    public static void main(String[] args) {
        log.info("crc16 result:{}" , crc16("hi".getBytes()));
    }
}
