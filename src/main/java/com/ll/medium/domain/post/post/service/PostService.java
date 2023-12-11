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


}
