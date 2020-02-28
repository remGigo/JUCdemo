package _7_flowcontrol.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述：  工厂中，质检，5个工人检查，所有人都认为通过，才通过
 */
public class _1_CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for (int i = 1; i <= 5; i++) {
            int finalI = i;  //还非得这样一下才能把i传到lambda表达式中了
            Runnable runnable = () -> {
                try {
//                    Thread.sleep(1000);  //这样会导致1秒后五个检查员同时结束，因为它们是并行执行的嘛
                    Thread.sleep((long) (Math.random()*10000)); //每一个检查员都是0到10中一个随机数
                    System.out.println("No." + finalI + "完成了检查。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            };
            threadPool.submit(runnable);
        }
        System.out.println("等待5个人检查完.....");
        latch.await();//门闩插住~
        System.out.println("所有人都完成了工作，进入下一个环节。");
    }
}
