package gq.jingge.blog.base.thread.chapter07;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangyj
 * @description
 * @create 2018-05-02 11:34
 *
 * 在使用notify/notifyAll()方法进行通知时，被通知的线程是有JVM选择的，
 * 使用ReentrantLock类结合Condition实例可以实现<font color="red">“选择性通知”</font>，
 * 这个功能非常重要，而且是Condition接口默认提供的。
 * 而synchronized关键字就相当于整个Lock对象中只有一个Condition实例，所有的线程都注册在它一个身上。
 * 如果执行notifyAll()方法的话就会通知所有处于等待状态的线程这样会造成很大的效率问题，而Condition实例的signalAll()方法 只会唤醒注册在该Condition实例中的所有等待线程
 **/
public class UseSingleConditionWaitNotify {

    public static void main(String[] args) throws InterruptedException {

        MyService service = new MyService();

        ThreadA a = new ThreadA(service);
        a.start();

        Thread.sleep(3000);

        service.signal();

    }

    static public class MyService {

        private Lock lock = new ReentrantLock();
        public Condition condition = lock.newCondition();

        public void await() {
            lock.lock();
            try {
                System.out.println(" await时间为" + System.currentTimeMillis());
                condition.await();
                System.out.println("这是condition.await()方法之后的语句，condition.signal()方法之后我才被执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        /**
         * <font color="red">在使用wait/notify实现等待通知机制的时候我们知道必须执行完notify()方法所在的synchronized代码块后才释放锁。
         * 在这里也差不多，必须执行完signal所在的try语句块之后才释放锁，condition.await()后的语句才能被执行。</font>
         * @throws InterruptedException
         */
        public void signal() throws InterruptedException {
            lock.lock();
            try {
                System.out.println("signal时间为" + System.currentTimeMillis());
                condition.signal();
                Thread.sleep(3000);
                System.out.println("这是condition.signal()方法之后的语句");
            } finally {
                lock.unlock();
            }
        }
    }

    static public class ThreadA extends Thread {

        private MyService service;

        public ThreadA(MyService service) {
            super();
            this.service = service;
        }

        @Override
        public void run() {
            service.await();
        }

    }
}
