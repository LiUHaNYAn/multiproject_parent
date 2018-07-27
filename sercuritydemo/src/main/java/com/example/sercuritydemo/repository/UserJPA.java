package com.example.sercuritydemo.repository;

import com.example.sercuritydemo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJPA extends JpaRepository<User,Long> {
    public  User findUserByName(String username);
}
