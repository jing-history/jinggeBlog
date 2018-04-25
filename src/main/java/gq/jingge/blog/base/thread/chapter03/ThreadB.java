package gq.jingge.blog.base.thread.chapter03;

/**
 * @author wangyj
 * @description
 * @create 2018-04-25 15:51
 **/
public class ThreadB extends Thread {
    private Service service;
    private MyObject object;

    public ThreadB(Service service, MyObject object) {
        super();
        this.service = service;
        this.object = object;
    }

    @Override
    public void run() {
        super.run();
        service.testMethod1(object);
    }
}
