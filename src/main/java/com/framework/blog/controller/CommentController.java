package com.framework.blog.controller;

import com.framework.blog.exception.CommentNotExist;
import com.framework.blog.exception.PermissionDeniedException;
import com.framework.blog.model.Comment;
import com.framework.blog.repository.UserBlogRepository;
import com.framework.blog.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(value = "Endpoint of Comment", description = "Endpoint used to make alterations in comment Entity", tags = "Endpoint of Comments")
@RestController
@RequestMapping(value = "/v1/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService, UserBlogRepository userBlogRepository) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "Create Comment")
    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody @Valid Comment comment) throws CommentNotExist {
        Comment commentSaved = commentService.createComment(comment);
        commentSaved.add(linkTo(methodOn(CommentController.class).findById(commentSaved.getId())).withSelfRel());
        return ResponseEntity.ok(commentSaved);
    }

    @ApiOperation(value = "Delete Comment")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable long id) throws CommentNotExist, PermissionDeniedException {
        commentService.deleteComment(id);
    }


    @ApiOperation(value = "Find all Comments")
    @GetMapping
    public ResponseEntity<List<Comment>> findAll(){
        List<Comment> allComments = commentService.findAll();
        allComments.forEach(p -> {
            try {
                p.add(linkTo(methodOn(CommentController.class).findById(p.getId())).withSelfRel());
            } catch (CommentNotExist commentNotExist) {
                commentNotExist.printStackTrace();
            }
        });
        return ResponseEntity.ok(allComments);
    }

    @ApiOperation(value = "Find Comment by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Comment> findById(@PathVariable long id) throws CommentNotExist {
        Comment commentGet = commentService.findById(id);
        commentGet.add(linkTo(methodOn(CommentController.class).findAll()).withSelfRel());
        return ResponseEntity.ok(commentGet);
    }

    @ApiOperation(value = "Update Comment")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable long id, @RequestBody @Valid Comment comment) throws CommentNotExist, PermissionDeniedException {
        Comment commentUpdated = commentService.updateComment(id, comment);
        commentUpdated.add(linkTo(methodOn(CommentController.class).findById(commentUpdated.getId())).withSelfRel());
        return ResponseEntity.ok(commentUpdated);
    }

}
