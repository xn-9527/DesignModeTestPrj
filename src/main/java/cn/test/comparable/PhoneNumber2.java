package cn.test.comparable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xiaoni on 2019/3/6.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhoneNumber2 implements Comparable<PhoneNumber2> {
    public int areaCode;
    public int prefix;
    public long lineNumber;

    /**
     * 改进的compareTo方法
     *
     * @param pn
     * @return
     */
    @Override
    public int compareTo(PhoneNumber2 pn) {
        //重要的先比较
        int areaCodeDiff = areaCode - pn.areaCode;
        if (areaCodeDiff != 0) {
            return areaCodeDiff;
        }
        //areaCode相等再比较prefix
        int prefixDiff = prefix - pn.prefix;
        if (prefixDiff != 0) {
            return prefixDiff;
        }
        //areaCode和prfix都相等，比较号码
        return Long.valueOf(lineNumber - pn.lineNumber).intValue();
    }

    public static void main(String[] args) {
        PhoneNumber2 a = new PhoneNumber2(777, 230, 0123);
        PhoneNumber2 b = new PhoneNumber2(777, 0330, 023);//0330被识别成216，0开头的表示8进制整数，0x开头表示16进制整数，0b开头表示二进制
        System.out.println(a.compareTo(b)); //14
        System.out.println(b.compareTo(a)); //-14
    }
}
