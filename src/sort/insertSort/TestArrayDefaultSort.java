package sort.insertSort;

import sort.GoodsPrice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by xiaoni on 2017/12/22.
 * 测试Arrays自带的sort方法，用的是归并排序
 */
public class TestArrayDefaultSort {

    public static void main(String[] args) {
        System.out.println("Array自带的归并排序");
        List<GoodsPrice> goodsPrices1 = new ArrayList<>();
        goodsPrices1.add(new GoodsPrice("apple", 12L));
        goodsPrices1.add(new GoodsPrice("banana", 12L));
        goodsPrices1.add(new GoodsPrice("pear", 10L));
        goodsPrices1.add(new GoodsPrice("orange", 3L));
        /*goodsPrices1.sort(new Comparator<GoodsPrice>() {
            @Override
            public int compare(GoodsPrice o1, GoodsPrice o2) {
                if(o1.getPrice() > o2.getPrice()) {
                    return 1;
                }else if(o1.getPrice() < o2.getPrice()) {
                    return -1;
                }
                else {
                    return 0;
                }
            }
        });*/
        Collections.sort(goodsPrices1);
        InsertSort.displayArray(goodsPrices1);


    }
}
