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
            System.out.println("test1111:" + decompress(new String(compressedData, StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
            System.out.println("test1234:" + decompress("u�{\"a\":\"Hello, world!asdfsdfgfdgfghnaaa6\u0002\u0000\u0014sdfdsf.\u0001\u0000\u0004sd\u001D\u0001�vbngfhjrtyrerertwerwe我的家庭\"}".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("decompress new string error,", e);
        }


        String base64 = Base64.getEncoder().encodeToString(compressedData);
        System.out.println(base64);
        System.out.println("inputSt length: " + inputString1.length() + ", base64 length: " + base64.length() + ", compressedData length: " + compressedData.length);
        try {
            System.out.println("test2222:" + decompress(Base64.getDecoder().decode(base64), StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("decompress base64 error", e);
        }
        String comLog = compressLog(inputString1);
        System.out.println(comLog);
        //#######inputSt length: 109, comLog length: 93, compressedData length: 93
        System.out.println("#######inputSt length: " + inputString1.length() + ", comLog length: " + comLog.length() + ", compressedData length: " + compressedData.length);
        try {
            System.out.println("test3333:" + decompressLog(comLog));
        } catch (Exception e) {
            log.error("decompressLog error", e);
        }
        try {
            System.out.println("test4444:" + decompress(Arrays.toString(compressedData).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("decompress Arrays.toString error", e);
        }
        try{
            System.out.println("test5555:" + decompressLog("u\u0090{\"a\":\"Hello, world!asdfsdfgfdgfghnaaa6\u0002\u0000\u0014sdfdsf.\u0001\u0000\u0004sd\u001D\u0001\u0088vbngfhjrtyrerertwerweæ\u0088\u0091ç\u009A\u0084å®¶åº\u00AD\"}"));
            System.out.println("test5255:" + decompressLog("u\u0090{\"a\":\"Hello, world!asdfsdfgfdgfghnaaa6\u0002\u0000\u0014sdfdsf.\u0001\u0000\u0004sd\u001D\u0001\u0088vbngfhjrtyrerertwerweæ\u0088\u0091ç\u009A\u0084å®¶åº\u00AD\"}"));
        } catch (Exception e) {
            log.error("decompress error", e);
        }

        byte[] input = inputString1.getBytes(StandardCharsets.UTF_8);
        String base85 = jBaseZ85.encode(input);
        System.out.println("base85 encode log:" + base85 + ",length:" + base85.length());
        String base85compress = jBaseZ85.encode(compressedData);
        System.out.println("base85 encode compress log:" + base85compress + ",length:" + base85compress.length());
        System.out.println("base85 decode log:" + new String(jBaseZ85.decode(base85), StandardCharsets.UTF_8));
        System.out.println("base85 compress decode log:" + new String(decompress(jBaseZ85.decode(base85compress), StandardCharsets.ISO_8859_1).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));

        String inputString2 = inputString1.replace("\"", "");
        System.out.println("inputString2 length:" + inputString2.length());
        byte[] input2 = inputString2.getBytes(StandardCharsets.UTF_8);
        String base852 = jBaseZ85.encode(input2);
        System.out.println("base852 encode log:" + base852 + ",length:" + base852.length());
        byte[] compressedData2 = compress(inputString2);
        String base852compress = jBaseZ85.encode(compressedData2);
        System.out.println("base852 encode compress log:" + base852compress + ",length:" + base852compress.length());
        System.out.println("base852 decode log:" + new String(jBaseZ85.decode(base852), StandardCharsets.UTF_8));
        System.out.println("base852 compress decode log:" + new String(decompress(jBaseZ85.decode(base852compress), StandardCharsets.ISO_8859_1).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        System.out.println("base852 compress decode log:" + new String(decompress(jBaseZ85.decode("AErawi.i*#y&p<ECw?IrwfCx5wm/)[w[LZ^xj+^&zECZf02fG+w[k%{e=+}PB7uo(GMZ*xxj+*(A==OEwPzv5BA.qpCv/])K(@f^</V5NXp]j"), StandardCharsets.ISO_8859_1).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
    }
}
