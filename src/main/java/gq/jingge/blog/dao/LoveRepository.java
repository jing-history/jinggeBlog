package gq.jingge.blog.dao;

import gq.jingge.blog.domain.Love;
import gq.jingge.blog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wangyj
 * @description
 * @create 2018-05-10 14:40
 **/
@Repository
@Transactional
public interface LoveRepository  extends JpaRepository<Love, Long> {
}
