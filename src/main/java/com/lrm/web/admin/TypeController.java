package com.lrm.web.admin;

import com.lrm.po.Type;
import com.lrm.service.TypeService;
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
public class TypeController {
    @Autowired
    private TypeService typeService;

    //分页查询，每一页10条数据，按照id倒序
    @GetMapping("/types")
    public String types(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));

        return "admin/types";
    }
    //跳转分类新增
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }
    //编辑页面
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }
    //新增判断
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        //判断是否存在
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null){
            result.rejectValue("name","nameError","该分类已存在,不能添加重复分类");

        }

        //判断是否空值
        if(result.hasErrors()){
            return "admin/types-input";
        }
        //新增
        Type t = typeService.saveType(type);
        if(t == null){
             attributes.addFlashAttribute("message","添加失败");
        }else{
             attributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/types";
    }
    //更新分类
    @PostMapping("/types/{id}")
    public String editpost(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        //判断是否存在
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null){
            result.rejectValue("name","nameError","该分类已存在,不能添加重复分类");

        }
        //判断是否空值
        if(result.hasErrors()){
            return "admin/types-input";
        }
        //更新
        Type t = typeService.updateType(id,type);
        if(t == null){
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }
    //删除
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
