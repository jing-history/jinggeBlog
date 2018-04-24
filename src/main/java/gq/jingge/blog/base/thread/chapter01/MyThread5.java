package gq.jingge.blog.base.thread.chapter01;

/**
 * @author wangyj
 * @description
 * @create 2018-04-24 17:15
 **/
public class MyThread5 extends Thread {
    @Override
    public void run() {
        System.out.println("MyThread1 run priority=" + this.getPriority());
        MyThread6 thread6 = new MyThread6();
        thread6.start();
    }
}

class MyThread6 extends Thread{
    @Override
    public void run() {
        System.out.println("MyThread2 run priority=" + this.getPriority());
    }
}

class Run5{
    public static void main(String[] args) {
        System.out.println("main thread begin priority="
                + Thread.currentThread().getPriority());
        Thread.currentThread().setPriority(6);
        System.out.println("main thread end   priority="
                + Thread.currentThread().getPriority());
        MyThread5 thread5 = new MyThread5();
        thread5.start();
    }
}
