package com.taahaagul.redditclone.repository;

import com.taahaagul.redditclone.model.Comment;
import com.taahaagul.redditclone.model.Post;
import com.taahaagul.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);
}
