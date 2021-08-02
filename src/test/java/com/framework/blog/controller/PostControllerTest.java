package com.framework.blog.controller;

import com.framework.blog.exception.PermissionDeniedException;
import com.framework.blog.exception.PostNotExist;
import com.framework.blog.model.Post;
import com.framework.blog.service.PostService;
import com.framework.blog.service.UserBlogService;
import com.framework.blog.util.PostCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
class PostControllerTest {

    @InjectMocks
    private PostController postController;
    @Mock
    private PostService postServiceMock;

    @BeforeEach
    void setup() throws PostNotExist, PermissionDeniedException {
        List<Post> post = Arrays.asList(PostCreator.createValidPost());
        BDDMockito.when(postServiceMock.findAll())
                .thenReturn(post);

        BDDMockito.when(postServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(PostCreator.createValidPost());

        BDDMockito.when(postServiceMock.createPost(ArgumentMatchers.any(Post.class)))
                .thenReturn(PostCreator.createValidPost());

        BDDMockito.doNothing().when(postServiceMock).deletePost(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("ListAll returns list of post when successful")
    void listAll_ReturnsListOfPost_WhenSuccessful(){
        String expectedDescription = PostCreator.createValidPost().getDescription();

        List<Post> posts = postController.findAll().getBody();

        Assertions.assertThat(posts).isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(posts.get(0).getDescription()).isEqualTo(expectedDescription);
    }

    @Test
    @DisplayName("findById returns a post when successful")
    void findById_ReturnsPost_WhenSuccessful() throws PostNotExist {
        Post expectedPost = PostCreator.createValidPost();

        Post post = postController.findById(1L).getBody();

        Assertions.assertThat(post).isNotNull();

        Assertions.assertThat(post.getId()).isNotNull()
                .isEqualTo(expectedPost.getId());

        Assertions.assertThat(post.getDescription()).isEqualTo(expectedPost.getDescription());
    }

    @Test
    @DisplayName("save returns a post when successful")
    void save_ReturnsPost_WhenSuccessful() throws PostNotExist {

        Post post = postController.createPost(PostCreator.createPostToBeSaved()).getBody();

        Assertions.assertThat(post).isNotNull().isEqualTo(PostCreator.createValidPost());

    }

    @Test
    @DisplayName("replace update a post when successful")
    void replace_UpdatesPost_WhenSuccessful() throws PostNotExist, PermissionDeniedException {

        Post postToBeUpdated = postServiceMock.findById(PostCreator.createValidUpdatePost().getId());
        ResponseEntity<Post> entity = postController.updatePost(PostCreator.createValidUpdatePost().getId(), postToBeUpdated);

        Assertions.assertThat(entity.getBody()).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("delete removes a post when successful")
    void delete_RemovesPost_WhenSuccessful(){

        Assertions.assertThatCode(() ->postController.deletePost(1))
                .doesNotThrowAnyException();

    }

}