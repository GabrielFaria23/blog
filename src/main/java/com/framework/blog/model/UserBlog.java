package com.framework.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_blog")
public class UserBlog implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;

    @CPF
    @NotNull
    @Column(unique = true)
    private String cpf;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_blog_role")
    private UserBlogRole userBlogRole;

    @NotNull
    private static Boolean locked = false;

    @NotNull
    private static Boolean enabled = true;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userBlog")
    private List<Post> posts;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userBlog")
    private List<Album> albums;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userBlog")
    private List<Comment> comments;

    public UserBlog(String name, String username, String password, String cpf, UserBlogRole userBlogRole) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.cpf = cpf;
        this.userBlogRole = userBlogRole;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userBlogRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
