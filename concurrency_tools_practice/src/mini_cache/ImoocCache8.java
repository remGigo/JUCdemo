package mini_cache;

import mini_cache.computable.Computable;
import mini_cache.computable.ExpensiveFunction;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 描述：    有了Future的情况下再加上原子操作，保证万无一失，彻底解决重复计算的性能损耗
 */
public class ImoocCache8<A, V> implements Computable<A, V> {

    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public ImoocCache8(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws Exception {
        Future<V> f = cache.get(arg);
        if (f == null) {   //相同的key一旦通过这个条件，重复计算问题就肯定又会发生了
            FutureTask<V> ft = new FutureTask<>(()->c.compute(arg));
            f = cache.putIfAbsent(arg, ft); //使用原子操作保证原子性
            if (f == null) {   //再来一层过滤
                f = ft;
                System.out.println("正在执行FutureTask中的callable任务...");
                ft.run();
            }
        }
        return f.get();
    }

    public static void main(String[] args) throws Exception {

        ImoocCache8<String, Integer> expensiveComputer = new ImoocCache8<>(new ExpensiveFunction());

        new Thread(() -> {
            try {
                Integer result = expensiveComputer.compute("666");
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

    }
}
