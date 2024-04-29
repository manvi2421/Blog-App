package com.blogapp.controllers;

import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.CommentDto;
import com.blogapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @PathVariable Integer postId) {

        CommentDto comment = commentService.createComment(commentDto, postId);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment Deleted Successfully!", "true"), HttpStatus.OK);
    }
}
