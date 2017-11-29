package gq.jingge.blog.support;

import com.domingosuarez.boot.autoconfigure.jade4j.JadeHelper;
import gq.jingge.blog.service.AppSetting;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

/**
 * Created by wangyunjing on 2017/11/29.
 */
@Service
@JadeHelper("viewHelper")
public class ViewHelper {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd, yyyy");
    private static final SimpleDateFormat DATE_FORMAT_MONTH_DAY = new SimpleDateFormat("MMM dd");

    private AppSetting appSetting;

    private String applicationEnv;
}
