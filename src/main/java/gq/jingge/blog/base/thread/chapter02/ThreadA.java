package gq.jingge.blog.base.thread.chapter02;

/**
 * @author wangyj
 * @description
 * @create 2018-04-24 17:35
 **/
public class ThreadA extends Thread {

    private HasSelfPrivateNum numRef;

    public ThreadA(HasSelfPrivateNum numRef) {
        super();
        this.numRef = numRef;
    }

    @Override
    public void run() {
        super.run();
        numRef.addI("a");
    }
}
