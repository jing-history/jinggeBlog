package gq.jingge.blog.base.thread.chapter02;

/**
 * @author wangyj
 * @description
 * @create 2018-04-24 18:06
 **/
public class Sub extends Main {

    synchronized public void operateISubMethod() {
        try {
            while (i > 0) {
                i--;
                System.out.println("sub print i=" + i);
                Thread.sleep(100);
                this.operateIMainMethod();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
