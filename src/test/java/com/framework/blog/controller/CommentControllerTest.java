package com.framework.blog.controller;

import com.framework.blog.exception.PermissionDeniedException;
import com.framework.blog.exception.CommentNotExist;
import com.framework.blog.exception.UserBlogNotExist;
import com.framework.blog.model.Comment;
import com.framework.blog.model.UserBlog;
import com.framework.blog.service.CommentService;
import com.framework.blog.util.CommentCreator;
import com.framework.blog.util.UserBlogCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
class CommentControllerTest {

    @InjectMocks
    private CommentController commentController;
    @Mock
    private CommentService commentServiceMock;

    @BeforeEach
    void setup() throws CommentNotExist, PermissionDeniedException {
        List<Comment> comment = Arrays.asList(CommentCreator.createValidComment());
        BDDMockito.when(commentServiceMock.findAll())
                .thenReturn(comment);

        BDDMockito.when(commentServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(CommentCreator.createValidComment());

        BDDMockito.when(commentServiceMock.createComment(ArgumentMatchers.any(Comment.class)))
                .thenReturn(CommentCreator.createValidComment());

        BDDMockito.doNothing().when(commentServiceMock).deleteComment(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("ListAll returns list of comment when successful")
    void listAll_ReturnsListOfComment_WhenSuccessful(){
        String expectedComment = CommentCreator.createValidComment().getComment();

        List<Comment> comments = commentController.findAll().getBody();

        Assertions.assertThat(comments).isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(comments.get(0).getComment()).isEqualTo(expectedComment);
    }

    @Test
    @DisplayName("findById returns a comment when successful")
    void findById_ReturnsComment_WhenSuccessful() throws CommentNotExist {
        Comment expectedComment = CommentCreator.createValidComment();

        Comment comment = commentController.findById(1L).getBody();

        Assertions.assertThat(comment).isNotNull();

        Assertions.assertThat(comment.getId()).isNotNull()
                .isEqualTo(expectedComment.getId());

        Assertions.assertThat(comment.getComment()).isEqualTo(expectedComment.getComment());
    }

    @Test
    @DisplayName("save returns a comment when successful")
    void save_ReturnsComment_WhenSuccessful() throws CommentNotExist {

        Comment comment = commentController.createComment(CommentCreator.createCommentToBeSaved()).getBody();

        Assertions.assertThat(comment).isNotNull().isEqualTo(CommentCreator.createValidComment());

    }

    @Test
    @DisplayName("replace update a comment when successful")
    void replace_UpdatesComment_WhenSuccessful() throws CommentNotExist, PermissionDeniedException {

        ResponseEntity<Comment> entity = commentController.updateComment(CommentCreator.createValidUpdateComment().getId(), CommentCreator.createValidUpdateComment());

        Assertions.assertThat(entity.getBody()).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("delete removes a comment when successful")
    void delete_RemovesComment_WhenSuccessful(){

        Assertions.assertThatCode(() ->commentController.deleteComment(1))
                .doesNotThrowAnyException();

    }

}