package gq.jingge.blog.base.jvm;

import java.util.HashMap;

/**
 * HashMap多线程并发问题分析
 * https://www.jianshu.com/p/93580534fdb3
 * @author wangyj
 * @description
 * @create 2018-04-19 9:11
 * 多线程put的时候可能导致元素丢失
 * 主要问题出在addEntry方法的new Entry<K,V>(hash, key, value, e)，如果两个线程都同时取得了e,
 * 则他们下一个元素都是e，然后赋值给table元素的时候有一个成功有一个丢失。
 *
 * HashMap通常会用一个指针数组（假设为table[]）来做分散所有的key，当一个key被加入时，
 * 会通过Hash算法通过key算出这个数组的下标i，然后就把这个<key, value>插到table[i]中，
 * 如果有两个不同的key被算在了同一个i，那么就叫冲突，又叫碰撞，这样会在table[i]上形成一个链表。
 *
 * 于是一个O(1)的查找算法，就变成了链表遍历，性能变成了O(n)，这是Hash表的缺陷。
 *
 * 所以，Hash表的尺寸和容量非常的重要。一般来说，Hash表这个容器当有数据要插入时，
 * 都会检查容量有没有超过设定的thredhold，如果超过，需要增大Hash表的尺寸，
 * 但是这样一来，整个Hash表里的元素都需要被重算一遍。这叫rehash，这个成本相当的大。
 **/
public class HashMapLock {

    private HashMap map = new HashMap();

    public HashMapLock() {
        Thread t1 = new Thread() {
            public void run() {
                for (int i = 0; i < 50000; i++) {
                    map.put(new Integer(i), i);
                }
                System.out.println("t1 over");
            }
        };

        Thread t2 = new Thread() {
            public void run() {
                for (int i = 0; i < 50000; i++) {
                    map.put(new Integer(i), i);
                }

                System.out.println("t2 over");
            }
        };

        Thread t3 = new Thread() {
            public void run() {
                for (int i = 0; i < 50000; i++) {
                    map.put(new Integer(i), i);
                }

                System.out.println("t3 over");
            }
        };

        Thread t4 = new Thread() {
            public void run() {
                for (int i = 0; i < 50000; i++) {
                    map.put(new Integer(i), i);
                }

                System.out.println("t4 over");
            }
        };

        Thread t5 = new Thread() {
            public void run() {
                for (int i = 0; i < 50000; i++) {
                    map.put(new Integer(i), i);
                }

                System.out.println("t5 over");
            }
        };

        Thread t6 = new Thread() {
            public void run() {
                for (int i = 0; i < 50000; i++) {
                    map.get(new Integer(i));
                }

                System.out.println("t6 over");
            }
        };

        Thread t7 = new Thread() {
            public void run() {
                for (int i = 0; i < 50000; i++) {
                    map.get(new Integer(i));
                }

                System.out.println("t7 over");
            }
        };

        Thread t8 = new Thread() {
            public void run() {
                for (int i = 0; i < 50000; i++) {
                    map.get(new Integer(i));
                }

                System.out.println("t8 over");
            }
        };

        Thread t9 = new Thread() {
            public void run() {
                for (int i = 0; i < 50000; i++) {
                    map.get(new Integer(i));
                }

                System.out.println("t9 over");
            }
        };

        Thread t10 = new Thread() {
            public void run() {
                for (int i = 0; i < 50000; i++) {
                    map.get(new Integer(i));
                }

                System.out.println("t10 over");
            }
        };

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();
    }

    public static void main(String[] args) {
     //   new HashMapLock();
    }
}
