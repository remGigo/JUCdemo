package _9_future;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 描述： (重要栗子)    Future初体验
 */
public class _2_OneFuture {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        Future<Integer> future = threadPool.submit(new CallableTask());//交给子线程/线程池中的线程去做，我主线程继续往下走
        try {
            System.out.println("任务已经接下了，但还在执行中...预计花费3秒");
            System.out.println(future.get());  //到这儿阻塞三秒
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        threadPool.shutdown();
    }

    static class CallableTask implements Callable<Integer> { //一定要实现Callable接口啊
        @Override
        public Integer call() throws Exception {
            Thread.sleep(3000);
            return new Random().nextInt();
        }
    }
}
