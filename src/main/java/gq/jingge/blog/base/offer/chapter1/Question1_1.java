package gq.jingge.blog.base.offer.chapter1;

/**
 * 算法1.1 实现一个算法，确定一个字符串的所有字符是否全部不同。假使不允许使用额外的数据结构，如何处理；
 * @author wangyj
 * @description
 * @create 2018-04-17 15:13
 *
 * 解法：首先假定字符集是ASCII，可以做一个简单的优化，若字符串的长度大于字母表
 * 中的字符个数，则直接返回false。毕竟，字母表只有256个字符，字符串里就不可能
 * 有280 各不相同的字符。
 * 下面的解法是构建一个布尔值的数组，索引值i对应的标记指示该字符串是否含有字母
 * 表第i 个字符。若这个字符第二次出现，则立即返回false。
 **/
public class Question1_1 {

    private static boolean isUniqueChars(String str) {
        if(str.length() > 128){
            return false;
        }
        int checker = 0;
        for (int i = 0; i < str.length(); i++) {
            int val = str.charAt(i) - 'a';
            if ((checker & (1 << val)) > 0) return false;
            checker |= (1 << val);
        }
        return true;
    }

    private static boolean isUniqueChars2(String str) {
        if(str.length() > 256){
            return false;
        }
        boolean[] char_set = new boolean[256];
        for (int i = 0; i < str.length(); i++) {
            int val = str.charAt(i);
            if(char_set[val])
                return false;
            char_set[val] = true;
        }
        return true;
    }

    public static void main(String[] args) {
        String[] words = {"abcde", "hello", "apple", "kite", "padle"};
        for (String word : words) {
            System.out.println(word + ": " + isUniqueChars(word) + " " + isUniqueChars2(word));
        }
    }


}
