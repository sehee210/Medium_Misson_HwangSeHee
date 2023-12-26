package com.ll.medium.domain.member.member.controller;

import com.ll.medium.domain.member.member.dto.MemberCreateForm;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String signup(MemberCreateForm memberCreateForm) {
        return "domain/member/member/join_form";
    }

    @PostMapping("/join")
    public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "domain/member/member/join_form";
        }

        if (!memberCreateForm.getPassword().equals(memberCreateForm.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "domain/member/member/join_form";
        }

        memberService.create(memberCreateForm.getUsername(), memberCreateForm.getPasswordConfirm());

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "domain/member/member/login_form";
    }
}