package _1_threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述：  只有一个线程的线程池
 */
public class _5_SingleThreadExecutor {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 1000; i++) {
            threadPool.execute(new Task());
        }
    }
}
