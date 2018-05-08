package gq.jingge.blog.base.offer.chapter3;

import gq.jingge.blog.base.offer.CtCILibrary.AssortedMethods;

/**
 * 设计一个栈，除了pop 与 push 方法，还支持min 方法，可返回栈元素中的最小值。
 * @author wangyj
 * @description
 * @create 2018-05-08 16:54
 **/
public class Question3_2 {
    public static void main(String[] args) {
        StackWithMin stack = new StackWithMin();
        StackWithMin2 stack2 = new StackWithMin2();

        for (int i = 0; i < 15; i++) {
            int value = AssortedMethods.randomIntInRange(0, 100);
            stack.push(value);
            stack2.push(value);
            System.out.print(value + ", ");
        }
        System.out.println('\n');
        for (int i = 0; i < 15; i++) {
            System.out.println("Popped " + stack.pop().value + ", " + stack2.pop());
            System.out.println("New min is " + stack.min() + ", " + stack2.min());
        }
    }
}
