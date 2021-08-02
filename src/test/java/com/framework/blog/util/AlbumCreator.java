package com.framework.blog.util;

import com.framework.blog.model.Album;

public class AlbumCreator {

    public static Album createAlbumToBeSaved(){
        return Album.builder()
                .userBlog(UserBlogCreator.createValidUserBlog())
                .build();
    }

    public static Album   createValidAlbum(){
        return Album.builder()
                .id(1L)
                .userBlog(UserBlogCreator.createValidUserBlog())
                .build();
    }

    public static Album   createValidUpdateAlbum(){
        return Album.builder()
                .id(1L)
                .userBlog(UserBlogCreator.createValidUserBlog())
                .build();
    }
}
