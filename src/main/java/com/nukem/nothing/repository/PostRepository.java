package com.nukem.nothing.repository;

import com.nukem.nothing.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
