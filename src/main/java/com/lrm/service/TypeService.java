package com.lrm.service;

import com.lrm.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    //新增分类
    Type saveType(Type type);
    //获取分类
    Type getType(Long id);
    //获取分类列表
    Page<Type> listType(Pageable pageable);
    //更新分类
    Type updateType(Long id,Type type);
    //获取所有分类
    List<Type> listType();
    //获取分类数据列表大小
    List<Type> listTypeTop(Integer size);

    //删除分类
    void deleteType(Long id);
    //通过名称查询Type
    Type getTypeByName(String name);

}
