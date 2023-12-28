package com.ll.medium.domain.post.post.service;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.repository.PostRepository;
import com.ll.medium.global.exception.DataNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public List<Post> getList() {
        return this.postRepository.findAll();
    }

    public Post getPost(Integer id) {
        Optional<Post> post = this.postRepository.findById(id);
        if (post.isPresent()) {
            return post.get();
        } else {
            throw new DataNotFoundException("post not found");
        }
    }

    public void create(String title, String body, boolean isPublished, Member author) {
        Post post = new Post();
        post.setTitle(title);
        post.setBody(body);
        post.setCreateDate(LocalDateTime.now());
        post.setPublished(isPublished);
        post.setAuthor(author);
        post.setHit(0);
        this.postRepository.save(post);
    }

    public List<Post> getPublishedList() {
        return postRepository.findByIsPublished(true);
    }

    public void modify(Post post, String title, String body, boolean ispublished) {
        post.setTitle(title);
        post.setBody(body);
        post.setPublished(ispublished);
        post.setModifyDate(LocalDateTime.now());
        this.postRepository.save(post);
    }

    public void delete(Post post) {
        this.postRepository.delete(post);
    }

    public List<Post> getLatestPublishedPosts() {
        // 최신순으로 정렬된 공개된 게시글 중 최신 30개 가져오기
        List<Post> latestPublishedPosts = postRepository.findTop30ByIsPublishedOrderByCreateDateDesc(true);

        return latestPublishedPosts;
    }

    public Page<Post> getPaging(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.postRepository.findByIsPublishedTrue(pageable);
    }

    //BlogController에서 사용
    public List<Post> getidPublishedList(String userId) {
        return this.postRepository.findByAuthorUsernameAndIsPublished(userId, true);
    }

    public void like(Post post, Member member) {
        post.getLike().add(member);
        this.postRepository.save(post);
    }

    @Transactional
    public Post hitPost(Integer id) {
        Optional<Post> hPost = this.postRepository.findById(id);
        if (hPost.isPresent()) {
            Post post = hPost.get();
            post.setHit(post.getHit() + 1);
            return post;
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    @Transactional
    public void increaseHit(Integer postId) {
        Post post = postRepository.findById(postId).orElse(null);

        if (post != null) {
            post.setHit(post.getHit() + 1);
            postRepository.save(post);
        } else {
            throw new EntityNotFoundException("Post not found with id: " + postId);
        }
    }

    public void cancellike(Post post, Member member) {
        post.getLike().remove(member);
        this.postRepository.save(post);
    }

    public boolean hasLiked(Post post, Member member) {
        return postRepository.existsByLikeContainsAndAuthor(member, post.getAuthor());
    }

    public boolean isPaidPost(Integer postId) {
        Post post = postRepository.findById(postId).orElse(null);
        return post != null && post.isIspaid();
    }
}