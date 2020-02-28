package _5_cas;

/**
 * 描述：  自己写代码模拟CAS操作   也就是说语义上是和CAS等价的~
 */
public class SimulatedCAS {
    private volatile int value;

    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue) {//如果旧的值就是我期待的值
            value = newValue;           //我就把它改成新的值
        }
        return oldValue;
    }
}
