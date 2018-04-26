package gq.jingge.blog.base.thread.chapter04;

/**
 * @author wangyj
 * @description
 * @create 2018-04-26 17:00
 **/
public class Run2 {

    public static void main(String[] args) {

        MyThread2[] mythreadArray = new MyThread2[100];
        for (int i = 0; i < 100; i++) {
            mythreadArray[i] = new MyThread2();
        }
        //要保证数据的原子性还是要使用synchronized关键字

        for (int i = 0; i < 100; i++) {
            mythreadArray[i].start();
        }
    }
}
