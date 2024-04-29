package com.blogapp.controllers;

import com.blogapp.config.AppConstants;
import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;
import com.blogapp.services.FileService;
import com.blogapp.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@PathVariable Integer userId,
                                              @PathVariable Integer categoryId,
                                              @Valid @RequestBody PostDto postDto) {
        PostDto post = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostByUser(
            @PathVariable Integer userId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize) {
        PostResponse postResponse = postService.getPostByUser(userId, pageNumber, pageSize);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostByCategory(
            @PathVariable Integer categoryId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize) {
        PostResponse postDtoList = postService.getPostByCategory(categoryId, pageNumber, pageSize);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder) {
        PostResponse postDtoList = postService.getAllPost(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        PostDto postDto = postService.getPostById(postId);
        return new ResponseEntity<>(postDto, HttpStatus.FOUND);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer postId) {
        postService.deletePost(postId);
        return new ResponseEntity(new ApiResponse("Post Deleted", "true"), HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Integer postId, @RequestBody PostDto postDto) {
        PostDto updatedPost = postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @GetMapping("/posts/search/{title}")
    public ResponseEntity<PostDto> searchPost(@PathVariable String title) {
        return new ResponseEntity(postService.searchPostsByTitle(title), HttpStatus.OK);
    }

    // Post Image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image") MultipartFile imageFile,
            @PathVariable Integer postId
    ) {
        try {
            PostDto postById = postService.getPostById(postId);
            String imageName = fileService.uploadImage(path, imageFile);
            postById.setImageName(imageName);
            PostDto postDto = postService.updatePost(postById, postId);
            return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse("Error uploading Image", "false"), HttpStatus.EXPECTATION_FAILED);
        }
    }

    //  Get Image
    @GetMapping(value = "post/image/{imageName}" , produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {
        try {
            InputStream resource = this.fileService.getResource(path , imageName);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(resource , response.getOutputStream());
        } catch (FileNotFoundException e) {
            response.sendError(404 , "Image Not Found");
        } catch (IOException e) {
            response.sendError(500 , "Internal Server Error");
        }

    }
}
