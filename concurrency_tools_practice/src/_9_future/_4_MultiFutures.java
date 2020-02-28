package _9_future;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 描述：（重要栗子） 演示批量提交任务时，用List来批量接收结果
 */
public class _4_MultiFutures {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        ArrayList<Future> futures = new ArrayList<>();
        for (int i = 0; i < 20; i++) {         //迅速20个空箱子接下任务
            Future<Integer> future = threadPool.submit(()->{
                Thread.sleep(3000);
                return new Random().nextInt();
            });
            futures.add(future);
            System.out.println("线程池迅速收下第"+i+"个任务");
        }
        //瞬间就执行到这儿了，一个futures数组中存放了20个空的future容器
        System.out.println("......................");
        for (int i = 0; i < 20; i++) {
            Future<Integer> future = futures.get(i);
            try {
                Integer integer = future.get();//第一次执行到这儿的时候肯定是被阻塞的
                System.out.println(integer);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
