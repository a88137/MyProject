package com.lrm.web;

import com.lrm.po.Message;
import com.lrm.po.User;
import com.lrm.service.BlogService;
import com.lrm.service.CommentSerivce;
import com.lrm.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MessageContriller {
    @Autowired
    private MessageService messageService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentSerivce commentSerivce;

    @Value("${message.avatar}")
    private String avatar;
    //get评论区
    @GetMapping("/messages")
    public String messages(Model modeld){
        modeld.addAttribute("messages",messageService.listMessage());
        return "message :: messageList";
    }

    //get评论区
    @GetMapping("/message")
    public String message(Model model){
        model.addAttribute("blogCount",blogService.countBlog());
        model.addAttribute("commentCount",commentSerivce.countComment());
        model.addAttribute("summessage",messageService.countMessage());
        model.addAttribute("sumviews",blogService.countViewBlog());
        return "message";
    }

    //留言发送
    @PostMapping("/messages")
    public String post(Message message, HttpSession session){
        User user = (User)session.getAttribute("user");
        //判断是否是博主
        if(user != null){
            message.setAvatar(user.getAvatar());
            message.setAdminMessage(true);
            message.setNickname(user.getNickname());
            message.setEmail(user.getEmail());
        }else {
            //统一游客头像
            message.setAvatar(avatar);
        }

        //保存
        messageService.saveMessage(message);
        return "redirect:messages";
    }

}
