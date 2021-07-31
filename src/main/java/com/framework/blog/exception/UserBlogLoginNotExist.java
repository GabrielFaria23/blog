package com.framework.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserBlogLoginNotExist extends Exception {
    public UserBlogLoginNotExist(String message) {
        super(message);
    }
}
