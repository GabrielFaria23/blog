package com.framework.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class PermissionDeniedException extends Exception {
    public PermissionDeniedException(String message) {
        super(message);
    }
}
