package com.framework.blog.repository;

import com.framework.blog.exception.UserBlogNotExist;
import com.framework.blog.model.Photo;
import com.framework.blog.model.Post;
import com.framework.blog.model.UserBlog;
import com.framework.blog.util.PostCreator;
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
@DisplayName("Test of Post Repository")
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserBlogRepository userBlogRepository;

    @Test
    @DisplayName("Save persist Post when successful")
    void save_PersistPost_WhenSuccessful() throws UserBlogNotExist {

        Post postToBeSaved = PostCreator.createPostToBeSaved();
        postToBeSaved.setUserBlog(userBlogRepository.findById(postToBeSaved.getUserBlog().getId()).get());
        Post postSaved = postRepository.save(postToBeSaved);

        Assertions.assertThat(postSaved).isNotNull();
        Assertions.assertThat(postSaved.getId()).isEqualTo(postToBeSaved.getId());

    }

    @Test
    @DisplayName("Save Update Post when successful")
    void update_UpdatePost_WhenSuccessful() throws UserBlogNotExist {

        Post postToBeSaved = PostCreator.createPostToBeSaved();
        postToBeSaved.setUserBlog(userBlogRepository.findById(postToBeSaved.getUserBlog().getId()).get());
        Post postSaved = postRepository.save(postToBeSaved);

        postSaved.setDescription("description A");

        Post postUpdated = postRepository.save(postSaved);

        Assertions.assertThat(postUpdated).isNotNull();
        Assertions.assertThat(postUpdated.getId()).isEqualTo(postSaved.getId());
    }

    @Test
    @DisplayName("Save delete Post when successful")
    void delete_RemovePost_WhenSuccessful() throws UserBlogNotExist {

        Post postToBeSaved = PostCreator.createPostToBeSaved();
        postToBeSaved.setUserBlog(userBlogRepository.findById(postToBeSaved.getUserBlog().getId()).get());
        Post postSaved = postRepository.save(postToBeSaved);

        postRepository.delete(postSaved);

        Optional<Post> post = postRepository.findById(postSaved.getId());

        Assertions.assertThat(post).isEmpty();
    }

    @Test
    @DisplayName("Find By Id returns instance of post when Successful")
    void findById_ReturnInstanceOfPost_WhenSuccessfull() throws UserBlogNotExist {
        Post postToBeSaved = PostCreator.createPostToBeSaved();
        postToBeSaved.setUserBlog(userBlogRepository.findById(postToBeSaved.getUserBlog().getId()).get());
        Post postSaved = postRepository.save(postToBeSaved);

        long id = postSaved.getId();

        Post posts = postRepository.findById(id).get();

        Assertions.assertThat(posts).isNotNull();
        Assertions.assertThat(posts.getId()).isEqualTo(postSaved.getId());
        Assertions.assertThat(posts.getId()).isEqualTo(postSaved.getId());

    }

    @Test
    @DisplayName("Find all returns list of post when Successful")
    void findAll_ReturnInstanceOfUserBlog_WhenSuccessfull(){

        List<Post> posts = postRepository.findAll();

        Assertions.assertThat(posts).isNotEmpty();

    }

}