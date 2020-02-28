package _6_collections.queue;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


//利用condition手撕阻塞队列，当然啦，应付面试用的
public class SSBlockingQueue {
    private int cap = 10;
    private LinkedList<Integer> queue= new LinkedList<>();

    Lock lock = new ReentrantLock();
    private Condition isNull = lock.newCondition();
    private Condition isFull = lock.newCondition();

    public void put (int data) throws InterruptedException {
        try {
            lock.lock();
            while (queue.size() == cap) {
                try {
                    isFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.add(data);
            isNull.signal();
        }finally {
            lock.unlock();
        }
    }

    public void take () throws InterruptedException {
        try {
            lock.lock();
            while (queue.size() == cap) {
                try {
                    isNull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.poll();
            isNull.signal();
        }finally {
            lock.unlock();
        }
    }

//    //队列容器
//    private List<Integer> container = new ArrayList<>();
//    private volatile int size;
//    private volatile int capacity;
//    private Lock lock = new ReentrantLock();
//    //Condition
//    private final Condition isNull = lock.newCondition();
//    private final Condition isFull = lock.newCondition();
//
//    SSBlockingQueue(int cap) {
//        this.capacity = cap;
//    }
//
//
//    public void add(int data) {
//        try {
//            lock.lock();
//            try {
//                while (size >= capacity) {
//                    System.out.println("阻塞队列满了");
//                    isFull.await();
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            ++size;
//            container.add(data);
//            isNull.signal();
//        } finally {
//            lock.unlock();
//        }
//    }
//
//
//    public int take() {
//        try {
//            lock.lock();
//            try {
//                while (size == 0) {
//                    System.out.println("阻塞队列空了");
//                    isNull.await();
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            --size;
//            int res = container.get(0);
//            container.remove(0);
//            isFull.signal();
//            return res;
//        } finally {
//            lock.unlock();
//        }
//    }
}
