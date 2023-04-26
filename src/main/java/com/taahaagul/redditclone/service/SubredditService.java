package com.taahaagul.redditclone.service;

import com.taahaagul.redditclone.dto.SubredditDto;
import com.taahaagul.redditclone.exception.SpringRedditException;
import com.taahaagul.redditclone.model.Subreddit;
import com.taahaagul.redditclone.model.User;
import com.taahaagul.redditclone.repository.SubredditRepository;
import com.taahaagul.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit save = mapSubredditDto(subredditDto);
        save.setCreateDate(Instant.now());
        save.setUser(authService.getCurrentUser());
        subredditRepository.save(save);
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(toList());
    }


    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with Id - " + id));
        return mapToDto(subreddit);
    }

    private Subreddit mapSubredditDto(SubredditDto subredditDto) {
        return Subreddit.builder()
                .name(subredditDto.getName())
                .description(subredditDto.getDescription())
                .build();
    }
    private SubredditDto mapToDto(Subreddit subreddit) {
        return SubredditDto.builder()
                .name(subreddit.getName())
                .id(subreddit.getId())
                .numberOfPosts(subreddit.getPosts().size())
                .description(subreddit.getDescription())
                .build();
    }
}
