package com.framework.blog.util;

import com.framework.blog.model.Comment;

public class CommentCreator {

    public static Comment createCommentToBeSaved(){
        return Comment.builder()
                .comment("comment")
                .userBlog(UserBlogCreator.createValidUserBlog())
                .post(PostCreator.createValidPost())
                .build();
    }

    public static Comment   createValidComment(){
        return Comment.builder()
                .id(1L)
                .comment("comment")
                .userBlog(UserBlogCreator.createValidUserBlog())
                .post(PostCreator.createValidPost())
                .build();
    }

    public static Comment   createValidUpdateComment(){
        return Comment.builder()
                .id(1L)
                .comment("comment")
                .userBlog(UserBlogCreator.createValidUserBlog())
                .post(PostCreator.createValidPost())
                .build();
    }
}
