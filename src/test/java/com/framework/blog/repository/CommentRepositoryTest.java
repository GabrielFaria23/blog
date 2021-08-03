package com.framework.blog.repository;

import com.framework.blog.model.Comment;
import com.framework.blog.util.CommentCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Test of Comment Repository")
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserBlogRepository userBlogRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("Save persist Comment when successful")
    void save_PersistComment_WhenSuccessful() {

        Comment commentToBeSaved = CommentCreator.createCommentToBeSaved();
        commentToBeSaved.setUserBlog(userBlogRepository.findById(commentToBeSaved.getUserBlog().getId()).get());
        commentToBeSaved.setPost(postRepository.findById(commentToBeSaved.getPost().getId()).get());
        Comment commentSaved = commentRepository.save(commentToBeSaved);

        Assertions.assertThat(commentSaved).isNotNull();
        Assertions.assertThat(commentSaved.getId()).isEqualTo(commentToBeSaved.getId());

    }

    @Test
    @DisplayName("Save Update Comment when successful")
    void update_UpdateComment_WhenSuccessful() {

        Comment commentToBeSaved = CommentCreator.createCommentToBeSaved();
        commentToBeSaved.setUserBlog(userBlogRepository.findById(commentToBeSaved.getUserBlog().getId()).get());
        commentToBeSaved.setPost(postRepository.findById(commentToBeSaved.getPost().getId()).get());
        Comment commentSaved = commentRepository.save(commentToBeSaved);

        commentSaved.setComment("comment A");

        Comment commentUpdated = commentRepository.save(commentSaved);

        Assertions.assertThat(commentUpdated).isNotNull();
        Assertions.assertThat(commentUpdated.getId()).isEqualTo(commentSaved.getId());
    }

    @Test
    @DisplayName("Save delete Comment when successful")
    void delete_RemoveComment_WhenSuccessful() {

        Comment commentToBeSaved = CommentCreator.createCommentToBeSaved();
        commentToBeSaved.setUserBlog(userBlogRepository.findById(commentToBeSaved.getUserBlog().getId()).get());
        commentToBeSaved.setPost(postRepository.findById(commentToBeSaved.getPost().getId()).get());
        Comment commentSaved = commentRepository.save(commentToBeSaved);

        commentRepository.delete(commentSaved);

        Optional<Comment> comment = commentRepository.findById(commentSaved.getId());

        Assertions.assertThat(comment).isEmpty();
    }

    @Test
    @DisplayName("Find By Id returns instance of comment when Successful")
    void findById_ReturnInstanceOfComment_WhenSuccessfull() {
        Comment commentToBeSaved = CommentCreator.createCommentToBeSaved();
        commentToBeSaved.setUserBlog(userBlogRepository.findById(commentToBeSaved.getUserBlog().getId()).get());
        commentToBeSaved.setPost(postRepository.findById(commentToBeSaved.getPost().getId()).get());
        Comment commentSaved = commentRepository.save(commentToBeSaved);

        long id = commentSaved.getId();

        Comment comments = commentRepository.findById(id).get();

        Assertions.assertThat(comments).isNotNull();
        Assertions.assertThat(comments.getId()).isEqualTo(commentSaved.getId());
        Assertions.assertThat(comments.getId()).isEqualTo(commentSaved.getId());

    }

    @Test
    @DisplayName("Find all returns list of comment when Successful")
    void findAll_ReturnInstanceOfUserBlog_WhenSuccessfull(){

        List<Comment> comments = commentRepository.findAll();

        Assertions.assertThat(comments).isNotEmpty();

    }

}