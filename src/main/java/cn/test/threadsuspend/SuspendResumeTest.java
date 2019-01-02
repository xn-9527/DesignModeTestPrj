package cn.test.threadsuspend;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by zhengbinMac on 2017/3/3.
 */
public class SuspendResumeTest {
    public static Object object = new Object();
    static TestThread t1 = new TestThread("线程1");
    static TestThread t2 = new TestThread("线程2");

    public static class TestThread extends Thread {
        public TestThread(String name) {
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (object) {
                System.out.println(getName() + " 占用。。");
                /**
                 * Suspends this thread.
                 * <p>
                 * First, the <code>checkAccess</code> method of this thread is called
                 * with no arguments. This may result in throwing a
                 * <code>SecurityException </code>(in the current thread).
                 * <p>
                 * If the thread is alive, it is suspended and makes no further
                 * progress unless and until it is resumed.
                 *
                 * @exception  SecurityException  if the current thread cannot modify
                 *               this thread.
                 * @see #checkAccess
                 * @deprecated   This method has been deprecated, as it is
                 *   inherently deadlock-prone.  If the target thread holds a lock on the
                 *   monitor protecting a critical system resource when it is suspended, no
                 *   thread can access this resource until the target thread is resumed. If
                 *   the thread that would resume the target thread attempts to lock this
                 *   monitor prior to calling <code>resume</code>, deadlock results.  Such
                 *   deadlocks typically manifest themselves as "frozen" processes.
                 *   For more information, see
                 *   <a href="{@docRoot}/../technotes/guides/concurrency/threadPrimitiveDeprecation.html">Why
                 *   are Thread.stop, Thread.suspend and Thread.resume Deprecated?</a>.
                 */
                Thread.currentThread().suspend();
//                LockSupport.park();
                System.out.println(getName() + " 执行结束");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(200);
        t2.start();
        /**
         * Resumes a suspended thread.
         * <p>
         * First, the <code>checkAccess</code> method of this thread is called
         * with no arguments. This may result in throwing a
         * <code>SecurityException</code> (in the current thread).
         * <p>
         * If the thread is alive but suspended, it is resumed and is
         * permitted to make progress in its execution.
         *
         * @exception  SecurityException  if the current thread cannot modify this
         *               thread.
         * @see        #checkAccess
         * @see        #suspend()
         * @deprecated This method exists solely for use with {@link #suspend},
         *     which has been deprecated because it is deadlock-prone.
         *     For more information, see
         *     <a href="{@docRoot}/../technotes/guides/concurrency/threadPrimitiveDeprecation.html">Why
         *     are Thread.stop, Thread.suspend and Thread.resume Deprecated?</a>.
         */
        t1.resume();
//        LockSupport.unpark(t1);
//        LockSupport.unpark(t2);
        t2.resume();
        /**
         * Waits for this thread to die.
         *
         * <p> An invocation祈祷; 乞求 of this method behaves in exactly the same
         * way as the invocation
         *
         * <blockquote>
         * {@linkplain #join(long) join}{@code (0)}
         * </blockquote>
         *
         * @throws InterruptedException
         *          if any thread has interrupted the current thread. The
         *          <i>interrupted status</i> of the current thread is
         *          cleared when this exception is thrown.
         */
        t1.join();
        t2.join();

        /**
         * 通过jstack -l PID命令查看现场状态：
         *
         * "线程2" #13 prio=5 os_prio=0 tid=0x000000001f5d2800 nid=0x2750 runnable [0x00000000205bf000]
         java.lang.Thread.State: RUNNABLE
         at java.lang.Thread.suspend0(Native Method)
         at java.lang.Thread.suspend(Thread.java:1029)
         at cn.test.threadsuspend.SuspendResumeTest$TestThread.run(SuspendResumeTest.java:19)
         - locked <0x000000076b4c9430> (a java.lang.Object)
         线程2依然处于runnable状态。

         "main" #1 prio=5 os_prio=0 tid=0x0000000003346000 nid=0x18e0 in Object.wait() [0x000000000323f000]
         java.lang.Thread.State: WAITING (on object monitor)
         at java.lang.Object.wait(Native Method)
         - waiting on <0x000000076b4cceb0> (a cn.test.threadsuspend.SuspendResumeTest$TestThread)
         at java.lang.Thread.join(Thread.java:1245)
         - locked <0x000000076b4cceb0> (a cn.test.threadsuspend.SuspendResumeTest$TestThread)
         at java.lang.Thread.join(Thread.java:1319)
         at cn.test.threadsuspend.SuspendResumeTest.main(SuspendResumeTest.java:48)

         */
    }
}