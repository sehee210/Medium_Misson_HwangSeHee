package com.ll.medium.domain.post.post.controller;

import com.ll.medium.domain.comment.comment.dto.CommentForm;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.service.MemberService;
import com.ll.medium.domain.post.post.dto.PostForm;
import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.repository.PostRepository;
import com.ll.medium.domain.post.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;


import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Post> paging = this.postService.getPaging(page);
        model.addAttribute("paging", paging);
        return "domain/post/post/post_list";
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_PAID') and @postService.isPaidPost(#id) or !@postService.isPaidPost(#id)")
    public String detail(Model model, @PathVariable("id") Integer id, CommentForm commentForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_PAID"))) {
            // 현재 로그인한 사용자가 ROLE_PAID가 아닌 경우 권한이 없다는 메시지를 표시하고 리턴
            return "domain/post/post/post_denied";
        }

        Post post = this.postService.hitPost(id);
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

    @GetMapping("/{postId}/increaseHit")
    public ResponseEntity<String> increaseHit(@PathVariable("postId") Integer postId) {
        this.postService.increaseHit(postId);
        return ResponseEntity.ok("조회수가 증가되었습니다.");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{postId}/like")
    public String like(Principal principal, @PathVariable("postId") Integer postId, Model model) {
        Post post = this.postService.getPost(postId);
        Member member = this.memberService.getMember(principal.getName());

        if (!this.postService.hasLiked(post, member)) {
            this.postService.like(post, member);
        } else {
            this.postService.cancellike(post, member);
        }

        return String.format("redirect:/post/%s", postId);
    }
}