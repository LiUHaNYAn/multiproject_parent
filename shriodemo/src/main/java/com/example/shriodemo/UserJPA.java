package com.example.shriodemo;

import com.example.shriodemo.domain.UserInfo;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJPA extends JpaRepository<UserInfo,Long> {
    public UserInfo findUserByUsername(String username);
}
