package _2_threadlocal;

public class ThreadLocalTest {

    public static void begin(){
        TimeContextHandler.TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static long end(){
        return System.currentTimeMillis() - TimeContextHandler.TIME_THREADLOCAL.get();
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadLocalTest.begin();
        Thread.sleep(1000);
        System.out.println(ThreadLocalTest.end());
    }
}

class TimeContextHandler{
    public static ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<>();
}