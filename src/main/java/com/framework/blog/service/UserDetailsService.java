package com.framework.blog.service;

import com.framework.blog.model.UserBlog;
import com.framework.blog.repository.UserBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserBlogRepository userBlogRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserBlog userBlog = userBlogRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("UserBlog not found in database"));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        return new User(userBlog.getUsername(), userBlog.getPassword(),authorities);
    }

}

