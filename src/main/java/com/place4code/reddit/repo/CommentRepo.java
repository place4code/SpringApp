package com.place4code.reddit.repo;

import com.place4code.reddit.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Long> {

    List<Comment> findAllByCreatedBy(String email);

}
