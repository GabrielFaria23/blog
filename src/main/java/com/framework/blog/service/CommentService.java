package com.framework.blog.service;

import com.framework.blog.exception.CommentNotExist;
import com.framework.blog.exception.PermissionDeniedException;
import com.framework.blog.model.Comment;
import com.framework.blog.model.UserBlog;
import com.framework.blog.repository.CommentRepository;
import com.framework.blog.repository.UserBlogRepository;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserBlogRepository userBlogRepository;

    public CommentService(CommentRepository commentRepository, UserBlogRepository userBlogRepository) {
        this.commentRepository = commentRepository;
        this.userBlogRepository = userBlogRepository;
    }

    public Comment createComment(Comment comment){
        UserBlog userLogged = userLogged();
        comment.setUserBlog(userLogged);
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) throws CommentNotExist, PermissionDeniedException {
        UserBlog userLogged = userLogged();
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

    private UserBlog userLogged() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userBlogRepository.findByUsername(username).get();
    }
}
