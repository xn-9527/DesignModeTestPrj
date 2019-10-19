package cn.test.reentrantlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Created by xiaoni on 2019/10/19.
 *
 * 测试一个类的两个方法是否能够加到同一个锁.
 * 结论是可以。
 * 看trylock的描述，只有锁没有被另一个线程拿着，都是可以的，所以只要是同一个线程的不同方法，拿相同锁都是成功的。
 * Acquires the lock only if it is not held by another thread at the time
 * of invocation.
 */
@Slf4j
public class ReentrantLockTest {
    ReentrantLock reentrantLock = new ReentrantLock();

    public void a() {
        if (reentrantLock.tryLock()) {
            log.info("a locked");
        } else {
            log.info("a locked failed");
        }
    }

    public void b() {
        if (reentrantLock.tryLock()) {
            log.info("b locked");
        } else {
            log.info("b locked failed");
        }
    }

    public void test() {
        a();
        b();
        try {
            reentrantLock.unlock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
        reentrantLockTest.test();
    }
}
