package cn.test.forward;

import java.util.Collection;
import java.util.Set;

/**
 * Created by xiaoni on 2019/3/13.
 *
 * "复合"设计的类，不需要继承原有类，不依赖与原有类的实现细节。只需要继承转发类。
 *
 * 本质就是“装饰着模式”
 */
public class InstrumentedSet<E> extends ForwardingSet<E> {
    private int addCount = 0;

    public InstrumentedSet(Set<E> s) {
        super(s);
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
     * 因为原来的类的addAll调用的是add方法,在调用addAll时，+1，然后又调用add，又+1。
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
}
