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
 * 同一个线程虽然多个方法可以拿同一个锁，但是计数器都会+1
 */
@Slf4j
public class ReentrantLockTest {
    ReentrantLock reentrantLock = new ReentrantLock();

    public void aLock() {
        if (reentrantLock.tryLock()) {
            log.info("a locked");
        } else {
            log.info("a locked failed");
        }
    }

    public void bLock() {
        if (reentrantLock.tryLock()) {
            log.info("b locked");
        } else {
            log.info("b locked failed");
        }
    }

    public void cLockUnlock() {
        if (reentrantLock.tryLock()) {
            log.info("c locked");
            log.info(String.valueOf(reentrantLock.getHoldCount()));
            reentrantLock.unlock();
            log.info(String.valueOf(reentrantLock.getHoldCount()));
        } else {
            log.info("c locked failed");
        }
    }

    public void test() {
        aLock();
        log.info(String.valueOf(reentrantLock.getHoldCount()));
        cLockUnlock();
        bLock();
        log.info(String.valueOf(reentrantLock.getHoldCount()));
        try {
            reentrantLock.unlock();
            log.info(String.valueOf(reentrantLock.getHoldCount()));
            reentrantLock.unlock();
            log.info(String.valueOf(reentrantLock.getHoldCount()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
        reentrantLockTest.test();
    }
}
