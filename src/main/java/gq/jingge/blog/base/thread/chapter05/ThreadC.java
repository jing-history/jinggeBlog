package gq.jingge.blog.base.thread.chapter05;

/**
 * @author wangyj
 * @description
 * @create 2018-04-27 11:35
 **/
public class ThreadC extends Thread  {

    private Object lock;

    public ThreadC(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        Service service = new Service();
        service.testMethod(lock);
    }
}
