package gq.jingge.blog.base.thread.chapter05;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyj
 * @description
 * @create 2018-04-27 10:04
 **/
public class MyList {

    private static List<String> list = new ArrayList<String>();

    public static void add() {
        list.add("anyString");
    }

    public static int size() {
        return list.size();
    }
}
