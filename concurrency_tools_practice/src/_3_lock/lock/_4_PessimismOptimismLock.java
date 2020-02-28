package _3_lock.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述：     乐观锁，其实这没啥演示的哈哈，就是调用人家的原子类嘛
 */
public class _4_PessimismOptimismLock {

    int a;

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.incrementAndGet();
    }

    public synchronized void testMethod() {
        a++;
    }


}
