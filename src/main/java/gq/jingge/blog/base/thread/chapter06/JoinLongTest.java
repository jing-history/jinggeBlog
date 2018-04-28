package gq.jingge.blog.base.thread.chapter06;

/**
 * @author wangyj
 * @description
 * @create 2018-04-28 9:51
 *
 * 另外threadTest.join(2000) 和Thread.sleep(2000) 和区别在于：<font color="red">
 *     Thread.sleep(2000)不会释放锁，threadTest.join(2000)会释放锁 </font>。
 **/
public class JoinLongTest {

    public static void main(String[] args) {
        try {
            MyThread threadTest = new MyThread();
            threadTest.start();

            threadTest.join(2000);// 只等2秒
            //Thread.sleep(2000);

            System.out.println("  end timer=" + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static public class MyThread extends Thread {

        @Override
        public void run() {
            try {
                System.out.println("begin Timer=" + System.currentTimeMillis());
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
