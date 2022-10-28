package cn.test.comparator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: Blackknight
 * @Date: 2022/10/28 4:03 下午
 * @Description:
 */
@Slf4j
public class ListTest<T> {
    public List<T> getSortedList(List<T> inputList, Comparator<T> comparator) {
        if (CollectionUtils.isEmpty(inputList)) {
            return Collections.emptyList();
        }
        inputList.sort(comparator);
        return inputList;
    }


    public static void main(String[] args) {
        ListTest<Long> listTest = new ListTest<>();
        List<Long> inputList = Arrays.asList(23L,12L,33L);
        Comparator<Long> comparator = (new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                return o1.compareTo(o2);
            }
        });
        log.info("input : {}", inputList);
        log.info("output : {}", listTest.getSortedList(inputList, comparator));
    }
}
