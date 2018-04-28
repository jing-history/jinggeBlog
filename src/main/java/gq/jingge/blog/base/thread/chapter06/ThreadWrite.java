package gq.jingge.blog.base.thread.chapter06;

import java.io.PipedOutputStream;

/**
 * @author wangyj
 * @description
 * @create 2018-04-28 9:39
 **/
public class ThreadWrite extends Thread {

    private WriteData write;
    private PipedOutputStream out;

    public ThreadWrite(WriteData write, PipedOutputStream out) {
        super();
        this.write = write;
        this.out = out;
    }

    @Override
    public void run() {
        write.writeMethod(out);
    }

}
