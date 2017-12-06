package gq.jingge.blog.service;

import gq.jingge.blog.dao.TagRepository;
import gq.jingge.blog.domain.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangyunjing on 2017/12/6.
 */
@Service
public class TagService {

    private TagRepository tagRepository;

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    public static final String CACHE_NAME = "cache.tag";
    public static final String CACHE_NAME_TAGS = "cache.tag.all";

    public static final String CACHE_TYPE = "'_Tag_'";
    public static final String CACHE_KEY = CACHE_TYPE + " + #tagName";
    public static final String CACHE_TAG_KEY = CACHE_TYPE + " + #tag.name";

    @Autowired
    public TagService(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    public Tag findOrCreateByName(String name){
        Tag tag = tagRepository.findByName(name);
        if (tag == null){
            tag = tagRepository.save(new Tag(name));
        }
        return tag;
    }
}
