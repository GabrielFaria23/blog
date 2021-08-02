package com.framework.blog.service;

import com.framework.blog.exception.PermissionDeniedException;
import com.framework.blog.exception.PostNotExist;
import com.framework.blog.model.Post;
import com.framework.blog.model.UserBlog;
import com.framework.blog.repository.PostRepository;
import com.framework.blog.repository.UserBlogRepository;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserBlogService userBlogService;

    public PostService(PostRepository postRepository, UserBlogService userBlogService) {
        this.postRepository = postRepository;
        this.userBlogService = userBlogService;
    }

    public Post createPost(Post post) {
        UserBlog userBlog = userBlogService.userLogged();
        post.setUserBlog(userBlog);
        return postRepository.save(post);
    }

    public void deletePost(Long id) throws PostNotExist, PermissionDeniedException {
        UserBlog userLogged = userBlogService.userLogged();
        Post post = checkPostExist(id);
        if (post.getUserBlog().getId() == userLogged.getId()) {
            postRepository.deleteById(post.getId());
        } else {
            throw new PermissionDeniedException("You don't have permission to delete this post!");
        }
    }

    public Post findById(Long id) throws PostNotExist {
        return checkPostExist(id);
    }

    public Post updatePost(Long id, Post post) throws PostNotExist, PermissionDeniedException {
        UserBlog userLogged = userBlogService.userLogged();
        Post postToBeUpdated = checkPostExist(id);

        if (userLogged.getId() == post.getUserBlog().getId()) {

            postToBeUpdated.setDescription(post.getDescription());
            postToBeUpdated.setLink(post.getLink());
            postToBeUpdated.setUserBlog(userLogged);

            return postRepository.save(postToBeUpdated);
        }else{
            throw new PermissionDeniedException("You don't have permission to update this post!");
        }
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post checkPostExist(Long id) throws PostNotExist {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotExist("Cannot find post with id: " + id + " /n" +
                        "Please insert another id and try again!"));
    }
}
