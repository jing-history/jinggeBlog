package gq.jingge.blog.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.UUID;

/**
 * Redis 延时队列实现
 * @author wangyj
 * @description
 * @create 2018-08-09 10:36
 **/
public class RedisDelayingQueue<T> {

    static class TaskItem<T> {
        public String id;
        public T msg;
    }

    // fastjson 序列化对象中存在 generic 类型时，需要使用 TypeReference
    private Type TaskType = new TypeReference<TaskItem<T>>(){}.getType();

    private Jedis jedis;
    private String queueKey;

    public RedisDelayingQueue(Jedis jedis, String queueKey) {
        this.jedis = jedis;
        this.queueKey = queueKey;
    }

    public void delay(T msg){
        TaskItem<T> task = new TaskItem<>();
        // 分配唯一的 uuid
        task.id = UUID.randomUUID().toString();
        task.msg = msg;
        // fastjson 序列化
        String s = JSON.toJSONString(task);
        // 塞入延时队列 ,5s 后再试
        jedis.zadd(queueKey, System.currentTimeMillis() + 5000, s);
    }

    public void loop(){
        while (!Thread.interrupted()){
            // 只取一条
            Set<String> values = jedis.zrangeByScore(queueKey,0, System.currentTimeMillis(), 0, 1);
            if(values.isEmpty()){
                try{
                    Thread.sleep(500);  //歇会继续
                }catch (Exception e){
                    break;
                }
                continue;
            }
            String s = values.iterator().next();
            //抢到了
            if(jedis.zrem(queueKey, s) > 0){
                // fastjson 反序列化
                TaskItem<T> task = JSON.parseObject(s, TaskType);
                this.handleMsg(task.msg);
            }
        }
    }

    public void handleMsg(T msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        RedisDelayingQueue<String> queue = new RedisDelayingQueue<>(jedis, "q-demo");
        Thread producer = new Thread(){
          
            public void run(){
                for (int i = 0; i < 10; i++) {
                    queue.delay("codehole" + i);
                }
            }
        };

        Thread consumer = new Thread(){
            public void run() {
                queue.loop();
            }
        };

        producer.start();
        consumer.start();

        try{
            producer.join();
            Thread.sleep(6000);
            consumer.interrupt();
            consumer.join();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
