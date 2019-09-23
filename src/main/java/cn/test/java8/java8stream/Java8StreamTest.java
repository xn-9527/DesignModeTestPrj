package cn.test.java8.java8stream;

import cn.string2asc.StringToAscUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Created by xiaoni on 2018/10/9.
 */
@Slf4j
public class Java8StreamTest {
    public static void main(String[] args) {

        /**
         * 过滤筛选==============================================================================
         */

        /**
         * filter()：接受一个谓词，返回符合条件的元素集合
         * **/
        List<String> list = Arrays.asList("悟空","","唐僧","八戒","悟净","白龙马");
        List<String> newList = list.stream().filter(str->!str.isEmpty()).collect(Collectors.toList());
        //结果：newList=("悟空","唐僧","八戒","悟净","白龙马")

        //:: 1.8引入的符号，表示方法具柄，JVM底层实现是CallSite，对JDK层暴漏的接口是Functional
        newList.forEach(System.out::println);
        newList.forEach(System.out::print);

        /**
         * 求偶数
         */
        Integer [] nums = {1, 2, 3, 4, 5, 6};
        Integer[] evenNums = Stream.of(nums).filter(n->n%2==0).toArray(Integer[]::new);
        //结果：evenNums=[2, 4,6]
        System.out.println(Arrays.toString(evenNums));

        /**
         *  求偶数个数
         */
        long count = Stream.of(nums).filter(n->n%2==0).count();
        //结果：count=3
        System.out.println(count);
        //通过stream api的count()可以计算出符合元素的个数。

        /**
         * distinct ()：返回集合中各异的元素集合(去重)
         */
        Integer[] numbers = {5, 1, 2, 1, 3, 3, 2, 4};
        Integer[] newNumbers = Arrays.stream(numbers).distinct().toArray(Integer[]::new);
        //结果：newNumbers =  [5, 1, 2, 3, 4]
        System.out.println(Arrays.toString(newNumbers));

        /**
         * limit()：截取流中指定数量的元素，返回一个不超过给定长度的流。如果流是有序的，则最多会返回前n个元素。
         */
        //如果我们要得到一组数前3个大于100的数，应该怎么编码呢？
        Integer[] nums1 = {100,30,400,200,600,20,800};
        Integer[] newNums = Arrays.stream(nums1).filter(n->n>100).limit(3).toArray(Integer[]::new);
        //结果：newNums= [400, 200, 600]
        System.out.println(Arrays.toString(newNums));

        /**
         * skip()：跳过指定数量元素，返回一个扔掉了前n个元素的流。如果流中元素不足n个，则返回一个空流。
         */
        Integer[] nums2 ={100,30,400,200,600,20,800};
        Integer[] newNums2 = Arrays.stream(nums2).skip(2).toArray(Integer[]::new);
        //结果：newNums= [400, 200, 600,20,800]
        System.out.println(Arrays.toString(newNums2));

        /**
         * 映射==============================================================================
         */

        /**
         * map()：接受一个函数作为参数。这个函数会被应用到每个元素上，并将其映射成一个新的元素。以下代码片段使用 map 输出了元素对应的平方数
         */
        Integer[] nums3 = {2,3,5,6,7,9,8};
        Integer[] newNums3 = Arrays.stream(nums3).map(n -> n*n).toArray(Integer[]::new);
        //结果：newNums= [4, 9, 25, 36, 49, 81, 64]
        System.out.println(Arrays.toString(newNums3));

        /**
         * 匹配==============================================================================
         * 匹配比较简单，返回一个boolean
         - anyMatch()：至少匹配一个
         - allMatch()：全部匹配
         - noneMatch()：全部不匹配，和allMatch相反
         */
        Integer[] nums4 ={2,3,5,6,7,9,8};
        //是否有存在偶数.
        boolean isEven = Arrays.stream(nums4).anyMatch(n->n%2==0);
        //结果：isEven=true
        System.out.println("isEven="+isEven);
        //是否全部是偶数.
        boolean isEvenAll = Arrays.stream(nums4).allMatch(n->n%2==0);
        //结果：isEvenAll=false
        System.out.println("isEvenAll="+isEvenAll);
        //是否全部不是偶数
        boolean noneMatch = Arrays.stream(nums4).noneMatch(n->n%2==0);
        //结果：noneMatch=false
        System.out.println("noneMatch="+noneMatch);

        /**
         * 查找==============================================================================
         * 查找有2个方法：findFirst()和findAny()，返回一个Optional<T>集合。
         如果你不关心返回的元素是哪个，请使用findAny()，因为它在使用并行流时限制较少。
         */
        List<Integer> someNumbers = Arrays.asList(1,2, 3, 4, 5,6);
        //找到第一个能够被3整除的数
        Optional<Integer> findFirstOptional = someNumbers.stream()
                .filter(x -> x % 3 == 0)
                .findFirst();
        //结果：3
        System.out.println(findFirstOptional.get());
        findFirstOptional = someNumbers.stream()
                .filter(x -> x % 3 == 0)
                .findAny();
        //结果：3
        System.out.println(findFirstOptional.get());

        /**
         * 归约==============================================================================
         * 归约在汇总结合内所有数据的时候使用。比如求 max，min，sum。
         */
        List<Integer> integers = Arrays.asList(1,2, 3, 4, 5);
        IntSummaryStatistics stats = integers.stream().mapToInt((x) -> x).summaryStatistics();
        //列表中最大的数 : 5
        System.out.println("列表中最大的数 : " + stats.getMax());
        //列表中最小的数 : 1
        System.out.println("列表中最小的数 : " + stats.getMin());
        //所有数之和 : 15
        System.out.println("所有数之和 : " + stats.getSum());
        //平均数 : 3.0
        System.out.println("平均数 :" + stats.getAverage());


        /**
         * 阿里2019云栖========================================================
         */
        Stream.of("Apsara", "2019", "9.25- 9.27", "Digital Economy")
                /**
                 *Stream.map()对数组的每个元素都执行
                 * 57 == x.charAt(0) ? "Hera is the" : (50 == x.charAt(0) ? x.concat(":") : x)
                 * 57 对应9，50 对应2
                 * 所以当执行到x.charAt(0)第一个字符是9的时候，会进行替换"Hera is the"；
                 * 其他字符会进入50 == x.charAt(0) ? x.concat(":") : x。
                 * 其他字符中，x.charAt(0)第一个字符是2的时候，会进行连接":"，剩下的原字符串输出。
                 * 所以上面的"2019"会连接":","9.25- 9.27"会被替换成"Hera is the"，
                 * 最终输出就是: Apsara 2019: Hera is the Digital Economy
                 */
                .map(x -> 57 == x.charAt(0) ? "Hera is the" :
                        50 == x.charAt(0) ? x.concat(":") : x)
                .reduce((x,y) -> x + " " + y)
                .ifPresent(System.out::println);
        //57 对应9
        log.info(StringToAscUtil.ASCIIToConvert(String.valueOf(57)));
        //50 对应2
        log.info(StringToAscUtil.ASCIIToConvert(String.valueOf(50)));
    }
}
