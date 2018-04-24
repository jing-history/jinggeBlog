package gq.jingge.blog.base.thread.chapter01;

/**
 * @author wangyj
 * @description
 * @create 2018-04-24 17:00
 **/
public class MyThread3 extends Thread {
    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 500000; i++) {
            if (this.interrupted()) {
                System.out.println("已经是停止状态了!我要退出了!");
                break;
            }
            System.out.println("i=" + (i + 1));
        }
        System.out.println("看到这句话说明线程并未终止------");
    }
}

class Run3{
    public static void main(String[] args) {
        try {
            MyThread3 thread = new MyThread3();
            thread.start();
            Thread.sleep(2000);
            thread.interrupt();
        } catch (InterruptedException e) {
            System.out.println("main catch");
            e.printStackTrace();
        }
        System.out.println("end!");
    }
}
