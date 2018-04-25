package gq.jingge.blog.base.thread.chapter03;

/**
 * @author wangyj
 * @description
 * @create 2018-04-25 16:03
 **/
public class ThreadE extends Thread{

    private Service2 service;
    public ThreadE(Service2 service) {
        super();
        this.service = service;
    }
    @Override
    public void run() {
        service.printC();
    }
}
