package _3_lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 描述：  （重要栗子）真正利用可重入性做点事情：对一个任务进行三次相同的处理，并保证执行过程中线程安全(加锁)
 */
public class _4_RecursionDemo {

    private static ReentrantLock lock = new ReentrantLock();

    private static void accessResource() {
        lock.lock();
        try {
            System.out.println("重入"+lock.getHoldCount()+"层锁");

            if (lock.getHoldCount()<=3) {
                System.out.println("已完成对资源的一次处理");
                accessResource();
                System.out.println(lock.getHoldCount());
            }
        } finally {
            System.out.println("释放一层🔒");
            lock.unlock();
        }
    }
    public static void main(String[] args) {
        accessResource();
    }
}
