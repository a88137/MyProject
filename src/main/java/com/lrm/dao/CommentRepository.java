package com.lrm.dao;

import com.lrm.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    //根据博客ID获取，并且按照时间排序
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
