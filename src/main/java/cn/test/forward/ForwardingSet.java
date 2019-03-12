package cn.test.forward;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;

/**
 * Created by xiaoni on 2019/3/13.
 *
 * 为了避免继承带来的子类脆弱问题，采用“复合”设计：不用扩展现有的类，而是在新的类中
 * 增加一个私有域，它引用现有类的一个实例。
 *
 * 新类是“转发类”，用于“转发”：新类中每个实例方法都可以调用被包含的现有类实例中对应的方法，并返回它的结果。
 */
public class ForwardingSet<E> implements Set<E> {
    private final Set<E> s;

    public ForwardingSet(Set<E> s) {
        this.s = s;
    }

    @Override
    public void clear() {
        s.clear();
    }

    @Override
    public Spliterator<E> spliterator() {
        return s.spliterator();
    }

    @Override
    public int size() {
        return s.size();
    }

    @Override
    public boolean isEmpty() {
        return s.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return s.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return s.iterator();
    }

    @Override
    public Object[] toArray() {
        return s.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return s.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return s.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return s.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return s.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return s.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return s.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return s.removeAll(c);
    }

}
