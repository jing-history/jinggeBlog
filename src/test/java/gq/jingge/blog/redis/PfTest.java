package gq.jingge.blog.redis;

import redis.clients.jedis.Jedis;

/**
 * @author wangyj
 * @description
 * @create 2018-08-09 14:58
 **/
public class PfTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        for (int i = 0; i < 1000; i++) {
            jedis.pfadd("codehole", "user" + i);
            long total = jedis.pfcount("codehole");
            if (total != i + 1) {
                System.out.printf("%d %d\n", total, i + 1);
                break;
            }
        }
        jedis.close();
    }
}
