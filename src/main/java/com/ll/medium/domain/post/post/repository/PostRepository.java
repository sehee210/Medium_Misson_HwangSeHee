package com.ll.medium.domain.post.post.repository;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.post.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer>, PostRepositoryCustom {
    List<Post> findByIsPublished(boolean isPublished);

    List<Post> findTop30ByIsPublishedOrderByCreateDateDesc(boolean isPublished);

    Page<Post> findByIsPublishedTrue(Specification<Post> spec, Pageable pageable);

    List<Post> findByAuthorUsernameAndIsPublished(String username, boolean isPublished);

    boolean existsByLikeContainsAndAuthor(Member like, Member author);

    @Query("select "
            + "distinct q "
            + "from Post q "
            + "left outer join Member m1 on q.author=m1 "
            + "where "
            + "   (q.title like %:kw% or q.body like %:kw% or m1.username like %:kw%) "
            + "   and q.isPublished = true")
    Page<Post> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
