package gq.jingge.blog.base.thread.chapter06;

/**
 * 不使用join方法的弊端演示
 * @author wangyj
 * @description
 * @create 2018-04-28 9:44
 *
 * 在很多情况下，主线程生成并起动了子线程，如果子线程里要进行大量的耗时的运算，主线程往往将于子线程之前结束，
 * 但是如果主线程处理完其他的事务后，需要用到子线程的处理结果，也就是<font color="red">主线程需要等待子线程执行完成之后再结束，
 * 这个时候就要用到join()方法了。另外，一个线程需要等待另一个线程也需要用到join()方法</font>。
 **/
public class Test {

    public static void main(String[] args) throws InterruptedException {

        MyThread threadTest = new MyThread();
        threadTest.start();

        //Thread.sleep(?);//因为不知道子线程要花的时间这里不知道填多少时间
        System.out.println("我想当threadTest对象执行完毕后我再执行");
    }
    static public class MyThread extends Thread {

        @Override
        public void run() {
            System.out.println("我想先执行");
        }

    }
}
