package com.framework.blog.repository.util;

import com.framework.blog.model.UserBlog;

public class PhotoCreator {

    public static UserBlog createUserBlogToBeSaved(){
        return UserBlog.builder()
                .name("Gabriel")
                .username("gabriel")
                .password("123")
                .cpf("95542836011")
                .build();
    }

    public static UserBlog   createValidUserBlog(){
        return UserBlog.builder()
                .id(5L)
                .name("Gabriel")
                .username("gabriel")
                .password("123")
                .cpf("95542836011")
                .build();
    }

    public static UserBlog   createValidUpdateUserBlog(){
        return UserBlog.builder()
                .id(5L)
                .name("Gabriel Faria")
                .username("gabriel faria")
                .password("123")
                .cpf("95542836011")
                .build();
    }
}
