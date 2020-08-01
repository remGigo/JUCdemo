package _6_collections.concurrenthashmap;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述：     自己看看源码吧
 */
public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        ConcurrentHashMap<String,String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("mxm","ddddd");
        String mxm = concurrentHashMap.get("mxm");
        System.out.println(mxm);
    }
}
