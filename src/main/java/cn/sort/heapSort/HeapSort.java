package cn.sort.heapSort;

/**
 * @Author: xiaoni
 * @Date: 2020/3/14 20:15
 *
 * 堆排序（大顶堆、小顶堆）----C语言 - 蓝海人 - 博客园  https://www.cnblogs.com/lanhaicode/p/10546257.html
 *
 * 堆和普通树的区别
 * 内存占用：
 * 普通树占用的内存空间比它们存储的数据要多。你必须为节点对象以及左/右子节点指针分配额外的内存。堆仅仅使用数组，且不使用指针
 * （可以使用普通树来模拟堆，但空间浪费比较大，不太建议这么做）
 *
 * 平衡：
 * 二叉搜索树必须是“平衡”的情况下，其大部分操作的复杂度才能达到O(nlog2n)。你可以按任意顺序位置插入/删除数据，或者使用 AVL 树或者红黑树，但是在堆中实际上不需要整棵树都是有序的。我们只需要满足对属性即可，所以在堆中平衡不是问题。因为堆中数据的组织方式可以保证O(nlog2n) 的性能
 *
 * 搜索：
 * 在二叉树中搜索会很快，但是在堆中搜索会很慢。在堆中搜索不是第一优先级，因为使用堆的目的是将最大（或者最小）的节点放在最前面，从而快速的进行相关插入、删除操作
 */
public class HeapSort {
    /* Function: 构建大顶堆 */
    public static void buildMaxHeap(int[] heap, int len) {
        int i;
        int temp;

        /**
         * 升序----使用大顶堆
         * 大顶堆：arr[i] >= arr[2i+1] && arr[i] >= arr[2i+2]
         * 降序----使用小顶堆
         * 小顶堆：arr[i] <= arr[2i+1] && arr[i] <= arr[2i+2]
         */
        for (i = len / 2 - 1; i >= 0; i--) {
            if ((2 * i + 1) < len && heap[i] < heap[2 * i + 1])    /* 根节点大于左子树 */ {
                temp = heap[i];
                heap[i] = heap[2 * i + 1];
                heap[2 * i + 1] = temp;
                /*
                 * 检查交换后的左子树是否满足大顶堆性质 如果不满足 则重新调整子树结构
                 *  把if里面的条件分来开看2*(2*i+1)+1 < len的作用是判断该左子树有没有左子树（可能有点绕），
                 * heap[2*i+1] < heap[2*(2*i+1)+1]就是判断左子树的左子树的值是否大于左子树，
                 * 如果是，那么就意味着交换值过后左子树大顶堆的性质被破环了，需要重构该左子树
                 */
                if ((2 * (2 * i + 1) + 1 < len && heap[2 * i + 1] < heap[2 * (2 * i + 1) + 1]) || (2 * (2 * i + 1) + 2 < len && heap[2 * i + 1] < heap[2 * (2 * i + 1) + 2])) {
                    buildMaxHeap(heap, len);
                }
            }
            if ((2 * i + 2) < len && heap[i] < heap[2 * i + 2])    /* 根节点大于右子树 */ {
                temp = heap[i];
                heap[i] = heap[2 * i + 2];
                heap[2 * i + 2] = temp;
                /* 检查交换后的右子树是否满足大顶堆性质 如果不满足 则重新调整子树结构 */
                if ((2 * (2 * i + 2) + 1 < len && heap[2 * i + 2] < heap[2 * (2 * i + 2) + 1]) || (2 * (2 * i + 2) + 2 < len && heap[2 * i + 2] < heap[2 * (2 * i + 2) + 2])) {
                    buildMaxHeap(heap, len);
                }
            }
        }
    }

    /* Function: 交换交换根节点和数组末尾元素的值*/
    public static void swap(int[] heap, int len) {
        int temp;

        temp = heap[0];
        heap[0] = heap[len - 1];
        heap[len - 1] = temp;
    }

    public static void main(String[] args) {
        int[] a = {7, 3, 8, 5, 1, 2};
        int len = 6;    /* 数组长度 */
        int i;

        for (i = len; i > 0; i--) {
            buildMaxHeap(a, i);
            swap(a, i);
        }
        for (i = 0; i < len; i++) {
            System.out.println("%d " + a[i]);
        }
    }
}
