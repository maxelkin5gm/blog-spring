package com.blog.repositories;


import com.blog.models.Post;
import com.blog.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
}
