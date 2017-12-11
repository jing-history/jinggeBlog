package gq.jingge.blog.dao;

import gq.jingge.blog.domain.Post;
import gq.jingge.blog.domain.support.PostStatus;
import gq.jingge.blog.domain.support.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangyunjing on 2017/11/30.
 */
@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByPostTypeAndPostStatus(PostType postType, PostStatus postStatus, Pageable pageRequest);

    Post findByPermalinkAndPostStatus(String permalink, PostStatus published);

    @Query("SELECT t.name, count(p) as tag_count from Post p " +
            "INNER JOIN p.tags t " +
            "WHERE p.postStatus = :status " +
            "GROUP BY t.id " +
            "ORDER BY tag_count DESC")
    List<Object[]> countPostsByTags(@Param("status") PostStatus status);

    @Query("SELECT p FROM Post p INNER JOIN p.tags t WHERE t.name = :tag")
    Page<Post> findByTag(@Param("tag") String tag, Pageable pageable);
}
