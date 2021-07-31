package com.framework.blog.service;

import com.framework.blog.model.UserBlog;
import com.framework.blog.model.token.ConfirmationToken;
import com.framework.blog.repository.UserBlogRepository;
import com.framework.blog.service.token.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserBlogService userBlogService;

    @Autowired
    private UserBlogRepository userBlogRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userBlogService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with username: "+ username +" /n" +
                        "Please insert another username and try again!"));
    }

    public UserBlog signUpUserBlog(UserBlog userBlog){
        boolean userBlogExists = userBlogRepository.findByUsername(userBlog.getUsername()).isPresent();

        if (userBlogExists) {
            throw new IllegalStateException("Username already being used");
        }

        String encodePassword = bCryptPasswordEncoder.encode(userBlog.getPassword());

        userBlog.setPassword(encodePassword);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(1),
                userBlog
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return userBlogRepository.save(userBlog);
    }

    public UserBlog signInUserBlog(UserBlog userBlog){
        boolean userBlogExists = userBlogRepository.findByUsername(userBlog.getUsername()).isPresent();

        if (userBlogExists) {
            throw new IllegalStateException("Username already being used");
        }

        String encodePassword = bCryptPasswordEncoder.encode(userBlog.getPassword());

        userBlog.setPassword(encodePassword);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(1),
                userBlog
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return userBlogRepository.save(userBlog);
    }

}

