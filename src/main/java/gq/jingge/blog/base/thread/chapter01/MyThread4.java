package gq.jingge.blog.base.thread.chapter01;

/**
 * @author wangyj
 * @description
 * @create 2018-04-24 17:07
 **/
public class MyThread4 extends Thread  {
    @Override
    public void run() {
        while (true) {
            if (this.isInterrupted()) {
                System.out.println("ֹͣ停止了!");
                return;
            }
            System.out.println("timer=" + System.currentTimeMillis());
        }
    }
}

class Run4{
    public static void main(String[] args) throws InterruptedException {
        MyThread4 t=new MyThread4();
        t.start();
        Thread.sleep(2000);
        t.interrupt();
    }
}
