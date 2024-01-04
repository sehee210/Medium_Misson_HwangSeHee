package com.ll.medium.domain.post.post;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.service.MemberService;
import com.ll.medium.domain.post.post.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostTests {

    @Autowired
    private PostService postService;

    @Autowired
    private MemberService memberService;

    @Test
    void testJpa() {
        for (int i = 1; i <= 150; i++) {
            String title = String.format("유료글 샘플입니다:[%03d]", i);
            String body = "테스트를 위한 유료글 샘플";
            boolean isPublished = true;
            Member author = this.memberService.getMemberById(Long.valueOf(13));
            this.postService.create(title, body, isPublished, author);
        }
    }
}
