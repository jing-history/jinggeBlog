package gq.jingge.blog.base.offer.chapter3;

/**
 * 汉诺塔问题
 * @author wangyj
 * @description
 * @create 2018-05-17 17:10
 **/
public class Question3_4 {

    public static void main(String[] args) {
        // Set up code.
        int n = 5;
        Tower[] towers = new Tower[3];

        for (int i = 0; i < 3; i++) {
            towers[i] = new Tower(i);
        }
        for (int i = n - 1; i >= 0; i--) {
            towers[0].add(i);
        }

        // Copy and paste output into a .XML file and open it with internet explorer.
        //towers[0].print();
        towers[0].moveDisks(n, towers[2], towers[1]);
        //towers[2].print();
    }
}
