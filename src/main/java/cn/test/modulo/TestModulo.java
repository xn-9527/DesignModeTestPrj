package cn.test.modulo;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: cc
 * @Date: 2024/12/23 20:04
 * @Description:
 */
@Slf4j
public class TestModulo {

    public static void main(String[] args) {

        String aaa = "dfasfds";
        System.out.println(Math.abs(aaa.hashCode()) % 256 / 16);
        System.out.println(Math.abs(aaa.hashCode()) % 256);
    }
}
