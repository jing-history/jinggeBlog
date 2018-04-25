package gq.jingge.blog.base.thread.chapter03;

/**
 * @author wangyj
 * @description
 * @create 2018-04-25 16:03
 **/
public class Run4 {

    public static void main(String[] args) {
        Service2 service = new Service2();
        ThreadC c = new ThreadC(service);
        c.setName("A");
        c.start();

        ThreadD d = new ThreadD(service);
        d.setName("B");
        d.start();

        ThreadE e = new ThreadE(service);
        e.setName("C");
        e.start();
    }
}
