package gq.jingge.blog.base.thread.core;

/**
 * https://www.cnblogs.com/yxysuanfa/p/7125761.html
 * 了解了基本应用场景后，接下来看一个样例。定义一个类用于存放静态的ThreadLocal对象，
 * 通过多个线程并行地对ThreadLocal对象进行set、get操作，并将值进行打印。
 * 来看看每一个线程自己设置进去的值和取出来的值是否是一样的。
 * @author wangyj
 * @description
 * @create 2018-04-26 17:31
 *
 * ◎ 定义了两个ThreadLocal变量。终于的目的就是要看最后两个值能否相应上。这样才有机会证明ThreadLocal所保存的数据可能是线程私有的。
 * ◎ 使用两个内部类仅仅是为了使測试简单，方便大家直观理解，大家也能够将这个样例的代码拆分到多个类中，得到的结果是同样的。
 * ◎ 測试代码更像是为了方便传递參数。由于它确实传递參数非常方便，但这不过为了測试。
 * ◎ 在finally里面有remove()操作，是为了清空数据而使用的。
 **/
public class ThreadLocalTest {

    static class ResourceClass {
        public final static ThreadLocal<String> RESOURCE_1 = new ThreadLocal<String>();
        public final static ThreadLocal<String> RESOURCE_2 = new ThreadLocal<String>();
    }

    static class A {
        public void setOne(String value) {
            ResourceClass.RESOURCE_1.set(value);
        }
        public void setTwo(String value) {
            ResourceClass.RESOURCE_2.set(value);
        }
    }

    static class B {
        public void display() {
            System.out.println(ResourceClass.RESOURCE_1.get()
                    + ":" + ResourceClass.RESOURCE_2.get());
        }
    }

    public static void main(String[] args) {
        final A a = new A();
        final B b = new B();

        for(int i = 0 ; i < 15 ; i ++) {
            final String resouce1 = "线程-" + i;
            final String resouce2 = " value = (" + i + ")";

            new Thread() {
                public void run() {
                    try {
                        a.setOne(resouce1);
                        a.setTwo(resouce2);
                        b.display();
                    }finally {
                        ResourceClass.RESOURCE_1.remove();
                        ResourceClass.RESOURCE_2.remove();
                    }
                }
            }.start();
        }
    }
}
