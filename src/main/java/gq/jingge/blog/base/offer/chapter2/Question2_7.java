package gq.jingge.blog.base.offer.chapter2;

import gq.jingge.blog.base.offer.CtCILibrary.LinkedListNode;

/**
 * @author wangyj
 * @description
 * @create 2018-05-07 16:07
 **/
public class Question2_7 {
    public class Result2 {
        public LinkedListNode node;
        public boolean Result2;
        public Result2(LinkedListNode n, boolean res) {
            node = n;
            Result2 = res;
        }
    }

    public Question2_7.Result2 isPalindromeRecurse(LinkedListNode head, int length) {
        if (head == null || length == 0) {
            return new Question2_7.Result2(null, true);
        } else if (length == 1) {
            return new Question2_7.Result2(head.next, true);
        } else if (length == 2) {
            return new Question2_7.Result2(head.next.next, head.data == head.next.data);
        }
        Question2_7.Result2 res = isPalindromeRecurse(head.next, length - 2);
        if (!res.Result2 || res.node == null) {
            return res; // Only "Result2" member is actually used in the call stack.
        } else {
            res.Result2 = head.data == res.node.data;
            res.node = res.node.next;
            return res;
        }
    }

    public boolean isPalindrome(LinkedListNode head) {
        int size = 0;
        LinkedListNode n = head;
        while (n != null) {
            size++;
            n = n.next;
        }
        Result2 p = isPalindromeRecurse(head, size);
        return p.Result2;
    }

    public static void main(String[] args) {
        int length = 10;
        LinkedListNode[] nodes = new LinkedListNode[length];
        for (int i = 0; i < length; i++) {
            nodes[i] = new LinkedListNode(i >= length / 2 ? length - i - 1 : i, null, null);
        }

        for (int i = 0; i < length; i++) {
            if (i < length - 1) {
                nodes[i].setNext(nodes[i + 1]);
            }
            if (i > 0) {
                nodes[i].setPrevious(nodes[i - 1]);
            }
        }
        // nodes[length - 2].data = 9; // Uncomment to ruin palindrome

        LinkedListNode head = nodes[0];
        System.out.println(head.printForward());
        Question2_7 q = new Question2_7();
        System.out.println(q.isPalindrome(head));
    }
}
