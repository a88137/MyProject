package com.lrm.web;

import com.lrm.service.BlogService;
import com.lrm.service.CommentSerivce;
import com.lrm.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutShowContriller {
    @Autowired
    private BlogService blogService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private CommentSerivce commentSerivce;

    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("blogCount",blogService.countBlog());
        model.addAttribute("commentCount",commentSerivce.countComment());
        model.addAttribute("sumviews",blogService.countViewBlog());
        model.addAttribute("summessage",messageService.countMessage());

        return "about";
    }
}
