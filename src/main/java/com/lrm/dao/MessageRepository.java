package com.lrm.dao;

import com.lrm.po.Message;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    //按照时间排序
    List<Message> findByParentMessageNull(Sort sort);
}
