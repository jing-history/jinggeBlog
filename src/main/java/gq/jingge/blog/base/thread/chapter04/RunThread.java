package gq.jingge.blog.base.thread.chapter04;

/**
 * @author wangyj
 * @description
 * @create 2018-04-26 16:48
 * RunThread类中的isRunning变量没有加上<font color="red">volatile关键字</font>时，
 * 运行以上代码会出现<font color="red">死循环</font>，这是因为isRunning变量虽然被修改但是没有被写到<font color="red">主存</font>中，
 * 这也就导致该线程在<font color="red">本地内存</font>中的值一直为true，这样就导致了死循环的产生。
 *
 * 不存在的！！！(这里还有一点需要强调，下面的内容一定要看，不然你在用volatile关键字时会很迷糊，因为书籍几乎都没有提这个问题)
 * 假如你把while循环代码里加上任意一个输出语句或者sleep方法你会发现死循环也会停止，不管isRunning变量是否被加上了上volatile关键字。
 * 因为：JVM会尽力保证内存的可见性，即便这个变量没有加同步关键字</font>。换句话说，
 * 只要CPU有时间，JVM会尽力去保证变量值的更新。这种与volatile关键字的不同在于，
 * volatile关键字会强制的保证线程的可见性。而不加这个关键字，JVM也会尽力去保证可见性，
 * 但是如果CPU一直有其他的事情在处理，它也没办法。最开始的代码，一直处于死循环中，CPU处于一直占用的状态，
 * 这个时候CPU没有时间，JVM也不能强制要求CPU分点时间去取最新的变量值。而<font color="red">加了输出或者sleep语句之后，
 * CPU就有可能有时间去保证内存的可见性，于是while循环可以被终止
 **/
public class RunThread extends Thread{

    private volatile boolean isRunning = true;
    int m;

    public boolean isRunning() {
        return isRunning;
    }
    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        System.out.println("进入run了");
        while (isRunning == true) {
            int a=2;
            int b=3;
            int c=a+b;
            m=c;
            //假如你把while循环代码里加上任意一个输出语句或者sleep方法你会发现死循环也会停止，不管isRunning变量是否被加上了上volatile关键字
        //    System.out.println(m);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println(m);
        System.out.println("线程被停止了！");
    }
}
