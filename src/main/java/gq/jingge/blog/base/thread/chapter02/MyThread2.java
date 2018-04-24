package gq.jingge.blog.base.thread.chapter02;

/**
 * @author wangyj
 * @description
 * @create 2018-04-24 18:07
 **/
public class MyThread2 extends Thread {

    @Override
    public void run() {
        Sub sub = new Sub();
        sub.operateISubMethod();
    }
}
