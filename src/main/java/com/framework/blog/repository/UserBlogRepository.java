package com.framework.blog.repository;

import com.framework.blog.model.UserBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBlogRepository extends JpaRepository<UserBlog, Long> {

    Optional<UserBlog> findByLogin(String login);
}
