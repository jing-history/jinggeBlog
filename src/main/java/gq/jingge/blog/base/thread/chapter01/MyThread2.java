package gq.jingge.blog.base.thread.chapter01;

/**
 * @author wangyj
 * @description
 * @create 2018-04-24 16:38
 **/
public class MyThread2 extends Thread {
    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 5000000; i++) {
            System.out.println("i=" + (i + 1));
        }
    }
}

class Run2 {
    public static void main(String[] args) {
        try {
            MyThread2 thread = new MyThread2();
            thread.start();
            Thread.sleep(2000);
            thread.interrupt();
        } catch (InterruptedException e) {
            System.out.println("main catch");
            e.printStackTrace();
        }
    }

}
