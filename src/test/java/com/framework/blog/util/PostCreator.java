package com.framework.blog.util;

import com.framework.blog.model.Post;

public class PostCreator {

    public static Post createPostToBeSaved(){
        return Post.builder()
                .description("description")
                .link("link")
                .userBlog(UserBlogCreator.createValidUserBlog())
                .build();
    }

    public static Post  createValidPost(){
        return Post.builder()
                .id(2L)
                .description("description")
                .link("link")
                .userBlog(UserBlogCreator.createValidUserBlog())
                .build();
    }

    public static Post  createValidUpdatePost(){
        return Post.builder()
                .id(2L)
                .description("description update")
                .link("link update")
                .userBlog(UserBlogCreator.createValidUserBlog())
                .build();
    }
}
