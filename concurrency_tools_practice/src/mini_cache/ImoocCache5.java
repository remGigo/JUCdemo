package mini_cache;

import mini_cache.computable.Computable;
import mini_cache.computable.ExpensiveFunction;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述：    怎么才想到上ConcurrentHashMap，但是细细想来还有一个重复计算的问题，而且应该频率不低
 */
public class ImoocCache5<A, V> implements Computable<A, V> {

    private final Map<A, V> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public ImoocCache5(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws Exception {
        System.out.println("进入缓存机制");
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        ImoocCache5<String, Integer> expensiveComputer = new ImoocCache5<>(new ExpensiveFunction());

        Integer result = expensiveComputer.compute("666");
        System.out.println("第一次计算结果：" + result);

        result = expensiveComputer.compute("666");
        System.out.println("第二次计算结果：" + result);
    }
}
