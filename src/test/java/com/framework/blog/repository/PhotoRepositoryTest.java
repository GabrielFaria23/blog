package com.framework.blog.repository;

import com.framework.blog.model.Photo;
import com.framework.blog.util.PhotoCreator;
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
@DisplayName("Test of Photo Repository")
class PhotoRepositoryTest {

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Test
    @DisplayName("Save persist Photo when successful")
    void save_PersistPhoto_WhenSuccessful() {

        Photo photoToBeSaved = PhotoCreator.createPhotoToBeSaved();
        photoToBeSaved.setAlbum(albumRepository.findById(photoToBeSaved.getAlbum().getId()).get());
        Photo photoSaved = photoRepository.save(photoToBeSaved);

        Assertions.assertThat(photoSaved).isNotNull();
        Assertions.assertThat(photoSaved.getId()).isEqualTo(photoToBeSaved.getId());

    }

    @Test
    @DisplayName("Save delete Photo when successful")
    void delete_RemovePhoto_WhenSuccessful() {

        Photo photoToBeSaved = PhotoCreator.createPhotoToBeSaved();
        photoToBeSaved.setAlbum(albumRepository.findById(photoToBeSaved.getAlbum().getId()).get());
        Photo photoSaved = photoRepository.save(photoToBeSaved);

        photoRepository.delete(photoSaved);

        Optional<Photo> photo = photoRepository.findById(photoSaved.getId());

        Assertions.assertThat(photo).isEmpty();
    }

    @Test
    @DisplayName("Find By Id returns instance of photo when Successful")
    void findById_ReturnInstanceOfPhoto_WhenSuccessfull() {
        Photo photoToBeSaved = PhotoCreator.createPhotoToBeSaved();
        photoToBeSaved.setAlbum(albumRepository.findById(photoToBeSaved.getAlbum().getId()).get());
        Photo photoSaved = photoRepository.save(photoToBeSaved);

        long id = photoSaved.getId();

        Photo photos = photoRepository.findById(id).get();

        Assertions.assertThat(photos).isNotNull();
        Assertions.assertThat(photos.getId()).isEqualTo(photoSaved.getId());
        Assertions.assertThat(photos.getId()).isEqualTo(photoSaved.getId());

    }

    @Test
    @DisplayName("Find Photos By Album returns instance of photo when Successful")
    void findByAlbum_ReturnListOfPhoto_WhenSuccessfull() {
        Photo photoToBeSaved = PhotoCreator.createPhotoToBeSaved();
        photoToBeSaved.setAlbum(albumRepository.findById(photoToBeSaved.getAlbum().getId()).get());
        Photo photoSaved = photoRepository.save(photoToBeSaved);

        List<Photo> photos = photoRepository.findByAlbum(photoSaved.getAlbum().getId());

        Assertions.assertThat(photos).isNotEmpty();
    }

    @Test
    @DisplayName("Find all returns list of photo when Successful")
    void findAll_ReturnInstanceOfAlbum_WhenSuccessfull(){

        List<Photo> photos = photoRepository.findAll();

        Assertions.assertThat(photos).isNotEmpty();

    }


}