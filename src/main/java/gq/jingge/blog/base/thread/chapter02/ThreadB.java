package gq.jingge.blog.base.thread.chapter02;

/**
 * @author wangyj
 * @description
 * @create 2018-04-24 17:37
 **/
public class ThreadB extends Thread {

    private HasSelfPrivateNum numRef;

    public ThreadB(HasSelfPrivateNum numRef) {
        super();
        this.numRef = numRef;
    }

    @Override
    public void run() {
        super.run();
        numRef.addI("b");
    }
}
