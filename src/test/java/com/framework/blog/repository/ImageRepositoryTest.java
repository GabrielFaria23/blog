package com.framework.blog.repository;

import com.framework.blog.model.Image;
import com.framework.blog.util.ImageCreator;
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
@DisplayName("Test of Image Repository")
class ImageRepositoryTest {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("Save persist Image when successful")
    void save_PersistImage_WhenSuccessful() {

        Image imageToBeSaved = ImageCreator.createImageToBeSaved();
        imageToBeSaved.setPost(postRepository.findById(imageToBeSaved.getPost().getId()).get());
        Image imageSaved = imageRepository.save(imageToBeSaved);

        Assertions.assertThat(imageSaved).isNotNull();
        Assertions.assertThat(imageSaved.getId()).isEqualTo(imageToBeSaved.getId());

    }

    @Test
    @DisplayName("Save delete Image when successful")
    void delete_RemoveImage_WhenSuccessful() {

        Image imageToBeSaved = ImageCreator.createImageToBeSaved();
        imageToBeSaved.setPost(postRepository.findById(imageToBeSaved.getPost().getId()).get());
        Image imageSaved = imageRepository.save(imageToBeSaved);

        imageRepository.delete(imageSaved);

        Optional<Image> image = imageRepository.findById(imageSaved.getId());

        Assertions.assertThat(image).isEmpty();
    }

    @Test
    @DisplayName("Find By Id returns instance of image when Successful")
    void findById_ReturnInstanceOfImage_WhenSuccessfull() {
        Image imageToBeSaved = ImageCreator.createImageToBeSaved();
        imageToBeSaved.setPost(postRepository.findById(imageToBeSaved.getPost().getId()).get());
        Image imageSaved = imageRepository.save(imageToBeSaved);

        long id = imageSaved.getId();

        Image images = imageRepository.findById(id).get();

        Assertions.assertThat(images).isNotNull();
        Assertions.assertThat(images.getId()).isEqualTo(imageSaved.getId());
        Assertions.assertThat(images.getId()).isEqualTo(imageSaved.getId());

    }

    @Test
    @DisplayName("Find Images By Post returns instance of image when Successful")
    void findByPost_ReturnListOfImage_WhenSuccessfull() {
        Image imageToBeSaved = ImageCreator.createImageToBeSaved();
        imageToBeSaved.setPost(postRepository.findById(imageToBeSaved.getPost().getId()).get());
        Image imageSaved = imageRepository.save(imageToBeSaved);

        List<Image> images = imageRepository.findByPost(imageSaved.getPost().getId());

        Assertions.assertThat(images).isNotEmpty();
    }

    @Test
    @DisplayName("Find all returns list of image when Successful")
    void findAll_ReturnInstanceOfPost_WhenSuccessfull(){

        List<Image> images = imageRepository.findAll();

        Assertions.assertThat(images).isNotEmpty();

    }


}