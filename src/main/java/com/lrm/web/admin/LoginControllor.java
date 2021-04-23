package com.lrm.web.admin;

import com.lrm.po.User;
import com.lrm.service.BlogService;
import com.lrm.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginControllor {

    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;
    //登录
    @GetMapping()
    public String loginPage() {
        return "admin/login";
    }
    //首页
    @GetMapping("/index")
    public String indexPage(Model model) {
        model.addAttribute("blogCount",blogService.countBlog());
        return "admin/index";
    }

    //登录提交
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        if (user != null) {
            user.setUsername(null);
            session.setAttribute("user", user);
            return "admin/index";

        } else {
            attributes.addFlashAttribute("message", "用户名或密码错误");
            return "redirect:/admin";
        }
    }
    //  退出/注销
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }


}
