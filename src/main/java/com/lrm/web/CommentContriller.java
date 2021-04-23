package com.lrm.web;

import com.lrm.po.Comment;
import com.lrm.po.User;
import com.lrm.service.BlogService;
import com.lrm.service.CommentSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentContriller {
    @Autowired
    private CommentSerivce commentSerivce;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;
    //get评论区
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model modeld){
        modeld.addAttribute("comments",commentSerivce.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }
    //post评论区
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        //拿到当前blogid
        Long blogId = comment.getBlog().getId();
        //根据id设置Blog的对象
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User)session.getAttribute("user");
        //判断是否是博主
        if(user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
            comment.setNickname(user.getNickname());
            comment.setEmail(user.getEmail());
        }else {
            //统一游客头像
            comment.setAvatar(avatar);
        }

        //保存
        commentSerivce.saveComment(comment);
        return "redirect:/comments/" +comment.getBlog().getId();
    }

}
