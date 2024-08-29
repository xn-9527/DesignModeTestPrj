package cn.test.compress;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * @Author: Blackknight
 * @Date: 2024/8/28 21:39
 * @Description:
 */
@Slf4j
public class InflaterCompressAlgorithm {


    public static byte[] compressData(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        byte[] buffer = new byte[1024];
        int compressedDataLength = deflater.deflate(buffer);
        deflater.end();

        byte[] output = new byte[compressedDataLength];
        System.arraycopy(buffer, 0, output, 0, compressedDataLength);
        return output;
    }


    public static byte[] decompressData(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        byte[] buffer = new byte[1024];
        int decompressedDataLength = 0;
        try {
            decompressedDataLength = inflater.inflate(buffer);
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
        inflater.end();

        byte[] output = new byte[decompressedDataLength];
        System.arraycopy(buffer, 0, output, 0, decompressedDataLength);
        return output;
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
                compressData = new String(compressData(enteringStr.getBytes(StandardCharsets.UTF_8)), StandardCharsets.ISO_8859_1);
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
                decompressData = new String(decompressData(compressedData.getBytes(StandardCharsets.ISO_8859_1)), StandardCharsets.UTF_8);
            } catch (Exception var3) {
                log.warn("inflater decompressLog fail", var3);
            }
            return decompressData;
        }
    }

    public static void main(String[] args) {
        String inputString = "{\"a\":\"Hello, world!asdfsdfgfdgfghnaaaaaaaaaaaaaaaaasdfdsfffffffffffffsddddddddddddvbngfhjrtyrerertwerwe我的家庭\"}";
        byte[] input = inputString.getBytes(StandardCharsets.UTF_8);
        byte[] compressed = compressData(input);
        byte[] decompressed = decompressData(compressed);
        String outputString = new String(decompressed, StandardCharsets.UTF_8);
        String outputString2 = new String(decompressData(new String(compressed, StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        String outputString3 = new String(decompressData(new String(compressed, StandardCharsets.ISO_8859_1).getBytes(StandardCharsets.ISO_8859_1)), StandardCharsets.UTF_8);
        System.out.println("Original: " + inputString);
        System.out.println("Compressed: " + new String(compressed, StandardCharsets.UTF_8));
        System.out.println("Decompressed: " + outputString);
        System.out.println("Decompressed2: " + outputString2);
        System.out.println("Decompressed3: " + outputString3);
        System.out.println("input str length:" + inputString.length() + ",input byte length:" + input.length + ", compressed length:" + compressed.length + ", decompressed byte length:" + decompressed.length);
        String compressLog = compressLog(inputString);
        System.out.println("compressed log:" + compressLog);
        System.out.println("decompressed log:" + decompressLog(compressLog));
        //input str length:109,compressLog length:88
        System.out.println("input str length:" + inputString.length() + ",compressLog length:" + compressLog.length());
    }
}
