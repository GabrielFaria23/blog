package com.framework.blog.repository;

import com.framework.blog.model.UserBlog;
import com.framework.blog.repository.util.UserBlogCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Test of User Blog Repository")
class UserBlogRepositoryTest {

    @Autowired
    UserBlogRepository userBlogRepository;

    @Test
    @DisplayName("Save persist UserBlog when successful")
    void save_PersistUserBlog_WhenSuccessful(){

        UserBlog userBlogToBeSaved = UserBlogCreator.createUserBlogToBeSaved();

        UserBlog userBlogSaved = userBlogRepository.save(userBlogToBeSaved);

        Assertions.assertThat(userBlogSaved).isNotNull();
        Assertions.assertThat(userBlogSaved.getId()).isEqualTo(userBlogToBeSaved.getId());
        Assertions.assertThat(userBlogSaved.getName()).isEqualTo(userBlogToBeSaved.getName());
    }

    @Test
    @DisplayName("Save Update UserBlog when successful")
    void update_UpdateUserBlog_WhenSuccessful(){

        UserBlog userBlogToBeSaved = UserBlogCreator.createUserBlogToBeSaved();

        UserBlog userBlogSaved = userBlogRepository.save(userBlogToBeSaved);

        userBlogSaved.setName("Gabriel Nunes");

        UserBlog userBlogUpdated = userBlogRepository.save(userBlogSaved);

        Assertions.assertThat(userBlogUpdated).isNotNull();
        Assertions.assertThat(userBlogUpdated.getId()).isEqualTo(userBlogSaved.getId());
        Assertions.assertThat(userBlogUpdated.getName()).isEqualTo(userBlogSaved.getName());
    }

    @Test
    @DisplayName("Save delete UserBlog when successful")
    void save_RemoveUserBlog_WhenSuccessful(){

        UserBlog userBlogToBeSaved = UserBlogCreator.createUserBlogToBeSaved();

        UserBlog userBlogSaved = userBlogRepository.save(userBlogToBeSaved);

        Assertions.assertThat(userBlogSaved).isNotNull();
        Assertions.assertThat(userBlogSaved.getId()).isEqualTo(userBlogToBeSaved.getId());
        Assertions.assertThat(userBlogSaved.getName()).isEqualTo(userBlogToBeSaved.getName());
    }

    @Test
    @DisplayName("Find By Username returns instance of userBlog when Successful")
    void findByUsername_ReturnInstanceOfUserBlog_WhenSuccessfull(){
        UserBlog userBlogToBeSaved = UserBlogCreator.createUserBlogToBeSaved();

        UserBlog userBlogSaved = userBlogRepository.save(userBlogToBeSaved);

        String username = userBlogSaved.getUsername();

        UserBlog userBlogs = userBlogRepository.findByUsername(username).get();

        Assertions.assertThat(userBlogs).isNotNull();
        Assertions.assertThat(userBlogs.getId()).isEqualTo(userBlogSaved.getId());
        Assertions.assertThat(userBlogs.getUsername()).isEqualTo(userBlogSaved.getUsername());

    }
}