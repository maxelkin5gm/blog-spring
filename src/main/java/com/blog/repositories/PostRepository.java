package com.blog.repositories;


import com.blog.entities.PostEntity;
import com.blog.entities.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findByUser(UserEntity user);
    List<PostEntity> findByOrderByDateDesc();
}
