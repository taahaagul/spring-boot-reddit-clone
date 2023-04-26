package com.taahaagul.redditclone.service;

import com.taahaagul.redditclone.dto.PostRequest;
import com.taahaagul.redditclone.exception.SubredditNotFoundException;
import com.taahaagul.redditclone.model.Post;
import com.taahaagul.redditclone.model.Subreddit;
import com.taahaagul.redditclone.repository.PostRepository;
import com.taahaagul.redditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
        Post post = new Post();
        post.setPostName(postRequest.getPostName());
        post.setUrl(postRequest.getUrl());
        post.setDescription(postRequest.getDescription());
        post.setSubreddit(subreddit);
        post.setUser(authService.getCurrentUser());
        post.setCreatedDate(Instant.now());
        postRepository.save(post);
    }
}
