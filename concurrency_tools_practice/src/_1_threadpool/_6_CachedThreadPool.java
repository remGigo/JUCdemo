package _1_threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述：  可以有超多线程的线程池   用的是synchronousqueue和linkedBlockingQueue： 核心线程0，最大线程MAX_INTEGER
 *                                   进一个任务没有空闲线程就马上新建一个线程，每一个空闲线程等60秒没任务就被回收掉
 */
public class _6_CachedThreadPool {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new Task());
        }
    }
}
