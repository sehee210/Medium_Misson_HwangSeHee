package com.ll.medium.domain.comment.comment.controller;

import com.ll.medium.domain.comment.comment.service.CommentService;
import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/post")
@RequiredArgsConstructor
@Controller
public class CommentController {
    // /post/5/comment/write
    private final PostService postService;
    private final CommentService commentService;

    @PostMapping("{id}/comment/write")
    public String createComment(Model model, @PathVariable("id") Integer id, @RequestParam(value="body") String body) {
        Post post = this.postService.getPost(id);
        this.commentService.create(post, body);
        return String.format("redirect:/post/%s", id);
    }
}
