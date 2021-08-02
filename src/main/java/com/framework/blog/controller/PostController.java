package com.framework.blog.controller;

import com.framework.blog.exception.PermissionDeniedException;
import com.framework.blog.exception.PostNotExist;
import com.framework.blog.model.Post;
import com.framework.blog.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(value = "Endpoint of Posts", description = "Endpoint used to make alterations in post Entity", tags = "Endpoint of Posts")
@RestController
@RequestMapping(value = "/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @ApiOperation(value = "Create Post")
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody @Valid Post post) throws PostNotExist {
        Post postSaved = postService.createPost(post);
        postSaved.add(linkTo(methodOn(PostController.class).findById(postSaved.getId())).withSelfRel());
        return ResponseEntity.ok(postSaved);
    }

    @ApiOperation(value = "Delete Post")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable long id) throws PostNotExist, PermissionDeniedException {
        postService.deletePost(id);
    }

    @ApiOperation(value = "Find all Posts")
    @GetMapping
    public ResponseEntity<List<Post>> findAll(){
        List<Post> allPosts = postService.findAll();
        allPosts.forEach(p -> {
            try {
                p.add(linkTo(methodOn(PostController.class).findById(p.getId())).withSelfRel());
            } catch (PostNotExist postNotExist) {
                postNotExist.printStackTrace();
            }
        });
        return ResponseEntity.ok(allPosts);
    }

    @ApiOperation(value = "Find Post by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Post> findById(@PathVariable long id) throws PostNotExist {
        Post postGet = postService.findById(id);
        postGet.add(linkTo(methodOn(PostController.class).findAll()).withSelfRel());
        return ResponseEntity.ok(postService.findById(id));
    }

    @ApiOperation(value = "Update Post")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable long id, @RequestBody @Valid Post post) throws PostNotExist, PermissionDeniedException {
        return ResponseEntity.ok(postService.updatePost(id, post));
    }
}
