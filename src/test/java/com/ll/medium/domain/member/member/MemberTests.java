package com.ll.medium.domain.member.member;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.repository.MemberRepository;
import com.ll.medium.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootTest
class MemberTests {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberService memberService;

    @Test
    void testJpa() {
        for (int i = 1; i <= 150; i++) {
            String username = String.format("paid_user%03d", i);
            String password = passwordEncoder.encode("0000");
            boolean isPaid = true;
            this.memberService.create_test(username, password, isPaid);
        }
    }
}