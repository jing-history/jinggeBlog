package gq.jingge.blog.base.thread.chapter07;

/**
 * ReadWriteLock接口的实现类：ReentrantReadWriteLock
 * 读写锁维护了两个锁，一个是读操作相关的锁也成为共享锁，一个是写操作相关的锁 也称为排他锁。通过分离读锁和写锁，其并发性比一般排他锁有了很大提升。
 * 多个读锁之间不互斥，读锁与写锁互斥，写锁与写锁互斥（只要出现写操作的过程就是互斥的。）。</font>在没有线程Thread进行写入操作时，
 * 进行读取操作的多个Thread都可以获取读锁，而进行写入操作的Thread只有在获取写锁后才能进行写入操作。即多个Thread可以同时进行读取操作，
 * 但是同一时刻只允许一个Thread进行写入操作
 * @author wangyj
 * @description
 * @create 2018-05-02 14:31
 **/
public class ReentrantReadWriteLock {

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /*public void read() {
        try {
            try {
                lock.readLock().lock();
                System.out.println("获得读锁" + Thread.currentThread().getName()
                        + " " + System.currentTimeMillis());
                Thread.sleep(10000);
            } finally {
                lock.readLock().unlock();
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/

}
