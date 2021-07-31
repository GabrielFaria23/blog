package com.framework.blog.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_blog")
public class UserBlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @NotNull
    @Column(unique = true)
    private String login;

    @NotNull
    private String password;

    @CPF
    @NotNull
    @Column(unique = true)
    private String cpf;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userBlog")
    private List<Post> posts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userBlog")
    private List<Album> albums;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userBlog")
    private List<Comment> comments;
}
