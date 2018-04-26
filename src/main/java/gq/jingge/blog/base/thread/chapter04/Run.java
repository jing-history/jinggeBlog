package gq.jingge.blog.base.thread.chapter04;

/**
 * @author wangyj
 * @description
 * @create 2018-04-26 16:51
 **/
public class Run {

    public static void main(String[] args) throws InterruptedException {
        RunThread thread = new RunThread();

        thread.start();
        Thread.sleep(1000);
        thread.setRunning(false);

        System.out.println("已经赋值为false");
    }
}
