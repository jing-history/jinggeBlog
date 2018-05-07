package gq.jingge.blog.base.offer.chapter2;

import gq.jingge.blog.base.offer.CtCILibrary.AssortedMethods;
import gq.jingge.blog.base.offer.CtCILibrary.LinkedListNode;

/**
 * 实现一个算法，删除单向链表中间的某个结点，假定你只能访问该结点
 * @author wangyj
 * @description
 * @create 2018-05-07 15:37
 * 解法是直接将后续结点的数据复制到当前结点，然后删除这个后续结点
 **/
public class Question2_3 {

    private static boolean deleteNode(LinkedListNode n) {
        if (n == null || n.next == null) {
            return false; // Failure
        }
        LinkedListNode next = n.next;
        n.data = next.data;
        n.next = next.next;
        return true;
    }

    public static void main(String[] args) {
        LinkedListNode head = AssortedMethods.randomLinkedList(10, 0, 10);
        System.out.println(head.printForward());
        deleteNode(head.next.next.next.next); // delete node 4
        System.out.println(head.printForward());
    }
}
