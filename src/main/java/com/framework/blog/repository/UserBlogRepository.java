package com.framework.blog.repository;

import com.framework.blog.model.UserBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBlogRepository extends JpaRepository<UserBlog, Long> {
}
