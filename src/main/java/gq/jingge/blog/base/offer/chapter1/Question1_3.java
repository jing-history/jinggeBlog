package gq.jingge.blog.base.offer.chapter1;

import java.util.Arrays;

/**
 * 给定2个字符串，确定其中一个字符串的字符重新排序后，能否变成另外一个字符串。
 * @author wangyj
 * @description
 * @create 2018-04-18 9:39
 *
 * 解法：首先确认是否区分大小写，是否要考虑空白字符。这里假定变位词比较区分大小写，
 * 空白也考虑。比较2个字符串时，只要2者长度不同，就不可能变位词。
 * 基于ASCII 。
 **/
public class Question1_3 {

    /**
     * 解法1 若2个字符串互为变位词，那么它们拥有同一组字符，只不过顺序不同。因此，
     * 对字符串排序，组成这2个变位词的字符就会有相同的顺序。我们只需比较排序后的字符串。
     */
    private static boolean permutation(String s, String t) {
        return sort(s).equals(sort(t));
    }

    private static String sort(String s) {
        char[] content = s.toCharArray();
        Arrays.sort(content);
        return new String(content);
    }

    /**
     *  解法2 组成2个单词的字符数相同，我们只需遍历字母表，计算每个字符出现的次数。
     * @param s
     * @param t
     * @return
     */
    private static boolean anagram(String s, String t) {
        if (s.length() != t.length())
            return false;
        int[] letters = new int[256];   //假设条件
        int num_unique_chars = 0;
        int num_completed_t = 0;
        char[] s_array = s.toCharArray();
        for (char c : s_array) {     // 计算字符串s中每个字符出现的次数
            if (letters[c] == 0){
                ++num_unique_chars;
            }
            ++letters[c];
        }
        for (int i = 0; i < t.length(); ++i) {
            int c = (int) t.charAt(i);
            if (letters[c] == 0) { // Found more of char c in t than in t.
                return false;
            }
            --letters[c];
            if (letters[c] == 0) {
                ++num_completed_t;
                if (num_completed_t == num_unique_chars) {
                    // itÕs a match if t has been processed completely
                    return true;
                    //return i == t.length() - 1;
                }
            }
        }
        return false;
    }

    private static boolean anagram2(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] letters = new int[256];   //字母表
        char[] s_array = s.toCharArray();
        for (char c : s_array) { // 计算字符串s中每个字符出现的次数
            letters[c]++;
        }
        for (int i = 0; i < t.length(); i++) {
            int c = (int) t.charAt(i);
            if (--letters[c] < 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String[][] pairs = {{"apple", "papel"}, {"carrot", "tarroc"}, {"hello", "llloh"}};
        for (String[] pair : pairs) {
            String word1 = pair[0];
            String word2 = pair[1];
            boolean anagram = permutation(word1, word2);
            System.out.println(word1 + ", " + word2 + ": " + anagram);
            System.out.println(anagram(word1, word2));

            System.out.println(anagram2(word1, word2));
        }
    }
}
