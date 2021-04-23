package com.lrm.service;

import com.lrm.NotFoundException;
import com.lrm.dao.TypeRepository;
import com.lrm.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService{

    @Autowired
    private TypeRepository typeRepository;


    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    //新增分类
    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    //根据id查询分类
    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }

    //查询所有分类
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    //更新分类
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.getOne(id);
        if(t == null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);

        return typeRepository.save(t);
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return typeRepository.findTop(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    //删除分类
    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
