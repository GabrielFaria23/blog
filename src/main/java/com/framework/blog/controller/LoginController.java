package com.framework.blog.controller;

import com.framework.blog.model.Login;
import com.framework.blog.model.UserBlog;
import com.framework.blog.service.UserBlogService;
import com.framework.blog.service.UserDetailsService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Api(value = "Endpoint of Login", description = "Endpoint used to Login on Blog", tags = "Endpoint of Login")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/v1/login")
public class LoginController {

    private final UserDetailsService userDetailsService;

    @PostMapping
    public UserBlog login(@RequestBody Login login){

    }

}
