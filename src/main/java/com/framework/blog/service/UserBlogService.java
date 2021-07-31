package com.framework.blog.service;

import com.framework.blog.exception.UserBlogNotExist;
import com.framework.blog.model.UserBlog;
import com.framework.blog.repository.UserBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserBlogService{

    @Autowired
    private UserBlogRepository userBlogRepository;

    @Autowired
    private UserBlogService userBlogService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserBlog createUserBlog(UserBlog userBlog){
        userBlog.setPassword(passwordEncoder.encode(userBlog.getPassword()));
        return userBlogService.createUserBlog(userBlog);
    }

    public void deleteUserBlog(Long id) throws UserBlogNotExist {
        userBlogRepository.deleteById(checkUserIdExist(id).getId());
    }

    public UserBlog findById(Long id) throws UserBlogNotExist {
        return checkUserIdExist(id);
    }

    public UserBlog updateUserBlog(Long id, UserBlog userBlog) throws UserBlogNotExist {
        UserBlog userToBeUpdated = checkUserIdExist(id);

        userToBeUpdated.setName(userBlog.getName());
        userToBeUpdated.setCpf(userBlog.getCpf());
        userToBeUpdated.setPassword(userBlog.getPassword());

        return userBlogRepository.save(userToBeUpdated);
    }

    public Page<UserBlog> findAllUsers(Pageable pageable){
        return userBlogRepository.findAll(pageable);
    }

    private UserBlog checkUserIdExist(Long id) throws UserBlogNotExist {
        return userBlogRepository.findById(id)
                .orElseThrow(() -> new UserBlogNotExist("Cannot find user with id: "+ id +" /n" +
                        "Please insert another id and try again!"));
    }

}
