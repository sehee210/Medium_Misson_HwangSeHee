package com.ll.medium.domain.post.post.service;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.repository.PostRepository;
import com.ll.medium.global.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

}
