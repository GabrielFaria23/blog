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
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserBlogRepository userBlogRepository;

    public PostService(PostRepository postRepository, UserBlogRepository userBlogRepository) {
        this.postRepository = postRepository;
        this.userBlogRepository = userBlogRepository;
    }

    public Post createPost(Post post){
        return postRepository.save(post);
    }

    public void deletePost(Long id) throws PostNotExist, PermissionDeniedException {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserBlog userLogged = userBlogRepository.findByUsername(username).get();
        Post post = checkPostExist(id);
        System.out.println(post.getUserBlog().getId());
        if (post.getUserBlog().getId() == userLogged.getId()){
            postRepository.deleteById(userLogged.getId());
        }else{
            throw new PermissionDeniedException("You don't have permission to delete this post!");
        }
    }

    public Post findById(Long id) throws PostNotExist {
        return checkPostExist(id);
    }

    public Post updatePost(Long id, Post post, UserDetails userDetails) throws PostNotExist {
        Post postToBeUpdated = checkPostExist(id);

        postToBeUpdated.setDescription(post.getDescription());
        postToBeUpdated.setImage(post.getImage());
        postToBeUpdated.setLink(post.getLink());

        return postRepository.save(postToBeUpdated);
    }

    public Page<Post> findAllPosts(Pageable pageable){
        return postRepository.findAll(pageable);
    }

    private Post checkPostExist(Long id) throws PostNotExist {
        return postRepository.findById(id)
                .orElseThrow(()-> new PostNotExist("Cannot find post with id: "+ id +" /n" +
                        "Please insert another id and try again!"));
    }
}
