package com.lrm.service;

import com.lrm.dao.UserResponsitory;
import com.lrm.po.User;
import com.lrm.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserResponsitory userResponsitory;

    @Override
    public User checkUser(String username, String password) {
        User user = userResponsitory.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
