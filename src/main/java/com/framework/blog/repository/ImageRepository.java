package com.framework.blog.repository;

import com.framework.blog.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT i FROM Image i JOIN i.post p ON i.post.id = p.id WHERE p.id =:idPost")
    List<Image> findByPost(@Param("idPost") Long idPost);
}
