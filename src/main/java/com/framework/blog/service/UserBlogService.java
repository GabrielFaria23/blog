package com.framework.blog.service;

import com.framework.blog.exception.UserBlogUsernameNotExist;
import com.framework.blog.exception.UserBlogNotExist;
import com.framework.blog.model.UserBlog;
import com.framework.blog.repository.UserBlogRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserBlogService {

    @Autowired
    private UserBlogRepository userBlogRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    public UserBlog createUserBlog(UserBlog userBlog){
        return userDetailsService.signUpUserBlog(userBlog);
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

    public Optional<UserBlog> findByUsername(String username) {
        return userBlogRepository.findByUsername(username);
    }

    private UserBlog checkUserIdExist(Long id) throws UserBlogNotExist {
        return userBlogRepository.findById(id)
                .orElseThrow(() -> new UserBlogNotExist("Cannot find user with id: "+ id +" /n" +
                        "Please insert another id and try again!"));
    }

}
