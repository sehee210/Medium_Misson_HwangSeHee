package com.ll.medium.domain.post.post.controller;

import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/b")
public class BlogController {

    private final PostService postService;

    @GetMapping("/{userid}")
    public String list(Model model, @PathVariable("userid") String userid) {
        List<Post> postList = this.postService.getidPublishedList(userid);
        model.addAttribute("postList", postList);
        return "domain/post/post/blog_list";
    }

    @GetMapping("/{userid}/{postid}")
    public String list(Model model, @PathVariable("userid") String userid, @PathVariable("postid") Integer postid) {
        Post post = this.postService.getPost(postid);
        model.addAttribute("post", post);
        return "domain/post/post/post_detail";
    }
}