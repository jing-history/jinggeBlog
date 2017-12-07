package gq.jingge.blog.service;

import gq.jingge.JinggeBlogApplication;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by wangyunjing on 2017/12/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JinggeBlogApplication.class)
public class PostServiceTest {

    private final static Logger LOG =  LoggerFactory.getLogger(PostServiceTest.class);

    @Autowired
    private PostService postService;


}
