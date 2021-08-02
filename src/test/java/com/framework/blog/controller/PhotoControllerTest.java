package com.framework.blog.controller;

import com.framework.blog.exception.AlbumNotExist;
import com.framework.blog.exception.PermissionDeniedException;
import com.framework.blog.exception.PhotoNotExist;
import com.framework.blog.model.Photo;
import com.framework.blog.service.PhotoService;
import com.framework.blog.util.PhotoCreator;
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

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
class PhotoControllerTest {

    @InjectMocks
    private PhotoController photoController;
    @Mock
    private PhotoService photoServiceMock;

    @BeforeEach
    void setup() throws PhotoNotExist, PermissionDeniedException, AlbumNotExist {
        List<Photo> Photo = Arrays.asList(PhotoCreator.createValidPhoto());
        BDDMockito.when(photoServiceMock.findAll())
                .thenReturn(Photo);

        BDDMockito.when(photoServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(PhotoCreator.createValidPhoto());

        BDDMockito.when(photoServiceMock.uploadPhoto(ArgumentMatchers.anyLong(), ArgumentMatchers.any(MultipartFile.class)))
                .thenReturn(PhotoCreator.createValidPhoto());

        BDDMockito.doNothing().when(photoServiceMock).deletePhoto(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("ListAll returns list of Photo when successful")
    void listAll_ReturnsListOfPhoto_WhenSuccessful(){

        List<Photo> photo = photoController.findAll().getBody();

        Assertions.assertThat(photo).isNotNull()
                .isNotEmpty()
                .hasSize(1);

    }

    @Test
    @DisplayName("findById returns a Photo when successful")
    void findById_ReturnsPhoto_WhenSuccessful() throws PhotoNotExist {

        Photo photo = photoController.findById(1L).getBody();

        Assertions.assertThat(photo).isNotNull();

    }

    @Test
    @DisplayName("save returns a Photo when successful")
    void save_ReturnsPhoto_WhenSuccessful() throws PhotoNotExist, PermissionDeniedException, AlbumNotExist {

        Photo photo = photoController.uploadPhoto(PhotoCreator.createValidPhoto().getId(), ArgumentMatchers.any()).getBody();

        Assertions.assertThat(photo).isNotNull().isEqualTo(PhotoCreator.createValidPhoto());

    }

    @Test
    @DisplayName("delete removes a Photo when successful")
    void delete_RemovesPhoto_WhenSuccessful(){

        Assertions.assertThatCode(() ->photoController.deletePhoto(1))
                .doesNotThrowAnyException();

    }

}