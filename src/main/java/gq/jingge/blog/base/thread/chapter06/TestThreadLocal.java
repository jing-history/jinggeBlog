package gq.jingge.blog.base.thread.chapter06;

/**
 * @author wangyj
 * @description
 * @create 2018-04-28 9:56
 **/
public class TestThreadLocal {

    public static ThreadLocal<String> t1 = new ThreadLocal<String>();

    public static void main(String[] args) {
        if (t1.get() == null) {
            System.out.println("为ThreadLocal类对象放入值:aaa");
            t1.set("aaaֵ");
        }
        System.out.println(t1.get());
        System.out.println(t1.get());
    }
}
