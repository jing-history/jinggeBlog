package gq.jingge.blog.base.jvm;

/**
 * 线程死锁 jstack
 * @author wangyj
 * @description
 * @create 2018-04-19 9:27
 **/
public class LockApplication implements Runnable {
    @Override
    public void run() {
        while(true)
        {
            System.out.println("the thread name is:" + Thread.currentThread().getName());
        }
    }
}
class DemoApplication {

    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new LockApplication());
        t.run();
    }
}

