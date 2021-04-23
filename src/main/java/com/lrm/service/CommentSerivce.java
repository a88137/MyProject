package com.lrm.service;

import com.lrm.po.Comment;

import java.util.List;

public interface CommentSerivce {
    //依据blog id 获取评论列表
    List<Comment> listCommentByBlogId(Long blogId);
    //保存评论对象
    Comment saveComment(Comment comment);
    //统计评论次数
    Long countComment();
}
