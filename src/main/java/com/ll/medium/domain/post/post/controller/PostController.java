package com.ll.medium.domain.post.post.controller;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.service.MemberService;
import com.ll.medium.domain.post.post.dto.PostForm;
import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.repository.PostRepository;
import com.ll.medium.domain.post.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String postWrite(PostForm postForm) {
        return "domain/post/post/write_form";
    }

    @PostMapping("/write")
    public String postWrite(@Valid PostForm postForm, BindingResult bindingResult, @RequestParam(value = "isPublished", defaultValue = "false") boolean isPublished, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "domain/post/post/write_form";
        }
        Member author = this.memberService.getMember(principal.getName());
        this.postService.create(postForm.getTitle(), postForm.getBody(), isPublished, author);
        return "redirect:/post/list"; // 게시글 저장 후 다시 목록으로 이동
    }
}