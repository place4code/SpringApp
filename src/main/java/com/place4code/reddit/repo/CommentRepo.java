package com.place4code.reddit.repo;

import com.place4code.reddit.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepo extends JpaRepository<Comment,Long> {

    List<Comment> findAllByCreatedBy(String email);

    Optional<Comment> findById(Long id);

}
