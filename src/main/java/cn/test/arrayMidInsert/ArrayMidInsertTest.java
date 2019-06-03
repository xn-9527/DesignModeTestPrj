package cn.test.arrayMidInsert;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by xiaoni on 2019/6/3.
 */
@Slf4j
public class ArrayMidInsertTest {
    private int[] array;
    private int size;

    public ArrayMidInsertTest(int capacity) {
        this.array = new int[capacity];
        size = 0;
    }

    /**
     * 数组插入元素
     *
     * @param element 插入的元素
     * @param index 插入的位置
     * @throws Exception
     */
    public void insert(int element, int index) throws Exception {
        //判断下标是否越界
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("超出数组实际元素范围！");
        }
        //从右向左循环，将元素逐个向右挪1位
        for (int i = size -1; i >= index; i--) {
            array[i + 1] = array[i];
        }
        //腾出的位置放入新元素
        array[index] = element;
        size ++;
    }

    /**
     * 输出数组
     */
    public void output() {
        for(int i = 0;i< size;i++) {
            log.info(String.valueOf(array[i]));
        }
    }

    public static void main(String[] args) {
        ArrayMidInsertTest myArray = new ArrayMidInsertTest(10);
        try {
            myArray.insert(5,0);
            myArray.insert(2,1);
            myArray.insert(3,2);
            myArray.output();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
