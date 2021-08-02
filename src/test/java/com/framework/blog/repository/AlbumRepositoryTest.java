package com.framework.blog.repository;

import com.framework.blog.model.Album;
import com.framework.blog.model.Photo;
import com.framework.blog.util.AlbumCreator;
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
@DisplayName("Test of Album Repository")
class AlbumRepositoryTest {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    UserBlogRepository userBlogRepository;

    @Test
    @DisplayName("Save persist Album when successful")
    void save_PersistAlbum_WhenSuccessful() {

        Album albumToBeSaved = AlbumCreator.createAlbumToBeSaved();
        albumToBeSaved.setUserBlog(userBlogRepository.findById(albumToBeSaved.getUserBlog().getId()).get());
        Album albumSaved = albumRepository.save(albumToBeSaved);

        Assertions.assertThat(albumSaved).isNotNull();
        Assertions.assertThat(albumSaved.getId()).isEqualTo(albumToBeSaved.getId());

    }

    @Test
    @DisplayName("Save delete Album when successful")
    void delete_RemoveAlbum_WhenSuccessful() {

        Album albumToBeSaved = AlbumCreator.createAlbumToBeSaved();
        albumToBeSaved.setUserBlog(userBlogRepository.findById(albumToBeSaved.getUserBlog().getId()).get());
        Album albumSaved = albumRepository.save(albumToBeSaved);

        albumRepository.delete(albumSaved);

        Optional<Album> album = albumRepository.findById(albumSaved.getId());

        Assertions.assertThat(album).isEmpty();
    }

    @Test
    @DisplayName("Find By Id returns instance of album when Successful")
    void findById_ReturnInstanceOfAlbum_WhenSuccessfull() {
        Album albumToBeSaved = AlbumCreator.createAlbumToBeSaved();
        albumToBeSaved.setUserBlog(userBlogRepository.findById(albumToBeSaved.getUserBlog().getId()).get());
        Album albumSaved = albumRepository.save(albumToBeSaved);

        long id = albumSaved.getId();

        Album albums = albumRepository.findById(id).get();

        Assertions.assertThat(albums).isNotNull();
        Assertions.assertThat(albums.getId()).isEqualTo(albumSaved.getId());
        Assertions.assertThat(albums.getId()).isEqualTo(albumSaved.getId());

    }

    @Test
    @DisplayName("Find all returns list of album when Successful")
    void findAll_ReturnInstanceOfUserBlog_WhenSuccessfull(){

        List<Album> albums = albumRepository.findAll();

        Assertions.assertThat(albums).isNotEmpty();

    }

}