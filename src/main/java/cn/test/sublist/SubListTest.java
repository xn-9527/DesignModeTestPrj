package cn.test.sublist;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: Blackknight
 * @Date: 2022/9/2 10:31 上午
 * @Description:
 */
public class SubListTest {
    public final static int MAX_GROUP_NUM = 2;

    public static void main(String[] args) {
        List<String> devIds = new ArrayList<>();
        devIds.add("a1");
        devIds.add("a2");
        devIds.add("b1");
        devIds.add("b2");
        devIds.add("c1");

        System.out.println(devIds.subList(4,5));

        int groupNum = devIds.size() / SubListTest.MAX_GROUP_NUM + 1;
        int devSize = devIds.size();
        List<List<String>> groupDevIds = new ArrayList<>(groupNum);
        for (int i = 0; i < groupNum; i++) {
            int startIndexContain = i * SubListTest.MAX_GROUP_NUM;
            int endIndexNotContain = (i + 1) * SubListTest.MAX_GROUP_NUM;
            System.out.println("i=" + i + ",startIndexContain=" + startIndexContain + ",endIndexNotContain=" + endIndexNotContain);
            groupDevIds.add(devIds.subList(startIndexContain, Math.min(endIndexNotContain, devSize)));
        }
        System.out.println(JSON.toJSONString(groupDevIds));
        List<String> resultList = groupDevIds.stream().parallel().flatMap(Collection::stream).collect(
                Collectors.toList());
        System.out.println(JSON.toJSONString(resultList));
    }
}
