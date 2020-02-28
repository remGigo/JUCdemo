package _9_future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 描述：     演示一个Future的使用方法,lambda形式
 */
public class _3_OneFutureLambda {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

//        Callable<Integer> callable = () -> {
//            Thread.sleep(3000);
//            return new Random().nextInt();
//        };
//        Future<Integer> future = service.submit(callable);

        Future<Integer> future = threadPool.submit(() -> {
            Thread.sleep(3000);
            return new Random().nextInt();
        });

        try {
            System.out.println("任务已经接下了，但还在执行中...预计花费3秒");
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        threadPool.shutdown();
    }

}
