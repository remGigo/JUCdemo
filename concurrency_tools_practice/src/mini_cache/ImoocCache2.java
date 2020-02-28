package mini_cache;

import mini_cache.computable.Computable;
import mini_cache.computable.ExpensiveFunction;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：   用装饰者模式解耦，不让缓存侵入到业务逻辑中，而是把实现某业务的对象传入cache中(构造器)
 *              如：给计算器自动添加缓存功能     所以缓存类才是装饰器了！！！
 */
public class ImoocCache2<A,V> implements Computable<A,V> {

    private final Map<A, V> cache = new HashMap();

    private  final Computable<A,V> c;

    public ImoocCache2(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public synchronized V compute(A arg) throws Exception {
        System.out.println("进入缓存机制");  //这就是装饰！
        V result = cache.get(arg);          //这就是装饰！
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {

        ImoocCache2<String, Integer> expensiveComputer = new ImoocCache2<>(new ExpensiveFunction());

        Integer result = expensiveComputer.compute("666");
        System.out.println("第一次计算结果："+result);
        result = expensiveComputer.compute("666");
        System.out.println("第二次计算结果："+result);
    }
}
