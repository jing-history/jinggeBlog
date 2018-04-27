package gq.jingge.blog.base.thread.chapter05;

/**
 * @author wangyj
 * @description
 * @create 2018-04-27 11:37
 **/
public class SynNotifyMethodThread extends Thread {

    private Object lock;

    public SynNotifyMethodThread(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        Service service = new Service();
        service.synNotifyMethod(lock);
    }
}
