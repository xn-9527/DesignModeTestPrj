package cn.test.comparable;

/**
 * Created by xiaoni on 2019/3/6.
 *
 * 比较：
 * 小于  返回负数
 * 等于  返回0
 * 大于  返回正数
 */
public class CaseInsensitiveString implements Comparable<CaseInsensitiveString> {
    public String s;

    public CaseInsensitiveString() {
    }

    public CaseInsensitiveString(String a) {
        this.s = a;
    }

    @Override
    public int compareTo(CaseInsensitiveString o) {
        return String.CASE_INSENSITIVE_ORDER.compare(s, o.s);
    }

    public static void main(String[] args) {
        CaseInsensitiveString a = new CaseInsensitiveString("AWfd");
        CaseInsensitiveString b = new CaseInsensitiveString("bWfd");
        System.out.println(a.compareTo(b));//-1
        System.out.println(b.compareTo(a));//1
    }
}
