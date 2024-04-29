package com.blogapp.services;

import com.blogapp.entities.Post;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    PostDto updatePost(PostDto postDto, Integer postId);

    void deletePost(Integer postId);

    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    PostDto getPostById(Integer postId);

    PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);

    PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize);

    List<PostDto> searchPostsByTitle(String title);
}
