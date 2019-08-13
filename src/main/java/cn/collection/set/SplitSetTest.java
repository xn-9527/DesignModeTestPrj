package cn.collection.set;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.*;

/**
 * Created by xiaoni on 2019/8/13.
 */
@Slf4j
public class SplitSetTest {
    /**
     * 将一个集合按照要求拆分成n个子集合
     *
     * @param orinSet
     * @param n
     * @return
     */
    public static <T extends Object> List<Set<T>> splitSet(Set<T> orinSet, int n) {
        if (orinSet == null || orinSet.isEmpty() || n == 0) {
            return Collections.emptyList();
        }
        int orinSize = orinSet.size();
        //比划分大1
        int splitSize = 1 + orinSize / n;
        List<Set<T>> returnList = new ArrayList<>(n);
        if (orinSize < n) {
            log.warn("orinSize {} < n {}, result list size maybe less than require", orinSet, n);
            orinSet.forEach(element -> {
                Set<T> temp = new HashSet<>(1);
                temp.add(element);
                returnList.add(temp);
            });
        } else {
            List<T> orinList = new ArrayList<>(orinSet);
            for (int i = 0; i < orinSize; i++) {
                int index = i % n;
                //因为index从0开始，所以看数量比较时需要+1
                if (returnList.size() < (index + 1)) {
                    returnList.add(new HashSet<>(splitSize));
                }
                Set<T> temp = returnList.get(index);
                temp.add(orinList.get(i));
            }
        }
        return returnList;
    }

    public static void main(String[] args) {
        Set<Integer> mySet = new HashSet<Integer>(){{
            add(1);
            add(2);
            add(3);
            add(4);
            add(5);
        }};
        log.info(JSON.toJSONString(SplitSetTest.splitSet(mySet, 2)));
        log.info("#############################");
        log.info(JSON.toJSONString(SplitSetTest.splitSet(mySet, 8)));
    }
}
