package com.ll.medium.domain.member.member;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootTest
class MemberTests {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testJpa() {
        Member member1 = new Member();
        member1.setUsername("user123");
        member1.setPassword(passwordEncoder.encode("0000"));
        this.memberRepository.save(member1);
    }
}