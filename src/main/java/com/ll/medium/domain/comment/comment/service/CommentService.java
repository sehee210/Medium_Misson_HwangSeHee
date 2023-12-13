package com.ll.medium.domain.comment.comment.service;

import com.ll.medium.domain.comment.comment.entity.Comment;
import com.ll.medium.domain.comment.comment.repository.CommentRepository;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.global.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment create(Post post, String body, Member author) {
        Comment comment = new Comment();
        comment.setBody(body);
        comment.setCreateDate(LocalDateTime.now());
        comment.setPost(post);
        comment.setAuthor(author);
        this.commentRepository.save(comment);
        return comment;
    }

    public Comment getComment(Integer id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (!comment.isPresent()) {
            throw new DataNotFoundException("answer not found");
        }
        return comment.get();
    }

    public void modify(Comment comment, String body) {
        comment.setBody(body);
        comment.setModifyDate(LocalDateTime.now());
        this.commentRepository.save(comment);
    }
}
