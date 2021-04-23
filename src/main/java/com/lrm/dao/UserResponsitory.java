package com.lrm.dao;

import com.lrm.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserResponsitory extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username, String password);
}
