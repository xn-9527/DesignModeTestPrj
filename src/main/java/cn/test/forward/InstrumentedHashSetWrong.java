package cn.test.forward;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by xiaoni on 2019/3/13.
 */
public class InstrumentedHashSetWrong<E> extends HashSet<E> {
    private int addCount = 0;

    public InstrumentedHashSetWrong() {}

    public InstrumentedHashSetWrong(int initCap, float loadFactor) {
        super(initCap, loadFactor);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    /**
     * 为了统计数量，我们自己复写addAll方法。
     *
     * 如果简单的扩展原来的add和addAll，都增进addCount++,可能会导致数量double两倍。
     * 因为原来的类的addAll调用的是add方法。
     * 在我们调用我们类的addAll时，先count+1，然后又调用父类的addAll，而父类的addAll调用的是add，
     * 所以又调用了我们复写的add方法，就又+1。导致最终结果double。
     * 所以在不知道原来类的方法的实现细节的时候，直接继承调用或修改，子类是很脆弱的。
     *
     * @param c
     * @return
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }

    public static void main(String[] args) {
        InstrumentedHashSetWrong<String> s = new InstrumentedHashSetWrong<>();
        s.addAll(Arrays.asList("A", "b", "dfsf"));
        System.out.println(s.getAddCount());//6
    }
}
