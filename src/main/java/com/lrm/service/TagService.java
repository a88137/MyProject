package com.lrm.service;

import com.lrm.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TagService {
    //新增标签
    Tag saveTag(Tag type);
    //获取标签
    Tag getTag(Long id);
    //获取标签列表
    Page<Tag> listTag(Pageable pageable);
    //获取所有标签
    List<Tag> listTag();
    //获取所有标签(String类型)
    List<Tag> listTag(String ids);

    List<Tag> listTagTop(Integer size);

    //更新标签
    Tag updateTag(Long id, Tag tag);
    //删除标签
    void deleteTag(Long id);
    //通过名称查询标签
    Tag getTagByName(String name);

}
