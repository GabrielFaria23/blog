package com.framework.blog.service;

import com.framework.blog.exception.UserBlogNotExist;
import com.framework.blog.model.UserBlog;
import com.framework.blog.repository.UserBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBlogService{

    @Autowired
    private UserBlogRepository userBlogRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserBlog createUserBlog(UserBlog userBlog){
        userBlog.setPassword(passwordEncoder.encode(userBlog.getPassword()));
        return userBlogRepository.save(userBlog);
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
        userToBeUpdated.setUsername(userBlog.getUsername());
        userToBeUpdated.setCpf(userBlog.getCpf());
        userToBeUpdated.setPassword(passwordEncoder.encode(userBlog.getPassword()));
        userToBeUpdated.setUserBlogRole(userBlog.getUserBlogRole());

        return userBlogRepository.save(userToBeUpdated);
    }

    public List<UserBlog> findAll(){
        return userBlogRepository.findAll();
    }

    private UserBlog checkUserIdExist(Long id) throws UserBlogNotExist {
        return userBlogRepository.findById(id)
                .orElseThrow(() -> new UserBlogNotExist("Cannot find user with id: "+ id +" /n" +
                        "Please insert another id and try again!"));
    }

    public UserBlog userLogged() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userBlogRepository.findByUsername(username).get();
    }

}
