package cn.test.listAdd;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoni on 2018/11/24.
 */
public class TestListAdd {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(11);
        list.add(12);
        list.add(13);
        list.add(14);
        list.add(15);
        //倒序循环
        for (int i = list.size() - 1; i >= 0; i--) {
            Integer value = list.get(i);
            //打印该值在list中的索引
            System.out.println("index:" + list.indexOf(value));
            //当遍历倒数N个元素的时候，在这个元素值索引后面插入一个新的数字
            if (i == 2) {
                list.add(list.indexOf(value) + 1, 4343);
                break;
            }
            System.out.println(value);
        }
        System.out.println("++++++++++++++++++++++++++++");
        System.out.println(list);
    }
}
