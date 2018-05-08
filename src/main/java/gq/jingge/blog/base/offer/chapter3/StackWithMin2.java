package gq.jingge.blog.base.offer.chapter3;

import java.util.Stack;

/**
 * @author wangyj
 * @description
 * @create 2018-05-08 17:54
 **/
public class StackWithMin2 extends Stack<Integer>{
    Stack<Integer> s2;

    public StackWithMin2() {
        s2 = new Stack<Integer>();
    }

    public void push(int value){
        if(value <= min()){
            s2.push(value);
        }
        super.push(value);
    }

    public Integer pop(){
        int value = super.pop();
        if(value == min()){
            s2.pop();
        }
        return value;
    }

    public int min() {
        if(s2.isEmpty()){
            return Integer.MAX_VALUE;
        }else {
            //java.util.Stack这个类不难，但需要注意其中的peek()方法： 查看栈顶对象而不移除它
            return s2.peek();
        }
    }
}
