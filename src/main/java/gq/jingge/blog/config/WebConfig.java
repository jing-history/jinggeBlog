package gq.jingge.blog.config;

import gq.jingge.blog.support.ViewHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by wangyunjing on 2017/11/29.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private ViewHelper viewHelper;

    @Autowired
    private Environment env;


}
