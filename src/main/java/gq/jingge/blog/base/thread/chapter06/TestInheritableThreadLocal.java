package gq.jingge.blog.base.thread.chapter06;

import java.util.Date;

/**
 * @author wangyj
 * @description
 * @create 2018-04-28 9:56
 *
 * 从运行结果可以看出子线程和父线程各自拥有各自的值
 **/
public class TestInheritableThreadLocal {

    public static void main(String[] args) {
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println("       在Main线程中取值=" + Tools.tl.get());
                Thread.sleep(100);
            }
            Thread.sleep(5000);
            ThreadA a = new ThreadA();
            a.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static public class Tools {
        public static InheritableThreadLocalExt  tl = new InheritableThreadLocalExt();
    }

    static public class InheritableThreadLocalExt  extends InheritableThreadLocal  {
        @Override
        protected Object initialValue() {
            return new Date().getTime();
        }

        @Override
        protected Object childValue(Object parentValue) {
            return parentValue + " 我在子线程加的~!";
        }
    }

    static public class ThreadA extends Thread {

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("在ThreadA线程中取值=" + Tools.tl.get());
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}
