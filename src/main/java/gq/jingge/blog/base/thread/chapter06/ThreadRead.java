package gq.jingge.blog.base.thread.chapter06;

import java.io.PipedInputStream;

/**
 * @author wangyj
 * @description
 * @create 2018-04-28 9:30
 **/
public class ThreadRead extends Thread{

    private ReadData read;
    private PipedInputStream input;

    public ThreadRead(ReadData read, PipedInputStream input) {
        super();
        this.read = read;
        this.input = input;
    }

    @Override
    public void run() {
        read.readMethod(input);
    }
}
