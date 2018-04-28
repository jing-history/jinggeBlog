package gq.jingge.blog.base.thread.chapter06;

import java.io.IOException;
import java.io.PipedOutputStream;

/**
 * @author wangyj
 * @description
 * @create 2018-04-28 9:40
 **/
public class WriteData {

    public void writeMethod(PipedOutputStream out) {
        try {
            System.out.println("write :");
            for (int i = 0; i < 300; i++) {
                String outData = "" + (i + 1);
                out.write(outData.getBytes());
                System.out.print(outData);
            }
            System.out.println();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
