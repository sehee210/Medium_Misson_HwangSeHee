package com.ll.medium.domain.member.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)      //중복 값 저장 불가
    private String username;

    private String password;

    @Column(columnDefinition = "boolean default false")
    private boolean ispaid;
}