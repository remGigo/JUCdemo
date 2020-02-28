package mini_cache;

import mini_cache.computable.Computable;
import mini_cache.computable.MayFail;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 描述： 出于安全性考虑，缓存需要设置有效期，到期自动失效，否则如果缓存一直不失效，那么带来缓存不一致等问题
 */
public class ImoocCache10<A, V> implements Computable<A, V> {

    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public ImoocCache10(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException, ExecutionException {
        while (true) {
            Future<V> f = cache.get(arg);
            if (f == null) {
                Callable<V> callable = () -> c.compute(arg);
                FutureTask<V> ft = new FutureTask<>(callable);
                f = cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    System.out.println("从FutureTask调用了计算函数");
                    ft.run();
                }
            }
            try {
                return f.get();
            } catch (CancellationException e) {
                System.out.println("被取消了");
                cache.remove(arg);
                throw e;
            } catch (InterruptedException e) {
                cache.remove(arg);
                throw e;
            } catch (ExecutionException e) {
                System.out.println("计算错误，需要重试");
                cache.remove(arg);
            }
        }
    }

    //要增加缓存过期功能了，重载了compute方法
    public final static ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

    public V compute(A arg, long expiretime) throws ExecutionException, InterruptedException {
        if (expiretime>0)   //如果超时时间大于0，用线程池帮我们做延迟的工作
            scheduledThreadPool.schedule(() -> expire(arg), expiretime, TimeUnit.MILLISECONDS);//给线程池传入任务和延迟时间
        return compute(arg);
    }

    public synchronized void expire(A key) {
        Future<V> future = cache.get(key);
        if (future != null) {
            if (!future.isDone()) {    //缓存有效时间到了任务还没有执行结束
                System.out.println("Future任务因过期被取消");
                future.cancel(true);
            }
            System.out.println("过期时间到，缓存被清除");
            cache.remove(key);
        }
    }


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ImoocCache10<String, Integer> expensiveComputer = new ImoocCache10<>(new MayFail());

        new Thread(() -> {
            try {
                Integer result = expensiveComputer.compute("666",5000L); //走带过期时间的compute方法
                System.out.println("第一次的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Integer result = expensiveComputer.compute("666");
                System.out.println("第三次的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Integer result = expensiveComputer.compute("667");
                System.out.println("第二次的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(6000);
        Integer result = expensiveComputer.compute("666");
        System.out.println("主线程的计算结果：" + result);
    }
}
