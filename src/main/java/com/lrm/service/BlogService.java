package com.lrm.service;

import com.lrm.po.Blog;
import com.lrm.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    //根据id查询博客
    Blog getBlog(Long id);
    // 编辑器转换
    Blog getAndConvert(Long id);
    //返回博客列表 分页
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    //返回博客列表 分页
    Page<Blog> listBlog(Pageable pageable);
    //返回博客列表 分页 根据标签查询
    Page<Blog> listBlog(Long tagId,Pageable pageable);
    //返回指定数量的推荐博客 分页
    List<Blog> listRecommendBlogTop(Integer size);
    //返回每个年份的博客列表
    Map<String,List<Blog>> archiveBlog();
    //博客总数
    Long countBlog();
    //博客访问总数
    Long countViewBlog();
    //返回博客列表 分页
    Page<Blog> listBlog(String query,Pageable pageable);
    //新增
    Blog saveBlog(Blog blog);
    //修改
    Blog updateBlog(Long id,Blog blog);
    //删除
    void  deleteBlog(Long id);
}
