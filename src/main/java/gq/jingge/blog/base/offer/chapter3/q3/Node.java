package gq.jingge.blog.base.offer.chapter3.q3;

/**
 * @author wangyj
 * @description
 * @create 2018-05-15 15:41
 **/
public class Node {
    public Node above;
    public Node below;
    public int value;
    public Node(int value) {
        this.value = value;
    }
}
