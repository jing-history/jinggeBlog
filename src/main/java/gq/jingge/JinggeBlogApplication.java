package gq.jingge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 开发不用开启缓存
 * @author wangyj
 * @description
 * @create 2017-06-12 16:15
 **/
@SpringBootApplication
//@EnableCaching
public class JinggeBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(JinggeBlogApplication.class, args);
    }
}
