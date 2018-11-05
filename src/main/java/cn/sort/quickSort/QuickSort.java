package cn.sort.quickSort;

import cn.sort.GoodsPrice;
import cn.sort.insertSort.InsertSort;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoni on 2017/12/22.
 */
public class QuickSort {
    /**
     * 快速排序
     * 基本思想：选择一个基准元素,通常选择第一个元素或者最后一个元素,通过一趟扫描，将待排序列分成两部分,一部分比基准元素小,
     * 一部分大于等于基准元素,此时基准元素在其排好序后的正确位置,然后再用同样的方法递归地排序划分的两部分。
     * @param roadPathResultMapList
     * @param low
     * @param high
     * @return
     */
    private static void quickSortRoadPathResultMapList(List<GoodsPrice> roadPathResultMapList,
                                                       int low,
                                                       int high) {
        if (low < high) {
            //将list数组进行一分为二
            int middle = getMiddle(roadPathResultMapList, low, high);
            //对低字表进行递归排序
            quickSortRoadPathResultMapList(roadPathResultMapList, low, middle - 1);
            //对高字表进行递归排序
            quickSortRoadPathResultMapList(roadPathResultMapList, middle + 1, high);
        }
    }

    private static int getMiddle(List<GoodsPrice> roadPathResultMapList, int low, int high) {
        //数组的第一个作为中轴
        GoodsPrice temp = roadPathResultMapList.get(low);
        while (low < high) {
            while (low < high
                    && roadPathResultMapList.get(high).getPrice()
                    >= temp.getPrice()) {
                high--;
            }

            //比中轴小的记录移到低端
            roadPathResultMapList.set(low, roadPathResultMapList.get(high));
            while (low < high
                    && roadPathResultMapList.get(low).getPrice()
                    <= temp.getPrice()) {
                low++;
            }

            //比中轴大的记录移到高端
            roadPathResultMapList.set(high, roadPathResultMapList.get(low));
        }
        //中轴记录到尾
        roadPathResultMapList.set(low, temp);
        //返回中轴的位置
        return low;
    }

    public static void main(String[] args) {
        List<GoodsPrice> goodsPrices = new ArrayList<>();
        goodsPrices.add(new GoodsPrice("apple", 12L));
        goodsPrices.add(new GoodsPrice("banana", 12L));
        goodsPrices.add(new GoodsPrice("pear", 10L));
        goodsPrices.add(new GoodsPrice("orange", 3L));
        System.out.println("快速排序");
        quickSortRoadPathResultMapList(goodsPrices, 0, goodsPrices.size() -1 );
        InsertSort.displayArray(goodsPrices);
    }
}
