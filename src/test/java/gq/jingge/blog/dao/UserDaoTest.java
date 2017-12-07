package gq.jingge.blog.dao;

import gq.jingge.JinggeBlogApplication;
import gq.jingge.blog.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by wangyunjing on 2017/12/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JinggeBlogApplication.class)
public class UserDaoTest {

    @Resource
    private UserRepository userRepository;

    @Test
    public void findByEmailTest(){
        String email = "admin@admin.com";
        User user = userRepository.findByEmail(email);
        Assert.assertNotNull(user);
    }
}
