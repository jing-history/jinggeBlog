package gq.jingge.blog.service;

import gq.jingge.blog.dao.PostRepository;
import gq.jingge.blog.domain.Post;
import gq.jingge.blog.domain.Tag;
import gq.jingge.blog.domain.support.PostFormat;
import gq.jingge.blog.domain.support.PostStatus;
import gq.jingge.blog.domain.support.PostType;
import gq.jingge.blog.error.NotFoundException;
import gq.jingge.blog.util.Markdown;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private TagService tagService;

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

    public Set<Tag> parseTagNames(String tagNames) {
        Set<Tag> tags = new HashSet<>();

        if (tagNames != null && !tagNames.isEmpty()) {
            tagNames = tagNames.toLowerCase();
            String[] names = tagNames.split("\\s*,\\s*");
            for (String name : names) {
                tags.add(tagService.findOrCreateByName(name));
            }
        }

        return tags;
    }

    @Caching(evict = {
            @CacheEvict(value = CACHE_NAME_ARCHIVE, allEntries = true),
            @CacheEvict(value = CACHE_NAME_PAGE, allEntries = true),
            @CacheEvict(value = CACHE_NAME_COUNTS, allEntries = true)
    })
    public Post createPost(Post post) {
        if (post.getPostFormat() == PostFormat.MARKDOWN) {
            post.setRenderedContent(Markdown.markdownToHtml(post.getContent()));
        }

        return postRepository.save(post);
    }

    @Cacheable(CACHE_NAME)
    public Post getPublishedPostByPermalink(String permalink) {
        logger.debug("Get post with permalink " + permalink);

        Post post = postRepository.findByPermalinkAndPostStatus(permalink, PostStatus.PUBLISHED);

        if (post == null) {
            throw new NotFoundException("Post with permalink '" + permalink + "' is not found.");
        }

        return post;
    }

    @Cacheable(CACHE_NAME)
    public Post getPost(Long postId) {
        logger.debug("Get post " + postId);

        Post post = postRepository.findOne(postId);

        if (post == null) {
            throw new NotFoundException("Post with id " + postId + " is not found.");
        }

        return post;
    }

    @Cacheable(value = CACHE_NAME_TAGS, key = "#post.id.toString().concat('-tags')")
    public List<Tag> getPostTags(Post post) {
        logger.debug("Get tags of post " + post.getId());

        List<Tag> tags = new ArrayList<>();

        // Load the post first. If not, when the post is cached before while the tags not,
        // then the LAZY loading of post tags will cause an initialization error because
        // of not hibernate connection session
        postRepository.findOne(post.getId()).getTags().forEach(tags::add);
        return tags;
    }

    @Cacheable(value = CACHE_NAME_ARCHIVE, key = "#root.method.name")
    public List<Post> getArchivePosts() {
        logger.debug("Get all archive posts from database.");

        Iterable<Post> posts = postRepository.findAllByPostTypeAndPostStatus(
                PostType.POST,
                PostStatus.PUBLISHED,
                new PageRequest(0, Integer.MAX_VALUE, Sort.Direction.DESC, "createdAt"));

        List<Post> cachedPosts = new ArrayList<>();
        posts.forEach(post -> cachedPosts.add(extractPostMeta(post)));

        return cachedPosts;
    }

    private Post extractPostMeta(Post post) {
        Post archivePost = new Post();
        archivePost.setId(post.getId());
        archivePost.setTitle(post.getTitle());
        archivePost.setPermalink(post.getPermalink());
        archivePost.setCreatedAt(post.getCreatedAt());

        return archivePost;
    }

    public String getTagNames(Set<Tag> tags) {
        if (tags == null || tags.isEmpty())
            return "";

        StringBuilder names = new StringBuilder();
        tags.forEach(tag -> names.append(tag.getName()).append(","));
        names.deleteCharAt(names.length() - 1);

        return names.toString();
    }

    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    public Post updatePost(Post post) {
        if (post.getPostFormat() == PostFormat.MARKDOWN) {
            post.setRenderedContent(Markdown.markdownToHtml(post.getContent()));
        }

        return postRepository.save(post);
    }
}
