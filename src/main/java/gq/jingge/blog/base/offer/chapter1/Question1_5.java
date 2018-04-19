package gq.jingge.blog.base.offer.chapter1;

/**
 * 利用字符串重复出现的次数，写个方法实现基本的字符串压缩功能。
 * 比如：aabcccccaaa 压缩变成 a2b1c5a3
 * 若压缩后的字符串没有变短，则返回原先的字符串。
 * @author wangyj
 * @description
 * @create 2018-04-19 14:05
 *
 *
 **/
public class Question1_5 {

    private static int countCompression(String str) {
        if (str == null || str.isEmpty()) return 0;
        char last = str.charAt(0);
        int size = 0;
        int count = 1;
        for (int i = 1; i < str.length(); i++) {
            if(str.charAt(i) == last){
                count++;
            }else {
                last = str.charAt(i);
                size += 1 + String.valueOf(count).length();
                count = 1;
            }
        }
        size += 1+ String.valueOf(count).length();
        return size;
    }

    // 推荐使用这种方法
    public static String compressBetter(String str) {
        int size = countCompression(str);
        if (size >= str.length()) {
            return str;
        }
        StringBuffer mystr = new StringBuffer();
        char last = str.charAt(0);
        int count = 1;
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == last) {    //找到重复字符
                count++;
            } else {    //插入字符的数目，更新last 字符
                mystr.append(last);
                mystr.append(count);
                last = str.charAt(i);
                count = 1;
            }
        }
        /*
         * 上面插入字符是当有重复字符改变时候，才会插入字符。我们还需要在函数末尾更新字符串，
         * 因为最后一组重复字符还未放入压缩字符串中。
         */
        mystr.append(last);
        mystr.append(count);
        return mystr.toString();
    }

    /**
     * 第三种 不使用StringBuffer 的
     * 时间复杂度为O(N),空间复杂度为O(N)
     * @param str
     * @return
     */
    public static String compressAlternate(String str) {
        int size = countCompression(str);
        if (size >= str.length()) {
            return str;
        }
        char[] array = new char[size];
        int index = 0;
        char last = str.charAt(0);
        int count = 1;
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == last) {
                count++;
            } else {
                index = setChar(array, last, index, count);
                last = str.charAt(i);
                count = 1;
            }
        }
        index = setChar(array, last, index, count);
        return String.valueOf(array);
    }

    public static int setChar(char[] array, char c, int index, int count) {
        array[index] = c;
        index++;
        char[] cnt = String.valueOf(count).toCharArray();
        for (char x : cnt) {
            array[index] = x;
            index++;
        }
        return index;
    }

    /**
     * 不建议使用的方法，代码执行时间为O(p+k^2),
     * p 为原始字符串长度，k为字符序列的数量，改用StringBuffer来优化
     * StringBuffer的时间复杂度为O(N),空间复杂度为O(N)
     * @param str
     * @return
     */
    public static String compressBad(String str) {
        int size = countCompression(str);
        if (size >= str.length()) {
            return str;
        }
        String mystr = "";
        char last = str.charAt(0);
        int count = 1;
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == last) {
                count++;
            } else {
                mystr += last + "" + count;
                last = str.charAt(i);
                count = 1;
            }
        }
        return mystr + last + count;
    }

    public static void main(String[] args) {
        String str = "abbccccccde";
        int c = countCompression(str);
        String str2 = compressAlternate(str);
        String t = compressBetter(str);
        System.out.println("Compression: " + t);
        System.out.println("Old String (len = " + str.length() + "): " + str);
        System.out.println("New String (len = " + str2.length() + "): " + str2);
        System.out.println("Potential Compression: " + c);
    }

}
