package com.framework.blog.controller;

import com.framework.blog.exception.UserBlogNotExist;
import com.framework.blog.model.UserBlog;
import com.framework.blog.service.UserBlogService;
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
class UserBlogControllerTest {

    @InjectMocks
    private UserBlogController userBlogController;
    @Mock
    private UserBlogService userBlogServiceMock;

    @BeforeEach
    void setup() throws UserBlogNotExist {
        List<UserBlog> userBlog = Arrays.asList(UserBlogCreator.createValidUserBlog());
        BDDMockito.when(userBlogServiceMock.findAll())
                .thenReturn(userBlog);

        BDDMockito.when(userBlogServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(UserBlogCreator.createValidUserBlog());

        BDDMockito.when(userBlogServiceMock.createUserBlog(ArgumentMatchers.any(UserBlog.class)))
                .thenReturn(UserBlogCreator.createValidUserBlog());

        BDDMockito.doNothing().when(userBlogServiceMock).deleteUserBlog(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("ListAll returns list of userBlog when successful")
    void listAll_ReturnsListOfUserBlog_WhenSuccessful(){
        String expectedName = UserBlogCreator.createValidUserBlog().getName();

        List<UserBlog> userBlogs = userBlogController.findAll().getBody();

        Assertions.assertThat(userBlogs).isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(userBlogs.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns a userBlog when successful")
    void findById_ReturnsUserBlog_WhenSuccessful() throws UserBlogNotExist {
        UserBlog expectedUserBlog = UserBlogCreator.createValidUserBlog();

        UserBlog userBlog = userBlogController.findById(1L).getBody();

        Assertions.assertThat(userBlog).isNotNull();

        Assertions.assertThat(userBlog.getId()).isNotNull()
                .isEqualTo(expectedUserBlog.getId());

        Assertions.assertThat(userBlog.getName()).isEqualTo(expectedUserBlog.getName());
    }

    @Test
    @DisplayName("save returns a userBlog when successful")
    void save_ReturnsUserBlog_WhenSuccessful() throws UserBlogNotExist {

        UserBlog userBlog = userBlogController.createUserBlog(UserBlogCreator.createUserBlogToBeSaved()).getBody();

        Assertions.assertThat(userBlog).isNotNull().isEqualTo(UserBlogCreator.createValidUserBlog());

    }

    @Test
    @DisplayName("replace update a userBlog when successful")
    void replace_UpdatesUserBlog_WhenSuccessful() throws UserBlogNotExist {

        ResponseEntity<UserBlog> entity = userBlogController.updateUserBlog(UserBlogCreator.createValidUpdateUserBlog().getId(), UserBlogCreator.createValidUpdateUserBlog());

        Assertions.assertThat(entity.getBody()).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("delete removes a userBlog when successful")
    void delete_RemovesUserBlog_WhenSuccessful(){

        Assertions.assertThatCode(() ->userBlogController.deleteUserBlog(1))
                .doesNotThrowAnyException();

    }

}