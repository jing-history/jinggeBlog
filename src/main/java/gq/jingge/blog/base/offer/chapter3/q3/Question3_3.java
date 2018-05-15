package gq.jingge.blog.base.offer.chapter3.q3;

/**
 * @author wangyj
 * @description
 * @create 2018-05-15 15:37
 **/
public class Question3_3 {
    public static void main(String[] args) {
        int capacity_per_substack = 5;
        SetOfStacks set = new SetOfStacks(capacity_per_substack);
        for (int i = 0; i < 34; i++) {
            set.push(i);
        }
        for (int i = 0; i < 34; i++) {
            System.out.println("Popped " + set.pop());
        }
    }
}
