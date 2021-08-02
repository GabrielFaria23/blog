package com.framework.blog.controller;

import com.framework.blog.exception.ImageNotExist;
import com.framework.blog.exception.PermissionDeniedException;
import com.framework.blog.exception.PostNotExist;
import com.framework.blog.model.Image;
import com.framework.blog.service.ImageService;
import com.framework.blog.util.ImageCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
class ImageControllerTest {

    @InjectMocks
    private ImageController imageController;
    @Mock
    private ImageService imageServiceMock;

    @BeforeEach
    void setup() throws ImageNotExist, PermissionDeniedException, PostNotExist {
        List<Image> Image = Arrays.asList(ImageCreator.createValidImage());
        BDDMockito.when(imageServiceMock.findAll())
                .thenReturn(Image);

        BDDMockito.when(imageServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(ImageCreator.createValidImage());

        BDDMockito.when(imageServiceMock.uploadImage(ArgumentMatchers.anyLong(), ArgumentMatchers.any(MultipartFile.class)))
                .thenReturn(ImageCreator.createValidImage());

        BDDMockito.doNothing().when(imageServiceMock).deleteImage(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("ListAll returns list of Image when successful")
    void listAll_ReturnsListOfImage_WhenSuccessful(){

        List<Image> image = imageController.findAll().getBody();

        Assertions.assertThat(image).isNotNull()
                .isNotEmpty()
                .hasSize(1);

    }

    @Test
    @DisplayName("findById returns a Image when successful")
    void findById_ReturnsImage_WhenSuccessful() throws ImageNotExist {

        Image image = imageController.findById(1L).getBody();

        Assertions.assertThat(image).isNotNull();

    }

    @Test
    @DisplayName("save returns a Image when successful")
    void save_ReturnsImage_WhenSuccessful() throws ImageNotExist, PostNotExist, PermissionDeniedException, IOException {

        Image image = imageController.uploadImage(ImageCreator.createValidImage().getId(), ArgumentMatchers.any()).getBody();

        Assertions.assertThat(image).isNotNull().isEqualTo(ImageCreator.createValidImage());

    }

    @Test
    @DisplayName("delete removes a Image when successful")
    void delete_RemovesImage_WhenSuccessful(){

        Assertions.assertThatCode(() ->imageController.deleteImage(1))
                .doesNotThrowAnyException();

    }

}