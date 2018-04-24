package gq.jingge.blog.base.thread.chapter01;

/**
 * @author wangyj
 * @description
 * @create 2018-04-24 16:26
 **/
public class MyThread extends Thread {

    private int count = 5;


/*  不共享数据的情况
  public MyThread(String name) {
        super();
        this.setName(name);
    }*/

    @Override
    public synchronized void run() {
        super.run();
        while (count > 0) {
            count--;
            System.out.println("由 " + MyThread.currentThread().getName()
                    + " 计算，count=" + count);
        }
    }
}

class Run {
    public static void main(String[] args) {
        /*  不共享数据的情况
        MyThread a = new MyThread("A");
        MyThread b = new MyThread("B");
        MyThread c = new MyThread("C");
        a.start();
        b.start();
        c.start();*/

        //共享数据的情况
        MyThread mythread=new MyThread();
        //下列线程都是通过mythread对象创建的
        Thread a=new Thread(mythread,"A");
        Thread b=new Thread(mythread,"B");
        Thread c=new Thread(mythread,"C");
        Thread d=new Thread(mythread,"D");
        Thread e=new Thread(mythread,"E");
        a.start();
        b.start();
        c.start();
        d.start();
        e.start();
    }
}
