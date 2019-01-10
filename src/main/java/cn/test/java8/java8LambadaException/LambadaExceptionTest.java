package cn.test.java8.java8LambadaException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoni on 2019/1/10.
 */
public class LambadaExceptionTest {
    public static void main(String[] args) {
        List<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(234);
        a.add(2134);

        /**
         * 偶数不打印处理，说明执行第一个1/0报错后，循环终止了
         */
        String loop1 = "loop1:";
        try {
            a.forEach(c -> {
                if (c % 2 == 0) {
                    System.out.println(loop1 + c);
                } else {
                    System.out.println(loop1 + c/0);
                }
            });
        } catch (Exception e) {
            System.out.println(loop1 + " error");
//            e.printStackTrace();
        }

        /**
         * 循环并没有终止
         */
        String loop2 = "loop2:";
        a.forEach(c -> {
            try {
                if (c % 2 == 0) {
                    System.out.println(loop2 + c);
                } else {
                    System.out.println(loop2 + c/0);
                }
            } catch (Exception e) {
                System.out.println(loop2 + " error:" + c);
//                e.printStackTrace();
            }
        });
    }

}
