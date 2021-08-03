package com.framework.blog.controller;

import com.framework.blog.exception.AlbumNotExist;
import com.framework.blog.exception.PermissionDeniedException;
import com.framework.blog.model.Album;
import com.framework.blog.service.AlbumService;
import com.framework.blog.util.AlbumCreator;
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

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
class AlbumControllerTest {

    @InjectMocks
    private AlbumController albumController;
    @Mock
    private AlbumService albumServiceMock;

    @BeforeEach
    void setup() throws AlbumNotExist, PermissionDeniedException {
        List<Album> album = Arrays.asList(AlbumCreator.createValidAlbum());
        BDDMockito.when(albumServiceMock.findAll())
                .thenReturn(album);

        BDDMockito.when(albumServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(AlbumCreator.createValidAlbum());

        BDDMockito.when(albumServiceMock.createAlbum())
                .thenReturn(AlbumCreator.createValidAlbum());

        BDDMockito.doNothing().when(albumServiceMock).deleteAlbum(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("ListAll returns list of album when successful")
    void listAll_ReturnsListOfAlbum_WhenSuccessful(){

        List<Album> albums = albumController.findAll().getBody();

        Assertions.assertThat(albums).isNotNull()
                .isNotEmpty()
                .hasSize(1);

    }

    @Test
    @DisplayName("findById returns a album when successful")
    void findById_ReturnsAlbum_WhenSuccessful() throws AlbumNotExist {

        Album album = albumController.findById(1L).getBody();

        Assertions.assertThat(album).isNotNull();

    }

    @Test
    @DisplayName("save returns a album when successful")
    void save_ReturnsAlbum_WhenSuccessful() throws AlbumNotExist {

        Album album = albumController.createAlbum().getBody();

        Assertions.assertThat(album).isNotNull().isEqualTo(AlbumCreator.createValidAlbum());

    }

    @Test
    @DisplayName("delete removes a album when successful")
    void delete_RemovesAlbum_WhenSuccessful(){

        Assertions.assertThatCode(() ->albumController.deleteAlbum(1))
                .doesNotThrowAnyException();

    }

}