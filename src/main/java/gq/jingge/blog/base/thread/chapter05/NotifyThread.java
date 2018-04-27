package gq.jingge.blog.base.thread.chapter05;

/**
 * @author wangyj
 * @description
 * @create 2018-04-27 11:33
 **/
public class NotifyThread extends Thread {
    private Object lock;

    public NotifyThread(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        Service service = new Service();
        service.synNotifyMethod(lock);
    }
}
