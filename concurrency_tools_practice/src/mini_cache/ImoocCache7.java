package mini_cache;

import mini_cache.computable.Computable;
import mini_cache.computable.ExpensiveFunction;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 描述：  (重要迭代版本)   利用Future，避免重复计算   和上一个版本和下一个版本分别对照着看哦
 */
public class ImoocCache7<A, V> implements Computable<A, V> {

    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public ImoocCache7(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws Exception {
        Future<V> f = cache.get(arg);
        if (f == null) {   //相同的key一旦通过这个条件，重复计算问题就肯定又会发生了
            FutureTask<V> ft = new FutureTask<>(()->c.compute(arg));//lambda表达式就是一个实现了callable接口的任务实例
            f = ft;
            cache.put(arg, ft);   //先put后run
            System.out.println("正在执行FutureTask中的callable任务...");
            ft.run();
        }
        return f.get();
    }

    public static void main(String[] args) throws InterruptedException {

        ImoocCache7<String, Integer> expensiveComputer = new ImoocCache7<>(new ExpensiveFunction());

        new Thread(() -> {
            try {
                Integer result = expensiveComputer.compute("666");
                System.out.println("第一次的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1);//害，有了这步能治标不治本的解决一下"future"失效问题，重复计算依旧发生的问题

        new Thread(() -> {
            try {
                Integer result = expensiveComputer.compute("667");
                System.out.println("第三次的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Integer result = expensiveComputer.compute("666");
                System.out.println("第二次的计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
