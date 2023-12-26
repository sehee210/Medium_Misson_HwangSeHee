package com.ll.medium.domain.post.post.repository;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.post.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByIsPublished(boolean isPublished);

    List<Post> findTop30ByIsPublishedOrderByCreateDateDesc(boolean isPublished);

    Page<Post> findByIsPublishedTrue(Pageable pageable);

    List<Post> findByAuthorUsernameAndIsPublished(String username, boolean isPublished);

    boolean existsByLikeContainsAndAuthor(Member like, Member author);
}
