package cn.test.sendHttpMessage;

import java.util.UUID;

/**
 * @author Created by chay on 2018/10/22.
 */
public class UUIDUtil {

    /**
     * 合并uuid的规则统一化
     * @param robotCode
     * @param uuid
     * @param suffix
     * @return
     */
    public static String combineUUIDWithRobotCodeAndSuffix(String robotCode, String uuid, String suffix) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(robotCode).append(uuid).append(suffix);
        return stringBuffer.toString();
    }

    /**
     * 获得29个长度的十六进制的UUID+时间戳，和前端29位生成方式统一
     *
     * @return UUID
     */
    public static String get29UUID() {
        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        long millis = System.currentTimeMillis();
        return new StringBuffer().append(idd[0]).append(idd[1]).append(idd[2]).append(millis).toString();
    }

    /**
     * 获得32个长度的十六进制的UUID
     *
     * @return UUID
     */
    public static String get32UUID() {
        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return idd[0] + idd[1] + idd[2] + idd[3] + idd[4];
    }
}
