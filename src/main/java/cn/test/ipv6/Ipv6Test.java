package cn.test.ipv6;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: Blackknight
 * @Date: 2022/10/11 3:05 下午
 * @Description:
 */
@Slf4j
public class Ipv6Test {

    /**
     * 判断是否为Ip地址,只是基于加密结果判断是不是有特殊字符，并不是真正判断是不是ip地址
     * @param str
     * @return
     */
    public static boolean isIPAddress(String str){
        if (StringUtils.isBlank(str)) {
            return false;
        }
        boolean flagColon = false;
        //没有冒号也没有小数点就是加密后的数据
        boolean flagColonExist = false;
        boolean flagPointExist = false;
        for(int i=str.length();--i>=0;){
            int chr=str.charAt(i);
            //包含 ] 符号直接返回就是IP地址
            if(chr == 93){
                return true;
            }
            if(chr == 58 && !flagColon){
                flagColon = true;
                //存在冒号，是IPV6
                flagColonExist = true;
                continue;
            }
            //存在两个冒号 直接返回为IPV6
            if(chr == 58 && flagColon){
                return true;
            }
            //存在小数点，是IPV4
            if(chr == 46){
                flagPointExist = true;
            }
        }
        return flagPointExist || flagColonExist;
    }

    public static void main(String[] args) {
        String IP="2409:4053:d8a:b642::7ec9:8b14";
        String IP2="[2001:DB8:0:23:8:800:200C:417A]:8000";
        String IP3="::1:8000";
        String IP4=":::8000";
        String IP5="0.1.1.2";
        String IP6="0.1.1.2:8000";
        String IP7="::";
        String IP8="A324+sad=";
        String IP9="2131231sdasd";
        String IP10="";
        //有.就认为是ip，这里不是很完善
        String IP11="a.b";
        String IP12="a.b:ccc";

        log.info("IP-\"{}\" is ip:{}", IP, Ipv6Test.isIPAddress(IP));
        log.info("IP2-\"{}\" is ip:{}", IP2, Ipv6Test.isIPAddress(IP2));
        log.info("IP3-\"{}\" is ip:{}", IP3, Ipv6Test.isIPAddress(IP3));
        log.info("IP4-\"{}\" is ip:{}", IP4, Ipv6Test.isIPAddress(IP4));
        log.info("IP5-\"{}\" is ip:{}", IP5, Ipv6Test.isIPAddress(IP5));
        log.info("IP6-\"{}\" is ip:{}", IP6, Ipv6Test.isIPAddress(IP6));
        log.info("IP7-\"{}\" is ip:{}", IP7, Ipv6Test.isIPAddress(IP7));
        log.info("IP8-\"{}\" is ip:{}", IP8, Ipv6Test.isIPAddress(IP8));
        log.info("IP9-\"{}\" is ip:{}", IP9, Ipv6Test.isIPAddress(IP9));
        log.info("IP10-\"{}\" is ip:{}", IP10, Ipv6Test.isIPAddress(IP10));
        log.info("IP11-\"{}\" is ip:{}", IP11, Ipv6Test.isIPAddress(IP11));
        log.info("IP12-\"{}\" is ip:{}", IP12, Ipv6Test.isIPAddress(IP12));
    }
}
