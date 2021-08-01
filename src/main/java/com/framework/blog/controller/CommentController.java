package com.framework.blog.controller;

import com.framework.blog.exception.CommentNotExist;
import com.framework.blog.model.Comment;
import com.framework.blog.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Endpoint of Comment", description = "Endpoint used to make alterations in comment Entity", tags = "Endpoint of Comments")
@RestController
@RequestMapping(value = "/v1/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "Create Comment")
    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody @Valid Comment comment){
        return ResponseEntity.ok(commentService.createComment(comment));
    }

    @ApiOperation(value = "Delete Comment")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable long id) throws CommentNotExist {
        commentService.deleteComment(id);
    }

    @ApiOperation(value = "Find all Comments")
    @GetMapping
    public ResponseEntity<Page<Comment>> findAll(Pageable pageable){
        return ResponseEntity.ok(commentService.findAllComments(pageable));
    }

    @ApiOperation(value = "Find Comment by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Comment> findById(@PathVariable long id) throws CommentNotExist {
        return ResponseEntity.ok(commentService.findById(id));
    }

    @ApiOperation(value = "Update Comment")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable long id, @RequestBody @Valid Comment comment) throws CommentNotExist {
        return ResponseEntity.ok(commentService.updateComment(id, comment));
    }
}
