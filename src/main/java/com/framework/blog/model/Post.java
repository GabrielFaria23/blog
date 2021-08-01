package com.framework.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String description;

    @NotNull
    private String image;

    @NotNull
    private String link;

    @ManyToOne()
    @JoinColumn(name = "user_blog_id", referencedColumnName = "id", nullable = false)
    private UserBlog userBlog;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL)
    private List<Image> images;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

}
