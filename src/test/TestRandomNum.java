package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by xiaoni on 2017/12/19.
 */
public class TestRandomNum {

    /**
     * 产生n个从start(包含)到end(包含)的不重复的整数
     * @param n
     * @param start
     * @param end
     * @return
     */
    public static List<Integer> getIntegerRandomNoRepeatByScale(int n, int start, int end) throws Exception{
        List<Integer> result = new ArrayList<Integer>();
        //校验n的数量在范围内最大整数数量之内
        if(end < start) {
            throw new Exception("输入的范围最大值必须大于等于最小值");
        }
        if(n > (end - start + 1)) {
            throw new Exception("要求输出的数据个数" + n + "大于范围[" + start + "," + end + "]之间的最大整数数量，数据个数n输入错误！");
        }

        int i = n;
        while (i > 0) {
            Random seed = new Random();
            //计算随机数并换算到范围内，包含end和start
            int tryNum = seed.nextInt(end - start + 1) + start;

            boolean noDuplicate = true;
            for(Integer j : result) {
                if(j.equals(tryNum)) {
                    noDuplicate = false;
                    break;
                }
            }
            if(noDuplicate) {
                System.out.println(tryNum);
                result.add(tryNum);
                i--;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        try {
            System.out.println(TestRandomNum.getIntegerRandomNoRepeatByScale(31,2,32).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
