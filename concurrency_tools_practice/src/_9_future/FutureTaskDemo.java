package _9_future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 描述： (重要栗子)    演示FutureTask的用法
 *              目前我的理解FutureTask就是补充了原来用future的时候只能用线程池.submit(callable)返回future对象，
 *              用了FutureTask后就单个线程也可以用上future这么棒的功能了，具体使用方法有两种~
 *              */
public class FutureTaskDemo {

    public static void main(String[] args) {
        Task task = new Task();     //已实现Callable接口
        FutureTask<Integer> integerFutureTask = new FutureTask<>(task);

//        new Thread(integerFutureTask).start(); //单独线程通过FT使用future功能 形式一
//        integerFutureTask.run();               //单独线程通过FT使用future功能 形式二
        ExecutorService threadPool = Executors.newCachedThreadPool();
        threadPool.submit(integerFutureTask);  //我不接你的返回值了，我是FutureTask鸭

        try {
            System.out.println("（主线程打的）task运行结果：");
            System.out.println(integerFutureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class Task implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("子线程正在计算...");
        Thread.sleep(3000);
        int sum = 0;
        for (int i = 0; i < 101; i++) {
            sum += i;
        }
        return sum;
    }
}

