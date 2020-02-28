package _1_threadpool;

/**
 * 描述： 一千个任务就创建一千个线程？汗
 */
public class _2_ForLoop {
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(()->System.out.println("执行了任务"));
            thread.start();
        }
    }
}
