package com.framework.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImageNotExist extends Exception {
    public ImageNotExist (String message){
        super(message);
    }
}
