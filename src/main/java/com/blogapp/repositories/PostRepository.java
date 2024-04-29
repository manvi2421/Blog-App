package com.blogapp.repositories;

import com.blogapp.entities.Category;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    Page<Post> findByUser(User user, Pageable pageable);

    Page<Post> findByCategory(Category category, Pageable pageable);

    List<Post> findByTitleContaining(String title);
}
