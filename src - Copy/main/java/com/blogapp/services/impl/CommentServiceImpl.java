package com.blogapp.services.impl;

import com.blogapp.entities.Comment;
import com.blogapp.entities.Post;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CommentDto;
import com.blogapp.repositories.CommentRepository;
import com.blogapp.repositories.PostRepository;
import com.blogapp.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));
        commentRepository.delete(comment);
    }
}
