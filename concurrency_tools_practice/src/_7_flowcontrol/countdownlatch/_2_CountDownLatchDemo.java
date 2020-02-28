package _7_flowcontrol.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述：     模拟100米跑步，5名选手都准备好了，只等裁判员一声令下，所有人同时开始跑步。
 */
public class _2_CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch begin = new CountDownLatch(1);   //只需要一次倒数!
        ExecutorService service = Executors.newFixedThreadPool(8);//把这儿改成7，贼好玩儿。
        for (int i = 0; i < 8; i++) {
            final int no = i + 1;
            Runnable runnable = () -> {
                System.out.println(no + "道准备完毕，等待发令枪");
                try {
                    begin.await();   //糟糕被阻塞了，线程池总部再派一个线程继续执行下一个任务(下一次for循环)
                    System.out.println(no + "道选手像箭一样发射出去 嗖~");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            service.submit(runnable);
        }
        Thread.sleep(3000);   //裁判员检查发令枪...
        System.out.println("发令枪响，比赛开始！");
        begin.countDown();
    }
}
