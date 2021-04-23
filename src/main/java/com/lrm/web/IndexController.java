package com.lrm.web;


import com.lrm.service.*;
import com.lrm.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private CommentSerivce commentSerivce;

    @Autowired
    private TagService tagService;

    @Autowired
    private MessageService messageService;

    //首页博客列表渲染
    @GetMapping("/")
    public String index(@PageableDefault(size = 7,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
        model.addAttribute("blogCount",blogService.countBlog());
        model.addAttribute("commentCount",commentSerivce.countComment());
        model.addAttribute("sumviews",blogService.countViewBlog());
        model.addAttribute("summessage",messageService.countMessage());
        return "index";
    }

    //搜索页博客列表渲染
    @PostMapping("/search")
    public String search(@PageableDefault(size = 7,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("blogCount",blogService.countBlog());
        model.addAttribute("commentCount",commentSerivce.countComment());
        model.addAttribute("sumviews",blogService.countViewBlog());
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        model.addAttribute("blogCount",blogService.countBlog());
        model.addAttribute("commentCount",commentSerivce.countComment());
        model.addAttribute("sumviews",blogService.countViewBlog());
        model.addAttribute("summessage",messageService.countMessage());
        return "blog";
    }
    @GetMapping("/music")
    public String music(){
        return "music";
    }


}
