package gq.jingge.blog.base.thread.chapter05;

/**
 * @author wangyj
 * @description
 * @create 2018-04-27 11:38
 **/
public class Test {

    public static void main(String[] args) throws InterruptedException {

        Object lock = new Object();

        ThreadC a = new ThreadC(lock);
        a.start();

        NotifyThread notifyThread = new NotifyThread(lock);
        notifyThread.start();

        SynNotifyMethodThread c = new SynNotifyMethodThread(lock);
        c.start();

    }
}
