package com.ll.medium.domain.post.post.controller;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.service.MemberService;
import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.repository.PostRepository;
import com.ll.medium.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Post> postList = this.postService.getList();
        model.addAttribute("postList", postList);
        return "domain/post/post/post_list";
    }

    @GetMapping(value = "/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);
        return "domain/post/post/post_detail";
    }

    @GetMapping("/write")
    public String postWrite() {
        return "domain/post/post/write_form";
    }

    @PostMapping("/write")
    public String postWrite(@RequestParam(value = "title") String title, @RequestParam(value = "body") String body, @RequestParam(value = "isPublished", defaultValue = "false") boolean isPublished, Principal principal) {
        Member author = this.memberService.getMember(principal.getName());
        this.postService.create(title, body, isPublished, author);
        return "redirect:/post/list"; // 게시글 저장 후 다시 목록으로 이동
    }
}