package com.blogapp.services.impl;

import com.blogapp.entities.Category;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;
import com.blogapp.repositories.CategoryRepository;
import com.blogapp.repositories.PostRepository;
import com.blogapp.repositories.UserRepository;
import com.blogapp.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private PostDto postToDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

    private Post dtoToPost(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }


    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        Post post = dtoToPost(postDto);
        post.setImageName("default.png");
        post.setCreatedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost = postRepository.save(post);
        return postToDto(newPost);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post updatedPost = postRepository.save(post);
        return modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sort = null;
        if(sortOrder.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> pagePost = postRepository.findAll(pageable);
        List<Post> postList = pagePost.getContent();

        return preparePostResponse(pagePost, postList);
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
        Page<Post> pagePost = postRepository.findByCategory(category, pageable);
        List<Post> postList = pagePost.getContent();

        return preparePostResponse(pagePost, postList);
    }

    @Override
    public PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Page<Post> pagePost = postRepository.findByUser(user, pageable);
        List<Post> postList = pagePost.getContent();

        return preparePostResponse(pagePost, postList);
    }

    @Override
    public List<PostDto> searchPostsByTitle(String title) {
        List<Post> postList = postRepository.findByTitleContaining(title);
        List<PostDto> postDtoList = postList.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtoList;
    }

    public PostResponse preparePostResponse(Page<Post> pagePost, List<Post> postList) {
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postList.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList()));
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }
}
