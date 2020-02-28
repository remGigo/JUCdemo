package _2_threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述：     两个线程打印日期
 */
public class _1_ThreadLocalNormalUsage {

    public static void main(String[] args) {
        new Thread(() -> {
            String date = new _1_ThreadLocalNormalUsage().date(10);
            System.out.println(date);
        }).start();
        new Thread(() -> {
            String date = new _1_ThreadLocalNormalUsage().date(104707);
            System.out.println(date);
        }).start();
    }

    public String date(int seconds) {
        //参数的单位是毫秒，从1970.1.1 00:00:00 GMT计时
        Date date = new Date(1000 * seconds);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
