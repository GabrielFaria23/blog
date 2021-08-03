package com.framework.blog.controller;

import com.framework.blog.exception.UserBlogNotExist;
import com.framework.blog.model.UserBlog;
import com.framework.blog.service.UserBlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(value = "Endpoint of UserBlogs", description = "Endpoint used to make alterations in userBlog Entity", tags = "Endpoint of UserBlog")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/v1/usersBlog")
public class UserBlogController {

    private final UserBlogService userBlogService;

    @ApiOperation(value = "Create UserBlog")
    @PostMapping
    public ResponseEntity<UserBlog> createUserBlog(@RequestBody @Valid UserBlog userBlog) throws UserBlogNotExist {
        UserBlog userBlogSaved = userBlogService.createUserBlog(userBlog);
        userBlogSaved.add(linkTo(methodOn(UserBlogController.class).findById(userBlogSaved.getId())).withSelfRel());
        return ResponseEntity.ok(userBlogSaved);
    }

    @ApiOperation(value = "Delete UserBlog")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserBlog(@PathVariable long id) throws UserBlogNotExist {
        userBlogService.deleteUserBlog(id);
    }

    @ApiOperation(value = "Find all UserBlog")
    @GetMapping
    public ResponseEntity<List<UserBlog>> findAll(){
        List<UserBlog> allUserBlogs = userBlogService.findAll();
        allUserBlogs.forEach(p -> {
            try {
                p.add(linkTo(methodOn(UserBlogController.class).findById(p.getId())).withSelfRel());
            } catch (UserBlogNotExist userBlogNotExist) {
                userBlogNotExist.printStackTrace();
            }
        });
        return ResponseEntity.ok(allUserBlogs);
    }

    @ApiOperation(value = "Find User by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserBlog> findById(@PathVariable long id) throws UserBlogNotExist {
        UserBlog userBlogGet = userBlogService.findById(id);
        userBlogGet.add(linkTo(methodOn(UserBlogController.class).findAll()).withSelfRel());
        return ResponseEntity.ok(userBlogGet);
    }

    @ApiOperation(value = "Update User")
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserBlog> updateUserBlog(@PathVariable long id, @RequestBody @Valid UserBlog userBlog) throws UserBlogNotExist {
        UserBlog userBlogUpdated = userBlogService.updateUserBlog(id, userBlog);
        userBlogUpdated.add(linkTo(methodOn(UserBlogController.class).findById(userBlogUpdated.getId())).withSelfRel());
        return ResponseEntity.ok(userBlogUpdated);
    }
}
