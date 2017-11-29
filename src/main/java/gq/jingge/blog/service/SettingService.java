package gq.jingge.blog.service;

import java.io.Serializable;

/**
 * Created by wangyunjing on 2017/11/29.
 */
public interface SettingService {

    Serializable get(String key);
    Serializable get(String key, Serializable defaultValue);
    void put(String key, Serializable value);
}
