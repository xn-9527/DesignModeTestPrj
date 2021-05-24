package cn.test.sha256;

import cn.test.sendHttpMessage.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import sun.misc.BASE64Encoder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: chay.ni
 * @date: 2021/4/8 11:13 
 * @description:
 */
@Slf4j
public class TestSha256 {
    public static void main(String[] args) {
        String newS = null;
        String prefix = "abcde";
        StringBuilder sb = new StringBuilder();
        Map<String, String[]> params = new HashMap<String, String[]>(3){{
            put("key", new String[]{"a", "b"});
        }};
        if (!CollectionUtils.isEmpty(params)) {
            params.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(paramEntry -> {
                String paramValue = String
                        .join(",", Arrays.stream(paramEntry.getValue()).sorted().toArray(String[]::new));
                if (!"exlusive".equals(paramEntry.getKey()) && StringUtil.isNotEmpty(paramValue)) {
                    sb.append(paramEntry.getKey()).append("=").append(paramValue).append('&');
                }
            });
        }
        sb.deleteCharAt(sb.length() - 1);
        String readyBase64Str = sb.toString();
        log.info("readyBase64Str:{}", readyBase64Str);
        String base64Str = new BASE64Encoder().encode(readyBase64Str.getBytes());
        log.info("base64Str:{}", base64Str);
        String readySha1Str = prefix + ":" + base64Str;
        log.info("readyStr:{}", readySha1Str);
        newS = DigestUtils.sha1Hex(readySha1Str);
        log.info("newS:{}", newS);
    }
}
