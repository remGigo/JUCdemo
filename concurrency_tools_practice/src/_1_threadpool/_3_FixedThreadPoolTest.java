package _1_threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述：     演示newFixedThreadPool的用法
 */
public class _3_FixedThreadPoolTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new Task());
        }
    }
}


class Task implements Runnable {     //这个不用lambda表达式了，原因一：需要重写的 该函数式接口中的抽象方法 有点长
    @Override                                              //原因二：对于该包下其他类 task也要代码复用了
    public void run() {
        try {
            Thread.sleep(500); //睡半秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }
}