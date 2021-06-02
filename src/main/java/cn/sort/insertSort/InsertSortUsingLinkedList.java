package cn.sort.insertSort;

import lombok.ToString;

/**
 * 用单向链表实现插入排序
 * @author Chay
 * @date 2021/6/1 22:18
 */
public class InsertSortUsingLinkedList {
    @ToString
    public static class Node {
        public int data;
        public Node next;

        public Node() {

        }

        public Node(int data) {
            this.data = data;
        }
    }

    public static Node insertSort(Node head) {
        Node t = head.next;
        //缓存结果链
        Node output = new Node();
        //初始化, 指向head
        output.next = new Node(head.data);
        //第一层循环
        while (true) {
            Node currentN = t.next;
            int currentData = t.data;

            //第二层循环
            Node lastN = output;
            while (lastN.next != null) {
                Node itemN = lastN.next;
                int itemData = itemN.data;
                if (currentData < itemData) {
                    //插入
                    lastN.next = new Node(currentData);
                    lastN.next.next = itemN;
                    //结束第二层循环
                    break;
                }
                //下移到下一个节点
                lastN = lastN.next;
            }
            //下移到下一个节点
            if (t.next == null) {
                break;
            }
            t = t.next;
        }
        //因为output头是我们临时的，头后面是排过序的
        return output.next;
    }

    public static void main(String[] args) {
        Node head = new Node(4);
        Node next1 = new Node(2);
        Node next2 = new Node(1);
        Node next3 = new Node(3);
        head.next = next1;
        next1.next = next2;
        next2.next = next3;

        System.out.println(insertSort(head));
    }
}
