package com.framework.blog.controller;

import com.framework.blog.exception.UserBlogNotExist;
import com.framework.blog.model.UserBlog;
import com.framework.blog.service.UserBlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Endpoint of UserBlogs", description = "Endpoint used to make alterations in userBlog Entity", tags = "Endpoint of UserBlog")
@RestController
@RequestMapping(value = "/v1/usersBlog")
public class UserBlogController {

    private final UserBlogService userBlogService;

    public UserBlogController(UserBlogService userBlogService) {
        this.userBlogService = userBlogService;
    }

    @ApiOperation(value = "Create UserBlog")
    @PostMapping
    public ResponseEntity<UserBlog> createUserBlog(@RequestBody @Valid UserBlog userBlog){
        return ResponseEntity.ok(userBlogService.createUserBlog(userBlog));
    }

    @ApiOperation(value = "Delete UserBlog")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserBlog(@PathVariable long id) throws UserBlogNotExist {
        userBlogService.deleteUserBlog(id);
    }

    @ApiOperation(value = "Find all UserBlog")
    @GetMapping
    public ResponseEntity<Page<UserBlog>> findAll(Pageable pageable){
        return ResponseEntity.ok(userBlogService.findAllUsers(pageable));
    }

    @ApiOperation(value = "Find User by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserBlog> findById(@PathVariable long id) throws UserBlogNotExist {
        return ResponseEntity.ok(userBlogService.findById(id));
    }

    @ApiOperation(value = "Update User")
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserBlog> updateUserBlog(@PathVariable long id, @RequestBody @Valid UserBlog userBlog) throws UserBlogNotExist {
        return ResponseEntity.ok(userBlogService.updateUserBlog(id, userBlog));
    }
}
