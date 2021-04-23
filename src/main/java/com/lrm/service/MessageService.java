package com.lrm.service;

import com.lrm.po.Message;

import java.util.List;

public interface MessageService {
    //依据blog id 获取评论列表
    List<Message> listMessage();
    //保存评论对象
    Message saveMessage(Message message);
    //统计评论次数
    Long countMessage();
}
