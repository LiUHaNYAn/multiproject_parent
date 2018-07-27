package com.example.sercuritydemo.service;

import com.example.sercuritydemo.domain.User;
import com.example.sercuritydemo.repository.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserJPA userJPA;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user=userJPA.findUserByName(s);
        if(user==null){
            throw  new UsernameNotFoundException("用户名"+s+"查找不倒");
        }
        return  user;
    }
}
