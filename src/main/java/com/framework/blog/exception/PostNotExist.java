package com.framework.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PostNotExist extends Exception {
    public PostNotExist(String message){
        super(message);
    }
}
