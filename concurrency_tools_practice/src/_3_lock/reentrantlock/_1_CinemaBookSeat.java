package _3_lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 描述： (不重要栗子)    锁的基本使用：演示多线程预定电影院座位
 */
public class _1_CinemaBookSeat {

    private static ReentrantLock lock = new ReentrantLock();

    private static void bookSeat() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "正在预定座位...(拿到🔒)");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "完成预定座位(释放🔒)");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(() -> bookSeat()).start();
        new Thread(() -> bookSeat()).start();
        new Thread(() -> bookSeat()).start();
        new Thread(() -> bookSeat()).start();
    }
}
