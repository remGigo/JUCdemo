package _2_threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述：     for循环10个线程打印日期 （建了十个线程，也new了10个simpledataformat变量，已经在不能忍受的边缘了）
 */
public class _2_ThreadLocalNormalUsage {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            int finalI = i;

            new Thread(() -> {
                String date = new _2_ThreadLocalNormalUsage().date(finalI);
                System.out.println(date);
            }).start();

            Thread.sleep(100);
        }

    }

    public String date(int seconds) {
        //参数的单位是毫秒，从1970.1.1 00:00:00 GMT计时
        Date date = new Date(1000 * seconds);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
