package com.framework.blog.service;

import com.framework.blog.exception.PostNotExist;
import com.framework.blog.model.Post;
import com.framework.blog.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(Post post){
        return postRepository.save(post);
    }

    public void deletePost(Long id) throws PostNotExist {
        postRepository.deleteById(checkPostExist(id).getId());
    }

    public Post findById(Long id) throws PostNotExist {
        return checkPostExist(id);
    }

    public Post updatePost(Long id, Post post) throws PostNotExist {
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
