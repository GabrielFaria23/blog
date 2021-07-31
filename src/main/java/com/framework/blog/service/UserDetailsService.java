package com.framework.blog.service;

import com.framework.blog.model.UserBlog;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserBlogService userBlogService;

    public UserDetailsService(UserBlogService userBlogService) {
        this.userBlogService = userBlogService;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserBlog userBlog = userBlogService.findByLogin(login);

        if (userBlog.getLogin().equals(login)){
            return new User(login, userBlog.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Cannot find UserBlog with login: "+ login +"/n " +
                    "Please insert another login and try again!");
        }
    }

}

