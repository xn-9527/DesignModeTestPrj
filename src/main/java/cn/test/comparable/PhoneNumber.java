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
public class PhoneNumber implements Comparable<PhoneNumber> {
    public int areaCode;
    public int prefix;
    public long lineNumber;

    @Override
    public int compareTo(PhoneNumber pn) {
        //重要的先比较
        if (areaCode < pn.areaCode) {
            return -1;
        }
        if (areaCode > pn.areaCode) {
            return 1;
        }
        //areaCode相等再比较prefix
        if (prefix < pn.prefix) {
            return -1;
        }
        if (prefix > pn.prefix) {
            return 1;
        }
        //areaCode和prfix都相等，比较号码
        if (lineNumber < pn.lineNumber) {
            return -1;
        }
        if (lineNumber > pn.lineNumber) {
            return 1;
        }
        //全相等
        return 0;
    }

    public static void main(String[] args) {
        PhoneNumber a = new PhoneNumber(777, 230, 0123);
        PhoneNumber b = new PhoneNumber(777, 0330, 023);//0330被识别成216，0开头的表示8进制整数，0x开头表示16进制整数，0b开头表示二进制
        System.out.println(a.compareTo(b)); //1
        System.out.println(b.compareTo(a)); //-1
    }
}
