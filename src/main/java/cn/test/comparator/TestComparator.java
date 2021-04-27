package cn.test.comparator;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author: nichengyun
 * @date: 2021/3/24 17:08 
 * @description:
 */
@Data
@AllArgsConstructor
public class TestComparator {
    Integer order;

    public static void main(String[] args) {
        List<TestComparator> l = new ArrayList<>();
        l.add(new TestComparator(1));
        l.add(new TestComparator(null));
        l.add(new TestComparator(23));
        //从小到大
        l.sort(new Comparator<TestComparator>() {
            @Override
            public int compare(TestComparator o1, TestComparator o2) {
                boolean isO1OrderNull = o1 == null || o1.getOrder() == null;
                boolean isO2OrderNull = o2 == null || o2.getOrder() == null;
                if (!isO2OrderNull && isO1OrderNull) {
                    return -1;
                } else if (isO2OrderNull && !isO1OrderNull) {
                    return 1;
                } else if (isO2OrderNull && isO1OrderNull) {
                    return 0;
                } else {
                    return o1.getOrder().compareTo(o2.getOrder());
                }
            }
        });
        System.out.println(l);
        //从大到小
        l.sort((o1, o2) -> {
            boolean isO1OrderNull = o1 == null || o1.getOrder() == null;
            boolean isO2OrderNull = o2 == null || o2.getOrder() == null;
            if (!isO2OrderNull && isO1OrderNull) {
                return 1;
            } else if (isO2OrderNull && !isO1OrderNull) {
                return -1;
            } else if (isO2OrderNull && isO1OrderNull) {
                return 0;
            } else {
                return o2.getOrder().compareTo(o1.getOrder());
            }
        });
        System.out.println(l);
    }
}
