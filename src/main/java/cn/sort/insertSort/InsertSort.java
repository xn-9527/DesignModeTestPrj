package cn.sort.insertSort;

import cn.sort.GoodsPrice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoni on 2017/12/22.
 * 直接插入排序
 * 基本思想：在要排序的一组数中，假设前面(n-1)[n>=2] 个数已经是排
 好顺序的，现在要把第n个数插到前面的有序数中，使得这n个数
 也是排好顺序的。如此反复循环，直到全部排好顺序。
 */
public class InsertSort {

    public static void insertSort(List<GoodsPrice> array) {
        //至少有两个元素才排序
        if(array != null && array.size() > 1) {
            for (int i = 1; i < array.size(); i++) {
                GoodsPrice temp = array.get(i);
                int j = i - 1;
                for (; j >= 0 && array.get(j).getPrice() > temp.getPrice(); j--) {
                    //将大于temp的值整体后移一个单位
                    array.set(j + 1 , array.get(j));
                }
                array.set(j + 1, temp);
            }
        }

        displayArray(array);
    }

    public static void displayArray(List<GoodsPrice> array) {
        if(array != null && array.size() > 0) {
            for(GoodsPrice goodsPrice : array) {
                System.out.println(goodsPrice.getName() + goodsPrice.getPrice() + ",");
            }
        }
    }

    public static void main(String[] args) {
        List<GoodsPrice> goodsPrices = new ArrayList<>();
        goodsPrices.add(new GoodsPrice("apple", 12L));
        goodsPrices.add(new GoodsPrice("banana", 12L));
        goodsPrices.add(new GoodsPrice("pear", 10L));
        goodsPrices.add(new GoodsPrice("orange", 3L));
        System.out.println("插入排序");
        insertSort(goodsPrices);
    }
}
