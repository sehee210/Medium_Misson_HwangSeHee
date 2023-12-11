package com.ll.medium.domain.post.post.controller;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.service.MemberService;
import com.ll.medium.domain.post.post.dto.PostForm;
import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.repository.PostRepository;
import com.ll.medium.domain.post.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        List<Post> postList = this.postService.getPublishedList();
        model.addAttribute("postList", postList);
        return "domain/post/post/post_list";
    }

    @GetMapping(value = "/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);
        return "domain/post/post/post_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String postWrite(PostForm postForm) {
        return "domain/post/post/write_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String postWrite(@Valid PostForm postForm, BindingResult bindingResult, @RequestParam(value = "isPublished", defaultValue = "false") boolean isPublished, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "domain/post/post/write_form";
        }
        Member author = this.memberService.getMember(principal.getName());
        this.postService.create(postForm.getTitle(), postForm.getBody(), isPublished, author);
        return "redirect:/post/list"; // 게시글 저장 후 다시 목록으로 이동
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/modify")
    public String postModify(PostForm postForm, @PathVariable("id") Integer id, Principal principal) {
        Post post = this.postService.getPost(id);
        if(!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        postForm.setTitle(post.getTitle());
        postForm.setBody(post.getBody());
        postForm.setIspublished(post.isPublished());
        return "domain/post/post/write_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/modify")
    public String postModify(@Valid PostForm postForm, BindingResult bindingResult, @RequestParam(value = "isPublished", defaultValue = "false") boolean isPublished,
                                 Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "write_form";
        }
        Post post = this.postService.getPost(id);
        if (!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.postService.modify(post, postForm.getTitle(), postForm.getBody(), isPublished);
        return String.format("redirect:/post/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/delete")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Post post = this.postService.getPost(id);
        if (!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.postService.delete(post);
        return "redirect:/post/list";
    }

}