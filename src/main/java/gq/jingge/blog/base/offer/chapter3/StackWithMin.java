package gq.jingge.blog.base.offer.chapter3;

import java.util.Stack;

/**
 * @author wangyj
 * @description
 * @create 2018-05-08 17:04
 **/
public class StackWithMin extends Stack<NodeWithMin> {

    public void push(int value){
        int newMin = Math.min(value, min());
        super.push(new NodeWithMin(value, newMin));
    }

    public int min() {
        if(this.isEmpty()){
            return Integer.MAX_VALUE;
        }else {
            return peek().min;
        }
    }
}
