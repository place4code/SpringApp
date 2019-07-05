package com.place4code.reddit.service;

import com.place4code.reddit.model.Comment;
import com.place4code.reddit.repo.CommentRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    CommentRepo commentRepo;

    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public void save(Comment comment) {
        commentRepo.save(comment);
    }

    public List<Comment> findAllByCreatedBy(String email) {
        return commentRepo.findAllByCreatedBy(email);
    }

    public Optional<Comment> findById(Long id) {
        return commentRepo.findById(id);
    }

    public void delete(Comment comment) {
        commentRepo.delete(comment);
    }
}
