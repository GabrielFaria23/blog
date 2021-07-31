package com.framework.blog.service;

import com.framework.blog.exception.CommentNotExist;
import com.framework.blog.model.Comment;
import com.framework.blog.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment createComment(Comment comment){
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) throws CommentNotExist {
        commentRepository.deleteById(checkCommentExist(id).getId());
    }

    public Comment findById(Long id) throws CommentNotExist {
        return checkCommentExist(id);
    }

    public Comment updateComment(Long id, Comment comment) throws CommentNotExist {
        Comment commentToBeUpdated = checkCommentExist(id);

        commentToBeUpdated.setComment(comment.getComment());

        return commentRepository.save(commentToBeUpdated);
    }

    public Page<Comment> findAllComments(Pageable pageable){
        return commentRepository.findAll(pageable);
    }

    private Comment checkCommentExist(Long id) throws CommentNotExist {
        return commentRepository.findById(id)
                .orElseThrow(()-> new CommentNotExist("Cannot find comment with id: "+ id +" /n" +
                        "Please insert another id and try again!"));
    }
}
