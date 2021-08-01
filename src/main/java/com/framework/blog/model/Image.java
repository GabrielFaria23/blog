package com.framework.blog.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "image_path")
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    public Image(String imagePath, Post post, Album album) {
        this.imagePath = imagePath;
        this.post = post;
    }

    @ManyToOne
    @JoinColumn(name = "album_id", referencedColumnName = "id")
    private Album album;
}
