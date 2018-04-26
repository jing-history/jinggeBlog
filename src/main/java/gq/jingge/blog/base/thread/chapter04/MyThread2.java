package gq.jingge.blog.base.thread.chapter04;

/**
 * @author wangyj
 * @description
 * @create 2018-04-26 16:59
 **/
public class MyThread2 extends Thread {

    volatile public static int count;
    //用synchronized关键字加锁</font>。（这只是一种方法，Lock和AtomicInteger原子类都可以，因为之前学过synchronized关键字，所以我们使用synchronized关键字的方法）

    synchronized private static void addCount() {
        for (int i = 0; i < 100; i++) {
            count=i;
        }
        System.out.println("count=" + count);

    }
    @Override
    public void run() {
        addCount();
    }
}
