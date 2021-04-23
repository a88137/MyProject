package com.lrm.web.admin;

import com.lrm.po.Tag;
import com.lrm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    //分页查询，每一页10条数据，按照id倒序
    @GetMapping("/tags")
    public String types(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("page",tagService.listTag(pageable));

        return "admin/tags";
    }
    //跳转分类新增
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }
    //编辑页面
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }
    //新增判断
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        //判断是否存在
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 != null){
            result.rejectValue("name","nameError","该标签已存在,不能添加重复标签");

        }

        //判断是否空值
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        //新增
        Tag t = tagService.saveTag(tag);
        if(t == null){
             attributes.addFlashAttribute("message","添加失败");
        }else{
             attributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/tags";
    }
    //更新标签
    @PostMapping("/tags/{id}")
    public String editpost(@Valid Tag tag, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        //判断是否存在
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 != null){
            result.rejectValue("name","nameError","该标签已存在,不能添加重复标签");

        }
        //判断是否空值
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        //更新
        Tag t = tagService.updateTag(id,tag);
        if(t == null){
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/tags";
    }
    //删除
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }
}
