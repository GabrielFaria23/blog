package com.framework.blog.service;

import com.framework.blog.exception.UserBlogLoginNotExist;
import com.framework.blog.exception.UserBlogNotExist;
import com.framework.blog.model.UserBlog;
import com.framework.blog.repository.UserBlogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserBlogService {

    private final UserBlogRepository userBlogRepository;

    public UserBlogService(UserBlogRepository userBlogRepository) {
        this.userBlogRepository = userBlogRepository;
    }

    public UserBlog createUserBlog(UserBlog userBlog){
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
        userToBeUpdated.setCpf(userBlog.getCpf());
        userToBeUpdated.setPassword(userBlog.getPassword());

        return userBlogRepository.save(userToBeUpdated);
    }

    public Page<UserBlog> findAllUsers(Pageable pageable){
        return userBlogRepository.findAll(pageable);
    }

    public UserBlog findByLogin(String login) throws UserBlogLoginNotExist {
        return checkUserLoginExist(login);
    }

    private UserBlog checkUserLoginExist(String login) throws UserBlogLoginNotExist {
        return userBlogRepository.findByLogin(login)
                .orElseThrow(() -> new UserBlogLoginNotExist("Cannot find user with login: "+ login +" /n" +
                        "Please insert another login and try again!"));
    }

    private UserBlog checkUserIdExist(Long id) throws UserBlogNotExist {
        return userBlogRepository.findById(id)
                .orElseThrow(() -> new UserBlogNotExist("Cannot find user with id: "+ id +" /n" +
                        "Please insert another id and try again!"));
    }

}
