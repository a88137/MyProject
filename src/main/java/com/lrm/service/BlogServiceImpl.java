package com.lrm.service;

import com.lrm.NotFoundException;
import com.lrm.dao.BlogRepository;
import com.lrm.po.Blog;
import com.lrm.po.Type;
import com.lrm.util.MarkdownUtils;
import com.lrm.util.MyBeanUtils;
import com.lrm.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {
    //获取BlogRepository接口

    @Autowired
    private BlogRepository blogRepository;
    //根据id获取博客
    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.getOne(id);
        if (blog==null){
            throw  new NotFoundException("选择博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        //获取博客内容
        String content = b.getContent();
        //将Markdown转成HTML5
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        //更新浏览次数
        blogRepository.updateViews(id);
        return b;
    }

    //分页查询博客,动态查询
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(!"".equals(blog.getTitle()) && blog.getTitle() != null){//非空判断
                    //获取标题并查询
                    predicates.add(cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));

                }
                if(blog.getTypeId()!= null){
                    //获取类型列表并根据id查询
                    predicates.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                if(blog.isRecommend()){
                    //获取推荐是否查询
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                //自判断以上if条件执行
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }
    //首页查询列表分页
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }
    //根据标签查询博客
    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb ) {
                //关联
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    //指定数量的博客列表
    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        //构建排序按更新时间倒序排序
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable =PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        //拿到年份
        List<String> years = blogRepository.findGroupYear();
        //创建集合
        Map<String, List<Blog>> map = new HashMap<>();
        //循环年份
        for(String year : years){
            //拿到年份对应的博客列表
            map.put(year, blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Override
    public Long countViewBlog() {
        return blogRepository.sumviewblog();
    }


    //搜索查询列表分页  根据内容与标题搜索
    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {

        return blogRepository.findByQuery(query,pageable);
    }

    //新增博客
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId()==null){
            //新增     自获取更新时间，创建时间，浏览次数
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else{
            //更新   自获取更新时间
            blog.setUpdateTime(new Date());

        }
        return blogRepository.save(blog);
    }
    //更新博客
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.getOne(id);
        if(b == null){
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }
    //删除博客
    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
