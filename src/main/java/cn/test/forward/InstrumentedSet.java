package cn.test.forward;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
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
     * 这里结果正常。
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
        InstrumentedSet<String> s = new InstrumentedSet<>(new HashSet<>());
        s.addAll(Arrays.asList("A", "b", "dfsf"));
        System.out.println(s.getAddCount());//3
    }
}
