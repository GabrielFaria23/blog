package com.framework.blog.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "album")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "album")
    private List<Photo> photos;

    @ManyToOne()
    @JoinColumn(name = "user_blog_id", referencedColumnName = "id", nullable = false)
    private UserBlog userBlog;
}
