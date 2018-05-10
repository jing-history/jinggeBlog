package gq.jingge.blog.service;

import gq.jingge.JinggeBlogApplication;
import gq.jingge.blog.dao.LoveRepository;
import gq.jingge.blog.domain.Love;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by wangyunjing on 2017/12/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JinggeBlogApplication.class)
public class LoveServiceTest {

    private final static Logger LOG =  LoggerFactory.getLogger(LoveServiceTest.class);

    @Autowired
    private LoveRepository loveRepository;

    @Test
    public void myLoves(){
        List<Love> loveList = loveRepository.findAll();
    }

    @Test
    public void addLoves(){
        Love love = new Love("My Test",
                "先写奥斯卡等级啊是",
                "http://s1.wailian.download/2017/12/18/2017.md.jpg",
                "这是图片",
                "PS 了一晚上才诞生的 logo");

        loveRepository.save(love);
    }
}
