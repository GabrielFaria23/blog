package com.framework.blog.util;

import com.framework.blog.model.Image;

public class ImageCreator {

    public static Image createImageToBeSaved(){
        return Image.builder()
                .imagePath("path")
                .post(PostCreator.createValidPost())
                .build();
    }

    public static Image createValidImage(){
        return Image.builder()
                .id(1L)
                .imagePath("path")
                .post(PostCreator.createValidPost())
                .build();
    }

    public static Image createValidUpdateImage(){
        return Image.builder()
                .id(1L)
                .imagePath("path 2")
                .post(PostCreator.createValidPost())
                .build();
    }
}
