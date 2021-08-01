package com.framework.blog.controller;

import com.framework.blog.exception.PermissionDeniedException;
import com.framework.blog.exception.PostNotExist;
import com.framework.blog.model.Post;
import com.framework.blog.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Endpoint of Posts", description = "Endpoint used to make alterations in post Entity", tags = "Endpoint of Posts")
@RestController
@RequestMapping(value = "/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @ApiOperation(value = "Create Post")
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody @Valid Post post){
        return ResponseEntity.ok(postService.createPost(post));
    }

    @ApiOperation(value = "Delete Post")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable long id) throws PostNotExist, PermissionDeniedException {
        postService.deletePost(id);
    }

    @ApiOperation(value = "Find all Posts")
    @GetMapping
    public ResponseEntity<Page<Post>> findAll(Pageable pageable){
        return ResponseEntity.ok(postService.findAllPosts(pageable));
    }

    @ApiOperation(value = "Find Post by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Post> findById(@PathVariable long id) throws PostNotExist {
        return ResponseEntity.ok(postService.findById(id));
    }

    @ApiOperation(value = "Update Post")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable long id, @RequestBody @Valid Post post, @AuthenticationPrincipal UserDetails userDetails) throws PostNotExist {
        return ResponseEntity.ok(postService.updatePost(id, post, userDetails));
    }
}
