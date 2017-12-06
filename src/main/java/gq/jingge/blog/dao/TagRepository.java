package gq.jingge.blog.dao;

import gq.jingge.blog.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wangyunjing on 2017/12/6.
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);
}
