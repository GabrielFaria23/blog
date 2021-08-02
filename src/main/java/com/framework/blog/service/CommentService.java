package com.framework.blog.service;

import com.framework.blog.exception.CommentNotExist;
import com.framework.blog.exception.PermissionDeniedException;
import com.framework.blog.model.Comment;
import com.framework.blog.model.UserBlog;
import com.framework.blog.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserBlogService userBlogService;

    public CommentService(CommentRepository commentRepository, UserBlogService userBlogService) {
        this.commentRepository = commentRepository;
        this.userBlogService = userBlogService;
    }

    public Comment createComment(Comment comment){
        UserBlog userLogged = userBlogService.userLogged();
        comment.setUserBlog(userLogged);
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) throws CommentNotExist, PermissionDeniedException {
        UserBlog userLogged = userBlogService.userLogged();
        Comment comment = checkCommentExist(id);
        if (comment.getUserBlog().getId() == userLogged.getId()){
            commentRepository.deleteById(checkCommentExist(id).getId());
        }else{
            throw new PermissionDeniedException("You don't have permission to delete this comment!");
        }
    }

    public Comment findById(Long id) throws CommentNotExist {
        return checkCommentExist(id);
    }

    public Comment updateComment(Long id, Comment comment) throws CommentNotExist, PermissionDeniedException {
        UserBlog userBlog = userBlogService.userLogged();
        Comment commentToBeUpdated = checkCommentExist(id);
        if (userBlog.getId() == commentToBeUpdated.getUserBlog().getId()){
            commentToBeUpdated.setComment(comment.getComment());

            return commentRepository.save(commentToBeUpdated);
        }else{
            throw new PermissionDeniedException("You don't have permission to update this comment!");
        }
    }

    public List<Comment> findAll(){
        return commentRepository.findAll();
    }

    private Comment checkCommentExist(Long id) throws CommentNotExist {
        return commentRepository.findById(id)
                .orElseThrow(()-> new CommentNotExist("Cannot find comment with id: "+ id +" /n" +
                        "Please insert another id and try again!"));
    }

}
