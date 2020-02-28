package _1_threadpool;

/**
 * 描述：     TODO
 */
public class _1_EveryTaskOneThread {
    public static void main(String[] args) {
        Thread thread = new Thread(()->System.out.println("某个线程执行了某个任务任务"));
        thread.start();
    }
}
