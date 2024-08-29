package cn.test.compress;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.xerial.snappy.Snappy;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

/**
 * @Author: aaa
 * @Date: 2024/8/28 11:04
 * @Description:
 */
@Slf4j
public class SnappyPressAlgorithm {
    public SnappyPressAlgorithm() {
    }

    public static byte[] compress(String enteringStr) {
        if (StringUtils.isEmpty(enteringStr)) {
            return null;
        } else {
            byte[] compressedData = null;

            try {
                compressedData = Snappy.compress(enteringStr);
            } catch (Exception var3) {
                var3.printStackTrace();
                log.error("SnappyOne compress fail {}", var3);
            }

            return compressedData;
        }
    }

    public static String decompress(byte[] compressedData, Charset charset) {
        if (ArrayUtils.isEmpty(compressedData)) {
            return null;
        } else {
            String decompressData = null;

            try {
                decompressData = new String(Snappy.uncompress(compressedData), charset);
            } catch (Exception var3) {
                var3.printStackTrace();
                log.error("SnappyOne decompress fail {}", var3);
            }

            return decompressData;
        }
    }

    /**
     * 压缩
     *
     * @param enteringStr
     * @return
     */
    public static String compressLog(String enteringStr) {
        if (StringUtils.isEmpty(enteringStr)) {
            return null;
        } else {
            String compressData = null;
            try {
                byte[] compressDataByte = compress(enteringStr);
                if (compressDataByte != null) {
                    compressData = new String(compressDataByte, StandardCharsets.ISO_8859_1);
                }
            } catch (Exception var3) {
                log.warn("inflater compressBase64Log fail", var3);
            }
            return compressData;
        }
    }

    /**
     * 解压缩字符串
     *
     * @param compressedData
     * @return
     */
    public static String decompressLog(String compressedData) {
        if (StringUtils.isEmpty(compressedData)) {
            return null;
        } else {
            String decompressData = null;
            try {
                decompressData = decompress(compressedData.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            } catch (Exception var3) {
                log.warn("inflater decompressLog fail", var3);
            }
            return decompressData;
        }
    }

    public static void main(String[] args) {
        String inputString1 = "{\"a\":\"Hello, world!asdfsdfgfdgfghnaaaaaaaaaaaaaaaaasdfdsfffffffffffffsddddddddddddvbngfhjrtyrerertwerwe我的家庭\"}";
        byte[] compressedData = compress(inputString1);
        System.out.println(compressedData);
        System.out.println(compressedData.toString());
        System.out.println(new String(compressedData, StandardCharsets.UTF_8));
        System.out.println(decompress(compressedData, StandardCharsets.UTF_8));
        System.out.println(compressedData.toString().getBytes());
        System.out.println(compressedData.toString().getBytes());
        System.out.println(Arrays.toString(compressedData).getBytes());
        System.out.println(Arrays.toString(compressedData).getBytes(StandardCharsets.UTF_8));
        System.out.println("decompress new string" + new String(compressedData, StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8));
        try {
            System.out.println(decompress(new String(compressedData, StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("decompress new string error,", e);
        }


        String base64 = Base64.getEncoder().encodeToString(compressedData);
        System.out.println(base64);
        System.out.println("inputSt length: " + inputString1.length() + ", base64 length: " + base64.length() + ", compressedData length: " + compressedData.length);
        try {
            System.out.println(decompress(Base64.getDecoder().decode(base64), StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("decompress base64 error", e);
        }
        String comLog = compressLog(inputString1);
        System.out.println(base64);
        //#######inputSt length: 109, comLog length: 93, compressedData length: 93
        System.out.println("#######inputSt length: " + inputString1.length() + ", comLog length: " + comLog.length() + ", compressedData length: " + compressedData.length);
        try {
            System.out.println(decompressLog(comLog));
        } catch (Exception e) {
            log.error("decompressLog error", e);
        }
        try {
            System.out.println(decompress(Arrays.toString(compressedData).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("decompress Arrays.toString error", e);
        }
    }
}
