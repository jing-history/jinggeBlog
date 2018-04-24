package gq.jingge.blog.base.thread.chapter02;

/**
 * @author wangyj
 * @description
 * @create 2018-04-24 17:55
 **/
public class Run2 {

    public static void main(String[] args) {
        try {
            PublicVar publicVarRef = new PublicVar();
            ThreadC thread = new ThreadC(publicVarRef);
            thread.start();

            Thread.sleep(200);//打印结果受此值大小影响

            publicVarRef.getValue();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
