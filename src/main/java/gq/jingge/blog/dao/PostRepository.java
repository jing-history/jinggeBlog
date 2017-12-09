package gq.jingge.blog.dao;

import gq.jingge.blog.domain.Post;
import gq.jingge.blog.domain.support.PostStatus;
import gq.jingge.blog.domain.support.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wangyunjing on 2017/11/30.
 */
@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByPostTypeAndPostStatus(PostType postType, PostStatus postStatus, Pageable pageRequest);

    Post findByPermalinkAndPostStatus(String permalink, PostStatus published);
}
