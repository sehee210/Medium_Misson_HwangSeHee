package com.ll.medium.domain.comment.comment.service;

import com.ll.medium.domain.comment.comment.entity.Comment;
import com.ll.medium.domain.comment.comment.repository.CommentRepository;
import com.ll.medium.domain.post.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public void create(Post post, String body) {
        Comment comment = new Comment();
        comment.setBody(body);
        comment.setCreateDate(LocalDateTime.now());
        comment.setPost(post);
        this.commentRepository.save(comment);
    }
}
