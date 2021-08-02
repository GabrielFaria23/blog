package com.framework.blog.util;

import com.framework.blog.model.Photo;

public class PhotoCreator {

    public static Photo createPhotoToBeSaved(){
        return Photo.builder()
                .photoPath("path")
                .album(AlbumCreator.createValidAlbum())
                .build();
    }

    public static Photo createValidPhoto(){
        return Photo.builder()
                .id(1L)
                .photoPath("path")
                .album(AlbumCreator.createValidAlbum())
                .build();
    }

    public static Photo createValidUpdatePhoto(){
        return Photo.builder()
                .id(1L)
                .photoPath("path 2")
                .album(AlbumCreator.createValidAlbum())
                .build();
    }
}
