package cn.soulutions.stackTest;

import java.util.LinkedList;

/**
 * Created by xiaoni on 2020/3/26.
 */
public class Stack {

    LinkedList list = new LinkedList();


    public synchronized void push(Object x) {
        synchronized(list) {
            list.addLast( x );
            notify();
        }
    }

    public synchronized Object pop()
            throws Exception {
        synchronized(list) {
            if( list.size() <= 0 ) {
                wait();
            }
            return list.removeLast();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i< 100; i++) {
            Stack stack = new Stack();
            Thread a = new Thread(() -> {
                stack.push("a");
            });
            Thread b = new Thread(() -> {
                try {
                    System.out.println(stack.pop());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            Thread c = new Thread(() -> {
                stack.push("c");
            });
            a.start();
            b.start();
            c.start();
        }

        /**
         * java的stack，没有阻塞一说，如果pop为空，直接抛出异常
         *
         * java.util.EmptyStackException
             at java.util.Stack.peek(Stack.java:102)
             at java.util.Stack.pop(Stack.java:84)
             at cn.soulutions.stackTest.Stack.lambda$main$1(Stack.java:58)
             at java.lang.Thread.run(Thread.java:745)
         */
        for (int i = 0; i< 100; i++) {
            java.util.Stack realStack = new java.util.Stack<>();
            Thread a = new Thread(() -> {
                realStack.push("a");
            });
            Thread b = new Thread(() -> {
                try {
                    System.out.println(realStack.pop());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            Thread c = new Thread(() -> {
                realStack.push("c");
            });
            a.start();
            b.start();
            c.start();
        }
    }
}
