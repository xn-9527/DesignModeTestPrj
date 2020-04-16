package cn.lrutest;

import java.util.HashMap;

/**
 * 非线程安全的写法
 *
 * @author Created by xiaoni on 2020/4/16.
 */
public class LRUCacheTest {
    class Node {
        public Node pre;
        public Node next;
        public String key;
        public String value;

        Node(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    private Node head;
    private Node end;
    /**
     * 缓存存储上限
     */
    private int limit;

    private HashMap<String, Node> hashMap;

    public LRUCacheTest(int limit) {
        this.limit = limit;
        hashMap = new HashMap<String, Node>();
    }

    /**
     * 删除节点
     *
     * @param node 要删除的节点
     * @return 被删除的节点的数据
     */
    private synchronized String removeNode(Node node) {
        if (node == head && node == end) {
            //移除唯一的节点
            head = null;
            end = null;
        } else if (node == end) {
            //移除尾节点
            end = end.pre;
            end.next = null;
        } else if (node == head) {
            //移除头结点
            head = head.next;
            head.pre = null;
        } else {
            //移除中间节点
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }
        return node.key;
    }

    /**
     * 尾部插入节点
     *
     * @param node 要插入的节点
     */
    private synchronized void addNode(Node node) {
        if (end != null) {
            end.next = node;
            node.pre = end;
            node.next = null;
        }
        end = node;
        if (head == null) {
            head = node;
        }
    }

    /**
     * 刷新被访问的节点位置
     *
     * @param node 被访问的节点
     */
    private synchronized void refreshNode(Node node) {
        //如果访问的是尾节点，则无需移动节点
        if (node == end) {
            return;
        }
        //移除节点
        removeNode(node);
        //重新插入节点
        addNode(node);
    }

    public String get(String key) {
        Node node = hashMap.get(key);
        if (node == null) {
            return null;
        }
        refreshNode(node);
        return node.value;
    }

    public synchronized void put(String key, String value) {
        Node node = hashMap.get(key);
        if (node == null) {
            //如果key不存在，则插入key-value
            if(hashMap.size() >= limit) {
                String oldKey = removeNode(head);
                hashMap.remove(oldKey);
            }
            node = new Node(key, value);
            addNode(node);
            hashMap.put(key, node);
        } else {
            //如果key存在，则刷新key-value
            node.value = value;
            refreshNode(node);
        }
    }

    public synchronized void remove(String key) {
        Node node = hashMap.get(key);
        removeNode(node);
        hashMap.remove(key);
    }

    public static void main(String[] args) {
        LRUCacheTest lruCacheTest = new LRUCacheTest(5);
        lruCacheTest.put("001","u1");
        lruCacheTest.put("002","u2");
        lruCacheTest.put("003","u3");
        lruCacheTest.put("004","u4");
        lruCacheTest.put("005","u5");
        System.out.println(lruCacheTest.get("002"));
        System.out.println(lruCacheTest.hashMap);
        lruCacheTest.put("002", "u2update");
        System.out.println(lruCacheTest.get("002"));
        lruCacheTest.put("006", "u6");
        lruCacheTest.put("007", "u7");
        System.out.println(lruCacheTest.get("001"));
        System.out.println(lruCacheTest.get("006"));
        System.out.println(lruCacheTest.hashMap);
    }
}
