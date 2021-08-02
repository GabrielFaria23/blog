package com.framework.blog.repository;

import com.framework.blog.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Query("SELECT p FROM Photo p JOIN p.album a ON p.album.id = a.id WHERE a.id =:idAlbum")
    List<Photo> findByAlbum(@Param("idAlbum") Long idAlbum);
}
