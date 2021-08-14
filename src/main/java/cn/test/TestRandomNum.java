package cn.test;

import lombok.Getter;

import java.util.*;

/**
 * Created by xiaoni on 2017/12/19.
 */
public class TestRandomNum {

    /**
     * 产生n个从start(包含)到end(包含)的不重复的整数
     *
     * @param n
     * @param start
     * @param end
     * @return
     */
    public static List<Integer> getIntegerRandomNoRepeatByScale(int n, int start, int end) throws Exception {
        List<Integer> result = new ArrayList<Integer>();
        //校验n的数量在范围内最大整数数量之内
        if (end < start) {
            throw new Exception("输入的范围最大值必须大于等于最小值");
        }
        if (n > (end - start + 1)) {
            throw new Exception("要求输出的数据个数" + n + "大于范围[" + start + "," + end + "]之间的最大整数数量，数据个数n输入错误！");
        }

        int i = n;
        while (i > 0) {
            Random seed = new Random();
            //计算随机数并换算到范围内，包含end和start
            int tryNum = seed.nextInt(end - start + 1) + start;

            boolean noDuplicate = true;
            for (Integer j : result) {
                if (j.equals(tryNum)) {
                    noDuplicate = false;
                    break;
                }
            }
            if (noDuplicate) {
//                System.out.println(tryNum);
                result.add(tryNum);
                i--;
            }
        }
        System.out.println("产生的不重复序列：" + result.toString());
        return result;
    }

    /**
     * 产生n个从start(包含)到end(包含)的可重复的整数
     *
     * @param n
     * @param start
     * @param end
     * @return
     */
    public static List<Integer> getIntegerRandomRepeatByScale(int n, int start, int end) throws Exception {
        List<Integer> result = new ArrayList<>(n);
        int i = n;
        while (i > 0) {
            Random seed = new Random();
            //计算随机数并换算到范围内，包含end和start
            int tryNum = seed.nextInt(end - start + 1) + start;
            result.add(tryNum);
            i--;
        }
        System.out.println("产生的可重复序列：" + result.toString());
        return result;
    }

    @Getter
    public enum House {
        ZHAOSHANTIANLAN(1, "招商天澜"),
        XINGCHUANGCHENG(2, "星创城"),
        HANGCHENLI(3, "杭宸里"),
        ZHONGLIANGMUCHENYUAN(4, "中梁沐晨苑"),
        ;

        private House(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        String name;
        Integer value;

        public static House getHouse(Integer value) {
            for(House house : House.values()) {
                if (house.getValue().equals(value)) {
                    return house;
                }
            }
            return null;
        }

        public static Map<House, Integer> init() {
            Map<House, Integer> result = new HashMap<>(House.values().length);
            for(House house : House.values()) {
                result.put(house, 0);
            }
            return result;
        }

        public static void print(Map<House, Integer> result) {
            House max = null;
            int lastMax = 0;
            for(Map.Entry<House, Integer> entry : result.entrySet()) {
                House k = entry.getKey();
                Integer v = entry.getValue();
                if (max == null || lastMax < v) {
                    max = k;
                    lastMax = v;
                }
                System.out.println(k.getName() + ":" + v);
            }
            System.out.println("max:" + max.getName() + "," + lastMax);
        }

        public static void update(Map<House, Integer> result, Integer i) {
            House house = House.getHouse(i);
            if (house != null) {
                result.put(house, result.get(house) + 1);
            }
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(TestRandomNum.getIntegerRandomNoRepeatByScale(31, 2, 32).toString());

            Map<House, Integer> resultMap = House.init();
            /**
             * 一次一个，重复N次
             */
            for (int j = 0; j < 1000; j++) {
                List<Integer> result = TestRandomNum.getIntegerRandomRepeatByScale(1, 1, 4);
                for (Integer i : result) {
                    House.update(resultMap, i);
                }
            }
            House.print(resultMap);

            /**
             * 一次N个
             */
/*            resultMap = House.init();
            List<Integer> result2 = TestRandomNum.getIntegerRandomRepeatByScale(1000, 1, 4);
            for (Integer i : result2) {
                House.update(resultMap, i);
            }
            House.print(resultMap);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
