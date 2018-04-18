package gq.jingge.blog.base.offer.chapter1;
/**
 * 编写一个方法将字符串中的空格全部替换为"%20"。
 * 假定改字符串尾部有足够的空间存放新增字符，并且知道
 * 字符串的真实长度。
 * @author wangyj
 * @description
 * @create 2018-04-18 15:38
 *
 * 解法：处理字符串操作的问题时，常见的做法是从字符串尾部开始编辑，
 * 从后往前反向操作。因为字符串尾部有额外的缓冲，可以直接修改，不
 * 用担心覆盖原有的数据。
 * 使用这种方法，算法会进行两次扫描。第一次扫描先数出字符串中有多少空格，
 * 从而算出最重的字符串长度。第二次扫描才真正开始反向编辑字符串。检测到
 * 空格则将%20复制到下一个位置，若不是空白，就复制原先的字符。
 **/
public class Question1_4 {

    private static void replaceSpaces(char[] str, int length) {
        int spaceCount = 0, index, i = 0;
        for (i = 0; i < length; i++) {
            if (str[i] == ' ') {
                spaceCount++;
            }
        }
        index = length + spaceCount * 2;
        str[index] = '\0';
        for (i = length - 1; i >= 0; i--) {
            if (str[i] == ' ') {
                str[index - 1] = '0';
                str[index - 2] = '2';
                str[index - 3] = '%';
                index = index - 3;
            } else {
                str[index - 1] = str[i];
                index--;
            }
        }
    }

    public static void main(String[] args) {
        String str = "abc d e f";
        char[] arr = new char[str.length() + 3 * 2 + 1];
        for (int i = 0; i < str.length(); i++) {
            arr[i] = str.charAt(i);
        }
        replaceSpaces(arr, str.length());
        System.out.println("\"" + String.valueOf(arr) + "\"");
    }


}
