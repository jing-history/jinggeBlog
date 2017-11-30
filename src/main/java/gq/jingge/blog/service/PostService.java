package gq.jingge.blog.service;

import gq.jingge.blog.dao.PostRepository;
import gq.jingge.blog.domain.Post;
import gq.jingge.blog.domain.support.PostStatus;
import gq.jingge.blog.domain.support.PostType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by wangyunjing on 2017/11/30.
 */
@Service
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    public static final String CACHE_NAME = "cache.post";
    public static final String CACHE_NAME_ARCHIVE = CACHE_NAME + ".archive";
    public static final String CACHE_NAME_PAGE = CACHE_NAME + ".page";
    public static final String CACHE_NAME_TAGS = CACHE_NAME + ".tag";
    public static final String CACHE_NAME_COUNTS = CACHE_NAME + ".counts_tags";

    @Autowired
    private PostRepository postRepository;

    @Cacheable(value = CACHE_NAME_PAGE, key = "T(java.lang.String).valueOf(#page).concat('-').concat(#pageSize)")
    public Page<Post> getAllPublishedPostsByPage(int page, int pageSize) {
        logger.debug("Get posts by page " + page);

        return postRepository.findAllByPostTypeAndPostStatus(
                PostType.POST,
                PostStatus.PUBLISHED,
                new PageRequest(page, pageSize, Sort.Direction.DESC, "createdAt"));
    }
}
