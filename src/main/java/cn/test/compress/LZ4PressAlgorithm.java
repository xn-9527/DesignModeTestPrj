package cn.test.compress;

import lombok.extern.slf4j.Slf4j;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

/**
 * compress Algorithm by lz4
 * @date 2021-04-23
 *
 * https://github.com/lz4/lz4/wiki
 */
@Slf4j
public class LZ4PressAlgorithm {

    /**
     * 压缩
     *
     * @param enteringStr
     * @return
     */
    public static byte[] compress(String enteringStr, Charset charset) {
        if(StringUtils.isEmpty(enteringStr)){
            return null;
        }

        byte[] compressed = null;

        try{
            int maxCompressedLength = enteringStr.getBytes(charset).length;
            LZ4Factory lz4Factory = LZ4Factory.fastestInstance();
            LZ4Compressor compressor = lz4Factory.fastCompressor();
            compressed = new byte[maxCompressedLength];

            compressor.compress(enteringStr.getBytes(charset), 0, maxCompressedLength, compressed, 0, maxCompressedLength);

        }catch (Exception e){
            e.printStackTrace();
            log.error("LZ4Algo compress fail {}", e);
        }

        return compressed;
    }


    /**
     * 解压缩
     *
     * @param compressedData
     * @return
     */
    public static String decompress(byte[] compressedData, Charset charset) {
        if(ArrayUtils.isEmpty(compressedData)){
            return null;
        }

        String decompressData = null;

        try{
            LZ4Factory lz4Factory = LZ4Factory.fastestInstance();
            LZ4FastDecompressor decompressor = lz4Factory.fastDecompressor();
            byte[] restored = new byte[compressedData.length];
            decompressor.decompress(compressedData, 0, restored, 0, compressedData.length);

            decompressData = new String(restored, charset);

        }catch (Exception e){
            e.printStackTrace();
            log.error("LZ4Algo decompress fail {}", e);
        }

        return decompressData;
    }

    /**
     * 压缩并 base64 编码
     *
     * @param enteringStr
     * @return
     */
    public static String compressBase64Log(String enteringStr) {
        if (StringUtils.isEmpty(enteringStr)) {
            return null;
        } else {
            String compressData = null;
            try {
                compressData = Base64.getEncoder().encodeToString(compress(enteringStr, StandardCharsets.UTF_8));
            } catch (Exception var3) {
                log.warn("LZ4Algo compressBase64Log fail", var3);
            }
            return compressData;
        }
    }

    /**
     * 解压缩 base64 编码的字符串
     *
     * @param base64CompressedData
     * @return
     */
    public static String decompressBase64Log(String base64CompressedData) {
        if (StringUtils.isEmpty(base64CompressedData)) {
            return null;
        } else {
            String decompressData = null;
            try {
                decompressData = decompress(Base64.getDecoder().decode(base64CompressedData), StandardCharsets.UTF_8);
            } catch (Exception var3) {
                log.warn("LZ4Algo decompressBase64Log fail", var3);
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
                byte[] compressDataByte = compress(enteringStr, StandardCharsets.UTF_8);
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
                decompressData = decompress(compressedData.getBytes(StandardCharsets.ISO_8859_1) ,StandardCharsets.UTF_8);
            } catch (Exception var3) {
                log.warn("inflater decompressLog fail", var3);
            }
            return decompressData;
        }
    }

    public static void main(String[] args) {
        String inputString1 = "{\"a\":\"Hello, world!asdfsdfgfdgfghnaaaaaaaaaaaaaaaaasdfdsfffffffffffffsddddddddddddvbngfhjrtyrerertwerwe我的家庭\"}";
        byte[] compressedData = compress(inputString1, StandardCharsets.UTF_8);
        System.out.println(compressedData);
        System.out.println(compressedData.toString());
        System.out.println(new String(compressedData, StandardCharsets.UTF_8));
        System.out.println(decompress(compressedData, StandardCharsets.UTF_8));
        System.out.println(compressedData.toString().getBytes());
        System.out.println(compressedData.toString().getBytes());
        System.out.println(Arrays.toString(compressedData).getBytes());
        System.out.println(Arrays.toString(compressedData).getBytes(StandardCharsets.UTF_8));
        System.out.println("decompress new string" + new String(compressedData, StandardCharsets.ISO_8859_1).getBytes(StandardCharsets.ISO_8859_1));
        try {
            System.out.println(decompress(new String(compressedData, StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        } catch (Exception e) {
            log.error("decompress new string error,", e);
        }

        String base64 = compressBase64Log(inputString1);
        System.out.println(base64);
        System.out.println("inputSt length: " + inputString1.length() + ", base64 length: " + base64.length() + ", compressedData length: " + compressedData.length);
        try {
            System.out.println(decompressBase64Log(base64));
        } catch (Exception e) {
            log.error("decompress base64 error", e);
        }
        String comLog = compressLog(inputString1);
        System.out.println(base64);
        //#######inputSt length: 109, comLog length: 117, compressedData length: 117
        System.out.println("#######inputSt length: " + inputString1.length() + ", comLog length: " + comLog.length() + ", compressedData length: " + compressedData.length);
        try {
            System.out.println(decompressLog(comLog));
        } catch (Exception e) {
            log.error("decompressLog error", e);
        }
        try {
            System.out.println(decompress(Arrays.toString(compressedData).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("decompress Arrays.toString error", e);
        }
    }
}
